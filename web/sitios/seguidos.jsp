<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<!DOCTYPE html>
<html:html lang="true">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><bean:message key="app.name"/> - <bean:message key="ui.start" /></title>
        <html:base/>
        <link href="../estilos/general.css" rel="stylesheet" type="text/css" />
        <link href="../estilos/hyf.css" type="text/css" rel="stylesheet" />
        <script src="../js/jquery-1.7.1.js"></script>
        <script src="../js/jquery.textareaCounter.plugin.js"></script>
        <script>
            var tweetBox, idUsuario,thobber;
            function init(){
                tweetBox =  document.getElementById("tweetBox");
                idUsuario = document.getElementById("userId");
                thobber =  document.getElementById("thobber");
            }
            
            function publica(){
                init();
                var url = "../publicaMensaje?msg="+tweetBox.value+"&idU="+idUsuario.value;
                thobber.style.display = "block";
                $.getJSON(url,function(data){
                    tweetBox.value ="";
                    thobber.style.display = "none";
                    cargaMensajesNuevos();
                });
            }
            
            $(document).ready(function(){
                $('#tweetBox').textareaCount({
                    maxCharacterSize: <bean:message key="app.maxChars" />,
                    displayFormat: ''
                }, function(data){  
                    var result = data.left; 
                    if(parseInt(data.input) >= parseInt(data.max) - 20){
                        $("#tweetBox").addClass("mensajeAdvertido");
                        $("#tweetBox").removeClass("mensajeNormal");
                    }else{
                        $("#tweetBox").addClass("mensajeNormal");
                        $("#tweetBox").removeClass("mensajeAdvertido");
                    }
                    $('#infoChars').html(result);  
                    
                }); 
                $('#tweetBox').trigger('mouseenter'); 
                cargaMensajes();
                revisaActualizaciones();
                $("#alertNvosMensajes").slideUp();
            });
            var actUltimo = true;
            function cargaMensajes(){//Antigüos
                contenedor = document.getElementById("contenido");
                var desde = document.getElementById("hasta").value;
                var url = "../recuperaMensajesPorUsuario?desde="+desde;
                // alert(url);
                $.getJSON(url,function(resultado){               
                    var msg = resultado.mensajes;
                    if(msg.length == 0 && document.getElementById("lastActID").value == undefined){
                        divInforme =  document.createElement("div");
                        divInforme.id = "noMensajes";
                        divInforme.className = "mensaje";
                        divInforme.innerHTML = "<bean:message key="ui.noMessages" />";
                        contenedor.appendChild(divInforme);
                        document.getElementById("hasta").value = 0;
                    }
                    else{
                        $("#noMensajes").remove();
                    }
                    for(x = 0; x < msg.length; x++){
                        var mensaje = resultado.mensajes[x];
                        if(x == 0 && actUltimo){//Poner la hora de la última actualización
                            document.getElementById("lastActHD").value = mensaje.fecha_publicacion;
                            document.getElementById("lastActID").value =mensaje.unique_id;
                            actUltimo = false;
                        }
                            
                        var div = creaDivMensaje(mensaje);

                        contenedor.appendChild(div);
                    }
                    document.getElementById("hasta").value = resultado.hasta;
                });
            }
            
            
        
            function creaDivMensaje(mensaje){
                var div = document.createElement("div");
                var pUsuario = document.createElement("p");
                pUsuario.className="cUserneim";
                pUsuario.innerHTML="<a href='../public.do?" +mensaje.seguido +"'>@"+mensaje.seguido+"</a>";
                var pContenido = document.createElement("p");
                pContenido.className = "cMensaje";
                //pContenido.innerHTML =  mensaje.contenido;
                var pFecha = document.createElement("p");
                pFecha.className = "cData";
                //pFecha.innerHTML =  mensaje.fecha_publicacion + " via " + mensaje.via;
                //pFecha.innerHTML = "<a href='../message.do?"+mensaje.seguido+ "."+ mensaje.id +"'>"+ mensaje.fecha_publicacion + " via " + mensaje.via+"</a>";
                div.appendChild(pUsuario);
                div.appendChild(pContenido);
                div.appendChild(pFecha);
                /*if(mensaje.destacado){
                    div.className="mensaje destacado";
                }else{
                    div.className="mensaje";
                }*/
                return div;
            }
            
            var timer = null;
            function revisaActualizaciones(){
                var ultimoId= document.getElementById("lastActID").value;
                var url ="../recuperaNuevosMensajesPorUsuario?hasta="+ultimoId+"&c=true";
                $.getJSON(url,function(resultado){               
                    var numeroMensajes = resultado.total;
                    if(numeroMensajes > 0){
                        if(numeroMensajes == 1 )
                            var alertaNuevosMensajes = "<bean:message key="ui.xNewMessage" />";
                        else{
                            var alertaNuevosMensajes = "<bean:message key="ui.xNewMessages" />";
                        }
                        alertaNuevosMensajes = alertaNuevosMensajes.replace("x",numeroMensajes);
                        $("#alertNvosMensajes").html(alertaNuevosMensajes);
                        $("#alertNvosMensajes").slideDown();
                    }
                });
                timer = setTimeout(revisaActualizaciones,6000);
            }
            
            function cargaMensajesNuevos(){
            var url = "../obtenerSeguidos?Username=<bean:write scope="session" name="usuarioLogeado"></bean:write>";
            $.getJSON(url, function (resultado){
            var numSeguidos = resultado.total;
            var cadena = "";
            if (resultado.total > 0){
                var siguiente = document.getElementById("alertNvosMensajes");
                for (i = 0; i < numSeguidos; i++){
                    var seguido = resultado.Seguidos[i];
                    cadena += seguido.seguido + "<br>";
                    var div = creaDivMensaje(seguido);
                    $(siguiente).after(div);
                    siguiente = div;
                }
            }
            else{
                var siguiente = document.getElementById("alertNvosMensajes");
                var div = document.createElement("div");
                var pUsuario = document.createElement("p");
                pUsuario.className="cUserneim";
                pUsuario.innerHTML="NO HAY SEGUIDOS...";
                div.appendChild(pUsuario);
                $(siguiente).after(div);
                siguiente = div;
            }
        //document.getElementById("prueba").innerHTML = cadena;
            })
            /*
                var fechaUlima = document.getElementById("lastActHD").value;
                var ultimoId= document.getElementById("lastActID").value;
                var url ="../recuperaNuevosMensajesPorUsuario?hasta="+ultimoId;
                //alert(url);
                //var labelNumeroMensajes = document.getElementById("lbNumeroMensajes");
                //var numeroAntiguoMensajes = parseInt(labelNumeroMensajes.innerHTML);
                actUltimo = true;
                $.getJSON(url,function(resultado){               
                    var numeroMensajes = resultado.total;
                    if(numeroMensajes > 0){
                        //labelNumeroMensajes.innerHTML = numeroAntiguoMensajes+numeroMensajes;
                        var msg = resultado.mensajes;
                        var siguiente = document.getElementById("alertNvosMensajes");
                        for(x = 0; x < msg.length; x++){
                            var mensaje = resultado.mensajes[x];
                            if(x == 0 && actUltimo){//Poner la hora de la última actualización
                                document.getElementById("lastActHD").value = mensaje.fecha_publicacion;
                                document.getElementById("lastActID").value =mensaje.unique_id;
                                actUltimo = false;
                            }
                            var div = creaDivMensaje(mensaje);
                            $(siguiente).after(div);
                            siguiente = div;
                        }
                        
                        $("#alertNvosMensajes").slideUp("slow",function(){
                            $("#alertNvosMensajes").html("");
                        });
                    }
                });*/
            }
        </script>
    </head>
    <body onload="cargaMensajesNuevos()">
        <jsp:include page="../rsc/header.jsp"></jsp:include>
        <div class="innerDiv">
            <html:form action="/profile">
                <div id="usrMenuBar">
                    <input type="hidden" id="lastActHD" />
                    <input type="hidden" id="lastActID" />
                    <style type="text/css">
                        <bean:write name="profile" property="rol" /> 
                    </style>
                    <logic:equal name="profile" property="administrador" value="true">
                        <html:submit styleId="adm" property="adminOn">
                            Administrar
                        </html:submit>
                    </logic:equal>
                    <html:submit property="editProfile" >
                        <bean:message key="ui.editProfile" />
                    </html:submit>
                    <html:link styleClass="headerLink" forward="log_off"><bean:message key="ui.logoff" /></html:link>
                </div>
                <div id="profileDiv" class="divisionIzquierda">
                    <div id="innerProfDiv">
                        <img id="profilepic" src="<bean:write name="profile" property="imgUrl" />" alt="Imagen de perfil de <bean:write name="profile" property="username" filter="false" />"  />
                        <p id="usuario"><span class="username"><bean:write name="profile" property="username" filter="false" /></span> <span class="realname">(<bean:write name="profile" property="realname" filter="false" />)</span> </p>
                        <p id="descripcion"><bean:write name="profile" property="descripcion" filter="false" /></p>
                        <p id="blogOweb"><a href=" <bean:write name="profile" property="url" /> "> <bean:write name="profile" property="url" /></a></p>         
                    </div>  
                    <div id="innerRestoDiv">
                        <table>
                            <tr>
                                <td>
                                    <img src="../images/sitio/ajax-loader.gif" id="thobber" alt="Trabajando" title="Trabajando" />
                                    <html:hidden styleId="userId" property="username" />
                                    <textarea maxlength="140" id="tweetBox"></textarea>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <input type="button" class="btnAltern" onclick="publica()" value="<bean:message key="ui.publish" />" />
                                    <span id="infoChars"></span>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="clear"></div>
                </div>
                <div id="cajaDerecha">
                    <div id="tituloDerecha">
                        <bean:message key="ui.seguidos" />
                    </div>  
                    <div id="contenido">
                        <div id="alertNvosMensajes" onclick="cargaMensajesNuevos()"></div>
                    </div>                    
                    <div id="masMensajes" onclick="cargaMensajes()">
                        <input type="hidden" value="" id="hasta" />
                        <bean:message key="ui.loadmore" />
                    </div>
                </div>
                <div class="divisionIzquierda">
                    <logic:equal name="profile" property="protegida" value="true">
                        <p>
                            <html:link forward="peticiones"><bean:message key="ui.petitions" />
                                <bean:write name="profile" property="peticiones" /></html:link>
                            </p>
                    </logic:equal>
                </div>
                    <div class="divisionIzquierda">
                        <jsp:include page="../rsc/appInfo.jsp" />
                    </div>
            </html:form>    
        </div>
        <div id="prueba">
        </div>
    </body>
</html:html>
