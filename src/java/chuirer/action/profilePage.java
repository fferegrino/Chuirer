/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chuirer.action;

import chuirer.beans.profile;
import dataAccess.DaEstadisticas;
import dataAccess.DaPeticiones;
import dataAccess.DaUsuarios;
import entidadesDeNegocio.EnEstadisticas;
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
 * @author Antonio
 */
public class profilePage extends org.apache.struts.action.Action {

    /*
     * forward name="success" path=""
     */
    private static final String SUCCESS = "success";
    private static final String M_SUCCESS = "m_success";
    private static final String NOEXISTE = "noexiste";
    private static final String CERAR_SESION = "logoff";
    private static final String EDITAR_PERFIL = "editar_perfil";
    private static final String ADMIN = "admin";

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

        String defaultImage = "http://1.bp.blogspot.com/_Gb77vg70nCE/Sk5IM2vBL1I/AAAAAAAAARs/lN4wrDTrVGU/s200/los-pilares-de-la-creacion-ii.jpg";
        profile formulario = (profile)form;
        
        
        if(formulario.getAdminOn() != null){
            return mapping.findForward(ADMIN);
        }
        
        if(formulario.getLogOff() != null){
            HttpSession ses = request.getSession();
            ses.invalidate();
            return mapping.findForward(CERAR_SESION);
        }
        
        if(formulario.getEditProfile() != null){
            return mapping.findForward(EDITAR_PERFIL);
        }
        
        HttpSession ses = request.getSession();
        if (ses.getAttribute("usuarioLogueado") == null) {
            return mapping.findForward(NOEXISTE);
        }
        
        EnUsuario enUsuario = (new DaUsuarios()).recuperaUsuario(ses.getAttribute("usuarioLogueado").toString());
        EnPeticiones peticiones = (new DaPeticiones()).recuperaPeticiones(enUsuario.getUserName());
        EnEstadisticas ee = (new DaEstadisticas()).recuperaEstadisticas(enUsuario.getUserName());
        formulario.setUsername(enUsuario.getUserName());
        formulario.setDescripcion(enUsuario.getDscripcion());
        formulario.setRealname(enUsuario.getNombreReal() + " "+enUsuario.getApellidos());
        formulario.setUrl(enUsuario.getUrl());
        formulario.setProtegida(enUsuario.getPerfilPrivado());
        formulario.setPeticiones(peticiones.getNumero_peticiones());
        formulario.setImgUrl( (enUsuario.getImgUrl() != null && !enUsuario.getImgUrl().isEmpty() ? ".."+enUsuario.getImgUrl() : defaultImage));
        formulario.setEstadisticas(ee);
        if(enUsuario.getRol().equals(ADMIN)){
            formulario.setAdministrador(true);
        }
        else
        {
            formulario.setAdministrador(false);
        }
        Boolean mov =  (Boolean)request.getSession().getAttribute("movil");
        mov = mov == null ? false : mov;
        if(mov){
            return mapping.findForward(M_SUCCESS);
        }
        return mapping.findForward(SUCCESS);
    }
}
