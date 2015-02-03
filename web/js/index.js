/*global $, Helper */
$(function() {
    var Lengths = {
        rankingArtistIntroduction: 200,
        rankingArtistTitle: 30,
        rankingMusicDescription: 200,
        rankingMusicTitle: 30,
        latestMusicDescription: 50,
        latestMusicTitle: 30,
    };

    loadJson('RankingArtistVoteJsonServlet', function(artistVotes) {
        var $template = $('#ranking-artist-template').clone();
        var $container = $('#ranking-artists').empty();
        artistVotes.forEach(function(artistVote) {
            var $artist = $template.clone();
            var $name = $('<a>', {
                href: 'ArtistServlet?id=' + artistVote['artist']['artist_id']
            }).text(artistVote['artist']['name']);
            $artist.find('.ranking-artist-name').append($name);
            $artist.find('.ranking-artist-introduction').text(Helper.truncateString(artistVote['artist']['introduction'], Lengths.rankingArtistIntroduction));
            $artist.removeAttr('id').removeClass('hidden').appendTo($container);
        });
    });
    loadJson('RankingMusicAdvertisementJsonServlet', function(musicAdvertisements) {
        var $template = $('#ranking-music-template').clone();
        var $container = $('#ranking-musics').empty();
        musicAdvertisements.forEach(function(musicAdvertisement) {
            var $music = $template.clone();
            $music.find('.ranking-music-a').attr('href', 'MusicServlet?id=' + musicAdvertisement['music']['music_id']);
            $music.find('.ranking-music-img').attr('src', 'http://img.youtube.com/vi/' + musicAdvertisement['music']['youtube_video_id'] + '/1.jpg');
            $music.find('.ranking-music-title').text(Helper.truncateString(musicAdvertisement['music']['title'], Lengths.rankingMusicTitle));
            $music.find('.ranking-music-description').text(Helper.truncateString(musicAdvertisement['music']['description'], Lengths.rankingMusicDescription));
            $music.removeAttr('id').removeClass('hidden').appendTo($container);
        });
    });
    loadJson('LatestMusicJsonServlet', function(musics) {
        var $template = $('#latest-music-template').clone();
        var $container = $('#latest-musics').empty();
        musics.forEach(function(music) {
            var $music = $template.clone();
            $music.find('.latest-music-a').attr('href', 'MusicServlet?id=' + music['music_id']);
            $music.find('.latest-music-img').attr('src', 'http://img.youtube.com/vi/' + music['youtube_video_id'] + '/1.jpg');
            $music.find('.latest-music-title').text(Helper.truncateString(music['title'], Lengths.latestMusicTitle));
            $music.find('.latest-music-description').text(Helper.truncateString(music['description'], Lengths.latestMusicDescription));
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
