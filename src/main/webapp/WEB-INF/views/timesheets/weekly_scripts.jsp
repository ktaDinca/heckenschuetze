<%@ include file="/WEB-INF/taglibs.jsp" %>

<script>

    function submitLastTimesheetHandler(){
        $.ajax({
            type: "post",
            url: "<spring:url value="/intervals/weekly/submitLast" />",
            success: function(data) {
                if(data.message == 'success') {
                    console.log("done");
                }
            }
        });
    }

    $(document).ready(function() {

    });

</script>