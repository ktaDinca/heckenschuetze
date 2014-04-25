<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ include file="/WEB-INF/taglibs.jsp" %>

<html>
<head>
    <title><tiles:insertAttribute name="title" ignore="true"/></title>

    <link rel="stylesheet" href="<spring:url value="/"/><tiles:insertAttribute name="styles" />"/>
    <link rel="stylesheet" href="<spring:url value="/resources/css/bootstrap.css" />"/>
    <link rel="stylesheet" href="<spring:url value="/resources/css/template.css" />"/>

    <script type="text/javascript" src="<spring:url value="/resources/javascript/jquery.js" />"></script>
    <script type="text/javascript" src="<spring:url value="/resources/javascript/bootstrap.js" />"></script>

    <tiles:insertAttribute name="scripts"/>
</head>

<body>
    <div id="wrapper">
        <div id="sidebar-wrapper">
            <tiles:insertAttribute name="sidebar"/>
        </div>
        <div id="page-content-wrapper">
            <tiles:insertAttribute name="content"/>
        </div>
    </div>
</body>
</html>