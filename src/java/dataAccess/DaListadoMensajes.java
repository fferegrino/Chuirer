package dataAccess;

import chuirer.utilitarios.DocumentoXML;
import chuirer.utilitarios.Funciones;
import chuirer.utilitarios.Impresion;
import com.myapp.struts.Propiedades;
import entidadesDeNegocio.EnListadoMensajes;
import entidadesDeNegocio.EnLlaveListadoMensajes;
import entidadesDeNegocio.EnMensaje;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;

/**
 *
 * @author fferegrino
 */
public class DaListadoMensajes {

    private File archivoMensajes;
    private String archivoXML;

    public DaListadoMensajes() {
        Properties props = new Propiedades().getProperties();
        this.archivoMensajes = new File(props.getProperty("rutaArchivoMensajes"));
    }

    /**
     * Recupera una hoja de mensajes, completa con los id de mensaje
     *
     * @param NUMERO_DE_HOJA El número de hoja a recuperar
     * @return El objeto hoja con todas las propiedades contenidas en los
     * registros de la aplicación
     */
    public EnListadoMensajes recuperaHoja(Long NUMERO_DE_HOJA) {
        String hoja = String.valueOf(NUMERO_DE_HOJA) + ".xml";
        EnListadoMensajes enListadoMensajes = null;
        File archivo_hoja = new File(this.archivoMensajes.getAbsolutePath() + "\\" + hoja);
        try {
            Document documento_hoja = DocumentoXML.recuperaDocumento(archivo_hoja);
            Element raiz = documento_hoja.getRootElement();
            return creaObjetoListadoMensajes(raiz);
        } catch (JDOMException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        }
    }

    /**
     * Agrega un mensaje al índice general, crea las hojas de este dinámicamente
     * según el mensaje que se le envíe
     *
     * @param em El mensaje que se va a agregar
     * @return
     * <code>true</code> si se agregó correctamente,
     * <code>false</code> si no
     */
    public boolean agregaMensaje(EnMensaje em) {
        Long hoja = Funciones.devuelveNumeroHoja(em.getUNIVERSAL_ID());
        File hojaDeMensajes = new File(this.archivoMensajes.getAbsolutePath() + "\\" + hoja + ".xml");
        if (!hojaDeMensajes.exists()) {
            try {
                
                crearHojaDeMensajes(hoja);
            } catch (IOException ex) {
                System.out.println("error" + ex);
                return false;
            }
        }
        try {
            Document documentHoja = DocumentoXML.recuperaDocumento(hojaDeMensajes);
            EnListadoMensajes elm = creaObjetoListadoMensajes(documentHoja.detachRootElement());
            elm.addMensaje(new EnLlaveListadoMensajes(em.getUNIVERSAL_ID(),em.getUSERNAME(),em.getID_MENSAJE(),true));
            documentHoja.setRootElement(creaElementoListadoMensajes(elm));
            Impresion.imprimeXML(documentHoja, hojaDeMensajes);
            return true;
        } catch (JDOMException ex) {
            System.out.println("error2");
            return false;
        } catch (IOException ex) {
            System.out.println("error3");
            return false;
        } catch (Exception ex) {
            System.out.println("error4" +ex);
            return false;
        }
    }

    /**
     * Crea una hoja nueva, a partir del número que se le envíe.
     *
     * @param hoja El número de la hoja a crear
     * @throws IOException Si hubo un error de escritura
     */
    private void crearHojaDeMensajes(Long hoja) throws IOException {
        File hojaDeMensajes = new File(this.archivoMensajes.getAbsolutePath() + "\\" + hoja + ".xml");
        Long inicial = (hoja * 100) + 1;
        EnListadoMensajes elementoNuevo = new EnListadoMensajes(hoja, inicial, inicial);
        Document d = new Document(creaElementoListadoMensajes(elementoNuevo));
        Impresion.imprimeXML(d, hojaDeMensajes);
    }

    public EnListadoMensajes creaObjetoListadoMensajes(Element elementRaiz) {
        Long pagina = Long.parseLong(elementRaiz.getAttributeValue("pagina"));
        long inicial = Long.parseLong(elementRaiz.getAttributeValue("inicio"));
        long _final = Long.parseLong(elementRaiz.getAttributeValue("final"));
        EnListadoMensajes valorDeRetorno = new EnListadoMensajes(pagina, inicial, _final);
        List<Element> mensajes = elementRaiz.getChildren("mensaje");
        for (Element mensajeElement : mensajes) {
            EnLlaveListadoMensajes enLlaveListadoMensajes = creaObjetoLlaveListadoMensajes(mensajeElement);
            if (enLlaveListadoMensajes != null) {
                valorDeRetorno.addMensaje(enLlaveListadoMensajes);
            }
        }
        return valorDeRetorno;
    }

    public EnLlaveListadoMensajes creaObjetoLlaveListadoMensajes(Element elementHijo) {
        try {
            String idRelativoUsuario = elementHijo.getText();
            Long idUniversal = Long.parseLong(elementHijo.getAttributeValue("id"));
            EnLlaveListadoMensajes llaveMensajes = new EnLlaveListadoMensajes(idRelativoUsuario, idUniversal);
            return llaveMensajes;
        } catch (Exception ex) {
        }
        return null;
    }

    public Element creaElementoListadoMensajes(EnListadoMensajes elm) {
        Element mensajes = new Element("mensajes");
        mensajes.setAttribute("pagina", String.valueOf(elm.getPAGINA()));
        mensajes.setAttribute("inicio", elm.getNUMERO_INICIAL().toString());
        mensajes.setAttribute("final", elm.getNUMERO_FINAL().toString());
        for (EnLlaveListadoMensajes ellm : elm.getMENSAJES()) {
            mensajes.addContent(creaElementoLlaveListadoMensajes(ellm));
        }
        return mensajes;
    }

    public Element creaElementoLlaveListadoMensajes(EnLlaveListadoMensajes ellm) {
        Element mensaje = new Element("mensaje");
        mensaje.setAttribute("id", ellm.getID_UNIVERSAL().toString());
        mensaje.setText(ellm.getLlaveMensajeRelativo());
        return mensaje;
    }
}
