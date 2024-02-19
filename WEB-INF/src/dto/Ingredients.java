package dto;

public class Ingredients {
    private int id;
    private String nom;
    private int prix;

    public Ingredients(String nom, int prix, int id) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
    }

    public Ingredients(String nom, int id) {
        this.id = id;
        this.nom = nom;
    }

    public Ingredients() {
    }

    public String getNom() {
        return nom;
    }

    public int getPrix() {
        return prix;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrice(int price) {
        this.prix = price;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return getNom() + " " + getPrix() + " " + getId();
    }
    public static void main(String[] args) {
        
    }
}