<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/layout/admin.jsp">
    <c:param name="title" value="管理" />
    <c:param name="header">
    </c:param>
    <c:param name="content">
        <h3>メニュー</h3>
        <ul>
            <li><a href="AdminArtistServlet">アーティスト一覧</a></li>
            <li><a href="AdminUserServlet">ユーザ一覧</a></li>
            <li><a href="AdminMusicServlet">楽曲一覧</a></li>
        </ul>
    </c:param>
</c:import>