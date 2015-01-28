<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String error = (String) request.getAttribute("error");
%>
<c:import url="/layout/application.jsp">
    <c:param name="header"></c:param>
    <c:param name="title" value="ページのタイトル" />
    <c:param name="content">
        <p>
            Muzy へようこそ！<br>
            基本情報を入力して登録してください。
        </p>
        <form method="post" action="" class="form-horizontal">
            <div class="form-group row">
                <label for="name" class="col-sm-2 control-label">名前</label>
                <div class="col-sm-5">
                    <input type="text" class="form-control" name="name" id="name">
                </div>
            </div>
            <div class="form-group row">
                <label for="introduction" class="col-sm-2 control-label">プロフィール</label>
                <div class="col-sm-5">
                    <textarea name="introduction" class="form-control" id="introduction"></textarea>
                </div>
            </div>
            <div class="form-group row">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-primary">登録</button>
                </div>
            </div>
        </form>
    </c:param>
</c:import>