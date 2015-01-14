<%@page import="jp.ac.jec.jz.gr03.dao.entityresultset.TagResultSet"%>
<%@page import="jp.ac.jec.jz.gr03.entity.Tag"%>
<%@page import="jp.ac.jec.jz.gr03.servlet.SearchMusicServlet"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="jp.ac.jec.jz.gr03.dao.entityresultset.MusicResultSet" %>
<%@page import="jp.ac.jec.jz.gr03.entity.Music" %>
<%
    String keyword = (String) request.getAttribute("keyword");
    String tagName = (String) request.getAttribute("tagName");
    MusicResultSet musics = (MusicResultSet) request.getAttribute("musics");
    TagResultSet tags = (TagResultSet) request.getAttribute("tags");
%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="楽曲検索結果" />
    <c:param name="header">
        <script type="text/javascript" src="js/search.js"></script>
    </c:param>
    <c:param name="content">

        <%-- TODO: 見づらいのでリファクタリング --%>
        並べ替え
        <div id="orders">
            <label><input type="radio" name="o" value="<%= SearchMusicServlet.Order.CREATED_AT.ordinal()%>">新着順</label>
            <label><input type="radio" name="o" value="<%= SearchMusicServlet.Order.COMMENT_CREATED_AT.ordinal()%>">コメントの新しい順</label>
            <label><input type="radio" name="o" value="<%= SearchMusicServlet.Order.VIEW.ordinal()%>">再生数順</label>
            <label><input type="radio" name="o" value="<%= SearchMusicServlet.Order.MYLIST.ordinal()%>">マイリスト登録数順</label>
            <% if (tagName != null) {%>
            <label><input type="radio" name="o" value="<%= SearchMusicServlet.Order.TAG_SCORE.ordinal()%>">タグの評価順</label>
            <% } %>
        </div>
        <div>
            <% if (musics != null) {%>
            キーワード "<%= keyword%>" の検索結果
            <ul>
                <% for (Music music : musics) {%>
                <li>
                    <a href="MusicServlet?id=<%= music.musicId%>"><%= music.title%></a>
                </li>
                <% }%>
            </ul>
            <% } else if (tags != null) {%>
            タグ "<%= tagName%>" の検索結果
            <ul>
                <% for (Tag t : tags) {%>
                <li>
                    <a href="MusicServlet?id=<%= t.music.musicId%>"><%= t.music.title%></a>
                </li>
                <% }%>
            </ul>
            <% } else { %>
            なにかがおかしいです。<br>
            このメッセージを消すには、このJSPまたは呼び出し元のサーブレットを修正してください。
            <% }%>
        </div>
    </c:param>
</c:import>