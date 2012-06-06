package dataAccess;

import chuirer.utilitarios.Funciones;
import com.myapp.struts.Propiedades;
import entidadesDeNegocio.EnGrupo;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * Clase de acceso a a la informacion de los grupos.
 *
 * @author danFudo
 */
public class DaGrupos {

    private File rutaBuscarGrupo;
    private String archivoXML;


    /**
     * Constructor de la clase
     */
    public DaGrupos(String NOMBRE) {
        Properties props = new Propiedades().getProperties();
        this.rutaBuscarGrupo = new File(props.getProperty("rutaGrupos"));
        this.archivoXML = NOMBRE+".xml";
    }
    
    public DaGrupos() {
      
    }

    /**
     * Valida la existencia de un grupo en el sistema
     *
     * @param NOMBRE Nombre del grupo
     * @return
     * <code>true</code> si existe el grupo y el id coincide.
     * <code>false</code> en otro caso.
     *  OK 160512
     */
    public static boolean validaGrupo(String NOMBRE) {
         String[] gruposLeidos;      
         int tamaño;
         String aux;
        //acerca de la ruta de la carpeta usuarios
        Properties props = new Propiedades().getProperties();
        File rutaBuscarUsuario= new File(props.getProperty("rutaGrupos"));
        
        //buscamos los archivos xml que contenga la carpeta "grupos"
        File directorio = new File(rutaBuscarUsuario.getAbsolutePath());
        if (directorio.exists()) {
            gruposLeidos=directorio.list();//recupera los archivos que tenga
            for(String elemento:gruposLeidos){                
                tamaño=elemento.length()-4;
                //corta el .xml del string
                aux=elemento.substring(0, tamaño);
                
                //si el nombre ingresado concide con uno existente
                if(aux.equals(NOMBRE))
                    return true; 
            }
        } 
        return false; 
    }

    /**
     * Recupera un grupo especifico, con todas sus propiedades
     *
     * @param NOMBRE El nombre del grupo que se desea recuperar
     * @return Un objeto del tipo
     * <code>EnGrupo</code>, conteniendo las propiedades del mismo tal y como
     * están en los registros de la aplicación, regresa null en caso de que no
     * exista en los registros
     *  OK 160512
     */
    public  EnGrupo recuperaGrupo(String NOMBRE) {
        EnGrupo GrupoDeRetorno = null;
        if (NOMBRE != null && !NOMBRE.isEmpty()) {
            File directorio = new File(rutaBuscarGrupo.getAbsolutePath() + "/" +NOMBRE+".xml");
            if (directorio.exists()) {
                GrupoDeRetorno = new EnGrupo();
                SAXBuilder SB = new SAXBuilder();
                try {
                    Document Grupo = SB.build(directorio.getAbsolutePath());
                    Element raiz = Grupo.getRootElement();
                    List<Element> hijos = raiz.getChildren();
                    for (Element hijo : hijos) {
                        if (hijo.getName().equals("Nombre"))
                        {
                            GrupoDeRetorno.setNOMBRE(hijo.getValue());
                        }
                        else if (hijo.getName().equals("Creador"))
                        {
                            GrupoDeRetorno.setCREADOR(hijo.getValue());
                        }
                        else if (hijo.getName().equals("Descripcion"))
                        {
                            GrupoDeRetorno.setDESCRIPCION(hijo.getValue());
                        }
                        else if (hijo.getName().equals("NumUsuarios"))
                        {
                            GrupoDeRetorno.setNUM_USER(Integer.parseInt(hijo.getValue()));
                        }
                        else if (hijo.getName().equals("Usuarios"))
                        {
                            List<Element> nietos = hijo.getChildren();
                            ArrayList Usuarios = new ArrayList();
                            for (Element nieto : nietos) {
                                if (nieto.getName().equals("Usuario"))
                                {
                                    Usuarios.add(nieto.getValue());
                                }
                            }
                            GrupoDeRetorno.setUSERS(Usuarios);
                        }
                    }
                } catch (JDOMException ex) {
                    Logger.getLogger(DaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(DaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        return GrupoDeRetorno;
    }

    /**
     * Guarda un Grupo en los archivos del servidor
     *
     * @param Grupo El objeto Grupo que debe ser guardado en la base XML
     * @return
     * <code>true</code> si el Grupo se guardó correctamente,
     * <code>false</code> en otro caso
     *  OK 160512
     */
    public boolean guardaGrupo(EnGrupo Grupo) throws JDOMException {
        boolean guardado = false;
        if (Grupo != null) {
			
            if (!Funciones.cadenaNulaOVacia(Grupo.getNOMBRE())
				&& !Funciones.cadenaNulaOVacia(Grupo.getCREADOR())) {
                if (this.recuperaGrupo(Grupo.getNOMBRE()) != null) {
                    return false;
                }
                
                File directorio = new File(rutaBuscarGrupo.getAbsolutePath() + "/" + archivoXML);
                
                try {
                    
                    Element raiz;
                    Element Nombre;
                    Element Creador;
                    Element Descripcion;
                    Element NumUsuarios;
                    Element Usuarios;
                    Element Usuario;
                    
                    raiz = new Element ("Grupo");
                    
                    Nombre = new Element ("Nombre");
                    Nombre.setText(Grupo.getNOMBRE());
                    raiz.addContent(Nombre);
                    
                    Creador = new Element ("Creador");
                    Creador.setText(Grupo.getCREADOR());
                    raiz.addContent(Creador);
                    
                    Descripcion = new Element ("Descripcion");
                    Descripcion.setText(Grupo.getDESCRIPCION());
                    raiz.addContent(Descripcion);
                    
                    NumUsuarios = new Element ("NumUsuarios");
                    NumUsuarios.setText(""+Grupo.getNUM_USER());
                    raiz.addContent(NumUsuarios);
                    
                    Usuarios = new Element ("Usuarios");
                    
                    for(int i = 0; i < Grupo.getUSERS().size(); i++)
                    {
			Usuario = new Element ("Usuario");
                        Usuario.setText((String)Grupo.getUSERS().get(i));
                        Usuarios.addContent(Usuario);
                    }
                    
                    raiz.addContent(Usuarios);
                    
                    
                    Document newdoc=new Document(raiz);
                    Format format = Format.getPrettyFormat();
                    XMLOutputter fmt=new XMLOutputter(format);
                    FileWriter writer=new FileWriter(directorio);
                    fmt.output(newdoc,writer);                   
                    writer.flush();   
                    writer.close();                    
                    guardado = true;
                    
                    
                } catch (IOException ioe) { 
		  guardado=false;			
		}
	}
        }
		
        return guardado;
    }

    /**
     * Borra un Grupo de los archivos del servidor
     *
     * @param NOMBRE El nombre del grupo a borrar
     * @return
     * <code>true</code> si se borró al grupo,
     * <code>false</code> en otro caso.
     *  OK 160512
     */
    public boolean borraGrupo(String NOMBRE) {
        boolean borrado = false;
        if (!Funciones.cadenaNulaOVacia(NOMBRE)) {
            if (this.recuperaGrupo(NOMBRE) != null) {
                File directorio = new File(rutaBuscarGrupo.getAbsolutePath() + "/" + NOMBRE+".xml");
                if (directorio.exists()) {                   
                    directorio.delete();
                    borrado = true;
                }
                
            }

        }
        
        return borrado;
    }
    
    /**
     * Actualiza un Grupo en los archivos del servidor mediante un usuario nuevo, seguir Grupo
     *
     * @param Grupo El objeto Grupo que debe ser guardado en la base XML
     * @param ID_USER El objeto Grupo que debe ser guardado en la base XML
     * @return
     * <code>true</code> si el Grupo se actualizo correctamente,
     * <code>false</code> en otro caso
     *  OK 160512
     */
     public boolean actualizaGrupoUsuario(EnGrupo Grupo, String ID_USER) {
        boolean actualizado = false;
        if (Grupo != null) {
           
                File directorio = new File(rutaBuscarGrupo.getAbsolutePath() + "/" + archivoXML);
                
                try {
                    
                    Element raiz;
                    Element Nombre;
                    Element Creador;
                    Element Descripcion;
                    Element NumUsuarios;
                    Element Usuarios;
                    Element Usuario;
                    
                    raiz = new Element ("Grupo");
                    
                    Nombre = new Element ("Nombre");
                    Nombre.setText(Grupo.getNOMBRE());
                    raiz.addContent(Nombre);
                    
                    Creador = new Element ("Creador");
                    Creador.setText(Grupo.getCREADOR());
                    raiz.addContent(Creador);
                    
                    Descripcion = new Element ("Descripcion");
                    Descripcion.setText(Grupo.getDESCRIPCION());
                    raiz.addContent(Descripcion);
                    
                    NumUsuarios = new Element ("NumUsuarios");
                    Grupo.setNUM_USER(Grupo.getNUM_USER()+1);//se aumenta 1 al numero de integrantes
                    NumUsuarios.setText(""+Grupo.getNUM_USER());
                    raiz.addContent(NumUsuarios);
                    
                    Usuarios = new Element ("Usuarios");
                    
                    for(int i = 0; i < Grupo.getUSERS().size(); i++)
                    {
			Usuario = new Element ("Usuario");
                        Usuario.setText((String)Grupo.getUSERS().get(i));
                        Usuarios.addContent(Usuario);
                    }
                    
                    
                    ///agrego el nuevo id a la entidad
                    Grupo.setUSER(ID_USER);
                    
                    Usuario = new Element ("Usuario");
                    Usuario.setText(ID_USER);
                    Usuarios.addContent(Usuario);
                    
                    raiz.addContent(Usuarios);
                    
                    
                    Document newdoc=new Document(raiz);
                    Format format = Format.getPrettyFormat();
                    XMLOutputter fmt=new XMLOutputter(format);
                    FileWriter writer=new FileWriter(directorio);
                    fmt.output(newdoc,writer);
                    writer.flush();
                    writer.close();
                    actualizado = true;
                    
                } catch (IOException ioe) {

            }
    }
    return actualizado;
}
     
/**
     * elimina un id de usuario en los archivos del servidor de un grupo en especifico,dejar de Seguir
     *
     * @param Grupo El objeto Grupo que debe ser guardado en la base XML
     * @param ID_USER El identificador del usuario a dar de baja en el grupo ingresado
     * @return
     * <code>true</code> si el Grupo se actualizo correctamente,
     * <code>false</code> en otro caso
     * ok 18 05 12
     */
public boolean eliminaGrupoUsuario(EnGrupo Grupo, String ID_USER) {
        boolean actualizado = false;
        if (Grupo != null) {
           
                File directorio = new File(rutaBuscarGrupo.getAbsolutePath() + "/" + archivoXML);
                
                try {
                    
                    Element raiz;
                    Element Nombre;
                    Element Creador;
                    Element Descripcion;
                    Element NumUsuarios;
                    Element Usuarios;
                    Element Usuario;
                    
                    raiz = new Element ("Grupo");
                    
                    Nombre = new Element ("Nombre");
                    Nombre.setText(Grupo.getNOMBRE());
                    raiz.addContent(Nombre);
                    
                    Creador = new Element ("Creador");
                    Creador.setText(Grupo.getCREADOR());
                    raiz.addContent(Creador);
                    
                    Descripcion = new Element ("Descripcion");
                    Descripcion.setText(Grupo.getDESCRIPCION());
                    raiz.addContent(Descripcion);
                    
                    NumUsuarios = new Element ("NumUsuarios");
                    Grupo.setNUM_USER(Grupo.getNUM_USER()-1);//se disminuye 1 al numero de integrantes
                    NumUsuarios.setText(""+Grupo.getNUM_USER());
                    raiz.addContent(NumUsuarios);
                    
                    Usuarios = new Element ("Usuarios");
                    int borrar=0;//indice para saber que elemento de la entidad borrar
                    
                    for(int i = 0; i < Grupo.getUSERS().size(); i++)
                    {			
                        //Si el usuario es distinto al ingresado
                        if(!Grupo.getUSERS().get(i).equals(ID_USER)){
                            Usuario = new Element ("Usuario");
                            Usuario.setText((String)Grupo.getUSERS().get(i));
                            Usuarios.addContent(Usuario);
                        }
                        else{
                            borrar=i;
                        }
                    }
                    
                    //elimino el usuario de la entidad
                    Grupo.eliminaUSER(borrar);                    

                    //agrego raiz ya sin el usuario a eliminar
                    raiz.addContent(Usuarios);
                    
                    
                    Document newdoc=new Document(raiz);
                    Format format = Format.getPrettyFormat();
                    XMLOutputter fmt=new XMLOutputter(format);
                    FileWriter writer=new FileWriter(directorio);
                    fmt.output(newdoc,writer);
                    writer.flush();
                    writer.close();
                    actualizado = true;
                    
                } catch (IOException ioe) {

            }
    }
    return actualizado;
}
     
/**
     * Actualiza un Grupo en los archivos del servidor mediante una descripcion nueva
     * NOTA: El usuario que modifica la descripcion, debe de ser el usuario creaor
     *
     * @param Grupo El objeto Grupo que debe ser guardado en la base XML
     * @param ID_USER El objeto Grupo que debe ser guardado en la base XML
     * @return
     * <code>true</code> si el Grupo se actualizo correctamente,
     * <code>false</code> en otro caso
     *  OK 160512
     */
     public boolean actualizaGrupoDescripcion(EnGrupo Grupo, String ID_USER, String DESCRIP) {
        boolean actualizado = false;
        if (Grupo != null) {
           
                if(Grupo.getCREADOR().equals(ID_USER))
                {
                    File directorio = new File(rutaBuscarGrupo.getAbsolutePath() + "/" + archivoXML);
                    try {

                        Element raiz;
                        Element Nombre;
                        Element Creador;
                        Element Descripcion;
                        Element NumUsuarios;
                        Element Usuarios;
                        Element Usuario;

                        raiz = new Element ("Grupo");

                        Nombre = new Element ("Nombre");
                        Nombre.setText(Grupo.getNOMBRE());
                        raiz.addContent(Nombre);

                        Creador = new Element ("Creador");
                        Creador.setText(Grupo.getCREADOR());
                        raiz.addContent(Creador);

                        Descripcion = new Element ("Descripcion");
                        Descripcion.setText(DESCRIP);
                        raiz.addContent(Descripcion);

                        NumUsuarios = new Element ("NumUsuarios");
                        NumUsuarios.setText(""+Grupo.getNUM_USER());
                        raiz.addContent(NumUsuarios);

                        Usuarios = new Element ("Usuarios");

                        for(int i = 0; i < Grupo.getUSERS().size(); i++)
                        {
                            Usuario = new Element ("Usuario");
                            Usuario.setText((String)Grupo.getUSERS().get(i));
                            Usuarios.addContent(Usuario);
                        }

                        raiz.addContent(Usuarios);


                        Document newdoc=new Document(raiz);
                        Format format = Format.getPrettyFormat();
                        XMLOutputter fmt=new XMLOutputter(format);
                        FileWriter writer=new FileWriter(directorio);
                        fmt.output(newdoc,writer);
                        writer.flush();
                        writer.close();
                        actualizado = true;

                    } catch (IOException ioe) {

                    }
                }
                
    }
    return actualizado;
}
     
/**
     * Recupera usuarios de un grupo especifico
     * NOTA: Metodo recomendable para enviar mensajes grupales.
     *
     * @param NOMBRE El nombre del grupo que se desea recuperar
     * @return Un ArrayList del tipo
     * <code>EnGrupo</code>, conteniendo las propiedades del mismo tal y como
     * están en los registros de la aplicación, regresa null en caso de que no
     * exista en los registros
     * OK 160512
     */
    public ArrayList recuperaUsuariosGrupo(String NOMBRE) {
        ArrayList Usuarios = new ArrayList();
        if (NOMBRE != null && !NOMBRE.isEmpty()) {
            File directorio = new File(rutaBuscarGrupo.getAbsolutePath() + "/" + archivoXML);
            if (directorio.exists()) {
                SAXBuilder SB = new SAXBuilder();
                try {
                    Document Grupo = SB.build(directorio.getAbsolutePath() );
                    Element raiz = Grupo.getRootElement();
                    List<Element> hijos = raiz.getChildren();
                    for (Element hijo : hijos) {
                        
                        if (hijo.getName().equals("Usuarios"))
                        {
                            List<Element> nietos = hijo.getChildren();
                            for (Element nieto : nietos) {
                                if (nieto.getName().equals("Usuario"))
                                {
                                    Usuarios.add(nieto.getValue());
                                }
                            }
                        }
                    }
                } catch (JDOMException ex) {
                    Logger.getLogger(DaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(DaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        }
        
        return Usuarios;
    }
     
    /**agregaGrupoEnCarpetaUsuario
     * Agrega un id de Grupo al xml(grupos a que pertenece) dentro de la carpeta del usuario
     * NOTA:Al dare de alta un usuario a un grupo, se modifica el xml propio del Grupo y
     * el xml de grupos a que pertenece el usuario.
     *
     * @param idUsuario El nombre del usuario que se agrego al grupo
     *        idGrupo. Identificador del grupo al que se dio de alta el usuario
     * @return boolean de control    
     * ok 18 05 12
     */
    
      public static boolean agregaGrupoEnCarpetaUsuario(String idUsuario,String idGrupo) {
        boolean actualizado = false;
        //acerca de la ruta de la carpeta usuarios
        Properties props = new Propiedades().getProperties();
        File rutaBuscarUsuario= new File(props.getProperty("rutaUsuarios"));
        
        //buscamos ruta del usuario con id recibido
        File directorio = new File(rutaBuscarUsuario.getAbsolutePath() + "/" +idUsuario );
        if (directorio.exists()) {
            SAXBuilder SB = new SAXBuilder();
            try {
                //leemos el archivo xml que contiene los grupos a los que esta suscrito el usuario
                Document Grupo = SB.build(directorio.getAbsolutePath() + "/" + "miembroDe.xml");                
                Element raiz = Grupo.getRootElement();
                
                
                List<Element> hijos=raiz.getChildren();
                boolean existeGrupo=false;
                for(Element hijo:hijos){
                    if(hijo.getValue().equals(idGrupo))
                    {
                         existeGrupo=true;
                    }
                }
                
                if(!existeGrupo){
                    
                        //nodo a agregar
                        Element grupoNuevo=new Element("idGrupo");
                        grupoNuevo.setText(idGrupo);
                        raiz.addContent(grupoNuevo);

                        //modifica contador, aumenta en uno
                        Element cont=raiz.getChild("contador");
                        if(cont == null){
                            cont =  new Element("contador");
                            cont.setText("0");
                        }
                        int n=Integer.parseInt(cont.getValue());
                        n++;
                        cont.setText(""+n);
                        
                        //impresion del xml                  
                        Format format = Format.getPrettyFormat();
                        XMLOutputter fmt=new XMLOutputter(format);
                        FileWriter writer=new FileWriter(directorio.getAbsolutePath()+"/miembroDe.xml");
                        fmt.output(Grupo,writer);
                        writer.flush();
                        writer.close();
                }
                
                actualizado = true;

            } catch (IOException ioe) {
            } catch (JDOMException jdome) {
            }
            
        }
        return actualizado;
    }
      
     /**
      * elimnaGrupoEnCarpetaUsuario
     * elimina un id de Grupo al xml(grupos a que pertenece) dentro de la carpeta del usuario,dejar de Seguir
     * NOTA:Al dare de alta un usuario a un grupo, se modifica el xml propio del Grupo y
     * el xml de grupos a que pertenece el usuario.
     *
     * @param idUsuario El nombre del usuario que se agrego al grupo
     *        idGrupo. Identificador del grupo al que se dio de alta el usuario
     * @return boolean de control    
     * ok 18 05 12
     */ 
     public static boolean elimnaGrupoEnCarpetaUsuario(String idUsuario,String idGrupo) {
        boolean actualizado = false;
        //acerca de la ruta de la carpeta usuarios
        Properties props = new Propiedades().getProperties();
        File rutaBuscarUsuario= new File(props.getProperty("rutaUsuarios"));
        
        //buscamos ruta del usuario con id recibido
        File directorio = new File(rutaBuscarUsuario.getAbsolutePath() + "/" +idUsuario );
        if (directorio.exists()) {
            SAXBuilder SB = new SAXBuilder();
            try {
                //leemos el archivo xml que contiene los grupos a los que esta suscrito el usuario
                Document Grupo = SB.build(directorio.getAbsolutePath() + "/" + "miembroDe.xml");                
                Element raiz = Grupo.getRootElement();
                
                //si el grupo no esta agregado en el xml
                List<Element> hijos=raiz.getChildren();
                boolean existeGrupo=false;
                int indice=0,borrar=0;
               
                for(Element hijo:hijos){
                    
                    if(hijo.getValue().equals(idGrupo))
                    {                       
                       existeGrupo=true;
                       borrar=indice;
                    }
                   
                    indice++;
                }
                 
                if(existeGrupo){
                        //borra el nodo grupo  ingresado
                        hijos.remove(borrar); 
                        
                        //modifica contador, disminuye en uno
                        Element cont=raiz.getChild("contador");
                        if(cont == null){
                            cont =  new Element("contador");
                            cont.setText("0");
                        }
                        int n=Integer.parseInt(cont.getValue());
                        n--;
                        cont.setText(""+n);
                        
                        //impresion del xml                  
                        Format format = Format.getPrettyFormat();
                        XMLOutputter fmt=new XMLOutputter(format);
                        FileWriter writer=new FileWriter(directorio.getAbsolutePath()+"/miembroDe.xml");
                        fmt.output(Grupo,writer);
                        writer.flush();
                        writer.close();
                }
                
                actualizado = true;

            } catch (IOException ioe) {
            } catch (JDOMException jdome) {
            }
            
        }
        return actualizado;
    }
     
      /**recuperaGrupoEnCarpetaUsuario
     * Recupera los id de los grupos en los cuales esta inscrito el usuario
     *
     * @param idUsuario El nombre del usuario que se agrego al grupo
     *        idGrupo. Identificador del grupo al que se dio de alta el usuario
     * @return arrayList con el contador y los id de grpos    
     * ok 18 05 12
     */
    
      public static ArrayList recuperaGruposEnCarpetaUsuario(String idUsuario) {
         ArrayList datos=null;
        //acerca de la ruta de la carpeta usuarios
        Properties props = new Propiedades().getProperties();
        File rutaBuscarUsuario= new File(props.getProperty("rutaUsuarios"));
        
        //buscamos ruta del usuario con id recibido
        File directorio = new File(rutaBuscarUsuario.getAbsolutePath() + "/" +idUsuario );
        if (directorio.exists()) {
            SAXBuilder SB = new SAXBuilder();
            try {
                //leemos el archivo xml que contiene los grupos a los que esta suscrito el usuario
                Document Grupo = SB.build(directorio.getAbsolutePath() + "/" + "miembroDe.xml");                
                Element raiz = Grupo.getRootElement();
                
                
                List<Element> hijos=raiz.getChildren();
                datos=new ArrayList();//almacena numero con id de gpos a los que se unio
                for(Element hijo:hijos){
                    datos.add(hijo.getValue());
                }
                

            } catch (IOException ioe) {
            } catch (JDOMException jdome) {
            }
            
        }
        return datos;
    }
      
       /**recuperaGrupoEnCarpetaUsuario
     * Recupera los id de todos los grupos existentes en la aplicacion!!!
     *    
     * @return arrayList con todos los grupos existentes
     * ok 280512
     */
    
      public static ArrayList recuperaGruposExistentes() {
         String[] gruposLeidos;
         ArrayList grupos=new ArrayList();
         int tamaño;
        //acerca de la ruta de la carpeta usuarios
        Properties props = new Propiedades().getProperties();
        File rutaBuscarUsuario= new File(props.getProperty("rutaGrupos"));
        
        //buscamos los archivos xml que contenga la carpeta "grupos"
        File directorio = new File(rutaBuscarUsuario.getAbsolutePath());
        if (directorio.exists()) {
            gruposLeidos=directorio.list();//recupera los archivos que tenga
            for(String elemento:gruposLeidos){                
                tamaño=elemento.length()-4;
                //corta el .xml del string
                grupos.add(elemento.substring(0, tamaño));                
            }
        } 
        
        return grupos;
    }
      
        /**esSeguido
     * verifica si el id de grupo esta siendo seguido por el usuario invocador
     *@param idGpo El nombre del grupo que se quiere verificar si se sigue
     *@param idUser  El nombre del usuario que se desea verificar si sigue al grupo
     * @return boolean de verificacion
     * ok 280512
     */
    
      public static boolean esSeguido(String idGpo, String idUser) {
          ArrayList gposSeguidos=recuperaGruposEnCarpetaUsuario(idUser);
          
         //recorre el arraylist y compara
         for(int i=0;i<gposSeguidos.size();i++){
             if(gposSeguidos.get(i).equals(idGpo)){
                 return true;
             }             
         }
         return false;  
    }
     
}
