<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="パスワード登録・変更" />
    <c:param name="header">
        <script type="text/javascript" src="js/password.js"></script>
        <link rel="stylesheet" type="text/css" href="css/password.css">
    </c:param>
    <c:param name="content">
        
        <h2>パスワード登録・変更</h2>
        <form action="" method="POST" id="password-form">
            <div id="error" class="hidden error"></div>
            <input type="password" name="password" id="pass1" placeholder="パスワード" required><br>
            <input type="password" id="pass2" placeholder="もう一度入力" required><br>
            <input type="submit" value="登録">
        </form>
    </c:param>
</c:import>