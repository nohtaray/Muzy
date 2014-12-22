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