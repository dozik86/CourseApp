var lang;
$(document).ready(function () {
    var url_string = window.location.href;
    var url = new URL(url_string);
    var c = url.searchParams.get("lang");
    if (c == null) lang = '//cdn.datatables.net/plug-ins/1.10.16/i18n/English.json';
    if (c === 'en') lang = '//cdn.datatables.net/plug-ins/1.10.16/i18n/English.json';
    if (c === 'ru') lang = '//cdn.datatables.net/plug-ins/1.10.16/i18n/Russian.json';


    $('.to-search').each(function () {
        var title = $(this).text();
        $(this).html('<input type="text" placeholder="' + title + '" />');
    });

    var table = $('#example').DataTable(
        {
            "language": {
                "url": lang
            },
            "initComplete": function () {
                table.columns().every(function () {
                    var that = this;

                    $('input', this.footer()).on('keyup change', function () {
                        if (that.search() !== this.value) {
                            that
                                .search(this.value)
                                .draw();
                        }
                    });
                });
            },

            "sDom": 't'
        });


    $('#example tfoot tr').prependTo('#example thead');
});
