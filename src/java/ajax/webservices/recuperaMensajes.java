    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ajax.webservices;

import com.myapp.struts.Propiedades;
import dataAccess.DaEstadisticas;
import dataAccess.DaMensajes;
import entidadesDeNegocio.EnMensaje;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Recupera los mensajes antigüos del usuario
 * @author Antonio
 */
public class recuperaMensajes extends HttpServlet {

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
        String usuarioARecuperar = request.getParameter("usuario");
        int mensajesARecuperar = Integer.parseInt(new Propiedades().getProperties().getProperty("mensajes.usuario.numero"));
        int mensajesARecuperarAux = mensajesARecuperar;
        int tweetsUsuario = (new DaEstadisticas()).recuperaEstadisticas(usuarioARecuperar).getMensajes();

        int nTwetts;;
        try {
            nTwetts = Integer.parseInt(request.getParameter("t"));

            if (nTwetts > tweetsUsuario) {
                nTwetts = tweetsUsuario;
            }
        } catch (NumberFormatException e) {
            nTwetts = tweetsUsuario;
        }
        int nTwettsAux = nTwetts;
        JSONArray arreglo = new JSONArray();
        DaMensajes daM = new DaMensajes();
        JSONObject objeto = new JSONObject();
        objeto.put("usuario", usuarioARecuperar);
        objeto.put("numero_mensajes", tweetsUsuario);

        EnMensaje men = daM.recuperaMensaje(usuarioARecuperar + "." + nTwetts);
        while (men != null && mensajesARecuperar > 0) {
            arreglo.add(men.toJSONObject());
            nTwetts--;//El tweet a recuperar
            mensajesARecuperar--; //Para controlar la cantidad
            men = daM.recuperaMensaje(usuarioARecuperar + "." + nTwetts);
        }
        int consultadoHasta = nTwettsAux - mensajesARecuperarAux;
        consultadoHasta = consultadoHasta < 1 ? 0 : consultadoHasta;
        objeto.put("hasta", consultadoHasta);
        objeto.put("mensajes", arreglo);
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
