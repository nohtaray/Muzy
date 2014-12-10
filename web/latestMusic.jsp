<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="jp.ac.jec.jz.gr03.dao.entityresultset.MusicResultSet" %>
<%@page import="jp.ac.jec.jz.gr03.entity.Music" %>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="新着楽曲" />
    <c:param name="header">
    </c:param>
    <c:param name="content">
        
        <p>
            新着楽曲
        </p>
        <ul>
        <%
            MusicResultSet musics = (MusicResultSet)request.getAttribute("musics");
            Music music;
            while ((music = musics.readRow()) != null) {
                out.println("<li>");
                out.println(music.createdAt);
                out.println("<a href=\"MusicServlet?id=" + music.musicId + "\">" + music.title + "</a>");
                out.println("</li>");
            }
        %>
        </ul>
        
    </c:param>
</c:import>