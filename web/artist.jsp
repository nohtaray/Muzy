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
    User me = (User) request.getAttribute("me");

    boolean loggedIn = me != null;
%>
<c:import url="/layout/application.jsp">
    <c:param name="title">
        <%= artist.name%> さんのページ
    </c:param>
    <c:param name="header">
        <script type="text/javascript" src="js/artist.js"></script>
    </c:param>
    <c:param name="content">
        <div class="row">
            <div class="col-sm-7">
                <h1><%= artist.name%> さんのページ</h1>
                <% if (loggedIn) { %>
                <div>
                    <a class="modal-open btn" data-toggle="modal" data-target="#vote-modal" id="vote-button">このアーティストを応援する</a>
                    <a href="#" id="addto">マイリストに追加する</a>
                </div>
                <% }%>
                <div id="profile">
                    <%= artist.introduction%>
                </div>

                <form method="post" action="NewMessageServlet">
                    <input type="hidden" name="artist" value="<%= artist.artistId%>">
                    <textarea placeholder="コメントを入力" name="content"></textarea>
                    <input type="submit" value="送信">
                </form>
                <div id="messages">
                    <% for (Message message : messages) {%>
                    <div class="message <%= message.isDeleted ? "message-deleted" : ""%>">
                        <div class="message-header">
                            <%= message.messageId%>. <%= message.user.name%>
                        </div>
                        <div class="message-body">
                            <%= message.isDeleted ? "削除されました" : message.content%>
                        </div>
                        <div class="message-footer">
                            <%= message.createdAt%>
                            <% if (me != null && (me.userId == message.user.userId || me.userId == message.artist.user.userId || me.isOwner)) {%>
                            <button class="message-delete-button" data-artist-id="<%=message.artist.artistId%>" data-message-id="<%=message.messageId%>">削除</button>
                            <%} %>
                        </div>
                    </div>
                    <% }%>
                </div>
            </div>
            <div class="col-sm-5">
                <div id="musics">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            投稿楽曲一覧
                        </div>
                        <div class="panel-body">
                            <ul class="media-list">
                                <% for (Music music : musics) {%>
                                <li class="media">
                                    <a class="pull-left" href="MusicServlet?id=<%= music.musicId%>">
                                        <img class="media-object" src="http://img.youtube.com/vi/<%= music.youtubeVideoId%>/1.jpg">
                                    </a>
                                    <div class="media-body">
                                        <h4 class="media-heading"><%= truncate(music.title, 30)%></h4>
                                        <%= truncate(music.description, 150)%>
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
        <% }%>
    </c:param>
</c:import>
