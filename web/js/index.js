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
    var Limits = {
        rankingArtist: 10,
        rankingMusic: 10,
        latestMusic: 10,
    };

    loadJson('RankingArtistVoteJsonServlet', Limits.rankingArtist, function(artistVotes) {
        var $template = $('#ranking-artist-template').clone();
        var $container = $('#ranking-artists').empty();
        artistVotes.forEach(function(artistVote) {
            var $artist = $template.clone();
            $artist.find('.ranking-artist-a').attr('href', 'ArtistServlet?id=' + artistVote['artist']['artist_id']);
            $artist.find('.ranking-artist-img').attr('src', artistVote['artist']['user']['icon_image_file']);
            $artist.find('.ranking-artist-name').text(artistVote['artist']['name']);
            $artist.find('.ranking-artist-introduction').text(Helper.truncateString(artistVote['artist']['introduction'], Lengths.rankingArtistIntroduction));
            $artist.find('.ranking-artist-points').text(artistVote['spent_tickets_sum']);
            $artist.removeAttr('id').removeClass('hidden').appendTo($container);
        });
    });
    loadJson('RankingMusicAdvertisementJsonServlet', Limits.rankingMusic, function(musicAdvertisements) {
        var $template = $('#ranking-music-template').clone();
        var $container = $('#ranking-musics').empty();
        musicAdvertisements.forEach(function(musicAdvertisement) {
            var $music = $template.clone();
            $music.find('.ranking-music-a').attr('href', 'MusicServlet?id=' + musicAdvertisement['music']['music_id']);
            $music.find('.ranking-music-img').attr('src', 'http://img.youtube.com/vi/' + musicAdvertisement['music']['youtube_video_id'] + '/1.jpg');
            $music.find('.ranking-music-title').text(Helper.truncateString(musicAdvertisement['music']['title'], Lengths.rankingMusicTitle));
            $music.find('.ranking-music-description').text(Helper.truncateString(musicAdvertisement['music']['description'], Lengths.rankingMusicDescription));
            $music.find('.ranking-music-points').text(musicAdvertisement['spent_point_sum']);
            $music.removeAttr('id').removeClass('hidden').appendTo($container);
        });
    });
    loadJson('LatestMusicJsonServlet', Limits.latestMusic, function(musics) {
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

    function loadJson(url, limit, onSuccess) {
        $.ajax({
            type: 'GET',
            url: url,
            dataType: 'json',
            data: {
                limit: limit
            },
        }).success(onSuccess);
    }
});
