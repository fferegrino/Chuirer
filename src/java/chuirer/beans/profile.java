/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chuirer.beans;

import entidadesDeNegocio.EnEstadisticas;
import entidadesDeNegocio.EnUsuario;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Antonio
 */
public class profile extends org.apache.struts.action.ActionForm {

    private EnUsuario usuario = new EnUsuario();
    private String username;
    private String descripcion;
    private String url;
    private String imgUrl;
    private String realname;
    private String rol;
    private boolean protegida;
    private boolean administrador;
    private int peticiones;
    private int seguidos;
    private int seguidores;
    private int grupos;
    private int mensajes;
    /**
     *
     */
    private String logOff;
    private String editProfile;
    private String adminOn;

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public boolean isProtegida() {
        return protegida;
    }

    public void setProtegida(boolean protegida) {
        this.protegida = protegida;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int getPeticiones() {
        return peticiones;
    }

    public void setPeticiones(int peticiones) {
        this.peticiones = peticiones;
    }

    public String getAdminOn() {
        return adminOn;
    }

    public void setAdminOn(String adminOn) {
        this.adminOn = adminOn;
    }

    public String getEditProfile() {
        return editProfile;
    }

    public void setEditProfile(String editProfile) {
        this.editProfile = editProfile;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLogOff() {
        return logOff;
    }

    public void setLogOff(String logOff) {
        this.logOff = logOff;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEstadisticas(EnEstadisticas ee){
        this.seguidores = ee.getSeguidores();
        this.seguidos = ee.getSeguidos();
        this.mensajes = ee.getMensajes();
        this.grupos = ee.getGrupos();
    }
    
    public EnUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(EnUsuario usuario) {
        this.usuario = usuario;
    }

    public int getGrupos() {
        return grupos;
    }

    public void setGrupos(int grupos) {
        this.grupos = grupos;
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

    /**
     *
     */
    public profile() {
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
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();

        return errors;
    }
}
