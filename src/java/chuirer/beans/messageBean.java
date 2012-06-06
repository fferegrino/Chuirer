package chuirer.beans;

import entidadesDeNegocio.EnMensaje;
import entidadesDeNegocio.EnUsuario;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author fferegrino
 */
public class messageBean extends org.apache.struts.action.ActionForm {
    
    private Date fechaPublicacion;
    private String usuario;
    private String contenido;
    private String via;
    private String imgUsuario;
    private boolean destacado;
    private int id;
    private String idUnico;
    private String realname;
    private String imgUrl;

    public String getImgUsuario() {
        return imgUsuario;
    }

    public void setImgUsuario(String imgUsuario) {
        this.imgUsuario = imgUsuario;
    }
    
    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public boolean isDestacado() {
        return destacado;
    }

    public void setDestacado(boolean destacado) {
        this.destacado = destacado;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdUnico() {
        return idUnico;
    }

    public void setIdUnico(String idUnico) {
        this.idUnico = idUnico;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }
    

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public void setInfo(EnMensaje Mensaje, EnUsuario Usuario){
      this.contenido = Mensaje.getCONTENIDO(true);
      this.destacado = Mensaje.isDESTACADO();
      this.fechaPublicacion = Mensaje.getFECHA_PUBLICACION();
      this.idUnico = Mensaje.getiD_UNICO();
      this.usuario = Mensaje.getUSERNAME();
      this.id = Mensaje.getID_MENSAJE();
      this.via = Mensaje.getVIA();
      this.realname = Usuario.getNombreReal() + " " + Usuario.getApellidos();
      this.imgUrl = Usuario.getImgUrl();
    }
    
   
    /**
     *
     */
    public messageBean() {
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
