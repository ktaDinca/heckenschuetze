
<%@ include file="/WEB-INF/taglibs.jsp" %>

<link rel="stylesheet" href="http://cdn.oesmith.co.uk/morris-0.4.3.min.css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js"></script>
<script src="http://cdn.oesmith.co.uk/morris-0.4.3.min.js"></script>

<script src="<spring:url value="/resources/javascript/jquery-dateFormat.min.js" />"></script>

<script src="http://cdnjs.cloudflare.com/ajax/libs/moment.js/2.5.1/moment.min.js"></script>

<%--<script src="//cdnjs.cloudflare.com/ajax/libs/jquery.nicescroll/3.5.1/jquery.nicescroll.min.js"></script>--%>

<h2>Weekly timesheets</h2>

<br />

<div class="container">
    <div class="row">
        <div class="col-md-4">
            <h2>By Week</h2>
            <hr/>
            <div class="date-picker">
                <div class="date-container pull-left">
                    <h4 class="weekday">Monday</h4>

                    <h2 class="date">Februray 4th</h2>
                    <h4 class="year pull-right">2014</h4>
                </div>
                <span data-toggle="datepicker" data-type="subtract" data-amt="7"
                      class="fa fa-angle-left pull-left"></span>
                <span data-toggle="datepicker" data-amt="7" class="fa fa-angle-right pull-right"></span>
            </div>
        </div>
    </div>

    <div id="weekly-status" class="row">
        <div class="col-md-8">
            <button style="display: none" class="btn btn-info" id="submitThisTimesheet">Submit this timesheet</button>

            <div style="display: none" class="alert alert-info alert-dismissable">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                Your timesheet is not submitted!
            </div>

            <div style="display: none" class="alert alert-warning alert-dismissable">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                Your timesheet is waiting for approval!
            </div>

            <div style="display: none" class="alert alert-danger alert-dismissable">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                Your timesheet has been rejected!
            </div>

            <div style="display: none" class="alert alert-success alert-dismissable">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                Your timesheet is approved!
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-8">
            <h2>Move Me</h2>
            <div class="panel panel-primary">
                <div class="panel-heading">
                    Arrival chart
                </div>
                <div class="panel-body">
                    <div id="weekly-working-hours-line"></div>
                </div>
            </div>
        </div>

        <div class="col-md-4">
            <h2>Move me to the right!</h2>

            <div class="panel panel-primary">
                <div class="panel-heading">
                    Work chart
                </div>
                <div class="panel-body">
                    <div id="weekly-projects-donut"></div>
                </div>
            </div>
        </div>
    </div>
</div>