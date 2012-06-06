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
<meta name="viewport" content="width=device-width, initial-scale=1"> 
        <link rel="stylesheet" href="http://code.jquery.com/mobile/1.1.0/jquery.mobile-1.1.0.min.css" />
        <link rel="stylesheet" href="../estilos/mobile.css" />
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js">
        </script>
        <script src="http://code.jquery.com/mobile/1.1.0/jquery.mobile-1.1.0.min.js">
        </script>
    </head>
    <body>
        <div id="login">

            <table>
                <tr>
                    <td>
                        <div id="form">
                            <html:form action="/login">
                                        <div data-role="page" id="page1">
                                            <div data-role="content">
                                                <div>
                                                    <b>
                                                        <html:link styleClass="appNameLink" forward="go_prof"><bean:message key="app.name" /></html:link>
                                                    </b>
                                                </div>
                                                <div>
                                                    <b>
                                                        <bean:message key="ui.login" />
                                                    </b>
                                                </div>
                                                <div>
                                                    <b><bean:write property="error" filter="false" name="login" /></b>
                                                </div>
                                                <div data-role="fieldcontain">
                                                    <fieldset data-role="controlgroup">
                                                        <label for="textinput1">
                                                            <bean:message key="ui.username" /> 
                                                        </label>
                                                        <html:text styleClass="txt rotate1"  styleId="textinput1" property="name" />
                                                    </fieldset>
                                                </div>
                                                <div data-role="fieldcontain">
                                                    <fieldset data-role="controlgroup">
                                                        <label for="textinput2">
                                                            <bean:message key="ui.passwd" /> 
                                                        </label>
                                                        <html:password styleClass="txt rotate2" styleId="textinput2" property="password" />
                                                    </fieldset>
                                                </div>

                                                <html:submit property="login" styleClass="btn" style="margin:0 auto;">
                                                    <bean:message key="ui.login" />
                                                </html:submit> 
                                                <html:submit styleClass="linkBtn" value="Registrate" property="reg">
                                                    <bean:message key="ui.register" />
                                                </html:submit>

                                            </div>
                                        </div>
                                        <script>
                                            //App custom javascript
                                        </script>
                            </html:form>
                        </div>
                    </td>
                </tr>
            </table>
        </div>  
    </body>
</html:html>
