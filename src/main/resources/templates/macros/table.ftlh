<#--noinspection HtmlUnknownTarget-->
<#macro table name order = "desc">

    <link rel="stylesheet" type="text/css" href="static/datatables/datatables.min.css"/>
    <script type="text/javascript" src="static/datatables/datatables.min.js"></script>

    <script>
        $(document).ready(function() {
            $('table#${name}').DataTable({
                "order": [[ 0, "${order}" ]],
                "bDeferRender": true,
                "sPaginationType": "full_numbers",
                "oLanguage": {
                    "sLengthMenu": 'Показывать по <select class="form-control input-sm">'+
                        '<option value="10">10</option>'+
                        '<option value="25">25</option>'+
                        '<option value="50">50</option>'+
                        '<option value="100">100</option>'+
                        '<option value="500">500</option>'+
                        '<option value="-1">Все</option>'+
                        '</select>',
                    "oPaginate": {
                        "sFirst":    "Первая",
                        "sLast":     "Последняя",
                        "sNext":     "Вперёд",
                        "sPrevious": "Назад"
                    },
                    "sInfo": "Отображены с _START_ по _END_ из _TOTAL_ элементов",
                    "sSearch": "Поиск:"
                },
                "responsive": true
            });
        });
    </script>

</#macro>
