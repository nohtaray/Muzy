/*global $, Helper */
$(function() {
    $('#delete-button').click(function() {
        var ok = confirm('本当に削除しますか？この動作は取り消せません。');
        if (ok) {
            Helper.postHref('DeleteMusicServlet', {
                id: $(this).data('musicId')
            });
        }
    });
});
