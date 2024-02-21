package dto;

import java.util.ArrayList;
import java.util.List;

public class Pizzas {
    private int id;
    private String nom;
    private int prixBase;
    private String pate;
    private List<Ingredients> ingredients;

    public Pizzas(int id, String nom, int prixBase, String pate) {
        this.id = id;
        this.nom = nom;
        this.prixBase = prixBase;
        this.pate = pate;
        ingredients = new ArrayList<>();
    }

    public Pizzas() {
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public int getPrixBase() {
        return prixBase;
    }

    public String getPate() {
        return pate;
    }

    public int getPrixTotal(){
        int prixfinal = 0;
            for (Ingredients ing : ingredients) {
                prixfinal += ing.getPrix();
            }
            prixfinal += prixBase;
        return prixfinal;
    }
    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrixBase(int prixBase) {
        this.prixBase = prixBase;
    }

    public void setPate(String pate) {
        this.pate = pate;
    }

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public void add(Ingredients ingredient) {
        ingredients.add(ingredient);
    }
}