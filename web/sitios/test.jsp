<%-- 
    Document   : test
    Created on : 20-may-2012, 8:35:24
    Author     : fferegrino
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="../js/jquery-1.7.1.js"></script>
        <title>JSP Page</title>
        <script>
            var mi = 0;
            var total = 300;
            var timeee= null;
            function publica(){
                mi++;
                //                init();
                var url = "../publicaMensaje?msg=Mensahe"+mi+"&idU=fferegrino";
                //thobber.style.display = "block";
                $.getJSON(url,function(data){
                    
                });
                timeee = setTimeout("publica()",300);
                if(mi >= total){
                    clearTimeout(timeee);
                }
                $("h1").text(mi);
            }
            $(document).ready(function(){
                publica();
            });
        </script>
    </head>
    <body>
        <h1>Hello World!</h1>
    </body>
</html>
