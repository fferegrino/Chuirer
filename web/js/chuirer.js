/**
 * Chuirer sources
 */

function showTweetBox(action,id) {
    //  Checamos si existe el div
    var divTxt = document.getElementById("divTweetBox");
    if(divTxt == undefined){
        divTxt = document.createElement("div");
        divTxt.id = "divTweetBox";
        //Mensaje
        var spanMensajeTxt = document.createElement("span");
        spanMensajeTxt.id = "tweetBoxMsg";
        divTxt.appendChild(spanMensajeTxt);
        //  Lo añadimos al documento
        document.getElementById("body").appendChild(divTxt);
    }
    else{
        var spanMensajeTxt = document.getElementById("tweetBoxMsg");
    }
    if(action == "reply"){
        
    }
//
}

function fav(faveado){
    var url = "../faveaMensaje?idM="+faveado;
    $.getJSON(url, function(data){
        if(data.success){
            alert("Has calificado con éxito este mensaje");
        }
        else{
            alert(data.error);
        }
    });
}

function creaDivMensaje(mensaje){
    var div = document.createElement("div");
    var pUsuario = document.createElement("p");
    pUsuario.className="cUserneim";
    pUsuario.innerHTML="<a href='../usuario/" +mensaje.usuario +"'>@"+mensaje.usuario+"</a>";
    var pContenido = document.createElement("p");
    pContenido.className = "cMensaje";
    pContenido.innerHTML =  mensaje.contenido;
    var pFecha = document.createElement("p");
    pFecha.className = "cData";
    //pFecha.innerHTML =  mensaje.fecha_publicacion + " via " + mensaje.via;
    pFecha.innerHTML = "<a href='../message.do?"+mensaje.usuario+ "."+ mensaje.id +"'>"+ mensaje.fecha_publicacion + " via " + mensaje.via+"</a>";
    var divClear = document.createElement("div");
    divClear.className = "clear";
    div.appendChild(pUsuario);
    div.appendChild(pFecha);
    div.appendChild(divClear);
    div.appendChild(pContenido);
    /**BUTTONSSSSS**/
    var buttons = ["fav","respuesta"];
    var botones = buttons.length;
    for(var i = 0; i< botones;i++){
        var spanReply = document.createElement("a");
        spanReply.className ="iconoMensaje "+buttons[i];
        spanReply.setAttribute("onclick", buttons[i]+ "('" + mensaje.usuario+ "."+ mensaje.id  + "')" );
        div.appendChild(spanReply);
    }
    /************/
    if(mensaje.destacado){
        div.className="mensaje destacado";
    }else{
        div.className="mensaje";
    }
    return div;
} 