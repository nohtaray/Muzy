<%@page import="java.sql.ResultSet"%>
<%@page import="jp.ac.jec.jz.gr03.entity.MyList"%>
<%@page import="jp.ac.jec.jz.gr03.dao.entityresultset.MyListResultSet"%>
<%@page import="jp.ac.jec.jz.gr03.entity.Music"%>
<%@page import="jp.ac.jec.jz.gr03.entity.User"%>
<%@page import="jp.ac.jec.jz.gr03.entity.Comment"%>
<%@page import="jp.ac.jec.jz.gr03.dao.entityresultset.CommentResultSet"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    Music music = (Music) request.getAttribute("music");
    User me = (User) request.getAttribute("me");
    CommentResultSet comments = (CommentResultSet) request.getAttribute("comments");
    boolean loggedIn = me != null;
%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="<%= h(music.title)%>" />
    <c:param name="header">
        <link rel="stylesheet" href="css/music.css">
        <script src="js/lib/jquery.raty.js"></script>
        <script src="js/music.js"></script>
    </c:param>
    <c:param name="content">


        <div class="row">
            <div class="col-lg-7 col-md-8">
                <h2><%= h(music.title)%></h2>
                <hr>
                <div class="clearfix">
                    <% if (loggedIn) { %>
                    <div class="pull-right">
                        <a class="modal-open btn" data-toggle="modal" data-target="#advertise-modal" id="advertise-button">この動画を広告する</a>
                        <a class="modal-open btn" data-toggle="modal" data-target="#add-mylist-modal" id="add-mylist-button">マイリストに追加</a>
                    </div>
                    <% }%>
                </div>
                <div class="text-center" id="video">
                    <iframe width="576" height="360" src="//www.youtube.com/embed/<%= h(music.youtubeVideoId)%>?autoplay=1&controls=2&rel=0&showinfo=0" frameborder="0" allowfullscreen></iframe>
                </div>
                <div>
                    <div class="clearfix">
                        <div class="pull-right">
                            再生：<%= music.viewCount%> 回
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-9">
                            <div id="tags"></div>
                        </div>
                        <div class="col-md-3 col-md-offset-0 col-xs-4 col-xs-offset-8 clearfix">
                            <form id="add-tag-form" onsubmit="funcSignUpTags();
                                    return false;">
                                <a class="pull-right" id="add-tag-button" onclick="HeaderClick();">タグ追加...</a>
                                <div id="ContentsPanel" class="hidden">
                                    <input type="text" id="tagname" class="form-control" placeholder="タグ名" required>
                                </div>
                            </form>
                        </div>
                    </div>
                    <%= br(h(music.description))%>
                </div>

                <hr>

                <form id="add-comment-form" class="clearfix" onsubmit="funcReview();
                        return false;">
                    <div class="form-group">
                        <label for="comment-add-content">この動画にコメントする</label>
                        <textarea placeholder="コメントを入力してください" id="comment-add-content" class="form-control" rows="3"></textarea>
                    </div>
                    <input type="submit" class="btn btn-primary pull-right" value="送信">
                </form>

                <div id="comments">
                    <% for (Comment comment : comments) {%>
                    <div class="media">
                        <a class="media-left">
                            <img class="media-object" src="<%= !comment.isDeleted && comment.user.iconImageFile != null ? h(comment.user.iconImageFile) : "img/default.png"%>" width="64">
                        </a>
                        <div class="media-body thumbnail clearfix">
                            <% if (comment.isDeleted) {%>
                            <h4></h4>
                            <div class="text-muted">削除されました</div>
                            <% } else {%>
                            <h4>
                                <a href="UserServlet?id=<%= comment.user.userId%>"><%= h(comment.user.name)%></a>
                                <small><%= dateToString(comment.createdAt)%></small>
                            </h4>
                            <%= br(h(comment.content))%>
                            <% if (me != null) {%>
                            <div class="pull-right">
                                <div>
                                    <a class="btn" onclick="evaluationCommentGood(<%= comment.commentId%>, this, <%= comment.scorePlusCount%>)"><span class="glyphicon glyphicon-thumbs-up"></span></a><span class="comment-good-count text-success"><%= (comment.scorePlusCount)%></span>
                                    <a class="btn" onclick="evaluationCommentBad(<%= comment.commentId%>, this, <%= comment.scoreMinusCount%>)"><span class="glyphicon glyphicon-thumbs-down"></span></a><span class="comment-bad-count text-danger"><%= (comment.scoreMinusCount)%></span>
                                </div>
                                <% if (me.userId == comment.user.userId || me.userId == comment.music.artist.user.userId || me.isOwner) {%>
                                <div class="pull-right">
                                    <small><a class="btn comment-delete-button" data-comment-id="<%=comment.commentId%>">削除</a></small>
                                </div>
                                <% } %>
                            </div>
                            <% } %>
                            <% } %>
                        </div>
                    </div>
                    <% }%>
                </div>
            </div>

            <div class="col-lg-5 col-md-4">

            </div>

        </div>

        <% if (loggedIn) {%>
        <div id="advertise-modal" class="modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4>広告</h4>
                    </div>
                    <div class="modal-body">
                        <div id="advertise-roles">
                            <div id="advertise-input">
                                <p>
                                    持っているポイントを使ってこの楽曲を広告できます。<br>
                                    広告した楽曲はあなたの名前と共にトップページに表示されます。
                                </p>
                                <form id="advertise-form">
                                    <div id="advertise-error" class="hidden error"></div>
                                    保有ポイント：<span id="advertise-now-points">0</span><br>
                                    <label>利用ポイント：</label><input type="text" id="advertise-use-points">
                                    <input type="hidden" id="advertise-music-id" value="<%= music.musicId%>">
                                    <input type="submit" class="btn btn-primary" value="ポイントを使って広告する！">
                                </form>

                            </div>
                            <div id="advertise-sending" class="hidden">
                                <img src="img/ajax-loader.gif">通信中です...
                            </div>
                            <div id="advertise-done" class="hidden">
                                広告が完了しました！
                            </div>
                            <div id="advertise-fail" class="hidden">
                                <div id="advertise-fail-message">
                                </div>
                                <button type="button" class="btn btn-default" id="advertise-retry-button">やり直す</button>
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
                        <h4>マイリスト追加</h4>
                    </div>
                    <div class="modal-body">
                        <%
                            MyListResultSet mylists = (MyListResultSet) request.getAttribute("mylists");
                            MyList mylist;

                            out.print("<table>");
                            while ((mylist = mylists.readRow()) != null) {
                                out.println("<tr>");
                                out.println("<td>" + h(mylist.name) + "</td>");
                                out.println("<td><input type=\"button\" onclick=\"addMyListDetail(" + mylist.mylist_id + "," + music.musicId + ")\" value=\"追加\" /></td></tr>");
                            }
                            out.print("</table>");

                        %>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">閉じる</button>
                    </div>
                </div>
            </div>
        </div>

        <% }%>

        <input type="hidden" id="musicid" value="<%= music.musicId%>">
        <%-- 投稿者かどうか --%>
        <input type="hidden" id="is-my-music" value="<%= me != null && me.userId == music.artist.user.userId%>">
        <%-- ログインしてるか --%>
        <input type="hidden" id="is-logged-in" value="<%= loggedIn%>">
    </c:param>
</c:import>