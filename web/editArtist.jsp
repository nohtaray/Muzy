<%@page import="jp.ac.jec.jz.gr03.entity.Artist"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    Artist artist = (Artist) request.getAttribute("artist");
%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="ページのタイトル" />
    <c:param name="header">
    </c:param>
    <c:param name="content">
        <div class="row">
            <div class="col-sm-9">
                <h3>アーティスト情報編集</h3>
                <hr>
                <form method="post" action="EditArtistServlet" class="form-horizontal">
                    <div class="form-group row">
                        <label for="name" class="col-sm-3 control-label">アーティスト名</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" name="name" id="name" value="<%= h(artist.name)%>">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="introduction" class="col-sm-3 control-label">プロフィール</label>
                        <div class="col-sm-9">
                            <textarea name="introduction" class="form-control" id="introduction" rows="15"><%= h(artist.introduction)%></textarea>
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-sm-offset-3 col-sm-9">
                            <button type="submit" class="btn btn-primary pull-right">更新</button>
                        </div>
                    </div>
                </form>
                <div class="clearfix">
                    <div class="pull-right">
                        <a href="WithdrawArtistServlet">アーティスト登録解除</a>
                    </div>
                </div>
            </div>
        </div>
    </c:param>
</c:import>
