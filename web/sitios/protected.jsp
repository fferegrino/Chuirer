<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><bean:message key="app.name"/> - <bean:message key="ui.whops" /> <bean:message key="ui.usrNotExists" /></title>
        <html:base/> 
        <link href="../estilos/general.css" rel="stylesheet" type="text/css" />
        <link href="../estilos/hyf.css" type="text/css" rel="stylesheet" />
        <jsp:include page="../rsc/colorBody.jsp"></jsp:include>
    </head>
    <body>
        <jsp:include page="../rsc/header.jsp"></jsp:include>
        <div class="innerDiv">
            <h1><bean:message key="ui.whops" /> <bean:message key="ui.accountProtected" /></h1>
        </div>
    </body>
</html>
