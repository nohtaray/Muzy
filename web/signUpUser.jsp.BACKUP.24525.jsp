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
        <style type="text/css">
        input {width:180px;}
        </style>
        
        
        <script src="js/lib/prototype.js"></script> 
        
        <!--
 <form onsubmit="return funcDispChange()">
    <input type="text" name="email" onfocus="xx()" onblur="yy()" onkeyup="zz(this)"/><br>
    <input type="submit" name="bt" value="使用可能なＩＤかチェック" /> 
 </form>
        
        <script type="text/javascript">
            function funcDispChange() {
                $.ajax({
                    type     : 'POST',
                    dataType : 'text',
                    url      : 'UniqueCheck', //ここにServletのアドレス
                    //ここがServletに渡される値
                    data     : {
                                 email   : 'bastest'
                               }
                }).done(function() {
                    // ajax ok
                    alert("使用可能です");
                }).fail(function() {
                    // ajax error
                    alert("使用不可です");
                }).always(function() {
                    // ajax complete
                    alert("あああ");
                });
            }
        </script>
        
        
        <script type="text/javascript">
            function funcDispChange() {
                new Ajax.Request('UniqueCheckServlet', {
                    method: 'get',
                    onSuccess: function() {
                        alert("成功");
                    },
                    onFailure: function() {
                        alert("失敗");
                    },
                    onException: function() {
                        alert("エラー");
                    }
                });
            }
        </script>
        -->
        
        
        
        
        
<<<<<<< HEAD
        
        <form method="POST" action="SignUpUserServlet">
            <% if (error != null) { %>
=======
        <form method="GET" action="SignUpUserServlet">
            <% if (request.getAttribute("error") != null) { %>
>>>>>>> マイリスト修正
            <div class="has-error">
                <%= error %>
            </div>
            <% } %>
            <label>
                email：<input type="text" name="email">
            </label><br>
            <label>
                ユーザ名：<input type="text" name="name">
            </label><br>
            <label>
                パスワード：<input type="password" name="password">
            </label><br>
            <label>
                自己紹介文：<input type="text" name="introduction">
            </label><br>
            
            <input type="submit" value="会員登録">
        </form>
        <% request.setCharacterEncoding("UTF-8"); %>
        
    </c:param>
</c:import>