package controleurs;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.PizzaDAO;
import dto.Ingredients;
import dto.Pizzas;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/pizzas/*")
public class PizzaRestApi extends HttpServlet {
    PizzaDAO dao = new PizzaDAO();

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if(!AuthenUtil.verifyToken(req)){
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        if (req.getMethod().equalsIgnoreCase("PATCH")) {
            doPatch(req, res);
        } else {
            super.service(req, res);
        }
    }

    public void doPatch(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        String info = req.getPathInfo();
        ObjectMapper objectMapper = new ObjectMapper();
        String[] splits = info.split("/");
        if (splits.length == 2) {
            int id;
            try {
                id = Integer.parseInt(splits[1]);
            } catch (NumberFormatException e) {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            int prix;
            try {
                prix = objectMapper.readValue(req.getReader(), Integer.class);
            } catch (Exception e) {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            if (!dao.patch(prix, id)) {
                res.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            Pizzas p = dao.findById(id);
            out.print(objectMapper.writeValueAsString(p));
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, java.io.IOException {
                res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        String info = req.getPathInfo();
        ObjectMapper objectMapper = new ObjectMapper();
        if (info == null || info.equals("/")) {
            List<Pizzas> l = dao.findAll();
            String jsonstring = objectMapper.writeValueAsString(l);
            out.print(jsonstring);
            return;
        }
        String[] splits = info.split("/");
        if (splits.length > 3) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String id = splits[1];
        Pizzas e;
        try {
            e = dao.findById(Integer.parseInt(id));
        } catch (NumberFormatException ex) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (e == null) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        if (splits.length == 2) {
            out.print(objectMapper.writeValueAsString(e));
        } else if (splits.length == 3 && splits[2].equals("prixfinal")) {
            out.print(e.getPrixTotal());
        } else {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        return;
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, java.io.IOException {
        if(!AuthenUtil.verifyToken(req)){
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        String info = req.getPathInfo();
        String[] splits = info.split("/");
        if (info == null || info.equals("/")) {
            ObjectMapper objectMapper = new ObjectMapper();
            Pizzas e;
            try {
                e = objectMapper.readValue(req.getReader(), Pizzas.class);
            } catch (Exception ex) {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            if (!dao.save(e)) {
                res.sendError(HttpServletResponse.SC_CONFLICT);
                return;
            }
            out.print(objectMapper.writeValueAsString(e));
        } else if (splits.length == 2) {
            int id;
            try {
                id = Integer.parseInt(splits[1]);
            } catch (NumberFormatException e) {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            Ingredients e;
            try {
                e = objectMapper.readValue(req.getReader(), Ingredients.class);
            } catch (Exception ex) {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            if (!dao.addIngredientsToPizza(id, e.getId())) {
                res.sendError(HttpServletResponse.SC_CONFLICT);
                return;
            }
            Pizzas p = dao.findById(id);
            out.print(objectMapper.writeValueAsString(p));
        } else {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if(!AuthenUtil.verifyToken(req)){
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
            List<Pizzas> l = dao.findAll();
            String jsonstring = objectMapper.writeValueAsString(l);
            out.print(jsonstring);
        } else if (splits.length == 3) {
            int idIngredient;
            try {
                idIngredient = Integer.parseInt(splits[2]);
            } catch (NumberFormatException e) {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            if (dao.deleteIngredientsFromPizza(id, idIngredient)) {
                Pizzas e = dao.findById(id);
                out.print(objectMapper.writeValueAsString(e));
            } else {
                res.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

        }

    }
}
