/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ajax.webservices;

import chuirer.utilitarios.Funciones;
import dataAccess.DaEstadisticas;
import dataAccess.DaPeticiones;
import dataAccess.DaSeguidores;
import dataAccess.DaSeguidos;
import entidadesDeNegocio.EnPeticion;
import entidadesDeNegocio.EnPeticiones;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

/**
 * Acepta la petición de un usuario ;)
 *
 * @author fferegrino
 */
public class aceptaPeticion extends HttpServlet {

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
        String username = (String) request.getSession().getAttribute("usuarioLogueado");
        String usuario_peticion = request.getParameter("usuario");
        if (!Funciones.cadenaNulaOVacia(username) && !Funciones.cadenaNulaOVacia(usuario_peticion)) {
            DaPeticiones daPeticiones = new DaPeticiones();
            EnPeticiones enPeticiones = daPeticiones.recuperaPeticiones(username);
            EnPeticion enPeticion = enPeticiones.sacaPeticion(usuario_peticion);
            if (enPeticion == null) {
                respuesta.put("success", false);
            } else {
                DaSeguidos daSeguidos = new DaSeguidos();
                DaSeguidores daSeguidores = new DaSeguidores();
                DaEstadisticas daEstadisticas =  new DaEstadisticas();
                
                daSeguidos.agregarSeguido(usuario_peticion, username);
                daEstadisticas.modificaSeguidos(enPeticion.getUsuario_peticion(), 1);
                
                daSeguidores.agregarSeguidor(username, usuario_peticion);
                daEstadisticas.modificaSeguidores(enPeticion.getUsuario(), 1);
                
                daPeticiones.remuevePeticion(enPeticion);
                respuesta.put("success", true);
            }
        } else {
            respuesta.put("success", false);
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
