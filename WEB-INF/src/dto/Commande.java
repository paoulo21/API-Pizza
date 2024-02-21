package dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Commande {
    private int id;
    private String nom;
    private Date date;
    private List<Pizzas> pizzas;

    public Commande(int id, String nom, Date date, List<Pizzas> pizzas) {
        this.id = id;
        this.nom = nom;
        this.date = date;
        this.pizzas = pizzas;
    }

    public Commande(int id, String nom, Date date) {
        this(id,nom,date, new ArrayList<>());
    }


    public int getPrixTotal(){
        int prixTotal = 0;
        for (Pizzas p : pizzas) {
            prixTotal += p.getPrixTotal();
        }
        return prixTotal;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public Date getDate() {
        return date;
    }

    public List<Pizzas> getPizzas() {
        return pizzas;
    }
}
