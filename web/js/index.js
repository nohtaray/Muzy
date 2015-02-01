/*global $ */
$(function() {
    loadJson('RankingArtistVoteJsonServlet', function(artistVotes) {
        var $template = $('#ranking-artist-template').clone();
        var $container = $('#ranking-artists').empty();
        artistVotes.forEach(function(artistVote) {
            var $artist = $template.clone();
            var $name = $('<a>', {
                href: 'ArtistServlet?id=' + artistVote['artist']['artist_id']
            }).text(artistVote['artist']['name']);
            $artist.find('.ranking-artist-name').append($name);
            $artist.find('.ranking-artist-introduction').text(artistVote['artist']['introduction']);
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
            $music.find('.ranking-music-description').text(musicAdvertisement['music']['description']);
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
            $music.find('.latest-music-description').text(music['description']);
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
