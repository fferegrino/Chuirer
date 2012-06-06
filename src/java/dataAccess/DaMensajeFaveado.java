/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataAccess;

import com.myapp.struts.Propiedades;
import entidadesDeNegocio.EnMensajeCalificado;
import java.io.File;
import java.util.Properties;

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
    
//    public EnMensajeFaveado(String mensaje){
//        
//    }
    
}
