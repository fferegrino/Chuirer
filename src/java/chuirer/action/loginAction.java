package chuirer.action;

import chuirer.beans.login;
import chuirer.utilitarios.Funciones;
import dataAccess.DaUsuarios;
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
public class loginAction extends org.apache.struts.action.Action {

    /*
     * forward name="success" path=""
     */
    private static final String SUCCESS = "success";
    private final static String FAILURE = "failure";
    private final static String M_FAILURE = "m_failure";
    private final static String REGISTER = "register";

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
        
        
       boolean esmovil = Funciones.esMovil(request.getHeader("user-agent"));

        HttpSession ses = request.getSession();
        if (ses.getAttribute("usuarioLogueado") != null) {
            return mapping.findForward(SUCCESS);
        }

        login formBean = (login) form;
        String name = formBean.getName();
        if (formBean.getReg() != null) {
            if (formBean.getReg().equals("Registrate")) {
                return mapping.findForward(REGISTER);
            }
        }

        String pass = formBean.getPassword();
        if (((name == null)
                || pass == null
                || name.equals(""))
                && formBean.getLogin() != null) {
            formBean.setError("Error, campos vacíos");
            return mapping.findForward(FAILURE);
        }

        if ((new DaUsuarios()).validaUsuario(name, pass)) {
            ses.setAttribute("usuarioLogueado", name);
            ses.setAttribute("movil", esmovil);
            return mapping.findForward(SUCCESS);
        }

        if (formBean.getLogin() != null) {
            formBean.setError("Error, usuario o contraseña inválidos");
            if(esmovil){
            return mapping.findForward(M_FAILURE);
        }
            return mapping.findForward(FAILURE);
        }
        
        if(esmovil){
            return mapping.findForward(M_FAILURE);
        }
        return mapping.findForward(FAILURE);
    }
}
