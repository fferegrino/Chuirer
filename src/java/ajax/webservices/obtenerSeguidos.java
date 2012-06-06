package ajax.webservices;

import dataAccess.DaSeguidos;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class obtenerSeguidos extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username"); // Obtenemos el nombre de usuario
        String usuarioActual = (String) request.getSession().getAttribute("usuarioLogueado");
        DaSeguidos seguidos = new DaSeguidos();

        JSONObject objeto = new JSONObject();
        JSONArray arraySeguidos = new JSONArray();

        ArrayList<String> Seguidos = seguidos.obtenerSeguidos(username);

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        for (int i = 0; i < Seguidos.size(); i++) {
            JSONObject seguido = new JSONObject();
            seguido.put("seguido", Seguidos.get(i));
            if (usuarioActual.equals(username)) {
                seguido.put("s", true);
            }
            arraySeguidos.add(seguido);
        }

        objeto.put("total", Seguidos.size());
        objeto.put("seguidos", arraySeguidos);

        out.print(objeto.toJSONString());
        out.close();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
