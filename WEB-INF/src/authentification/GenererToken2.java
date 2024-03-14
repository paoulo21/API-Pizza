package authentification;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

import com.fasterxml.jackson.databind.JsonSerializable.Base;

import dao.DS;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/users/token")
public class GenererToken2 extends HttpServlet {
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
        PrintWriter out = res.getWriter();
        Connection con=null;
        try{
            DS ds = new DS();
            con = ds.getConnection();
            String login = req.getParameter("login");
            String password = req.getParameter("mdp");
            String querry = "SELECT * FROM users WHERE login=? AND pwd=?";
            PreparedStatement ps = con.prepareStatement(querry);
            ps.setString(1, login);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String token = generateRandomToken(login, password);
                out.print("<p>votre token ici: "+token.toString()+"</p>");
            } else {
                out.print("<p>Vous êtes inconnu ici !</p>");
            }
        }catch(Exception e){
            out.println(e.getMessage());
        }finally {
            try {con.close();} catch(Exception e2) {}
        }
    }

    public static String generateRandomToken(String login, String password) {
        String total = login+","+password;
        byte[] encodedBytes = Base64.getEncoder().encode(total.getBytes());

        return new String(encodedBytes);
    }
}