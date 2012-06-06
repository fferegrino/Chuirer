package chuirer.action;

import chuirer.beans.SEGUIDOSBean;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SEGUIDOSAction extends org.apache.struts.action.Action {

    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String nombre = request.getParameter("Name");
        SEGUIDOSBean bean = (SEGUIDOSBean) form;
        
        bean.setName(nombre);
        return mapping.findForward(SUCCESS);
    }
}