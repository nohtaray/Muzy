<%@page import="jp.ac.jec.jz.gr03.servlet.SearchMusicServlet"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="jp.ac.jec.jz.gr03.dao.entityresultset.MusicResultSet" %>
<%@page import="jp.ac.jec.jz.gr03.entity.Music" %>
<%
    String keyword = (String) request.getAttribute("keyword");
    MusicResultSet musics = (MusicResultSet) request.getAttribute("musics");
%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="楽曲検索結果" />
    <c:param name="header">
        <link rel="stylesheet" type="text/css" href="css/searchMusic.css">
        <script type="text/javascript" src="js/search.js"></script>
    </c:param>
    <c:param name="content">


        <form method="GET" action="" id="music-search-form" class="form-inline">
            <div class="form-group">
                <label for="keyword">キーワード：</label>
                <input type="text" name="q" id="keyword" class="form-control" placeholder="キーワード" value="<%= keyword != null ? h(keyword) : ""%>">
            </div>
            <input type="submit" class="btn btn-primary" value="検索">
            <div id="orders">
                <label><input type="radio" name="o" value="<%= SearchMusicServlet.Order.CREATED_AT.ordinal()%>">新着順</label>
                <label><input type="radio" name="o" value="<%= SearchMusicServlet.Order.COMMENT_CREATED_AT.ordinal()%>">コメントの新しい順</label>
                <label><input type="radio" name="o" value="<%= SearchMusicServlet.Order.VIEW.ordinal()%>">再生数順</label>
                <label><input type="radio" name="o" value="<%= SearchMusicServlet.Order.MYLIST.ordinal()%>">マイリスト登録数順</label>
            </div>
        </form>
        <div>
            <% if (musics != null) {%>
            <h3>キーワード "<%= h(keyword)%>" の楽曲検索結果</h3>
            <div id="musics">
                <% for (Music music : musics) {%>
                <div class="music thumbnail clearfix media">
                    <a class="pull-left" href="MusicServlet?id=<%= music.musicId%>">
                        <img class="media-object" src="http://img.youtube.com/vi/<%= h(music.youtubeVideoId)%>/1.jpg">
                    </a>
                    <div class="media-body">
                        <h4 class="media-heading"><a href="MusicServlet?id=<%= music.musicId%>"><%= h(truncate(music.title, 100))%></a></h4>
                            <%= h(truncate(music.description, 200))%>
                    </div>
                </div>
                <% }%>
            </div>
            <% }%>
        </div>
    </c:param>
</c:import>