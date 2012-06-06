/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ajax.webservices;

import chuirer.utilitarios.Funciones;
import chuirer.utilitarios.Validaciones;
import dataAccess.DaUsuarios;
import entidadesDeNegocio.EnUsuario;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

/**
 *
 * @author Antonio
 */
public class modificaUsuario extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String usuario = request.getParameter("u");
        String apellido = request.getParameter("a");
        String nombreReal = request.getParameter("r");
        String desc = request.getParameter("d");
        String priv = request.getParameter("p");
        String url = request.getParameter("l");

        String usuarioSession = (String) request.getSession().getAttribute("usuarioLogueado");
        
        DaUsuarios dataAccessUsuarios = new DaUsuarios();
        JSONObject respuesta = new JSONObject();
        if (!Funciones.cadenaNulaOVacia(usuarioSession)) {
            if (usuario.equals(usuarioSession)) {
                boolean guarda = true;
                String error = "";
                Validaciones v = new Validaciones();
                EnUsuario EntidadUsuario = dataAccessUsuarios.recuperaUsuario(usuario);
                EntidadUsuario.setPerfilPrivado(Boolean.parseBoolean(priv));
                EntidadUsuario.setApellidos(apellido);
                EntidadUsuario.setNombreReal(nombreReal);
                if(desc.length() > 160){
                    error += "La descripci&oacute;n no debe de tener m&aacute;s de 160 caracteres\n";
                    guarda = false;
                }
                EntidadUsuario.setDscripcion(desc);
                
                
                if (v.validaUrl(url) || Funciones.cadenaNulaOVacia(url)) {
                    EntidadUsuario.setUrl(url);
                } else {
                    error += "La url debe ser v&aacute;lida";
                    guarda = false;
                }
                if (guarda) {
                    dataAccessUsuarios.actualizaUsuario(EntidadUsuario);
                    respuesta.put("success",true);
                }
                else{
                    respuesta.put("success", false);
                    respuesta.put("error", error);
                }
            } else {
                respuesta.put("succes", false);
                respuesta.put("error", "Solo puedes modificar tu usuario");
            }
        } else {
            respuesta.put("succes", false);
            respuesta.put("error", "Debes estar logueado");
        }
        out.print(respuesta.toString());
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
