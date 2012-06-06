<%@page import="chuirer.beans.login"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<html:html lang="true">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><bean:message key="ui.loginTitle"/></title>
        <html:base/>
        <script src="../js/jquery-1.7.1.js"></script>
        <script src="../js/watermark.js"></script>
        <link href="../estilos/hyf.css" type="text/css" rel="stylesheet" />
        <link rel="stylesheet" href="../estilos/login.css" type="text/css" />
        <jsp:include page="../rsc/colorBody.jsp"></jsp:include>
    </head>
    <body>
        <div id="login">
            
            <table>
                <tr>
                    <td>
                        <div id="logo_image">
                        </div>
                    </td>
                    <td>
                        <div id="form">
                            <html:form action="/login">
                                <fieldset>
                                    <legend>
                                        
                                        <bean:message key="ui.login" />
                                    </legend>
                                    <table>
                                        <tr>
                                            <td colspan="2">
                                                <html:text styleClass="txt rotate1"  styleId="name" property="name" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="2">
                                                <html:password styleClass="txt rotate2" styleId="pass" property="password" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="2">
                                                <bean:write property="error" filter="false" name="login" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <html:submit property="login" styleClass="btn" style="margin:0 auto;">
                                                    <bean:message key="ui.login" />
                                                </html:submit>  
                                            </td>
                                           <td>
                                           
                                                    
                                                <span class="izquierda">
                                                    <html:submit styleClass="linkBtn" value="Registrate" property="reg">
                                                        <bean:message key="ui.register" />
                                                    </html:submit>
                                                </span>

                                            </td>
                                        </tr>
                                    </table>
                                </fieldset>
                            </html:form>
                        </div>
                    </td>
                </tr>
            </table>
        </div>  
        <script>
            $('#login').css({top:'50%',left:'50%',margin:'-'+( $('#login').height() / 2)+'px 0 0 -'+($('#login').width() / 2)+'px'});
            $("#pass").Watermark(' <bean:message key="ui.passwd" />  ');
            $("#name").Watermark(' <bean:message key="ui.username" />  ');
        </script>
    </body>
</html:html>
