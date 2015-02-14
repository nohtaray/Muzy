<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="ページのタイトル" />
    <c:param name="header">
        <link rel="stylesheet" type="text/css" href="css/index.css">
        <script type="text/javascript" src="js/index.js"></script>
    </c:param>
    <c:param name="content">
        
        <div class="row">
            <div class="col-md-8">
                注目ランキング
                <ul class="nav nav-tabs">
                    <li class="active"><a href="#ranking-artists" data-toggle="tab">アーティスト</a></li>
                    <li><a href="#ranking-musics" data-toggle="tab">楽曲</a></li>
                </ul>
                <div id="rankings" class="tab-content">
                    <div class="tab-pane fade in active" id="ranking-artists">
                        <div class="text-center">
                            <img src="img/ajax-loader.gif">
                        </div>
                        <ul class="media-list">
                            <li id="ranking-artist-template" class="ranking-artist hidden media list-unstyled">
                                <a class="media-left ranking-artist-a">
                                    <img class="media-object ranking-artist-img" width="80" height="80">
                                </a>
                                <div class="media-body">
                                    <h4 class="media-heading ranking-artist-name"></h4>
                                    <div class="ranking-artist-introduction"></div>
                                </div>
                            </li>
                        </ul>
                        <div id="ranking-artist-template" class="ranking-artist hidden">
                            <div class="ranking-artist-name"></div>
                            <div class="ranking-artist-introduction"></div>
                        </div>
                    </div>
                    <div class="tab-pane fade" id="ranking-musics">
                        <div class="text-center">
                            <img src="img/ajax-loader.gif">
                        </div>
                        <ul class="media-list">
                            <li id="ranking-music-template" class="ranking-music hidden media list-unstyled">
                                <a class="media-left ranking-music-a">
                                    <img class="media-object ranking-music-img" width="120" height="90">
                                </a>
                                <div class="media-body">
                                    <h4 class="media-heading ranking-music-title"></h4>
                                    <div class="ranking-music-description"></div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <h4>最新楽曲</h4>
                <div id="latest-musics" class="">
                    <div class="text-center">
                        <img src="img/ajax-loader.gif">
                    </div>
                    <ul class="media-list">
                        <li id="latest-music-template" class="latest-music hidden media list-unstyled">
                            <a class="media-left latest-music-a">
                                <img class="media-object latest-music-img" width="120" height="90">
                            </a>
                            <div class="media-body">
                                <h4 class="media-heading latest-music-title"></h4>
                                <div class="latest-music-description"></div>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>

    </c:param>
</c:import>