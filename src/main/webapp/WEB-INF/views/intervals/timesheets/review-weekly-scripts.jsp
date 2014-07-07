<%@ include file="/WEB-INF/taglibs.jsp" %>

<script>
    $(document).ready(function() {
        checkNotifications();
//        checkNotificationsEveryXseconds(10);
        loadDataForTotalWorkPerProjectDonut();
    });

    function checkNotificationsEveryXseconds(seconds) {
        setInterval(function() {
            checkNotifications();
        }, parseInt(seconds) * 1000);
    }

    function checkNotifications() {
        console.log("checking notifications");
        $.ajax({
            type: "post",
            url: "<spring:url value="/intervals/notifications/check" />",
            success: function(data) {
                console.log(data);
                if (data.message == 'success') {
                    $('#notificationsPanel .badge').text(data.notifications.length);

                    var panelList = $('#notificationsPanel .list-group');
                    panelList.empty();

                    for (var i = 0; i < data.notifications.length; i ++) {
                        var notif = '<a href="#" id="notif_' + data.notifications[i].id + '" class="list-group-item">' +
                                data.notifications[i].sheet.owner.firstname + ' ' +
                                data.notifications[i].sheet.owner.lastname +
                                '<span class="pull-right text-muted small">' + data.notifications[i].issue_time + '' +
                                '</a>';
                        var approve = '<button type="button" id="approve_' + data.notifications[i].sheet.id + '" class="btn btn-success">' +
                                '<span class="glyphicon glyphicon-ok"></span> Approve' + '</button>';

                        var reject = '<button type="button" id="reject_' + data.notifications[i].sheet.id + '" class="btn btn-danger">' +
                                '<span class="glyphicon glyphicon-ok"></span> Reject' + '</button>';

                        panelList.append(notif);
                        panelList.append(approve);
                        panelList.append(reject);

                        $('#notif_' + data.notifications[i].id).on('click', populateCalendar(data.notifications[i]));
                        $('#approve_' + data.notifications[i].sheet.id).on('click', approveWeekly(data.notifications[i].sheet, 'approve', data.notifications[i]));
                        $('#reject_' + data.notifications[i].sheet.id).on('click', approveWeekly(data.notifications[i].sheet, 'reject', data.notifications[i]));
                    }
                }
            }
        });
    }

    function approveWeekly(weekly, action, notification) {
        return function() {
            console.log(weekly);
            console.log(action);

            $.ajax({
                type: "post",
                url: "<spring:url value="/intervals/weekly/approve" />",
                data: {
                    weeklyId: weekly.id,
                    action: action,
                    notificationId: notification.id
                },
                success: function(data) {
                    if (data.message == 'success') {
                        console.log(data);
                    }
                }
            });
            checkNotifications();
        }
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

    function populateCalendar(notification) {
        return function() {

            console.log("notificarea primita");
            console.log(notification);

            var reviewCalendar = $('#review-calendar');
            console.log(reviewCalendar);
            reviewCalendar.empty();

            reviewCalendar.fullCalendar({
                theme: true,
                header: {
                    left: '',
                    center: 'title',
                    right: ''
                },
                defaultView: 'agendaWeek',
                weekends: false,
                minTime: 8,
                events: function(start, end, callback) {
                    $.ajax({
                        type: "get",
                        url: "<spring:url value="/intervals/employee/activities/week" />",
                        data: {
                            _weeklyId: notification.sheet.id
                        },
                        success: function(data) {
                            var events = [];
                            if (data.message == 'success') {
                                for (var i = 0; i < data.events.length; i ++) {
                                    events.push({
                                        id: data.events[i].id,
                                        title: data.events[i].description,
                                        start: new Date(data.events[i].start),
                                        end: new Date(data.events[i].end),
                                        project: data.events[i].project,
                                        allDay: false,
                                        color: generateRandomColor()
                                    });
                                }
                                callback(events);
                            }
                        }
                    });
                }
            });
            reviewCalendar.fullCalendar('gotoDate', new Date(notification.sheet.startingDay));
        }
    }

    function loadDataForTotalWorkPerProjectDonut() {
        $.ajax({
            method: "GET",
            url : "<spring:url value="/intervals/charts/general/project-hours-donut" />",
            success: function(data) {
                console.log(data);

                var donutData = [];
                $.each(data.generalWorkPerProject, function(key, value) {
                    donutData.push({
                        label: key,
                        value: value
                    });
                });
                if (donutData.length > 0) {
                    drawDonut('all-project-work-donut', donutData);
                }
                else {
                    alert("Nothing to be shown");
                }

            }
        });
    }

    function drawDonut(elementId, content) {
        $('#' + elementId).empty();
        Morris.Donut({
            element: elementId,
            data: content,
            resize: true
        });
    }

    function exportGeneralHoursAsXLS() {
        $.ajax({
            method: "GET",
            url : "<spring:url value="/intervals/charts/general/export/xls/work-department" />",
            success : function(data) {
                $('#generatedFilePath').prop('href', "/diplomarbeit/intervals/reports/xls/" + data.filepath);
                console.log(data.filepath);
                $('#exportSuccessfulModal').modal('show');
            }
        });
    }

    function exportGeneralHoursAsPDF() {
        $.ajax({
            method: "GET",
            url : "<spring:url value="/intervals/charts/general/export/pdf/work-department" />",
            success : function(data) {
                $('#generatedFilePath').prop('href', "/diplomarbeit/intervals/reports/pdf/" + data.filepath);
                console.log(data.filepath);
                $('#exportSuccessfulModal').modal('show');
            }
        });
    }

</script>