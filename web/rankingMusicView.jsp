<%@page import="jp.ac.jec.jz.gr03.entity.Music"%>
<%@page import="jp.ac.jec.jz.gr03.dao.entityresultset.MusicResultSet"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="楽曲ランキング（再生数順）" />
    <c:param name="header">
    </c:param>
    <c:param name="content">

        <p>
            楽曲ランキング（再生数順）
        </p>
        <ol>
            <%
                MusicResultSet musics = (MusicResultSet) request.getAttribute("musics");
                for (Music music : musics) {
            %>
            <li>
                <a href="MusicServlet?id=<%= music.musicId%>"><%= music.title%></a>（<%= music.viewCount %>）
            </li>
            <%
                }
            %>
        </ol>

    </c:param>
</c:import>