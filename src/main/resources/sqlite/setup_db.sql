-- ******************* --
-- Creation bdd SQLite --
-- ******************* --

PRAGMA foreign_keys = ON;

-- TABLE nomDePlat *** renommer 'designation'
create table if not exists designation (
	id int primary key not null ,
	nomPlat varchar(250),
	regime varchar(250)
);

-- TABLE ingredient
create table if not exists ingredient (
	id int primary key not null,
	nomIngr varchar(100),
	groupe varchar(100),
	kcal int
);

-- TABLE recette  *** renommer 'composition'
create table if not exists recette (
	id int primary key not null,
	P_id int REFERENCES designation(id),
	portion int,
	prepa int,
	cuisson int,
	I_id int REFERENCES ingredient(id), 
	ingrQtt int,
	ingrUnite varchar(25)
);


-- VIEW v_globale  *** renommer 'v_glob'
create view if not exists v_glob as 
	select 
		designation.nomPlat as Plats,
		recette.portion as Pers,
		recette.prepa as Prepa,
		recette.cuisson as Cuisson,
		ingredient.nomIngr as Ingredients,
		recette.ingrQtt as Qtt,
		recette.ingrUnite as Unites, 	
		ingredient.groupe as Groupes,
		designation.regime as Regimes
	from
		recette, ingredient, designation
	where
		designation.id = recette.P_id
	and	ingredient.id = recette.I_id;