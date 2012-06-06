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
            
           
           function autoCompletar(){             
               //limpia msjs de visitar otros perfiles
               limpiaMsjs();
               
               var table=document.getElementById("name");             
               var prefijo=document.getElementById("prefijo");
               var tr;
               var td;
               var url ="../autocompletadoAmigos?prefijo="+prefijo.value;
               
                $.getJSON(url,function(resultado){
                    var total=resultado.total;
                    var coincidencias=resultado.coincidencias;
                    limpiaUsuarios(); //limpia resultados anteriore
                    for(var i=0;i<total;i++){
                        tr=document.createElement("tr");
                        td=document.createElement("td");
                        td.innerHTML=coincidencias[i];
                        td.value=coincidencias[i];
                        //estilos  
                        td.className="autoCo";
                        td.onmouseout = function() {this.className='mouseOver';};
			td.onmouseover = function() {this.className='mouseOut';};                        
                        td.onclick=function() { userSeleccionado(this); } ;
                        tr.appendChild(td);
                        table.appendChild(tr);
                    }
                }
                );    
           }
            
         function limpiaUsuarios() { //limpia resultados previos
                var table=document.getElementById("name");
		var ind = table.childNodes.length;
		for (var i = ind - 1; i >= 0 ; i--) {
			table.removeChild(table.childNodes[i]);
		}		
	}
        
        function userSeleccionado(td) {
                var prefijo=document.getElementById("prefijo");
		prefijo.value =td.value;
		limpiaUsuarios();
                verPerfil()
	}
        
        function limpiaMsjs(){
            var p=document.getElementById("lista");           
            p.innerHTML=" ";
           
        }
        
        function verPerfil(){
            var prefijo=document.getElementById("prefijo");
            var link=document.getElementById("lista");
            var a=document.createElement("a");
            a.setAttribute("href", "../public.do?"+prefijo.value);
            a.innerHTML="Visitar perfil de "+prefijo.value;
            link.appendChild(a);
            
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
                           @<bean:write name="amigosBean" property="user" />
                        </p>                        
                        <p >
                             Buscar Amigos
                        </p>
                        <div class="clear"></div>
                    </div>
                    <div  > 
                        <table>
                            <tr>
                                <td>
                                      <p>
                                       Nombre de Usuario:
                                      </p>                                    
                                </td>
                            </tr>
                            <tr>
                                 <td></td>
                                  <td>                                      
                                        <div>
                                            <input type="text" id="prefijo" onkeyup="autoCompletar()"/>
                                        </div>    
                                        <div >  
                                            <table id="name" class="tablaAC">   <!autocompletado>                                             
                                            </table>
                                        </div>                                             
                                </td>
                            </tr> 
                             <tr>     
                                 <td>
                                    <p id="lista">                                       
                                    </p> 
                                 </td>
                            </tr>
                        </table>
                      
                    </div>                    
                </div>
           
        </div>
                                            
                           
                      
    </body>
</html:html>
