<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<c:set var="forum" value="${requestScope.forum }"/>
	<c:set var="parent" value="${forum.parentForum }"/>
	<title>Edit forum</title>
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
		<form action="./ForumEditServlet" method="POST">
			<div class="form-group">
				<input type="hidden" name="action" value="edit"/>
				<input type="hidden" name="id" value="${forum.id }"/>
				<label for="name">Naziv</label>
				<input class="form-control" type="text" id="name" name="name" value="${forum.name } placeholder="Obavezno"/><br/>
				<label for="description">Opis</label>
				<input class="form-control" type="text" id="description" name="description" value="${forum.description }"/><br/>
				<label for="dateCreated">Datum kreiranja</label>
				<input class="form-control" type="datetime-local" id="dateCreated" name="dateCreated" value="${forum.dateCreated }"/><br/>
			
				<label for="type">Tip</label>
				<select class="form-control" id="type" name="type">
				<c:choose>
					<c:when test="${empty parent }">
						<option>Public</option>
						<option>Open</option>
						<option>Close</option>
					</c:when>
					<c:otherwise>
						<c:if test="${parent.type == 'Public' }">
						<option>Public</option>
						<option>Open</option>
						</c:if>
						<c:if test="${parent.type == 'Open' }">
						<option>Open</option>
						</c:if>
						<option selected="${forum.type }">Closed</option>
					</c:otherwise>
				</c:choose>
				</select><br/>
				
				<input type="hidden" name="locked" value="${forum.locked }"/>
				<input type="hidden" name="owner" value="${forum.owner.id }"/>
				<input type="hidden" name="parent" value="${forum.parentForum.id }"/>		
				<a class="btn btn-default" href="./ForumServlet?forumId=${forum.id }">Otkazi</a>
				<input class="btn btn-default" type="submit" id="add" value="Sacuvaj">
			</div>
		</form>
	</div>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
  	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  	<script type="text/javascript">
  		$(document).ready(function() {
  			var nameInput = $('#name');
  			var submitButton = $('#add');
  			
  			nameInput.on('keyup', function(event) {
  				forumName = nameInput.val();
  				if (forumName != '') {
  					submitButton.prop('disabled', false);
  				} else {
  					submitButton.prop('disabled', true);
  				}
  			});
  		});
  	</script>
</body>
</html>