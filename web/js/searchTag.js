/*global $ */
$(function() {
    $('#search-form').on('submit', function() {
        if ($('#keyword').val() !== '') {
            // 空じゃなかったときだけクエリを送信
            $('#keyword').attr('name', 'q');
        }
    });
});
