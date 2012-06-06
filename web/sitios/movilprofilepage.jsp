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
        <meta name="viewport" content="width=device-width, initial-scale=1"> 
        <link rel="stylesheet" href="http://code.jquery.com/mobile/1.1.0/jquery.mobile-1.1.0.min.css" />
        <link rel="stylesheet" href="../estilos/mobile.css" />
        <script src="../js/jquery-1.7.1.js"></script>
        <script src="../js/jquery.textareaCounter.plugin.js"></script>
        <script src="../js/waypoints.min.js"></script>
        <script src="http://code.jquery.com/mobile/1.1.0/jquery.mobile-1.1.0.min.js">
        </script>
        <script>
            var tweetBox, idUsuario,thobber;
            function init(){
                tweetBox =  document.getElementById("tweetBox");
                idUsuario = document.getElementById("userId");
                thobber =  document.getElementById("thobber");
            }
            
            function publica(){
                init();
                tweetBox.disabled=true;
                $("#" + tweetBox.id).slideUp();
                var url = "../publicaMensaje?msg="+tweetBox.value+"&idU="+idUsuario.value;
                //thobber.style.display = "block";
                $.getJSON(url,function(data){
                    tweetBox.value ="";
                    cargaMensajesNuevos();
                    setTimeout(activaCaja, 1000);
                });
            }
            
            function activaCaja(){
                tweetBox.disabled=false;
                $("#" + tweetBox.id).slideDown();
                
            }
            var opts = {
                offset: '100%'
            };
            $(document).ready(function(){
                /*
                $('#tweetBox').textareaCount({
                    maxCharacterSize: 
                    displayFormat: ''
                }, function(data){  
                    var result = data.left; 
                   
                    $('#infoChars').html(result);  
                    
                }); 
                 */
                //$('#tweetBox').trigger('mouseenter'); 
                cargaMensajes();
                setTimeout(revisaActualizaciones,10000);
                $("#alertNvosMensajes").slideUp();
                
            });
            
            function setWaypoint(){
                $("#masMensajes").waypoint(function(event, direction){
                    //this.waypoint("remove");
                    
                    //this.waypoint(opts);
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
            
            var actUltimo = true;
            var puedeCargar  = true;
            function cargaMensajes(){//Antigüos
                if(puedeCargar){
                    puedeCargar = false;
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
                            div.style.display = "none";
                            $(contenedor).after(div);
                            $(div).fadeIn();
                            contenedor = div;
                        }
                        document.getElementById("hasta").value = resultado.hasta;
                        if(resultado.hasta > 0){
                            //setWaypoint();
                            puedeCargar =  true;
                        }else{
                            //alert("End");
                        }
                    });
                }
            }
            
            
         
        
            function creaDivMensaje(mensaje){
                var div = document.createElement("li");
                div.setAttribute("data-theme", "g");
                var aMensaje =  document.createElement("a");
                aMensaje.setAttribute("href", "../message.do?"+mensaje.usuario+"."+mensaje.id);
                aMensaje.setAttribute("data-transition", "slide");
                var pUsuario = document.createElement("p");
                pUsuario.className="cUserneim";
                pUsuario.innerHTML="@"+mensaje.usuario;
                var pContenido = document.createElement("p");
                pContenido.className = "cMensaje";
                pContenido.innerHTML =  mensaje.contenido;
                var pFecha = document.createElement("p");
                pFecha.className = "cData";
                //pFecha.innerHTML =  mensaje.fecha_publicacion + " via " + mensaje.via;
                pFecha.innerHTML = mensaje.fecha_publicacion + " via " + mensaje.via;
                var divClear = document.createElement("div");
                divClear.className = "clear";
                aMensaje.appendChild(pUsuario);
                aMensaje.appendChild(pFecha);
                aMensaje.appendChild(divClear);
                aMensaje.appendChild(pContenido);
                if(mensaje.destacado){
                    aMensaje.className="mensaje destacado";
                }else{
                    aMensaje.className="mensaje";
                }
                div.appendChild(aMensaje);
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
                        var siguiente = document.getElementById("contenido");
                        for(x = 0; x < msg.length; x++){
                            var mensaje = resultado.mensajes[x];
                            if(x == 0 && actUltimo){//Poner la hora de la última actualización
                                document.getElementById("lastActHD").value = mensaje.fecha_publicacion;
                                document.getElementById("lastActID").value =mensaje.unique_id;
                                actUltimo = false;
                            }
                            var div = creaDivMensaje(mensaje);
                            
                            $(siguiente).after(div);
                            $(div).fadeIn();
                            siguiente = div;
                        }
                        
                        $("#alertNvosMensajes").slideUp("slow",function(){
                            $("#alertNvosMensajes").html("");
                        });
                    }
                });
            }
        </script>
    </head>

    <body>
        <html:form action="/profile">
            <div data-role="page" id="page1">
                <div data-theme="c" data-role="header">
                    <h3>

                        <input type="hidden" id="lastActHD" />
                        <input type="hidden" id="lastActID" value="0" />
                        <bean:message key="app.name"/> de <bean:write name="profile" property="username" filter="false" />
                    </h3>
                </div>
                <div data-theme="a" data-role="header">
                </div>
                <div data-role="content">
                    <div data-role="collapsible-set" data-theme="a" data-content-theme="b">
                        <div data-role="collapsible" data-collapsed="true">
                            <h3>
                                Publicar
                            </h3>
                            <div data-role="fieldcontain">
                                <fieldset data-role="controlgroup">
                                    <html:hidden styleId="userId" property="username" />
                                    <textarea id="tweetBox" maxlength="140" placeholder="">
                                    </textarea>
                                </fieldset>
                            </div>
                            <a data-role="button" data-direction="reverse" data-transition="fade" data-theme="a" href="javascript:void(0);" data-icon="alert" data-iconpos="right" onclick="publica()">
                                <bean:message key="ui.publish" />
                            </a>
                        </div>
                    </div>

                    <ul data-role="listview" data-divider-theme="b" data-inset="true">
                        <li data-role="list-divider" role="heading">
                            <bean:message key="ui.messages" />
                        </li>
                        <li data-theme="g" id="contenido">
                            <span href="#"   onclick="cargaMensajesNuevos()" id="alertNvosMensajes">

                            </span>
                        </li>
                        <li data-role="list-divider" role="heading"  onclick="cargaMensajes()">
                            <input type="hidden" value="" id="hasta" />
                            <bean:message key="ui.loadmore" />
                        </li>
                    </ul>
                </div>
                <div id="cajaDerecha">
                    <div id="tituloDerecha">
                    </div>  
                    <div id="contenido">
                        <div id="alertNvosMensajes" onclick="cargaMensajesNuevos()"></div>
                    </div>                    
                    <div id="masMensajes" onclick="cargaMensajes()">

                    </div>
                </div>




                <div data-theme="a" data-role="footer" data-position="fixed">
                    <h3>
                        <jsp:include page="../rsc/appInfo.jsp" />
                    </h3>
                </div>
            </div>
        </html:form>    

    </body>
</html:html>
