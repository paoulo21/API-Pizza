package dao;

import dto.Ingredients;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class database implements DAOIngredient {

    DS ds;
    Connection con;

    public database() {
        ds = new DS();
        con = ds.getConnection();
    }

    public List<Ingredients> findAll() {
        List<Ingredients> ingredients = new ArrayList<Ingredients>();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM ingredients");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Ingredients ingredient = new Ingredients(rs.getString("nom"), rs.getInt("prix"), rs.getInt("id"));
                ingredients.add(ingredient);
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
        return ingredients;
    }

    public Ingredients findById(int id) {
        Ingredients ingredient = new Ingredients();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM ingredients where id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ingredient = new Ingredients(rs.getString("nom"), rs.getInt("prix"), rs.getInt("id"));
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
        return ingredient;
    }

    public void save(Ingredients ingredient) {
        try {
            PreparedStatement ps = con.prepareStatement("insert into ingredients (nom,id,prix) values (?,?,?)");
            ps.setString(1, ingredient.getNom());
            ps.setInt(2, ingredient.getId());
            ps.setInt(3, ingredient.getPrix());
            ps.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (Exception e2) {
            }
        }
    }

    public static void main(String[] args) {

    }
}