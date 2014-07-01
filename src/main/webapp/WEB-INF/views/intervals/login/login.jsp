<%@ include file="/WEB-INF/taglibs.jsp" %>

<div id="carousel" class="carousel slide">
    <div class="carousel-inner">
        <div class="item active">
            <img src="resources/images/offices.jpg">
            <div class="container">
                <div class="carousel-caption">
                    <h1>Intervals</h1>
                    <p>
                        <a data-toggle="modal" class="btn btn-success" href="#loginModal">login</a>
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>

<%--&lt;%&ndash;<div class="container-fluid" class="emptyDiv">&nbsp;</div>&ndash;%&gt;--%>
<%--<div class="container-fluid" id="pageContainer">--%>
    <%--<h1>Intervals</h1>--%>
    <%--<p>--%>
        <%--<a data-toggle="modal" class="btn btn-success" href="#loginModal">login</a>--%>
    <%--</p>--%>
<%--</div>--%>
<%--&lt;%&ndash;<div class="container-fluid" class="emptyDiv">&nbsp;</div>&ndash;%&gt;--%>


<div class="modal fade in" id="loginModal" tab-index="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" id="login-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <p>You are one click away from ending a great working day</p>
            </div>
                <form id="login-form" method="post" action="<spring:url value="/intervals/login" />">
                    <div class="modal-body">
                    <input type="text" name="username" id="username" class="form-control input-lg"
                           placeholder="username">
                    <input type="password" name="password" id="password" class="form-control input-lg"
                           placeholder="password">
                    </div>
                    <%--<div class="modal-footer">--%>
                        <input type="submit" value="login"/>
                    <%--</div>--%>
                </form>
            </div>
        </div>
    </div>
</div>

<%--<div class="container">--%>

    <%--<div class="row" style="margin-top:20px">--%>
        <%--<div class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3">--%>
            <%--<form role="form">--%>
                <%--<fieldset>--%>
                    <%--<h2>Please Sign In</h2>--%>
                    <%--<hr class="colorgraph">--%>
                    <%--<div class="form-group">--%>
                        <%--<input type="email" name="email" id="email" class="form-control input-lg"--%>
                               <%--placeholder="Email Address">--%>
                    <%--</div>--%>
                    <%--<div class="form-group">--%>
                        <%--<input type="password" name="password" id="password" class="form-control input-lg"--%>
                               <%--placeholder="Password">--%>
                    <%--</div>--%>
<%--<span class="button-checkbox">--%>
<%--<button type="button" class="btn" data-color="info">Remember Me</button>--%>
<%--<input type="checkbox" name="remember_me" id="remember_me" checked="checked" class="hidden">--%>
<%--<a href="" class="btn btn-link pull-right">Forgot Password?</a>--%>
<%--</span>--%>
                    <%--<hr class="colorgraph">--%>
                    <%--<div class="row">--%>
                        <%--<div class="col-xs-6 col-sm-6 col-md-6">--%>
                            <%--<input type="submit" class="btn btn-lg btn-success btn-block" value="Sign In">--%>
                        <%--</div>--%>
                        <%--<div class="col-xs-6 col-sm-6 col-md-6">--%>
                            <%--<a href="" class="btn btn-lg btn-primary btn-block">Register</a>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</fieldset>--%>
            <%--</form>--%>
        <%--</div>--%>
    <%--</div>--%>

<%--</div>--%>
