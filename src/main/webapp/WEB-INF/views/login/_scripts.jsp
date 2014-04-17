<%@ include file="/WEB-INF/taglibs.jsp" %>

<script type="text/javascript">

    function attemptLogin() {
        var username = $('#username').val();
        var password = $('#password').val();

        if (username == null || password == null || username.length < 1 || password.length < 1) {
            alert("muie!");
            return false;
        }

        $.ajax({
            type: "POST",
            url: '<spring:url value="/login" />',
            data: {username : username,
                    password : password}
            ,
            success: function (response) {
                console.log("success");
                console.log(response);
            },
            error: function () {
                console.log("error");
            }
        });
    }
</script>