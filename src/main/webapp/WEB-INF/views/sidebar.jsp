<%@ include file="/WEB-INF/taglibs.jsp" %>

<link rel="stylesheet" href="resources/css/sidebar.css" />

<script type="text/javascript" src="<spring:url value="/resources/javascript/jquery.js" />" ></script>
<script type="text/javascript" src="<spring:url value="/resources/javascript/bootstrap.js" />" ></script>

<div class="logo-container">
    <a href="<spring:url value="/homepage" />">
        <img src="<spring:url value="/resources/images/logo.png" />" >
    </a>
</div>

<ul class="">
    <li class="top-bordered">
        <%--<span id="today-span"></span>--%>
        <a href="/timesheet/today">today's</a>
    </li>
    <li class="top-bordered">
        <a href="#">tomorrow's</a>
    </li>
    <li class="top-bordered bottom-bordered">
        <a href="/timesheet/today">toyota's</a>
    </li>
</ul>