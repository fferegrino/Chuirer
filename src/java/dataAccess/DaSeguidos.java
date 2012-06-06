package dataAccess;

import chuirer.utilitarios.DocumentoXML;
import chuirer.utilitarios.Impresion;
import com.myapp.struts.Propiedades;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class DaSeguidos {
    
    private File rutaBuscarUsuario;
    private String seguidosXML;
    
    public DaSeguidos() {
        Properties props = new Propiedades().getProperties();
        this.rutaBuscarUsuario = new File(props.getProperty("rutaUsuarios"));
        this.seguidosXML = "seguidos.xml";
    }
    
    /**Agrega un seguido a un usuario determinado
     * 
     * @param username Éste usuario va a seguir a "nomSeguido"
     * @param nomSeguido
     */
    public void agregarSeguido (String username, String nomSeguido)
    {
        username = username.toLowerCase();
        File directorio = new File(rutaBuscarUsuario.getAbsolutePath() + "/" + username);
        SAXBuilder SB = new SAXBuilder();
        try{
            Document Usuarios =DocumentoXML.recuperaDocumento(directorio.getAbsolutePath() + "/" + seguidosXML);
            Element raiz = Usuarios.getRootElement();
            Element seguidoTag = new Element("seguido");
            seguidoTag.setText(nomSeguido);
            raiz.addContent(seguidoTag);
            
            Impresion.imprimeXML(Usuarios,directorio.getAbsolutePath() + "/" + seguidosXML);
        }catch (JDOMException ex) {}
        catch (IOException ex) {}
    }
    
    /**Elimina un seguido de un usuario determinado
     * 
     * @param username Es el usuario al cual se le va a eliminar un seguido
     * @param nomSeguido Es el nombre del seguido que se va a eliminar
     */
    public void eliminarSeguido (String username, String nomSeguido)
    {
        username = username.toLowerCase();
        File directorio = new File(rutaBuscarUsuario.getAbsolutePath() + "/" + username);
        SAXBuilder SB = new SAXBuilder();
        try{
            Document Usuarios = DocumentoXML.recuperaDocumento(directorio.getAbsolutePath() + "/" + seguidosXML);
            Element raiz = Usuarios.getRootElement();
            List<Element> listaSeguidos = raiz.getChildren("seguido");
            for (int i = 0; i < listaSeguidos.size(); i++)
            {
                Element seguido = listaSeguidos.get(i);
                if (seguido.getText().toUpperCase().equals(nomSeguido.toUpperCase()))
                {
                    listaSeguidos.remove(i);
                }
            }
            Impresion.imprimeXML(Usuarios, directorio.getAbsolutePath() + "/" + seguidosXML);
            
        }catch (JDOMException ex) {}
        catch (IOException ex) {}
    }
    
    public ArrayList<String> obtenerSeguidos(String username) throws IOException
    {
        ArrayList<String> seguidosRetorno = new ArrayList();
        username = username.toLowerCase();
        File directorio = new File(rutaBuscarUsuario.getAbsolutePath() + "/" + username);
        SAXBuilder SB = new SAXBuilder();
        try{
            Document Usuarios = DocumentoXML.recuperaDocumento(directorio.getAbsolutePath() + "/" + seguidosXML);
            Element raiz = Usuarios.getRootElement();
            List<Element> listaSeguidos = raiz.getChildren("seguido");
            for (int i = 0; i < listaSeguidos.size(); i++)
            {
                Element seguido = listaSeguidos.get(i);
                seguidosRetorno.add(seguido.getText());
                
            }
            Impresion.imprimeXML(Usuarios, directorio.getAbsolutePath() + "/" + seguidosXML);
            
        }catch (JDOMException ex) {}
        
        return seguidosRetorno;
    }
}
