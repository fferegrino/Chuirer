package dataAccess;

import chuirer.utilitarios.DocumentoXML;
import chuirer.utilitarios.Funciones;
import chuirer.utilitarios.Impresion;
import com.myapp.struts.Propiedades;
import entidadesDeNegocio.EnPeticion;
import entidadesDeNegocio.EnPeticiones;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;

/**
 * Para los usuarios con cuenta privada, para controlar las peticiones de los
 * usuarios
 *
 * @author fferegrino
 */
public class DaPeticiones {

    private File rutaBuscarUsuario;
    private String archivoPeticiones;

    public DaPeticiones() {
        Properties props = new Propiedades().getProperties();
        this.rutaBuscarUsuario = new File(props.getProperty("rutaUsuarios"));
        this.archivoPeticiones = "peticiones.xml";
    }

    public EnPeticiones recuperaPeticiones(String Username) {
        EnPeticiones valorDeRetorno = null;
        try {
            File archivoPeticion = new File(rutaBuscarUsuario.getAbsolutePath() + "//" + Username + "//" + archivoPeticiones);
            Document peticiones = DocumentoXML.recuperaDocumento(archivoPeticion);
            Element raiz = peticiones.getRootElement();
            return creaObjetoPeticiones(raiz);
        } catch (JDOMException ex) {
        } catch (IOException ex) {
        }
        return valorDeRetorno;
    }

    /**
     * Agrega una petición a un usuario
     * @param peticion La ptetición a agregar
     * @return <code>true</code> si se agregó correctamente, <code>false</code> si no.
     */
    public boolean agregaPeticion(EnPeticion peticion) {
        boolean agregada = false;
        peticion.setFecha_peticion(new Date());
        EnPeticiones peticiones = recuperaPeticiones(peticion.getUsuario());
        agregada = peticiones.agregarPeticion(peticion);
        if (agregada) {
            File archivoPeticion = new File(rutaBuscarUsuario.getAbsolutePath() + "//" + peticion.getUsuario() + "//" + archivoPeticiones);
            peticiones.setNumero_peticiones(peticiones.getNumero_peticiones() + 1);
            Element nodoRaiz = this.creaNodoPeticiones(peticiones);
            Document Documento = new Document(nodoRaiz);
            agregada = true;
            try {
                Impresion.imprimeXML(Documento, archivoPeticion);
            } catch (FileNotFoundException ex) {
                agregada = false;
                System.out.printf("Entro");
            } catch (IOException ex) {
                agregada = false;
                System.out.printf("Entro");
            }
        }
        return agregada;
    }

    /**
     * Remover una petición de los archivos de la base
     * @param peticion La petición a remover
     * @return <code>true</code> si se eliminó correctamente, <code>false</code> en otro caso
     */
    public boolean remuevePeticion(EnPeticion peticion) {
        boolean agregada = false;
        peticion.setFecha_peticion(new Date());
        EnPeticiones peticiones = recuperaPeticiones(peticion.getUsuario());
        agregada = true;
        peticiones.remuevePeticion(peticion);
        if (agregada) {
            File archivoPeticion = new File(rutaBuscarUsuario.getAbsolutePath() + "//" + peticion.getUsuario() + "//" + archivoPeticiones);
            peticiones.setNumero_peticiones(peticiones.getNumero_peticiones() - 1);
            Element nodoRaiz = this.creaNodoPeticiones(peticiones);
            Document Documento = new Document(nodoRaiz);
            agregada = true;
            try {
                Impresion.imprimeXML(Documento, archivoPeticion);
            } catch (FileNotFoundException ex) {
                agregada = false;
                //System.out.printf("Entro");
            } catch (IOException ex) {
                agregada = false;
                //System.out.printf("Entro");
            }
        }
        return agregada;
    }
    
    /**
     * Crea un objeto que contiene las peticiones que se le han realizado al
     * usuario así como la información de cuantas son
     *
     * @param RaizDeNodo El nodo a partir del cual se desea armar el objeto
     * @return El objeto que contiene a la información del nodo
     */
    public EnPeticiones creaObjetoPeticiones(Element RaizDeNodo) {
        EnPeticiones nodoConvertido = new EnPeticiones(RaizDeNodo.getAttributeValue("usuario"));
        nodoConvertido.setNumero_peticiones(Integer.parseInt(RaizDeNodo.getAttributeValue("cantidad")));
        if (nodoConvertido.getNumero_peticiones() > 0) {
            List<Element> hijos = RaizDeNodo.getChildren();
            for (Element e : hijos) {
                EnPeticion peticion = creaObjetoPeticion(e);
                peticion.setUsuario(nodoConvertido.getUsuario());
                nodoConvertido.agregarPeticion(peticion);
            }
        }
        return nodoConvertido;
    }

    public Element creaNodoPeticiones(EnPeticiones Peticiones) {
        Element valorDeRetorno = new Element("peticiones");
        valorDeRetorno.setAttribute("usuario", Peticiones.getUsuario());
        valorDeRetorno.setAttribute("cantidad", String.valueOf(Peticiones.getNumero_peticiones()));
        for (EnPeticion pet : Peticiones.getPeticiones()) {
            valorDeRetorno.addContent(creaNodoPeticion(pet));
        }
        return valorDeRetorno;
    }

    public Element creaNodoPeticion(EnPeticion Peticion) {
        // <peticion fecha="06/09/2012" bloqueada="true">Baumgartner</peticion>
        Element valorDeRetorno = new Element("peticion");
        valorDeRetorno.setAttribute("fecha", Funciones.Date2ShortDateString(Peticion.getFecha_peticion()));
        valorDeRetorno.setAttribute("bloqueada", String.valueOf(Peticion.isBloqueada()));
        valorDeRetorno.setText(Peticion.getUsuario_peticion());
        return valorDeRetorno;
    }

    /**
     * Crea un objeto que contiene solo la información básica, no contiene la
     * lista de las peticiones.
     *
     * @param RaizDeNodo El nodo a partir del cual se desea armar el objeto
     * @return El objeto que contiene a la información del nodo
     */
    public EnPeticiones creaObjetoPeticionesSencillo(Element RaizDeNodo) {
        EnPeticiones nodoConvertido = new EnPeticiones(RaizDeNodo.getAttributeValue("usuario"));
        nodoConvertido.setNumero_peticiones(Integer.parseInt(RaizDeNodo.getAttributeValue("cantidad")));
        return nodoConvertido;
    }

    /**
     *
     * @param node
     * @return
     */
    public EnPeticion creaObjetoPeticion(Element node) {
        EnPeticion NodoConvertido = new EnPeticion();
        NodoConvertido.setBloqueada(Boolean.parseBoolean(node.getAttributeValue("bloqueada")));
        NodoConvertido.setFecha_peticion(Funciones.ShortDateString2Date(node.getAttributeValue("fecha")));
        NodoConvertido.setUsuario_peticion(node.getText());
        return NodoConvertido;
    }
}
