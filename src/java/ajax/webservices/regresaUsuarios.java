package ajax.webservices;

import chuirer.utilitarios.Funciones;
import dataAccess.DaUsuarios;
import entidadesDeNegocio.EnUsuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Antonio
 */
public class regresaUsuarios extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        String user = request.getParameter("u");
        String unico = request.getParameter("unico");


        JSONArray arreglo = new JSONArray();
        if (unico == null) {
            ArrayList<EnUsuario> users;
            users = (new DaUsuarios()).regresaUsuarios(user);
            for (EnUsuario u : users) {
                JSONObject onjeto = new JSONObject();
                onjeto.put("username", u.getUserName());
                onjeto.put("realName", u.getNombreReal());
                onjeto.put("lastName", u.getApellidos());
                onjeto.put("email", u.getEmail());
                onjeto.put("description", u.getDscripcion());
                onjeto.put("registerDate", Funciones.Date2ShortDateString(u.getFechaRegistro()));
                arreglo.add(onjeto);
            }
        }
        else{
            EnUsuario u = (new DaUsuarios()).recuperaUsuario(user);
            if(u != null){
            JSONObject onjeto = new JSONObject();
                onjeto.put("username", u.getUserName());
                onjeto.put("realName", u.getNombreReal());
                onjeto.put("lastName", u.getApellidos());
                onjeto.put("email", u.getEmail());
                onjeto.put("description", u.getDscripcion());
                onjeto.put("registerDate", Funciones.Date2ShortDateString(u.getFechaRegistro()));
                arreglo.add(onjeto);
            }
        }
        out.print(arreglo);


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
