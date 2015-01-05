/*global $, Helper */
$(function() {
    $('.buy-button').click(function() {
        var requires = $(this).data('requires');
        var qt = $(this).data('qt');
        var ok = confirm(requires + 'ポイント使って' + qt + '枚のチケットを購入します');
        if (ok) {
            Helper.postHref('', {
                ticket: $(this).data('qt')
            });
        }
    });
});
