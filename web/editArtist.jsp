<%@page import="jp.ac.jec.jz.gr03.entity.Artist"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    Artist artist = (Artist) request.getAttribute("artist");
%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="ページのタイトル" />
    <c:param name="header">
    </c:param>
    <c:param name="content">
        アーティスト情報編集

        <form method="post" action="EditArtistServlet">
            <input type="text" name="name" value="<%= h(artist.name)%>"><br>
            <textarea placeholder="アーティストプロフィール" name="introduction"><%= h(artist.introduction)%></textarea><br>
            <input type="submit" value="更新">
        </form>

        <a href="WithdrawArtistServlet">アーティスト登録解除</a>
    </c:param>
</c:import>
