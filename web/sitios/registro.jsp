<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="chuirer.beans.registro"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<!DOCTYPE html>
<html:html lang="true">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><bean:message key="app.name" /> - <bean:message key="ui.register" /></title>
        <html:base/>
        <link href="../estilos/general.css" rel="stylesheet" type="text/css" />
        <link href="../estilos/hyf.css" type="text/css" rel="stylesheet" />
        <link href="../estilos/registro.css" type="text/css" rel="stylesheet" />
        <link href="../estilos/validaciones.css" type="text/css" rel="stylesheet" />
        <script src="../js/jquery-1.7.1.js"></script>
        <script src="../js/vanadium.js"></script>
        <script>
            
        </script>
        <jsp:include page="../rsc/colorBody.jsp"></jsp:include>
    </head>
    <body>
        <jsp:include page="../rsc/header.jsp"></jsp:include>
        <div class="innerDiv">
            <div class="frmRegistro">
                <html:form action="/register">
                    <fieldset>
                        <legend>Registro</legend>
                        <table>
                            <tr>
                                <td colspan="2">
                                    <bean:write property="error" filter="false" name="registro" />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <bean:message key="ui.username" />
                                </td>
                                <td>
                                    <div id="errus7" class="errorDeValidacion"><bean:message key="ui.validation.userexists"/></div>
                                    <div id="errus2" class="errorDeValidacion"><bean:message key="ui.validation.usernamelength" /></div>
                                    <div id="errus1" class="errorDeValidacion"><bean:message key="ui.validation.required" /></div>
                                    <div id="errus8" class="errorDeValidacion"><bean:message key="ui.validation.invalidChars" /></div>
                                      <html:text styleClass=":ajax;/Login/UsrExistente :required;;errus1 :min_length;3;errus2 :max_length;16;errus2 :format;/^[A-Za-z0-9]+$/i;errus8 :wait;500" property="nombreUsuario" />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <bean:message key="ui.realName" />
                                </td>
                                <td>
                                    <html:text property="nombreReal" />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <bean:message key="ui.lastName" />
                                </td>
                                <td>
                                    <html:text property="apellido" />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <bean:message key="ui.email" />
                                </td>
                                <td>
                                    <div id="errus6" class="errorDeValidacion"><bean:message key="ui.validation.email" /></div>
                                    <div id="errus5" class="errorDeValidacion"><bean:message key="ui.validation.required" /></div>
                                    <div id="errus9" class="errorDeValidacion"><bean:message key="ui.validation.invalidChars" /></div>
                                    <html:text styleClass=":email;;errus6 :required;;errus5 :format;/^[A-Za-z0-9@.]+$/i;errus9 :wait;500" property="email" />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <bean:message key="ui.passwd" />
                                </td>
                                <td>
                                    <div id="errus4" class="errorDeValidacion"><bean:message key="ui.validation.passwordlength" /></div>
                                    <div id="errus3" class="errorDeValidacion"><bean:message key="ui.validation.required" /></div>
                                    <html:password styleClass=":required;;errus3 :min_length;6;errus4 :max_length;16;errus4 :wait;500" property="pass" />
                                </td>
                            </tr>
                            <tr>
                                <td>

                                </td>
                                <td>
                                    <html:submit styleClass="btn">
                                        <bean:message key="ui.register" />
                                    </html:submit>
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                </html:form>
            </div>
        </div>
    </body>
</html:html>
