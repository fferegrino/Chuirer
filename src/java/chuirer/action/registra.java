/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chuirer.action;

import chuirer.beans.registro;
import chuirer.utilitarios.Funciones;
import dataAccess.DaUsuarios;
import entidadesDeNegocio.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 *
 * @author Antonio
 */
public class registra extends org.apache.struts.action.Action {

    /*
     * forward name="success" path=""
     */
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private static final String USREXISTS = "failure";
    

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
        registro formBean = (registro) form;
        MessageResources mr = getResources(request);
        String usrNme = formBean.getNombreUsuario();
        DaUsuarios dUs = new DaUsuarios();
        EnUsuario usuario = dUs.recuperaUsuario(usrNme);
        
        if(usuario != null){
            formBean.setError(mr.getMessage("ui.validation.userexists"));
            return mapping.findForward(USREXISTS);
        }
        
        usuario = new EnUsuario();
        usuario.setRol("com");
        String pass = Funciones.md5(formBean.getPass());
        usuario.setPassword(pass);
        usuario.setEmail(formBean.getEmail());
        usuario.setUserName(formBean.getNombreUsuario());
        usuario.setNombreReal(formBean.getNombreReal());
        usuario.setApellidos(formBean.getApellido());
        dUs.guardaUsuario(usuario);
        HttpSession Ses = request.getSession();
        Ses.setAttribute("usuarioLogueado", usuario.getUserName());
        return mapping.findForward(SUCCESS);
    }
}
