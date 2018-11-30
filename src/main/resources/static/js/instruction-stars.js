$('.star').hover(function () {
    var value = $(this).attr('value');
    highlightStars(value);

}, function () {
    disHighlightAll();
});

function getInstructionId() {
    var url = window.location.pathname;
    var segments = url.split('/');
    return segments[2];
}

function highlightStars(n) {
    for (i = 1; i <= n; i++) {
        $('#star-' + i).removeClass('fa-star-o');
        $('#star-' + i).addClass('fa-star');
    }
}

function disHighlightAll() {
    for (i = 1; i <= 5; i++) {
        $('#star-' + i).removeClass('fa-star');
        $('#star-' + i).addClass('fa-star-o');
    }
}

function showRatingPanel() {
    $('.rating-panel').show();
}

$('.star').click(function () {
    var rating = {
        value: $(this).attr('value'),
        author: $('#loggedInUser').val()
    }

    addRating(rating);
});

function addRating(rating) {
    var json = JSON.stringify(rating);
    $.ajax({
        type: 'POST',
        url: '/instruction/' + getInstructionId() + '/rating',
        data: json,
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        success: function (data) {
            console.log("SUCCESS: ", data);
        },
        error: function (e) {
            console.log("ERROR: ", e);
            $('.rating-panel').hide();
            $('.your-rating').show();
            $('#user-rating').html(rating.value);

        },
        done: function (e) {
            console.log("DONE");
        }
    });
}