/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidadesDeNegocio;

/**
 *
 * @author HS-12
 */
public class EnLlaveCalificados {

    private String usuarioCalificador;
    private String idMensaje;
    private double calificacion;

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    public String getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(String idMensaje) {
        this.idMensaje = idMensaje;
    }

    public String getUsuarioCalificador() {
        return usuarioCalificador;
    }

    public void setUsuarioCalificador(String usuarioCalificador) {
        this.usuarioCalificador = usuarioCalificador;
    }

    public EnLlaveCalificados(String usuarioCalificador, String idMensaje, double calificacion) {
        this.usuarioCalificador = usuarioCalificador;
        this.idMensaje = idMensaje;
        this.calificacion = calificacion;
       
    }

    public EnLlaveCalificados() {
        this.calificacion = 0;
    }

    @Override
    public boolean equals(Object o) {
        boolean sonIguales = false;
        if (o instanceof EnLlaveCalificados) {
            EnLlaveCalificados elc = (EnLlaveCalificados) o;
            if (/*elc.usuarioCalificador.equals(this.usuarioCalificador) && */elc.idMensaje.equals(this.idMensaje)) {
                sonIguales = true;
            }
        }
        return sonIguales;
    }
}
