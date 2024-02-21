package dao;

import dto.Ingredients;
import dto.Pizzas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PizzaDAO {

    DS ds;
    Connection con;

    public PizzaDAO() {
        ds = new DS();
        con = ds.getConnection();
    }

    public List<Pizzas> findAll() {
        con = ds.getConnection();
        List<Pizzas> pizzas = new ArrayList<Pizzas>();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM pizzas");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Pizzas pizza = new Pizzas(rs.getInt("id"), rs.getString("nom"), rs.getInt("prixBase"),
                        rs.getString("pate"));
                pizza.setIngredients(getIngredientFromPizza(pizza.getId()));
                pizzas.add(pizza);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (Exception e2) {
            }
        }
        return pizzas;
    }

    private List<Ingredients> getIngredientFromPizza(int id) throws SQLException {
        List<Ingredients> ingredients = new ArrayList<Ingredients>();
        PreparedStatement ps2 = con.prepareStatement("SELECT * FROM ingredientsPizza where idPiz = ?");
        ps2.setInt(1, id);
        ResultSet rs2 = ps2.executeQuery();
        while (rs2.next()) {
            Ingredients ingredient = new Ingredients(rs2.getString("nom"), rs2.getInt("prix"), rs2.getInt("id"));
            ingredients.add(ingredient);
        }
        return ingredients;
    }

    public Pizzas findById(int id) {
        con = ds.getConnection();
        Pizzas pizza = new Pizzas();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM pizzas where id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                pizza = new Pizzas(rs.getInt("id"), rs.getString("nom"), rs.getInt("prixBase"),
                        rs.getString("pate"));
                pizza.setIngredients(getIngredientFromPizza(pizza.getId()));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (Exception e2) {
            }

        }
        return pizza;
    }

    public boolean save(Ingredients ingredient) {
        con = ds.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("insert into ingredients (nom,id,prix) values (?,?,?)");
            ps.setString(1, ingredient.getNom());
            ps.setInt(2, ingredient.getId());
            ps.setInt(3, ingredient.getPrix());
            int res = ps.executeUpdate();
            if (res == 1) {
                con.close();
                return true;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (Exception e2) {
            }
        }
        return false;
    }

    public boolean delete(int id) {
        con = ds.getConnection();
        try {

            PreparedStatement ps = con.prepareStatement("DELETE FROM ingredients where id = ?");
            ps.setInt(1, id);
            int result = ps.executeUpdate();
            if (result == 1) {
                con.close();
                return true;
            } else {
                con.close();
                return false;
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (Exception e2) {
            }

        }
        return false;
    }

    public static void main(String[] args) {

    }
}
