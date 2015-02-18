<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="退会" />
    <c:param name="header">
    </c:param>
    <c:param name="content">
        <h3>Muzyを退会する</h3>
        <p>
            本当に退会しますか？<br>
            この動作は取り消せません。よろしければ以下のボタンを押してください。
        </p>
        <form action="" method="POST">
            <input class="btn btn-danger" type="submit" value="登録情報削除">
        </form>
    </c:param>
</c:import>