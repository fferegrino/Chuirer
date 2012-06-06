package dataAccess;

import chuirer.utilitarios.DocumentoXML;
import chuirer.utilitarios.Funciones;
import chuirer.utilitarios.Impresion;
import com.myapp.struts.Propiedades;
import entidadesDeNegocio.EnMensaje;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * Clase de acceso a datos de mensajes
 *
 * @author fferegrino
 */
public class DaMensajes {

    private File rutaBuscar;
    private File rutaBuscarUser;
    private String archivoXML;

    /**
     * Constructor de la clase
     */
    public DaMensajes() {
        Properties props = new Propiedades().getProperties();
        this.rutaBuscarUser = new File(props.getProperty("rutaUsuarios"));
        this.rutaBuscar = new File(props.getProperty("rutaMensajes"));
        this.archivoXML = "usuario.xml";
    }

    /**
     * Método para recuperar un mensaje, recibe el ID único del mensaje, a
     * recordar que este está formado por el
     * <code>nombre de usuario</code> un
     * <code>"punto" . </code> y el
     * <code>número de mensaje</code> este método recuperará solo el
     * especificado.
     *
     * @param IdMensaje El id del mensaje con la composición antes mencionada
     * @return
     * <code>null</code> si hubo algun error o no existe el mensaje, el mensaje
     * correspondiente en otro caso
     */
    public EnMensaje recuperaMensaje(String IdMensaje) {
        EnMensaje retorno = null;
        if (!Funciones.cadenaNulaOVacia(IdMensaje)) {
            int posPunto = IdMensaje.indexOf(".");
            if (posPunto > 0) {
                try {
                    String idUsuario = IdMensaje.substring(0, posPunto); // Obtenemos el ID de usuario
                    int nUMEROmENSAJE = Integer.parseInt(IdMensaje.substring(posPunto + 1));
                    //Nos movemos hasta la carpeta de mensajes del usuario:
                    File carpetaMensajesUsuario = new File(this.rutaBuscarUser.getAbsolutePath() + "\\" + idUsuario + "\\mensajes");
                    // De ahí tratamos de leer el mensaje que se ha solicitado
                    Document mensaje = DocumentoXML.recuperaDocumento(carpetaMensajesUsuario.getAbsolutePath() + "\\" + IdMensaje + ".xml");
                    return creaObjetoMensaje(mensaje.getRootElement());
                } catch (JDOMException ex) {
                    
                } catch (IOException ex) {
                    
                }
            }
        }
        return retorno;
    }

    /**
     * Método para publicar mensaje aquí se pone automáticamente el ID único, el
     * mensaje debe tener ya todo el contenido previo como usuario, contenido u
     * la vía por la que fue publicado
     *
     * @param EnMensaje El objeto a ser publicado
     * @return
     * <code>true</code> si se publicó con éxito,
     * <code>false</code> en otro caso.
     */
    public boolean publicaMensaje(EnMensaje EnMensaje) {
        boolean guardado = false;
        if (EnMensaje != null) {
            if (!Funciones.cadenaNulaOVacia(EnMensaje.getUSERNAME())
                    && !Funciones.cadenaNulaOVacia(EnMensaje.getCONTENIDO())) {
                File directorioMensajes = new File(rutaBuscarUser.getAbsolutePath() + "/" + EnMensaje.getUSERNAME() + "/mensajes");
                try {
                    Integer iD_MENSAJE = 1;
                    if (!directorioMensajes.exists()) {
                        directorioMensajes.mkdir();
                    } else {
                        iD_MENSAJE = new DaEstadisticas().recuperaEstadisticas(EnMensaje.getUSERNAME()).getMensajes()+1;
                    }
                    DaMensajesGeneral msgGral = new DaMensajesGeneral();
                    DaListadoMensajes daListadoMensajes =  new DaListadoMensajes();
                    EnMensaje.setID_MENSAJE(iD_MENSAJE);
                    EnMensaje.setFECHA_PUBLICACION(new Date());
                    EnMensaje.setDESTACADO(false);
                    EnMensaje.setUNIVERSAL_ID(msgGral.sumaMensaje());
                    daListadoMensajes.agregaMensaje(EnMensaje);
                    Element raiz = creaNodoMensaje(EnMensaje);
                    String nombreArchivo = raiz.getChildText("id");
                    File archivo = new File(directorioMensajes.getAbsolutePath() + "/" + nombreArchivo + ".xml");
                    Document f = new Document(raiz);
                    Impresion.imprimeXML(f, archivo);
                    guardado = true;
                } catch (IOException ioe) {
                }
            }
        }
        return guardado;
    }

    /**
     *
     */
    public boolean creaArchivoDeMensajes(File raiz, String usuario) throws IOException {
        boolean vRegreso = false;
        File archivo = new File(raiz.getAbsolutePath() + "\\" + archivoXML);
        Element usuarios = new Element("usuario");
        usuarios.setAttribute("user", usuario);
        Element iDMENSAJES = new Element("numero_mensajes");
        iDMENSAJES.addContent("0");
        usuarios.addContent(iDMENSAJES);
        Document f = new Document(usuarios);
        Format format = Format.getPrettyFormat();
        XMLOutputter xmloutputter = new XMLOutputter(format);
        FileOutputStream file_os = new FileOutputStream(archivo);
        xmloutputter.output(f, file_os);
        file_os.close();
        return vRegreso;
    }

    /**
     * Realiza el mapeo de un objeto
     * <code>EnMensaje</code> a un nodo XML listo para ser impreso en el arvhivo
     *
     * @param Mensaje El mensaje a mapear
     * @return El nodo que contiene el resultado del mapeo
     */
    public Element creaNodoMensaje(EnMensaje Mensaje) {
        String nombre = Mensaje.getUSERNAME() + "." + Mensaje.getID_MENSAJE();
        Mensaje.setiD_UNICO(nombre);
        Element msg = new Element("mensaje");
        String destacado = Mensaje.isDESTACADO() ? "true" : "false";
        msg.setAttribute("destacado", destacado);
        msg.setAttribute("id",String.valueOf(Mensaje.getID_MENSAJE()));
        msg.setAttribute("universal",Mensaje.getUNIVERSAL_ID().toString());
        msg.setAttribute("favs",String.valueOf(Mensaje.getFAVS()));
        Element usuario = new Element("usuario");
        usuario.setText(Mensaje.getUSERNAME());
        Element contenido = new Element("contenido");
        contenido.setText(Mensaje.getCONTENIDO());
        Element fecha = new Element("fecha_publicacion");
        fecha.setText(Funciones.Date2HourDayString(Mensaje.getFECHA_PUBLICACION()));
        Element via = new Element("via");
        via.setText(Mensaje.getVIA());
        Element idUnico = new Element("id");
        idUnico.setText(Mensaje.getiD_UNICO());
        Element respuesta_a =  new Element("respuesta_a");
        respuesta_a.setText(String.valueOf(Mensaje.getEN_RESPUESTA_A()));
        msg.addContent(usuario);
        msg.addContent(contenido);
        msg.addContent(fecha);
        msg.addContent(via);
        msg.addContent(idUnico);
        msg.addContent(respuesta_a);
        return msg;
    }

    /**
     * Mapea de un nodo que contiene todos los elementos a un objeto de la clase
     * @param raiz El nodo del cual se desea extraer los datos
     * @return El objeto que contiene las propiedades que estaban guardadas en el nodo
     */
    public EnMensaje creaObjetoMensaje(Element raiz) {
        EnMensaje valorDeRetorno = new EnMensaje();
        valorDeRetorno.setDESTACADO(Boolean.parseBoolean(raiz.getAttributeValue("destacado")));
        valorDeRetorno.setID_MENSAJE(Integer.parseInt(raiz.getAttributeValue("id")));
        valorDeRetorno.setUSERNAME(raiz.getChildText("usuario"));
        valorDeRetorno.setCONTENIDO(raiz.getChildText("contenido"));
        valorDeRetorno.setFECHA_PUBLICACION(Funciones.HourDayString2Date(raiz.getChildText("fecha_publicacion")));
        valorDeRetorno.setVIA(raiz.getChildText("via"));
        valorDeRetorno.setiD_UNICO(raiz.getChildText("id"));
        valorDeRetorno.setUNIVERSAL_ID(Long.parseLong((raiz.getAttributeValue("universal"))));
        try{
            valorDeRetorno.setEN_RESPUESTA_A(Long.parseLong(raiz.getChildText("respuesta_a")));
        }catch(Exception e){
            valorDeRetorno.setEN_RESPUESTA_A(-1L);
        }
        try{
            valorDeRetorno.setFAVS(Integer.parseInt(raiz.getAttributeValue("favs")));
        }catch(Exception e){
            valorDeRetorno.setFAVS(0);
        }
        return valorDeRetorno;
    }
}
