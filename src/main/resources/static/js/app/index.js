$('#btn-save').on('click', function() {
    let data = {
        title : $('#title').val(),
        author : $('#author').val(),
        content : $('#content').val()
    };

    $.ajax({
        type : 'POST',
        url : '/api/v1/article',
        dataType : 'json',
        contentType : 'application/json; charset=utf-8',
        data : JSON.stringify(data)
    }).done(function () {
        alert('글이 등록되었습니다.');
        window.location.href= '/';
    }).fail(function () {
        alert(JSON.stringify(error));
    });
});