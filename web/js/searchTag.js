/*global $ */
$(function() {
    // 最上部の共通部分のやつ
    $('.search-form-type').val(['tag']);

    if ($('#keyword').val() !== '') {
        $('#keyword').removeClass('hidden');
    }

    $('#show-keywordform').on('click', function() {
        $('#keyword').removeClass('hidden').focus();
    });
    $('#tag-search-form').submit(function() {
        if ($('#keyword').val() !== '') {
            // 空じゃなかったときだけクエリを送信
            $('#keyword').attr('name', 'q');
        }
    });
});
