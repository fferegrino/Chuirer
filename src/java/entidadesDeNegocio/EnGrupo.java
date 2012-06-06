package entidadesDeNegocio;

import java.util.ArrayList;


/**
 *Representa al objeto de grupos común d
 * @author danFudo
 */
public class EnGrupo {    
    private String CREADOR;
    private String NOMBRE;
    private String DESCRIPCION;
    private int NUM_USER;
    private ArrayList USERS;
    
    
    /**
     * Constructor con los parametros excepto los usuarios.
     * @param CREADOR
     * @param NOMBRE
     * @param NUM_USER
     */
    
     public EnGrupo() {    
    }
     
    public EnGrupo(String CREADOR, String NOMBRE, String DESCRIPCION) {
        this.CREADOR = CREADOR;
        this.NOMBRE = NOMBRE;
        this.NUM_USER = 1;
        this.DESCRIPCION = DESCRIPCION;
        this.USERS = new ArrayList();
        this.USERS.add(CREADOR);
    }
    
	
    public String getCREADOR() {
        return CREADOR;
    }
	
    public void setCREADOR(String CREADOR) {
        this.CREADOR = CREADOR;
    }
	
    public String getDESCRIPCION() {
        return DESCRIPCION;
    }
	
    public void setDESCRIPCION(String DESCRIPCION) {
        this.DESCRIPCION = DESCRIPCION;
    }
	
    public String getNOMBRE() {
        return NOMBRE;
    }
	
    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }
	
    public int getNUM_USER() {
        return NUM_USER;
    }
	
    public void setNUM_USER(int NUM_USER) {
        this.NUM_USER = NUM_USER;
    }
	
    public ArrayList getUSERS() {
        return USERS;
    }
	
    public void setUSERS(ArrayList USERS) {
        this.USERS = USERS;
    }    
    
    public void setUSER(String  idUser) {
        this.USERS.add(idUser);
    }
    
    public void eliminaUSER(int indice){
        this.USERS.remove(indice);
    }
    
}
