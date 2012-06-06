package dataAccess;

import chuirer.utilitarios.DocumentoXML;
import chuirer.utilitarios.Impresion;
import com.myapp.struts.Propiedades;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class DaSeguidores {
    
    private File rutaBuscarUsuario;
    private String seguidoresXML;
    
    public DaSeguidores() {
        Properties props = new Propiedades().getProperties();
        this.rutaBuscarUsuario = new File(props.getProperty("rutaUsuarios"));
        this.seguidoresXML = "seguidores.xml";
    }
    
    /**
     * Agrega un seguidor para un usuario determinado
     * 
     * @param username Representa el usuario al cual se le va a agregar un seguidor
     * @param seguidor Representa el usuario que quiere seguir a <code>username</code>
     */
    public void agregarSeguidor(String username, String seguidor)
    {
        username = username.toLowerCase();
        File directorio = new File(rutaBuscarUsuario.getAbsolutePath() + "/" + username);
        SAXBuilder SB = new SAXBuilder();
        try{
            Document Usuarios = SB.build(directorio.getAbsolutePath() + "/" + seguidoresXML);
            Element raiz = Usuarios.getRootElement();
            Element seguidorTag = new Element("seguidor");
            seguidorTag.setText(seguidor);
            raiz.addContent(seguidorTag);
            Impresion.imprimeXML(Usuarios, directorio.getAbsolutePath() + "/" + seguidoresXML);
        }catch (JDOMException ex) {}
        catch (IOException ex) {}
    }
    
    /**
     * Elimina un seguidor de un usuario determinado
     * 
     * @param username Representa el usuario al cual se le va a eliminar un seguidor
     * @param seguidor Representa el usuario que se quiere borrar
     */
    public void eliminarSeguidor(String username, String seguidor)
    {
        username = username.toLowerCase();
        File directorio = new File(rutaBuscarUsuario.getAbsolutePath() + "/" + username);
        SAXBuilder SB = new SAXBuilder();
        try{
            
            Document Usuarios = DocumentoXML.recuperaDocumento(directorio.getAbsolutePath() + "/" + seguidoresXML);
            Element raiz = Usuarios.getRootElement();
            List<Element> seguidores = raiz.getChildren("seguidor");
            for (int i = 0; i < seguidores.size(); i++)
            {
                Element seguidorTag = seguidores.get(i);
                if (seguidorTag.getText().toUpperCase().equals(seguidor.toUpperCase()))
                {
                    seguidores.remove(i);
                    break;
                }
            }
            Impresion.imprimeXML(Usuarios, directorio.getAbsolutePath() + "/" + seguidoresXML);
        }catch (JDOMException ex) {}
        catch (IOException ex) {}
    }
    
    public ArrayList<String> obtenerSeguidores(String username) throws IOException
    {
        ArrayList<String> seguidoresRetorno = new ArrayList();
        username = username.toLowerCase();
        File directorio = new File(rutaBuscarUsuario.getAbsolutePath() + "/" + username);
        SAXBuilder SB = new SAXBuilder();
        try{
            Document Usuarios = DocumentoXML.recuperaDocumento(directorio.getAbsolutePath() + "/" + seguidoresXML);
            Element raiz = Usuarios.getRootElement();
            List<Element> listaSeguidores = raiz.getChildren("seguidor");
            for (int i = 0; i < listaSeguidores.size(); i++)
            {
                Element seguido = listaSeguidores.get(i);
                seguidoresRetorno.add(seguido.getText());
                
            }
            Impresion.imprimeXML(Usuarios, directorio.getAbsolutePath() + "/" + seguidoresXML);
        }catch (JDOMException ex) {}
        
        return seguidoresRetorno;
    }
}
