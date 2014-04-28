
<%@ include file="/WEB-INF/taglibs.jsp" %>

<link href="<spring:url value="/resources/css/fullcalendar.css" />" rel="stylesheet" />
<link href="<spring:url value="/resources/css/fullcalendar.print.css" />" rel="stylesheet" media="print" />

<script src="<spring:url value="/resources/javascript/jquery-ui.custom.min.js" />"></script>
<script src="<spring:url value="/resources/javascript/fullcalendar.js" />"></script>

<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/flick/jquery-ui.css" type="text/css" />


<h1>Flick, Redmond</h1>

<script>

    //6 levels of brightness from 0 to 5, 0 being the darkest
    function getRandColor(brightness){
        var rgb = [Math.random() * 256, Math.random() * 256, Math.random() * 256];
        var mix = [brightness*51, brightness*51, brightness*51];
        var mixedrgb = [Math.floor((rgb[0] + mix[0]) / 2.0), Math.floor((rgb[1] + mix[1]) / 2.0), Math.floor((rgb[2] + mix[2]) / 2.0)];
        return "rgb(" + mixedrgb.join(",") + ")";
    }

    function generateRandomColor() {
        var colors = [];
        colors.push("rgb(26, 188, 156)");
        colors.push("rgb(46, 204, 113)");
        colors.push("rgb(52, 152, 219)");
        colors.push("rgb(155, 89, 182)");
        colors.push("rgb(52, 73, 94)");
        colors.push("rgb(41, 128, 185)");
        colors.push("rgb(39, 174, 96)");
        colors.push("rgb(241, 196, 15)");
        colors.push("rgb(243, 156, 18)");
        colors.push("rgb(211, 84, 0)");
        colors.push("rgb(231, 76, 60)");
        colors.push("rgb(192, 57, 43)");
        colors.push("rgb(44, 62, 80)");
        colors.push("rgb(142, 68, 173)");

        return colors[Math.floor(Math.random() * colors.length) + 1];
    }

    function clearAddActivityModal() {

        console.log("clearing!");

        $('#addActivityModal #description').val('');
        $('#addActivityModal #id').val('');
        $('#addActivityModal p').text('What did you do?');

        $('#removeActivity').attr("disabled", true);
    }

    $(document).ready(function() {
        var calendar = $('#calendar');

        var date = new Date();
        var d = date.getDate();
        var m = date.getMonth();
        var y = date.getFullYear();

        calendar.fullCalendar({
            theme: true,
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek'
            },
            selectable: true,
            selectHelper: true,
            allDaySlot: false,
            weekends: false,
            minTime: 8,
            maxTime: 24,
            editable: true,
            eventColor: '#378006',
            // this is called when you add another activity.
            select: function(start, end, allDay) {
                $('#addActivityModal').modal('show');

                $('#saveActivity').unbind('click');
                $('#saveActivity').click(function() {

                    console.log("save din select");

                    var description = $('#addActivityModal #description').val();
                    var id= $('#addActivityModal #id').val();
                    var projectId = $('#addActivityModal #projects option:selected').val();

                    // an event should be saved only if it has a description
                    if (description) {
                        persistActivityChange(description, start.getTime(), end.getTime(), id, projectId, function(data) {
                            if (data.message == 'success') {
                                clearAddActivityModal();
                                $('#addActivityModal').modal('hide');
                                $('#calendar').fullCalendar('refetchEvents');
                            }
                        });
                    }
                    else {
                        alert("Nu ai completat descrierea!");
                    }
                });
                calendar.fullCalendar('unselect');
            },
            eventClick: function(calEvent, jsEvent, view) {
                $('#addActivityModal .modal-header p').text('*Edit ' + calEvent.title);

                $('#addActivityModal #description').val(calEvent.title);
                $('#addActivityModal #id').val(calEvent.id);
                $('#addActivityModal #projects').val(calEvent.project.id);

                $('#addActivityModal').modal('show');

                $('#saveActivity').unbind('click');
                $('#saveActivity').click(function() {

                    console.log("save din eventClick!");

                    var description = $('#addActivityModal #description').val();
                    var id= $('#addActivityModal #id').val();
                    var projectId = $('#addActivityModal #projects option:selected').val();

                    // an event should be saved only if it has a description
                    if (description) {
                        persistActivityChange(description, calEvent.start.getTime(), calEvent.end.getTime(), calEvent.id, projectId, function(data) {
                            if (data.message == 'success') {
                                clearAddActivityModal();
                                $('#addActivityModal').modal('hide');
                                $('#calendar').fullCalendar('refetchEvents');
                            }
                        });
                    }
                    else {
                        alert("Nu ai completat descrierea!");
                    }
                });
                $('#removeActivity').removeAttr("disabled");
                $('#removeActivity').click(function() {
                    var id= $('#addActivityModal #id').val();

                    console.log("clicked remove");
                    removeActivityHandler(id);

                });
//                $(this).css('border-color', 'red');
                clearAddActivityModal();
            },
            eventResize: function( event, dayDelta, minuteDelta, revertFunc, jsEvent, ui, view ) {
                // send to the server the resized version of the event=activity
                persistActivityChange(event.title, event.start.getTime(), event.end.getTime(), event.id, event.project.id, function(data) {
                    $('#calendar').fullCalendar('refetchEvents');
                });
            },
            eventDrop: function(event, jsEvent, ui, view ) {
                console.log("translated!");
                persistActivityChange(event.title, event.start.getTime(), event.end.getTime(), event.id, event.project.id, function(data) {
                    $('#calendar').fullCalendar('refetchEvents');
                });
            },
            events : function(start, end, callback) {
                $.ajax({
                    type: "get",
                    url: "<spring:url value="/intervals/employee/activities" />",
                    success: function(data) {
                        var events = [];

                        for (var i = 0; i < data.events.length; i ++) {
                            var randomColor = generateRandomColor();
                            console.log(randomColor);

                            events.push({
                                id: data.events[i].id,
                                title: data.events[i].description,
                                start: new Date(data.events[i].start),
                                end: new Date(data.events[i].end),
                                project: data.events[i].project,
                                allDay: false,
                                color: randomColor
                            });
                        }
                        callback(events);
                    }
                });
            }
        });

        function persistActivityChange(description, start, end, id, projectId, successCallback) {

            $.ajax({
                type: "post",
                url: "<spring:url value="/intervals/employee/activities/edit" />",
                data: {
                    description: description,
                    start: start,
                    end: end,
                    id: id,
                    projectId: projectId
                },
                success: successCallback
            });
        }
    });


    function removeActivityHandler(id) {

        console.log("remove - activity");

        if (id == null || id == 'undefined' || id.length < 1) {
            alert("ID'ul este null");
            return false;
        }

        $.ajax({
            type: "post",
            url: "<spring:url value="/intervals/employee/activities/remove" />",
            data: {
                id: id
            },
            success: function(data) {
                if (data.message == 'success') {
                    $('#calendar').fullCalendar('refetchEvents');
                }
                $('#addActivityModal').modal('hide');
            }
        });
    }

</script>

<h1>How are you Today?</h1>

<div id="calendar"></div>

<div id="addActivityModal" class="modal fade in" role="dialog" aria-hidden="true" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <p>What did you do?</p>
            </div>
            <div class="modal-body">

                <label for="description">Few words about it:</label>
                <input type="text" id="description">

                <label for="projects">Projects</label>
                <select id="projects">
                    <c:forEach var="project" items="${projects}">
                        <option value="${project.id}">${project.name}</option>
                    </c:forEach>
                </select>
                <input type="hidden" id="duration">
                <input type="hidden" id="id">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" id="removeActivity" disabled >Remove</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-success" id="saveActivity">Save</button>
            </div>
        </div>
    </div>

</div>