/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chuirer.beans;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author Miguel
 */
public class amigosBean extends org.apache.struts.action.ActionForm {
    
    private String user;//nombre de usuario actual

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    

    /**
     *
     */
    public amigosBean() {
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
