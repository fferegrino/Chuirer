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
            
           
           function crearGrupo(){             
            
             var nombreGpo=document.getElementById("name").value;
             var desc=document.getElementById("desc").value;
             
             
             var url ="../creaGrupo?nombreGpo="+nombreGpo+"&desc="+desc+"&creador=<bean:write name="gruposBean" property="user" />";
                $.getJSON(url,function(resultado){ 
                    var respuesta=resultado.creador;
                    var no=document.getElementById("resFalse");
                    var si=document.getElementById("lista");
                    var a;
                    //borramos contenido previo
                    no.innerHTML=" ";
                    si.innerHTML=" ";
                    
                    //si no se creo pq ya existia..
                    if(respuesta==="noCreado"){                       
                       no.innerHTML="El grupo ya existe";
                    }
                    //si se creo
                    else{                    
                        a=document.createElement("a");
                        a.setAttribute("href","../grupos.do?nombre="+nombreGpo);                        
                        a.innerHTML="¡Grupo "+nombreGpo+" creado!";
                        si.appendChild(a);
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
                        <html:link styleClass="headerLink" forward="go_prof" >
                            <bean:message key="ui.start" />
                        </html:link>
                        <html:link styleClass="headerLink" forward="log_off"><bean:message key="ui.logoff" /></html:link>
                </div>
                <div class="divAcoplador"></div>
                <div id="unMensaje">
                    <div class="userInfo">                       
                        <p class="username">
                           @<bean:write name="gruposBean" property="user" />
                        </p>                        
                        <p >
                             Crear Grupo
                        </p>
                        <div class="clear"></div>
                    </div>
                    <div  > 
                        <table>
                            <tr>
                                <td>
                                      <p>
                                        Nombre:
                                      </p>                                    
                                </td>
                                  <td>
                                      <p>
                                        <input type="text" id="name"/>
                                      </p>                                    
                                </td>
                            </tr>
                            <tr>
                                <td>
                                      <p>
                                        Descrpción:
                                      </p>                                    
                                </td>
                                  <td>
                                      <p>
                                          <textarea id="desc"></textarea>
                                      </p>                                    
                                </td>
                            </tr>
                             <tr>
                                <td>
                                      <p>
                                          <input type="submit" value="Crear" class="btnAltern" onclick="crearGrupo()"/>
                                      </p>                                    
                                </td>                                  
                            </tr>
                             <tr>     
                                 <td>
                                 <p id="lista" style="color:green">                                       
                                 </p>                                    
                                 <p id="resFalse" style="color:red" >                                       
                                 </p>    
                                 </td>
                            </tr>
                        </table>
                      
                    </div>                    
                </div>
           
        </div>
                                            
                           
                      
    </body>
</html:html>
