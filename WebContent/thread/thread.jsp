<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<c:set var="user" value="${sessionScope.user }"/>
	<c:set var="thread" value="${requestScope.thread }"/>
	<c:set var="forum" value="${thread.forum }"/>
	<title>${requestScope.thread.name }</title>
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
			<li><a href="./ForumServlet?forumId=${forum.id }">Forum</a></li>

			<c:if test="${thread.flags.canEdit }">
				<li><a href="./ThreadEditServlet?action=find&threadId=${thread.id }">Izmeni</a></li>
			</c:if>

			<c:if test="${thread.flags.canLock }">
				<c:choose>
					<c:when test="${thread.locked }">
						<li><a href="./ThreadEditServlet?action=unlock&threadId=${thread.id }">Otkljucaj</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="./ThreadEditServlet?action=lock&threadId=${thread.id }">Zakljucaj</a></li>
					</c:otherwise>
				</c:choose>
			</c:if>

			<c:if test="${thread.flags.canPin }">
				<c:choose>
					<c:when test="${thread.pinned }">
						<li><a href="./ThreadEditServlet?action=unpin&threadId=${thread.id }">Otkaci</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="./ThreadEditServlet?action=pin&threadId=${thread.id }">Zakaci</a></li>
					</c:otherwise>
				</c:choose>
			</c:if>

			<c:if test="${thread.flags.canDelete }">
				<li><a href="./ThreadDeleteServlet?threadId=${thread.id }">Obrisi</a></li>
			</c:if>
			
			<c:if test="${thread.flags.canAddPost }">
				<li><a href="./PostCreateServlet?forumId=${forum.id }&threadId=${thread.id }">Novi post</a></li>
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
		<div class="panel panel-defautl">
			<h2>${thread.name }</h2>
			${thread.description }<br/>
			<a href="./UserServlet?userId=${thread.owner.id}">
				${thread.owner.userName }
			</a><br/>
			${thread.dateCreated }
		</div>
	</div><br/>

	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">
				Poruke
			</div>
			<div class="panel-body">
				<table class="table">
					<c:forEach var="i" items="${requestScope.threadPosts }">
						<tr>
							<td><a href="./UserServlet?userId=${i.owner.id }">${i.owner.userName }</a></td>
							<td>${i.dateCreated }</td>
							<td>${i.content }</td>
							<td><a href="PostServlet?postId=${i.id }">Detalji</a></td>
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