package dto;

public class Ingredients {
    private int id;
    private String name;
    private int price;

    public Ingredients(String name, int price, int id) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Ingredients(String name, int id) {
        this.id = id;
        this.name = name;
    }

    public Ingredients() {
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return getName() + " " + getPrice() + " " + getId();
    }
}