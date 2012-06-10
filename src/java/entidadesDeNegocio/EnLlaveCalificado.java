package entidadesDeNegocio;

import org.json.simple.JSONObject;

/**
 * Clase que representa cada una de las calificaciones otorgadas a cada mensaje,
 * depende directamente de
 * <code>EnMensajeCalificado</code>, contiene la calificación y el calificador
 *
 * @author fferegrino
 */
public class EnLlaveCalificado {

    private String calificador;
    private String idMensaje;
    private double calificacion;

    public EnLlaveCalificado(String calificador, double calificacion, String idMensaje) {
        this.calificador = calificador;
        this.calificacion = calificacion;
        this.idMensaje = idMensaje;
    }

    public EnLlaveCalificado() {
    }

    public String getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(String idMensaje) {
        this.idMensaje = idMensaje;
    }    
    
    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    public String getCalificador() {
        return calificador;
    }

    public void setCalificador(String calificador) {
        this.calificador = calificador;
    }

    /**
     * Función que compara dos objetos, dos objetos son iguales si los calificadores son iguales
     * @param o El objeto a comparar
     * @return <code>true<code> si los calificadores son iguales
     */
    public boolean equals(Object o) {
        boolean esIgual = false;
        if (o instanceof EnLlaveCalificado) {
            EnLlaveCalificado elc = (EnLlaveCalificado) o;
            if (elc.calificador.equals(this.calificador) /*&& elc.idMensaje.equals(this.idMensaje)*/) {
                esIgual = true;
            }
        }
        return esIgual;
    }
    
    public JSONObject toJSONObject(){
        JSONObject  object = new JSONObject();
        object.put("calificacion", this.calificacion);
        object.put("calificador",this.calificador);
        return object;
    }
}
