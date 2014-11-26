<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/layout/application.jsp">
    <c:param name="header"></c:param>
    <c:param name="title" value="ページのタイトル" />
    <c:param name="content">
        
        <form method="POST" action="LoginWithPasswordServlet">
            <% if (request.getAttribute("error") != null) { %>
            <div class="has-error">
                ${error}
            </div>
            <% } %>
            
            <input type="hidden" name="token" value="${token}">
            <label>
                email：<input type="text" name="email">
            </label><br>
            <label>
                パスワード：<input type="password" name="password">
            </label><br>
            <input type="submit" value="ログイン">
        </form>
        
    </c:param>
</c:import>