<%@page import="jp.ac.jec.jz.gr03.entity.Music"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    Music music = (Music) request.getAttribute("music");
%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="<%= music.title%>" />
    <c:param name="header">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap-theme.min.css">
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>

        <link rel="stylesheet" href="css/music.css">
        <script src="js/music.js"></script>
    </c:param>
    <c:param name="content">


        <h2>
            <%= music.title%>
        </h2>
        <div id="tools">
            <a class="modal-open btn" data-toggle="modal" data-target="#advertise-modal" id='advertise-button'>この動画を広告する！</a>
        </div>
        <div>
            <iframe width="576" height="360" src="//www.youtube.com/embed/<%= music.youtubeVideoId%>?autoplay=1&controls=2&rel=0&showinfo=0" frameborder="0" allowfullscreen></iframe>    
        </div>
        <div>
            <%= music.description%>
        </div>

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
                                    <div id="advertise-error" class="hidden"></div>
                                    <%-- TODO: 今の保有ポイントを取得する --%>
                                    保有ポイント：<span id="advertise-now-points">〇〇</span><br>
                                    <label>利用ポイント：</label><input type="text" id="advertise-use-points">
                                    <input type="hidden" id="advertise-music-id" value="<%= music.musicId%>">
                                    <input type="submit" class="btn btn-primary" value="ポイントを使って広告する！">
                                </form>

                            </div>
                            <div id="advertise-loading" class="hidden">
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
    </c:param>
</c:import>