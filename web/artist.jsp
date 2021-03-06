<%@page import="jp.ac.jec.jz.gr03.entity.Mylist"%>
<%@page import="jp.ac.jec.jz.gr03.dao.entityresultset.MylistResultSet"%>
<%@page import="jp.ac.jec.jz.gr03.entity.User"%>
<%@page import="jp.ac.jec.jz.gr03.entity.Music"%>
<%@page import="jp.ac.jec.jz.gr03.entity.Message"%>
<%@page import="jp.ac.jec.jz.gr03.dao.entityresultset.MusicResultSet"%>
<%@page import="jp.ac.jec.jz.gr03.dao.entityresultset.MessageResultSet"%>
<%@page import="jp.ac.jec.jz.gr03.entity.Artist"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    Artist artist = (Artist) request.getAttribute("artist");
    MessageResultSet messages = (MessageResultSet) request.getAttribute("messages");
    MusicResultSet musics = (MusicResultSet) request.getAttribute("musics");
    MylistResultSet mylists = (MylistResultSet) request.getAttribute("mylists");
    User me = (User) request.getAttribute("me");

    boolean loggedIn = me != null;
%>
<c:import url="/layout/application.jsp">
    <c:param name="title">
        <%= h(artist.name)%> さんのページ
    </c:param>
    <c:param name="header">
        <link rel="stylesheet" type="text/css" href="css/artist.css">
        <script type="text/javascript" src="js/artist.js"></script>
    </c:param>
    <c:param name="content">
        <!--
        <div id="artist-header">
            <img id="header-image" src="<%= h(artist.headerImageFile)%>">
        </div>
        -->
        <div class="row">
            <div class="col-sm-7">
                <div class="clearfix">
                    <img src="<%= h(artist.user.iconImageFile)%>" class="pull-left" width="80" height="80" id="icon-image">
                    <h2 id="artist-name"><%= h(artist.name)%></h2>
                </div>
                <% if (loggedIn) { %>
                <div class="clearfix">
                    <div class="pull-right">
                        <a class="modal-open btn" data-toggle="modal" data-target="#vote-modal" id="vote-button">このアーティストを応援する</a>
                        <a class="modal-open btn" data-toggle="modal" data-target="#add-mylist-modal" id="add-mylist-button">マイリストに追加</a>
                    </div>
                </div>
                <% }%>

                <div id="profile">
                    <%= br(h(artist.introduction))%>
                </div>
                <hr>
                <% if (loggedIn) {%>
                <form method="post" action="NewMessageServlet" class="clearfix" id="new-message-form">
                    <input type="hidden" name="artist" value="<%= artist.artistId%>">
                    <div class="form-group">
                        <label for="new-message-content">メッセージを送る</label>
                        <textarea placeholder="メッセージを入力してください" name="content" id="new-message-content" class="form-control" rows="3" required></textarea>
                    </div>
                    <input type="submit" class="btn btn-primary pull-right" value="送信">
                </form>
                <% } %>
                <div id="messages">
                    <% for (Message message : messages) {%>
                    <div class="media">
                        <a class="media-left">
                            <img class="media-object" src="<%= !message.isDeleted && message.user.iconImageFile != null ? h(message.user.iconImageFile) : ""%>" height="64" width="64">
                        </a>
                        <div class="media-body thumbnail">
                            <% if (message.isDeleted) {%>
                            <h4>
                                <%= message.messageId%>.
                            </h4>
                            <div class="text-muted">削除されました</div>
                            <% } else {%>
                            <h4>
                                <%= message.messageId%>. <a href="UserServlet?id=<%= message.user.userId%>"><%= h(message.user.name)%></a>
                                <small><%= dateToString(message.createdAt)%></small>
                            </h4>
                            <%= br(h(message.content))%>
                            <% if (me != null && (me.userId == message.user.userId || me.userId == message.artist.user.userId || me.isOwner)) {%>
                            <div class="pull-right">
                                <small><a class="message-delete-button" data-artist-id="<%=message.artist.artistId%>" data-message-id="<%=message.messageId%>" href="#">削除</a></small>
                            </div>
                            <%}%>
                            <% } %>
                        </div>
                    </div>
                    <% }%>
                </div>
            </div>
            <div class="col-sm-5">
                <div id="uploaded-musics">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            投稿楽曲一覧
                        </div>
                        <div class="panel-body">
                            <ul class="media-list">
                                <% for (Music music : musics) {%>
                                <li class="media">
                                    <a class="media-left" href="MusicServlet?id=<%= music.musicId%>">
                                        <img class="media-object" src="http://img.youtube.com/vi/<%= h(music.youtubeVideoId)%>/1.jpg">
                                    </a>
                                    <div class="media-body">
                                        <h4 class="media-heading"><a href="MusicServlet?id=<%= music.musicId%>"><%= h(truncate(music.title, 30))%></a></h4>
                                        <%= h(truncate(music.description, 100))%>
                                    </div>
                                </li>
                                <% } %>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <% if (loggedIn) {%>
        <div id="vote-modal" class="modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4>アーティスト投票</h4>
                    </div>
                    <div class="modal-body">
                        <div id="vote-roles">
                            <form id="vote-form">
                                <p>
                                    持っているチケットを使ってアーティストに投票します。<br>
                                    投票数が多いアーティストはランキングに表示されます。
                                </p>
                                <div id="vote-error" class="hidden error"></div>
                                保有チケット：<span id="vote-now-tickets">0</span><br>
                                利用チケット：<input type="text" id="vote-use-tickets">
                                <input type="hidden" id="vote-artist-id" value="<%= artist.artistId%>">
                                <input type="submit" class="btn btn-primary" value="投票">
                            </form>
                            <div id="vote-sending" class="hidden">
                                <img src="img/ajax-loader.gif">通信中です...
                            </div>
                            <div id="vote-done" class="hidden">
                                投票しました！
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">閉じる</button>
                    </div>
                </div>
            </div>
        </div>

        <div id="add-mylist-modal" class="modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4>追加先を選択してください</h4>
                    </div>
                    <div class="modal-body">
                        <ul class="list-unstyled">
                            <% if (mylists != null)
                                    for (Mylist mylist : mylists) {%>
                            <li>
                                <a class="btn add-mylist-button" data-mylist-id="<%=mylist.mylistId %>" data-artist-id="<%= artist.artistId%>"><%= h(mylist.name)%></a>
                            </li>
                            <% } %>
                        </ul>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">閉じる</button>
                    </div>
                </div>
            </div>
        </div>
        <% }%>
    </c:param>
</c:import>
