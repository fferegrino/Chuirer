package entidadesDeNegocio;

import chuirer.utilitarios.Funciones;
import chuirer.utilitarios.Validaciones;
import java.util.Date;
import org.jdom.Element;
import org.json.simple.JSONObject;

/**
 * Representa el núcleo de la aplicación, para lo que está hecha, los mensajes
 *
 * @author fferegrino
 */
public class EnMensaje {

    private String USERNAME;
    private boolean DESTACADO;
    private int ID_MENSAJE;
    private int FAVS;
    private String CONTENIDO;
    private Date FECHA_PUBLICACION;
    private String VIA;
    private String iD_UNICO;
    private Long UNIVERSAL_ID;
    private Long EN_RESPUESTA_A;

    /**
     * Constructor con todos los parámetros, privado ya que por default un
     * mensaje no puede ser destacado
     *
     * @param USERNAME
     * @param DESTACADO
     * @param ID_MENSAJE
     * @param CONTENIDO
     * @param FECHA_PUBLICACION
     * @param VIA
     */
    private EnMensaje(String USERNAME, boolean DESTACADO, int ID_MENSAJE, String CONTENIDO, Date FECHA_PUBLICACION, String VIA) {
        this.USERNAME = USERNAME;
        this.DESTACADO = DESTACADO;
        this.ID_MENSAJE = ID_MENSAJE;
        this.CONTENIDO = CONTENIDO;
        this.FECHA_PUBLICACION = FECHA_PUBLICACION;
        this.VIA = VIA;
    }

    /**
     * Este es el constructor recomendado para publicar un mensaje
     *
     * @param USERNAME El nombre de usuario que publica el mensaje
     * @param CONTENIDO El contenido del mensaje
     * @param VIA La aplicación que usó para publicarlo
     * @param RESPUESTA_A El identificador del mensaje al que responde, a modo
     * de conversación si es
     * <code>-1</code> es que es un mensaje fue fuera de una conversación
     */
    public EnMensaje(String USERNAME, String CONTENIDO, String VIA, Long RESPUESTA_A) {
        this.USERNAME = USERNAME;
        this.CONTENIDO = CONTENIDO;
        this.VIA = VIA;
        this.FAVS = 0;
        this.EN_RESPUESTA_A = RESPUESTA_A;
    }

    /**
     * Este es el constructor recomendado para publicar un mensaje, usar este
     * constructor implica que el mensaje estuvo completamente fuera de una
     * conversación
     *
     * @param USERNAME El nombre de usuario que publica el mensaje
     * @param CONTENIDO El contenido del mensaje
     * @param VIA La aplicación que usó para publicarlo
     */
    public EnMensaje(String USERNAME, String CONTENIDO, String VIA) {
        this.USERNAME = USERNAME;
        this.CONTENIDO = CONTENIDO;
        this.VIA = VIA;
        this.FAVS = 0;
        this.EN_RESPUESTA_A = -1L;
    }

    public EnMensaje() {
    }

    public String getCONTENIDO() {
        return this.getCONTENIDO(false);
    }

    public String getCONTENIDO(boolean formateado) {
        if (!formateado) {
            return this.CONTENIDO;
        }
        Validaciones v = new Validaciones();
        String[] contenidoPartes = this.CONTENIDO.split(" ");
        int indice = 0;
        for (String s : contenidoPartes) {

            if (v.validaUrl(s)) {
                String a = "<a href='" + s + "' target='_blank'>" + s + "</a>";
                contenidoPartes[indice] = a;
            }
            if (s.startsWith("@")) {
                int ibex = 0;
                for (char c : s.toCharArray()) {

                    if (!Character.isAlphabetic(c) && !Character.isDigit(c)) {
                        if (ibex != 0 || c != 64) {
                            break;
                        }
                    }
                    ibex++;
                }
                String a = "<a href='../usuario/" + s.substring(1, ibex) + "'>" + s.substring(0, ibex) + "</a>" + s.substring(ibex);
                contenidoPartes[indice] = a;
            }
            indice++;
        }
        //Unir partes
        int tamano = contenidoPartes.length;
        String contenidoFinal = "";
        for (int i = 0; i < tamano; i++) {
            contenidoFinal += contenidoPartes[i] + (i < tamano + 1 ? " " : "");
        }
        return contenidoFinal;
    }

    public void setCONTENIDO(String CONTENIDO) {
        this.CONTENIDO = CONTENIDO;
    }

    public boolean isDESTACADO() {
        return DESTACADO;
    }

    public void setDESTACADO(boolean DESTACADO) {
        this.DESTACADO = DESTACADO;
    }

    public Date getFECHA_PUBLICACION() {
        return FECHA_PUBLICACION;
    }

    public void setFECHA_PUBLICACION(Date FECHA_PUBLICACION) {
        this.FECHA_PUBLICACION = FECHA_PUBLICACION;
    }

    public int getID_MENSAJE() {
        return ID_MENSAJE;
    }

    public void setID_MENSAJE(int ID_MENSAJE) {
        this.ID_MENSAJE = ID_MENSAJE;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getVIA() {
        return VIA;
    }

    public void setVIA(String VIA) {
        this.VIA = VIA;
    }

    public String getiD_UNICO() {
        return iD_UNICO;
    }

    public void setiD_UNICO(String iD_UNICO) {
        this.iD_UNICO = iD_UNICO;
    }

    public Long getUNIVERSAL_ID() {
        return UNIVERSAL_ID;
    }

    public void setUNIVERSAL_ID(Long UNIVERSAL_ID) {
        this.UNIVERSAL_ID = UNIVERSAL_ID;
    }

    public Long getEN_RESPUESTA_A() {
        return EN_RESPUESTA_A;
    }

    public void setEN_RESPUESTA_A(Long EN_RESPUESTA_A) {
        this.EN_RESPUESTA_A = EN_RESPUESTA_A;
    }

    public int getFAVS() {
        return FAVS;
    }

    public void setFAVS(int FAVS) {
        this.FAVS = FAVS;
    }

    public JSONObject toJSONObject() {
        JSONObject mensaje = new JSONObject();
        mensaje.put("usuario", this.USERNAME);
        mensaje.put("contenido", this.getCONTENIDO(true));
        mensaje.put("destacado", this.DESTACADO);
        mensaje.put("id", this.ID_MENSAJE);
        mensaje.put("fecha_publicacion", Funciones.Date2HourDayString(this.FECHA_PUBLICACION));
        mensaje.put("via", this.VIA);
        mensaje.put("unique_id", this.UNIVERSAL_ID);
        mensaje.put("favs", this.FAVS);
        mensaje.put("en_respuesta_a",this.EN_RESPUESTA_A);
        return mensaje;
    }
}
