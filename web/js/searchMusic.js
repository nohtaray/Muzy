/*global $ */
$(function() {
    // 最上部の共通部分のやつ
    $('.search-form-type').val(['music']);

    $('#music-search-form').submit(function() {
        if ($('#keyword').val() !== '') {
            // 空じゃなかったときだけクエリを送信
            $('#keyword').attr('name', 'q');
        }
    });
});
