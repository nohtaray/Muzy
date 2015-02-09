<%@page import="java.sql.ResultSet"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="jp.ac.jec.jz.gr03.entity.Music" %>
<%@page import="jp.ac.jec.jz.gr03.entity.MyList"%>
<%@page import="jp.ac.jec.jz.gr03.dao.entityresultset.MyListResultSet" %>
<%@page import="jp.ac.jec.jz.gr03.dao.entityresultset.MusicResultSet" %>
<%@page import="jp.ac.jec.jz.gr03.entity.MyList_Details" %>
<%@page import="jp.ac.jec.jz.gr03.dao.entityresultset.MyListDetailResultSet" %>
<%
ResultSet rs = (ResultSet)request.getAttribute("mylistThumbnail");
%>

<c:import url="/layout/application.jsp">
    
    <c:param name="title" value="マイリストの編集" />
    <c:param name="header">
        <script src="js/mylist.js"></script>
        
        
    </c:param>
    <c:param name="content">
        <input type="text" name="name" placeholder="マイリスト名" id="name" />
        <input type="button" onclick="addMyList() " name="bt" value="マイリスト追加" />
        
        <ul>
        <!-- マイリスト一覧表示 -->
        <% while (rs.next()) {%>
            <div>
                <a href="MyListDetailServlet?id=<%= rs.getString("mylist_id")%>">
                    <img src="http://img.youtube.com/vi/<%= h(rs.getString("youtube_video_id"))%>/1.jpg" alt="Loading..." />
                    <%= h(rs.getString("name"))%> <%=rs.getString("updated_at")%>
                </a>
                <input type="button" onclick="deleteMyList(<%= rs.getString("mylist_id")%> , this)" value="削除" />
            </div>
            <%}%>
        </ul>
        
    </c:param>
</c:import>