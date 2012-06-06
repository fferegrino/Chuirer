/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chuirer.action;

import chuirer.beans.amigosBean;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Miguel
 */
public class amigosAction extends org.apache.struts.action.Action {

    /*
     * forward name="success" path=""
     */
    private static final String SUCCESS = "success";
    private static final String SUGERENCIAS = "sugerencias";
    
    
    
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
        HttpSession sesion=request.getSession();
        String user=(String)sesion.getAttribute("usuarioLogueado");
        
        amigosBean formulario=(amigosBean)form;
        
        //si queremos ver sugerencias de amigos
        String sugerencias=request.getParameter("sugerencias");
        if(sugerencias!=null){
            formulario.setUser(sugerencias);
            return mapping.findForward(SUGERENCIAS);
        }
        
        
        formulario.setUser(user);
        return mapping.findForward(SUCCESS);
    }
}
