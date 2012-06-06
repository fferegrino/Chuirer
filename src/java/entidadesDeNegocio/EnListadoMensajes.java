package entidadesDeNegocio;

import java.util.ArrayList;

/**
 * De esta clase se recuperaran los mensajes de manera general
 *
 * @author fferegrino
 */
public class EnListadoMensajes {

    private Long PAGINA;
    private Long NUMERO_INICIAL;
    private Long NUMERO_FINAL;
    private int NUMERO_MENSAJES;
    private ArrayList<EnLlaveListadoMensajes> MENSAJES;

    
    
    public EnListadoMensajes(Long PAGINA, Long NUMERO_INICIAL, Long NUMERO_FINAL) {
        this.PAGINA = PAGINA;
        this.NUMERO_INICIAL = NUMERO_INICIAL;
        this.NUMERO_FINAL = NUMERO_FINAL;
        this.MENSAJES =  new ArrayList<EnLlaveListadoMensajes>();
        this.NUMERO_MENSAJES = 0;
    }

    public void addMensaje(EnLlaveListadoMensajes enLlaveListadoMensajes){
        this.NUMERO_MENSAJES++;
        this.MENSAJES.add(enLlaveListadoMensajes);
    }

    public int getNUMERO_MENSAJES() {
        return NUMERO_MENSAJES;
    }
    
    public ArrayList<EnLlaveListadoMensajes> getMENSAJES() {
        return MENSAJES;
    }

    public void setMENSAJES(ArrayList<EnLlaveListadoMensajes> MENSAJES) {
        this.MENSAJES = MENSAJES;
    }

    public Long getNUMERO_FINAL() {
        return NUMERO_FINAL;
    }

    public void setNUMERO_FINAL(Long NUMERO_FINAL) {
        this.NUMERO_FINAL = NUMERO_FINAL;
    }

    public Long getNUMERO_INICIAL() {
        return NUMERO_INICIAL;
    }

    public void setNUMERO_INICIAL(Long NUMERO_INICIAL) {
        this.NUMERO_INICIAL = NUMERO_INICIAL;
    }

    public Long getPAGINA() {
        return PAGINA;
    }

    public void setPAGINA(Long PAGINA) {
        this.PAGINA = PAGINA;
    }
    
    
}
