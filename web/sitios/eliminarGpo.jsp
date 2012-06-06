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
           
           function autoCompletar(){
               //limpamos los msjs anteriores, si hay..
                limpiaMsjs();
                
               var table=document.getElementById("name");             
               var prefijo=document.getElementById("prefijo");
               var tr;
               var td;
               var url ="../autocompletadoGrupos?prefijo="+prefijo.value;
               
                $.getJSON(url,function(resultado){
                    var total=resultado.total;
                    var coincidencias=resultado.coincidencias;
                    limpiaGrupos(); //limpia resultados anteriore
                    for(var i=0;i<total;i++){
                        tr=document.createElement("tr");
                        td=document.createElement("td");
                        td.innerHTML=coincidencias[i];
                        td.value=coincidencias[i];
                        //estilos  
                        td.className="autoCo";
                        td.onmouseout = function() {this.className='mouseOver';};
			td.onmouseover = function() {this.className='mouseOut';};                        
                        td.onclick=function() { gpoSeleccionado(this); } ;
                        tr.appendChild(td);
                        table.appendChild(tr);
                    }
                }
                );    
           }
            
         function limpiaGrupos() { //limpia resultados previos
                var table=document.getElementById("name");
		var ind = table.childNodes.length;
		for (var i = ind - 1; i >= 0 ; i--) {
			table.removeChild(table.childNodes[i]);
		}		
	}
        
        function gpoSeleccionado(td) {
                var prefijo=document.getElementById("prefijo");
		prefijo.value =td.value;
		limpiaGrupos();
	}
        
        function limpiaMsjs(){
            var p=document.getElementById("lista");
            var p2=document.getElementById("resFalse");
                
                //borramos mensajes anteriores
                p.innerHTML=" ";
                p2.innerHTML=" ";
        }
        
        function eliminaGrupo(){
            var  prefijo=document.getElementById("prefijo");
            var url="../borrarGpo?nombreGpo="+prefijo.value+"&user=<bean:write name="gruposBean" property="user" />";
            
        $.getJSON(url,function(resultado){
                var band=resultado.bandera;
                var p=document.getElementById("lista");
                var p2=document.getElementById("resFalse");
                
                //limpamos los msjs anteriores, si hay..
                limpiaMsjs();
                
                if(band=="esCreador"){
                    
                    p.innerHTML="El grupo se eliminó correctamente";
                }
                else if(band=="noExisteGrupo"){
                   
                    p2.innerHTML="El grupo a borrar no existe";   
                }
                else if(band=="noEsCreador"){
                    
                    p2.innerHTML="No puedes borrar el grupo, no eres el creador";   
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
                             Eliminar Grupo
                        </p>
                        <div class="clear"></div>
                    </div>
                    <div  > 
                        <table>
                            <tr>
                                <td>
                                      <p>
                                        Grupo a eliminar:
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
                                      <p>
                                          <input type="submit" value="Eliminar" class="btnAltern" onclick="eliminaGrupo()"/>
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
