<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<!DOCTYPE html>
<html:html lang="true">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><bean:message key="ui.welcome" /> <bean:message key="ui.to" /> <bean:message key="app.name" /></title>
        <html:base/>
        <link href="../estilos/general.css" rel="stylesheet" type="text/css" />
        <link href="../estilos/hyf.css" type="text/css" rel="stylesheet" />
        <link href="../estilos/registro.css" type="text/css" rel="stylesheet" />
        <link href="../estilos/validaciones.css" type="text/css" rel="stylesheet" />
        <script src="../js/jquery-1.7.1.js"></script>
        <script src="../js/vanadium.js"></script>
        <jsp:include page="../rsc/colorBody.jsp"></jsp:include>
        <script>
            $(document).ready(function(){
                $("#more").click(function(){
                    var acceptId = this.id;
                    $("#masDatos").slideDown('slow',function(){
                        document.getElementById(acceptId).type = 'submit';
                        $('#'+acceptId).val('<bean:message key="ui.accept_dis.informal"/>');
                    });   
                });
            });
        </script>
    </head>
    <body>
        <jsp:include page="../rsc/header.jsp"></jsp:include>
        <div class="innerDiv">
            <div class="frmRegistro">
                <html:form action="/continueReg" method="post" enctype="multipart/form-data">
                    <fieldset>
                        <legend>Registro</legend>
                        <!-- <table>
                            <tr>
                                <td colspan="2"> -->
                        <p> 
                            <bean:message key="ui.welcome" /> <bean:message key="ui.to" /> <span> <bean:message key="app.name" /></span> 
                            <bean:write name="registro" property="nombreUsuario" />
                        </p>
                        <!--  </td> 
                      </tr>
                      <tr>
                          <td colspan="2"> -->
                        <p>
                            Antes de que continuemos, ¿nos quieres dar un poco más de información sobre ti?
                        </p>
                        <!--      </td>
                          </tr>
                      </table> -->
                        <div style="display: none" id="masDatos">
                            <table>
                                <tr>
                                    <td>
                                        <bean:message key="ui.addLinks" />
                                    </td>
                                    <td>
                                        <div id="errus1" class="errorDeValidacion"><bean:message key="ui.validation.invalidWeb" /></div>
                                        <html:text  property="url" styleClass=":url;;errus1" styleId="url" />
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <bean:message key="ui.addDescription" />
                                    </td>
                                    <td>
                                        <div id="errus2" class="errorDeValidacion"><bean:message key="ui.validation.descriptionlength" /></div>
                                        <html:textarea property="descripcion"  styleClass=":max_length;160;errus2" />
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <bean:message key="ui.addImage" />
                                    </td>
                                    <td>
                                        <html:file property="image"></html:file> 
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <table>
                            <tr>
                                <td>
                                    <html:button styleId="more" property="continue" styleClass="btn green">
                                        <bean:message key="ui.accept.informal" />
                                    </html:button>
                                </td>
                                <td>
                                    <html:submit property="reg"   styleClass="btn">
                                        <bean:message key="ui.cancel.informal" />
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
