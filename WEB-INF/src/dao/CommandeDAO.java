package dao;

import java.sql.Connection;
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
            commande.add(pizza);
        }
        return commande;
    }
}
