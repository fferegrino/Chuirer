<%@page import="java.util.Random"%>
<%
    String [] colores = {"15CAB1","FA9F29","79CC3C","DED9D9","86ABD7","7BA29E"};
    Random rnd = new Random();
    
    out.println("<style>"
            + "body{ "
            + "background-color: #"+colores[rnd.nextInt(colores.length -1)] + "; "
            + "}" 
            +"#logo_image{"
            + "background-image: url(../images/sitio/logos/" + rnd.nextInt(3) +".jpg);"
            //+"background-image: url(../images/sitio/logos/"+ rnd.nextInt(3) +".jpg);"
            +"}"
            + "</style>");
    
%>