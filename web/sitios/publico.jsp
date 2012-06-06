<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<!DOCTYPE html>
<html:html lang="true">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <html:base/>
        <title><bean:message key="app.name"/> - <bean:message key="ui.start" /></title>
        <link href="../estilos/general.css" rel="stylesheet" type="text/css" />
        <link href="../estilos/hyf.css" type="text/css" rel="stylesheet" />
        <script src="../js/jquery-1.7.1.js"></script>
        <script src="../js/waypoints.min.js"></script>
        <script src="../js/chuirer.js"></script>
        <logic:equal name="publicProfile" property="visible" value="true">    
            <!-- Con ajax recuperamos los tweets -->
            <script>
                var contenedor;
                $(document).ready(function(){
                    cargaMensajes();
                    revisaActualizaciones();
                    $("#alertNvosMensajes").slideUp();
                });
                
                var opts = {
                    offset: '100%'
                };
                var actUltimo = true;
                var puedeCargar =  true;
                function cargaMensajes(){//Antigüos
                    if(puedeCargar){
                        puedeCargar = false;
                        contenedor = document.getElementById("contenido");
                        var hasta = document.getElementById("hasta").value;
                        var url = "../recuperaMensajes?usuario=<bean:write name="publicProfile" property="username" filter="false" />&t="+hasta;
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
                                    document.getElementById("lastActID").value =mensaje.id;
                                    actUltimo = false;
                                }
                            
                                var div = creaDivMensaje(mensaje);
                                contenedor.appendChild(div);
                            }
                            document.getElementById("hasta").value = resultado.hasta;
                            if(resultado.hasta > 0){
                                setWaypoint();
                                puedeCargar =  true;
                            }else{
                                //alert("End");
                            }
                        });
                    }
                }
            
                var timer = null;
                function revisaActualizaciones(){
                    var fechaUlima = document.getElementById("lastActHD").value;
                    var ultimoId= document.getElementById("lastActID").value;
                    var url = "../recuperaNuevosMensajes?u=<bean:write name="publicProfile" property="username" filter="false" />&t="+ultimoId+"&h="+fechaUlima;
                    $.getJSON(url,function(resultado){               
                        var numeroMensajes = resultado.nuevos_mensajes;
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
                    var fechaUlima = document.getElementById("lastActHD").value;
                    var ultimoId= document.getElementById("lastActID").value;
                    var url = "../recuperaNuevosMensajes?u=<bean:write name="publicProfile" property="username" filter="false" />&t="+ultimoId+"&h="+fechaUlima;
                    var labelNumeroMensajes = document.getElementById("lbNumeroMensajes");
                    var numeroAntiguoMensajes = parseInt(labelNumeroMensajes.innerHTML);
                    //alert(url);
                    // alert(url);
                    actUltimo = true;
                    $.getJSON(url,function(resultado){               
                        var numeroMensajes = resultado.nuevos_mensajes;
                        if(numeroMensajes > 0){
                            labelNumeroMensajes.innerHTML = numeroAntiguoMensajes+numeroMensajes;
                            var msg = resultado.mensajes;
                            var siguiente = document.getElementById("alertNvosMensajes");
                            for(x = 0; x < msg.length; x++){
                                var mensaje = resultado.mensajes[x];
                                if(x == 0 && actUltimo){//Poner la hora de la última actualización
                                    document.getElementById("lastActHD").value = mensaje.fecha_publicacion;
                                    document.getElementById("lastActID").value =mensaje.id;
                                    actUltimo = false;
                                }
                                var div = creaDivMensaje(mensaje);
                                $(siguiente).after(div);
                                siguiente = div;
                            }
                            
                            $("#alertNvosMensajes").slideUp("fast",function(){
                                $("#alertNvosMensajes").html("");
                            });
                        }
                    });
                }
               
                
                function setWaypoint(){
                    $("#masMensajes").waypoint(function(event, direction){

                        if (direction == 'down') {
                            var fin = $(this);
                            //alert(" d");
                            //fin.css({'visibility' : 'visible'}); // mostramos "cargando..."
                            fin.waypoint('remove'); // eliminamos el waypoint mientras mostramos datos
                            // el settimeout "simula" la carga de datos (se debe quitar)
                            cargaMensajes();
                        }
                    },opts);
                }
                
                function seguir(follow){
                    var url = "../seguirOnoSeguir?u=<bean:write name="publicProfile" property="username" filter="false" />" + "&seguir=" + follow;
                    $.getJSON(url,function(resultado){
                        if(resultado.success){
                            location.reload();
                        }
                    });

                }
            </script>
        </logic:equal>
        <logic:notEqual name="publicProfile" property="visible" value="true">  
            <script>
                function agregaPeticion(){
                    var url = "../agregaPeticion?u=<bean:write name="publicProfile" property="username" filter="false" />";
                    //alert(url);
                    $.getJSON(url,function(resultado){               
                        var recibida = resultado.success;
                        if(recibida){
                            alert("Tu petición fue correctamente enviada");
                        }
                        else{
                            alert("Ocurrió un error, puede que ya hayas enviado una petición a este usuario");
                        }
                    });
                }
            </script>
        </logic:notEqual>
    </head>
    <body>
        <jsp:include page="../rsc/header.jsp"></jsp:include>
            <div class="innerDiv">
            <html:form action="/profile">
                <div id="usrMenuBar">
                    <input type="hidden" id="lastActHD" />
                    <input type="hidden" id="lastActID" />
                    <logic:notEmpty scope="session" name="usuarioLogueado">
                        <html:link styleClass="headerLink" forward="go_prof">
                            <bean:message key="ui.start" />
                        </html:link>
                        <html:link styleClass="headerLink" forward="log_off"><bean:message key="ui.logoff" /></html:link>
                    </logic:notEmpty>
                </div>
                <div id="cajaIzquierda">
                    <div id="profileDiv" class="divisionIzquierda">
                        <img id="profilepic" src="<bean:write name="publicProfile" property="imgUrl" />" alt="Imagen de perfil de <bean:write name="publicProfile" property="username" filter="false" />"  />
                        <p id="usuario"><span class="username"><bean:write name="publicProfile" property="username" filter="false" /></span> <span class="realname">(<bean:write name="publicProfile" property="realname" filter="false" />)</span></p>
                        <p id="descripcion"><bean:write name="publicProfile" property="desc" filter="false" /></p>
                        <p id="blogOweb"><a href=" <bean:write name="publicProfile" property="url" /> "> <bean:write name="publicProfile" property="url" /></a></p>
                    </div>
                    <div class="divisionIzquierda">
                        <div class="stats">
                            <p id="lbNumeroMensajes"> <bean:write name="publicProfile" property="mensajes" filter="false" /> </p>
                            <p><bean:message key="ui.messages" /></p>
                        </div>
                        <html:link forward="seg" paramId="usuario" paramName="publicProfile" paramProperty="username">
                            <div class="stats">
                                <p id="lbNumeroSeguidos"> <bean:write name="publicProfile" property="seguidos" filter="false" /> </p>
                                <p><bean:message key="ui.following" /></p>
                            </div>
                        </html:link>
                        <html:link forward="seg" paramId="usuario" paramName="publicProfile" paramProperty="username">
                            <div class="stats">
                                <p id="lbNumeroSeguidores"> <bean:write name="publicProfile" property="seguidores" filter="false" /> </p>
                                <p><bean:message key="ui.followers" /></p>
                            </div>
                        </html:link>
                    </div>
                    <div class="divisionIzquierda">
                        <logic:equal name="publicProfile" property="visible" value="false">

                            <logic:notEmpty scope="session" name="usuarioLogueado">
                                <input type="button" class="btnAltern" value="<bean:message key="ui.sendPetition"/>" onclick="agregaPeticion()" />
                            </logic:notEmpty>
                        </logic:equal>
                        <logic:equal name="publicProfile" property="visible" value="true">
                            <logic:empty scope="session" name="usuarioLogueado">

                                <input type="button" class="btnAltern" value="<bean:message key="ui.loginTitle"/>" />
                                <bean:message key="ui.or" />
                                <input type="button" class="btnAltern" value="<bean:message key="ui.register"/>"  />
                                <bean:message key="ui.connectUser" />
                            </logic:empty>
                            <logic:notEmpty scope="session" name="usuarioLogueado">
                                <logic:equal name="publicProfile" property="mismoUsuario" value="true">
                                    <bean:message key="ui.thisIsYourPublicProfile" />
                                </logic:equal>
                                <logic:notEqual name="publicProfile" property="mismoUsuario" value="true">
                                    <logic:equal name="publicProfile" property="following" value="false">
                                        <bean:message key="ui.uRNotFollowing" />
                                        <input type="button" class="btnAltern" value="<bean:message key="ui.follow"/>" onclick="seguir(true)" />
                                    </logic:equal>
                                    <logic:notEqual name="publicProfile" property="following" value="false">
                                        <bean:message key="ui.uRFollowing" />
                                        <input type="button" class="btnAltern" value="<bean:message key="ui.unfollow"/>" onclick="seguir(false)" />
                                    </logic:notEqual>
                                </logic:notEqual>
                            </logic:notEmpty>
                        </logic:equal>
                    </div>
                    <div class="divisionIzquierda" id="appInfo">
                        <jsp:include page="../rsc/appInfo.jsp" />
                    </div>
                </div>


                <div id="cajaDerecha">
                    <logic:equal name="publicProfile" property="visible" value="true">  
                        <div id="tituloDerecha">
                            <bean:message key="ui.messages" />
                        </div>
                        <div id="contenido">
                            <div id="alertNvosMensajes" onclick="cargaMensajesNuevos()"></div>
                        </div>
                        <div id="masMensajes" onclick="cargaMensajes()">
                            <input type="hidden" value="" id="hasta" />
                            <bean:message key="ui.loadmore" />
                        </div>
                    </logic:equal>
                    <logic:notEqual name="publicProfile" property="visible" value="true">
                        <div id="tituloDerecha">
                            <bean:message key="ui.accountProtected" />
                        </div>
                        <div id="cuentaBloqueada">
                            <logic:notEmpty scope="session" name="usuarioLogueado">
                                <bean:message key="ui.accountProtectedInfoLoggedIn" />
                            </logic:notEmpty>
                            <logic:empty scope="session" name="usuarioLogueado">
                                <bean:message key="ui.accountProtectedInfo" />
                            </logic:empty>
                        </div>
                        <div>
                            &nbsp;  
                        </div>
                    </logic:notEqual>
                </div>

            </html:form>
        </div>
    </body>
</html:html>
