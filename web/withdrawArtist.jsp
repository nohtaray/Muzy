<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="アーティスト登録解除" />
    <c:param name="header">
    </c:param>
    <c:param name="content">
        <p>
            アーティスト情報を削除します。あなたのプロフィールと投稿した楽曲はすべて削除されます。<br>
            この動作は取り消せません。よろしければ以下のボタンを押してください。
        </p>
        <form action="" method="POST">
            <input type="submit" value="アーティスト登録解除">
        </form>
    </c:param>
</c:import>