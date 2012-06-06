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
import java.util.Arrays;
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
public class recuperaNuevosMensajesPorUsuario extends HttpServlet {

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

        ArrayList<String> usuarios = new ArrayList<String>();
        Long mensajeHasta = 0L;
        Boolean soloConsulta = false;
        try {
            mensajeHasta = Long.parseLong(request.getParameter("hasta"));
        } catch (NumberFormatException ex) {
            mensajeHasta = (new DaMensajesGeneral()).recuperaGeneralMensajes().getID_MENSAJES_UNIVERSAL();
        }
        try {
            soloConsulta = Boolean.parseBoolean(request.getParameter("c"));
        } catch (Exception ex) {
            soloConsulta = false;
        }
        Long mensajeDesde = (new DaMensajesGeneral()).recuperaGeneralMensajes().getID_MENSAJES_UNIVERSAL();
        // Aquí se debe recuperar la lista de seguidos y seguidores
        // A falta de eso, agregaré usuarios comunes
        String usuarioLogueado = (String) request.getSession().getAttribute("usuarioLogueado");
        Long hoja = Funciones.devuelveNumeroHoja(mensajeDesde);
        Long mensajesTotales = 0L;
        DaListadoMensajes dlm = new DaListadoMensajes();
        DaMensajes daMensajes = new DaMensajes();
        JSONObject objeto = new JSONObject();
        JSONArray arrayMensajes = new JSONArray();
        if (!Funciones.cadenaNulaOVacia(usuarioLogueado)) {
            DaSeguidos daSeguidos =  new DaSeguidos();
            usuarios = daSeguidos.obtenerSeguidos(usuarioLogueado);
            usuarios.add(usuarioLogueado);
            /*
             * 
             */
//            usuarios.add("usuariocomun");
//            usuarios.add("usuariocomu3");
            /*
             * 
             */
            excesoDeMensajes:
            while (hoja >= 0) {
                EnListadoMensajes recuperaHoja = dlm.recuperaHoja(hoja);
                ArrayList<EnLlaveListadoMensajes> mensajeS = recuperaHoja.getMENSAJES();
                Collections.reverse(mensajeS);
                for (EnLlaveListadoMensajes ellm : mensajeS) {
                    if (usuarios.contains(ellm.getUSUARIO())) {
                        if (ellm.getID_UNIVERSAL() <= mensajeHasta) {
                            hoja = -1L;
                            break excesoDeMensajes;
                        }
                        if (!soloConsulta) {
                            arrayMensajes.add(daMensajes.recuperaMensaje(ellm.getLlaveMensajeRelativo()).toJSONObject());
                        }
                        mensajesTotales++;
                    }
                }
                hoja--;
            }

            objeto.put("total", mensajesTotales);
            objeto.put("mensajes", arrayMensajes);
        } else {
            objeto.put("success", false);
            objeto.put("error", "usuario no logueado");
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
