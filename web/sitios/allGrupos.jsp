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
                cuentaGpos();                
            });
            
            function cuentaGpos(){   
                var url ="../cuentaAllGpos";
                $.getJSON(url,function(resultado){               
                    var num = resultado.numeroGpos;                   
                    var gpos=resultado.nombres; 
                    var li;
                    var list=document.getElementById("lista");
                    var p;
                    var a;
                   
                    //actualizamos cantidad gpos
                    $("#noGpos").html(num+" grupos encontrados");
                    
                    //lista
                    //borramos la lista anterior
                    list.removeChild(list.lastChild)
                    
                    //creamos la lista
                    ol=document.createElement("ol");
                    list.appendChild(ol)                    
                    
                    //hacemos cada itemo
                    for(i=0;i<gpos.length;i++){
                        p=document.createElement("p");
                       // p.className="enlaces";
                       // p.id="enlaces";
                        a=document.createElement("a");                        
                        li=document.createElement("li");
                        p.innerHTML =gpos[i];
                        a.setAttribute("href", "../grupos.do?nombre="+gpos[i]);
                        a.appendChild(p);
                        li.appendChild(a);                        
                        ol.appendChild(li);
                    }
                    
                    }
                );
                setTimeout(cuentaGpos,6000);
                
            }
            
            
        </script>      
    </head>
    <body>
        <jsp:include page="../rsc/header.jsp"></jsp:include>
        <div class="innerDiv">                   
                <div id="usrMenuBar">                    
                        <html:link styleClass="headerLink" forward="go_prof" >
                            <bean:message key="ui.start" />
                        </html:link>
                        <html:link styleClass="headerLink" forward="log_off"><bean:message key="ui.logoff" /></html:link>
                </div>
                <div class="divAcoplador"></div>
                <div id="unMensaje">
                    <div class="userInfo">                       
                        <p class="username">
                           Grupos
                        </p>                        
                        <p id="noGpos" >
                            <bean:write name="gruposBean" property="noTotalGpos" /> grupos encontrados
                        </p>
                        <div class="clear"></div>
                    </div>
                    <div id="lista">                            
                    </div>
                    
                </div>
           
        </div>
    </body>
</html:html>
