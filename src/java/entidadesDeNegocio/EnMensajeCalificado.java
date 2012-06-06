package entidadesDeNegocio;

import java.util.ArrayList;

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
    private int faveadores;

    public EnMensajeCalificado(Long idUnico, String idRelativo) {
        this.idUnico = idUnico;
        this.idRelativo = idRelativo;
        this.usuariosFaveadores = new ArrayList<EnLlaveCalificado>();
        this.faveadores = 0;
        this.calificacion = 0;
    }

    public void addUsuarioFaveador(EnLlaveCalificado elc) {
        if (!this.usuariosFaveadores.contains(elc)) {
            this.usuariosFaveadores.add(elc);
            this.calificacion += elc.getCalificacion();
            this.faveadores++;
        }
    }

    public int getFaveadores() {
        return faveadores;
    }

    public String getIdRelativo() {
        return idRelativo;
    }

    public Long getIdUnico() {
        return idUnico;
    }

    public double getCalificacion() {
        if (faveadores == 0) {
            calificacion = 0;
        } else {
            calificacion = calificacion / faveadores;
        }
        return calificacion;
    }

    public ArrayList<EnLlaveCalificado> getUsuariosFaveadores() {
        return usuariosFaveadores;
    }
}
