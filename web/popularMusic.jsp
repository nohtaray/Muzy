<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="jp.ac.jec.jz.gr03.dao.entityresultset.AdvertisementResultSet" %>
<%@page import="jp.ac.jec.jz.gr03.entity.Advertisement" %>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="注目動画" />
    <c:param name="header">
    </c:param>
    <c:param name="content">

        <p>
            注目動画
        </p>
        <ul>
            <%
                AdvertisementResultSet ads = (AdvertisementResultSet) request.getAttribute("advertisements");
                Advertisement ad;
                while ((ad = ads.readRow()) != null) {
            %>
            <li>
                <a href="MusicServlet?id=<%= ad.music.musicId%>"><%= ad.music.title%></a>
            </li>
            <%
                }
            %>
        </ul>

    </c:param>
</c:import>