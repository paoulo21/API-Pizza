package dao;

import java.util.List;

import dto.Ingredients;

public interface DAOIngredient {
    public List<Ingredients> findAll();

    public Ingredients findById(int id);

    public boolean save(Ingredients ingredient);
}
