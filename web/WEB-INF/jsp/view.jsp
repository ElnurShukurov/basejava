<%@ page import="com.urise.webapp.model.CompanySection" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page import="com.urise.webapp.model.TextSection" %>
<%@ page import="com.urise.webapp.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/edit.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br>
        </c:forEach>
    </p>
    <hr>
    <c:forEach var="sectionEntry" items="${resume.sections}">
        <jsp:useBean id="sectionEntry"
                     type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.Section>"
        />
        <c:set var="type" value="${sectionEntry.key}"/>
        <c:set var="section" value="${sectionEntry.value}"/>
        <jsp:useBean id="section" type="com.urise.webapp.model.Section"/>
        <h3>${type.title}</h3>
        <c:choose>
            <c:when test="${type == 'OBJECTIVE' || type == 'PERSONAL'}">
                <c:if test="${not empty section.content}">
                    <%=((TextSection) section).getContent()%>
                </c:if>
            </c:when>
            <c:when test="${type == 'ACHIEVEMENT' || type == 'QUALIFICATIONS'}">
                <c:if test="${fn:length(section.items) gt 0 && fn:length(section.items[0]) > 0}">
                    <tr>
                        <td>
                            <ul>
                                <c:forEach var="item" items="<%=((ListSection) section).getItems()%>">
                                    <li>${item}</li>
                                </c:forEach>
                            </ul>
                        </td>
                    </tr>
                </c:if>
            </c:when>
            <c:when test="${type == 'EDUCATION' || type == 'EXPERIENCE'}">
                <c:if test="${fn:length(section.companies) gt 0}">
                    <c:forEach var="company" items="<%=((CompanySection) section).getCompanies()%>">
                        <tr>
                            <td colspan="2">
                                <c:choose>
                                    <c:when test="${empty company.homepage.url}">
                                        Компания: ${company.homepage.name}<br>
                                    </c:when>
                                    <c:otherwise>
                                        Компания: <a href="${company.homepage.url}">${company.homepage.name}</a><br>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <c:forEach var="period" items="${company.periods}">
                            <jsp:useBean id="period" type="com.urise.webapp.model.Company.Period"/>
                            <tr>
                                <td>
                                    Период: <%=DateUtil.format(period.getStartDate())%>
                                    - <%=DateUtil.format(period.getEndDate())%><br>
                                </td>
                                <td>
                                    Должность: ${period.title} <br>
                                </td>
                                <c:if test="${not empty period.description}">
                                    <td>
                                        Описание: ${period.description}<br>
                                    </td>
                                </c:if>
                            </tr>
                        </c:forEach>
                        <hr>
                    </c:forEach>
                </c:if>
            </c:when>
        </c:choose>
    </c:forEach>
    <button onclick="window.history.back()">ОК</button>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>