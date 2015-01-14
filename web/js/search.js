/*global $ */
$(function() {
    // メソッド分割が雑
    // url に応じて並び順をチェックする
    checkOrderRadio();

    // 並び替えのチェックボックスがチェックされたら即座に更新する
    $('#orders input[name="o"]').on('change', function() {
        var now = pickUrlQuery('o');
        if (now != null) {
            // 今のクエリを置き換え
            location.search = location.search.replace(/([?&])o=([0-9]+)/, '$1o=' + $(this).val());
        } else {
            // クエリを追加
            location.search = location.search + '&o=' + $(this).val();
        }
    });

    function checkOrderRadio() {
        var order = pickUrlQuery('o');
        if (order != null) {
            $('#orders input[name="o"]').val([order]);
        }
    }
    function pickUrlQuery(name) {
        var q = location.search.match(new RegExp('[?&]' + name + '=([0-9]+)'));
        if ($.isArray(q)) {
            return q[1];
        } else {
            return void(0);
        }
    }
});
