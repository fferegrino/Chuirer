/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataAccess;

import chuirer.utilitarios.DocumentoXML;
import chuirer.utilitarios.Impresion;
import com.myapp.struts.Propiedades;
import entidadesDeNegocio.EnLlaveCalificado;
import entidadesDeNegocio.EnMensajeCalificado;
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
public class DaMensajeFaveado {

    private File rutaBuscar;
    private File rutaBuscarUser;
    private String archivoXML;

    /**
     * Constructor de la clase
     */
    public DaMensajeFaveado() {
        Properties props = new Propiedades().getProperties();
        this.rutaBuscarUser = new File(props.getProperty("rutaUsuarios"));
    }

    /**
     * Recupera la información de las calificaciones asignadas a un mensaje
     * @param idMensaje El id del mensaje que queremos recuperar
     * @return El objeto que contiene la información del mensaje
     */
    public EnMensajeCalificado recuperaCalificaciones(String idMensaje) {
        EnMensajeCalificado mensajeCalificado = null;
        try {
            File archivoDeDatos = new File(rutaFavsMensaje(idMensaje));
            if(!archivoDeDatos.exists()){
                Long id = new DaMensajes().recuperaMensaje(idMensaje).getUNIVERSAL_ID();
                EnMensajeCalificado calificados = new EnMensajeCalificado(id, idMensaje);
                Document algo = new Document(creaElementoEnMensajeCalificado(calificados));
                Impresion.imprimeXML(algo, archivoDeDatos);
            }
            Document document = DocumentoXML.recuperaDocumento(archivoDeDatos);
            Element raiz = document.getRootElement();
            return creaObjetoEnMensajeCalificado(raiz);
        } catch (JDOMException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        }
    }
    /**
     * Para agregar la calificación a un mensaje dentro de sus archivos
     * @param idMensaje El mensaje al cual se va a agregar la calificación
     * @param usuario El usuario calificador
     * @param calificacion La calificación que le desea asignar
     * @return <code>true</code> en caso de que se haya actualizado de manera correcta, <code>false</code> en otro caso.
     */
    public boolean agregaCalificacion(String idMensaje, String usuario, Double calificacion){
        EnMensajeCalificado mensajeCalificado = this.recuperaCalificaciones(idMensaje);
        EnLlaveCalificado llaveCalificado = new EnLlaveCalificado(usuario, calificacion, idMensaje);
        mensajeCalificado.addCalificacion(llaveCalificado);
        Document d = new Document(creaElementoEnMensajeCalificado(mensajeCalificado));
        try {
            Impresion.imprimeXML(d, rutaFavsMensaje(idMensaje));
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    public EnMensajeCalificado creaObjetoEnMensajeCalificado(Element padre) {
        Long idUniversal = Long.parseLong(padre.getAttributeValue("idUnico"));
        String idRelativo = padre.getAttributeValue("idRelativo");
        EnMensajeCalificado emc = new EnMensajeCalificado(idUniversal, idRelativo);
        List<Element> elements = padre.getChildren("usuario");
        for (Element element : elements) {
            emc.addCalificacion(creaObjetoLlaveCalificado(element));
        }
        return emc;
    }

    public EnLlaveCalificado creaObjetoLlaveCalificado(Element hijo) {
        EnLlaveCalificado elc = new EnLlaveCalificado();
        String usuarioCal = hijo.getText();
        Double calificacion = Double.parseDouble(hijo.getAttributeValue("calificacion"));
        elc.setCalificacion(calificacion);
        elc.setCalificador(usuarioCal);
        return elc;
    }

    public Element creaElementoEnMensajeCalificado(EnMensajeCalificado calificados) {
        Element mensaje = new Element("mensaje");
        mensaje.setAttribute("idUnico", String.valueOf(calificados.getIdUnico()));
        mensaje.setAttribute("idRelativo", String.valueOf(calificados.getIdRelativo()));
        for (EnLlaveCalificado calificads : calificados.getUsuariosFaveadores()) {
            mensaje.addContent(creaElementoEnLlaveCalificado(calificads));
        }
        return mensaje;
    }

    public Element creaElementoEnLlaveCalificado(EnLlaveCalificado elc) {
        Element usuario = new Element("usuario");
        usuario.setAttribute("calificacion", String.valueOf(elc.getCalificacion()));
        usuario.setText(elc.getCalificador());
        return usuario;
    }

    /**
     * Dadao el id de un mensaje, nos devuelve la ruta que ocupa en el servidor
     * @param idMensaje El id del mensaje a buscar
     * @return La cadena que representa la ruta absoluta al archivo de favs.
     */
    private String rutaFavsMensaje(String idMensaje) {
            String usuario = idMensaje.split("\\.")[0];
        return this.rutaBuscarUser.getAbsolutePath() + "\\" + usuario + "\\mensajes\\calificaciones\\fav." + idMensaje + ".xml";
    }
}
