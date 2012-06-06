/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ajax.webservices;

import chuirer.utilitarios.Funciones;
import dataAccess.DaEstadisticas;
import dataAccess.DaSeguidores;
import dataAccess.DaSeguidos;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

/**
 *
 * @author fferegrino
 */
public class seguirOnoSeguir extends HttpServlet {

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
        String usuarioLogueado = (String) request.getSession().getAttribute("usuarioLogueado");
        if (Funciones.cadenaNulaOVacia(usuarioLogueado)) {
            respuesta.put("success", false);
            respuesta.put("error", "Usuario no logueado");
        } else {
            boolean agregar = Boolean.parseBoolean(request.getParameter("seguir"));
            String usuario = request.getParameter("u");
            DaSeguidos daSeguidos = new DaSeguidos();
            DaSeguidores daSeguidores = new DaSeguidores();
            DaEstadisticas daEstadisticas = new DaEstadisticas();


            if (agregar) {
                daSeguidos.agregarSeguido(usuarioLogueado, usuario);
                daEstadisticas.modificaSeguidos(usuarioLogueado, 1);

                daSeguidores.agregarSeguidor(usuario, usuarioLogueado);
                daEstadisticas.modificaSeguidores(usuario, 1);
                respuesta.put("success", true);
            }else{
                daSeguidores.eliminarSeguidor(usuario, usuarioLogueado);
                daEstadisticas.modificaSeguidores(usuario, -1);
                
                
                daSeguidos.eliminarSeguido(usuarioLogueado, usuario);
                daEstadisticas.modificaSeguidos(usuarioLogueado, -1);
                respuesta.put("success", true);
            }

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
