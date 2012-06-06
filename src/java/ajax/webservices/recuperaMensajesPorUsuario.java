/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ajax.webservices;

import chuirer.utilitarios.Funciones;
import com.myapp.struts.Propiedades;
import dataAccess.DaListadoMensajes;
import dataAccess.DaMensajes;
import dataAccess.DaMensajesGeneral;
import dataAccess.DaSeguidos;
import entidadesDeNegocio.EnListadoMensajes;
import entidadesDeNegocio.EnLlaveListadoMensajes;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author fferegrino
 */
public class recuperaMensajesPorUsuario extends HttpServlet {

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

        int maxNumTweets = Integer.parseInt(new Propiedades().getProperties().getProperty("mensajes.general.numero"));
        PrintWriter out = response.getWriter();
        ArrayList<String> usuarios = new ArrayList<String>();
        Long mensajeDesde = 0L;
        try {
            mensajeDesde = Long.parseLong(request.getParameter("desde")) - 1L;
        } catch (NumberFormatException ex) {
            mensajeDesde = (new DaMensajesGeneral()).recuperaGeneralMensajes().getID_MENSAJES_UNIVERSAL();
        }
        // Aquí se debe recuperar la lista de seguidos y seguidores        
        String usuarioLogueado = (String) request.getSession().getAttribute("usuarioLogueado");
        JSONObject objeto = new JSONObject();
        if (!Funciones.cadenaNulaOVacia(usuarioLogueado)) {
            Long hoja = Funciones.devuelveNumeroHoja(mensajeDesde);
            Long mensajesTotales = 0L;
            Long mensajesLeidos = 0L;
            DaSeguidos daSeguidos = new DaSeguidos();
            DaListadoMensajes dlm = new DaListadoMensajes();
            DaMensajes daMensajes = new DaMensajes();
            JSONArray arrayMensajes = new JSONArray();
            usuarios = daSeguidos.obtenerSeguidos(usuarioLogueado);
            usuarios.add(usuarioLogueado);

            excesoDeMensajes:
            while (hoja >= 0) {
                EnListadoMensajes recuperaHoja = dlm.recuperaHoja(hoja);
                ArrayList<EnLlaveListadoMensajes> mensajeS = recuperaHoja.getMENSAJES();
                Collections.reverse(mensajeS);
                for (EnLlaveListadoMensajes ellm : mensajeS) {
                    if (ellm.getID_UNIVERSAL() <= mensajeDesde) {
                        if (usuarios.contains(ellm.getUSUARIO())) {
                            arrayMensajes.add(daMensajes.recuperaMensaje(ellm.getLlaveMensajeRelativo()).toJSONObject());
                            mensajesTotales++;
                            if (mensajesTotales > maxNumTweets) {
                                objeto.put("hasta", daMensajes.recuperaMensaje(ellm.getLlaveMensajeRelativo()).getUNIVERSAL_ID() - 1L);
                                hoja = -1L;
                                break excesoDeMensajes;
                            }
                        }
                        mensajesLeidos++;
                    }
                }
                hoja--;
            }
            mensajesLeidos = mensajeDesde - mensajesLeidos;
            objeto.put("total", mensajesTotales);
            objeto.put("hasta", mensajesLeidos > 0 ? mensajesLeidos : 0);
            objeto.put("mensajes", arrayMensajes);
        } else {
            objeto.put("success", false);
            objeto.put("error", "Usuario no logueado");
        }
        out.print(objeto.toJSONString());
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
