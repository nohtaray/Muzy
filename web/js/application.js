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
    };
})(jQuery);
