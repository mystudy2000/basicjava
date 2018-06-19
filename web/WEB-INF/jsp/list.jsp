<%@ page import="webapp.model.TypeOfContact" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css" media="all">
    <title>Список всех резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <a href="resume?action=add"><img src="img/add.png" style="margin: auto"></a>
    <table border="1" cellpadding="8" cellspacing="0" style="margin: auto" >
        <tr>
            <th>Фамилия Имя Отчество</th>
            <th>Email</th>
            <th>Телефон дом.</th>
            <th>Телефон моб.</th>
            <th>Удалить</th>
            <th>Изменить</th>
        </tr>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="webapp.model.Resume"/>
            <tr>
                <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                <th><%=TypeOfContact.MAIL.toHtml5(resume.getContact(TypeOfContact.MAIL))%></th>
                <th><%=TypeOfContact.HOMEPHONE.toHtml5(resume.getContact(TypeOfContact.HOMEPHONE))%></th>
                <th><%=TypeOfContact.MOBILEPHONE.toHtml5(resume.getContact(TypeOfContact.MOBILEPHONE))%></th>
                <td><a href="resume?uuid=${resume.uuid}&action=delete"><img src="${pageContext.request.contextPath}/img/delete.png" align="center"></a></td>
                <td><a href="resume?uuid=${resume.uuid}&action=edit"><img src="${pageContext.request.contextPath}/img/pencil.png" align="center"></a></td>
            </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>