var index = 1;
var questionsArr = [];
var dropzones = [];

function getId() {
    var url = window.location.pathname;
    var segments = url.split('/');
    console.log(segments);
    return segments[2];
}


Dropzone.autoDiscover = false;

dropzones[0] = new Dropzone('#dropzone-1', {
    url: "https://api.cloudinary.com/v1_1/vasilyyatskevich/image/upload",
    addRemoveLinks: true,
    acceptedFiles: '.jpg,.png,.jpeg,.gif',
    maxFilesize: 3.0,
    maxFiles: 3,
    parallelUploads: 10000,
    uploadMultiple: true,
    autoProcessQueue: false
});

function initDropzone(i) {
    dropzones[index - 1] = new Dropzone('#dropzone-' + index, {
        url: "https://api.cloudinary.com/v1_1/vasilyyatskevich/image/upload",
        addRemoveLinks: true,
        acceptedFiles: '.jpg,.png,.jpeg,.gif',
        maxFilesize: 3.0,
        maxFiles: 3,
        parallelUploads: 10000,
        uploadMultiple: true,
        autoProcessQueue: false
    });
}

function uploadImages(i) {
    console.log(dropzones);
    var imageUrls = [];
    var len = dropzones[i].files.length;
    for (j = 0; j < len; j++) {
        const data = new FormData();
        data.append('file', dropzones[i].files[j]);
        data.append('upload_preset', 'preset');
        data.append('api_key', 924795638924219);

        const xhr = new XMLHttpRequest();
        xhr.open('POST', "https://api.cloudinary.com/v1_1/vasilyyatskevich/image/upload", false);
        const sendImage = xhr.send(data);
        const imageResponse = xhr.responseText;
        console.log('Response: ', imageResponse);
        var jsonResponse = JSON.parse(imageResponse);
        imageUrls.push(jsonResponse["url"]);
    }
    return imageUrls;
}

$(".add-div-btn").click(function () {
    if (!checkTextareas()) return false;
    else {
        addDiv();
        initDropzone(index);
        $("#msg-error").hide();
    }
});

function addDiv() {
    ++index;
    $(".textareas").append('<div class="row"><div class="col-md-12 mt-2"> <h6 class="mt-4">' + index + ':</h6></div></div>' +
        '<div class="row">' +
        '<div class="col-md-6">' +
        '<textarea class="form-control mt-1 textarea" rows="1" cols="70" id="textarea-name-' + index + '"' +
        'placeholder="Title"></textarea>' +
        '<textarea class="form-control mt-1 textarea" rows="7" cols="70" id="textarea-text-' + index + '"' +
        'placeholder="Content"></textarea>' +
        '</div>' +
        '<div class="col-md-6">' +
        //'<div id="dropzone-"' + index + 'class="dropzone "></div>' +
        '<form action="" ><div class="dropzone" id="dropzone-' + index + '"></form>' +
        '</div>' +
        '</div>');
}


function checkTextareas() {
    var i = 0;
    $(".textarea").each(function () {
        if (!$.trim($(this).val())) {
            $("#msg-error").show();
            i = 1;
            return false;
        }
    });
    if (i === 1) return false;
    else return true;
}

$(".submit-btn").click(function () {
    if (checkTextareas()) {
        for (var i = 1; i <= index; i++) {
            questionsArr[i - 1] = {
                name: $('#textarea-name-' + i).val(),
                text: $('#textarea-text-' + i).val(),
                images: uploadImages(i - 1)
            };

        }
        var workbook = {
            category: $("#category").val(),
            name: $("#name").val(),
            questions: questionsArr,
            tags: $("#tags").tagsinput('items')
        };
        sendArray(workbook);
    }
});

function showSuccessMsg() {
    $("#msg-error").hide();
    $("#msg-success").show();
}

function sendArray(workbook) {
    var json = JSON.stringify(workbook);
    $.ajax({
        type: 'POST',
        url: '/user/' + getId() + '/add',
        data: json,
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        success: function (data) {
            console.log("SUCCESS: ", data);
        },
        error: function (e) {
            console.log("ERROR: ", e);
            $('form').fadeOut('slow', function () {
            });
            $("#msg-success").fadeIn('slow', function () {

            });
        },
        done: function (e) {
            console.log("DONE");
        }
    });
}