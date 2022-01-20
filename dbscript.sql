DROP VIEW IF EXISTS machines_games_joined;
DROP TABLE IF EXISTS records;
DROP TABLE IF EXISTS machines;
DROP TABLE IF EXISTS games;
DROP TABLE IF EXISTS players;
DROP TABLE IF EXISTS admins;
DROP TABLE IF EXISTS logins;

CREATE TABLE logins(
	user_email varchar(50) PRIMARY KEY,
	user_password varchar(100)
);

CREATE TABLE admins(
	admin_email varchar(50) REFERENCES logins(user_email) PRIMARY KEY,
	admin_first_name varchar(15),
	admin_last_name varchar(20)
);

CREATE TABLE players(
	player_email varchar(50) PRIMARY KEY REFERENCES logins(user_email) ON DELETE CASCADE,
	player_first_name varchar(15),
	player_last_name varchar(20),
	player_name varchar(15) UNIQUE,
	token_balance integer CHECK (token_balance >= 0),
	ticket_balance integer CHECK (ticket_balance >= 0),
	tier integer
);

CREATE TABLE games(
	title varchar(20) PRIMARY KEY,
	token_cost integer,
	point_min integer,
	point_max integer,
	ticket_to_point_ratio numeric
);

CREATE TABLE machines(
	machine_number SERIAL PRIMARY KEY,
	game_title varchar(20) REFERENCES games(title)
);

CREATE TABLE records(
	record_time timestamp,
	player varchar(15),
	score integer,
	machine integer REFERENCES machines(machine_number),
	PRIMARY KEY (record_time, player),
	FOREIGN KEY (player) REFERENCES players(player_name)
);

CREATE OR REPLACE VIEW machines_games_joined AS SELECT * FROM machines LEFT JOIN games ON  machines.game_title= games.title;

CREATE OR REPLACE FUNCTION random_between(low INT ,high INT)
   RETURNS INT AS
$$
BEGIN
   RETURN floor(random()* (high-low + 1) + low);
END;
$$ language 'plpgsql';

CREATE OR REPLACE FUNCTION play_game() RETURNS TRIGGER AS
$$
DECLARE
	new_score integer;
	player_tier integer;
	game_cost integer;
	score_min integer;
	score_max integer;
	ticket_rate NUMERIC;
BEGIN
	SELECT INTO score_min point_min FROM machines_games_joined WHERE machine_number = NEW.machine;
	SELECT INTO score_max point_max FROM machines_games_joined WHERE machine_number = NEW.machine;
	SELECT INTO game_cost token_cost FROM machines_games_joined WHERE machine_number = NEW.machine;
	SELECT INTO player_tier tier FROM players WHERE player_name = NEW.player;
	SELECT INTO ticket_rate ticket_to_point_ratio FROM machines_games_joined WHERE machine_number = NEW.machine;
	SELECT INTO new_score random_between(score_min, score_max);
	UPDATE players SET
		token_balance = token_balance - game_cost,
		ticket_balance = ticket_balance + player_tier * floor(new_score * ticket_rate)
		WHERE player_name = NEW.player;
	UPDATE records SET score = new_score WHERE (record_time, player) = (NEW.record_time, NEW.player);

	RETURN NEW;
END
$$
LANGUAGE plpgsql;

CREATE TRIGGER game_transaction AFTER INSERT ON records FOR EACH ROW EXECUTE FUNCTION play_game();

WITH user_data (email, first_name, last_name, username, tokens, tickets, tier, user_password) AS (
	VALUES ('adawg@sample.com', 'Adam', 'Appleseed', 'ADAWG', 500, 200, 1, '$2a$10$i50y4oYry5WA6rcSnsMU6.aJhFMbTLGdjjLKjaADoIB5.iF7Yksmy'),
	('betty852@sample.com', 'Bethany', 'Butcher', 'Betty', 700, 5421, 2, '$2a$10$aA8dtz0XDypB1cOVXkA86.6SPhV0R1bYOy1WfP8JBL2NdUq43MRYy'),
	('christopher@dandb.com', 'Chris', 'Carpenter', NULL, NULL, NULL, NULL, '$2a$10$jZ0CLMGf1iDJUBAhIdXOqe5bKdm/DVJcz978L/ZAaFNfgM65n8Pea')
	),

	login_insert AS (
		INSERT INTO logins(user_email, user_password)
			SELECT email, user_password FROM user_data
	),

	player_insert AS (
		INSERT INTO players(player_email,
			player_first_name,
			player_last_name,
			player_name,
			token_balance,
			ticket_balance,
			tier)
			SELECT email, first_name, last_name, username, tokens, tickets, tier FROM user_data WHERE username NOTNULL
	)

	INSERT INTO admins(admin_email, admin_first_name, admin_last_name)
		SELECT email, first_name, last_name FROM user_data WHERE username ISNULL;


INSERT INTO games(title,
	token_cost,
	point_min,
	point_max,
	ticket_to_point_ratio)
	VALUES ('pinball', 4, 0, 99999, 0.01),
	('whack-a-critter', 1, 5, 100, 0.2),
	('galaxy blasters', 5, 0, 1000, 1);

INSERT INTO machines(game_title)
	VALUES ('pinball'), ('pinball'), ('pinball'), ('pinball'), ('pinball'), ('whack-a-critter'), ('whack-a-critter'), ('galaxy blasters');

INSERT INTO records (record_time, player, machine)
	VALUES ('2022-01-01 14:00:00.000', 'ADAWG', 1),
	('2022-01-01 14:05:00.000', 'ADAWG', 1),
	('2022-01-01 14:10:00.000', 'Betty', 3),
	('2022-01-01 14:30:00.000', 'Betty', 8),
	('2022-01-01 14:45:00.000', 'Betty', 1),
	('2022-01-01 14:45:00.000', 'ADAWG', 8),
	('2022-01-01 14:50:00.000', 'ADAWG', 6),
	('2022-01-01 14:55:00.000', 'Betty', 6);

INSERT INTO logins(user_email, user_password) VALUES ('deleteme@bye.now', 'ToBeDeleted');
INSERT INTO players(player_email, player_name, token_balance, ticket_balance) VALUES ('deleteme@bye.now', 'no one', 1, 1);
