<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<title>Upravljanje korisnicima</title>
	<c:set var="users" value="${requestScope.users }"/>
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
	      	<li><a href="./UserCreateServlet?action=forward">Dodaj korisnika</a></li>
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
		<div class="panel panel-default">
			<div class="panel-heading">
		    	Korisnici
		  	</div>
		  	<div class="panel-body">
				<table class="table" align="left">
					<c:forEach var="i" items="${users }">
								<tr>
									<td><a href="./UserServlet?userId=${i.id }">${i.userName }</a></td>
									<td>${i.name }</td>
									<td>${i.surname }</td>
									<td>${i.email }</td>
									<td>${i.role }</td>
								</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
  	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>