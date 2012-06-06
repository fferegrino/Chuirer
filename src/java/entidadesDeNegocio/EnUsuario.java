package entidadesDeNegocio;

import java.util.Date;

/**
 * Entidad de negocio que representa a un usuario
 *
 * @author fferegrino
 */
public class EnUsuario {

    private String userName;
    private String rol;
    private String email;
    private String nombreReal;
    private String apellidos;
    private String password;
    private String dscripcion;
    private String url;
    private String imgUrl;
    private Integer seguidos;
    private Integer seguidores;
    private Integer mensajes;
    private Date fechaRegistro;
    private Boolean perfilPrivado;

    /**
     * Constructor genérico de la clase usuario
     */
    public EnUsuario() {
    }

    /**
     * Constructor completo de la clase ususario, recibe todos los parámetros
     *
     * @param userName
     * @param rol
     * @param email
     * @param nombreReal
     * @param apellidos
     * @param password
     * @param descripcion
     */
    public EnUsuario(String userName, String rol, String email, String nombreReal, String apellidos, String password, String descripcion, String url, String imgUrl) {
        this.userName = userName;
        this.rol = rol;
        this.email = email;
        this.nombreReal = nombreReal;
        this.apellidos = apellidos;
        this.password = password;
        this.url = url;
        this.imgUrl = imgUrl;
    }

    public Integer getMensajes() {
        return mensajes;
    }

    public void setMensajes(Integer mensajes) {
        this.mensajes = mensajes;
    }

    public Integer getSeguidores() {
        return seguidores;
    }

    public void setSeguidores(Integer seguidores) {
        this.seguidores = seguidores;
    }

    public Integer getSeguidos() {
        return seguidos;
    }

    public void setSeguidos(Integer seguidos) {
        this.seguidos = seguidos;
    }
    
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombreReal() {
        return nombreReal;
    }

    public void setNombreReal(String nombreReal) {
        this.nombreReal = nombreReal;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDscripcion() {
        return dscripcion;
    }

    public void setDscripcion(String dscripcion) {
        this.dscripcion = dscripcion;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Boolean getPerfilPrivado() {
        return perfilPrivado;
    }

    public void setPerfilPrivado(Boolean perfilPrivado) {
        this.perfilPrivado = perfilPrivado;
    }
    
    /**
     * Método para comparar si son iguales dos Usuarios
     *
     * @param Object El usuario contra el que queremos comparar
     * @return
     * <code>true</code> si son iguales en el nombre de usuario,
     * <code>false</code> en otro caso
     */
    @Override
    public boolean equals(Object Object) {
        boolean sonIguales = false;
        if (Object instanceof EnUsuario) {
            EnUsuario usr = (EnUsuario) Object;
            if (usr.userName.toString().equals(this.userName.toUpperCase())) {
                sonIguales = true;
            }
        }
        return sonIguales;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.userName != null ? this.userName.hashCode() : 0);
        hash = 89 * hash + (this.rol != null ? this.rol.hashCode() : 0);
        hash = 89 * hash + (this.email != null ? this.email.hashCode() : 0);
        hash = 89 * hash + (this.nombreReal != null ? this.nombreReal.hashCode() : 0);
        hash = 89 * hash + (this.apellidos != null ? this.apellidos.hashCode() : 0);
        hash = 89 * hash + (this.password != null ? this.password.hashCode() : 0);
        return hash;
    }
}
