package entidadesDeNegocio;

import java.util.ArrayList;

/**
 *
 * @author fferegrino
 */
public class EnCalificados {

    private String usuario;
    private ArrayList<EnLlaveCalificados> mensajes;
    private int favs;

    public EnCalificados(String usuario) {
        this.usuario = usuario;
        this.favs = 0;
        mensajes = new ArrayList<EnLlaveCalificados>();
    }

    /**
     * El mensaje a favear, si ya existe, no es agregado
     *
     * @param enLlaveCalificados
     */
    public void addFaveado(EnLlaveCalificados enLlaveCalificados) {
        if (!this.mensajes.contains(enLlaveCalificados)) {
            this.mensajes.add(enLlaveCalificados);
            this.favs++;
        }
    }

    public int getFavs() {
        return favs;
    }

    public ArrayList<EnLlaveCalificados> getMensajes() {
        return mensajes;
    }

    public String getUsuario() {
        return usuario;
    }
}
