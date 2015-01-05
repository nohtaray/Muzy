<%@page import="jp.ac.jec.jz.gr03.entity.MusicAdvertisement"%>
<%@page import="jp.ac.jec.jz.gr03.dao.entityresultset.MusicAdvertisementResultSet"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="楽曲ランキング（広告ポイント順）" />
    <c:param name="header">
    </c:param>
    <c:param name="content">

        <p>
            楽曲ランキング（広告ポイント順）
        </p>
        <ol>
            <%
                MusicAdvertisementResultSet musicAdvertisements = (MusicAdvertisementResultSet) request.getAttribute("musicAdvertisements");
                for (MusicAdvertisement musicAdvertisement : musicAdvertisements) {
            %>
            <li>
                <a href="MusicServlet?id=<%= musicAdvertisement.music.musicId%>"><%= musicAdvertisement.music.title%></a>（<%= musicAdvertisement.spentPointsSum %>pts）
            </li>
            <%
                }
            %>
        </ol>

    </c:param>
</c:import>