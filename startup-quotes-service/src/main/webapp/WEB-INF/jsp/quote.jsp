<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">

<body>
  <blockquote>
    <c:out value="${quote.comment}"/>
  </blockquote>
  <h5><c:out value="${quote.author.name}"/> - @<c:out value="${quote.author.twitterHandle}"/></h5>
  <br>
  <h5>Was a Cache Miss... <c:if test="${cacheMiss == true}">Yes</c:if><c:if test="${cacheMiss == true}">No</c:if></h5>
</body>

</html>
