function deleteDetail(detailId, target) {
    $.ajax({
        type: 'POST',
        dataType: 'text',
        url: 'MyListDetailServlet',
        data: {
            id: detailId
        }
    }).done(function () {
        $(target).parent().remove();
        alert("削除しました。");
    }).fail(function () {
        alert("削除できません。");
    }).always(function () {
    });
}
function deleteMyList(mylistId, target) {
    $.ajax({
        type: 'POST',
        dataType: 'text',
        url: 'MyListServlet',
        data: {
            id: mylistId
        }
    }).done(function () {
        $(target).parent().remove();
        alert("削除しました。");
    }).fail(function () {
        alert("削除できません。");
    }).always(function () {
    });
}
function addMyList(){
    var name = document.getElementById('name').value;
    if(name == "") return;
    $.ajax({
        type: 'POST',
        dataType: 'text',
        url : 'AddMyListServlet',
        data : {
            name: name
        }
    }).done (function() {
        alert("追加しました。");
    }).fail(function () {
        alert("削除できません。");
    });
    
}