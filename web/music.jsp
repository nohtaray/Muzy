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
    <c:param name="title" value="<%= music.title%>" />
    <c:param name="header">
        <link rel="stylesheet" href="css/music.css">
        <script src="js/lib/jquery.raty.js"></script>
        <script src="js/music.js"></script>
    </c:param>
    <c:param name="content">

        
        <h2><%= music.title%></h2>
        <hr>
        <div id="tools">
            <% if (loggedIn) { %>
            <a class="modal-open btn" data-toggle="modal" data-target="#advertise-modal" id="advertise-button">この動画を広告する！</a>
            <% }%>
        </div>
        <div>
            <iframe width="576" height="360" src="//www.youtube.com/embed/<%= music.youtubeVideoId%>?autoplay=1&controls=2&rel=0&showinfo=0" frameborder="0" allowfullscreen></iframe>    
        </div>
        <div>
            <div>
                再生： <%= music.viewCount %> 回
            </div>
            <%= music.description%>
        </div>

        <hr>

        <%--既存登録タグ(暫定的)--%>
        <div id="tags">
            
        </div>

        <%--タグ追加スペース--%>
        <input type="button" onclick="HeaderClick();" value="タグ追加">
        <div>
            <div id="ContentsPanel" style="width:780px;border:1px solid #008d18; display:none">
                <div id="tagfiled">
                    <input type="text" id="tagname" placeholder="タグ名" required>
                </div>
                <input type="button" onclick="funcSignUpTags();" value="新規タグを登録する">
            </div>
            <input type="hidden" id="musicid" value="<%= music.musicId %>">
        </div>

        レビュー<br />
        <div>
            <textarea id="comment-add-content" cols="30" rows="5"></textarea>
            <input type="button" onclick="funcReview();" value="レビュー書き込み">
        </div>

        <br /><br /><br />
        <h2>レビュー一覧</h2>
        <div id="reviewarea">
            <% for (Comment comment : comments) { %>
            <div class="comment" data-comment-id="<%= comment.commentId %>">
                <div class="comment-content"><%= comment.content %></div>
                <input type="button" onclick="evaluationCommentGood()" value="良い" />
                <input type="button" onclick="evaluationCommentBad()" value="悪い" />
                <input type="button" onclick="deleteComment()" value="削除" />
            </div>
            <% } %>
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
        <% }%>
        
        
        <%-- 投稿者かどうか --%>
        <input type="hidden" id="is-my-music" value="<%= me != null && me.userId == music.artist.user.userId %>">
        <%-- ログインしてるか --%>
        <input type="hidden" id="is-logged-in" value="<%= loggedIn %>">
    </c:param>
</c:import>