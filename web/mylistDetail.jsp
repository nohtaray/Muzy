<%@page import="java.sql.ResultSet"%>
<%@page import="jp.ac.jec.jz.gr03.entity.MylistDetail "%>
<%@page import="jp.ac.jec.jz.gr03.dao.entityresultset.MylistDetailResultSet"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
ResultSet rs = (ResultSet)request.getAttribute("musicThumbnail");
ResultSet rs2 = (ResultSet)request.getAttribute("artistThumbnail");
%>

<c:import url="/layout/application.jsp">
    <c:param name="title" value="ページのタイトル" />
    <c:param name="header">
        <script src="js/mylist.js"></script>
    </c:param>
    <c:param name="content">

        
        <% while (rs.next()) {%>
        <div>
            <a href="MusicServlet?id=<%= rs.getString("music_id")%>"><img src="http://img.youtube.com/vi/<%= h(rs.getString("youtube_video_id"))%>/1.jpg" alt="Loading..." />
                <%= h(rs.getString("title"))%>
            </a>
            <input type="button" onclick="deleteDetail(<%= rs.getString("mylist_detail_id")%> , this)" value="削除" />
        </div>
        <%}%>
        <% while (rs2.next()) {%>
        <div>
            <a href="ArtistServlet?id=<%= rs2.getString("artist_id")%>">
                歌手画像
                <%= h(rs2.getString("name"))%>
            </a>
            <input type="button" onclick="deleteDetail(<%= rs2.getString("mylist_detail_id")%>, this)" value="削除" />
        </div>
        <%}%>
        
    </c:param>
</c:import>