/*global $, Helper */
$(function() {
    // googleアカウントログイン用コールバック
    window.googleLoginCallback = function(authResult) {
        if (authResult['code']) {   // 成功
            Helper.postHref('LoginWithGoogleServlet', {
                code: authResult['code']
            });
        } else if (authResult['error']) {   // 失敗
            console.log('There was an error: ' + authResult['error']);
        }
    };
});


