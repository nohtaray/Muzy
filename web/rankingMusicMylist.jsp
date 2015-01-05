<%@page import="jp.ac.jec.jz.gr03.entity.MusicMylistDetail"%>
<%@page import="jp.ac.jec.jz.gr03.dao.entityresultset.MusicMylistDetailResultSet"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="楽曲ランキング（マイリスト登録数順）" />
    <c:param name="header">
    </c:param>
    <c:param name="content">

        <p>
            楽曲ランキング（マイリスト登録数順）
        </p>
        <ol>
            <%
                MusicMylistDetailResultSet musicMylistDetails = (MusicMylistDetailResultSet) request.getAttribute("musicMylistDetails");
                for (MusicMylistDetail musicMylistDetail : musicMylistDetails) {
            %>
            <li>
                <a href="MusicServlet?id=<%= musicMylistDetail.music.musicId%>"><%= musicMylistDetail.music.title%></a>（<%= musicMylistDetail.mylistDetailCount %>）
            </li>
            <%
                }
            %>
        </ol>

    </c:param>
</c:import>