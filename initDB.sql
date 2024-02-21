drop table if exists ingredientsPizza;
drop table if exists ingredients;
drop table if exists pizzas;

CREATE TABLE ingredients(id int primary key, nom text,prix int);
INSERT INTO ingredients VALUES (1,'pomme de terre',3);
INSERT INTO ingredients VALUES (2,'poivrons',2);
INSERT INTO ingredients VALUES (3,'chorizo',4);
INSERT INTO ingredients VALUES (4,'lardons',4);
INSERT INTO ingredients VALUES (5,'aubergines',2);
INSERT INTO ingredients VALUES (6,'champignons',2);
CREATE TABLE pizzas(id int primary key, nom text, prixBase int, pate text);
INSERT INTO pizzas VALUES (1,'savoyarde',10, 'extra');
INSERT INTO pizzas VALUES (2,'chorizo',7, 'fine');
INSERT INTO pizzas VALUES (3,'canibale',8, 'normal');
CREATE TABLE ingredientsPizza(
    idPiz int,
    idIng int,
    FOREIGN KEY (idPiz) references pizzas(id) ON DELETE CASCADE,
    FOREIGN KEY (idIng) references ingredients(id) ON DELETE CASCADE) ;
INSERT INTO ingredientsPizza VALUES (1,1);
INSERT INTO ingredientsPizza VALUES (1,4);
INSERT INTO ingredientsPizza VALUES (2,3);
INSERT INTO ingredientsPizza VALUES (3,3);
INSERT INTO ingredientsPizza VALUES (3,4);