<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="ページのタイトル" />
    <c:param name="header">
    </c:param>
    <c:param name="content">
        <form method="post" action="EditUserServlet">
        <input type="text" name="name" value="${name}"><br>
        <input type="text" name="email" value="${email}"><br>
        <input type="text" name="newpass1" placeholder="新しいパスワード"><br>
        <input type="text" name="newpass2" placeholder="もう1度入力してください"><br>
        <textarea placeholder="自己紹介文" name="introduction">${introduction}</textarea>
        <br>
        <br>
        　　サムネイル画像:<br>
        &nbsp;&nbsp;&nbsp;<input type="file" />
        <br><br><br><br>
        <input type="password" name="nowpass" placeholder="現在のパスワード" required><br>
        &nbsp;&nbsp;&nbsp;<input type="submit" value="更新">
        </form>
    </c:param>
</c:import>