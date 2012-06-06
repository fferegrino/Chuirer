package ajax.webservices;

import dataAccess.DaSeguidores;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class obtenerSeguidores extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username"); // Obtenemos el nombre de usuario

        DaSeguidores seguidores = new DaSeguidores();

        JSONObject objeto = new JSONObject();
        JSONArray arraySeguidores = new JSONArray();
        
        ArrayList<String> Seguidores = seguidores.obtenerSeguidores(username);

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        for (int i = 0; i < Seguidores.size(); i++)
        {
            JSONObject seguidor = new JSONObject();
            seguidor.put("seguido", Seguidores.get(i));
            arraySeguidores.add(seguidor);
        }
        
        objeto.put("total", Seguidores.size());
        objeto.put("seguidores", arraySeguidores);
        
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
