<%-- 
    Document   : inicio
    Created on : 24-mar-2012, 21:28:25
    Author     : Antonio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        
        <h1>Hola usuario <bean:write name="login" property="rol" /> <bean:write name="login" property="name" /></h1>
    </body>
</html>
