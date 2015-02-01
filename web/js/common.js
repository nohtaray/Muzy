/*global jQuery */

/**
 * 全画面共通のスクリプト
 */

window.Helper = (function($) {

    return {
        postHref: function(url, params) {
            var $form = $('<form>', { method: 'POST', action: url });
            Object.keys(params).forEach(function(key) {
                $('<input type="hidden">').attr('name', key).val(params[key]).appendTo($form);
            });
            $form.submit();
        },
        truncateString: function(src, maxLength) {
            if (src.length <= maxLength) {
                return src;
            } else {
                return src.slice(0, maxLength) + '...';
            }
        },
    };
})(jQuery);
