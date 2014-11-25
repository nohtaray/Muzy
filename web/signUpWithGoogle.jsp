<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/layout/application.jsp">
    <c:param name="header"></c:param>
    <c:param name="title" value="ページのタイトル" />
    <c:param name="content">
        
        <form method="POST" action="SignUpWithGoogleServlet">
            <% if (request.getAttribute("error") != null) { %>
            <div class="has-error">
                ${error}
            </div>
            <% } %>
            
            <label>
                名前：<input type="text" name="name">
            </label><br>
            <label>
                自己紹介：<br>
                <textarea name="introduction"></textarea>
            </label><br>
            <input type="submit" value="登録！">
        </form>

    </c:param>
</c:import>