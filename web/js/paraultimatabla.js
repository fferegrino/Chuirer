var filas_total = 0;
var columnas_total = 0;
var celdas_total = 0;

function campoColsChange(){
    var columnas = document.getElementById('nCols').value;
    var fila = document.getElementById('nFil').value;
    $("#infoTablas").html(columnas * fila + ' celda(s) se van a generar');
}

function clickAceptaTabla(){
    var columnas = document.getElementById('nCols').value;
    var fila = document.getElementById('nFil').value;
    var celdas = columnas * fila;
    if(celdas != NaN && celdas != 0){
        if(celdas > 25)
            alert('¡Muchas celdas!');
        columnas_total = columnas;
        filas_total = fila;
        celdas_total = filas_total * columnas_total;
        for(var x = 0; x < celdas_total; x++)
            creaCampo();
        
    }
}


var i = 1;
var j = 1;
function creaCampo(){
    var contenedorPrincipal = document.createElement("div");
    contenedorPrincipal.className = "tdProp";
    var minispan = document.createElement("span");
    if(j > filas_total){
        j=1;
        i++;
    }
    minispan.innerHTML = "Propiedades de la celda " + i +"," + j;
    contenedorPrincipal.appendChild(minispan);
    
    var id = ((i-1) * filas_total) + j;
    
    var p1  = document.createElement("p");
    var color_ = "color_";
    var label = document.createElement("label");
    label.setAttribute("for", color_+id)
    label.innerHTML = "Color: ";
    var color = document.createElement("input");
    color.setAttribute("type", "text");
    color.name = color_+id;
    color.id =color_+id;
    p1.appendChild(label);
    p1.appendChild(color);
    contenedorPrincipal.appendChild(p1)
    
    
    var p2  = document.createElement("p");
    var contenido_="contenido_";
    var labelContenido = document.createElement("label");
    labelContenido.setAttribute("for", contenido_+id)
    labelContenido.innerHTML = "Texto: ";
    var contenido = document.createElement("input");
    contenido.setAttribute("type", "text");
    contenido.setAttribute("Value", i+","+j);
    contenido.name = contenido_+id;
    contenido.id =contenido_+id;
    p2.appendChild(labelContenido);
    p2.appendChild(contenido);
    contenedorPrincipal.appendChild(p2);
    
    var p4  = document.createElement("p");
    var rowspan_="rowspan_";
    var labelRowspan = document.createElement("label");
    labelRowspan.setAttribute("for", rowspan_+id)
    labelRowspan.innerHTML = "Rowspan: ";
    var rowspan = document.createElement("input");
    rowspan.setAttribute("type", "text");
    rowspan.name = rowspan_+id;
    rowspan.id =rowspan_+id;
    p4.appendChild(labelRowspan);
    p4.appendChild(rowspan);
    contenedorPrincipal.appendChild(p4)
    
    var p5  = document.createElement("p");
    var valign_="valign_";
    var labelValign = document.createElement("label");
    labelValign.setAttribute("for", valign_+id)
    labelValign.innerHTML = "Valign: ";
    var valign = document.createElement("input");
    valign.setAttribute("type", "text");
    valign.name = valign_+id;
    valign.id =valign_+id;
    p5.appendChild(labelValign);
    p5.appendChild(valign);
    contenedorPrincipal.appendChild(p5)
    
    
    var p6  = document.createElement("p");
    var align_="align_";
    var labelalign = document.createElement("label");
    labelalign.setAttribute("for", align_+id)
    labelalign.innerHTML = "Align: ";
    var align = document.createElement("input");
    align.setAttribute("type", "text");
    align.name = align_+id;
    align.id =align_+id;
    p6.appendChild(labelalign);
    p6.appendChild(align);
    contenedorPrincipal.appendChild(p6)
    
    var p3  = document.createElement("p");
    var colspan_="colspan_";
    var labelColspan = document.createElement("label");
    labelColspan.setAttribute("for", colspan_+id)
    labelColspan.innerHTML = "Colspan: ";
    var colspan = document.createElement("input");
    colspan.setAttribute("type", "text");
    colspan.name = colspan_+id;
    colspan.id =colspan_+id;
    p3.appendChild(labelColspan);
    p3.appendChild(colspan);
    contenedorPrincipal.appendChild(p3)
    
    document.getElementById("tdContenedor").appendChild(contenedorPrincipal);
    j++;
}