/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chuirer.action;

import chuirer.beans.seguidorsresBean;
import dataAccess.DaEstadisticas;
import dataAccess.DaSeguidores;
import dataAccess.DaUsuarios;
import entidadesDeNegocio.EnEstadisticas;
import entidadesDeNegocio.EnUsuario;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author fferegrino
 */
public class seguidosseguidores extends org.apache.struts.action.Action {

    /*
     * forward name="success" path=""
     */
    private static final String SUCCESS = "success";
    private static final String NOTEXISTS = "not_exists";
    private static final String PROTECTED = "protected";
    private static final String LOGOFF = "log_off";


    /**
     * This is the action called from the Struts framework.
     *
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
         Enumeration en = request.getParameterNames();
        String username = null;
        if (en.hasMoreElements()) {
            username = (String) en.nextElement();
            if(username.equals("usuario")){
                username = request.getParameter(username);
            }
        }
        if (username == null) {
            return mapping.findForward(LOGOFF);
        }

        DaUsuarios datosUsuario = new DaUsuarios();
        DaEstadisticas est = new DaEstadisticas();
        String usuarioActual = (String) request.getSession().getAttribute("usuarioLogueado");
        EnUsuario usuario = datosUsuario.recuperaUsuario(username);
        EnEstadisticas estad = est.recuperaEstadisticas(username);
        if (usuario == null) {
            return mapping.findForward(NOTEXISTS);
        }

        seguidorsresBean formulario = (seguidorsresBean) form;
        formulario.poneUsuario(usuario);
        formulario.setEstadisticas(estad);

        boolean loSigue = true;
        DaSeguidores daSeguidores = new DaSeguidores();
        ArrayList<String> usuariosSeguidores = daSeguidores.obtenerSeguidores(username);
        if (usuariosSeguidores.contains(usuarioActual)) {
            loSigue = true;
        } else {
            loSigue = false;
        }
        formulario.setFollowing(loSigue);
        formulario.setMismoUsuario(false);
        if (usuario.getPerfilPrivado() == false) {
            formulario.setVisible(true);
        } else {
            if (usuarioActual == null) {
                formulario.setVisible(false);
            } else {
                if (usuarioActual.equals(username)) {
                    formulario.setVisible(true);
                    formulario.setMismoUsuario(true);
                } else {
                    formulario.setMismoUsuario(false);
                    formulario.setVisible(loSigue);
                }
            }
        }

        
        return mapping.findForward(SUCCESS);
    }
}
