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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;

/**
 *
 * @author Miguel
 */
public class dejarDeSeguir extends HttpServlet {

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
         
        HttpSession sesion=request.getSession();
        String iDuser=(String)sesion.getAttribute("usuarioLogueado");
        String gpo=request.getParameter("grupo");        
        JSONObject respuesta=new JSONObject();
        
        //creamos entidad Gpo y eleminamos el id de usruario de su carpeta de integrantes
        DaGrupos daGpos=new DaGrupos(gpo);
        EnGrupo enGpo=daGpos.recuperaGrupo(gpo);
        boolean band=daGpos.eliminaGrupoUsuario(enGpo, iDuser);
        
        //eliminamos el idde gpo dentro del xml "miembroDe" del usuario
        boolean band2=DaGrupos.elimnaGrupoEnCarpetaUsuario(iDuser, gpo);
        
        //si las 2 eliminaciones se hicieron correctas
        if(band &&band2){
            respuesta.put("bandera", "true");
            
            DaEstadisticas daEstadisticas = new DaEstadisticas();
            daEstadisticas.modificaGrupos(iDuser, -1);
        }
        else{
             respuesta.put("bandera", "false");
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
