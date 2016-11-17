<%
	//Check if already sign in, redirect to messageBoard.jsp
	if(session.getAttribute("userName") != null && session.getAttribute("userID") != null)
	{
		response.sendRedirect("messageBoard.jsp");
	}
%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Group xx Message Board</title>

    <!-- Bootstrap Core CSS -->
    <link href="bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    
    <!-- Social Buttons CSS -->
    <link href="bower_components/bootstrap-social/bootstrap-social.css" rel="stylesheet">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

    <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Please Sign In</h3>
                    </div>
                    <div class="panel-body">
                        <form role="form" action="LoginRedirection" method="POST">
                            <fieldset>
                                <button class="btn btn-block btn-social btn-google-plus" name="direction" value="Google" type="submit">
                                	<i class="fa fa-google-plus"></i> Sign in with Google
                            	</button>
                            	<br>
                            	<button class="btn btn-block btn-social btn-github" name="direction" value="Github" type="submit">
                                	<i class="fa fa-github"></i> Sign in with Github
                            	</button>
                            	<br>
                            	<button class="btn btn-block btn-social btn-facebook" name="direction" value="Facebook" type="submit">
                                	<i class="fa fa-facebook"></i> Sign in with Facebook
                            	</button>
                            </fieldset>
                            
                            
                            <%
								if(session.getAttribute("error") != null)
								{
									String error = (String) session.getAttribute("error");
									%>
									<br>
									<div class="alert alert-danger alert-dismissable">
                                		<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                                			<%=error %>
                            		</div>
									<%
									session.removeAttribute("error");
								}
							%>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- jQuery -->
    <script src="bower_components/jquery/dist/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="bower_components/metisMenu/dist/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="dist/js/sb-admin-2.js"></script>

</body>

</html>
