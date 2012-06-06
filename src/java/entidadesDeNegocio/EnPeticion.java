/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidadesDeNegocio;

import chuirer.utilitarios.Funciones;
import java.util.Date;
import org.json.simple.JSONObject;

/**
 *  Unidad de las peticiones, para saber si es bloqueada o no una petición
 * @author fferegrino
 */
public class EnPeticion {
    private String usuario;
    private String usuario_peticion;
    private boolean bloqueada;
    private Date fecha_peticion;

    public EnPeticion(String usuario, String usuario_peticion, boolean esBloqueada) {
        this.usuario = usuario;
        this.usuario_peticion = usuario_peticion;
        this.bloqueada = esBloqueada;
    }
    
    public EnPeticion(){
        
    }

    public Date getFecha_peticion() {
        return fecha_peticion;
    }

    public void setFecha_peticion(Date fecha_peticion) {
        this.fecha_peticion = fecha_peticion;
    }

    public boolean isBloqueada() {
        return bloqueada;
    }

    public void setBloqueada(boolean bloqueada) {
        this.bloqueada = bloqueada;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuario_peticion() {
        return usuario_peticion;
    }

    public void setUsuario_peticion(String usuario_peticion) {
        this.usuario_peticion = usuario_peticion;
    }
    
    
    @Override
    public boolean equals(Object o){
        if(o instanceof EnPeticion){
            EnPeticion p = (EnPeticion)o;
            if(p.getUsuario().equals(this.usuario) && p.getUsuario_peticion().equals(this.usuario_peticion))
            {
                return true;
            }
        }
        else if(o instanceof String){
            String s = (String)o;
            if(this.usuario_peticion.equals(s)){
                return true;
            }
        }
        return false;
    }
    
    public JSONObject toJSONObject(){
        JSONObject objeto = new JSONObject();
        objeto.put("usuario", this.usuario);
        objeto.put("usuario_peticion", this.usuario_peticion);
        objeto.put("fecha_peticion", Funciones.Date2ShortDateString(this.fecha_peticion));
        //objeto.put("fecha",this.fecha_peticion);
        objeto.put("bloqueada", this.bloqueada);
        return objeto;
    }
    
}
