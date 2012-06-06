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
                recuperaGpo();                
                
                
            });
            
            function recuperaGpo(){   
                var url ="../recuperaEntidadGrupo?nombreGpo=<bean:write name="gruposBean" property="nombreGpo" />";
                $.getJSON(url,function(resultado){               
                    var nombre = resultado.nombreGpo;                   
                    var creador=resultado.creador; 
                    var desc=resultado.descripcion;
                    var numUs=resultado.numUsers;
                    var users=resultado.usuarios;
                    var banderaBoton=resultado.banderaBoton;
                    var aux; //almacena si es seguidor,seguidor,o sin seguidores
                    var i=0;
                    var div;
                    
                    //para empezar, borramos, si existe, el boton seguir, para evitar que se muestre muchas veces
                    div=document.getElementById("seguirBtn");
                    div.removeChild(div.lastChild);
                    
                    //actualizamos cantidad seguidores
                    if(numUs>1){
                         $("#noSeguidores").html(numUs+" seguidores");
                         aux="Seguidores:";
                    }
                    else if(numUs==1){
                         $("#noSeguidores").html(numUs+" seguidor");
                          aux="Seguidor:";
                    }
                    else if(numUs==0){
                         $("#noSeguidores").html("Sin seguidores");
                          aux="Sin seguidores";
                    }
                   
                   
                   //actualizamos descripcion
                   $("#descr").html("Descrpcion: <br/>"+desc);
                   
                   //actualizamos creador
                   var a= document.createElement("a");
                   var p= document.createElement("p");
                    p.innerHTML="Creado por ";                
                    a.setAttribute("href","../public.do?<bean:write name="gruposBean" property="creador" />");
                    a.innerHTML=creador;
                    p.appendChild(a);
                    $("#lista").html(p);
                   
                   //actualizamos seguidores
                   //seguidores
                   var seg=document.getElementById("seguidores");
                   //eliminamos los seguidores mostrados para enseñar los nuevos
                   seg.removeChild(seg.lastChild);
                   p= document.createElement("p");
                   p.innerHTML=aux+" <br/>";
                   p.id="lista";
                   var ol= document.createElement("ol");
                   var li;
                  
                   for(i=0;i<users.length;i++){
                       a= document.createElement("a");
                       a.setAttribute("href","../public.do?"+users[i]);
                       a.innerHTML=users[i];                       
                       li=document.createElement("li");                       
                       li.appendChild(a);
                       ol.appendChild(li);
                       
                   }
                   p.appendChild(ol);
                   seg.appendChild(p);
                   
                   //si la banderaBoton es true, quiere decir que el grupo ya se sigue; no se muestra boton
                   if(!banderaBoton){                      
                       var button=document.createElement("button");
                       button.className="btnAltern";
                       button.setAttribute("onclick", "seguir()");
                       button.innerHTML="Seguir";
                       div.appendChild(button);
                   }
                   //si el grupo ya se sigue,se muestra el boton "Dejar de Seguir"
                   else{
                       var button=document.createElement("button");
                       button.className="btnAltern";
                       button.setAttribute("onclick", "dejarDeSeguir()");
                       button.innerHTML="Dejar de Seguir";
                       div.appendChild(button);
                   }
                    
                    }
                );
                var timer=setTimeout(recuperaGpo,1000);
                
            }
            
            function seguir(){
                var url ="../seguirGpo?grupo=<bean:write name="gruposBean" property="nombreGpo" />";
                $.getJSON(url,function(resultado){ 
                        var band=resultado.bandera;
                        var div=document.getElementById("seguirBtn");
                        if(!band){
                            div.innerHTML="Error, intenta seguir este grupo mas tarde"
                        }
                        
                }
                );
            }
            
            function dejarDeSeguir(){
                var url ="../dejarDeSeguir?grupo=<bean:write name="gruposBean" property="nombreGpo" />";
                $.getJSON(url,function(resultado){ 
                        var band=resultado.bandera;
                        var div=document.getElementById("seguirBtn");
                        if(!band){
                            div.innerHTML="Error, intenta seguir este grupo mas tarde"
                        }
                        
                }
                );
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
                            <bean:write name="gruposBean" property="nombreGpo" />  
                        </p>  
                        <p id="noSeguidores">                                                    
                        </p>
                        <div class="clear"></div>
                    </div>
                     <div>
                        <p id="descr">                                                  
                        </p>
                        <div id="lista"><!aqui va el creador>                            
                        </div>
                        <div id="seguidores">                                                         
                        </div>
                     </div>
                     <div id="seguirBtn">                        
                     </div>
                                   
                </div>            
        </div>
    </body>
</html:html>
