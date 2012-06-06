/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chuirer.beans;

import chuirer.utilitarios.Funciones;
import entidadesDeNegocio.EnEstadisticas;
import entidadesDeNegocio.EnUsuario;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author fferegrino
 */
public class seguidorsresBean extends org.apache.struts.action.ActionForm {
       
   
     private EnUsuario usuario;
    private String imgUrl;
    private String url;
    private String desc;
    private String username;
    private String realname;
    private int seguidores;
    private int seguidos;
    private int mensajes;
    private Boolean visible;
    private Boolean following;
    private Boolean mismoUsuario;

    
    public void poneUsuario(EnUsuario usuario) {
        this.usuario = usuario;
        this.desc = usuario.getDscripcion();
        this.username = usuario.getUserName();
        this.realname = usuario.getNombreReal() + " " + usuario.getApellidos();
        this.imgUrl = Funciones.cadenaNulaOVacia(usuario.getImgUrl()) ? "../images/sitio/DEFAULT.png":".."+usuario.getImgUrl();
        this.url = usuario.getUrl();
    }

    public void setEstadisticas(EnEstadisticas EnEstadisticas){
        this.mensajes = EnEstadisticas.getMensajes();
        this.seguidores = EnEstadisticas.getSeguidores();
        this.seguidos = EnEstadisticas.getSeguidos();
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
    
    public int getMensajes() {
        return mensajes;
    }

    public void setMensajes(int mensajes) {
        this.mensajes = mensajes;
    }

    public int getSeguidores() {
        return seguidores;
    }

    public void setSeguidores(int seguidores) {
        this.seguidores = seguidores;
    }

    public int getSeguidos() {
        return seguidos;
    }

    public void setSeguidos(int seguidos) {
        this.seguidos = seguidos;
    }
    
    public String getDesc() {
        return desc;
    }

    public String getUrl(){
        return url;
    }
    
    public String getImgUrl() {
        return imgUrl;
    }

    public String getRealname() {
        return realname;
    }

    public String getUsername() {
        return username;
    }

    public Boolean getFollowing() {
        return following;
    }

    public void setFollowing(Boolean following) {
        this.following = following;
    }

    public Boolean getMismoUsuario() {
        return mismoUsuario;
    }

    public void setMismoUsuario(Boolean mismoUsuario) {
        this.mismoUsuario = mismoUsuario;
    }
    
    public seguidorsresBean() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * This is the action called from the Struts framework.
     *
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */

    /**
     * This is the action called from the Struts framework.
     *
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
     
        return errors;
    }
}
