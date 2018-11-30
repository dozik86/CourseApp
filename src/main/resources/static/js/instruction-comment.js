$('#btn-comment').click(function () {
    if (isEmpty()) return;

    var comment = {
        text: $("#textarea-comment").val(),
        author: $('#comment-author').val()
    }

    addComment(comment);
});

//$('#btn-del').click(function() {
function delComment() {
    $.ajax({
        type: 'GET',
        url: '/instruction/' + getId() + '/comment/delete',
        data: {id: $('#btn-del').attr('value')},
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        success: function (data) {
            console.log("SUCCESS: ", data);
        },
        error: function (e) {
            console.log("ERROR: ", e);
            $("#comment-block").load(location.href + " #comment-block>*", "");

        },
        done: function (e) {
            console.log("DONE");
        }
    });
//});
}

function isEmpty() {
    if (!$.trim($('#textarea-comment').val())) {
        $("#msg-error").show();
    }
}

function getId() {
    var url = window.location.pathname;
    var segments = url.split('/');
    console.log(segments);
    return segments[2];
}

function addComment(comment) {
    var json = JSON.stringify(comment);
    $.ajax({
        type: 'POST',
        url: '/instruction/' + getId() + '/comment',
        data: json,
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        success: function (data) {
            console.log("SUCCESS: ", data);
        },
        error: function (e) {
            console.log("ERROR: ", e);
            $("#comment-block").load(location.href + " #comment-block>*", "");

        },
        done: function (e) {
            console.log("DONE");
        }
    });
}