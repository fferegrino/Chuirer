/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chuirer.utilitarios;

import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author fferegrino
 */
public class Funciones {

    /**
     * Método para validar que una cadena tenga un valor
     *
     * @param cadenaAEvaluar La cadena a evaluar
     * @return
     * <code>true</code> cuando la cadena es nula o no tiene un valor
     */
    public static boolean cadenaNulaOVacia(String cadenaAEvaluar) {
        if (cadenaAEvaluar == null || cadenaAEvaluar.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Función para parsear fechas
     *
     * @param fecha La fecha que se desea convertir
     * @return Una cadena representativa de la fecha en formato
     * <code>MM/dd/yyyy</code>
     */
    public static String Date2ShortDateString(Date fecha) {
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        return formatter.format(fecha);
    }

    /**
     * Función para parsear fechas
     *
     * @param fecha La fecha que se desea convertir en formato
     * <code>MM/dd/yyyy</code>
     * @return Un objeto que contiene la fecha especificada
     */
    public static Date ShortDateString2Date(String fecha) {
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date date;
        try {
            date = (Date) formatter.parse(fecha);
        } catch (ParseException ex) {
            date = null;
        }
        return date;
    }

    /**
     * Funcion para parsear fechas con horas, minutos y segundos
     *
     * @param fecha La fecha a convertir
     * @return Una cadena representativa del objeto fecha con el formato
     * <code>MM/dd/yyyy HH:mm:ss</code>
     */
    public static String Date2HourDayString(Date fecha) {
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return formatter.format(fecha);
    }

    /**
     * Funcion para parsear fechas con horas, minutos y segundos
     *
     * @param fecha La fecha que se desea convertir con el formato
     * <code>MM/dd/yyyy HH:mm:ss</code>
     * @return Un objeto que contiene la fecha especificada
     */
    public static Date HourDayString2Date(String fecha) {
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date;
        try {
            date = (Date) formatter.parse(fecha);
        } catch (ParseException ex) {
            date = null;
        }
        return date;
    }

    /**
     * Función que nos devuélve el número de hoja al que corresponde el mensaje
     * con el id universal especificado
     *
     * @param idMensaje El id universal del mensaje del que deseamos averiguar
     * la ubicación
     * @return Un número entero especificando el número de hoja en el que se
     * encuentra el mensaje deseado
     */
    public static Long devuelveNumeroHoja(Long idMensaje) {
        return ((idMensaje - 1L) / 100);
    }

    /**
     * Funcion para la enctriptación de cadenas, utiliza el algoritmo MD5
     * @param string La cadena a enctriptar
     * @return La cadena enctriptada
     * @throws Exception 
     */
    public static String md5(String string) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] b = md.digest(string.getBytes());
        int size = b.length;
        StringBuilder h = new StringBuilder(size);
        //algoritmo y arreglo md5
        for (int i = 0; i < size; i++) {
            int u = b[i] & 255;
            if (u < 16) {
                h.append("0").append(Integer.toHexString(u));
            } else {
                h.append(Integer.toHexString(u));
            }
        }
        //clave encriptada
        return h.toString();
    }
    
    public static boolean esMovil(String userAgent){
         String [] mobile  ={
      "iPhone","Android","MIDP","Opera Mobi",
      "Opera Mini","BlackBerry","HP iPAQ","IEMobile",
      "MSIEMobile","Windows Phone","HTC","LG",
      "MOT","Nokia","Symbian","Fennec",
      "Maemo","Tear","Midori","armv",
      "Windows CE","WindowsCE","Smartphone","240x320",
      "176x220","320x320","160x160","webOS",
      "Palm","Sagem","Samsung","SGH",
      "SIE","SonyEricsson","MMP","UCWEB"};

        boolean esmovil = false;
        for(String s:mobile){
            if(userAgent.contains(s)){
                esmovil = true;
                break;
            }
        }
        return esmovil;
    }
}
