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
            location.href = '';
        });
    });
    $('.mylist').on('click', function () {
        location.hash = $('.mylist').index(this);
        $('#mylist-title').text($(this).find('.mylist-name').text());
        $('#loading-image').removeClass('hidden');
        fetchDetails($(this).data('mylistId'), function (details) {
        $('#loading-image').addClass('hidden');
            showDetails(details);
        });
    });

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
                showArtist(detail.artist, $container);
            } else if (detail.music != null) {
                showMusic(detail.music, $container);
            }
        });
    }
    function showArtist(artist, $container) {
        var $artist = $('#artist-template').clone();
        $artist.find('.artist-a').attr('href', 'ArtistServlet?id=' + artist.artistId);
        $artist.find('.artist-img').attr('src', artist.user.iconImageFile);
        $artist.find('.artist-name').text(artist.name);
        $artist.find('.artist-introduction').text(Helper.truncateString(artist.introduction, Lengths.artistIntroduction));
        $artist.removeAttr('id').removeClass('hidden').appendTo($container);
    }
    function showMusic(music, $container) {
        var $music = $('#music-template').clone();
        $music.find('.music-a').attr('href', 'MusicServlet?id=' + music.musicId);
        $music.find('.music-img').attr('src', 'http://img.youtube.com/vi/' + music.youtubeVideoId + '/1.jpg');
        $music.find('.music-title').text(Helper.truncateString(music.title, Lengths.musicTitle));
        $music.find('.music-description').text(Helper.truncateString(music.description, Lengths.musicDescription));
        $music.removeAttr('id').removeClass('hidden').appendTo($container);
        console.log($music, $container);

    }
});
function deleteDetail(detailId, target) {
    $.ajax({
        type: 'POST',
        dataType: 'text',
        url: 'MyListDetailServlet',
        data: {
            id: detailId
        }
    }).done(function () {
        $(target).parent().remove();
        alert("削除しました。");
    }).fail(function () {
        alert("削除できません。");
    }).always(function () {
    });
}
function deleteMyList(mylistId, target) {
    $.ajax({
        type: 'POST',
        dataType: 'text',
        url: 'MyListServlet',
        data: {
            id: mylistId
        }
    }).done(function () {
        $(target).parent().remove();
        alert("削除しました。");
    }).fail(function () {
        alert("削除できません。");
    }).always(function () {
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