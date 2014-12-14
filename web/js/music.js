/*global $ */
$(function() {
    $('#advertise-form').submit(function() {
        var use = $('#advertise-use-points').val();
        var now = $('#advertise-now-points').text();

        // 入力チェック
        if (!use.match(/^[0-9]+$/g)) {
            $('#advertise-error').removeClass('hidden').text('正の整数で入力してください');
            return false;
        } else if (use > now) {
            $('#advertise-error').removeClass('hidden').text('保有ポイントを超えています');
            return false;
        } else {
            $('#advertise-error').addClass('hidden').text('');
        }

        // 表示切り替え
        $('#advertise-roles').children().addClass('hidden');
        $('#advertise-loading').removeClass('hidden');

        // 送信
        $.ajax({
            url: 'AdvertiseServlet',
            type: 'POST',
            data: {
                id: $('#advertise-music-id').val(),
                point: use,
            },
        }).done(function() {
            $('#advertise-roles').children().addClass('hidden');
            $('#advertise-done').removeClass('hidden');
        }).fail(function() {
            $('#advertise-fail-message').text('サーバエラーが発生しました');
            $('#advertise-roles').children().addClass('hidden');
            $('#advertise-fail').removeClass('hidden');
        });

        // デフォルトの form 送信を中止
        return false;
    });
    $('#advertise-button,#advertise-retry-button').click(function() {
        $('#advertise-roles').children().addClass('hidden');
        $('#advertise-input').removeClass('hidden');
    });
});