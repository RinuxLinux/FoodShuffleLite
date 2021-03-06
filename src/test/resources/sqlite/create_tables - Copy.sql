-- ******************* --
-- Creation bdd SQLite --
-- ******************* --

PRAGMA foreign_keys = ON;

-- TABLE nomDePlat *** renommer 'designation'
CREATE TABLE IF NOT EXISTS designation (
	id int PRIMARY KEY NOT NULL,
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
CREATE TABLE IF NOT EXISTS composition (
	id int PRIMARY KEY NOT NULL,
	P_id int REFERENCES designation(id),
	portion int,
	prepa int,
	cuisson int,
	I_id int REFERENCES ingredient(id), 
	ingrQtt int,
	ingrUnite varchar(25)
);

-- TABLE glossaire
CREATE TABLE IF NOT EXISTS glossaire (
  id int PRIMARY KEY NOT NULL,
  abreviation varchar(150),
  description varchar(150)
);

-- VIEW v_globale  *** renommer 'v_glob'
CREATE view IF NOT EXISTS v_glob as 
	select 
		designation.nomPlat as Plats,
		composition.portion as Pers,
		composition.prepa as Prepa,
		composition.cuisson as Cuisson,
		ingredient.nomIngr as Ingredients,
		composition.ingrQtt as Qtt,
		composition.ingrUnite as Unites, 	
		ingredient.groupe as Groupes,
		designation.regime as Regimes
	from
		recette, ingredient, designation
	where
		designation.id = composition.P_id
	and	ingredient.id = composition.I_id;