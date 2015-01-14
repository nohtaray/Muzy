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
        <script type="text/javascript" src="js/search.js"></script>
    </c:param>
    <c:param name="content">

        
        <form method="GET" action="">
            <input type="text" name="q" value="<%= keyword != null ? keyword : ""%>">
            <input type="submit" value="検索">
            <div id="orders">
                <label><input type="radio" name="o" value="<%= SearchMusicServlet.Order.CREATED_AT.ordinal()%>">新着順</label>
                <label><input type="radio" name="o" value="<%= SearchMusicServlet.Order.COMMENT_CREATED_AT.ordinal()%>">コメントの新しい順</label>
                <label><input type="radio" name="o" value="<%= SearchMusicServlet.Order.VIEW.ordinal()%>">再生数順</label>
                <label><input type="radio" name="o" value="<%= SearchMusicServlet.Order.MYLIST.ordinal()%>">マイリスト登録数順</label>
            </div>
        </form>
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
            <% }%>
        </div>
    </c:param>
</c:import>