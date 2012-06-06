package dataAccess;

import chuirer.utilitarios.DocumentoXML;
import chuirer.utilitarios.Impresion;
import com.myapp.struts.Propiedades;
import entidadesDeNegocio.EnMensajesGeneral;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;

/**
 * Encargado de aumentar el contador universal de los mensajes
 *
 * @author fferegrino
 */
public class DaMensajesGeneral {

    private File archivoMensajes;
    private String archivoXML;

    public DaMensajesGeneral() {
        Properties props = new Propiedades().getProperties();
        this.archivoXML = "mensajes.xml";
        this.archivoMensajes = new File(props.getProperty("rutaArchivoMensajes") + "\\" + this.archivoXML);
    }

    public synchronized Long sumaMensaje() {
        Long nuevoId = 0L;
        try {
            Document mensaje = DocumentoXML.recuperaDocumento(this.archivoMensajes);
            Element raiz = mensaje.getRootElement();
            Element numero_mensajes = raiz.getChild("numero_mensaje");
            nuevoId = Long.parseLong(numero_mensajes.getText()) + 1;
            numero_mensajes.setText(nuevoId.toString());
            Impresion.imprimeXML(mensaje, archivoMensajes);
        } catch (JDOMException ex) {
        } catch (IOException ex) {
        }
        return nuevoId;
    }

    /**
     * Recuperar cual es el id del mensaje
     * @return 
     */
    public synchronized EnMensajesGeneral recuperaGeneralMensajes() {
        try {
            Document mensaje = DocumentoXML.recuperaDocumento(this.archivoMensajes);
            Element raiz = mensaje.getRootElement();
            EnMensajesGeneral emg = new EnMensajesGeneral();
            Element numero_mensajes = raiz.getChild("numero_mensaje");
            emg.setID_MENSAJES_UNIVERSAL(Long.parseLong(numero_mensajes.getText()));
            return emg;
        } catch (JDOMException ex) {
        } catch (IOException ex) {
        }

        return new EnMensajesGeneral();
    }
}
