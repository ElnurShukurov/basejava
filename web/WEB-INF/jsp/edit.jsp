<%@ page import="com.urise.webapp.model.CompanySection" %>
<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size="50" value="${resume.fullName}" required></dd>
        </dl>
        <h2>Контакты:</h2>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h2>Секции:</h2>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(type)}"/>
            <jsp:useBean id="section" type="com.urise.webapp.model.Section"/>
            <h3>${type.title}</h3>
            <c:choose>
                <c:when test="${type == 'OBJECTIVE'}">
                    <input type="text" name="${type.name()}" size=90 value="${resume.getSection(type)}">
                </c:when>
                <c:when test="${type == 'PERSONAL'}">
                    <textarea name="${type}" cols=90 rows=5><%=section%></textarea>
                </c:when>
                <c:when test="${type == 'ACHIEVEMENT' || type == 'QUALIFICATIONS'}">
                    <c:set var="section" value="${resume.getSection(type)}"/>
                    <textarea name='${type}' cols=75
                              rows=5><%=String.join("\n", ((ListSection) section).getItems())%></textarea>
                </c:when>
                <c:when test="${type == 'EDUCATION' || type == 'EXPERIENCE'}">
                    <c:forEach var="company" items="<%=((CompanySection) section).getCompanies()%>" varStatus="counter">
                        <dl>
                            <dt>Компания:</dt>
                            <dd><input type="text" name="${type}" size=100 value="${company.homepage.name}"></dd>
                            </dd>
                        </dl>
                        <dl>
                            <dt>Сайт:</dt>
                            <dd><input type="text" name="${type}url" size=100 value="${company.homepage.url}"></dd>
                            </dd>
                        </dl>
                        <div style="margin-left: 20px">
                            <c:forEach var="period" items="${company.periods}">
                                <jsp:useBean id="period" type="com.urise.webapp.model.Company.Period"/>
                                <dl>
                                    <dt>Период:</dt>
                                    <dd><input type="text" name="${type}${counter.index}startDate" size="10"
                                               value="<%=DateUtil.format(period.getStartDate())%>" placeholder=MM/yyyy>
                                        -
                                        <input type="text" name="${type}${counter.index}endDate" size="10"
                                               value="<%=DateUtil.format(period.getEndDate()) %>" placeholder=MM/yyyy>
                                    </dd>
                                </dl>
                                <dl>
                                    <dt>Должность:</dt>
                                    <dd><input type="text" name="${type}${counter.index}title" size="50"
                                               value="${period.title}"></dd>
                                </dl>
                                <dl>
                                    <dt>Описание:</dt>
                                    <dd><textarea name="${type}${counter.index}description" cols="75"
                                                  rows="5">${period.description}</textarea></dd>
                                </dl>
                            </c:forEach>
                        </div>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>