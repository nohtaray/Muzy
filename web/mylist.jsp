<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="jp.ac.jec.jz.gr03.entity.MylistDetail" %>
<%@page import="jp.ac.jec.jz.gr03.dao.entityresultset.MylistDetailResultSet" %>
<%
    MylistDetailResultSet mylistDetails = (MylistDetailResultSet) request.getAttribute("mylistDetails");
%>

<c:import url="/layout/application.jsp">
    <c:param name="title" value="マイリスト" />
    <c:param name="header">
        <link rel="stylesheet" type="text/css" href="css/mylist.css">
        <script type="text/javascript" src="js/mylist.js"></script>
    </c:param>
    <c:param name="content">

        <div class="row">
            <div class="col-lg-3 col-md-4 col-sm-5">
                <div class="panel">
                    <div class="panel-heading">
                        <div class="panel-title">
                            <h4>あなたのマイリスト</h4>
                        </div>
                    </div>
                    <div class="panel-body">
                        <ul id="mylists" class="list-unstyled">
                            <% for (MylistDetail d : mylistDetails) {%>
                            <li class="mylist" data-mylist-id="<%= d.mylist.mylistId%>">
                                <div class="clearfix">
                                    <div class="pull-left">
                                        <img src="<%= d.music != null ? "http://img.youtube.com/vi/" + h(d.music.youtubeVideoId) + "/1.jpg" : ""%>" height="45" width="60">
                                    </div>
                                    <div class="mylist-name">
                                        <%= h(d.mylist.name)%>
                                    </div>
                                </div>
                            </li>
                            <%}%>
                        </ul>
                        <div class="clearfix">
                            <div class="pull-right">
                                <a id="add-mylist-button" style="cursor:pointer;">新規作成...</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-9 col-md-8 col-sm-7">
                <h3 id="mylist-title"></h3>
                <hr>
                <div class="text-center" id="loading-image">
                    <img src="img/ajax-loader.gif">
                </div>
                <ul id="details" class="media-list"></ul>
                <ul id="templates" class="hidden">
                    <li id="artist-template" class="artist hidden media clearfix">
                        <div class="pull-right">
                            <a class="btn delete-detail-button">削除</a>
                        </div>
                        <a class="media-left artist-a">
                            <img class="media-object artist-img" width="80" height="80">
                        </a>
                        <div class="media-body">
                            <h4 class="media-heading"><a class="artist-a artist-name"></a></h4>
                            <div class="artist-introduction"></div>
                        </div>
                    </li>
                    <li id="music-template" class="music hidden media clearfix">
                        <div class="pull-right">
                            <a class="btn delete-detail-button">削除</a>
                        </div>
                        <a class="media-left music-a">
                            <img class="media-object music-img" width="120" height="90">
                        </a>
                        <div class="media-body">
                            <h4 class="media-heading"><a class="music-a music-title"></a></h4>
                            <div class="music-description"></div>
                        </div>
                    </li>
                </ul>
            </div>

        </div>

    </c:param>
</c:import>