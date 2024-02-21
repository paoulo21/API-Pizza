package dao;

import dto.Ingredients;
import java.util.ArrayList;
import java.util.List;

public class IngredientDAOList implements DAOIngredient {
    List<Ingredients> ingredients = new ArrayList<Ingredients>();

    public IngredientDAOList() {
        ingredients.add(new Ingredients("Tomate", 1, 0));
        ingredients.add(new Ingredients("Oignon", 2, 1));
        ingredients.add(new Ingredients("Cheddar", 3, 2));
        ingredients.add(new Ingredients("Mozzarella", 4, 3));
        ingredients.add(new Ingredients("Jambon", 5, 4));
        ingredients.add(new Ingredients("Champignon", 6, 5));
        ingredients.add(new Ingredients("Ananas", 7, 6));
        ingredients.add(new Ingredients("Poulet", 8, 7));
        ingredients.add(new Ingredients("Boeuf", 9, 8));
        ingredients.add(new Ingredients("Saumon", 10, 9));
    }

    public List<Ingredients> findAll() {
        return ingredients;
    }

    public Ingredients findById(int id) {
        try {
            return ingredients.get(id);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public boolean save(Ingredients ingredient) {
        ingredients.add(ingredient);
        return true;
    }
}
