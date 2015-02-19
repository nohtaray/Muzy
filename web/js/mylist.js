$(function () {
    $(function () {
        var mylistSelected = location.hash.match(/^#[0-9]+$/g);
        var eq = 0;
        if (mylistSelected != null) {
            eq = parseInt(mylistSelected[0].slice(1), 10);
        }
        $('.mylist').eq(eq).trigger('click');
    });

    $('#add-mylist-button').on('click', function () {
        var name = prompt('マイリストの名前を入力してください');
        if (name == null || name === '')
            return;

        addMyList(name, function () {
            location.reload();
        });
    });
    $('.mylist').on('click', function () {
        location.hash = $('.mylist').index(this);
        $('#mylist-title').text($(this).find('.mylist-name').text());
        $('.delete-mylist-button').attr('data-mylist-id', $(this).data('mylistId'));
        $('#loading-image').removeClass('hidden');
        $('#right-column').removeClass('hidden');
        fetchDetails($(this).data('mylistId'), function (details) {
            $('#loading-image').addClass('hidden');
            if (details.length > 0) {
                showDetails(details);
            } else {
                $('#details').empty().text('このマイリストには項目がありません。');
            }
        });
    });
    $(document).on('click', '.delete-detail-button', function () {
        var ok = confirm('この項目を削除しますか？');
        if (!ok)
            return;

        deleteDetail($(this).data('mylistDetailId'), function () {
            location.reload();
        });
    });
    $(document).on('click', '.delete-mylist-button', function() {
        var ok = confirm('このマイリストを削除しますか？この動作は取り消せません');
        if (!ok) return;
        
        deleteMyList($(this).data('mylistId'), function() {
            location.href = '';
        });
    })

    function fetchDetails(mylistId, callback) {
        $.ajax({
            url: 'MylistJson',
            type: 'GET',
            dataType: 'json',
            data: {
                id: mylistId
            },
        }).done(callback).fail(function () {
            alert('マイリストの取得に失敗しました');
        });
    }

    var Lengths = {
        artistIntroduction: 200,
        artistTitle: 30,
        musicDescription: 200,
        musicTitle: 30,
    };
    function showDetails(details) {
        var $container = $('#details').empty();
        details.forEach(function (detail) {
            if (detail.artist != null) {
                showArtist(detail.artist, $container, detail.mylistDetailId);
            } else if (detail.music != null) {
                showMusic(detail.music, $container, detail.mylistDetailId);
            }
        });
    }
    function showArtist(artist, $container, detailId) {
        var $artist = $('#artist-template').clone();
        $artist.find('.artist-a').attr('href', 'ArtistServlet?id=' + artist.artistId);
        $artist.find('.artist-img').attr('src', artist.user.iconImageFile);
        $artist.find('.artist-name').text(artist.name);
        $artist.find('.artist-introduction').text(Helper.truncateString(artist.introduction, Lengths.artistIntroduction));
        $artist.find('.delete-detail-button').attr('data-mylist-detail-id', detailId);
        $artist.removeAttr('id').removeClass('hidden').appendTo($container);
    }
    function showMusic(music, $container, detailId) {
        var $music = $('#music-template').clone();
        $music.find('.music-a').attr('href', 'MusicServlet?id=' + music.musicId);
        $music.find('.music-img').attr('src', 'http://img.youtube.com/vi/' + music.youtubeVideoId + '/1.jpg');
        $music.find('.music-title').text(Helper.truncateString(music.title, Lengths.musicTitle));
        $music.find('.music-description').text(Helper.truncateString(music.description, Lengths.musicDescription));
        $music.find('.delete-detail-button').attr('data-mylist-detail-id', detailId);
        $music.removeAttr('id').removeClass('hidden').appendTo($container);

    }
});
function deleteDetail(detailId, callback) {
    $.ajax({
        type: 'POST',
        dataType: 'text',
        url: 'MyListDetailServlet',
        data: {
            id: detailId
        }
    }).done(callback).fail(function () {
        alert("項目の削除に失敗しました。");
    });
}
function deleteMyList(mylistId, callback) {
    $.ajax({
        type: 'POST',
        dataType: 'text',
        url: 'MyListServlet',
        data: {
            id: mylistId
        }
    }).done(callback).fail(function() {
        alert("マイリストの削除に失敗しました。");
    });
}
function addMyList(name, onSuccess) {
    $.ajax({
        type: 'POST',
        dataType: 'text',
        url: 'AddMyListServlet',
        data: {
            name: name
        }
    }).done(onSuccess).fail(function () {
        alert("追加に失敗しました");
    });
}