/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chuirer.action;

import chuirer.beans.gruposBean;
import dataAccess.DaGrupos;
import entidadesDeNegocio.EnGrupo;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Miguel
 */
public class gruposAction extends org.apache.struts.action.Action {

    /*
     * forward name="success" path=""
     */
    private static final String ALLGPOS = "todosLosGrupos";//todos los gpos de la aplicacion
    private static final String SINGLEGRUPO = "singleGpo";//caracteristicas de un gpo en especifico
    private static final String SEGUIDOS = "seguidos";//grupos seguidos por el usuario que se ingresa
    private static final String CREAR = "crearGpo";//crear un nuevo grupo
    private static final String NOEXISTE = "noExiste";//PAntalla de aviso que no existe el grupo
    private static final String ELIMINAR = "eliminar";//elimina un grupo
    

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
        gruposBean formulario=(gruposBean)form;
        //entidad y dataAcces a utilizar
        DaGrupos daGpo;
        EnGrupo enGpo;
        
        //MANDA A UN GRUPO PARA VER DATOS.nombre del gpo ingresado como parametro
        String nombre=request.getParameter("nombre");
        if(nombre!=null){
            //si el grupo existe
            if(DaGrupos.validaGrupo(nombre)){
            //recuperamos grupo desde el xml
            daGpo=new DaGrupos(nombre);
            enGpo=daGpo.recuperaGrupo(nombre);
            
            //agrgeamos atributos al form bean
            formulario.setNombreGpo(enGpo.getNOMBRE());
            formulario.setCreador(enGpo.getCREADOR());
            formulario.setDescripcion(enGpo.getDESCRIPCION());
            formulario.setNoUsuarios(enGpo.getNUM_USER());
            formulario.setUsuarios(enGpo.getUSERS());
            return mapping.findForward(SINGLEGRUPO);
        }
            else
                return mapping.findForward(NOEXISTE);
        }
        
        //si se quiere ver los gpos que sigue un usuario
        String seguidosUser=request.getParameter("seguidos");
        //contiene el nombre de usuario que se desea saber los gpos que sigue
        if(seguidosUser!=null){
            formulario.setUser(seguidosUser);
            return mapping.findForward(SEGUIDOS);
        }
        
        //si se va a crear un gpo nuevo
        String creador=request.getParameter("crearGpo");
        if(creador!=null){
            formulario.setUser(creador);
            return mapping.findForward(CREAR);
        }
        
        //si se quiere eliminar un grupo
        String eliminar=request.getParameter("eliminar");
        if(eliminar!=null){
            formulario.setUser(eliminar);
            return mapping.findForward(ELIMINAR);
        }
        
        
        //todos los grupos existentes
        ArrayList allGpos=DaGrupos.recuperaGruposExistentes();
        formulario.setAllGpos(allGpos);
        formulario.setNoTotalGpos(allGpos.size());
        return mapping.findForward(ALLGPOS);
    }
}
