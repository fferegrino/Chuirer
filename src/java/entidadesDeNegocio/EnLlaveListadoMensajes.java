/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidadesDeNegocio;

/**
 * De este tipo serán los nodos que contendrá el elemento.
 *
 * @author fferegrino
 */
public class EnLlaveListadoMensajes {

    private Long ID_UNIVERSAL;
    private String USUARIO;
    private Integer ID_MENSAJE;
    private Boolean VISIBLE;

    /**
     * Constructor completo de la clase, no debe usarse normalmente
     *
     * @param ID_UNIVERSAL El id universal que le corresponde, con respecto a la
     * generalidad de los mensajes
     * @param USUARIO El usuario que publica el mensaje
     * @param ID_MENSAJE El id relativo al usuario del mensaje
     */
    public EnLlaveListadoMensajes(Long ID_UNIVERSAL, String USUARIO, Integer ID_MENSAJE, Boolean VISIBLE) {
        this.ID_UNIVERSAL = ID_UNIVERSAL;
        this.USUARIO = USUARIO;
        this.ID_MENSAJE = ID_MENSAJE;
        this.VISIBLE = VISIBLE;
    }

    /**
     * Constructor recomendado para la clase, aquí se descomponen en las llaves
     * requeridas.
     *
     * @param ID_RELATIVO_MENSAJE A recordar que este id se compone de
     * <code>[usuario_publicación].[id_relativo]</code>
     * @param ID_UNIVERSAL El id universal que le corresponde, con respecto a la
     * generalidad de los mensajes
     * @throws Exception Si la llave pasada en
     * <code>ID_RELATIVO_MENSAJE</code> está mal formada.
     */
    public EnLlaveListadoMensajes(String ID_RELATIVO_MENSAJE, Long ID_UNIVERSAL) throws Exception {
        int Separador = ID_RELATIVO_MENSAJE.indexOf(".");
        if (Separador > 0) {
            this.USUARIO = ID_RELATIVO_MENSAJE.substring(0, Separador);
            this.ID_MENSAJE = Integer.parseInt(ID_RELATIVO_MENSAJE.substring(Separador + 1));
            
            this.ID_UNIVERSAL = ID_UNIVERSAL;
        } else {
            throw new Exception("Llave mal formada");
        }
    }

    /**
     * Recupera la llave relativa del mensaje deseado
     *
     * @return La cadena de la forma
     * <code>[usuario_publicación].[id_relativo]</code> que corresponde a la
     * llave relativa del mensaje
     */
    public String getLlaveMensajeRelativo() {
        return this.USUARIO + "." + this.ID_MENSAJE;
    }

    public Integer getID_MENSAJE() {
        return ID_MENSAJE;
    }

    public Long getID_UNIVERSAL() {
        return ID_UNIVERSAL;
    }

    public String getUSUARIO() {
        return USUARIO;
    }

    public Boolean getVISIBLE() {
        return VISIBLE;
    }

    public void setVISIBLE(Boolean VISIBLE) {
        this.VISIBLE = VISIBLE;
    }

    /**
     * Compara y nos dice si un objeto es igual a otro, para este caso
     *
     * @param obj Un objeto de la clase
     * <code>EnLlaveListadoMensajes</code> o uno de la clase
     * <code>Long</code>
     * @return Hay varias posibilidades, ya que recibe distintos parámetros, a
     * continuación el detalle de estos: <ul> <li>Si se le pasa un
     * <code>Long</code> como parámetro, devolverá
     * <code>true</code> siempre que ese número sea igual al
     * <code>ID_UNIVERSAL</code> </li> <li>Si se le envía un objeto de la clase
     * <code>EnLlaveListadoMensajes</code>, devolverá
     * <code>true<code> siempre que las llaves del mensaje sean las mismas
     * </li>
     * <li>Si se le envía otra cosa, devolverá siempre <code>false</code>
     * <code>false</code> </ul>
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Long) {
            Long idUniversal = (Long) obj;
            if (this.ID_UNIVERSAL == idUniversal) {
                return true;
            }
        } else if (obj instanceof EnLlaveListadoMensajes) {
            EnLlaveListadoMensajes llave = (EnLlaveListadoMensajes) obj;
            if (this.ID_UNIVERSAL == llave.ID_UNIVERSAL) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + (this.ID_UNIVERSAL != null ? this.ID_UNIVERSAL.hashCode() : 0);
        hash = 83 * hash + (this.USUARIO != null ? this.USUARIO.hashCode() : 0);
        hash = 83 * hash + (this.ID_MENSAJE != null ? this.ID_MENSAJE.hashCode() : 0);
        hash = 83 * hash + (this.VISIBLE != null ? this.VISIBLE.hashCode() : 0);
        return hash;
    }
}
