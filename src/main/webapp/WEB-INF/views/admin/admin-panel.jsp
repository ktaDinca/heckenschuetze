
<%@ include file="/WEB-INF/taglibs.jsp" %>

<script type="text/javascript">
    var departmentsMap = new Object();
</script>

<h1>Admin page</h1>

<c:set var="jobs" value="${allJobs}" scope="request" />
<c:set var="departments" value="${allDeps}" scope="request" />

<ul class="nav nav-tabs nav-justified">
    <li class="active"><a href="#tab-employees" data-toggle="tab" >employees</a></li>
    <li><a href="#tab-departments" data-toggle="tab" >departments</a></li>
    <li><a href="#tab-divisions" data-toggle="tab" >divisions</a></li>
    <li><a href="#tab-projects" data-toggle="tab" >projects</a></li>
    <li><a href="#tab-clients" data-toggle="tab" >clients</a></li>
</ul>

<div class="tab-content">
    <div class="tab-pane active" id="tab-employees">
        <jsp:include page="/WEB-INF/views/admin/tab-emploees-content.jsp" />
    </div>
    <div class="tab-pane" id="tab-departments">
        <jsp:include page="/WEB-INF/views/admin/tab-departments-content.jsp" />
    </div>
    <div class="tab-pane" id="tab-divisions">
        <jsp:include page="/WEB-INF/views/admin/tab-divisions-content.jsp" />
    </div>
    <div class="tab-pane" id="tab-projects">
        <jsp:include page="/WEB-INF/views/admin/tab-projects-content.jsp" />
    </div>
    <div class="tab-pane" id="tab-clients">
        <jsp:include page="/WEB-INF/views/admin/tab-clients-content.jsp" />
    </div>
</div>

