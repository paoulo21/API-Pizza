package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dto.Commande;

public class CommandeDAO {
    DS ds;
    Connection con;

    public CommandeDAO() {
        ds = new DS();
        con = ds.getConnection();
    }

    public List<Commande> findAll(){
        con = ds.getConnection();
        try{
            

        }catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (Exception e2) {
            }
        }
    }
}
