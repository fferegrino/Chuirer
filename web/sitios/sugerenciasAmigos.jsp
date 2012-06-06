<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<!DOCTYPE html>
<html:html lang="true">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <html:base/>
        <title><bean:message key="app.name" /> - <bean:message key="ui.groups" /></title>
        <link href="../estilos/general.css" rel="stylesheet" type="text/css" />
        <link href="../estilos/hyf.css" type="text/css" rel="stylesheet" />    
        <link href="../estilos/grupos.css" type="text/css" rel="stylesheet" /> 
        <script src="../js/jquery-1.7.1.js"></script>
        <script src="../js/jquery.textareaCounter.plugin.js"></script>    
        <script>
            $(document).ready(function(){
               amigosSugeridos();              
            });
            
          
            function amigosSugeridos(){   
                var url="../obtenerSugerenciasAmigos?usuario=<bean:write name="amigosBean" property="user"/>"; 
                $.getJSON(url,function(resultado){             
                                
                    var lista=document.getElementById("lista");//lista de amigosSugeridos
                    //eliminamos contenido actual
                    lista.removeChild(lista.lastChild);                   
                    var sugerencias=resultado.sugerencias;
                    var noSugerencias=resultado.noSugerencias;
                    var p=document.createElement("p");
                    var ul= document.createElement("ul");
                    var li;
                    var a;
                    var i=0;
                    
                
                for(i=0;i<noSugerencias;i++){                   
                       a= document.createElement("a"); 
                       p=document.createElement("p");
                       a.setAttribute("href", "../public.do?"+sugerencias[i]);
                       a.innerHTML=sugerencias[i];                   
                       li=document.createElement("li");                       
                       li.appendChild(a);   
                       p.appendChild(li);
                       ul.appendChild(p);
                }                
                lista.appendChild(ul);                
                
            });
          setTimeout(amigosSugeridos,5000);

   }                  
                  
            
            
        </script>      
    </head>
    <body>
        <jsp:include page="../rsc/header.jsp"></jsp:include>
        <div class="innerDiv">
            
                <div id="usrMenuBar">
                    <logic:notEmpty scope="session" name="usuarioLogueado">
                        <html:link styleClass="headerLink" forward="go_prof">
                            <bean:message key="ui.start" />
                        </html:link>
                        <html:link styleClass="headerLink" forward="log_off"><bean:message key="ui.logoff" /></html:link>
                    </logic:notEmpty>
                </div>
                <div class="divAcoplador"></div>
                <div id="unMensaje">
                    <div class="userInfo">                       
                        <p class="username">
                            @<bean:write name="amigosBean" property="user" />  
                        </p> 
                        <p>Amigos Sugeridos</p>
                          
                       
                    <div class="clear"></div>
                    </div>
                     <div id="lista"><!aqui va la lista de amigos sugeridos!!>     
                         jajaja
                     </div>   
                                   
                </div>            
        </div>
    </body>
</html:html>
