/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidadesDeNegocio;

/**
 *
 * @author fferegrino
 */
public class EnMensajesGeneral {
    public Long ID_MENSAJES_UNIVERSAL;

    public EnMensajesGeneral(Long ID_MENSAJES_UNIVERSAL) {
        this.ID_MENSAJES_UNIVERSAL = ID_MENSAJES_UNIVERSAL;
    }
    
    public EnMensajesGeneral(){
        
    }
    
    public Long getID_MENSAJES_UNIVERSAL() {
        return ID_MENSAJES_UNIVERSAL;
    }

    public void setID_MENSAJES_UNIVERSAL(Long ID_MENSAJES_UNIVERSAL) {
        this.ID_MENSAJES_UNIVERSAL = ID_MENSAJES_UNIVERSAL;
    }
    
    
}
