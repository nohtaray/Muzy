<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="jp.ac.jec.jz.gr03.dao.entityresultset.MusicResultSet" %>
<%@page import="jp.ac.jec.jz.gr03.entity.Music" %>
<%
    String keyword = (String) request.getAttribute("keyword");
    String tag = (String) request.getAttribute("tag");
%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="楽曲検索結果" />
    <c:param name="header">
    </c:param>
    <c:param name="content">

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
                MusicResultSet musics = (MusicResultSet) request.getAttribute("musics");
                Music music;
                while ((music = musics.readRow()) != null) {
                    out.println("<li>");
                    out.println("<a href=\"MusicServlet?id=" + music.musicId + "\">" + music.title + "</a>");
                    out.println(music.youtubeVideoId);
                    out.println("</li>");
                }
            %>
        </ul>

    </c:param>
</c:import>