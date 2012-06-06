package entidadesDeNegocio;
import java.util.ArrayList;

public class EnSeguidos {

    private String nomSeguidor;
    private ArrayList<String> seguidos;

    public EnSeguidos(String nomSeguidor, ArrayList<String> seguidos) {
        this.nomSeguidor = nomSeguidor;
        this.seguidos = seguidos;
    }

    public String getNomSeguidor() {
        return nomSeguidor;
    }

    public void setNomSeguidor(String nomSeguidor) {
        this.nomSeguidor = nomSeguidor;
    }

    public ArrayList<String> getSeguidos() {
        return seguidos;
    }

    public void setSeguidos(ArrayList<String> seguidos) {
        this.seguidos = seguidos;
    }
}
