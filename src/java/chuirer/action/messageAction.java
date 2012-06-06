package chuirer.action;

import chuirer.beans.messageBean;
import dataAccess.DaMensajes;
import dataAccess.DaUsuarios;
import entidadesDeNegocio.EnMensaje;
import entidadesDeNegocio.EnUsuario;
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
public class messageAction extends org.apache.struts.action.Action {

    /*
     * forward name="success" path=""
     */
    private static final String SUCCESS = "success";

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
        }
        
        EnMensaje mensaje = new DaMensajes().recuperaMensaje(username);
        EnUsuario usuario = new DaUsuarios().recuperaUsuario(mensaje.getUSERNAME());
        messageBean forma = (messageBean)form;
        forma.setInfo(mensaje,usuario);
        return mapping.findForward(SUCCESS);
    }
}
