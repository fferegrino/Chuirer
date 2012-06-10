package dataAccess;

import chuirer.utilitarios.Funciones;
import chuirer.utilitarios.Impresion;
import com.myapp.struts.Propiedades;
import entidadesDeNegocio.EnUsuario;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.CDATA;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * Clase de acceso a los datos de usuario
 *
 * @author fferegrino
 */
public class DaUsuarios {

    private File rutaBuscarUsuario;
    private String archivoDatosUsuario;
    private String archivoStats;
    private String archivoSeguidos;
    private String carpetaMensajes;
    private String carpetaFavoritos;
    private String archivoSeguidores;
    private String archivoPeticiones;
    private String archivoGrupos;

    /**
     * Constructor de la clase
     */
    public DaUsuarios() {
        Properties props = new Propiedades().getProperties();
        this.rutaBuscarUsuario = new File(props.getProperty("rutaUsuarios"));
        this.archivoDatosUsuario = "usuario.xml";
        this.archivoStats = "stats.xml";
        this.archivoSeguidos = "seguidos.xml";
        this.archivoSeguidores = "seguidores.xml";
        this.carpetaMensajes = "mensajes";
        this.carpetaFavoritos = "calificaciones";
        this.archivoPeticiones = "peticiones.xml";
        this.archivoGrupos = "miembroDe.xml";
    }

    /**
     * Valida la existencia de un usuario en el sistema
     *
     * @param userName Nombre de usuario
     * @param password La contraseña predefinida para dicho usuario
     * @return
     * <code>true</code> si existe y la contraseña es correcta,
     * <code>false</code> en otro caso.
     */
    public boolean validaUsuario(String userName, String password) {
        boolean valido = false;
        if (userName != null && !userName.isEmpty()) {
            String username = userName;
            username = username.toLowerCase();
            File directorio = new File(rutaBuscarUsuario.getAbsolutePath() + "/" + username);
            if (directorio.exists()) {
                SAXBuilder SB = new SAXBuilder();
                try {
                    Document Usuarios = SB.build(directorio.getAbsolutePath() + "/" + archivoDatosUsuario);
                    Element raiz = Usuarios.getRootElement();
                    List<Element> usuarios = raiz.getChildren("usuario");
                    for (Element usuario : usuarios) {
                        if (usuario.getChild("username").getText().toUpperCase().equals(userName.toUpperCase())) {
                            String pass;
                            try {
                                pass = Funciones.md5(password);
                            } catch (Exception e) {
                                return false;
                            }
                            if (usuario.getChild("pass").getText().equals(pass)) {
                                valido = true;
                                break;
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
        return valido;
    }

    /**
     * Recupera un usuario especifico, con todas sus propiedades
     *
     * @param userName El nombre del usuario que se desea recuperar
     * @return Un objeto del tipo
     * <code>EnUsuario</code>, conteniendo las propiedades del mismo tal y como
     * están en los registros de la aplicación, regresa null en caso de que no
     * exista en los registros
     */
    public EnUsuario recuperaUsuario(String userName) {
        EnUsuario usuarioDeRetorno = null;
        if (userName != null && !userName.isEmpty()) {
            File directorio = new File(rutaBuscarUsuario.getAbsolutePath() + "/" + userName);
            if (directorio.exists()) {
                SAXBuilder SB = new SAXBuilder();
                try {
                    Document Usuarios = SB.build(directorio.getAbsolutePath() + "/" + archivoDatosUsuario);
                    Element raiz = Usuarios.getRootElement();
                    List<Element> usuarios = raiz.getChildren("usuario");
                    for (Element usuario : usuarios) {
                        if (usuario.getChildText("username").toUpperCase().equals(userName.toUpperCase())) {
                            usuarioDeRetorno = this.creaObjetoUsuario(usuario);
                            break;
                        } else {
                            usuarioDeRetorno = null;
                        }
                    }
                } catch (JDOMException ex) {
                    Logger.getLogger(DaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(DaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        return usuarioDeRetorno;
    }

    /**
     * Guarda un usuario en los archivos del servidor
     *
     * @param Usuario El objeto Usuario que debe ser guardado en la base XML
     * @return
     * <code>true</code> si el usuario se guardó correctamente,
     * <code>false</code> en otro caso
     */
    public boolean guardaUsuario(EnUsuario Usuario) {
        boolean guardado = false;
        if (Usuario != null) {

            if (!Funciones.cadenaNulaOVacia(Usuario.getUserName())
                    && !Funciones.cadenaNulaOVacia(Usuario.getPassword())) {
                if (this.recuperaUsuario(Usuario.getUserName()) != null) {
                    return false;
                }
                File directorio = new File(rutaBuscarUsuario.getAbsolutePath() + "/" + Usuario.getUserName());
                try {
                    if (!directorio.exists()) {
                        directorio.mkdir();
                        creaArvchivoDeUsuarios(directorio);
                        creaArchivoStatsUsuario(directorio);
                        creaArchivoSeguidores(directorio, Usuario.getUserName());
                        creaArchivoSeguidos(directorio, Usuario.getUserName());
                        creaArchivoPeticiones(directorio, Usuario.getUserName());
                        creaArchivoGrupos(directorio, Usuario.getUserName());
                        File carpetaMsgs = new File(directorio.getAbsoluteFile() + "/" + carpetaMensajes);
                        carpetaMsgs.mkdir();
                    }
                    SAXBuilder SAXB = new SAXBuilder();
                    Document archivoDeUsuarios = SAXB.build(directorio.getAbsolutePath() + "\\" + archivoDatosUsuario);
                    Element root = archivoDeUsuarios.getRootElement();
                    Usuario.setFechaRegistro(new Date());
                    root.addContent(this.creaNodoUsuario(Usuario));
                    Impresion.imprimeXML(archivoDeUsuarios, directorio.getAbsolutePath() + "\\" + archivoDatosUsuario);
                    //Crear archivo de estadísticas
                    Document estats = SAXB.build(directorio.getAbsolutePath() + "/" + archivoStats);
                    estats.detachRootElement();
                    estats.setRootElement(creaNodoStats(Usuario.getUserName()));
                    Impresion.imprimeXML(estats, directorio.getAbsolutePath() + "\\" + archivoStats);
                    guardado = true;
                } catch (IOException ioe) {
                } catch (JDOMException jdome) {
                }
            }
        }

        return guardado;
    }

    /**
     * Borra un usuario de los archivos del servidor
     *
     * @param username El username del usuario a borrar
     * @return
     * <code>true</code> si se borró al usuario,
     * <code>false</code> en otro caso.
     */
    public boolean borraUsuario(String username) {
        boolean borrado = false;
        if (!Funciones.cadenaNulaOVacia(username)) {
            if (this.recuperaUsuario(username) != null) {
                File directorio = new File(rutaBuscarUsuario.getAbsolutePath() + "/" + username);
                if (directorio.exists()) {
                    for (File f : directorio.listFiles()) {
                        f.delete();
                    }
                    directorio.delete();
                    borrado = true;
                }
            }

        }

        return borrado;
    }

    public boolean actualizaUsuario(EnUsuario Usuario, File ImagenDePerfil) {
        return this.actualizaUsuario(Usuario);
    }

    /**
     * Regresa un arreglo de usuarios cuyo nombre inicia con las letras pasadas
     *
     * @param username El nombre o parte del nombre que se busca encontrar
     * @return Un arreglo que contiene a los usuarios que coincidieron con el
     * criterio de búsqueda
     */
    public ArrayList<EnUsuario> regresaUsuarios(String username) {
        ArrayList<EnUsuario> ArregloDeRetorno = new ArrayList<EnUsuario>();
        File directorio = new File(rutaBuscarUsuario.getAbsolutePath());
        String[] usersDirs = directorio.list();
        for (String estiloSebastino : usersDirs) {
            if (estiloSebastino.startsWith(username)) {
                EnUsuario nuevo = this.recuperaUsuario(estiloSebastino);
                ArregloDeRetorno.add(nuevo);
            }
        }
        return ArregloDeRetorno;
    }

    /**
     * Acrualiza un usuario, con las propiedades que traiga el objeto. <b>Es
     * necesario que para evitar la pérdida de datos, el objeto contenga todos
     * los atributos, incluso los que no deseas actualizar</b>
     *
     * @param Usuario El usuario que contiene las propiedades a actualizar
     * @return
     * <code>true</code> si se actualizó correctamente,
     * <code>false</code> en otro caso
     */
    public boolean actualizaUsuario(EnUsuario Usuario) {
        boolean guardado = true;
        File directorio = new File(rutaBuscarUsuario.getAbsolutePath() + "/" + Usuario.getUserName());
        if (directorio.exists()) {
            SAXBuilder SB = new SAXBuilder();
            try {
                Document Usuarios = SB.build(directorio.getAbsolutePath() + "/" + archivoDatosUsuario);
                Usuarios.detachRootElement();
                Element usuarios = new Element("usuarios");
                usuarios.addContent(creaNodoUsuario(Usuario));
                Usuarios.setRootElement(usuarios);
                Impresion.imprimeXML(Usuarios, directorio.getAbsolutePath() + "\\" + archivoDatosUsuario);
            } catch (IOException ioe) {
            } catch (JDOMException jdome) {
            }
        }
        return guardado;
    }

    /**
     * Mapea los elementos de la clase usuario a un nodo 'usuario' en xml
     *
     * @param Usuario El objeto usuario a ser mapeado
     * @return Un objeto de la clase Element, que contiene como hijos a las
     * partes que componen a un usuario dentro de la aplicación
     */
    public Element creaNodoUsuario(EnUsuario Usuario) {
        Element usrTag = new Element("usuario");
        //<editor-fold defaultstate="collapsed" desc="Creacion de los Element">
        Element username = new Element("username");
        username.addContent(Usuario.getUserName());
        Element pass = new Element("pass");
        pass.addContent(Usuario.getPassword());
        Element rol = new Element("rol");
        rol.addContent(Usuario.getRol());
        Element nombreReal = new Element("realname");
        nombreReal.addContent(new CDATA(Usuario.getNombreReal()));
        Element apellido = new Element("lastname");
        Element email = new Element("email");
        if (Usuario.getEmail() != null) {
            email.addContent(Usuario.getEmail());
        }
        if (Usuario.getApellidos() != null) {
            apellido.addContent(new CDATA(Usuario.getApellidos()));
        }
        Element descr = new Element("desc");
        if (Usuario.getDscripcion() != null) {
            descr.addContent(new CDATA(Usuario.getDscripcion()));
        }
        Element fechaRegistro = new Element("fecha_registro");
        if (Usuario.getFechaRegistro() != null) {
            fechaRegistro.addContent(Funciones.Date2ShortDateString(Usuario.getFechaRegistro()));
        }
        Element url = new Element("url");
        if (Usuario.getUrl() != null) {
            url.addContent(Usuario.getUrl());
        }

        Element imgUrl = new Element("img_url");
        if (Usuario.getImgUrl() != null) {
            imgUrl.addContent(Usuario.getImgUrl());
        }

        Element perfil_privado = new Element("perfil_privado");
        if (Usuario.getPerfilPrivado() != null) {
            perfil_privado.addContent(Usuario.getPerfilPrivado().toString());
        }
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Adicion de los tags al Elemento raiz">
        usrTag.addContent(username);
        usrTag.addContent(pass);
        usrTag.addContent(email);
        usrTag.addContent(rol);
        usrTag.addContent(nombreReal);
        usrTag.addContent(apellido);
        usrTag.addContent(descr);
        usrTag.addContent(fechaRegistro);
        usrTag.addContent(url);
        usrTag.addContent(imgUrl);
        usrTag.addContent(perfil_privado);
        //</editor-fold>
        return usrTag;
    }

    private Element creaNodoSeguidos(String username) {
        Element stats = new Element("seguidos");
        stats.setAttribute("ID", username);
        return stats;
    }

    private Element creaNodoSeguidores(String username) {
        Element stats = new Element("seguidores");
        stats.setAttribute("ID", username);
        return stats;
    }

    private Element creaNodoStats(String username) {
        Element stats = new Element("estadisticas");
        stats.setAttribute("usuario", username);
        Element numero_mensajes = new Element("numero_mensajes");
        Element seguidos = new Element("seguidos");
        Element seguidores = new Element("seguidores");
        Element destacados = new Element("destacados");
        numero_mensajes.addContent("0");
        seguidos.addContent("0");
        seguidores.addContent("0");
        destacados.addContent("0");
        stats.addContent(numero_mensajes);
        stats.addContent(seguidos);
        stats.addContent(seguidores);
        stats.addContent(destacados);
        return stats;
    }

    /**
     * Realiza el mapeo general de un nodo
     * <code>usuario</code> a un objeto
     * <code>EnUsuario</code>
     *
     * @param nodoUsuario El nodo que contiene la información del usuario a
     * recuperar
     * @return Un objeto que contiene los datos recuperados
     */
    public EnUsuario creaObjetoUsuario(Element nodoUsuario) {
        EnUsuario valorDeRetorno = new EnUsuario();
        valorDeRetorno.setUserName(nodoUsuario.getChildText("username"));
        valorDeRetorno.setPassword(nodoUsuario.getChildText("pass"));
        valorDeRetorno.setApellidos(nodoUsuario.getChildText("lastname"));
        valorDeRetorno.setNombreReal(nodoUsuario.getChildText("realname"));
        valorDeRetorno.setRol(nodoUsuario.getChildText("rol"));
        valorDeRetorno.setEmail(nodoUsuario.getChildText("email"));
        valorDeRetorno.setDscripcion(nodoUsuario.getChildText("desc"));
        valorDeRetorno.setUrl(nodoUsuario.getChildText("url"));
        valorDeRetorno.setImgUrl(nodoUsuario.getChildText("img_url"));
        valorDeRetorno.setPerfilPrivado(Boolean.parseBoolean(nodoUsuario.getChildText("perfil_privado")));
        DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
        try {
            Date date = (Date) formatter.parse(nodoUsuario.getChildText("fecha_registro"));
            valorDeRetorno.setFechaRegistro(date);
        } catch (ParseException ex) {
        }
        return valorDeRetorno;
    }

    /*
     * PROBABLEMENTE ESTO VAYA EN OTRA CLASE
     */
    public boolean creaArvchivoDeUsuarios(File raiz) throws IOException {
        boolean vRegreso = false;
        File archivo = new File(raiz.getAbsolutePath() + "\\" + archivoDatosUsuario);
        Element usuarios = new Element("usuarios");
        Document f = new Document(usuarios);
        Format format = Format.getPrettyFormat();
        XMLOutputter xmloutputter = new XMLOutputter(format);
        FileOutputStream file_os = new FileOutputStream(archivo);
        xmloutputter.output(f, file_os);
        file_os.close();
        return vRegreso;
    }

    public boolean creaArchivoStatsUsuario(File raiz) throws IOException {
        boolean vRegreso = false;
        File archivo = new File(raiz.getAbsolutePath() + "\\" + archivoStats);
        Element usuarios = new Element("estadisticas");
        Document f = new Document(usuarios);
        Impresion.imprimeXML(f, archivo);
        return vRegreso;
    }

    public boolean creaArchivoSeguidos(File raiz, String username) throws IOException {
        boolean vRegreso = false;
        File archivo = new File(raiz.getAbsolutePath() + "\\" + archivoSeguidos);
        Element usuarios = new Element("seguidos");
        usuarios.setAttribute("usuario", username);
        Document f = new Document(usuarios);
        Impresion.imprimeXML(f, archivo);
        return vRegreso;
    }

    public boolean creaArchivoSeguidores(File raiz, String username) throws IOException {
        boolean vRegreso = false;
        File archivo = new File(raiz.getAbsolutePath() + "\\" + archivoSeguidores);
        Element usuarios = new Element("seguidores");
        usuarios.setAttribute("usuario", username);
        Document f = new Document(usuarios);
        Impresion.imprimeXML(f, archivo);
        return vRegreso;
    }

    public boolean creaArchivoPeticiones(File raiz, String username) throws IOException {
        boolean vRegreso = false;
        File archivo = new File(raiz.getAbsolutePath() + "\\" + archivoPeticiones);
        Element usuarios = new Element("peticiones");
        usuarios.setAttribute("usuario", username);
        usuarios.setAttribute("cantidad", "0");
        Document f = new Document(usuarios);
        Impresion.imprimeXML(f, archivo);
        return vRegreso;
    }

    public boolean creaArchivoGrupos(File raiz, String username) throws IOException {
        boolean vRegreso = false;
        File archivo = new File(raiz.getAbsolutePath() + "\\" + archivoGrupos);
        Element usuarios = new Element("miembroDe");
        Element contador=new Element("contador");
        contador.setText("0");
        usuarios.addContent(contador);
        Document f = new Document(usuarios);
        Impresion.imprimeXML(f, archivo);
        return vRegreso;
    }
    
       /**recuperaGrupoEnCarpetaUsuario
     * Recupera los id de todos los usuarios existentes en la aplicacion!!!
     *    
     * @return arrayList con todos los usuarios existentes
     * ok 300512 miguel
     */
    
      public static ArrayList allUsers() {
         String[] usersLeidos;
         ArrayList users=new ArrayList();        
        //acerca de la ruta de la carpeta usuarios
        Properties props = new Propiedades().getProperties();
        File rutaBuscarUsuario= new File(props.getProperty("rutaUsuarios"));
        
        //buscamos los archivos xml que contenga la carpeta "grupos"
        File directorio = new File(rutaBuscarUsuario.getAbsolutePath());
        if (directorio.exists()) {
            usersLeidos=directorio.list();//recupera los archivos que tenga
            for(String elemento:usersLeidos){
                users.add(elemento);                
            }
        } 
        
        return users;
    }
}
