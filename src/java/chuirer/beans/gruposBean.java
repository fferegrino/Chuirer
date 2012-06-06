/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chuirer.beans;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Miguel
 */
public class gruposBean extends org.apache.struts.action.ActionForm {
    
    private String nombreGpo;
    private String creador;
    private String descripcion;
    private int noUsuarios;
    private ArrayList usuarios=new ArrayList();
    //almacena todos los grupos de la aplicacion
    private ArrayList allGpos=new ArrayList();    
    private int noTotalGpos;
    //usuario que quiere consultar los gpos que sigue, o crear gpo
    private String user;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getNoTotalGpos() {
        return noTotalGpos;
    }

    public void setNoTotalGpos(int noTotalGpos) {
        this.noTotalGpos = noTotalGpos;
    }

    public ArrayList getAllGpos() {
        return allGpos;
    }

    public void setAllGpos(ArrayList allGpos) {
        this.allGpos = allGpos;
    }

    public String getCreador() {
        return creador;
    }

    public void setCreador(String creador) {
        this.creador = creador;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getNoUsuarios() {
        return noUsuarios;
    }

    public void setNoUsuarios(int noUsuarios) {
        this.noUsuarios = noUsuarios;
    }

    public String getNombreGpo() {
        return nombreGpo;
    }

    public void setNombreGpo(String nombreGpo) {
        this.nombreGpo = nombreGpo;
    }

    public ArrayList getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList usuarios) {
        this.usuarios = usuarios;
    }

   

    /**
     *
     */
    public gruposBean() {
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
