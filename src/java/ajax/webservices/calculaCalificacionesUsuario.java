/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ajax.webservices;

import dataAccess.DaEstadisticas;
import dataAccess.DaMensajeFaveado;
import entidadesDeNegocio.EnMensajeCalificado;
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
public class calculaCalificacionesUsuario extends HttpServlet {

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
        String usuarioAObtenerCal = request.getParameter("idU");

        JSONObject respuesta = new JSONObject();
        int tweetsUsuario = (new DaEstadisticas()).recuperaEstadisticas(usuarioAObtenerCal).getMensajes();
        if (tweetsUsuario == 0) {
            respuesta.put("success", false);
            respuesta.put("error", "El usuario no tiene mensajes");
        } else {
            boolean tieneCalificaciones = false;
            int mensajesCalificados = 0;
            double calificaciones = 0;
            for (int i = tweetsUsuario; i > 0; i--) {
                EnMensajeCalificado calificado = new DaMensajeFaveado().recuperaCalificaciones(usuarioAObtenerCal + "." + i);
                if (calificado != null) {
                    if (calificado.getCalificacion() > 0) {
                        tieneCalificaciones = true;
                        mensajesCalificados++;
                        calificaciones += calificado.getCalificacion();
                    }
                }
            }
            if (tieneCalificaciones == false) {
                respuesta.put("success", false);
                respuesta.put("error", "El usuario no tiene mensajes calificados");
            } else {
                double nvaCal = calificaciones / mensajesCalificados;
                respuesta.put("success", true);
                respuesta.put("calificacion", nvaCal);
                respuesta.put("calificadores", mensajesCalificados);
            }
        }
        out.print(respuesta.toJSONString());
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
