PRAGMA foreign_keys = ON;
CREATE TABLE IF NOT EXISTS designation ( 
   id int PRIMARY KEY NOT NULL,
   nomPlat varchar(250),
   regime varchar(250));
CREATE TABLE IF NOT EXISTS ingredient ( 
   id int PRIMARY KEY NOT NULL,
   nomIngr varchar(100),
   groupe varchar(100),
   kcal int);
CREATE TABLE IF NOT EXISTS composition ( 
   id int PRIMARY KEY NOT NULL,
   P_id int REFERENCES designation(id),
   portion int,
   prepa int,
   cuisson int,
   I_id int REFERENCES ingredient(id),
   ingrQtt int,
   ingrUnite varchar(25));
CREATE TABLE IF NOT EXISTS glossaire (
   id int PRIMARY KEY NOT NULL,
   abreviation varchar(150),
   description varchar(150));
CREATE TABLE IF NOT EXISTS metadata (
   version varchar(10),
   date datetime);
CREATE VIEW IF NOT EXISTS v_glob as 
SELECT
   designation.nomPlat as Plats,
   composition.portion as Pers,
   composition.prepa as Prepa,
   composition.cuisson as Cuisson,
   ingredient.nomIngr as Ingredients,
   composition.ingrQtt as Qtt,
   composition.ingrUnite as Unites,
   ingredient.groupe as Groupes,
   designation.regime as Regimes 
FROM
   recette,
   ingredient,
   designation 
WHERE
   designation.id = composition.P_id and ingredient.id = composition.I_id;

