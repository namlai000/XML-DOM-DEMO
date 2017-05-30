<%-- 
    Document   : Search
    Created on : May 30, 2017, 7:16:13 AM
    Author     : thegu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome Page</title>
    </head>
    <body>
        <h1>Welcome ${sessionScope.fullname}</h1>
        <div>
            <form action="ProcessServlet" method="POST">
                Search: <input type="text" name="txtSearch" value="${param.txtSearch}"/>
                <button type="submit" name="btAction" value="search">Search</button>
            </form>
        </div>
        <div>
            <c:set var="search" value="${param.txtSearch}"/>
            <c:if test="${not empty search}">
                <c:set var="result" value="${requestScope.result}"/>
                <c:if test="${not empty result}">
                    <table>
                        <thead>
                            <tr>
                                <th>No.</th>
                                <th>Fullname</th>
                                <th>Class</th>
                                <th>Sex</th>
                                <th>Address</th>
                                <th>Status</th>
                                <th>Delete</th>
                                <th>Update</th>
                            </tr> 
                        </thead>
                        <tbody>
                            <c:forEach var="student" items="${result}" varStatus="counter">
                                <form action="ProcessServlet" method="POST">
                                    <tr>
                                        <td>
                                            ${counter.count}

                                            <!-- FOR EDITING ROW -->
                                            <input type="hidden" name="id" value="${student.id}"/>
                                            <input type="hidden" name="txtSearch" value="${param.txtSearch}"/>
                                        </td>
                                        <td>${student.lastname} ${student.middlename} ${student.firstname}</td>
                                        <td>
                                            <input type="text" name="class" value="${student.sClass}"/>
                                        </td>
                                        <td>${student.sex}</td>
                                        <td>${student.address}</td>
                                        <td>
                                            <input type="text" name="status" value="${student.status}"/>
                                        </td>
                                        <td>
                                            <c:url var="url" value="ProcessServlet">
                                                <c:param name="btAction" value="delete"/>
                                                <c:param name="id" value="${student.id}"/>
                                                <c:param name="txtSearch" value="${param.txtSearch}"/>
                                            </c:url>
                                            <a href="${url}">Delete</a>
                                        </td>
                                        <td>
                                            <button type="submit" name="btAction" value="update">Update</button>
                                        </td>
                                    </tr> 
                                </form>
                            </c:forEach>     
                        </tbody>
                    </table>
                </c:if>
                <c:if test="${empty result}">
                    <h2>No record is matched!!!</h2>
                </c:if>
            </c:if> 
        </div>
        <div>
            <h3>Insert new student</h3>
            <form action="ProcessServlet" method="POST">
                Username: <input type="text" name="username"/><br/>
                Password: <input type="password" name="password"/><br/>
                Lastname: <input type="text" name="lastname"/><br/>
                Middlename: <input type="text" name="middlename"/><br/>
                Firstname: <input type="text" name="firstname"/><br/>
                Class: <input type="text" name="class"/><br/>
                Sex: <input type="checkbox" name="sex" value="MALE" /><br/>
                Address: <input type="text" name="address"/><br/>
                Status: <input type="text" name="status"/><br/>
                <button type="submit" name="btAction" value="add">Insert</button>
            </form>
        </div>
    </body>
</html>
