function checkEmail() {
    var email = document.getElementById('email').value;
    $.ajax({
        type: 'GET',
        dataType: 'text',
        url: 'UniqueCheckServlet',
        data: {
            email: email,
        }
    }).done(function() {
        document.getElementById("NG").style.display = "none";
        document.getElementById("OK").style.display = "block";
    }).fail(function() {
        document.getElementById("OK").style.display = "none";
        document.getElementById("NG").style.display = "block";
    }).always(function() {
    });
}