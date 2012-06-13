package entidadesDeNegocio;

import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Esta clase representa las calificaciones que se han otorgado a cierto
 * mensaje, deberá existir calificaciones para todo mensaje, contiene el id del
 * mensaje tanto relativo como absoluto, el numero de calificadores, los
 * calificadores y la calificación promedio otorgada al mensaje
 *
 * @author fferegrino
 */
public class EnMensajeCalificado {

    private Long idUnico;
    private String idRelativo;
    private double calificacion;
    private ArrayList<EnLlaveCalificado> usuariosFaveadores;
    private int calificadores;

    public EnMensajeCalificado(Long idUnico, String idRelativo) {
        this.idUnico = idUnico;
        this.idRelativo = idRelativo;
        this.usuariosFaveadores = new ArrayList<EnLlaveCalificado>();
        this.calificadores = 0;
        this.calificacion = 0;
    }

    /**
     * Para agregar la calificación a un mensaje
     * @param elc La calificacion que se va a agregar, si ya existe, no se agrega :C
     */
    public void addCalificacion(EnLlaveCalificado elc) {
        if (!this.usuariosFaveadores.contains(elc)) {
            this.usuariosFaveadores.add(elc);
            this.calificacion += elc.getCalificacion();
            this.calificadores++;
        }
    }

    public int getFaveadores() {
        return calificadores;
    }

    public String getIdRelativo() {
        return idRelativo;
    }

    public Long getIdUnico() {
        return idUnico;
    }

    public double getCalificacion() {
        double res = 0;
        if (calificadores == 0) {
            res = 0;
        } else {
            res = calificacion / calificadores;
        }
        return res;
    }

    public ArrayList<EnLlaveCalificado> getUsuariosFaveadores() {
        return usuariosFaveadores;
    }
    
    public JSONObject toJSONObject(){
        JSONObject respuesta = new JSONObject();
        respuesta.put("unique_id",this.idUnico);
        respuesta.put("id",this.idRelativo);
        respuesta.put("id",this.idRelativo);
        respuesta.put("calificacion",this.getCalificacion());
        respuesta.put("calificadores",this.calificadores);
        JSONArray calificaciones = new JSONArray();
        for(EnLlaveCalificado llaveCalificado : this.usuariosFaveadores){
            calificaciones.add(llaveCalificado.toJSONObject());
        }
        respuesta.put("calificaciones", calificaciones);
        return respuesta;
    }
}
