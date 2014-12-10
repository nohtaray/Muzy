$(function() {
    $('.delete-button').click(function() {
        var ok = confirm('本当に削除しますか？');
        if (ok) {
            Helper.post('DeleteMusicByAdminServlet', {
                id: $(this).data('musicId')
            });
        }
    });
});