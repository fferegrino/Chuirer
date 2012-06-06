<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<!DOCTYPE html>
<html:html lang="true">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><bean:message key="app.name"/> - <bean:message key="ui.start" /></title>
        <html:base/> 
        <link href="../estilos/general.css" rel="stylesheet" type="text/css" />
        <link href="../estilos/hyf.css" type="text/css" rel="stylesheet" />
        <link href="../estilos/adminPage.css" type="text/css" rel="stylesheet" />
        <link type="text/css" href="../estilos/overcast/jquery-ui-1.8.18.custom.css" rel="stylesheet">
        <script src="../js/jquery-1.7.1.js"></script>
        <script src="../js/jquery-ui-1.8.18.custom.min.js"></script>
        <script>
            var txtNombre;
            var txtPasswd;
            var txtNombreReal;
            var txtApellido;
            var txtEmail;
            var lablRegister;
            var textSearch;
            $(document).ready(function(){
                txtApellido = document.getElementById("userlastname");
                txtNombreReal = document.getElementById("userrealname");
                txtEmail = document.getElementById("useremail");
                txtNombre = document.getElementById("usertext");
                lablRegister = document.getElementById("registerMessage");
                textSearch = document.getElementById("searchUser");
            });
            
            function sendUser(){
                var url = "../inscribeUsuario?u="
                    +txtNombre.value+"&a="+txtApellido.value
                    +"&r="+txtNombre.value+"&e="+txtEmail.value;
                $("#userAdd").slideUp();
                $.getJSON(url, function(data){
                    var inscrito= data.success;
                    if(inscrito == true){                   
                        $("#userAdd").slideDown(); 
                    }
                    else{
                        lablRegister.innerHTML = data.error;
                        lablRegister.className="malo";
                        $("#userAdd").slideDown(); 
                    }
                });
            }
            
            function buscaUser(){
                var url = "../regresaUsuarios?u="+textSearch.value;
                var div = document.getElementById("resultUser");
                div.innerHTML = "buscando";
                $.getJSON(url, function(data){
                    div.innerHTML = "";
                    var users = data;
                    for( x  = 0 ; x < users.length; x++){
                        var usrNme=users[x].username;
                        var pButtons = document.createElement("p");
                        var selectButton = document.createElement("input");
                        selectButton.type ="button";
                        selectButton.id =  "select_" + users[x].username;
                        selectButton.setAttribute("onclick", "selectButton_Click(this)");
                        selectButton.value = "<bean:message key="ui.select" />";
                        selectButton.className = "btn";
                        var deleteButton = document.createElement("input");
                        deleteButton.type ="button";
                        deleteButton.id =  "delete_" + users[x].username;
                        deleteButton.setAttribute("onclick", "deleteButton_Click(this)");
                        deleteButton.className = "btn";
                        deleteButton.value = "<bean:message key="ui.delete" />";
                        pButtons.appendChild(selectButton);
                        pButtons.appendChild(deleteButton);
                        var divUser = document.createElement("div");
                        divUser.className = "userdiv";
                        divUser.id = "div_"+selectButton.id;
                        var pUsername = document.createElement("p");
                        pUsername.className="user";
                        pUsername.innerHTML = "@" + users[x].username;
                        var pDesc = document.createElement("p");
                        pDesc.innerHTML = users[x].description;
                        var pDesc_edit = document.createElement("p");
                        pDesc_edit.innerHTML = "<textarea id='desc_"+usrNme+"'>"+users[x].description+"</textarea>";
                        var pNom = document.createElement("p");
                        pNom.innerHTML = users[x].realName;
                        var pNom_edit = document.createElement("p");
                        pNom_edit.innerHTML = "<input type='text' id='nom_"+usrNme+"' value='"+users[x].realName+"' />";
                        var pLas = document.createElement("p");
                        pLas.innerHTML = users[x].lastName;
                        var pLas_edit = document.createElement("p");
                        pLas_edit.innerHTML = "<input type='text' id='las_"+usrNme+"' value='"+users[x].lastName +"' />";
                        var pFecha = document.createElement("p");
                        pFecha.innerHTML = users[x].registerDate;
                        var pButtons_edit = document.createElement("p");
                        var selectButtonedit  = document.createElement("input");
                        selectButtonedit.type ="button";
                        selectButtonedit.id =  "confirm_" + users[x].username;
                        selectButtonedit.setAttribute("onclick", "confirmButton_Click(this)");
                        selectButtonedit.value = "<bean:message key="ui.confirm" />";
                        selectButtonedit.className = "btn";
                        var deleteButtonedit  = document.createElement("input");
                        deleteButtonedit.type ="button";
                        deleteButtonedit.id =  "cancel_" + users[x].username;
                        deleteButtonedit.setAttribute("onclick", "cancelButton_Click(this)");
                        deleteButtonedit.className = "btn";
                        deleteButtonedit.value = "<bean:message key="ui.cancel" />";
                        pButtons_edit.appendChild(selectButtonedit);
                        pButtons_edit.appendChild(deleteButtonedit);                        
                        pDesc.className="editMode";
                        pDesc_edit.className="editMode";
                        pButtons.className="editMode";
                        pNom.className="editMode";
                        pLas.className="editMode";
                        pNom_edit.className="editMode";
                        pLas_edit.className="editMode";
                        pButtons_edit.style.display = "none";
                        pDesc_edit.style.display = "none";
                        pNom_edit.style.display = "none";
                        pLas_edit.style.display = "none";
                        pButtons_edit.className="editMode";
                        divUser.appendChild(pUsername);
                        divUser.appendChild(pNom);
                        divUser.appendChild(pNom_edit);
                        divUser.appendChild(pLas);
                        divUser.appendChild(pLas_edit);
                        divUser.appendChild(pDesc);
                        divUser.appendChild(pDesc_edit);
                        divUser.appendChild(pFecha)
                        divUser.appendChild(pButtons);
                        divUser.appendChild(pButtons_edit);
                        div.appendChild(divUser);
                        divUser.style.display = "none";
                        $("#"+divUser.id).slideDown();
                    }
                });
            }
            
            
            function cancelButton_Click(sender){
                var pacquiao = "#div_"+sender.id;
                pacquiao = pacquiao.replace("cancel_","select_");
                $(pacquiao + " .editMode").slideToggle();
            }
            function selectButton_Click(sender){
                var pacquiao = "#div_"+sender.id;
                $(pacquiao + " .editMode").slideToggle();
            }
            function confirmButton_Click(sender){
                var usr = sender.id.replace("confirm_","");
                var apetxt = $("#las_"+usr).val();
                var nomtxt = $("#nom_"+usr).val();
                var desc = $("#desc_"+usr).val();
                 var url = "../modificaUsuario?u="+usr+"&a="+apetxt
                    +"&r="+nomtxt+"&e="+txtEmail.value+"&d="+desc;
                $.getJSON(url, function(data){
                    buscaUser();
                });
            }
            var a_borrar;
            function deleteButton_Click(sender){
                a_borrar = sender.id.substring(7);
                $("#aborrar").html(a_borrar);
                $("#confirmacionBorrar").removeAttr("title");
                $("#confirmacionBorrar").attr("title", "<bean:message key="ui.delete" /> "+a_borrar);
                $("#confirmacionBorrar").dialog({ closeOnEscape: true
                    ,resizeble: false
                    ,buttons: { "<bean:message key="ui.confirm" />": function(){ 
                            var url = "../borraUsuarios?u="+a_borrar;
                            $.getJSON(url, function(data){
                                if(data.success ==  true){
                                    $("#confirmacionBorrar").dialog("close");
                                    buscaUser();
                                    a_borrar = "";
                                }
                            });
                                                                                                            
                        }
                        ,"<bean:message key="ui.cancel" />" : function() { $(this).dialog("close"); a_borrar =""; } 
                    }
                });
            }
            
            function confirmDelete(sender){
                alert(sender.id);
            }
            
            $(function() {
                $( "#tabs" ).tabs();
            });
        </script>
    </head>
    <body>
        <jsp:include page="../rsc/header.jsp"></jsp:include>
        <div class="innerDiv">
            <html:form>
                <div id="usrMenuBar">
                    <html:submit styleId="adm" property="adminOn">
                        Volver
                    </html:submit>
                    <html:submit property="logOff" >
                        <bean:message key="ui.logoff" />
                    </html:submit>
                </div>


                <div id="tabs">
                    <ul>
                        <li><a href="#tabs-1">Añadir usuario</a></li>
                        <li><a href="#tabs-2">Consulta de usuarios</a></li>
                    </ul>
                    <div id="tabs-1">
                        <div id="userAdd">
                            <table>
                                <tr>
                                    <td>
                                        <bean:message key="ui.username" />
                                    </td>
                                    <td>
                                        <input type="text" id="usertext" name="usertext" />
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <bean:message key="ui.realName" />
                                    </td>
                                    <td>
                                        <input type="text" id="userrealname" name="userrealname" />
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <bean:message key="ui.lastName" />
                                    </td>
                                    <td>
                                        <input type="text" id="userlastname" name="userlastname" />
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <bean:message key="ui.email" />
                                    </td>
                                    <td>
                                        <input type="text" id="useremail" name="useremail" />
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <span id="registerMessage"></span>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <input class="btn" type="button" onclick="sendUser()" value="<bean:message key="ui.confirm" />" />
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                    <div id="tabs-2">
                        <div>
                            <input type="text" onchange="buscaUser()" id="searchUser" />
                            <div id="resultUser">

                            </div>
                        </div>
                        <div class="clear"></div>
                    </div>
                </div>
                <div id="confirmacionBorrar" style="display: none">
                    <span id="aborrar"></span>
                </div>
            </html:form>
        </div>
    </body>
</html:html>

