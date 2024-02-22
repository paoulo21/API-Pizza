package controleurs;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.IngredientDAO;
import dto.Ingredients;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/ingredients/*")
public class IngredientRestAPI extends HttpServlet {
    IngredientDAO dao = new IngredientDAO();

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, java.io.IOException {
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        String info = req.getPathInfo();
        ObjectMapper objectMapper = new ObjectMapper();
        if (info == null || info.equals("/")) {
            List<Ingredients> l = dao.findAll();
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
        Ingredients e;
        try {
            e = dao.findById(Integer.parseInt(id));
        } catch (Exception ex) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (e == null) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        if (splits.length == 2) {
            out.print(objectMapper.writeValueAsString(e));
        } else if (splits.length == 3 && splits[2].equals("name")) {
            out.print(objectMapper.writeValueAsString(e.getNom()));
        } else {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        return;
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, java.io.IOException {
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        String info = req.getPathInfo();
        if (info == null || info.equals("/")) {
            ObjectMapper objectMapper = new ObjectMapper();
            Ingredients e;
            try {
                e = objectMapper.readValue(req.getReader(), Ingredients.class);
            } catch (Exception ex) {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            if (!dao.save(e)) {
                res.sendError(HttpServletResponse.SC_CONFLICT);
                return;
            }
            out.print(objectMapper.writeValueAsString(e));
        } else {
            res.sendError(HttpServletResponse.SC_NO_CONTENT);
            return;
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
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
            if (!dao.delete(id)) {
                res.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            List<Ingredients> l = dao.findAll();
            String jsonstring = objectMapper.writeValueAsString(l);
            out.print(jsonstring);
            return;
        } else {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        if (!method.equals("PATCH")) {
            super.service(req, resp);
        } else {
            this.doPatch(req, resp);
        }
    }

    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        String info = req.getPathInfo();
        ObjectMapper objectMapper = new ObjectMapper();
        int prix;
        try {
            prix = objectMapper.readValue(req.getReader(), Integer.class);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String[] splits = info.split("/");
        if (splits.length == 2) {
            int id;
            try {
                id = Integer.parseInt(splits[1]);
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            if (!dao.changePrice(id, prix)) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
    }

}
