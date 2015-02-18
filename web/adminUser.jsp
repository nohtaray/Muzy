<%@page import="jp.ac.jec.jz.gr03.dao.entityresultset.ArtistResultSet"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="jp.ac.jec.jz.gr03.dao.entityresultset.UserResultSet" %>
<%@page import="jp.ac.jec.jz.gr03.entity.User" %>
<c:import url="/layout/admin.jsp">
    <c:param name="title" value="ユーザ管理" />
    <c:param name="header">
        <script type="text/javascript" src="js/adminUser.js"></script>
    </c:param>
    <c:param name="content">

        
        <h3>ユーザ一覧</h3>
        <table class="table table-hover">
            <thead>
                <tr>
                    <th>ユーザID</th>
                    <th>名前</th>
                    <th>メールアドレス</th>
                    <th>Google User ID</th>
                    <th>登録日時</th>
                    <th>更新日時</th>
                    <th>管理者</th>
                    <th>有効</th>
                    <th>削除</th>
                </tr>
            </thead>
            <tbody>
                <%
                    UserResultSet users = (UserResultSet) request.getAttribute("users");
                    for (User user : users) {
                %>
                <tr>
                    <td>
                        <%= user.userId%>
                    </td>
                    <td>
                        <%= h(user.name) %>
                    </td>
                    <td>
                        <%= h(user.email) %>
                    </td>
                    <td>
                        <%= h(user.googleUID) %>
                    </td>
                    <td>
                        <%= h(dateToString(user.createdAt))%>
                    </td>
                    <td>
                        <%= h(dateToString(user.updatedAt))%>
                    </td>
                    <td>
                        <input type="checkbox" <%= user.isOwner ? "checked" : "" %> onclick="return false;">
                    </td>
                    <td>
                        <input type="checkbox" <%= user.isValid ? "checked" : "" %> onclick="return false;">
                    </td>
                    <td>
                        <button class="delete-button btn btn-danger" data-user-id="<%= user.userId%>">削除</button>
                    </td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
            
    </c:param>
</c:import>