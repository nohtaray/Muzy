/*global $, Helper */
$(function() {
    // vote 機能
    $(function() {
        loadNowTickets();

        $('#vote-form').submit(function() {
            var use = parseInt( $('#vote-use-tickets').val(), 10);
            var now = parseInt( $('#vote-now-tickets').text(), 10);

            // 入力チェック
            if (!isFinite(use) || use <= 0) {
                $('#vote-error').removeClass('hidden').text('正の整数で入力してください');
                return false;
            } else if (use > now) {
                $('#vote-error').removeClass('hidden').text('チケットが足りません');
                return false;
            } else {
                $('#vote-error').addClass('hidden').text('');
            }

            switchView('sending');

            // 送信
            $.ajax({
                url: 'VoteServlet',
                type: 'POST',
                data: {
                    id: $('#vote-artist-id').val(),
                    ticket: use,
                },
            }).done(function() {
                switchView('done');
                loadNowTickets();
            }).fail(function() {
                $('#vote-error').removeClass('hidden').text('サーバエラーが発生しました');
                switchView('form');
            });

            return false;
        });
        $('#vote-retry-button').click(function() {
            switchView('form');
        });
        $('#vote-button').click(function() {
            $('#vote-use-tickets').val('');
            switchView('form');
        });
        function switchView(name) {
            $('#vote-roles').children().addClass('hidden');
            $('#vote-' + name).removeClass('hidden');
        }
        function loadNowTickets() {
            $.ajax({
                url: 'PointServlet',
                type: 'GET',
                dataType: 'json',
            }).done(function(res) {
                $('#vote-now-tickets').text(res['vote_ticket_count']);
            });
        }
    });
    // message
    $(function() {
        $(document).on('click', '.message-delete-button', function() {
            var ok = confirm('このメッセージを削除します。よろしいですか？');
            if (!ok) return;

            Helper.postHref('DeleteMessageServlet', {
                artist: $(this).data('artistId'),
                message: $(this).data('messageId')
            });
        });
    });
});
function addArtistMyListDetail(mylistId, artistId){
    $.ajax({
        type: 'GET',
        dataType: 'text',
        url: 'AddMyListDetailServlet',
        data: {
            mylistid: mylistId,
            artistid: artistId
        }
    }).done(function () {
        alert("追加しました");
    }).fail(function () {
        alert("追加済みです");
    });
}
