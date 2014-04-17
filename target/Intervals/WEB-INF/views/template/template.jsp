<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ include file="/WEB-INF/taglibs.jsp" %>

<html>
<head>
    <title><tiles:insertAttribute name="title" ignore="true"/></title>

    <link rel="stylesheet" href="<tiles:insertAttribute name="styles" />" />
    <link rel="stylesheet" href="<spring:url value="/resources/css/bootstrap.css" />" />
    <link rel="stylesheet" href="<spring:url value="/resources/css/template.css" />" />

    <script type="text/javascript" src="<spring:url value="/resources/javascript/jquery.js" />" ></script>
    <script type="text/javascript" src="<spring:url value="/resources/javascript/bootstrap.js" />"></script>

    <tiles:insertAttribute name="scripts" />
</head>

<body style="width:100%;height:100%">
<table cellspacing="0" cellpadding="0" style="width:100%;height:100%; margin-right: auto; margin-left: auto">
    <tr>
        <td colspan="2" id="header">
            <tiles:insertAttribute name="header" /></td>
    </tr>
    <tr>
        <%--<td>--%>
            <%--<tiles:insertAttribute name="menu" /></td>--%>
        <td id="content">
            <tiles:insertAttribute name="content" /></td>
    </tr>
    <tr>
        <td colspan="2" id="footer">
            <tiles:insertAttribute name="footer" /></td>
    </tr>
</table>
</body>
</html>
</body>
</html>