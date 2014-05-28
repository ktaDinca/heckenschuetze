
<%@ include file="/WEB-INF/taglibs.jsp" %>

<link href="<spring:url value="/resources/css/fullcalendar.css" />" rel="stylesheet" />
<link href="<spring:url value="/resources/css/fullcalendar.print.css" />" rel="stylesheet" media="print" />

<script src="<spring:url value="/resources/javascript/jquery-ui.custom.min.js" />"></script>
<script src="<spring:url value="/resources/javascript/fullcalendar.js" />"></script>

<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/flick/jquery-ui.css" type="text/css" />


<div class="container">
    <h2>Review Weekly</h2>
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
</div>