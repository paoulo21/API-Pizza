package authentification;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DS;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Controleur {
    public static boolean existInUsers(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Connection con=null;
        try{
            DS ds = new DS();
            con = ds.getConnection();
            PrintWriter out = res.getWriter();
            String login ="";
            String pwd = "";
            String authorization = req.getHeader("Authorization");
            if (authorization != null || authorization.startsWith("Basic")){
                String token = authorization.substring("Basic".length()).trim();
                byte[] base64 = Base64.getDecoder().decode(token);
                String[] lm = (new String(base64)).split(",");
                
                login = lm[0];
                pwd = lm[1];

                String querry = "SELECT * FROM users WHERE login=? AND pwd=?";
                PreparedStatement ps = con.prepareStatement(querry);
                ps.setString(1, login);
                ps.setString(2, pwd);
                ResultSet rs = ps.executeQuery();

                if(!rs.next()){
                    res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    return false;
                }
            }
            return true;
        }catch(Exception e){
            return false;
        }finally {
            try {con.close();} catch(Exception e2) {}
        }
    }
}
