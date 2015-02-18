<%@page import="jp.ac.jec.jz.gr03.entity.User"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    User user = (User) request.getAttribute("user");
%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="ページのタイトル" />
    <c:param name="header">
    </c:param>
    <c:param name="content">
        <div class="row">
            <div class="col-md-8">
                <h3>登録情報編集</h3>
                <hr>
                <form method="post" action="EditUserServlet" class="form-horizontal">
                    <div class="form-group row">
                        <label for="name" class="col-sm-3 control-label">名前</label>
                        <div class="col-sm-9">
                            <input class="form-control" type="text" id="name" name="name" placeholder="名前" value="<%= h(user.name)%>">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="email" class="col-sm-3 control-label">メールアドレス</label>
                        <div class="col-sm-9">
                            <input class="form-control" type="text" id="email" name="email" placeholder="メールアドレス" value="<%= h(user.email)%>">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="introduction" class="col-sm-3 control-label">プロフィール</label>
                        <div class="col-sm-9">
                            <textarea class="form-control" placeholder="プロフィール" name="introduction" rows="3"><%= h(user.introduction)%></textarea>
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-sm-offset-3 col-sm-9">
                            <input class="btn btn-primary pull-right" type="submit" value="更新">
                        </div>
                    </div>
                </form>

                <div class="clearfix">
                    <ul class="list-inline pull-right">
                        <% if (user.googleUID == null) { %>
                        <li><a href="LoginWithGoogleServlet">Google で認証する（アーティスト登録に必要です）</a></li>
                            <% }%>
                        <li><a href="WithdrawUserServlet">退会する</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </c:param>
</c:import>