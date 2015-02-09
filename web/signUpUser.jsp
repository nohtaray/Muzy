<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String error = (String) request.getAttribute("error");
%>
<c:import url="/layout/application.jsp">
    <c:param name="header">
    </c:param>
    <c:param name="title" value="会員登録" />
    <c:param name="content">
        <script src="js/lib/jquery-2.1.1.min.js"></script> 
        <script src="js/signUpUser.js"></script>

        <form method="POST" action="SignUpUserServlet">
            <% if (error != null) { %>

            <div class="has-error">
                <%= h(error) %>
            </div>
            <% } %>
            <label>
                email：<input type="text" name="email" id="email" onChange="checkEmail()" required>
                <p id="OK" style="display: none;">このemailは使用可能です。</p><p id="NG" style="display: none;">このemailは使用できません。</p>
            </label><br>
            <label>
                ユーザ名：<input type="text" name="name" required>
            </label><br>
            <label>
                パスワード：<input type="password" name="password" required>
                パスワードの確認 : 
            </label><br>
            <label>
                自己紹介文：<!--<input type="text" name="introduction">-->
                <textarea name="introduction" cols="30" rows="5"></textarea>
            </label><br>
            
            <input type="submit" value="会員登録">
        </form>
        <% request.setCharacterEncoding("UTF-8"); %>
        
    </c:param>
</c:import>