-- ******************* --
-- Creation bdd SQLite --
-- ******************* --

PRAGMA foreign_keys = ON;

-- TABLE nomDePlat *** renommer 'designation'
CREATE TABLE IF NOT EXISTS designation (
	id int PRIMARY KEY NOT NULL ,
	nomPlat varchar(250),
	regime varchar(250)
);

-- TABLE ingredient
CREATE TABLE IF NOT EXISTS ingredient (
	id int PRIMARY KEY NOT NULL,
	nomIngr varchar(100),
	groupe varchar(100),
	kcal int
);

-- TABLE recette  *** renommer 'composition'
CREATE TABLE IF NOT EXISTS recette (
	id int PRIMARY KEY NOT NULL,
	P_id int REFERENCES designation(id),
	portion int,
	prepa int,
	cuisson int,
	I_id int REFERENCES ingredient(id), 
	ingrQtt int,
	ingrUnite varchar(25)
);


-- VIEW v_globale  *** renommer 'v_glob'
CREATE VIEW IF NOT EXISTS v_glob AS 
	SELECT 
		designation.nomPlat AS Plats,
		recette.portion AS Pers,
		recette.prepa AS Prepa,
		recette.cuisson AS Cuisson,
		ingredient.nomIngr AS Ingredients,
		recette.ingrQtt AS Qtt,
		recette.ingrUnite AS Unites, 	
		ingredient.groupe AS Groupes,
		designation.regime AS Regimes
	FROM
		recette, ingredient, designation
	WHERE
		designation.id = recette.P_id
	AND	ingredient.id = recette.I_id;