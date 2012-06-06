/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ajax.webservices;

import dataAccess.DaEstadisticas;
import dataAccess.DaGrupos;
import entidadesDeNegocio.EnGrupo;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jdom.JDOMException;
import org.json.simple.JSONObject;

/**
 *
 * @author Miguel
 */
public class creaGrupo extends HttpServlet {

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
            throws ServletException, IOException, JDOMException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject respuesta=new JSONObject();
        
        //datos del Gpo
        String nombreGpo=request.getParameter("nombreGpo");
        String desc=request.getParameter("desc");
        String creador=request.getParameter("creador");
        
        DaGrupos dGpo=new DaGrupos(nombreGpo);
        EnGrupo enGpo= new EnGrupo(creador,nombreGpo,desc);
        boolean bandera=dGpo.guardaGrupo(enGpo);
        
        //actualizo miembreoDE.xml del usuario
        DaGrupos.agregaGrupoEnCarpetaUsuario(creador, nombreGpo);
       
        
        //si se creo el grupo, es decir si no existia anteriormente
        if(bandera){
            respuesta.put("nombreGpo",nombreGpo);
            respuesta.put("desc",desc);
            respuesta.put("creador",creador);
            
            DaEstadisticas daEstadisticas = new DaEstadisticas();
            daEstadisticas.modificaGrupos(creador, 1);
        }
        else{
            respuesta.put("nombreGpo","noCreado");
            respuesta.put("desc","noCreado");
            respuesta.put("creador","noCreado");
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
        try {
            processRequest(request, response);
        } catch (JDOMException ex) {
            Logger.getLogger(creaGrupo.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (JDOMException ex) {
            Logger.getLogger(creaGrupo.class.getName()).log(Level.SEVERE, null, ex);
        }
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
