/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataAccess;

import chuirer.utilitarios.DocumentoXML;
import chuirer.utilitarios.Impresion;
import com.myapp.struts.Propiedades;
import entidadesDeNegocio.EnCalificados;
import entidadesDeNegocio.EnLlaveCalificados;
import java.io.File;
import java.io.FileNotFoundException;
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
public class DaFaveados {

    private File rutaBuscarUser;
    private String archivoXML;

    public DaFaveados() {
        Properties props = new Propiedades().getProperties();
        this.rutaBuscarUser = new File(props.getProperty("rutaUsuarios"));
        this.archivoXML = "faveados.xml";
    }

    public EnCalificados recuperaFavs(String usuario) {
        EnCalificados valor_de_retorno = null;
        File archivoDeDatos = new File(rutaBuscarUser.getAbsolutePath() + "\\" + usuario + "\\" + archivoXML);
        if (!archivoDeDatos.exists()) {
            try {
                Document doc = new Document(creaNodoFaveados(new EnCalificados(usuario)));
                Impresion.imprimeXML(doc, archivoDeDatos);
            } catch (FileNotFoundException ex) {
                return null;
            } catch (IOException ex) {
                return null;
            }
        }
        try {
            Document documento = DocumentoXML.recuperaDocumento(archivoDeDatos);
            valor_de_retorno = creaObjetoFaveados(documento.getRootElement());
        } catch (JDOMException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        }
        return valor_de_retorno;
    }

    /**
     * Agrega una calificacion, el default es 8
     * @param usuario El usuario que califica
     * @param idMensaje El mensaje a calificar
     * @return <code>true</code> si se realizó la operacion con éxito, <code>false</code> si no.
     */
    public boolean agregaFav(String usuario, String idMensaje) {
        boolean se_agrego = false;
        EnLlaveCalificados elc = new EnLlaveCalificados(usuario, idMensaje, 8.0);
        EnCalificados ef = recuperaFavs(usuario);
        if (ef != null) {
            ef.addFaveado(elc);
            se_agrego = guardaFaveados(ef);
        }
        return se_agrego;
    }
    
    /**
     /**
     * Agrega una calificacion, el default es 8
     * @param usuario El usuario que califica
     * @param idMensaje El mensaje a calificar
     * @return <code>true</code> si se realizó la operacion con éxito, <code>false</code> si no.
     * @param calificacion La calificación al mensaje
     * @return <code>true</code> si se realizó la operacion con éxito, <code>false</code> si no.
     */
    public boolean agregaFav(String usuario, String idMensaje, double calificacion) {
        boolean se_agrego = false;
        EnCalificados ef = recuperaFavs(usuario);
        EnLlaveCalificados elc = new EnLlaveCalificados(usuario, idMensaje, calificacion);
        if (ef != null) {
            ef.addFaveado(elc);
            se_agrego = guardaFaveados(ef);
        }
        return se_agrego;
    }

    public boolean guardaFaveados(EnCalificados ef) {
        boolean se_guardo = false;
        String usuario = ef.getUsuario();
        File archivoDeDatos = new File(rutaBuscarUser.getAbsolutePath() + "\\" + usuario + "\\" + archivoXML);
        Document doc = new Document(creaNodoFaveados(ef));
        try {
            Impresion.imprimeXML(doc, archivoDeDatos);
            se_guardo = true;
        } catch (FileNotFoundException ex) {
            se_guardo = false;
        } catch (IOException ex) {
            se_guardo = false;
        }
        return se_guardo;
    }

    public EnCalificados creaObjetoFaveados(Element nodoRaiz) {
        String usuario = nodoRaiz.getAttributeValue("usuario");
        EnCalificados ef = new EnCalificados(usuario);
        List<Element> l = nodoRaiz.getChildren("faveado");
        for (Element s : l) {
            ef.addFaveado(creaObjetoHijoFaveados(s));
        }
        return ef;
    }

    public Element creaNodoFaveados(EnCalificados enFaveados) {
        Element faveados = new Element("faveados");
        faveados.setAttribute("usuario", enFaveados.getUsuario());
        faveados.setAttribute("calificaciones", String.valueOf(enFaveados.getFavs()));
        for (EnLlaveCalificados fav : enFaveados.getMensajes()) {
            faveados.addContent(creaNodoHijoFaveados(fav));
        }
        return faveados;
    }

    public Element creaNodoHijoFaveados(EnLlaveCalificados algo) {
        Element mensaje = new Element("faveado");
        mensaje.setAttribute("calificacion", String.valueOf(algo.getCalificacion()));
        mensaje.setText(algo.getIdMensaje());
        return mensaje;
    }
    
    public EnLlaveCalificados creaObjetoHijoFaveados(Element algo){
        EnLlaveCalificados calificados = new  EnLlaveCalificados();
        double calificacion  = 0;
        try{
            calificacion =  Double.parseDouble(algo.getAttributeValue("calificacion"));
        }catch(Exception e){
            
        }
        calificados.setIdMensaje(algo.getText());
        calificados.setCalificacion(calificacion);
        return calificados;
    }
}
