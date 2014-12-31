/*global $ */
$(function() {
    $('#password-form').submit(function() {
        if ($('#pass1').val() !== $('#pass2').val()) {
            $('#pass1').val('').focus();
            $('#pass2').val('');
            $('#error').removeClass('hidden').text('入力が一致しません。もう一度入力してください');
            return false;
        }
    });
});
