CREATE TABLE trimm.step_data(
	time text NOT NULL,
	step integer NOT NULL,
	username text NOT NULL,
	run integer NOT NULL,
	surface integer,
	ic_right text,
	to_right text,
	axtibacc_right numeric,
	tibimpact_right numeric,
	axsacacc_right numeric,
	sacimpact_right numeric,
	brakingforce_right numeric,
	pushoffpower_right numeric,
	tibintrot_right numeric,
	vll_right numeric,
	ic_left text,
	to_left text,
	axtibacc_left numeric,
	tibimpact_left numeric,
	axsacacc_left numeric,
	sacimpact_left numeric,
	brakingforce_left numeric,
	pushoffpower_left numeric,
	tibintrot_left numeric,
	vll_left numeric
PRIMARY KEY(step,username,run),
FOREIGN KEY username REFERENCES trimm.user(usern),,
FOREIGN KEY surface REFERENCES trimm.surface_type(id));


CREATE TABLE trimm.bodypackruns(
	date text,
	BodyPackFile text,
	run_no integer,
	runner text,
	distance text,
	time text,
	shoes text,
	Surface integer,
	description text,
	Remark text,
	StravaLink text
PRIMARY KEY(run_no,runner),
FOREIGN KEY run_no REFERENCES trimm.step_data(run),
FOREIGN KEY runner REFERENCES trimm.user(usern),
FOREIGN KEY surface REFERENCES trimm.surface_type(id),
FOREIGN KEY shoes REFERENCES trimm.shoes(sid)
);


CREATE TABLE trimm.shoes(
	sid integer PRIMARY KEY,
	brand text,
	model text,
	Heel(mm) integer,
	Forefoot(mm) integer,
	Drop(mm) integer,
	Weight integer);


CREATE TABLE trimm.surface_type
	id integer PRIMARY KEY,
	description text NOT NULL,
	type text NOT NULL);


CREATE TABLE trimm.user
	usern text PRIMARY KEY,
	password text,
        sessiontoken text
        session_expire_time text);
CREATE TABLE trimm.account
username text PRIMARY KEY,
firstname text,
lastname text,
email text,
weight numeric,
height numeric,
token text,
UNIQUE (token,email),
FOREIGN KEY username REFERENCES trimm.user(usern)
);
CREATE TABLE trimm.emailverify
email text,
token text
PRIMARY KEY(email,token));
