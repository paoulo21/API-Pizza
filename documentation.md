# Pizzaland

### Introduction

L’entreprise Pizzaland souhaite mettre à disposition des entreprises tierces, la gestion de ses pizzas et leurs
ingrédients ainsi que les commandes qui sont effectuées par les clients. Elle envisage pour cela de mettre en place une
API REST.

Pour simplifier le travail, Pizzaland a décidé de ne communiquer qu’en JSON. Bien évidemment ce travail necessite
une base de données. Pour faciliter la maintenance future de l’application on souhaite isoler les requêtes SQL dans des
DAO, et la connexion à la base de données à un seul endoit.

# API Ingrédients
***
| URI | Opération | MIME | Requête |  Réponse |
| --- | --- | --- | --- | --- |
| /ingredients  | GET | <-application/json | | liste des ingrédients (I1) |
| /ingredients/{id} | GET | <-application/json | | un ingrédient (I1) ou 400 ou 404 |
| /ingredients/{id}/name | GET | <-text/plain | | le nom de l'ingrédient ou erreur 400 ou erreur 404
| /ingredients | POST | <-/->application/json->application/x-www-form-urlencoded | Ingrédient(I1) | Nouvel Ingrédient (I1) ou 409 si exitse déjà ou 204 si pas de contenu ou 400 ou 401 si pas authentifié |
|  /ingredients/{id} | DELETE |  <-application/json | | liste des ingrédients (I1) 401 si pas authentifié ou 400 ou 404 |


# Corps des requêtes
 ***
### I1
Un ingrédient comporte uniquement un identifiant, un nom et un prix. Sa représentation JSON (I1) est la suivante :
~~~
{
 "id": 1,
 "name": "pomme de terre",
 "prix": 3
}
~~~

# Exemples
 ***
### Lister tous les ingrédients connus dans la base de données


> GET /pizzeria/ingredients

Requête vers le serveur

~~~
GET /pizzeria/ingredients
[
    {
        "id": 1,
        "name": "pomme de terre",
        "prix": 3
    },
    {
        "id": 2,
        "name": "poivrons",
        "prix": 2
    },
    {
        "id": 3,
        "name": "chorizo",
        "prix": 4
    },
    {
        "id": 4,
        "name": "lardons",
        "prix": 4
    },
    ...
]
~~~

Codes de status HTTP
| Status | Description|
| --- | --- |
| 200 OK | La requête s'est effectuée correctement |
***

### Récupèrer les détails d'un ingrédient

> GET /pizzeria/ingredients/{id}

Requête vers le serveur

~~~
GET /pizzeria/ingredients/1
{
    "id": 1,
    "name": "pomme de terre",
    "prix": 3
}
~~~

Codes de status HTTP
| Status | Description|
| --- | --- |
| 200 OK | La requête s'est effectuée correctement |
| 400 | mauvaise requête |
| 404 | ingrédient pas trouvé |

### Récupèrer le nom d'un ingrédient

> GET /pizzeria/ingredients/{id}/name

Requête vers le serveur

~~~
GET /pizzeria/ingredients/1/name
"pomme de terre"
~~~

Codes de status HTTP
| Status | Description|
| --- | --- |
| 200 OK | La requête s'est effectuée correctement |
| 400 | mauvaise requête |
| 404 | ingrédient pas trouvé |

### Enregistrer un nouvel ingrédient

> POST /pizzeria/ingredients

Requête vers le serveur

~~~
POST /pizzeria/ingredients
{
    "id": 10,
    "name": "Ail",
    "prix": 5
}
~~~

Réponse du serveur 
~~~
[
    {
        "id": 2,
        "name": "poivrons",
        "prix": 2
    },
    {
        "id": 3,
        "name": "chorizo",
        "prix": 4
    },
    {
        "id": 4,
        "name": "lardons",
        "prix": 4
    },
    ...
]
~~~
Codes de status HTTP
| Status | Description|
| --- | --- |
| 201 CREATED | L'ingrédient a été ajouté |
| 409 | l'identifiant existe déjà |
| 204 | pas de contenu au POST |
| 400 | mauvaise requête |
| 401 | pas authentifié |

### Supprimer un ingrédient

> DELETE /pizzeria/ingredients/{id}

Requête vers le serveur

~~~
DELETE /pizzeria/ingredients/1
~~~

Réponse du serveur 
Codes de status HTTP
| Status | Description|
| --- | --- |
| 404 | pas trouvé l'ingrédient |
| 400 | mauvaise requête |
| 401 | pas authentifié |

***

# API Pizzas

***
| URI | Opération | MIME | Requête |  Réponse |
| --- | --- | --- | --- | --- |
| /pizzas  | GET | <-application/json |  | liste des ingrédients (P1) |
| /pizzas/{id}  | GET | <-application/json |  | un ingrédient (P1) ou 404 ou 400  |
 | /pizzas  | POST | <-/->application/json->application/x-www-form-urlencoded| Pizza(P1) | la pizza postée ou 409 ou 400 ou 401 |
 | /pizzas/{id}  | DELETE | <-application/json |  | un ingrédient (P1) ou 404 ou 400 ou 401  |
 | /pizzas/{id}  | PATCH | <-/->application/json->application/x-www-form-urlencoded | Pizza(P1) | la pizza modifiée (P1) ou 404 ou 400 ou 401 |
  | /pizzas/{id}  | POST | <-/->application/json->application/x-www-form-urlencoded | Ingrédient(I1) | la pizza (P1) avec son nouvelle ingrédient  ou 409 ou 400 ou 401 |
  | /pizzas/{id}/{idIngredient}| DELETE | <-/->application/json->application/x-www-form-urlencoded | | la pizza (P1) concernée ou 404 ou 400 ou 401 |
  | /pizzas/{id}/prixfinal| GET | <-text/plain| |le prix final de la pizza concernée ou 404 ou 400 |
  
# Corps des requêtes
 ***
### P1
Une Pizza comporte uniquement un identifiant, un nom, un prix de base, une pâte et des ingrédients. Sa représentation JSON (I1) est la suivante :
~~~
{
 "id": 1,
 "name": "savoyarde",
 "prix": 10
 "pate": "extra",
 "ingredients":
 [
    {
        "id": 1,
        "name": "pomme de terre",
        "prix": 3
    },
    {
        "id": 4,
        "name": "lardons",
        "prix": 4
    }
 ]
}
~~~

# Exemples
 ***
### Lister toutes les pizzas connues dans la base de données


> GET /pizzeria/pizzas

Requête vers le serveur

~~~
GET /pizzeria/pizzas
[
    {
        "id": 1,
        "name": "savoyarde",
        "prix": 10
        "pate": "extra",
        "ingredients":
        [
            {
                "id": 1,
                "name": "pomme de terre",
                "prix": 3
            },
            {
                "id": 4,
                "name": "lardons",
                "prix": 4
            }
        ]
    },
    {
        "id": 2,
        "name": "chorizo",
        "prix": 7
        "pate": "fine",
        "ingredients":
        [
            {
                "id": 3,
                "name": "chorizo",
                "prix": 4
            }
        ]
    },
    ...
]
~~~

Codes de status HTTP
| Status | Description|
| --- | --- |
| 200 OK | La requête s'est effectuée correctement |
***

### Afficher les détails d'une pizza


> GET /pizzeria/pizzas/{id}

Requête vers le serveur

~~~
GET /pizzeria/pizzas/1
{
    "id": 1,
    "name": "savoyarde",
    "prix": 10
    "pate": "extra",
    "ingredients":
    [
        {
            "id": 1,
        "name": "pomme de terre",
            "prix": 3
        },
        {
            "id": 4,
            "name": "lardons",
            "prix": 4
        }
    ]
}
~~~

Codes de status HTTP
| Status | Description|
| --- | --- |
| 200 OK | La requête s'est effectuée correctement |
| 400 | la requête est mauvaise |
| 404 | une pizza ou un ingrédient n'a pas été pas trouvé |
***

### Enregister une nouvelle pizza


> POST /pizzeria/pizzas

Requête vers le serveur

~~~
POST /pizzeria/pizzas
{
    "id": 10,
    "name": "savoyarde",
    "prix": 10
    "pate": "extra",
    "ingredients":
    [
        {
            "id": 1,
        "name": "pomme de terre",
            "prix": 3
        },
        {
            "id": 4,
            "name": "lardons",
            "prix": 4
        }
    ]
}
~~~

Réponse du serveur :
~~~
{
    "id": 10,
    "name": "savoyarde",
    "prix": 10
    "pate": "extra",
    "ingredients":
    [
        {
            "id": 1,
        "name": "pomme de terre",
            "prix": 3
        },
        {
            "id": 4,
            "name": "lardons",
            "prix": 4
        }
    ]
}
~~~

Codes de status HTTP
| Status | Description|
| --- | --- |
| 201 CREATED | L'ingrédient a été ajouté |
| 400 | la requête est mauvaise |
| 401 | pas Authentifiée |
| 409 | une pizza porte déjà cet identifiant|
***

### Supprimer une pizza


> DELETE /pizzeria/pizzas/{id}

Requête vers le serveur

~~~
DELETE /pizzeria/pizzas/1
~~~

Réponse du serveur
~~~
[
    {
        "id": 2,
        "name": "chorizo",
        "prix": 7
        "pate": "fine",
        "ingredients":
        [
            {
                "id": 3,
                "name": "chorizo",
                "prix": 4
            }
        ]
    },
    ...
]
~~~

Codes de status HTTP
| Status | Description|
| --- | --- |
| 400 | la requête est mauvaise |
| 401 | pas Authentifiée |
| 404 | une pizza ou un ingrédient n'a pas été pas trouvé |
***

### Modifier une pizza


> PATCH /pizzeria/pizzas/{id}

Requête vers le serveur

~~~
PATCH /pizzeria/pizzas/1
{
    "id": 1,
    "name": "savoyarde",
    "prix": 5
    "pate": "fine",
    "ingredients":
    [
        {
            "id": 1,
            "name": "pomme de terre",
            "prix": 3
        },
        {
            "id": 4,
            "name": "lardons",
            "prix": 4
        }
    ]
}
~~~

Réponse du serveur
~~~
{
    "id": 1,
    "name": "savoyarde",
    "prix": 5
    "pate": "fine",
    "ingredients":
    [
        {
            "id": 1,
            "name": "pomme de terre",
            "prix": 3
        },
        {
            "id": 4,
            "name": "lardons",
            "prix": 4
        }
    ]
}
~~~

Codes de status HTTP
| Status | Description|
| --- | --- |
| 200 OK | La requête s'est effectuée correctement |
| 400 | la requête est mauvaise |
| 401 | pas Authentifiée |
| 404 | une pizza ou un ingrédient n'a pas été pas trouvé |
***

### Ajouter un ingrédient à une pizza


> POST /pizzeria/pizzas/{1}

Requête vers le serveur

~~~
POST /pizzeria/pizzas/{1}
{
    "id": 6,
    "name": "champignons",
    "prix": 2
}
~~~

Réponse du serveur
~~~
{
    "id": 1,
    "name": "savoyarde",
    "prix": 5
    "pate": "fine",
    "ingredients":
    [
        {
            "id": 1,
            "name": "pomme de terre",
            "prix": 3
        },
        {
            "id": 4,
            "name": "lardons",
            "prix": 4
        },
        {
            "id": 6,
            "name": "champignons",
            "prix": 2
        }
    ]
}
~~~

Codes de status HTTP
| Status | Description|
| --- | --- |
| 201 CREATED | L'ingrédient a été ajouté |
| 400 | la requête est mauvaise |
| 401 | pas Authentifiée |
| 404 | une pizza ou un ingrédient n'a pas été pas trouvé |
| 409 | cet ingrédient existe déjà dans la pizza |
***

### Supprimer un ingrédient à une pizza


> DELETE /pizzeria/pizzas/{id}/{idIngredient}

Requête vers le serveur

~~~
DELETE /pizzeria/pizzas/1/6
~~~

Réponse du serveur
~~~
{
    "id": 1,
    "name": "savoyarde",
    "prix": 5
    "pate": "fine",
    "ingredients":
    [
        {
            "id": 1,
            "name": "pomme de terre",
            "prix": 3
        },
        {
            "id": 4,
            "name": "lardons",
            "prix": 4
        },
    ]
}
~~~

Codes de status HTTP
| Status | Description|
| --- | --- |
| 200 OK | La requête est correcte |
| 400 | la requête est mauvaise |
| 401 | pas Authentifiée |
| 404 | une pizza ou un ingrédient n'a pas été pas trouvé |
***

### Avoir le prix final d'une pizza

C'est-à-dire le prix de tous les ingrédients additionné au prix de la pizza.

> GET /pizzeria/pizzas/{id}/prixfinal

Requête vers le serveur

~~~
GET /pizzeria/pizzas/{1}/prixfinal
12
~~~

Codes de status HTTP
| Status | Description|
| --- | --- |
| 200 OK | La requête est correcte |
| 400 | la requête est mauvaise |
| 404 | une pizza ou un ingrédient n'a pas été pas trouvé |
***

# API Commande

***

| URI | Opération | MIME | Requête |  Réponse |
| --- | --- | --- | --- | --- |
|  /commandes | GET | <-application/json |  | List des commandes (C1) |
|  /commandes/{id} | GET | <-application/json |  | détails d'une commande ou 404 ou 400 |
|  /commandes | POST | <-/->application/json->application/x-www-form-urlencoded | Commande(C1) | la commande postée (C1) ou 400 ou 409 ou 401 |
|  /commandess/{id}/prixfinal  | GET | <-text/plain |  | prix finale de la commande ou 400 ou 404 |

# Corps des requêtes
 ***
### C1
Une Pizza comporte uniquement un identifiant, un nom, un prix de base, une pâte et des ingrédients. Sa représentation JSON (I1) est la suivante :
~~~
{
    "id": 1,
    "nom":"commande1",
    "dateCommande":"2023-08-15",
    "pizzas":
    [
        {
            "id": 1,
            "name": "savoyarde",
            "prix": 10
            "pate": "extra",
            "ingredients":
            [
                {
                    "id": 1,
                    "name": "pomme de terre",
                    "prix": 3
                },
                {
                    "id": 4,
                    "name": "lardons",
                    "prix": 4
                }
            ]
        },
        {
            "id": 2,
            "name": "chozizo",
            "prix": 7
            "pate": "fine",
            "ingredients":
            [
                {
                    "id": 3,
                    "name": "chorizo",
                    "prix": 4
                }
            ]
        }
    ]
}
~~~

# Exemples
 ***
### Lister toutes les commandes connues dans la base de données


> GET /pizzeria/commandes

Requête vers le serveur

~~~
GET /pizzeria/commandes
[
    {
        "id": 1,
        "nom":"commande1",
        "dateCommande":"2023-08-15",
        "pizzas":
        [
            {
                "id": 1,
                "name": "savoyarde",
                "prix": 10
                "pate": "extra",
                "ingredients":
                [
                    {
                        "id": 1,
                        "name": "pomme de terre",
                        "prix": 3
                    },
                    {
                        "id": 4,
                        "name": "lardons",
                        "prix": 4
                    }
                ]
            },
            {
                "id": 2,
                "name": "chozizo",
                "prix": 7
                "pate": "fine",
                "ingredients":
                [
                    {
                        "id": 3,
                        "name": "chorizo",
                        "prix": 4
                    }
                ]
            }
        ]
    },
    {
        "id": 2,
        "nom":"commande2",
        "dateCommande":"2023-12-24",
        "pizzas":
        [
            {
                "id": 2,
                "name": "chozizo",
                "prix": 7
                "pate": "fine",
                "ingredients":
                [
                    {
                        "id": 3,
                        "name": "chorizo",
                        "prix": 4
                    }
                ]
            }
        ]
    },
    ...
]
~~~

Codes de status HTTP
| Status | Description|
| --- | --- |
| 200 OK | La requête s'est effectuée correctement |
***

### Afficher une commande spécifique avec son identifiant


> GET /pizzeria/commandes/{id}

Requête vers le serveur

~~~
GET /pizzeria/commandes/1
{
    "id": 1,
    "nom":"commande1",
    "dateCommande":"2023-08-15",
    "pizzas":
    [
        {
            "id": 1,
            "name": "savoyarde",
            "prix": 10
            "pate": "extra",
            "ingredients":
            [
                {
                    "id": 1,
                    "name": "pomme de terre",
                    "prix": 3
                },
                {
                    "id": 4,
                    "name": "lardons",
                    "prix": 4
                }
            ]
        },
        {
            "id": 2,
            "name": "chorizo",
            "prix": 7
            "pate": "fine",
            "ingredients":
            [
                {
                    "id": 3,
                    "name": "chorizo",
                    "prix": 4
                }
            ]
        }
    ]
}
~~~

Codes de status HTTP
| Status | Description|
| --- | --- |
| 200 OK | La requête s'est effectuée correctement |
| 400 | mauvaise requête |
| 404 | la commande n'a pas été trouvée |
***

### Créer une nouvelle commande

> POST /pizzeria/commandes

Requête vers le serveur

~~~
POST /pizzeria/commandes
{
    "id": 5,
    "nom":"commande5",
    "dateCommande":"2023-08-15",
    "pizzas":
    [
        {
            "id": 3,
            "name": "canibale",
            "prix": 8
            "pate": "normal",
            "ingredients":
            [
                {
                    "id": 3,
                    "name": "chorizo",
                    "prix": 4
                },
                {
                    "id": 4,
                    "name": "lardons",
                    "prix": 4
                }
            ]
        },
        {
            "id": 2,
            "name": "chorizo",
            "prix": 7
            "pate": "fine",
            "ingredients":
            [
                {
                    "id": 3,
                    "name": "chorizo",
                    "prix": 4
                }
            ]
        }
    ]
}
~~~

Codes de status HTTP
| Status | Description|
| --- | --- |
| 201 CREATED | L'ingrédient a été ajouté |
| 400 | mauvaise requête |
| 409 | L'identifiant de la commande est déjà utilisé |
| 404 | la commande n'a pas été trouvée |
***

### Afficher une commande spécifique avec son identifiant


> GET /pizzeria/commandes/{id}/

Requête vers le serveur

~~~
GET /pizzeria/commandes/1
28 
~~~
Codes de status HTTP
| Status | Description|
| --- | --- |
| 200 OK | La requête s'est effectuée correctement |
| 400 | mauvaise requête |
| 404 | la commande n'a pas été trouvée |