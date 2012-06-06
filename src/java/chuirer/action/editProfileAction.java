package chuirer.action;

import chuirer.beans.editProfileBean;
import dataAccess.DaUsuarios;
import entidadesDeNegocio.EnUsuario;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Antonio
 */
public class editProfileAction extends org.apache.struts.action.Action {

    /*
     * forward name="success" path=""
     */
    private static final String NOT_LOGGED = "not_logged";
    private static final String SUCCESS = "success";
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
        
        
        
        editProfileBean forma = (editProfileBean)form;
        HttpSession ses = request.getSession();
        if(forma.getLogOff() != null){
            ses.invalidate();
            return mapping.findForward(LOGOFF);
        }
        
        
        if(ses.getAttribute("usuarioLogueado") == null){
            return mapping.findForward(NOT_LOGGED);
        }
        EnUsuario usuario = (new DaUsuarios()).recuperaUsuario(ses.getAttribute("usuarioLogueado").toString());
        forma.setUsuario(usuario);
        
        return mapping.findForward(SUCCESS);
    }
}
