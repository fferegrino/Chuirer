<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<html:html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><bean:message key="app.name"/> - <bean:message key="ui.petitions" /></title>
        <html:base/>
        <link href="../estilos/general.css" rel="stylesheet" type="text/css" />
        <link href="../estilos/hyf.css" type="text/css" rel="stylesheet" />
        <script src="../js/jquery-1.7.1.js"></script>
        <script>
            function aceptaPeticion(boton){
                var usuario = boton.id.substr(2);
                var url = "../aceptaPeticion?usuario=" + usuario;
                $.getJSON(url,function(data){
                    if(data.success){
                        $("#divUsuario_"+usuario).fadeOut();
                    }
                    else{
                        alert("Hubo un error, intenta de nuevo");
                    }
                });
            }
            
            $(document).ready(function(){
                var url  = "../recuperaPeticiones?u=<bean:write name="profile" property="username" filter="false" />";
                var contenido =  document.getElementById("contenido");
                $.getJSON(url,function(data){
                    var peticiones = data.peticiones;
                    for(var i = 0; i < peticiones.length; i++){
                        var peticion = peticiones[i];
                        var div = document.createElement("div");
                        var pUsuario = document.createElement("p");
                        pUsuario.className="cUserneim";
                        pUsuario.innerHTML="<a href='../usuario/" +peticion.usuario_peticion +"'>@"+peticion.usuario_peticion+"</a>";
                        var pFecha = document.createElement("p");
                        pFecha.className = "cData";
                        pFecha.innerHTML = peticion.fecha_peticion;
                        div.appendChild(pUsuario);
                        /** 
                         * Crear botonera
                         */            
                        var pContenido = document.createElement("p");
                        var botonAceptar = document.createElement("input");
                        var botonBloquear = document.createElement("input");
                        botonAceptar.type  = "button";
                        botonBloquear.type  = "button";
                        botonAceptar.className ="btnAltern";
                        botonBloquear.className ="btnAltern";
                        botonAceptar.value = "<bean:message key="ui.accept" />";
                        botonBloquear.value ="<bean:message key="ui.block" />";;
                        //pUsuario.appendChild(botonAceptar);
                        //pUsuario.appendChild(botonBloquear);
                        pContenido.appendChild(botonAceptar);
                        botonAceptar.id = "a_" + peticion.usuario_peticion;
                        botonAceptar.setAttribute("onclick", "aceptaPeticion(this)");
                        //pContenido.appendChild(botonBloquear);
                        botonBloquear.id = "b_" + peticion.usuario_peticion;
                        /**
                         * Crear botonera
                         */
                        div.appendChild(pContenido);
                        //div.appendChild(pFecha);
                        div.className= "mensaje";
                        div.id = "divUsuario_" + peticion.usuario_peticion;
                        contenido.appendChild(div);
                    }
                });
            });
        </script>
    </head>
    <body>
        <jsp:include page="../rsc/header.jsp"></jsp:include>
            <div class="innerDiv">
            <html:form action="/profile">
                <div id="usrMenuBar">
                    <logic:notEmpty scope="session" name="usuarioLogueado">
                        <html:link styleClass="headerLink" forward="go_prof">
                            <bean:message key="ui.start" />
                        </html:link>
                        <html:link styleClass="headerLink" forward="log_off"><bean:message key="ui.logoff" /></html:link>
                    </logic:notEmpty>
                </div>
                <div id="cajaIzquierda">
                    <div id="profileDiv" class="divisionIzquierda">
                        <img id="profilepic" src="<bean:write name="profile" property="imgUrl" />" alt="Imagen de perfil de <bean:write name="profile" property="username" filter="false" />"  />
                        <p id="usuario"><span class="username"><bean:write name="profile" property="username" filter="false" /></span> <span class="realname">(<bean:write name="profile" property="realname" filter="false" />)</span></p>
                        <p id="descripcion"><bean:write name="profile" property="descripcion" filter="false" /></p>
                        <p id="blogOweb"><a href=" <bean:write name="profile" property="url" /> "> <bean:write name="profile" property="url" /></a></p>
                    </div>
                    <div class="divisionIzquierda" id="appInfo">
                        <jsp:include page="../rsc/appInfo.jsp" />
                    </div>
                </div>
                <div id="cajaDerecha">
                    <div id="tituloDerecha">
                        <bean:message key="ui.petitions" />
                    </div>
                    <div id="contenido">
                        <div id="alertNvosMensajes"></div>
                    </div>
                    <div id="masMensajes">
                        &nbsp;
                    </div>
                </div>
            </html:form>
        </div>
    </body>
</html:html>
