<%@page import="jp.ac.jec.jz.gr03.entity.Artist"%>
<%@page import="jp.ac.jec.jz.gr03.entity.User"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    User user = (User) request.getAttribute("user");
    Artist artist = (Artist) request.getAttribute("artist");
    boolean isArtist = artist != null;
%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="マイページ" />
    <c:param name="header">
        <%-- スクリプトのインポートなど。例↓
        <script type="text/javascript" src="js/template.js"></script>
        <link rel="stylesheet" type="text/css" href="css/template.css">
        --%>
    </c:param>
    <c:param name="content">

        <h2>マイページ</h2>

        name: <%= user.name%>
        <ul>
            <li><a href="UserServlet?id=<%= user.userId%>">あなたの公開プロフィール</a></li>
            <li><a href="EditUserServlet">ユーザ情報編集</a></li>
            <li><a href="MyListServlet">マイリスト</a></li>
        </ul>
        <% if (isArtist) {%>
        <div>
            アーティスト登録があります
            （<a href="ArtistServlet?id=<%= artist.artistId%>"><%= artist.name%></a>）
            <ul>
                <li><a href="MyMusicServlet">投稿した楽曲</a></li>
                <li><a href="NewMusicServlet">楽曲投稿</a></li>
                <li><a href="EditArtistServlet">アーティスト情報編集</a></li>
            </ul>
        </div>
        <% } else { %>
        <ul>
            <li><a href="SignUpArtistServlet">アーティスト登録</a></li>
        </ul>
        <% }%>

    </c:param>
</c:import>