<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<c:set var="user" value="${requestScope.user}"/>
	<c:set var="threads" value="${requestScope.threads}"/>
	<c:set var="posts" value="${requestScope.posts}"/>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<title>${user.userName}</title>
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
      		<c:if test="${sessionScope.user != null }">
				<c:choose>
					<c:when test="${sessionScope.user.role == 'Administrator' }">
						<li><a href="./UserEditServlet?action=find&userId=${user.id }">Izmeni</a></li>
					
						<c:if test="${user.disabled == false }">
							<li><a href="./UserEditServlet?action=disable&userId=${user.id }">Onemoguci</a></li>
						</c:if>
						<c:if test="${user.disabled == true }">
							<li><a href="./UserEditServlet?action=enable&userId=${user.id }">Omoguci</a></li>
						</c:if>
						
						<li><a href="./UserDeleteServlet?userId=${user.id }">Obrisi</a></li>
						
					</c:when>
					<c:when test="${sessionScope.user.userName == user.userName }">
						<li><a href="./UserEditServlet?action=find&userId=${user.id }">Izmeni</a><br/></li>
					</c:when>
				</c:choose>
			</c:if>
		</ul>
	      <form action="#" method="GET" class="navbar-form navbar-left">
	        <div class="form-group">
	          <input type="text" class="form-control" placeholder="Search" name="searchParameter">
	          <input type="hidden" value="search" name="action"/>
	        </div>
	        <button type="submit" class="btn btn-default">Submit</button>
	      </form>
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
			Kreiran: ${user.dateCreated }<br/>
			
			<c:if test="${user.disabled == false }">
				Nalog je omogucen
			</c:if>
			<c:if test="${user.disabled == true }">
				Nalog je onemogucen
			</c:if>
	</div></br>
	
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">
				Korisnikove Teme
			</div>
			<div class="panel-body">
				<table class="table">
					<c:forEach var="thread" items="${threads }">
						<tr>
							<td><a href="ThreadServlet?threadId=${thread.id }">${thread.name }</a></td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>

	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">
				Korisnikove Poruke
			</div>
			<div class="panel-body">
				<table class="table">
					<c:forEach var="post" items="${posts }">
						<tr>
							<td><a href="PostServlet?postId=${post.id }">${post.content}</a></td>
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