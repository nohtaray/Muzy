<%@page import="jp.ac.jec.jz.gr03.entity.Artist"%>
<%@page import="jp.ac.jec.jz.gr03.entity.Music"%>
<%@page import="jp.ac.jec.jz.gr03.dao.entityresultset.MusicResultSet"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/layout/admin.jsp">
    <c:param name="title" value="ページのタイトル" />
    <c:param name="header">
        <%-- スクリプトのインポートなど。例↓
        <script type="text/javascript" src="js/template.js"></script>
        <link rel="stylesheet" type="text/css" href="css/template.css">
        --%>
    </c:param>
    <c:param name="content">
        
        <h2>楽曲管理画面</h2>
        <ul>
            <%
                MusicResultSet musicsResult = (MusicResultSet)request.getAttribute("");
                Music music = new Music();
                Artist artist = new Artist();
                while ((music = musicsResult.readRow()) != null) {
                    out.println("<li>");
                    out.println("<a href=\"MusicServlet?id=" + music.musicId + "\">" + music.title + "</a>");
                    out.println("<form action=\"DeleteMusicByAdminServlet\" method=\"post\">");
                    out.println("<input type=\"hidden\" name=\"id\" value=\"" + music.musicId + "\">");
                    out.println("<input type=\"submit\" value=\"削除\">");
                    out.println("</form>");
                    out.println("</li>");
                }
            %>
           
        </ul>
        
    </c:param>
</c:import>