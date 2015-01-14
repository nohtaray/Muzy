<%@page import="jp.ac.jec.jz.gr03.servlet.SearchMusicServlet"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="jp.ac.jec.jz.gr03.dao.entityresultset.MusicResultSet" %>
<%@page import="jp.ac.jec.jz.gr03.entity.Music" %>
<%
    String keyword = (String) request.getAttribute("keyword");
    String tag = (String) request.getAttribute("tag");
    MusicResultSet musics = (MusicResultSet) request.getAttribute("musics");
%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="楽曲検索結果" />
    <c:param name="header">
        <script type="text/javascript" src="js/search.js"></script>
    </c:param>
    <c:param name="content">

        並べ替え
        <div id="orders">
            <label><input type="radio" name="o" value="<%= SearchMusicServlet.Order.CREATED_AT.ordinal()%>">新着順</label>
            <label><input type="radio" name="o" value="<%= SearchMusicServlet.Order.COMMENT_CREATED_AT.ordinal()%>">コメントの新しい順</label>
            <label><input type="radio" name="o" value="<%= SearchMusicServlet.Order.VIEW.ordinal()%>">再生数順</label>
            <label><input type="radio" name="o" value="<%= SearchMusicServlet.Order.MYLIST.ordinal()%>">マイリスト登録数順</label>
        </div>
        <% if (musics != null) { %>
        <p>
            <% if (keyword != null && tag == null) {%>
            キーワード "<%= keyword%>" の検索結果
            <% } else if (keyword == null && tag != null) {%>
            タグ "<%= tag%>" の検索結果
            <% } else { %>
            なにかがおかしいです。<br>
            このメッセージを消すには、このJSPまたは呼び出し元のサーブレットを修正してください。
            <% } %>
        </p>
        <ul>
            <%
                Music music;
                while ((music = musics.readRow()) != null) {
                    out.println("<li>");
                    out.println("<a href=\"MusicServlet?id=" + music.musicId + "\">" + music.title + "</a>");
                    out.println(music.youtubeVideoId);
                    out.println("</li>");
                }
            %>
        </ul>
        <% }%>

    </c:param>
</c:import>