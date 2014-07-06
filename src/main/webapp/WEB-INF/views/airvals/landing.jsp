
<%@ include file="/WEB-INF/taglibs.jsp" %>

<%--FLAT-UI CSS--%>
<%--<link rel="stylesheet" href="resources/css/flat-ui/flat-ui.css" />--%>
<%--<link rel="stylesheet" href="resources/css/flat-ui/demo.css" />--%>

<%--<script src="resources/javascript/vendor/jquery.autocomplete.min.js"></script>--%>
<%--<link rel="stylesheet" href="resources/css/autocomplete.css" />--%>

<script src="<spring:url value="/resources/javascript/vendor/typeahead.js" />"></script>

<%--MAPS--%>
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp"></script>

<%--ANGULAR--%>
<script src="<spring:url value="/resources/javascript/angular.js"/>"></script>
<script src="<spring:url value="/resources/javascript/angular-route.js" />"></script>

<%--DATEPICKER--%>
<script src="<spring:url value="/resources/javascript/bootstrap-datepicker.js" />"></script>
<link rel="stylesheet" href="<spring:url value="/resources/css/datepicker3.css"/>" />

<%--BOOTBOX--%>
<script src="<spring:url value="/resources/javascript/vendor/bootbox.min.js"/>"></script>

<%--TRANSLATE--%>
<script src="<spring:url value="/resources/javascript/vendor/angular-translate.js"/>"></script>

<%--NG-MAPS--%>
<script src="<spring:url value="/resources/javascript/vendor/ng-map.js"/>"></script>

<%--CONTROLLERS--%>
<script src="<spring:url value="/resources/javascript/spa/mainController.js" />" ></script>
<script src="<spring:url value="/resources/javascript/spa/FlightController.js" />"></script>
<script src="<spring:url value="/resources/javascript/spa/SignInController.js"/>"></script>
<script src="<spring:url value="/resources/javascript/spa/FlightServices.js"/>"></script>
<script src="<spring:url value="/resources/javascript/spa/CityService.js"/>"></script>
<script src="<spring:url value="/resources/javascript/spa/RegistrationController.js"/>"></script>
<script src="<spring:url value="/resources/javascript/spa/UserService.js"/>"></script>
<script src="<spring:url value="/resources/javascript/spa/LoginController.js"/>"></script>

<link rel="stylesheet" href="<spring:url value="/resources/css/airvals/flights.css"/>"/>
<link rel="stylesheet" href="<spring:url value="/resources/css/on-off-switch.css"/>"/>

<%--TYPEAHEAD--%>
<link rel="stylesheet" href="<spring:url value="/resources/css/typeahead.css"/>"/>

<%--ZOE HOVER--%>
<link rel="stylesheet" href="<spring:url value="/resources/css/zoe-hover.css"/>"/>

<%--ANIMATIONS--%>
<link rel="stylesheet" href="<spring:url value="/resources/css/animate.css"/>"/>

<div ng-app="airvals">
    <div ng-controller="mainController">
        <div ng-view></div>
    </div>
</div>

