var actionArr = [];

function disableAll() {
    $(".mybtn-del").attr("disabled", "disabled");
}


$(".chkbx").change(function () {
    disableAll();
    actionArr = $(".chkbx:checked").map(function (i, el) {
        $(".mybtn-del").removeAttr("disabled");
        return $(el).val();
    }).get();
    console.log(actionArr);
})


$(".mybtn-del").click(function () {
    $.ajax({
        type: 'GET',
        url: '/user/action/delete',
        data: {idArray: actionArr},
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        complete: function () {
            window.location.reload("true");
        },
        timeout: 500
    });
})