package controleurs;

import java.io.PrintWriter;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

import dao.database;
import dao.IngredientDAOList;
import dto.Ingredients;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/ingredients/*")
public class IngredientRestAPI extends HttpServlet {
    database dao = new database();

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
        if (splits.length != 2) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String id = splits[1];
        Ingredients e = dao.findById(Integer.parseInt(id));
        if (e == null) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        out.print(objectMapper.writeValueAsString(e));
        return;
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, java.io.IOException {
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        Ingredients e = objectMapper.readValue(req.getReader(), Ingredients.class);
        dao.save(e);
        out.print(objectMapper.writeValueAsString(e));

    }
}
