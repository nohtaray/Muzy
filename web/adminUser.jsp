<%@page import="jp.ac.jec.jz.gr03.dao.entityresultset.ArtistResultSet"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="jp.ac.jec.jz.gr03.dao.entityresultset.UserResultSet" %>
<%@page import="jp.ac.jec.jz.gr03.entity.User" %>
<c:import url="/layout/admin.jsp">
    <c:param name="title" value="ページのタイトル" />
    <c:param name="header">
    </c:param>
    <c:param name="content">

        <h2>ユーザ管理画面</h2>
        <ul>
            <%
                UserResultSet users = (UserResultSet)request.getAttribute("users");
                User user;
                while ((user = users.readRow()) != null) {
                    out.println("<li>");
                    out.println("<a href=\"UserServlet?id=" + user.userId + "\">" + user.name + "</a>");
                    out.println("<form action=\"DeleteUserByAdminServlet\" method=\"post\">");
                    out.println("<input type=\"hidden\" name=\"id\" value=\"" + user.userId + "\">");
                    out.println("<input type=\"submit\" value=\"削除\">");
                    out.println("</form>");
                    out.println("</li>");
                }
            %>
           
        </ul>

    </c:param>
</c:import>