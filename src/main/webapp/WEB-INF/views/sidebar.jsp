<%@ include file="/WEB-INF/taglibs.jsp" %>

<link rel="stylesheet" href="<spring:url value="/resources/css/sidebar.css" />" />

<script type="text/javascript" src="<spring:url value="/resources/javascript/jquery.js" />" ></script>
<script type="text/javascript" src="<spring:url value="/resources/javascript/bootstrap.js" />" ></script>

<div class="logo-container">
    <a href="<spring:url value="/homepage" />">
        <img src="<spring:url value="/resources/images/logo.png" />" >
    </a>
</div>

<ul class="">
    <li class="top-bordered">
        <a href="<spring:url value="/intervals/today" />">today's</a>
    </li>

    <c:if test="${loggedInUser.job eq 'ADMIN'}">
        <li class="top-bordered">
            <a href="<spring:url value="/intervals/admin/panel" />">Admin Panel</a>
        </li>
    </c:if>
</ul>