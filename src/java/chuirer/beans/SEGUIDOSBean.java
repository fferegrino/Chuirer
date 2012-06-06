package chuirer.beans;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author GothDunkel
 */
public class SEGUIDOSBean extends org.apache.struts.action.ActionForm {
    
    private String nombre;
    private int number;

    public String getName() {
        return nombre;
    }

    public void setName(String string) {
        nombre = string;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int i) {
        number = i;
    }

    public SEGUIDOSBean() {
        super();
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (nombre.equals("AAA")) {
            errors.add("name", new ActionMessage("errors.name"));
        }
        mapping.getInputForward();
        return errors;
    }
}
