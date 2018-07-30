<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<title>Novi korisnik</title>
	</head>
<body>
	<nav class="navbar navbar-default">
	  <div class="container-fluid">
	    <!-- Brand and toggle get grouped for better mobile display -->
	    <div class="navbar-header">
	      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
	        <span class="sr-only">Toggle navigation</span>
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>
	      </button>
	      <a class="navbar-brand" href="./HomepageServlet">Naslov foruma</a>
	    </div>
	
	    <!-- Collect the nav links, forms, and other content for toggling -->
	    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
	      <ul class="nav navbar-nav">
	      </ul>
	      <ul class="nav navbar-nav navbar-right">
	      
	      <c:choose>
		      <c:when test="${user == null }">
		      	<li><a id=loginLink href="login.html">Login</a></li>
		      </c:when>
		      <c:when test="${user != null }">
		      	<li><a id=loginLink href="./UserServlet?userId=${sessionScope.user.id }">${sessionScope.user.userName }</a></li>
		      	<li><a href="./LogoutServlet">Logout</a>
		      </c:when>
	      </c:choose>
	        
	      </ul>
	    </div><!-- /.navbar-collapse -->
	  </div><!-- /.container-fluid -->
	</nav>
	
	<div class="container">
		<form action="./UserCreateServlet?action=create" method="POST">
			<div class="form-group">
				<input type="hidden" name="action" value="forward"/>
				<label for="name">Ime</label>
				<input class="form-control" type="text" id="name" name="name"/><br/>
				<label for="surname">Prezime</label>
				<input class="form-control" type="text" id="surname" name="surname"/><br/>
				<label for="username">Korisnicko Ime</label>
				<input class="form-control" type="text" id="username" name="username"/><br/>
				<label for="password">Lozinka</label>
				<input class="form-control" type="password" id="password" name="password"/><br/>
				<label for="email">Email</label>
				<input class="form-control" type="text" id="text" name="email"/><br/>
				<label for="role">Uloga</label>
				<select class="form-control" id="role" name="role">
					<option>User</option>
					<option>Moderator</option>
					<option>Administrator</option>
				</select><br/>
				<a class="btn btn-default" href="./UserManagementServlet">Otkazi</a>
				<input class="btn btn-default" type="submit" value="Dodaj"/>
			</div>
		</form>
	</div>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
  	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>