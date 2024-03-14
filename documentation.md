# Pizzaland

### Introduction

L’entreprise Pizzaland souhaite mettre à disposition des entreprises tierces, la gestion de ses pizzas et leurs
ingrédients ainsi que les commandes qui sont effectuées par les clients. Elle envisage pour cela de mettre en place une
API REST.

Pour simplifier le travail, Pizzaland a décidé de ne communiquer qu’en JSON. Bien évidemment ce travail necessite
une base de données. Pour faciliter la maintenance future de l’application on souhaite isoler les requêtes SQL dans des
DAO, et la connexion à la base de données à un seul endoit.

# API Ingrédients
Dans un premier temps on va organiser les ingrédients des pizzas.
| URI | Opération | MIME | Requête |  Réponse |
| --- | --- | --- | --- | --- |
| /ingredients  | GET | <-application/json | | liste des ingrédients (I1) |
| /ingredients/{id} | GET | <-application/json | | un ingrédient (I1) ou 400 ou 404 |
| /ingredients/{id}/name | GET | 



### GET sur tout les ingrédients
Nous avons écrit un findAll qui effectu un SELECT sur l'ensemble de la table et retourne une liste d'objets JSON.