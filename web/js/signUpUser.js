function checkEmail() {
    var email = document.getElementById('email').value;
    $.ajax({
        type: 'GET',
        dataType: 'text',
        url: 'UniqueCheckServlet',
        data: {
            email: email,
        }
    }).done(function(result) {
        if (result === "OK") {
            ok();
        } else {
            ng();
        }
    }).fail(function() {
        ng();
    }).always(function() {
    });
    
    function ok() {
        document.getElementById("NG").style.display = "none";
        document.getElementById("OK").style.display = "block";
    }
    function ng() {
        document.getElementById("OK").style.display = "none";
        document.getElementById("NG").style.display = "block";
    }
}