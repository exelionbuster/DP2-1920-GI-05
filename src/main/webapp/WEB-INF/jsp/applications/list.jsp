<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="applications ">
    <h2>Applications</h2>

    <table id="applicationsTable" class="table table-striped">
        <thead>
        <tr>
            <th>Status</th>
            <th>Moment</th>
            <th>Credit Card</th>
            <th>Pet</th>
            <th>Tournament</th>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${applications}" var="application">
            <tr>
                <td>
                    <c:out value="${application.status} "/>
                </td>
                <td>
                    <c:out value="${application.moment} "/>
                </td>
                <td>
                    <c:out value="${application.creditCard} "/>
                </td>
                <td>
                    <c:out value="${application.pet} "/>
                </td>
                <td>
                    <c:out value="${application.tournament.name} "/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</petclinic:layout>
