<%@ include file="/WEB-INF/taglibs.jsp" %>

<script>
    function submitThisTimesheetHandler(start){
        $.ajax({
            type: "post",
            url: "<spring:url value="/intervals/weekly/submit" />",
            data: {
                start: start
            },
            success: function(data) {
                if(data.message == 'success') {
                    checkTimesheet(start);
                }
            }
        });
    }

    function loadDataForWorkPerProjectDonut(start) {
        $.ajax({
            type: "post",
            url: "<spring:url value="/intervals/charts/weekly/project-hours-donut" />",
            data: {
                start: start
            },
            success: function(data) {
                if(data.message == 'success') {
                    console.log(data);

                    var donutData = [];
                    $.each( data.projectHours, function(key, value) {
                        donutData.push({
                            label: key,
                            value: value
                        });
                    });
                    drawDonut('weekly-projects-donut', donutData);
                }
            }
        });

    }

    function loadDataForWorkingHoursLine(start) {
        $.ajax({
            type: "post",
            url: "<spring:url value="/intervals/charts/weekly/working-hours-line" />",
            data: {
                start: start
            },
            success: function(data) {
                console.log(data);
                if (data.message == 'success') {
                    var lineData = [];
                    $.each(data.startings, function(key, value) {
                        var crtDay = key;
                        var crtStart = value;
                        var crtEnd = data.endings[crtDay];

                        lineData.push({
                            y: new Date(Number(crtDay)).getTime(),
                            a: new Date(crtStart).getTime(),
                            b: new Date(crtEnd).getTime()
                        });
                    });

                    console.log(lineData);

                    drawWorkingHoursLine('weekly-working-hours-line', lineData);
                }
            }
        });
    }

    function drawDonut(elementId, content) {
        Morris.Donut({
            element: elementId,
            data: content,
            resize: true
        });
    }

    function drawWorkingHoursLine(elementId, content) {
        Morris.Line({
            element: elementId,
            data: content,
            xkey: 'y',
            ykeys: ['a', 'b'],
            xlabels: 'day',
            labels: ['Arrival', 'Departure'],
            hideHover: 'auto',
            xLabelFormat: function(x) {
                return $.format.date(x, 'ddd');
            },
            yLabelFormat: function(y) {
                return $.format.date(y, 'h:mm');
            },
            resize: true
        });

    }

    function checkTimesheet(start) {
        $.ajax({
            type: "post",
            url: "<spring:url value="/intervals/weekly/check" />",
            data: {
                start: start
            },
            success: function(data) {
                if (data.message == 'success' && data.weekly != null) {
                    console.log("weekly!");
                    console.log(data);
                    if (data.weekly.status == 'OK') {
                        console.log('OK');
                        hideAnyAlertBesidesClass('.alert-success');
                    }
                    else if (data.weekly.status == 'SUBMITTED_PENDING') {
                        console.log('SUBMITTED');
                        hideAnyAlertBesidesClass('.alert-warning');
                    }
                    else if (data.weekly.status == 'REJECTED') {
                        console.log('REJECTED');
                        hideAnyAlertBesidesClass('.alert-danger');
                        $('#weekly-status #submitThisTimesheet').click(function() {
                            submitThisTimesheetHandler(start);
                        });
                        $('#weekly-status #submitThisTimesheet').show();
                    }
                    else if (data.weekly.status == 'OPEN') {
                        console.log('OPEN');
                        hideAnyAlertBesidesClass('.alert-info');
                        $('#weekly-status #submitThisTimesheet').click(function() {
                            submitThisTimesheetHandler(start);
                        });
                        $('#weekly-status #submitThisTimesheet').show();
                    }
                }
                if (data.message == 'failed') {
                    hideAllAlerts();
                }
            }
        });
    }

    function hideAnyAlertBesidesClass(classId) {
        $('#weekly-status .alert-info').hide();
        $('#weekly-status .alert-warning').hide();
        $('#weekly-status .alert-danger').hide();
        $('#weekly-status .alert-success').hide();
        $('#weekly-status #submitThisTimesheet').hide();

//        console.log('#weekly-status ' + classId);
//        console.log('#weekly-status .alert-danger');
        $('#weekly-status ' + classId).show();
    }

    function hideAllAlerts() {
        $('#weekly-status .alert-warning').hide();
        $('#weekly-status .alert-danger').hide();
        $('#weekly-status .alert-success').hide();
    }

    $(document).ready(function() {
        loadDataForWorkingHoursLine(new Date().getTime());
        loadDataForWorkPerProjectDonut(new Date().getTime());

        checkTimesheet(new Date().getTime());

        // datepicker shit.. ignore :)
        $('.date-picker').each(function () {

            var $datepicker = $(this),
                    cur_date = ($datepicker.data('date') ? moment($datepicker.data('date'), "YYYY/MM/DD") : moment()),
                    format = {
                        "weekday" : ($datepicker.find('.weekday').data('format') ? $datepicker.find('.weekday').data('format') : "dddd"),
                        "date" : ($datepicker.find('.date').data('format') ? $datepicker.find('.date').data('format') : "MMMM Do"),
                        "year" : ($datepicker.find('.year').data('year') ? $datepicker.find('.weekday').data('format') : "YYYY")
                    };

            function updateDisplay(cur_date) {
                $datepicker.find('.date-container > .weekday').text(cur_date.format(format.weekday));
                $datepicker.find('.date-container > .date').text(cur_date.format(format.date));
                $datepicker.find('.date-container > .year').text(cur_date.format(format.year));
                $datepicker.data('date', cur_date.format('YYYY/MM/DD'));
                $datepicker.find('.input-datepicker').removeClass('show-input');
            }

            updateDisplay(cur_date);

            $datepicker.on('click', '[data-toggle="calendar"]', function(event) {
                event.preventDefault();
                $datepicker.find('.input-datepicker').toggleClass('show-input');
                console.log('s-a dat click pe undeva. 1');
            });

            $datepicker.on('click', '.input-datepicker > .input-group-btn > button', function(event) {
                event.preventDefault();
                var $input = $(this).closest('.input-datepicker').find('input'),
                        date_format = ($input.data('format') ? $input.data('format') : "YYYY/MM/DD");
                if (moment($input.val(), date_format).isValid()) {
                    updateDisplay(moment($input.val(), date_format));
                }else{
                    alert('Invalid Date');
                }
                console.log('s-a dat click pe undeva. 2');
            });

            $datepicker.on('click', '[data-toggle="datepicker"]', function(event) {
                event.preventDefault();

                var cur_date = moment($(this).closest('.date-picker').data('date'), "YYYY/MM/DD"),
                        date_type = ($datepicker.data('type') ? $datepicker.data('type') : "days"),
                        type = ($(this).data('type') ? $(this).data('type') : "add"),
                        amt = ($(this).data('amt') ? $(this).data('amt') : 1);

                if (type == "add") {
                    cur_date = cur_date.add(date_type, amt);
                }else if (type == "subtract") {
                    cur_date = cur_date.subtract(date_type, amt);
                }

                // here should go everything that is supposed to happen when the week is changed.
                updateDisplay(cur_date);

                // reload charts.
                loadDataForWorkingHoursLine(cur_date._d.getTime());
                loadDataForWorkPerProjectDonut(cur_date._d.getTime());

                checkTimesheet(cur_date._d.getTime());
            });
        });
    });


</script>