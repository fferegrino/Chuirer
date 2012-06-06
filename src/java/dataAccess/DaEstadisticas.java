package dataAccess;

import chuirer.utilitarios.DocumentoXML;
import chuirer.utilitarios.Funciones;
import chuirer.utilitarios.Impresion;
import com.myapp.struts.Propiedades;
import entidadesDeNegocio.EnEstadisticas;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;

/**
 * Clase de acceso a datos de estadisticas
 *
 * @author Antonio
 */
public class DaEstadisticas {

    private File rutaBuscarUser;
    private String archivoEstadisticasXML;

    /**
     * Constructor de la clase
     */
    public DaEstadisticas() {
        Properties props = new Propiedades().getProperties();
        this.rutaBuscarUser = new File(props.getProperty("rutaUsuarios"));
        this.archivoEstadisticasXML = "stats.xml";
    }

    /**
     * Recupera las estadisticas asociadas a un usuario
     *
     * @param username El nombre de usuario del que se desea recuparar datos
     * @return Un objeto que contiene las estadísticas del usuario
     */
    public EnEstadisticas recuperaEstadisticas(String username) {
        try {
            File archivoEstadisticas = new File(rutaBuscarUser.getAbsoluteFile() + "/" + username + "/" + archivoEstadisticasXML);
            Document stats = DocumentoXML.recuperaDocumento(archivoEstadisticas);
            Element raiz = stats.getRootElement();
            return creaObjetoEstadisticas(raiz);
        } catch (JDOMException ex) {
        } catch (IOException ex) {
        }
        return null;
    }

    /**
     * Método para actualizar las estadisticas de un usuario
     *
     * @param enEstadisticas
     * @return
     * <code>true</code> si la actualización se realizó correctamente,
     * <code>false</code> en otro caso
     */
    public boolean actualizaEstadisticas(EnEstadisticas enEstadisticas) {
        boolean hecho = true;
        try {
            File archivoEstadisticas = new File(rutaBuscarUser.getAbsoluteFile() + "/" + enEstadisticas.getUsername() + "/" + archivoEstadisticasXML);
            Document stats = DocumentoXML.recuperaDocumento(archivoEstadisticas);
            stats.detachRootElement();
            stats.setRootElement(this.creaNodoEstadisticas(enEstadisticas));
            Impresion.imprimeXML(stats, archivoEstadisticas);
        } catch (JDOMException ex) {
        } catch (IOException ex) {
        }
        return hecho;
    }

    /**
     * Se le suma un mensaje al contador del usuario
     *
     * @param username El nombre del usuario al cual se le sumarán los mensajes
     */
    public void sumaUnMensaje(String username) {
        EnEstadisticas est = this.recuperaEstadisticas(username);
        est.setMensajes(est.getMensajes() + 1);
        est.setUltimaActualizacion(new Date());
        this.actualizaEstadisticas(est);
    }

    /**
     * Método para agregar o remover un grupo al usuario
     *
     * @param username El usuario al cual le vamos a añadir ese mensaje
     * @param grupo
     * <code>1</code> para agregar
     * <code>-1</code> para descontar
     */
    public void modificaGrupos(String username, int grupo) {
        EnEstadisticas est = this.recuperaEstadisticas(username);
        est.setGrupos(est.getGrupos() + grupo);
        this.actualizaEstadisticas(est);
    }

    /**
     * Método para agregar o remover un seguido al usuario
     *
     * @param username El usuario al cual le vamos a añadir ese mensaje
     * @param seguido
     * <code>1</code> para agregar
     * <code>-1</code> para descontar
     */
    public void modificaSeguidos(String username, int seguido) {
        EnEstadisticas est = this.recuperaEstadisticas(username);
        est.setSeguidos(est.getSeguidos() + seguido);
        this.actualizaEstadisticas(est);
    }

    /**
     * Método para agregar o remover un seguidor al usuario
     *
     * @param username El usuario al cual le vamos a añadir ese mensaje
     * @param seguidor
     * <code>1</code> para agregar
     * <code>-1</code> para descontar
     */
    public void modificaSeguidores(String username, int seguidor) {
        EnEstadisticas est = this.recuperaEstadisticas(username);
        est.setSeguidores(est.getSeguidores() + seguidor);
        this.actualizaEstadisticas(est);
    }

    /**
     * Método para agregar o remover un mensaje destacado
     *
     * @param username El usuario al cual le vamos a añadir ese mensaje
     * @param destacado
     * <code>1</code> para agregar
     * <code>-1</code> para descontar
     */
    public void modificaDestacado(String username, int destacado) {
        EnEstadisticas est = this.recuperaEstadisticas(username);
        est.setDestacados(est.getDestacados() + destacado);
        this.actualizaEstadisticas(est);
    }

    /**
     * Crea un objeto mapeando los elementos de un nodo con su correspondiente
     *
     * @param Estadisticas El nodo que contiene la información
     * @return El objeto que se compone de la información extraída del nodo XML
     */
    public EnEstadisticas creaObjetoEstadisticas(Element Estadisticas) {
        EnEstadisticas estadisticas = new EnEstadisticas();
        estadisticas.setMensajes(Integer.parseInt(Estadisticas.getChildText("numero_mensajes")));
        estadisticas.setSeguidos(Integer.parseInt(Estadisticas.getChildText("seguidos")));
        estadisticas.setUsername(Estadisticas.getAttributeValue("usuario"));
        estadisticas.setSeguidores(Integer.parseInt(Estadisticas.getChildText("seguidores")));
        estadisticas.setDestacados(Integer.parseInt(Estadisticas.getChildText("destacados")));
        try {
            estadisticas.setUltimaActualizacion(Funciones.HourDayString2Date(Estadisticas.getChildText("ultima_actualizacion")));
        } catch (Exception E) {
            estadisticas.setUltimaActualizacion(null);
        }
        try {
            estadisticas.setGrupos(Integer.parseInt(Estadisticas.getChildText("grupos")));
        } catch (Exception E) {
            estadisticas.setGrupos(0);
        }
        return estadisticas;
    }

    /**
     * Crea un nodo XML a partir de las propiedades que componen al objeto
     *
     * @param Estadisticas El objeto que contiene la informacion a mapear
     * @return El nodo con el correspondiente contenido
     */
    public Element creaNodoEstadisticas(EnEstadisticas Estadisticas) {
        Element estadisticas = new Element("estadisticas");
        estadisticas.setAttribute("usuario", Estadisticas.getUsername());
        Element numero_mensajes = new Element("numero_mensajes");
        numero_mensajes.setText(Estadisticas.getMensajes().toString());
        Element seguidos = new Element("seguidos");
        seguidos.setText(Estadisticas.getSeguidos().toString());
        Element seguidores = new Element("seguidores");
        seguidores.setText(Estadisticas.getSeguidores().toString());
        Element destacados = new Element("destacados");
        destacados.setText(Estadisticas.getDestacados().toString());
        Element ultima_actualizacion = new Element("ultima_actualizacion");
        ultima_actualizacion.setText(Funciones.Date2HourDayString(Estadisticas.getUltimaActualizacion()));
        Element grupos = new Element("grupos");
        grupos.setText(Estadisticas.getGrupos().toString());
        estadisticas.addContent(numero_mensajes);
        estadisticas.addContent(seguidos);
        estadisticas.addContent(seguidores);
        estadisticas.addContent(destacados);
        estadisticas.addContent(ultima_actualizacion);
        estadisticas.addContent(grupos);
        return estadisticas;
    }
}
