<%@page import="cloud.Noback"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Group 8 Message Board</title>

<!-- Bootstrap Core CSS -->
<link href="bower_components/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet">

<!-- MetisMenu CSS -->
<link href="bower_components/metisMenu/dist/metisMenu.min.css"
	rel="stylesheet">

<!-- Custom CSS -->
<link href="dist/css/sb-admin-2.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="bower_components/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>
	<% 
		
		Noback yo = new Noback();
		yo.noback(session, request, response);
		//Session fixation
		if(session!=null){
			String realm=session.getAttribute("realm").toString();
			String user=session.getAttribute("user").toString();
			String email=session.getAttribute("email").toString();
			String at=session.getAttribute("at").toString();
			String uid=session.getAttribute("uid").toString();
			session.invalidate();
			session=request.getSession(true);
			session.setAttribute("realm",realm);
			session.setAttribute("user", user);
			session.setAttribute("email", email);
			session.setAttribute("at",at);
			session.setAttribute("uid", uid);
		}
		
	%>

	<div id="wrapper">

		<!-- Navigation -->
		<nav class="navbar navbar-default navbar-static-top" role="navigation"
			style="margin-bottom: 0">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="index.jsp">Group 8 Message Boards</a>
			</div>
			<!-- /.navbar-header -->

			<ul class="nav navbar-top-links navbar-right">
				<!-- /.dropdown -->
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#"> <i class="fa fa-user fa-fw"></i>
						<i>User</i> <i class="fa fa-caret-down"></i>
				</a>
					<ul class="dropdown-menu dropdown-user">

						<li><a href="Logout"><i class="fa fa-sign-out fa-fw"></i>
								Logout</a></li>
					</ul> <!-- /.dropdown-user --></li>
				<!-- /.dropdown -->
			</ul>
			<!-- /.navbar-top-links -->
		</nav>

		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Message Board</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<form method="post" action="SendChat">
				<div class="row">
					<div class="col-lg-2"></div>
					<div class="col-lg-8">
						<div class="col-lg-2">Numbers of Message :</div>
						<div class="col-lg-3">
							<select class="form-control" id="recentMessage" name="limit">
								<option value="5">5</option>
								<option value="10">10</option>
								<option value="30">30</option>
								<option value="40">40</option>
								<option value="0">All</option>
							</select>
						</div>
						<div class="col-lg-6"></div>
						<div class="col-lg-1">
							<button type="button" class="btn btn-info btn-circle"
								style="outline: 0 !important;">
								<i class="fa fa-refresh" id="refreshButton"></i>
							</button>
						</div>
					</div>
					<div class="col-lg-2"></div>
				</div>
				<br>
				<div class="row">
					<div class="col-lg-2"></div>
					<!-- /.col-lg-8 -->
					<div class="col-lg-8">
						<!-- /.panel -->
						<div class="chat-panel panel panel-default">
							<div class="panel-heading">
								<i class="fa fa-comments fa-fw"></i> Board
							</div>
							<!-- /.panel-heading -->
							<div class="panel-body">
								<center style="position: relative; top: 50%;"
									id="loadingAnimation">
									<img src="dist/ajax-loader.gif">
								</center>
								<ul class="chat">

								</ul>
							</div>
							<!-- /.panel-body -->

							<div class="panel-footer">
								<div class="input-group">

									<input id="btn-input" type="text" class="form-control input-sm"
										placeholder="Type your message here..." name="chat" /> <input
										type="hidden" name="userid"
										value="<%=request.getSession(false).getAttribute("uid")%>">
									<span class="input-group-btn">
										<button class="btn btn-warning btn-sm" id="btn-chat"
											type="submit">Send</button>
									</span>
								</div>
							</div>
							<!-- /.panel-footer -->
						</div>
						<!-- /.panel .chat-panel -->
					</div>
					<!-- /.col-lg-4 -->
				</div>
			</form>
			<!-- /.row -->
		</div>
		<!-- /#page-wrapper -->

	</div>
	<!-- /#wrapper -->

	<!-- jQuery -->
	<script src="bower_components/jquery/dist/jquery.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script src="bower_components/metisMenu/dist/metisMenu.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="dist/js/sb-admin-2.js"></script>

	<script type="text/javascript">
    
    $(document).ready(function(){
    	
    	function fetchMessage(x)
    	{
    		$(".chat").html("");
    		$("#loadingAnimation").show();
    		$.ajax({url: "FetchChat?limit="+x+"&userid="+<%=request.getSession(false).getAttribute("uid")%>, success: function(result)
    	    {
    	    	//console.log(result);
    	        $(".chat").html(result);
    	        $("#loadingAnimation").hide();
    	     }});
    	}
    	
    	fetchMessage($("#recentMessage").val());
    	$("#recentMessage").change(function()
    	{
    		//alert($(this).val());
    		fetchMessage($(this).val());
    	});
    	$("#refreshButton").click(function()
    	{
    		fetchMessage($("#recentMessage").val());
    	});
    	
    });
    
    </script>

</body>

</html>
