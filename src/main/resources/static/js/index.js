$(document).ready(function () {
    $.ajax({
        type: 'GET',
        url: '/tagcloud',
        data: '',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        success: function (data) {
            console.log("SUCCESS: ", data);
        },
        error: function (e) {
            console.log("ERROR: ", e);
            addTagCloud(e.responseText);

        },
        done: function (e) {
            console.log("DONE");
        }
    });

});

function addTagCloud(tagsString) {
    var tags = tagsString.replace(/[\[\]']+/g, '').split(', ');
    var words = [];
    var count;
    for (i = 0; i < tags.length; i++) {
        if (tags[i] == null) return;
        count = countInArray(tags, tags[i]);
        if (count > 1) {
            var temp = i;
            tags = delArray(tags, tags[i], i);
            i = temp;

        }
        words[i] = {
            text: tags[i],
            weight: count,
            link: '/search/tag/' + tags[i]
        }
    }
    console.log(words);
    $('#tag-cloud').jQCloud(words, {
        width: 500,
        height: 350
    });
}

function countInArray(array, what) {
    return array.filter(item => item == what).length; //todo array
}

function delArray(array, what, index) {
    for (i = index + 1; i < array.length; i++) {
        if (array[i] == what) array.splice(i, 1);
    }
    return array;
}