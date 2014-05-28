<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<html>
<head>
    <title><tiles:insertAttribute name="title" ignore="true"/></title>

    <link rel="stylesheet" href="<tiles:insertAttribute name="styles" />" />
    <link rel="stylesheet" href="resources/css/bootstrap.css" />

</head>
<body>
    <script type="text/javascript" src="resources/javascript/jquery.js" ></script>
    <script type="text/javascript" src="resources/javascript/bootstrap.js"></script>

    <tiles:insertAttribute name="scripts" />
    <tiles:insertAttribute name="content"/>
</body>
</html>