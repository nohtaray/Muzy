/*global $, Helper */
$(function() {
    $('.delete-button').click(function() {
        var ok = confirm('本当に削除しますか？');
        if (ok) {
            Helper.postHref('DeleteArtistByAdminServlet', {
                id: $(this).data('artistId')
            });
        }
    });
});