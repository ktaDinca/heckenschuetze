<%@ include file="/WEB-INF/taglibs.jsp" %>

<link rel="stylesheet" href="<spring:url value="/resources/css/header.css" />" />

<div clas="navbar navbar-default navbar-fixed-top">
    <%--<div class="container">--%>
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="icon-bar"></span>
                <span class="icon-next"></span>
                <span class="glyphicon-download"></span>
            </button>
            <a href="javascript:void(0);" class="">
                <img src="<spring:url value="/resources/images/logo.png" />" >
            </a>
        </div>
        <div class="navbar-collapse collapse">
            <button type="button" id="logout-btn" class="btn">Logout</button>
            <ul class="nav navbar-nav" id="links">
                <li>
                    <a href="<spring:url value="/timesheet/homepage" />" >Today's timesheet</a>
                    <div class="blue-underneath">&nbsp;</div>
                </li>
                <li>
                    <a href="<spring:url value="/todays-timesheet" />" >Daily timesheet</a>
                </li>
                <li>
                    <a href="<spring:url value="/todays-timesheet" />" >Weekly timesheet</a>
                </li>
                <li>
                    <a href="<spring:url value="/todays-timesheet" />" >Monthly timesheet</a>
                </li>
            </ul>
        </div>
    <%--</div>--%>
</div>
<%--<div id="color-container">&nbsp;</div>--%>
