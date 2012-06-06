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
        <link href="../estilos/grupos.css" type="text/css" rel="stylesheet" />      
        <script src="../js/jquery-1.7.1.js"></script>
        <script src="../js/jquery.textareaCounter.plugin.js"></script>
        <script src="../js/waypoints.min.js"></script>
        <script src="../js/chuirer.js"></script>
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
                $('#tweetBox').textareaCount({
                    maxCharacterSize: <bean:message key="app.maxChars" />,
                    displayFormat: ''
                }, function(data){  
                    var result = data.left; 
                    /*
                    if(parseInt(data.input) >= parseInt(data.max) - 20){
                        $("#tweetBox").addClass("mensajeAdvertido");
                        $("#tweetBox").removeClass("mensajeNormal");
                    }else{
                        $("#tweetBox").addClass("mensajeNormal");
                        $("#tweetBox").removeClass("mensajeAdvertido");
                    }
                     */
                    $('#infoChars').html(result);  
                    
                }); 
                $('#tweetBox').trigger('mouseenter'); 
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
                            contenedor.appendChild(div);
                            $(div).fadeIn();
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
                        var siguiente = document.getElementById("alertNvosMensajes");
                        for(x = 0; x < msg.length; x++){
                            var mensaje = resultado.mensajes[x];
                            if(x == 0 && actUltimo){//Poner la hora de la última actualización
                                document.getElementById("lastActHD").value = mensaje.fecha_publicacion;
                                document.getElementById("lastActID").value =mensaje.unique_id;
                                actUltimo = false;
                            }
                            var div = creaDivMensaje(mensaje);
                            div.style.display = "none";
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
        <jsp:include page="../rsc/header.jsp"></jsp:include>
            <div class="innerDiv">
            <html:form action="/profile">
                <div id="usrMenuBar">
                    <input type="hidden" id="lastActHD" />
                    <input type="hidden" id="lastActID" value="0" />
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
                    <div id="cajaIzquierda">
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

                    <div class="divisionIzquierda">
                        <div class="stats">
                            <p><bean:write name="profile" property="mensajes" /></p>
                            <p><bean:message key="ui.messages" /></p>
                        </div>
                        <html:link forward="seg" paramId="usuario" paramName="profile" paramProperty="username">
                            <div class="stats">
                                <p><bean:write name="profile" property="seguidores" /></p>
                                <p><bean:message key="ui.followers" /></p>
                            </div>
                        </html:link>
                        <html:link forward="seg" paramId="usuario" paramName="profile" paramProperty="username">
                            <div class="stats">
                                <p><bean:write name="profile" property="seguidos" /></p>
                                <p><bean:message key="ui.following" /></p>
                            </div>
                        </html:link>
                        <a href="../grupos.do?seguidos=<bean:write name="profile" filter="false" property="username" />">                        
                            <div class="stats">
                                <p><bean:write name="profile" property="grupos" /></p>
                                <p><bean:message key="ui.groups" /></p>
                            </div>
                        </a> 
                        <logic:equal name="profile" property="protegida" value="true">
                            <html:link forward="peticiones">
                                <div class="stats">

                                    <p>
                                        <bean:write name="profile" property="peticiones" />
                                    </p>
                                    <p>
                                        <bean:message key="ui.petitions" />
                                    </p>

                                </div>
                            </html:link>
                        </logic:equal>
                    </div>
                    <!Amigos>              
                    <div  class="divGrupos">             
                        <div>  
                            <p id="parrafo">
                                Amigos      
                            </p>


                            <table width="100%" cellspacing="2" cellpadding="5" style="text-align:center" >
                                <tr >
                                    <td width="50%" >
                                        <a href="../amigos.do">                        
                                            Buscar                                  
                                        </a> 
                                    </td>
                                    <td width="50%">
                                        <a href="../amigos.do?sugerencias=<bean:write name="profile" property="username" />">                        
                                            Sugerencias                                  
                                        </a>                                
                                    </td>    
                                </tr>  
                            </table>

                        </div>
                    </div>

                    <!Grupos>        
                    <div  class="divGrupos">             
                        <div>  
                            <p id="parrafo">
                                Grupos                    
                            </p>
                        </div>  
                        <div>
                            <table width="100%" cellspacing="2" cellpadding="5" style="text-align:center" >
                                <tr >
                                    <td width="25%" >
                                        <a href="../grupos.do?seguidos=<bean:write name="profile" property="username" />">                        
                                            Seguidos                                     
                                        </a> 
                                    </td>
                                    <td width="25%">
                                        <html:link forward="go_Gpos">                        
                                            Buscar
                                        </html:link> 
                                    </td>   
                                    <td width="25%" >
                                        <a href="../grupos.do?crearGpo=<bean:write name="profile" property="username" />">                        
                                            Crear                                     
                                        </a> 
                                    </td>
                                    <td width="25%">
                                        <a href="../grupos.do?eliminar=<bean:write name="profile" property="username" />">                        
                                            Eliminar                                     
                                        </a> 
                                    </td>    
                                </tr>  
                            </table>
                        </div>
                    </div>
                    <div class="divisionIzquierda" id="appInfo">
                        <jsp:include page="../rsc/appInfo.jsp" />
                    </div>
                </div>

                <div id="cajaDerecha">
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
                </div>





            </html:form>    
        </div>
    </body>
</html:html>
