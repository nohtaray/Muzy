<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="ページのタイトル" />
    <c:param name="header">
        <script type="text/javascript" src="js/index.js"></script>
    </c:param>
    <c:param name="content">

        <p>
            動画ランキング！！
        </p>
        <ul class="nav nav-tabs">
            <li role="presentation" class="active"><a href="#">再生数！</a></li>
            <li role="presentation"><a href="#">評価数！</a></li>
            <li role="presentation"><a href="#">マイリスト数！</a></li>
        </ul>

        <div id="rankings">
            アーティストランキング
            <div id="ranking-artists">
                <div id="ranking-artist-template" class="ranking-artist hidden">
                    <div class="ranking-artist-name"></div>
                    <div class="ranking-artist-introduction"></div>
                </div>
            </div>
            楽曲ランキング
            <div id="ranking-musics">
                <div id="ranking-music-template" class="ranking-music hidden">
                    <div class="ranking-music-title"></div>
                    <div class="ranking-music-description"></div>
                </div>
            </div>
        </div>

    </c:param>
</c:import>