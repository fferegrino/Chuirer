package ajax.webservices;

import chuirer.utilitarios.Funciones;
import dataAccess.DaEstadisticas;
import dataAccess.DaMensajes;
import entidadesDeNegocio.EnMensaje;
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
public class publicaMensaje extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");
        JSONObject respuesta = new JSONObject();
        if (request.getSession().getAttribute("usuarioLogueado") != null) {
            String idU = request.getSession().getAttribute("usuarioLogueado").toString();
            String mensaje = request.getParameter("msg");
            if (!Funciones.cadenaNulaOVacia(mensaje)) {
                if (mensaje.length() > 140) {
                    mensaje = mensaje.substring(0, 139);
                }
                String source = "???";
                Boolean mov = (Boolean) request.getSession().getAttribute("movil");
                mov = mov == null ? false : mov;
                if (mov) {
                    source = "movil";
                } else {
                    source = "web";
                }

                EnMensaje msg = new EnMensaje(idU, mensaje, source);

                DaMensajes daMensajes = new DaMensajes();
                daMensajes.publicaMensaje(msg);
                (new DaEstadisticas()).sumaUnMensaje(idU);
                respuesta.put("success", true);
            }
        } else {
            respuesta.put("success", false);
            respuesta.put("error", "No estás logueado");
        }
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
