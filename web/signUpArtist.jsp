<%@page import="jp.ac.jec.jz.gr03.entity.User"%>
<%@page import="jp.ac.jec.jz.gr03.entity.Artist"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    User me = (User) request.getAttribute("me");
    String name = (String) request.getAttribute("name");
    String introduction = (String) request.getAttribute("introduction");
%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="アーティスト登録" />
    <c:param name="header">
    </c:param>
    <c:param name="content">

        <div class="row">
            <div class="col-sm-7">
                <h3>アーティスト登録</h3>
                <p>
                    アーティスト名とプロフィールを入力して登録してください。
                </p>
                <hr>
                <form method="post" action="" class="form-horizontal">
                    <div class="form-group row">
                        <label for="name" class="col-sm-3 control-label">アーティスト名</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" name="name" id="name" value="<%= name != null ? h(name) : h(me.name)%>">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="introduction" class="col-sm-3 control-label">プロフィール</label>
                        <div class="col-sm-9">
                            <textarea name="introduction" class="form-control" id="introduction" rows="15"><%= introduction != null ? h(introduction) : h(me.introduction)%></textarea>
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-sm-offset-3 col-sm-9">
                            <button type="submit" class="btn btn-primary">登録</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </c:param>
</c:import>
