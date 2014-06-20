
var airportsLoaded = false;
var planesLoaded = false;
var weekDaysCapitalised = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'];

$(document).ready(function() {
    initTimepicker('departureTimepicker');
    initTimepicker('arrivalTimepicker');

    loadTemplates();

    initDatepickers();
});

refreshTemplates = function() {
    var templateContainer = $('#templateContainer');
    templateContainer.empty();

    loadTemplates();
}

initDatepickers = function() {
    $('#datepickersHolder #startDate').datepicker({});
    $('#datepickersHolder #startDate').datepicker().on('changeDate', function(ev) {
        $('#generateFlightsModal #secretStartDate').val(ev.date);
    });

    $('#datepickersHolder #enddate').datepicker({});
    $('#datepickersHolder #enddate').datepicker().on('changeDate', function(ev) {
        $('#generateFlightsModal #secretEndDate').val(ev.date);
    });
}

initTimepicker = function (timepickerId) {
    $('#' + timepickerId).timepicker(
        {
            minuteStep: 1,
            template: 'default',
            showSeconds: true,
            showMeridian: false
        }
    );
}

openAddTemplateModal = function() {
    $('#addTemplateModal').modal('show');
    if (!airportsLoaded) {
        loadAirports();
    }
    if (!planesLoaded) {
        loadPlaneTypes();
    }

}

function loadAirports() {
    $.ajax({
        type: "get",
        url: "/diplomarbeit/airvals/airport/load",
        success: function(data) {
            airportsLoaded  = true;
            console.log(data);

            var departureAirport = $('#departuringAirport');
            var arrivalAirport = $('#arrivalAirport');
            departureAirport.empty();
            arrivalAirport.empty();
            departureAirport.append('<option value="none"> select </option>');
            arrivalAirport.append('<option value="none"> select </option>');
            for (var i = 0; i < data.airports.length; i ++) {
                departureAirport.append('<option value="' + data.airports[i].id + '">' +
                    data.airports[i].name +
                    '(' + data.airports[i].code + ')' +
                    '</option>');
                arrivalAirport.append('<option value="' + data.airports[i].id + '">' +
                    data.airports[i].name +
                    '(' + data.airports[i].code + ')' +
                    '</option>');
            }
        }
    });

}

function loadPlaneTypes() {
    $.ajax({
        type: "get",
        url: "/diplomarbeit/airvals/plane/load",
        success: function(data) {

            var planeTypeSelect = $('#planeTypes');
            planeTypeSelect.empty();
            planeTypeSelect.append('<option value="none"> select </option>');

            for (var i = 0; i < data.planes.length; i ++) {
                planeTypeSelect.append('<option value="' + data.planes[i].id + '">' +
                    data.planes[i].name +
                    '</option>');
            }
            planesLoaded = true;
        }
    });
}

saveTemplate = function() {
    var error = '';

    var departuringAirportId = $('#departuringAirport option:selected').val();
    if (departuringAirportId == undefined || departuringAirportId == 'none') {
        error += '* No Airport for departure selected\n';
    }

    var arrivalAirportId = $('#arrivalAirport option:selected').val();
    if (arrivalAirportId == undefined || arrivalAirportId == 'none') {
        error += '* No Airport for arrival selected\n';
    }

    var planeTypeId = $('#planeTypes option:selected').val();
    if (planeTypeId == undefined || planeTypeId == 'none') {
        error += '* No Plane Type selected\n';
    }

    var departureTime;
    var departureTimeText = $('#departureTimepicker').val();
    if (departureTimeText == undefined || departureTimeText == 'none') {
        error += '* Departure time is incorrect\n';
    }
    else {
        departureTime = new Date("October 21, 1991 " + departureTimeText);
    }

    var arrivalTime;
    var arrivalTimeText = $('#arrivalTimepicker').val();
    if (arrivalTimeText == undefined || arrivalTimeText == 'none') {
        error += '* Arrival time is incorrect\n';
    }
    else {
        arrivalTime = new Date("October 21, 1991 " + arrivalTimeText);
    }

    var days = '';
    var weekDays = ['monday', 'tuesday', 'wednesday', 'thursday', 'friday', 'saturday', 'sunday'];
    for (var i = 0; i < weekDays.length; i ++) {
        if($('#' + weekDays[i]).prop('checked')) {
            days += '1';
        }
        else {
            days += '0';
        }
    }

    $.ajax({
        type: "post",
        url: "/diplomarbeit/airvals/template/save",
        data: {
            departureId: departuringAirportId,
            arrivalId: arrivalAirportId,
            planeId: planeTypeId,
            departureTime: departureTime.getTime(),
            arrivalTime: arrivalTime.getTime(),
            days: days
        },
        success: function(data) {
            console.log(data);
            if (data.message == "success" && data.error.length == 0) {
                alert("Template successfully saved!");
                $('#addTemplateModal').modal('hide');

                refreshTemplates();
            }
        }
    });
}

loadTemplates = function() {
    $.ajax({
        type: "get",
        url: "/diplomarbeit/airvals/template/load",
        success: function(data) {
            populateTemplatesTiles(data);
        }
    });
}

populateTemplatesTiles = function (data) {
    console.log(data);

    var templateContainer = $('#templateContainer');
    templateContainer.empty();

    for(var i = 0; i < data.templates.length; i ++) {

        if (i % 4 == 0) {
            var row = '<div class= "row" id="row_' + Math.floor(i / 4) + '"></div>';
            $(row).appendTo(templateContainer);
        }

        var column = '<div class="col-xs-3" id="col_' + Math.floor(i/4) + '_' + i + '"></div>';
        $(column).appendTo($('#row_' + Math.floor(i / 4)));

        var well = '<div class="well" id="well_' + Math.floor(i / 4) + '_' + i + '"></div>';
        $(well).appendTo($('#col_' + Math.floor(i / 4) + '_' + i));

        var wellObj = $('#well_' + Math.floor(i / 4) + '_' + i);

        wellObj.append('<p>Source: ' + data.templates[i].source.name + ' </p>');
        wellObj.append('<p>Destination: ' + data.templates[i].destination.name + ' </p>');
        wellObj.append('<p>Aircraft: ' + data.templates[i].plane.name + ' </p>');

        var operatingDays = 'Operating Days: ';
        for(var j = 0; j < data.templates[i].daysOfWeek.length; j ++) {
            if (data.templates[i].daysOfWeek.charAt(j) == '1') {
                operatingDays += weekDaysCapitalised[j];

                if (j < data.templates[i].daysOfWeek.length - 1) {
                    operatingDays += ', ';
                }
            }
        }
        wellObj.append('<p>' + operatingDays + '</p>');
        wellObj.append('<p>Departure time: ' + new Date(data.templates[i].departureTime) + '</p>');
        wellObj.append('<p>Arrival time: ' + new Date(data.templates[i].arrivalTime) + '</p>');

        var buttons = '<div class="btn-group">';
        buttons += '<button type="button" class="btn btn-default" onclick="removeTemplate(' + data.templates[i].id + ')">Remove</button>';
        buttons += '<button type="button" id="generateFlights_' + Math.floor(i / 4) + '_' + i + '" class="btn btn-default">Generate Flights</button>';
        buttons += '</div>';

        wellObj.append(buttons);

        $('#generateFlights_' + Math.floor(i / 4) + '_' + i).click(openGenerateFlightsModal(data.templates[i]));
    }

}

removeTemplate = function (templateId) {
    console.log("remove" + templateId);

}

openGenerateFlightsModal = function (template) {
    return function() {
        var generateFlightsModal = $('#generateFlightsModal');
        generateFlightsModal.modal('show');

        $('#generateFlightsModal #templateId').val(template.id);
        $('#generateFlightsModal #source').text(template.source.name);
        $('#generateFlightsModal #destination').text(template.destination.name);
        $('#generateFlightsModal #aircraft').text(template.plane.name);
        $('#generateFlightsModal #days').text('to do');
        $('#generateFlightsModal #departureTime').text('to do');
        $('#generateFlightsModal #arrivalTime').text('to do');

        $('#generateFlightsButton').unbind("click").click(generateFlights());
    }
}

generateFlights = function() {
    return function() {
        var generateFlightsModal = $('#generateFlightsModal');
        $.ajax({
            type: "post",
            url: "/diplomarbeit/airvals/flight/generate",
            data: {
                templateId : $('#generateFlightsModal #templateId').val(),
                start : new Date($('#generateFlightsModal #secretStartDate').val()).getTime(),
                end : new Date($('#generateFlightsModal #secretEndDate').val()).getTime()
            },
            success: function(data) {
                if (data.message == 'success') {
                    alert("Flights has been successfully saved!");
                    generateFlightsModal.modal('hide');
                }
            }
        });
    }
}