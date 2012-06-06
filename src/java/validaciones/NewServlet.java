/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package validaciones;

import dataAccess.DaGrupos;
import dataAccess.DaUsuarios;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Miguel
 */
public class NewServlet extends HttpServlet {

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
        try {
            /*
             * TODO output your page here. You may use following sample code.
             */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet NewServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            ArrayList gposSeguidos=DaGrupos.recuperaGruposEnCarpetaUsuario("miguel");
            out.println("<h1>Servlet NewServlet at " + gposSeguidos + "</h1><br/><br/>");
            
           
             DaGrupos gpo;
             String nombreGpo;   
             String user;        //un seguidor de un grupo "x"
             ArrayList users;    //seguidores de un grupo "x"
             
             //amigos sugeridos
             ArrayList sugerencias=new ArrayList();
            
            //recorre todos los grupos seguidos
            for(int i=1;i<=gposSeguidos.size()-1;i++){
                 //recupero el nombre de un grupo seguido
                 nombreGpo=(String)gposSeguidos.get(i);
                 
                 //recupera los usuarios del grupo "nombreGpo"
                   gpo=new DaGrupos(nombreGpo);
                   users=gpo.recuperaUsuariosGrupo(nombreGpo);
                   
                 //recorre los seguidores del grupo "x"
                 for(int j=0;j<users.size();j++){
                     user=(String)users.get(j);                    
                     
                     //agregamos un seguidor a las sugerencias,claro si no existe ya como sugerencia y si no soy y mismo
                     if(!sugerencias.contains(user) && !(user.equals("miguel"))){
                        sugerencias.add(user);
                     }                    
                 }
            }
            
            out.println("<h1>Servlet NewServlet at " + sugerencias + "</h1><br/><br/>");
            
               
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
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
