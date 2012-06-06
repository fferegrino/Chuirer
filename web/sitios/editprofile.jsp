<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<!DOCTYPE html>
<html:html lang="true">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <html:base/>
        <title><bean:message key="ui.editProfile" /> - <bean:message key="app.name" /></title>
        <link href="../estilos/general.css" rel="stylesheet" type="text/css" />
        <link href="../estilos/registro.css" rel="stylesheet" type="text/css" />
        <link href="../estilos/hyf.css" type="text/css" rel="stylesheet" />
        <link href="../estilos/validaciones.css" type="text/css" rel="stylesheet" />
        <script src="../js/jquery-1.7.1.js"></script>
        <script src="../js/vanadium.js"></script>
        <script>
            var apellido,nombre,email,descr,user,privado,urlWeb;
            function init(){
                apellido = document.getElementById("apellido");
                nombre = document.getElementById("real");
                email =  document.getElementById("email");
                urlWeb = document.getElementById("url");
                user =  document.getElementById("user");
                descr =  document.getElementById("desc");
                privado = document.getElementById("private");
            }
            
            function valida(){
                save();
                return false;
            }
            function save(){
                //alert($("#camp").validate());
                init();
                
                $("#cargando").fadeIn();
                var url = "../modificaUsuario?u="+user.value+"&a="+apellido.value
                    +"&r="+nombre.value+"&e="+email.value+"&d="+descr.value+"&p="+privado.checked+"&l="+urlWeb.value;
                $.getJSON(url,function(data){
                    $("#cargando").fadeOut();
                });
                //
            }
        </script>
        <style>
            .cargando{
                position: absolute;
                background-color: #FFF;
                opacity: 0.8;
                width: 900px;
                height: 100%;
                display: none;
            }
        </style>
    </head>
    <body>

        <jsp:include page="../rsc/header.jsp"></jsp:include>
            <div class="innerDiv">

                <html:form styleId="camp"  action="/editProfile">
                <div id="usrMenuBar">
                    <html:link styleClass="headerLink" forward="go_prof">
                        <bean:message key="ui.back" />
                    </html:link>
                    <html:link styleClass="headerLink" forward="log_off"><bean:message key="ui.logoff" /></html:link>
                    </div>
                    <div id="cargando" class="cargando">
                        loading
                    </div>
                    <table id="contents">
                        <tr>
                            <td class="centro" colspan="2">
                            <bean:message key="ui.editProfile" /> <span class="userneim"><bean:write property="username" name="editProfileBean" /></span>
                            <html:hidden property="username" styleId="user" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <bean:message key="ui.realName" />:
                        </td>
                        <td>
                            <html:text styleId="real" property="realName" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <bean:message key="ui.lastName" />:
                        </td>
                        <td>
                            <html:text styleId="apellido" property="lastName" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <bean:message key="ui.email" />:
                        </td>
                        <td>
                            <div id="errus6" class="errorDeValidacion"><bean:message key="ui.validation.email" /></div>
                            <div id="errus5" class="errorDeValidacion"><bean:message key="ui.validation.required" /></div>
                            <div id="errus9" class="errorDeValidacion"><bean:message key="ui.validation.invalidChars" /></div>
                            <html:text  property="email" styleId="email" styleClass=":email;;errus6 :required;;errus5 :format;/^[A-Za-z0-9@.]+$/i;errus9" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <bean:message key="ui.website" />:
                        </td>
                        <td>
                            <div id="errus1" class="errorDeValidacion"><bean:message key="ui.validation.invalidWeb" /></div>
                            <html:text  property="url" styleClass=":url;;errus1" styleId="url" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <bean:message key="ui.description" />:
                        </td>
                        <td>
                            <textarea id="desc" maxlength="140"><bean:write property="descripcion" name="editProfileBean"/>
                            </textarea>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <bean:message key="ui.private" />:
                        </td>
                        <td>
                            <html:checkbox property="privado" styleId="private"></html:checkbox>
                            </td>
                        </tr>
                        <tr>
                            <td class="centro" colspan="2">
                                <input type="button" class="btn green" value="<bean:message key="ui.saveProfile" />" onclick="save()" /> 
                        </td>
                    </tr>
                </table>
            </html:form>
        </div>
    </body>
</html:html>

