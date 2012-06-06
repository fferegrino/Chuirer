/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ajax.webservices;

import chuirer.utilitarios.Funciones;
import dataAccess.DaEstadisticas;
import dataAccess.DaMensajes;
import entidadesDeNegocio.EnEstadisticas;
import entidadesDeNegocio.EnMensaje;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Recupoera los mensajes posteados "hacia adelante" de un usuario.
 *
 * @author fferegrino
 */
public class recuperaNuevosMensajes extends HttpServlet {

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

        JSONObject respuesta = new JSONObject();

        PrintWriter out = response.getWriter();
        // <editor-fold desc="Recuperacion de los parámetros" default-state="collapsed">
        String fechaUltimaActualizacionS = request.getParameter("h");
        String usuarioARecuperar = request.getParameter("u");
        int id_tweet = 0;
        Date fechaUltimaActualizacion = null;
        boolean consultaValida = true;
        try {
            id_tweet = Integer.parseInt(request.getParameter("t"));
            fechaUltimaActualizacion = Funciones.HourDayString2Date(fechaUltimaActualizacionS);
            if (Funciones.cadenaNulaOVacia(usuarioARecuperar)) {
                throw new Exception();
            }
        } catch (Exception e) {
            respuesta.put("error", "Error en los parametros recibidos");
            consultaValida = false;
        }
        // </editor-fold>
        if (consultaValida) {
            EnEstadisticas estadisticasUsuario = new DaEstadisticas().recuperaEstadisticas(usuarioARecuperar);
            if (estadisticasUsuario != null) {
                int numero_tweets_nuevo = estadisticasUsuario.getMensajes();
                Date ultima_publicacion = estadisticasUsuario.getUltimaActualizacion();
                int mensajesRecuperados = 0;
                if (numero_tweets_nuevo > id_tweet /*
                         * ||
                         * fechaUltimaActualizacion.compareTo(ultima_publicacion)
                         * < 0
                         */) {
                    // Si el número de tweets es mayor a las estadisticas
                    // Si la última fecha de publicación es mayor a la que se recibe como parámetro
                    /**
                     *
                     * if(date1.compareTo(date2)>0){ System.out.println("Date1
                     * is after Date2"); }else if(date1.compareTo(date2)<0){
                     * System.out.println("Date1 is before Date2"); }else
                     * if(date1.compareTo(date2)==0){ System.out.println("Date1
                     * is equal to Date2"); }else{ System.out.println("How to
                     * get here?"); }
                     */
                    JSONArray mensajes = new JSONArray();
                    DaMensajes dataAccessMensajes = new DaMensajes();
                    EnMensaje mensaje = dataAccessMensajes.recuperaMensaje(usuarioARecuperar + "." + numero_tweets_nuevo);
                    numero_tweets_nuevo--;
                    while (numero_tweets_nuevo >= id_tweet && mensaje != null) {
                        mensajesRecuperados++;
                        mensajes.add(mensaje.toJSONObject());
                        mensaje = dataAccessMensajes.recuperaMensaje(usuarioARecuperar + "." + numero_tweets_nuevo);
                        numero_tweets_nuevo--;
                    }
                    respuesta.put("mensajes", mensajes);

                }
                respuesta.put("nuevos_mensajes", mensajesRecuperados);
            } else {
                respuesta.put("error", "El usuario no existe");
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
