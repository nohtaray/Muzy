<%-- 
    Document   : admin

    管理画面の共通の部分
--%>

<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${param.title}</title>
        <script type="text/javascript" src="js/lib/jquery-2.1.1.min.js"></script>
        <script type="text/javascript" src="js/application.js"></script>
        <link rel="stylesheet" type="text/css" href="css/application.css">
        ${param.header}
    </head>
    <body>
        <h1>管理画面</h1>
            <div id="content">
                ${param.content}
            </div>
        </div>
    </body>
</html>
