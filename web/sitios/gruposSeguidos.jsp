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
               gruposSeguidos();              
            });
            
          
            function gruposSeguidos(){   
                var url="../recuperaGposSeguidos?usuario=<bean:write name="gruposBean" property="user"/>"; 
                $.getJSON(url,function(resultado){             
                                
                    var lista=document.getElementById("lista");//lista de gpos seguidos
                    //eliminamos contenido actual
                    lista.removeChild(lista.lastChild);
                    var noGseguidos=resultado.noGposSeguidos;
                    var seguidos=resultado.gposSeguidos;
                    var p=document.createElement("p");
                    var ol= document.createElement("ol");
                    var li;
                    var a;
                    var i=0;
                    
                
                for(i=0;i<noGseguidos;i++){                   
                       a= document.createElement("a"); 
                       p=document.createElement("p");
                       a.setAttribute("href", "../grupos.do?nombre="+seguidos[i]);
                       a.innerHTML=seguidos[i];                   
                       li=document.createElement("li");                       
                       li.appendChild(a);   
                       p.appendChild(li);
                       ol.appendChild(p);
                }
                //p.appendChild(ol);
                lista.appendChild(ol);                
                
            });
          setTimeout(gruposSeguidos,5000);

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
                            @<bean:write name="gruposBean" property="user" />  
                        </p> 
                        <p>Grupos Seguidos</p>
                          
                       
                    <div class="clear"></div>
                    </div>
                     <div id="lista"><!aqui va la lista de grupos seguidos!!>     
                         jajaja
                     </div>   
                                   
                </div>            
        </div>
    </body>
</html:html>
