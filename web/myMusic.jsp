<%@page import="jp.ac.jec.jz.gr03.dao.entityresultset.MusicResultSet"%>
<%@page import="jp.ac.jec.jz.gr03.entity.Music"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    MusicResultSet musics = (MusicResultSet) request.getAttribute("musics");
%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="投稿楽曲" />
    <c:param name="header">
    </c:param>
    <c:param name="content">

        あなたが投稿した楽曲
        <ul>
        <% for (Music music : musics) { %>
        <li>
            <a href="MusicServlet?id=<%= music.musicId %>"><%= music.title %></a>
            [<a href="EditMusicServlet?id=<%= music.musicId %>">編集</a>]
        </li>
        <% }%>
        </ul>

    </c:param>
</c:import>