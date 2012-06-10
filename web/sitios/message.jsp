<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<!DOCTYPE html>
<html:html lang="true">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <html:base/>
        <title><bean:message key="app.name" /> - <bean:message key="ui.messages" /></title>
        <link href="../estilos/general.css" rel="stylesheet" type="text/css" />
        <link href="../estilos/hyf.css" type="text/css" rel="stylesheet" />
        <script src="../js/jquery-1.7.1.js"></script>
        <script>
            //  Recuperar calificaciones:
            function recuperaCalificaciones(){
                var url = "../recuperaCalificacionesMsg?idM=<bean:write name="messageBean" property="idUnico" />";
                $.getJSON(url,function(data){
                    var fff= data;
                    with(fff){
                        if(calificadores == 0){
                            $('#calif').text("Nadie ha calificado este mensaje");
                        }else{
                            $('#calif').text(calificacion);
                            var califS = calificaciones;
                            for(var i = 0; i < califS.length; i++){
                                with(califS[i]){
                                    var divCont = document.createElement("div");
                                    var pUsuario = document.createElement("span");
                                    pUsuario.className="cUserneim";
                                    pUsuario.innerHTML="<a href='../usuario/" +calificador +"'>@"+calificador+"</a>";
                                    divCont.appendChild(pUsuario);
                                    var pCal = document.createElement("span");
                                    pCal.className="cCalif";
                                    pCal.innerHTML="&nbsp;" +calificacion;
                                    divCont.appendChild(pCal);
                                    document.getElementById("calificaciones").appendChild(divCont);
                                }
                            }
                        }
                    }
                });
            }
            recuperaCalificaciones();
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
                <div style="height: 50px;"></div>
                <div id="unMensaje" class="centradoM">
                    <div class="userInfo">
                        <img src="..<bean:write name="messageBean" property="imgUrl" />" id="profilepic" />
                        <p class="username">
                            <a href="../public.do?<bean:write name="messageBean" property="usuario" />">@<bean:write name="messageBean" property="usuario" /></a>
                        </p>
                        <p class="realname">
                            <bean:write name="messageBean" property="realname" />
                        </p>
                        <div class="clear"></div>
                    </div>
                    <p class="contenido"><bean:write name="messageBean" property="contenido" /></p>
                    <p class="mensajeInfo"><bean:write name="messageBean" property="fechaPublicacion" /> via <bean:write name="messageBean" property="via" /> </p>
                </div>
                <div class="centradoM">
                    <bean:message key="ui.calificaciones" />: <span id="calif"></span>
                    <div id="calificaciones" style="max-height: 100px; overflow-y: scroll;"></div>
                </div>
                <div class="midBox" id="appInfo">
                    <jsp:include page="../rsc/appInfo.jsp" />
                </div>
            </html:form> 
        </div>
    </body>
</html:html>
