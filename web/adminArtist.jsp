<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="jp.ac.jec.jz.gr03.dao.entityresultset.ArtistResultSet" %>
<%@page import="jp.ac.jec.jz.gr03.entity.Artist" %>
<c:import url="/layout/admin.jsp">
    <c:param name="title" value="ページのタイトル" />
    <c:param name="header">
        <%-- スクリプトのインポートなど。例↓
        <script type="text/javascript" src="js/template.js"></script>
        <link rel="stylesheet" type="text/css" href="css/template.css">
        --%>
    </c:param>
    <c:param name="content">

        <h2>アーティスト管理画面</h2>
        <ul>
            <%
                ArtistResultSet artists = (ArtistResultSet)request.getAttribute("artists");
                Artist artist;
                while ((artist = artists.readRow()) != null) {
                    out.println("<li>");
                    out.println("<a href=\"ArtistServlet?id=" + artist.artistId + "\">" + artist.name + "</a>");
                    out.println("<form action=\"DeleteArtistByAdminServlet\" method=\"post\">");
                    out.println("<input type=\"hidden\" name=\"id\" value=\"" + artist.artistId + "\">");
                    out.println("<input type=\"submit\" value=\"削除\">");
                    out.println("</form>");
                    out.println("</li>");
                }
            %>
           
        </ul>

    </c:param>
</c:import>