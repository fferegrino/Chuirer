package entidadesDeNegocio;

import java.util.Date;

/**
 *
 * @author Antonio
 */
public class EnEstadisticas {
    private String username;
    private Integer mensajes;
    private Integer seguidos;
    private Integer seguidores;
    private Integer destacados;
    private Integer grupos;
    private Date ultimaActualizacion;

    public EnEstadisticas(String username, Integer mensajes, Integer seguidos, Integer seguidores, Integer destacados) {
        this.username = username;
        this.mensajes = mensajes;
        this.seguidos = seguidos;
        this.seguidores = seguidores;
        this.destacados = destacados;
    }

    public Integer getGrupos() {
        return grupos;
    }

    public void setGrupos(Integer grupos) {
        this.grupos = grupos;
    }

    public Integer getDestacados() {
        return destacados;
    }

    public void setDestacados(Integer destacados) {
        this.destacados = destacados;
    }
    
    public EnEstadisticas() {
    }

    public Date getUltimaActualizacion() {
        return ultimaActualizacion == null ? new Date() : ultimaActualizacion;
    }

    public void setUltimaActualizacion(Date ultimaActualizacion) {
        this.ultimaActualizacion = ultimaActualizacion;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
