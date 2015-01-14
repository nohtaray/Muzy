<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="jp.ac.jec.jz.gr03.dao.entityresultset.ArtistResultSet" %>
<%@page import="jp.ac.jec.jz.gr03.entity.Artist" %>
<%
    String keyword = (String) request.getAttribute("keyword");
    ArtistResultSet artists = (ArtistResultSet) request.getAttribute("artists");
%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="アーティスト検索" />
    <c:param name="header">
    </c:param>
    <c:param name="content">
        
        <p>
            <%= keyword %> の検索結果
        </p>
        <ul>
        <% for (Artist artist : artists) { %>
        <li>
            <%= artist.artistId%>
            <a href="ArtistServlet?id=<%= artist.artistId %>"><%= artist.name%></a>
        </li>
        <% } %>
        </ul>
        
    </c:param>
</c:import>