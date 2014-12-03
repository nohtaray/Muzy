<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="jp.ac.jec.jz.gr03.dao.entityresultset.MusicResultSet" %>
<%@page import="jp.ac.jec.jz.gr03.entity.Music" %>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="動画ランキング" />
    <c:param name="header">
    </c:param>
    <c:param name="content">
        
        <p>
            楽曲ランキング
        </p>
        <ol>
        <%
            MusicResultSet musics = (MusicResultSet)request.getAttribute("musics");
            Music music;
            int rank = 0;
            while ((music = musics.readRow()) != null) {
                out.println("<li>");
                out.println(++rank);
                out.println("<a href=\"MusicServlet?id=" + music.musicId + "\">" + music.musicId + "</a>");
                out.println(music.youtubeVideoId);
                out.println("</li>");
            }
        %>
        </ol>
        
    </c:param>
</c:import>