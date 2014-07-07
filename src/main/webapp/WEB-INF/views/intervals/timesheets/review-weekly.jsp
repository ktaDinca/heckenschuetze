
<%@ include file="/WEB-INF/taglibs.jsp" %>

<link href="<spring:url value="/resources/css/fullcalendar.css" />" rel="stylesheet" />
<link href="<spring:url value="/resources/css/fullcalendar.print.css" />" rel="stylesheet" media="print" />

<script src="<spring:url value="/resources/javascript/jquery-ui.custom.min.js" />"></script>
<script src="<spring:url value="/resources/javascript/fullcalendar.js" />"></script>

<link rel="stylesheet" href="http://cdn.oesmith.co.uk/morris-0.4.3.min.css">

<script src="//cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js"></script>
<script src="http://cdn.oesmith.co.uk/morris-0.4.3.min.js"></script>

<script src="<spring:url value="/resources/javascript/jquery-dateFormat.min.js" />"></script>

<script src="http://cdnjs.cloudflare.com/ajax/libs/moment.js/2.5.1/moment.min.js"></script>

<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/flick/jquery-ui.css" type="text/css" />


<div class="container-fluid">
    <div class="row">
        <h2 id="pageHeader">Review Weekly</h2>
    </div>
    <div class="row">
        <div class="col-md-4">
            <div class="panel panel-primary" id="notificationsPanel">
                <div class="panel-heading">
                    <span class="glyphicon-ok glyphicon-envelope"></span>Notifications <span class="badge"></span>
                </div>
                <div class="panel-body">
                    <div class="list-group">
                    </div>
                </div>
            </div>
        </div>

        <div class="col-md-8">
            <div id="review-calendar"></div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-6" id="generalHoursPerProjectDonutContainer">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    Hours spent per project
                </div>
                <div class="panel-body">
                    <a class="btn btn-default" target="_blank" onclick="exportGeneralHoursAsXLS()">Export as XLS</a>
                    <button class="btn btn-default" onclick="exportGeneralHoursAsPDF()">Export as PDF</button>
                    <div id="all-project-work-donut"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade in" id="exportSuccessfulModal" tabindex="-1" aria-hidden="true" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
                Your file has been successfully processed!
            </div>
            <div class="modal-footer">
                <a download id="generatedFilePath">Click here to download it</a>
            </div>
        </div>
    </div>

</div>