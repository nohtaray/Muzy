/*global $ */
$(function() {
    $('#tag-search-form').submit(function() {
        if ($('#keyword').val() !== '') {
            // 空じゃなかったときだけクエリを送信
            $('#keyword').attr('name', 'q');
        }
    });
});
