<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="jp.ac.jec.jz.gr03.servlet.SearchArtistServlet"%>
<%@page import="jp.ac.jec.jz.gr03.dao.entityresultset.ArtistResultSet" %>
<%@page import="jp.ac.jec.jz.gr03.entity.Artist" %>
<%
    String keyword = (String) request.getAttribute("keyword");
    ArtistResultSet artists = (ArtistResultSet) request.getAttribute("artists");
%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="アーティスト検索" />
    <c:param name="header">
        <script type="text/javascript" src="js/search.js"></script>
    </c:param>
    <c:param name="content">

        <form method="GET" action="">
            <input type="text" name="q" value="<%= keyword != null ? h(keyword) : ""%>">
            <input type="submit" value="検索">
            <div id="orders">
                <label><input type="radio" name="o" value="<%= SearchArtistServlet.Order.CREATED_AT.ordinal()%>">新着順</label>
                <label><input type="radio" name="o" value="<%= SearchArtistServlet.Order.MYLIST.ordinal()%>">マイリスト登録数順</label>
                <label><input type="radio" name="o" value="<%= SearchArtistServlet.Order.VOTE.ordinal()%>">投票数順</label>
            </div>
        </form>
            
            <% if (artists != null) { %>
            <%= h(keyword)%> の検索結果
        <ul>
            <% for (Artist artist : artists) {%>
            <li>
                <%= artist.artistId%>
                <a href="ArtistServlet?id=<%= artist.artistId%>"><%= h(artist.name)%></a>
            </li>
            <% }%>
        </ul>
        <% } %>

    </c:param>
</c:import>