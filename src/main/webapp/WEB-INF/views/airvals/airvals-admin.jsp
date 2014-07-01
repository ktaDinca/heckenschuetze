<%@ include file="/WEB-INF/taglibs.jsp" %>


<style>
    html {overflow-y: scroll;}
</style>

<%--DATEPICKER--%>
<script src="<spring:url value="/resources/javascript/bootstrap-datepicker.js" />"></script>
<link rel="stylesheet" href="<spring:url value="/resources/css/datepicker3.css"/>" />

<%--TIMEPICKER--%>
<link rel="stylesheet" href="<spring:url value="/resources/css/bootstrap-timepicker.css" />" />
<script src="<spring:url value="/resources/javascript/vendor/bootstrap-timepicker.min.js" />"></script>

<script src="<spring:url value="/resources/javascript/airvals/admin.js" />"></script>

<link rel="stylesheet" href="<spring:url value="/resources/css/airvals/admin.css" />" />

<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="active">
                    <a href="#tab-flights"
                       data-toggle="tab">Flights</a>
                </li>
                <li>
                    <a href="#tab-statistics"
                       data-toggle="tab">Statistics</a>
                </li>
                <li>
                    <a href="#tab-templates"
                       data-toggle="tab">Flight Templates</a>
                </li>

                <%--<li class="dropdown">--%>
                    <%--<a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <b class="caret"></b></a>--%>
                    <%--<ul class="dropdown-menu">--%>
                        <%--<li><a href="#">Action</a></li>--%>
                        <%--<li><a href="#">Another action</a></li>--%>
                        <%--<li><a href="#">Something else here</a></li>--%>
                        <%--<li class="divider"></li>--%>
                        <%--<li><a href="#">Separated link</a></li>--%>
                        <%--<li class="divider"></li>--%>
                        <%--<li><a href="#">One more separated link</a></li>--%>
                    <%--</ul>--%>
                <%--</li>--%>

            </ul>
        </div>

    </div>
</nav>

<div class="tab-content">
    <div class="tab-pane active" id="tab-flights">
        <jsp:include page="/WEB-INF/views/airvals/tab-flights.jsp"/>
    </div>

    <div class="tab-pane" id="tab-statistics">
        <jsp:include page="/WEB-INF/views/airvals/tab-statistics.jsp"/>
    </div>

    <div class="tab-pane" id="tab-templates">
        <jsp:include page="/WEB-INF/views/airvals/tab-templates.jsp"/>
    </div>
</div>
