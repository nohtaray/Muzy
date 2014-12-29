/*global $ */
// jquery raty のデフォルト設定
$.extend($.fn.raty.defaults, {
    path: 'img',
    hints: ['1', '2', '3', '4', '5'],
    number: 5,
});
$(function() {
    // advertisement 機能
    $(function() {
        loadNowPoints();

        $('#advertise-form').submit(function() {
            var use = parseInt( $('#advertise-use-points').val(), 10);
            var now = parseInt( $('#advertise-now-points').text(), 10);

            // 入力チェック
            if (!isFinite(use) || use <= 0) {
                $('#advertise-error').removeClass('hidden').text('正の整数で入力してください');
                return false;
            } else if (use > now) {
                $('#advertise-error').removeClass('hidden').text('ポイントが足りません');
                return false;
            } else {
                $('#advertise-error').addClass('hidden').text('');
            }

            switchView('sending');

            // 送信
            $.ajax({
                url: 'AdvertiseServlet',
                type: 'POST',
                data: {
                    id: $('#advertise-music-id').val(),
                    point: use,
                },
            }).done(function() {
                switchView('done');
                loadNowPoints();
            }).fail(function() {
                $('#advertise-fail-message').text('サーバエラーが発生しました');
                switchView('fail');
            });

            // デフォルトの form 送信を中止
            return false;
        });
        $('#advertise-retry-button').click(function() {
            switchView('input');
        });
        $('#advertise-button').click(function() {
            $('#advertise-use-points').val('');
            switchView('input');
        });
        function switchView(name) {
            $('#advertise-roles').children().addClass('hidden');
            $('#advertise-' + name).removeClass('hidden');
        }
        function loadNowPoints() {
            $.ajax({
                url: 'PointServlet',
                type: 'GET',
                dataType: 'json',
            }).done(function(res) {
                $('#advertise-now-points').text(res['point_count']);
            });
        }
    });

    // タグ
    $(function() {
        loadTags();

        $(document).on('click', '.tag-delete-button', function() {
            var ok = confirm('このタグを削除します。よろしいですか。');
            if (!ok) return;

            var tagId = $(this).parent().data('tagId');
            $.ajax({
                url: 'DeleteTagsServlet',
                type: 'POST',
                data: {
                    tagid: tagId
                }
            }).done(function() {
                loadTags();
            });
        });
    });
});

function HeaderClick() {
    var target = document.getElementById("ContentsPanel");
    if (target.style.display === "none") {
        target.style.display = "block";
    } else {
        target.style.display = "none";
    }
}

function funcSignUpTags() {
    $.ajax({
        type: 'POST',
        dataType: 'text',
        url: 'SignUpTagsServlet', //ここにServletのアドレス
        //ここがServletに渡される値
        //複数渡せる左が変数で右が値
        data: {
            tagname: $('#tagname').val(),
            score: $('#score').val(),
            musicid: $('#musicid').val()
        }
    }).done(function (data) {
        // ajax ok
        alert("成功");
        loadTags();
    }).fail(function (data) {
        // ajax error
        //追加できませんでした的な通知
        alert("失敗");
    }).always(function (data) {
        // ajax complete
    });
}
function loadTags() {
    var musicId = $('#musicid').val();
    $.ajax({
        url: 'MusicTagsServlet',
        type: 'GET',
        dataType: 'json',
        data: {
            id: musicId
        },
    }).done(function(tags) {
        var $tags = $('#tags').empty();
        tags.forEach(function(tag) {
            $tags.append(makeTagElement(tag));
        });

        function makeTagElement(tag) {
            var $tag = $('<div>').addClass('tag').attr('data-tag-id', tag['tag_id']);
            // tag name
            $('<a>', { href: 'SearchMusicServlet?t=' + tag['name'] }).addClass('tag-name').text(tag['name']).appendTo($tag);
            // 自分が投稿した楽曲の場合
            if ($('#is-my-music').val() === 'true') {
                // 削除ボタン
                $('<span>', { class: 'tag-delete-button' }).html('&times;').appendTo($tag);
            }
            // ログインしてる場合
            if ($('#is-logged-in').val() === 'true') {
                // タグ評価ボタン
                $('<div>').addClass('tag-rate').raty({
                    click: function(score) {
                        var tagId = $(this).parent().data('tagId');
                        rateTag(tagId, score);
                    },
                }).appendTo($tag);
            }
            return $tag;
        }
    });
}
function rateTag(tagId, score) {
    $.ajax({
        url: 'EvaluationTagsServlet',
        type: 'POST',
        data: {
            tagid: tagId,
            evaluation: score
        }
    });
}

function funcReview() {
    var content = $('#comment-add-content').val();
    $.ajax({
        type: 'POST',
        dataType: 'text',
        url: 'AddReviewServlet', //ここにServletのアドレス
        //ここがServletに渡される値
        //複数渡せる左が変数で右が値
        data: {
            review: content,
            musicid: $('#musicid').val()
        }
    }).done(function (data) {
        // ajax ok
        //追加表示
        var $newreview = $('<div>', { class: 'comment' }).appendTo($('#reviewarea'));
        // 内容
        $('<div>', { class: 'comment-content' }).text(content).appendTo($newreview);
        // 評価ボタン
        $('<input type="button" onclick="evaluationCommentGood()" value="良い" /><input type="button" onclick="evaluationCommentBad()" value="悪い" /><input type="button" onclick="deleteComment()" value="削除" />').appendTo($newreview);
        alert("成功");
    }).fail(function (data) {
        // ajax error
        //追加できませんでした的な通知
        alert("失敗");
    }).always(function (data) {
        // ajax complete
    });
}
//------------ここまで実行可能確認------------------

function evaluationCommentGood() {
    $.ajax({
        type: 'POST',
        dataType: 'text',
        url: 'EvaluationCommentsServlet', //ここにServletのアドレス
        //ここがServletに渡される値
        //複数渡せる左が変数で右が値
        data: {
            commentid: 3, //仮
            eva: 1
        }
    }).done(function () {
        alert("良い成功");
    }).fail(function () {
        alert("良い失敗");
    }).always(function () {
    });
}
function evaluationCommentBad() {
    $.ajax({
        type: 'POST',
        dataType: 'text',
        url: 'EvaluationCommentsServlet', //ここにServletのアドレス
        //ここがServletに渡される値
        //複数渡せる左が変数で右が値
        data: {
            commentid: 3, //仮
            eva: -1
        }
    }).done(function () {
        alert("悪い成功");
    }).fail(function () {
        alert("悪い失敗");
    }).always(function () {
    });
}

function deleteComment() {
    $.ajax({
        type: 'POST',
        dataType: 'text',
        url: 'DeleteCommentServlet', //ここにServletのアドレス
        //ここがServletに渡される値
        //複数渡せる左が変数で右が値
        data: {
            commentid: 3 //仮
        }
    }).done(function () {
        alert("削除成功");
    }).fail(function () {
        alert("削除失敗");
    }).always(function () {
    });
}