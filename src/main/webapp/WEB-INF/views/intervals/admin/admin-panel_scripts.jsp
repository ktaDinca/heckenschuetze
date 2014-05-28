<%@ include file="/WEB-INF/taglibs.jsp" %>

<script>


    /*
        When the admin-page is loaded, the employees tab is active by default.
        So, it should get all the employees from the server.

        Afterwards, an event is triggered upon tab click and consequently
        appropriate data should be retrieved from the server.
     */
    $(document).ready(function() {
        refreshEmployeeTable();
    });


    /*
        Bind a onTabShown event to each tab.
     */
    $(document).on('shown.bs.tab', 'a[data-toggle="tab"]', function (e) {
        console.log(e.target);

        var targetA = e.target.toString();

        // if departments tab has been pressed
        if (targetA.indexOf('departments') >= 0) {
            refreshDepartmentsTable();
        } else if (targetA.indexOf('projects') >= 0) {
            refreshProjectsTable();
        } else if (targetA.indexOf('divisions') >= 0) {
            refreshDivisionsTable();
        }  else if (targetA.indexOf('clients') >= 0) {
            refreshClientsTable();
        }
    });

</script>