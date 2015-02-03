<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="ページのタイトル" />
    <c:param name="header">
        <script type="text/javascript" src="js/index.js"></script>
    </c:param>
    <c:param name="content">

        <!--
        <p>
            動画ランキング！！
        </p>
        <ul class="nav nav-tabs">
            <li role="presentation" class="active"><a href="#">再生数！</a></li>
            <li role="presentation"><a href="#">評価数！</a></li>
            <li role="presentation"><a href="#">マイリスト数！</a></li>
        </ul>
        -->

        <div class="row">
            <div class="col-md-8">
                <h3>アーティストランキング</h3>
                <div id="ranking-artists">
                    <div class="text-center">
                        <img src="img/ajax-loader.gif">
                    </div>
                    <div id="ranking-artist-template" class="ranking-artist hidden">
                        <div class="ranking-artist-name"></div>
                        <div class="ranking-artist-introduction"></div>
                    </div>
                </div>
                <h3>楽曲ランキング</h3>
                <div id="ranking-musics">
                    <div class="text-center">
                        <img src="img/ajax-loader.gif">
                    </div>
                    <ul class="media-list">
                        <li id="ranking-music-template" class="ranking-music hidden media list-unstyled">
                            <a class="media-left ranking-music-a">
                                <img class="media-object ranking-music-img">
                            </a>
                            <div class="media-body">
                                <h4 class="media-heading ranking-music-title"></h4>
                                <div class="ranking-music-description"></div>
                            </div>
                        </li>
                    </ul>
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
                                <img class="media-object latest-music-img">
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