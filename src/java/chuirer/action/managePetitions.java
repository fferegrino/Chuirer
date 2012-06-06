/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chuirer.action;

import chuirer.beans.profile;
import dataAccess.DaPeticiones;
import dataAccess.DaUsuarios;
import entidadesDeNegocio.EnPeticiones;
import entidadesDeNegocio.EnUsuario;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author fferegrino
 */
public class managePetitions extends org.apache.struts.action.Action {

    /*
     * forward name="success" path=""
     */
    private static final String SUCCESS = "success";
    private static final String NOEXISTE = "noexiste";

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
        String defaultImage = "/images/profilepics/Chuirer.jpg";
        profile form2 = (profile) form;




        HttpSession ses = request.getSession();
        if (ses.getAttribute("usuarioLogueado") == null) {
            return mapping.findForward(NOEXISTE);
        }

        EnUsuario pro = (new DaUsuarios()).recuperaUsuario(ses.getAttribute("usuarioLogueado").toString());
        EnPeticiones peticiones = (new DaPeticiones()).recuperaPeticiones(pro.getUserName());
        form2.setUsername(pro.getUserName());
        form2.setDescripcion(pro.getDscripcion());
        form2.setRealname(pro.getNombreReal() + " " + pro.getApellidos());
        form2.setUrl(pro.getUrl());
        form2.setProtegida(pro.getPerfilPrivado());
        form2.setPeticiones(peticiones.getNumero_peticiones());
        form2.setImgUrl((pro.getImgUrl() != null && !pro.getImgUrl().isEmpty() ? ".." + pro.getImgUrl() : ".." + defaultImage));
        return mapping.findForward(SUCCESS);

    }
}
