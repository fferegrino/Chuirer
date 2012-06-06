/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ajax.webservices;

import dataAccess.DaGrupos;
import entidadesDeNegocio.EnGrupo;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

/**
 *
 * @author Miguel
 */
public class borrarGpo extends HttpServlet {

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
        PrintWriter out=response.getWriter();
        
        JSONObject respuesta=new JSONObject();
        String nombreGpo=request.getParameter("nombreGpo");
        String user=request.getParameter("user");
        
        DaGrupos dgpo=new DaGrupos(nombreGpo);
        
        
         //si no existe el grupo a borrar
        if(DaGrupos.validaGrupo(nombreGpo)){
            EnGrupo enGpo=dgpo.recuperaGrupo(nombreGpo);
            //si es el creador lo borra
            if(enGpo.getCREADOR().equals(user)){
                dgpo.borraGrupo(nombreGpo); 
                respuesta.put("bandera", "esCreador");              
            }
            else{
                respuesta.put("bandera", "noEsCreador");
            }               
        }
        else{
             respuesta.put("bandera", "noExisteGrupo");
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
