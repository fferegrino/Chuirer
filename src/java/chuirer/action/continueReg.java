package chuirer.action;

import chuirer.beans.succesReg;
import chuirer.utilitarios.Archivos;
import com.myapp.struts.Propiedades;
import dataAccess.DaUsuarios;
import entidadesDeNegocio.EnUsuario;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author Antonio
 */
public class continueReg extends org.apache.struts.action.Action {

    /*
     * forward name="success" path=""
     */
    private static final String SUCCESS = "success";
    private static final String ERR_IMG = "failure";
    private static final String OTRO = "success";

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
        request.setCharacterEncoding("UTF-8");
        succesReg formBean = (succesReg) form;
        if (formBean.getReg() != null) {
            if (formBean.getReg().equals("otrodia")) {
                return mapping.findForward(OTRO);
            }
        }



        HttpSession ses = request.getSession();
        String usuario = ses.getAttribute("usuarioLogueado").toString();
        DaUsuarios daUsuarios = new DaUsuarios();
        EnUsuario EnUsuario = daUsuarios.recuperaUsuario(usuario);
        FormFile imagen = formBean.getImage();
        if (imagen.getFileSize() > 0) {
            // <editor-fold desc="Subida de imagen" defaultstate="collapsed">
            String contentType = imagen.getContentType();
            String fileName = imagen.getFileName();
            FileOutputStream fos;
            String rutaServidorImagenes = (new Propiedades()).getProperties().getProperty("rutaServidorImagenes");
            String rutaTotalAEscribirImagenes = request.getServletContext().getRealPath(rutaServidorImagenes);
            String ruta = rutaTotalAEscribirImagenes + "/" + usuario + fileName.substring(fileName.lastIndexOf("."));
            fos = new FileOutputStream(ruta);
            fos.write(imagen.getFileData());
            fos.close();
            ruta = rutaServidorImagenes + "/" + usuario + fileName.substring(fileName.lastIndexOf("."));
            EnUsuario.setImgUrl(ruta);
            // </editor-fold>
        }
        String desc = formBean.getDescripcion();
        EnUsuario.setDscripcion(desc != null ? desc : "");
        String url = formBean.getUrl();
        EnUsuario.setUrl(url != null ? url : "");
        daUsuarios.actualizaUsuario(EnUsuario);

        return mapping.findForward(SUCCESS);
    }
}
