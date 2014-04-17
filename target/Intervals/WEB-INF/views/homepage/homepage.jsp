<%@ include file="/WEB-INF/taglibs.jsp" %>

<link href='<spring:url value="/resources/css/fullcalendar.css" />' rel='stylesheet' />
<link href='<spring:url value="/resources/css/fullcalendar.print.css" />' rel='stylesheet' media='print' />

<%--<script src='<spring:url value="/resources/javascript/jquery-ui.custom.min.js" />'></script>--%>
<%--<script src='<spring:url value="/resources/javascript/fullcalendar.js" />'></script>--%>

<script src="<spring:url value="/resources/javascript/Chart.js" />"></script>

<%--<div style="width: 50%; height: 50%; margin-left: auto; margin-right: auto" id="calendar"></div>--%>

<div id="container">
    <h2>Let's see what you have done so far, today</h2>


    <canvas id="todaysChart" width="300" height="300"></canvas>
</div>