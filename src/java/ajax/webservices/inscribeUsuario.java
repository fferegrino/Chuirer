/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ajax.webservices;

import dataAccess.DaUsuarios;
import entidadesDeNegocio.EnUsuario;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

/**
 *
 * @author Antonio
 */
public class inscribeUsuario extends HttpServlet {

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
        String usuario = request.getParameter("u");
        String apellido = request.getParameter("a");
        String nombreReal = request.getParameter("r");
        String email = request.getParameter("e");
        JSONObject respuesta = new JSONObject();

        if (usuario != null && apellido != null && nombreReal != null && email != null) {
            if (usuario.isEmpty() || apellido.isEmpty() || nombreReal.isEmpty() || email.isEmpty()) {
                respuesta.put("success", false);
                respuesta.put("error", "Campos vacíos");
            } else {


                EnUsuario ENU = new EnUsuario();
                ENU.setEmail(email);
                ENU.setUserName(usuario);
                ENU.setRol("com");
                ENU.setApellidos(apellido);
                ENU.setNombreReal(nombreReal);
                ENU.setDscripcion("Lorem Ipsum is industry, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
                ENU.setPassword("holamundo");
                if ((new DaUsuarios()).guardaUsuario(ENU)) {
                    respuesta.put("success", true);
                    
                } else {
                    respuesta.put("success", false);
                    respuesta.put("error", "Error interno");
                }
            }
        } else {
            respuesta.put("success", false);
            respuesta.put("error", "Campos nulos" + usuario +", "+apellido + ", "+ nombreReal + ","+ email);
        }
        respuesta.put("username", usuario);

        PrintWriter out = response.getWriter();
        out.print(respuesta.toString());
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
