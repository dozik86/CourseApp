$.fn.editable.defaults.mode = 'inline';

$.fn.editableform.buttons = '<button type="submit" class="btn btn-info editable-submit"><i class="fa fa-fw fa-check"></i></button>' + '<button type="button" class="btn editable-cancel"><i class="fa fa-fw fa-remove"></i></button>';

$(document).ready(function () {
    $('#name.editable').editable({
        validate: function (value) {
            var v = (valib.String.length.gte(value, 4) && valib.String.length.lte(value, 32));
            if (v == false) return 'must be >= 4 and <= 32';
        }
    });


    $('#email.editable').editable({

        validate: function (value) {
            var v = valib.String.isEmailLike(value)
            if (v == false) return 'insert valid mail';
        },
        success: function (response, newValue) {
            if (response.status == 'error') return response.msg;
        }

    });

    $('#password.editable').editable({});

});