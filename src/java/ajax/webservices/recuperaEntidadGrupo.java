/*
 *Utilizado por singleGrupo.jsp, recupera una entidad grupo con sus características
 */
package ajax.webservices;

import dataAccess.DaGrupos;
import entidadesDeNegocio.EnGrupo;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Miguel
 */
public class recuperaEntidadGrupo extends HttpServlet {

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
        HttpSession sesion=request.getSession();
       JSONObject respuesta=new JSONObject();
       String nombreGpo=request.getParameter("nombreGpo");
       
       //recuperamos grupo desde el xml
       DaGrupos daGpo=new DaGrupos(nombreGpo);
       EnGrupo enGpo=daGpo.recuperaGrupo(nombreGpo);
       
       //añadimos caracteristicas a la respuesta
       respuesta.put("nombreGpo", enGpo.getNOMBRE());
       respuesta.put("creador", enGpo.getCREADOR());
       respuesta.put("descripcion", enGpo.getDESCRIPCION());
       respuesta.put("numUsers", enGpo.getNUM_USER());
       
       ArrayList users=enGpo.getUSERS();
//       JSONArray usuarios=new JSONArray();
//       for(int i=0;i<users.size();i++){
//           usuarios.add(users.get(i));
//       }
       respuesta.put("usuarios", users);
       
       //para saber si se  muestra el boton "seguir"
       respuesta.put("banderaBoton", DaGrupos.esSeguido(enGpo.getNOMBRE(),(String)sesion.getAttribute("usuarioLogueado")));
       
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
