<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/layout/admin.jsp">
    <c:param name="title" value="管理" />
    <c:param name="header">
    </c:param>
    <c:param name="content">
        <ul>
            <li><a href="AdminArtistServlet">アーティスト管理</a></li>
            <li><a href="AdminUserServlet">ユーザ管理</a></li>
            <li><a href="AdminMusicServlet">楽曲管理</a></li>
        </ul>
    </c:param>
</c:import>