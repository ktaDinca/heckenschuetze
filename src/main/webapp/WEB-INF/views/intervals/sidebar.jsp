<%@ include file="/WEB-INF/taglibs.jsp" %>

<link rel="stylesheet" href="<spring:url value="/resources/css/sidebar.css" />"/>

<script type="text/javascript" src="<spring:url value="/resources/javascript/jquery.js" />"></script>
<script type="text/javascript" src="<spring:url value="/resources/javascript/bootstrap.js" />"></script>


<div class="container-fluid" id="personalInfoHolder">
    <div class="row" id="imageRow">
        <div class="col-sm-12">
            <img id=profile-img" class="profile_img"
                 src="<spring:url value="/resources/images/photo-cd.png" />" >
        </div>
    </div>

    <div class="row" id="profileNameRow">
        <div class="col-sm-12">
            <p>${loggedInUser.firstname} ${loggedInUser.lastname}</p>
        </div>
    </div>
</div>

<ul>
    <li class="top-bordered">
        <a href="<spring:url value="/intervals/today" />">today's</a>
    </li>
    <c:if test="${loggedInUser.job eq 'ADMIN'}">
        <li class="top-bordered item-active">
            <a href="<spring:url value="/intervals/admin/panel" />">Admin Panel</a>
        </li>
    </c:if>
    <li class="top-bordered">
        <a href="<spring:url value="/intervals/weekly" />">weekly timesheets</a>
    </li>

    <c:if test="${loggedInUser.job eq 'DEPARTMENT_MANAGER' or loggedInUser.job eq 'DIVISION_MANAGER'}">
        <li class="top-bordered">
            <a href="<spring:url value="/intervals/weekly/review" />">review weekly</a>
        </li>
    </c:if>

    <li class="top-bordered">
        <a href="<spring:url value="/intervals/logout" />">logout</a>
    </li>
</ul>