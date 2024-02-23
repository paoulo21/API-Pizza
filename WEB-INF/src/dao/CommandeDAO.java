package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.Commande;
import dto.Ingredients;
import dto.Pizzas;

public class CommandeDAO {
    DS ds;
    Connection con;

    public CommandeDAO() {
        ds = new DS();
        con = ds.getConnection();
    }

    public List<Commande> findAll(){
        List<Commande> liste = new ArrayList<>();
        con = ds.getConnection();
        try{
            PreparedStatement ps = con.prepareStatement("SELECT * FROM commande");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Commande commande = new Commande(rs.getInt("id"), rs.getString("nom"), rs.getDate("dateCommande"));
                commande.setPizzas(getPizzasFromCommande(commande.getId()));
                liste.add(commande);
            }
        }catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (Exception e2) {
            }
        }
        return liste;
    }

    private List<Pizzas> getPizzasFromCommande(int id) throws SQLException {
        List<Pizzas> commande = new ArrayList<Pizzas>();
        PreparedStatement ps2 = con
                .prepareStatement(
                        "SELECT id,nom,prixBase,pate FROM pizzasCommande,pizzas where idCom = ? and idPiz = id");
        ps2.setInt(1, id);
        ResultSet rs2 = ps2.executeQuery();
        while (rs2.next()) {
            Pizzas pizza = new Pizzas(rs2.getInt("id"), rs2.getString("nom"), rs2.getInt("prixBase"), rs2.getString("pate") );
            pizza.setIngredients(getIngredientsFromPizza(pizza.getId()));
            commande.add(pizza);
        }
        return commande;
    }

    private List<Ingredients> getIngredientsFromPizza(int id) throws SQLException {
        List<Ingredients> ingredients = new ArrayList<Ingredients>();
        PreparedStatement ps2 = con
                .prepareStatement(
                        "SELECT id,nom,prix FROM ingredientsPizza,ingredients where idPiz = ? and idIng = id");
        ps2.setInt(1, id);
        ResultSet rs2 = ps2.executeQuery();
        while (rs2.next()) {
            Ingredients pizza = new Ingredients(rs2.getString("nom"), rs2.getInt("prix"), rs2.getInt("id"));
            ingredients.add(pizza);
        }
        return ingredients;
    }

    public Commande findById(int id){
        con = ds.getConnection();
        Commande commande = null;
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM commande where id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                commande = new Commande(rs.getInt("id"), rs.getString("nom"), rs.getDate("dateCommande"));
                commande.setPizzas(getPizzasFromCommande(commande.getId()));
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
        return commande;
    }

    public boolean save(Commande c){
        con = ds.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("insert into commande (id, nom, dateCommande) values (?,?,?)");
            ps.setInt(1, c.getId());
            ps.setString(2, c.getNom());
            ps.setDate(3, new Date(c.getDate().getTime()));
            int res = ps.executeUpdate();
            if (res == 1) {
                for (Pizzas pizza : c.getPizzas()) {
                    PreparedStatement ps2 = con
                            .prepareStatement("insert into pizzasCommande (idCom,idPiz) values (?,?)");
                    ps2.setInt(1, c.getId()); 
                    ps2.setInt(2, pizza.getId());
                    ps2.executeUpdate();
                    int res2 = ps2.executeUpdate();
                    if(res2 == 1){
                        for (Ingredients ingredient : pizza.getIngredients()) {
                            PreparedStatement ps3 = con
                                    .prepareStatement("insert into ingredientsPizza (idPiz,idIng) values (?,?)");
                            ps3.setInt(1, pizza.getId());
                            ps3.setInt(2, ingredient.getId());
                            ps3.executeUpdate();
                        }
                        con.close();
                        return true;
                    }
                }
            }

        }catch (SQLException e) {
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
}
