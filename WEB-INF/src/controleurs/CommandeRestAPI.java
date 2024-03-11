package controleurs;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import authentification.Controleur;
import dao.CommandeDAO;
import dto.Commande;
import dto.Pizzas;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/commandes/*")
public class CommandeRestAPI extends HttpServlet {
    CommandeDAO dao = new CommandeDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if(!Controleur.existInUsers(req, res)){
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        String info = req.getPathInfo();
        String[] splits = info.split("/");
        ObjectMapper objectMapper = new ObjectMapper();
        if (info == null || info.equals("/")) {
            List<Commande> l = dao.findAll();
            String jsonstring = objectMapper.writeValueAsString(l);
            out.print(jsonstring);
            return;
        }

        if (splits.length == 2) {
            int id = Integer.parseInt(splits[1]);
            Commande c = dao.findById(id);
            if (c == null) {
                res.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            out.print(objectMapper.writeValueAsString(c));
        }

        if (splits.length == 3 && splits[2].equals("prixfinal")) {
            int id = Integer.parseInt(splits[1]);
            Commande c = dao.findById(id);
            if (c == null) {
                res.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            out.print(objectMapper.writeValueAsString(c.getPrixTotal()));
        }

    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, java.io.IOException {
            if(!Controleur.existInUsers(req, res)){
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        String info = req.getPathInfo();
        String[] splits = info.split("/");
        ObjectMapper objectMapper = new ObjectMapper();
        if (info == null || info.equals("/")) {
            Commande c = objectMapper.readValue(req.getReader(), Commande.class);
            if (!dao.save(c)) {
                res.sendError(HttpServletResponse.SC_CONFLICT);
                return;
            }
            out.print(objectMapper.writeValueAsString(c));
        } else if (splits.length == 2) {
            Pizzas p = objectMapper.readValue(req.getReader(), Pizzas.class);
            int idCom = Integer.parseInt(splits[1]);
            if (!dao.addPizzaToCommande(idCom, p.getId())) {
                res.sendError(HttpServletResponse.SC_CONFLICT);
                return;
            }
        }
    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if(!Controleur.existInUsers(req, res)){
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        String info = req.getPathInfo();
        ObjectMapper objectMapper = new ObjectMapper();
        String[] splits = info.split("/");
        int id;
        try {
            id = Integer.parseInt(splits[1]);
        } catch (NumberFormatException e) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (splits.length == 2) {
            if (!dao.delete(id)) {
                res.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            List<Commande> l = dao.findAll();
            String jsonstring = objectMapper.writeValueAsString(l);
            out.print(jsonstring);
        } else if (splits.length == 3) {
            int idPizza;
            try {
                idPizza = Integer.parseInt(splits[2]);
            } catch (NumberFormatException e) {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            if (dao.deletePizzaFromCommande(id, idPizza)) {
                Commande e = dao.findById(id);
                out.print(objectMapper.writeValueAsString(e));
            } else {
                res.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

        }

    }
}