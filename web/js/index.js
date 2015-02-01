/*global $, Helper */
$(function() {
    var LENGTH_RANKING_ARTIST = 200;
    var LENGTH_RANKING_MUSIC = 200;
    var LENGTH_LATEST_MUSIC = 100;

    loadJson('RankingArtistVoteJsonServlet', function(artistVotes) {
        var $template = $('#ranking-artist-template').clone();
        var $container = $('#ranking-artists').empty();
        artistVotes.forEach(function(artistVote) {
            var $artist = $template.clone();
            var $name = $('<a>', {
                href: 'ArtistServlet?id=' + artistVote['artist']['artist_id']
            }).text(artistVote['artist']['name']);
            $artist.find('.ranking-artist-name').append($name);
            $artist.find('.ranking-artist-introduction').text(Helper.truncateString(artistVote['artist']['introduction'], LENGTH_RANKING_ARTIST));
            $artist.removeAttr('id').removeClass('hidden').appendTo($container);
        });
    });
    loadJson('RankingMusicAdvertisementJsonServlet', function(musicAdvertisements) {
        var $template = $('#ranking-music-template').clone();
        var $container = $('#ranking-musics').empty();
        musicAdvertisements.forEach(function(musicAdvertisement) {
            var $music = $template.clone();
            var $title = $('<a>', {
                href: 'MusicServlet?id=' + musicAdvertisement['music']['music_id']
            }).text(musicAdvertisement['music']['title']);
            $music.find('.ranking-music-title').append($title);
            $music.find('.ranking-music-description').text(Helper.truncateString(musicAdvertisement['music']['description'], LENGTH_RANKING_MUSIC));
            $music.removeAttr('id').removeClass('hidden').appendTo($container);
        });
    });
    loadJson('LatestMusicJsonServlet', function(musics) {
        var $template = $('#latest-music-template').clone();
        var $container = $('#latest-musics').empty();
        musics.forEach(function(music) {
            var $music = $template.clone();
            var $title = $('<a>', {
                href: 'MusicServlet?id=' + music['music_id']
            }).text(music['title']);
            $music.find('.latest-music-title').append($title);
            $music.find('.latest-music-description').text(Helper.truncateString(music['description'], LENGTH_LATEST_MUSIC));
            $music.removeAttr('id').removeClass('hidden').appendTo($container);
        });
    });

    function loadJson(url, onSuccess) {
        $.ajax({
            type: 'GET',
            url: url,
            dataType: 'json'
        }).success(onSuccess);
    }
});
