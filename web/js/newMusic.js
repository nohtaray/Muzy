/*global $ */
$(function() {
    var $videoId = $('#music-youtube-video-id');
    if ($videoId.val() !== '') {
        showVideo($videoId.val());
        $('#new-music-form-area').removeClass('hidden');
    }

    $.ajax({
        url: 'YoutubeVideosServlet',
        type: 'GET',
        dataType: 'json',
        success: function(playlistItemsList) {
            // see: Youtube Data API Reference - https://developers.google.com/youtube/v3/docs/playlistItems/list?hl=ja
            displayYoutubeVideos(playlistItemsList.items);
        },
    });

    $(document).on('click', '.new-music-youtube-video', function() {
        setFormWithVideo.call(null, this);
    });

    function displayYoutubeVideos(playlistItems) {
        var $videoArea = $('#new-music-youtube-videos');
        $videoArea.empty();
        $.each(playlistItems, function(i, item) {
            appendVideoItem(item);
        });
        if ($videoArea.children().size() === 0) {
            $('<p>').html('YouTube に動画をアップロードしてください。')
                .attr('id', 'new-music-youtube-load-failed')
                .appendTo($videoArea);
        }
    }

    function appendVideoItem(item) {
        console.log(item);
        var $video = $('<div class="new-music-youtube-video clearfix thumbnail">').append(
            $('<img class="pull-left" width="120" height="90">').attr('src', item.snippet.thumbnails.default.url)
        ).append(
            $('<div class="title">').text(item.snippet.title)
        ).append(
            $('<div class="published-at">').text(item.snippet.publishedAt.split('T')[0])
        ).attr({
            'data-video-id': item.snippet.resourceId.videoId,
            'data-title': item.snippet.title,
            'data-description': item.snippet.description
        });
        $('#new-music-youtube-videos').append($video);
    }

    function setFormWithVideo(youtubeVideoElement) {
        var e = youtubeVideoElement;
        showVideo(e.dataset.videoId);
        $('#music-youtube-video-id').val(e.dataset.videoId);
        $('#music-title').val(e.dataset.title);
        $('#music-description').val(e.dataset.description);
        $('#new-music-form-area').removeClass('hidden');
        // エラー項目があったらエラーを消す
        $('#new_music .form-group.has-error')
            .removeClass('has-error')
            .children('.help-block').remove();
    }
    function showVideo(youtubeVideoId) {
        $('#music-youtube-video-area').empty().append(
            $('<iframe width="540" height="330" frameborder="0" allowfullscreen>')
                .attr('src', 'http://www.youtube.com/embed/' + youtubeVideoId + '?rel=0')
        );
    }
});
