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
        
        name: <%= user.name %>
        <% if (isArtist) { %>
        <div>
            アーティスト登録があります
            （<a href="ArtistServlet?id=<%= artist.artistId %>"><%= artist.name %></a>）
        </div>
        <% } %>
        
    </c:param>
</c:import>