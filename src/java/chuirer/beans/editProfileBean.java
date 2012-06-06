/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chuirer.beans;

import entidadesDeNegocio.EnUsuario;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Antonio
 */
public class editProfileBean extends org.apache.struts.action.ActionForm {

    private EnUsuario Usuario;
    private String username;
    private String realName;
    private String lastName;
    private String email;
    private String url;
    private String imgUrl;
    private String descripcion;
    private String logOff;
    private Boolean privado;
    
    public String getLogOff() {
        return logOff;
    }

    public void setLogOff(String logOff) {
        this.logOff = logOff;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }       

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getLastName() {
        return lastName;
    }

    public Boolean getPrivado() {
        return privado;
    }

    public void setPrivado(Boolean privado) {
        this.privado = privado;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public editProfileBean() {
        super();
        // TODO Auto-generated constructor stub
    }

    public EnUsuario getUsuario() {
        return Usuario;
    }

    public void setUsuario(EnUsuario Usuario) {
        this.Usuario = Usuario;
        this.username = Usuario.getUserName();
        this.email = Usuario.getEmail();
        this.url = Usuario.getUrl();
        this.realName = Usuario.getNombreReal();
        this.lastName = Usuario.getApellidos();
        this.descripcion = Usuario.getDscripcion();
        this.imgUrl = Usuario.getImgUrl();
        this.privado = Usuario.getPerfilPrivado();
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
