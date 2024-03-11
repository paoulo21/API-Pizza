package authentification;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Base64;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DS;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/etudiants2/*")
public class ControleurEtudiant2 extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
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
                if()
            }
            
            res.setContentType("application/json;charset=UTF-8");
            
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonstring = objectMapper.writeValueAsString(login+","+pwd);
            out.print(jsonstring);
            out.close();
        }catch(Exception e){
            out.println(e.getMessage());
        }finally {
            try {con.close();} catch(Exception e2) {}
        }
    }
    public static void main(String[] args) {
        
    }
}
