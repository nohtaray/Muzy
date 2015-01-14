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
    </c:param>
    <c:param name="content">

        並べ替え
        <div id="orders">
            <label><input type="radio" name="o" value="<%= SearchTagServlet.Order.MUSIC_CREATED_AT.ordinal()%>">新着順</label>
            <label><input type="radio" name="o" value="<%= SearchTagServlet.Order.MUSIC_COMMENT_CREATED_AT.ordinal()%>">コメントの新しい順</label>
            <label><input type="radio" name="o" value="<%= SearchTagServlet.Order.MUSIC_VIEW.ordinal()%>">再生数順</label>
            <label><input type="radio" name="o" value="<%= SearchTagServlet.Order.MUSIC_MYLIST.ordinal()%>">マイリスト登録数順</label>
            <label><input type="radio" name="o" value="<%= SearchTagServlet.Order.SCORE_AVERAGE.ordinal()%>">タグの評価順</label>
        </div>
        <div>
            <% if (tags != null) {%>
            タグ "<%= tagName%>" の検索結果
            <% if (keyword != null) {%>
            （キーワード "<%= keyword%>"）
            <% } %>
            <ul>
                <% for (Tag tag : tags) {%>
                <li>
                    <a href="MusicServlet?id=<%= tag.music.musicId%>"><%= tag.music.title%></a>
                </li>
                <% }%>
            </ul>
            <% }%>
        </div>
    </c:param>
</c:import>