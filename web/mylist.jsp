<%@page import="java.sql.ResultSet"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="jp.ac.jec.jz.gr03.entity.MylistDetail" %>
<%@page import="jp.ac.jec.jz.gr03.dao.entityresultset.MylistDetailResultSet" %>
<%
    MylistDetailResultSet mylistDetails = (MylistDetailResultSet) request.getAttribute("mylistDetails");
%>

<c:import url="/layout/application.jsp">
    <c:param name="title" value="マイリストの編集" />
    <c:param name="header">
        <script src="js/mylist.js"></script>
    </c:param>
    <c:param name="content">
        <input type="text" name="name" placeholder="マイリスト名" id="name" />
        <input type="button" onclick="addMyList()" name="bt" value="マイリスト追加" />

        <ul>
            <% for (MylistDetail d : mylistDetails) {%>
            <li>
                <a href="MyListDetailServlet?id=<%= d.mylist.mylistId %>">
                    <img src="<%= d.music != null ? "http://img.youtube.com/vi/" + h(d.music.youtubeVideoId) + "/1.jpg" : "" %>">
                    <%= h(d.mylist.name)%>
                </a>
                <input type="button" onclick="deleteMyList(<%= d.mylist.mylistId%>, this)" value="削除" />
            </li>
            <%}%>
        </ul>

    </c:param>
</c:import>