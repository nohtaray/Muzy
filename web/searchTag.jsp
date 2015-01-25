<%@page import="jp.ac.jec.jz.gr03.servlet.SearchTagServlet"%>
<%@page import="jp.ac.jec.jz.gr03.dao.entityresultset.TagResultSet"%>
<%@page import="jp.ac.jec.jz.gr03.entity.Tag"%>
<%@page import="jp.ac.jec.jz.gr03.servlet.SearchTagServlet"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String tagName = (String) request.getAttribute("tagName");
    String keyword = (String) request.getAttribute("keyword");
    TagResultSet tags = (TagResultSet) request.getAttribute("tags");
%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="楽曲検索結果" />
    <c:param name="header">
        <script type="text/javascript" src="js/search.js"></script>
        <script type="text/javascript" src="js/searchTag.js"></script>
    </c:param>
    <c:param name="content">


        <form method="GET" action="" id="tag-search-form">
            タグ：<input type="text" name="t" value="<%= tagName != null ? h(tagName) : ""%>">
            <input type="submit" value="検索">
            <br>
            キーワードで絞込：<input type="text" id="keyword" value="<%= keyword != null ? h(keyword) : ""%>">
            <div id="orders">
                <label><input type="radio" name="o" value="<%= SearchTagServlet.Order.MUSIC_CREATED_AT.ordinal()%>">新着順</label>
                <label><input type="radio" name="o" value="<%= SearchTagServlet.Order.MUSIC_COMMENT_CREATED_AT.ordinal()%>">コメントの新しい順</label>
                <label><input type="radio" name="o" value="<%= SearchTagServlet.Order.MUSIC_VIEW.ordinal()%>">再生数順</label>
                <label><input type="radio" name="o" value="<%= SearchTagServlet.Order.MUSIC_MYLIST.ordinal()%>">マイリスト登録数順</label>
                <label><input type="radio" name="o" value="<%= SearchTagServlet.Order.SCORE_AVERAGE.ordinal()%>">タグの評価順</label>
            </div>
        </form>
            
        <% if (tags != null) {%>
        <h3>タグ "<%= h(tagName)%>" の検索結果</h3>
        <% if (keyword != null) {%>
        （キーワード "<%= h(keyword)%>"）
        <% } %>
        <div id="musics">
            <% for (Tag tag : tags) {%>
            <div class="music thumbnail clearfix media">
                <a class="pull-left" href="MusicServlet?id=<%= tag.music.musicId%>">
                    <img class="media-object" src="http://img.youtube.com/vi/<%= h(tag.music.youtubeVideoId)%>/1.jpg">
                </a>
                <div class="media-body">
                    <h4 class="media-heading"><a href="MusicServlet?id=<%= tag.music.musicId%>"><%= h(truncate(tag.music.title, 30))%></a></h4>
                        <%= h(truncate(tag.music.description, 200))%>
                </div>
            </div>
            <% }%>
        </div>
        <% }%>
    </c:param>
</c:import>