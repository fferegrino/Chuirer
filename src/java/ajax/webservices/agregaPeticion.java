/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ajax.webservices;

import chuirer.utilitarios.Correo;
import chuirer.utilitarios.Funciones;
import dataAccess.DaPeticiones;
import dataAccess.DaUsuarios;
import entidadesDeNegocio.EnPeticion;
import entidadesDeNegocio.EnUsuario;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

/**
 *
 * @author fferegrino
 */
@WebServlet(name = "agregaPeticion", urlPatterns = {"/agregaPeticion"})
public class agregaPeticion extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject respuesta = new JSONObject();
        String usuarioPet = request.getParameter("u");     //  Usuario al cual se desea agregar
        //String usuarioQuePide = request.getParameter("uO"); //  Usuario que agrega
        String usuarioQuePide = (String) request.getSession().getAttribute("usuarioLogueado");
        Correo c = new Correo();

        if (usuarioQuePide != null) {
            if (new DaPeticiones().agregaPeticion(new EnPeticion(usuarioPet, usuarioQuePide, false))) {
                respuesta.put("success", true);
                EnUsuario enUsuario = new DaUsuarios().recuperaUsuario(usuarioPet);
                c.enviar(enUsuario.getEmail(), "Petición de usuario", "¡Hey! el "
                        + "usuario " + usuarioQuePide
                        + " te ha enviado una petición para seguirte,"
                        + " logueate a Chuirer para responderle");
            } else {
                respuesta.put("success", false);
            }
        } else {
            respuesta.put("success", false);
        }
        out.println(respuesta.toJSONString());
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
