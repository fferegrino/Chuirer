package entidadesDeNegocio;

import chuirer.utilitarios.Funciones;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Representa el archivo de peticiones de un usuario
 *
 * @author fferegrino
 */
public class EnPeticiones {

    private String usuario;
    private int numero_peticiones;
    private ArrayList<EnPeticion> peticiones;

    /**
     * Constructor de la clase
     *
     * @param usuario El usuario al que corresponden las peticiones
     */
    public EnPeticiones(String usuario) {
        this.usuario = usuario;
        this.peticiones = new ArrayList<EnPeticion>();
    }

    public ArrayList<EnPeticion> getPeticiones() {
        return peticiones;
    }

    public int getNumero_peticiones() {
        return numero_peticiones;
    }

    public void setNumero_peticiones(int numero_peticiones) {
        this.numero_peticiones = numero_peticiones;
    }

    public String getUsuario() {
        return usuario;
    }

    /**
     * Agregar una peticion al usuario, realiza la comprobación de que la
     * petición si corresponda a ese usuario
     *
     * @param peticion La petición a agregar
     * @return
     * <code>true</code> si se agregó,
     * <code>false</code> si no.
     */
    public boolean agregarPeticion(EnPeticion peticion) {
        boolean agrega = false;
        if (this.usuario.equals(peticion.getUsuario())) {
            if (!this.peticiones.contains(peticion)) {
                this.peticiones.add(peticion);
                agrega = true;
            }
        }
        return agrega;
    }
    
    public EnPeticion sacaPeticion(String username_solicitud){
        EnPeticion enPeticion =  new EnPeticion();
        enPeticion.setUsuario_peticion(username_solicitud);
        enPeticion.setUsuario(usuario);
        int i = this.peticiones.indexOf(enPeticion);
        if(i >= 0){
            return this.peticiones.get(i);
        }
        return null;
    }

    /**
     * Remueve la petición que se le indique
     *
     * @param peticion La petición a borrar
     */
    public void remuevePeticion(EnPeticion peticion) {
        this.peticiones.remove(peticion);
    }

    public JSONObject toJSONObject() {
        JSONObject objeto = new JSONObject();
        objeto.put("usuario", this.usuario);
        objeto.put("cantidad", this.numero_peticiones);
        JSONArray arregloPeticiones = new JSONArray();
        for (EnPeticion pet : this.peticiones) {
            arregloPeticiones.add(pet.toJSONObject());
        }
        objeto.put("peticiones", arregloPeticiones);
        return objeto;
    }
}
