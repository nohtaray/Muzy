/*global $ */
$(function() {
    // advertisement 機能
    $(function() {
        loadNowPoints();
        
        $('#advertise-form').submit(function() {
            var use = parseInt( $('#advertise-use-points').val(), 10);
            var now = parseInt( $('#advertise-now-points').text(), 10);

            // 入力チェック
            if (use <= 0) {
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
});

function HeaderClick() {
    target = document.getElementById("ContentsPanel");
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
            //score: $('#score').val(),
            musicid: $('#musicid').val()
        }
    }).done(function (data) {
        // ajax ok
        //追加表示処理
        $tag = $('#tag');
        $newtag = $('#tagname');
        $('<span>' + " " + $newtag.val() + " " + '</span>').appendTo($tag);
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

function funcReview() {
    $.ajax({
        type: 'POST',
        dataType: 'text',
        url: 'AddReviewServlet', //ここにServletのアドレス
        //ここがServletに渡される値
        //複数渡せる左が変数で右が値
        data: {
            review: $('#review').val(),
            musicid: $('#musicid').val()
        }
    }).done(function (data) {
        // ajax ok
        //追加表示処理
        $reviewarea = $('#reviewarea');
        $newreview = $('#review');
        $('<br /><br /><span>' + $newreview.val() + '</span>').appendTo($reviewarea);
        $('<br /><input type="button" onclick="evaluationCommentGood()" value="良い" /><input type="button" onclick="evaluationCommentBad()" value="悪い" /><input type="button" onclick="deleteComment()" value="削除" />').appendTo($reviewarea);
        alert("成功");
    }).fail(function (data) {
        // ajax error
        //追加できませんでした的な通知
        alert("失敗");
    }).always(function (data) {
        // ajax complete
    });
}

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