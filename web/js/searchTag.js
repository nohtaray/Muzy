/*global $ */
$(function() {
    if ($('#keyword').val() !== '') {
        $('#keyword').removeClass('hidden');
    }

    $('#show-keywordform').on('click', function() {
        $('#keyword').removeClass('hidden');
    });
    $('#tag-search-form').submit(function() {
        if ($('#keyword').val() !== '') {
            // 空じゃなかったときだけクエリを送信
            $('#keyword').attr('name', 'q');
        }
    });
});
