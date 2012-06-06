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
        <script>
            $(document).ready(function(){
                //$("#alertNvosMensajes").slideUp();
                //cargaSeguidos();
            });
            var cajaContenido;
            function init(){
                cajaContenido = document.getElementById("contenido");
                $(".mensaje").remove();
            }
            
            function cargaSeguidos(){
                init();
                $("contenido").remove();
                var url =  "../obtenerSeguidos?username=<bean:write name="seguidorsresBean" property="username" filter="false" />";
                $.getJSON(url, function(data){
                    var seguidos =  data.seguidos;
                    var t = 0;
                    if(seguidos.length > 0){
                        for(;t < seguidos.length; t++){
                            cajaContenido.appendChild(cajaSeguidos(seguidos[t]));
                        }
                    }
                });
            }
            
            function cargaSeguidores(){
                init();
                $("#tituloDerecha").html("<bean:message key="ui.seguidores" />");
                var url =  "../obtenerSeguidores?username=<bean:write name="seguidorsresBean" property="username" filter="false" />";
                $.getJSON(url, function(data){
                    var seguidos =  data.seguidores;
                    var t = 0;
                    if(seguidos.length > 0){
                        for(;t < seguidos.length; t++){
                            cajaContenido.appendChild(cajaSeguidos(seguidos[t]));
                        }
                    }
                });
            }
            
            function cargaSeguidos(){
                init();
                $("#tituloDerecha").html("<bean:message key="ui.seguidos" />");
                var url =  "../obtenerSeguidos?username=<bean:write name="seguidorsresBean" property="username" filter="false" />";
                $.getJSON(url, function(data){
                    var seguidos =  data.seguidos;
                    var t = 0;
                    if(seguidos.length > 0){
                        for(;t < seguidos.length; t++){
                            cajaContenido.appendChild(cajaSeguidos(seguidos[t]));
                        }
                    }
                });
            }
            
            function cajaSeguidos(seg){
                var divS = document.createElement("div");divS.className="mensaje";
                var pUserneim = document.createElement("p");
                pUserneim.className = "cUserneim";
                pUserneim.innerHTML = "<a href='../usuario/"+seg.seguido +"'>@"+seg.seguido+"</a>";
                divS.appendChild(pUserneim);
                var divClear =  document.createElement("div");
                divS.id="div_" + seg.seguido;
                if(seg.s != undefined){
                    var pData = document.createElement("p");
                    pData.className =  "cData";
                    var inputUnfollow = document.createElement("input");
                    inputUnfollow.type = "button";
                    inputUnfollow.className ="btnAltern";
                    inputUnfollow.setAttribute("onclick", "unfolou('"+seg.seguido+"')");
                    inputUnfollow.value = "<bean:message key="ui.unfollow" />";
                    pData.appendChild(inputUnfollow);
                    divS.appendChild(pData);
                }
                divClear.className ="clear";
                divS.appendChild(divClear);
                return divS;
            }
            
            function unfolou(follow){
                var url = "../seguirOnoSeguir?u="+follow+"&seguir=false";
                //alert(url);
                $.getJSON(url,function(resultado){
                    if(resultado.success){
                        $("#div_"+follow).slideUp();
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
                        <img id="profilepic" src="<bean:write name="seguidorsresBean" property="imgUrl" />" alt="Imagen de perfil de <bean:write name="seguidorsresBean" property="username" filter="false" />"  />
                        <p id="usuario"><span class="username"><bean:write name="seguidorsresBean" property="username" filter="false" /></span> <span class="realname">(<bean:write name="seguidorsresBean" property="realname" filter="false" />)</span></p>
                        <p id="descripcion"><bean:write name="seguidorsresBean" property="desc" filter="false" /></p>
                        <p id="blogOweb"><a href=" <bean:write name="seguidorsresBean" property="url" /> "> <bean:write name="seguidorsresBean" property="url" /></a></p>
                    </div>

                    <div class="divisionIzquierda">
                        <div class="stats">
                            <p id="lbNumeroMensajes"> <bean:write name="seguidorsresBean" property="mensajes" filter="false" /> </p>
                            <p><bean:message key="ui.messages" /></p>
                        </div>
                        <a href="../seguidos.do?usuario=<bean:write name="seguidorsresBean" property="username" filter="false" />">
                            <div class="stats">
                                <p id="lbNumeroSeguidos"> <bean:write name="seguidorsresBean" property="seguidos" filter="false" /> </p>
                                <p><bean:message key="ui.following" /></p>
                            </div>
                        </a>
                        <div class="stats">
                            <p id="lbNumeroSeguidores"> <bean:write name="seguidorsresBean" property="seguidores" filter="false" /> </p>
                            <p><bean:message key="ui.followers" /></p>
                        </div>
                    </div>
                    <div class="divisionIzquierda" id="appInfo">
                        <jsp:include page="../rsc/appInfo.jsp" />
                    </div>
                </div>

                <div id="cajaDerecha">
                    <logic:equal name="seguidorsresBean" property="visible" value="true">  
                        <div id="tituloDerecha">
                            Elige cuales deseas ver:
                        </div>
                        <div id="contenido">
                            <div id="alertNvosMensajes">
                                <input type="button" class="btnAltern" value="<bean:message key="ui.seguidos" />" onclick="$('#contenido').remove('.mensaje'); cargaSeguidos();">
                                <input type="button" class="btnAltern" value="<bean:message key="ui.seguidores" />" onclick="$('#contenido').remove('.mensaje'); cargaSeguidores();">
                            </div>
                        </div>
                        <div id="masMensajes">
                            &nbsp;
                        </div>
                    </logic:equal>
                    <logic:notEqual name="seguidorsresBean" property="visible" value="true">
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
