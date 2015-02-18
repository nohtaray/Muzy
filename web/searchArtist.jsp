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

        <form method="GET" action="" id="artist-search-form" class="form-inline">
            
            <div class="form-group">
                <label for="keyword">キーワード：</label>
                <input type="text" name="q" id="keyword" class="form-control" placeholder="キーワード" value="<%= keyword != null ? h(keyword) : ""%>">
            </div>
            <input type="submit" class="btn btn-primary" value="検索">
            <div id="orders">
                <label><input type="radio" name="o" value="<%= SearchArtistServlet.Order.CREATED_AT.ordinal()%>">新着順</label>
                <label><input type="radio" name="o" value="<%= SearchArtistServlet.Order.MYLIST.ordinal()%>">マイリスト登録数順</label>
                <label><input type="radio" name="o" value="<%= SearchArtistServlet.Order.VOTE.ordinal()%>">投票数順</label>
            </div>
        </form>

        <% if (artists != null) {%>
        <h3>キーワード "<%= h(keyword)%>" のアーティスト検索結果</h3>
        <div id="artists">
            <% for (Artist artist : artists) {%>
            <div class="artist thumbnail clearfix media">
                <a class="pull-left" href="ArtistServlet?id=<%= artist.artistId%>">
                    <img class="media-object" src="<%= h(artist.user.iconImageFile)%>" width="90" height="90">
                </a>
                <div class="media-body">
                    <h4 class="media-heading"><a href="ArtistServlet?id=<%= artist.artistId%>"><%= h(truncate(artist.name, 50))%></a></h4>
                        <%= h(truncate(artist.introduction, 200))%>
                </div>
            </div>
            <% }%>
        </div>
        <% }%>

    </c:param>
</c:import>