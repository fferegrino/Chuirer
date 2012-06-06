package entidadesDeNegocio;

import java.util.ArrayList;

public class EnSeguidores {

    private String nomSeguido;
    private ArrayList<String> seguidores;

    public EnSeguidores(String nomSeguido, ArrayList<String> seguidores) {
        this.nomSeguido = nomSeguido;
        this.seguidores = seguidores;
    }

    public String getNomSeguido() {
        return nomSeguido;
    }

    public void setNomSeguido(String nomSeguido) {
        this.nomSeguido = nomSeguido;
    }

    public ArrayList<String> getSeguidores() {
        return seguidores;
    }

    public void setSeguidores(ArrayList<String> seguidores) {
        this.seguidores = seguidores;
    }
}
