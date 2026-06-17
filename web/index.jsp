<%-- 
    Document   : index
    Created on : Apr 9, 2026, 5:12:37 PM
    Author     : KINGSTAR
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>University Portal - Login</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <div class="card" style="max-width: 400px; margin: auto;">
            <h2 style="margin-bottom: 1rem;">Academic Portal</h2>
            <form action="loginServlet" method="POST">
                <input type="text" name="username" placeholder="Student or Staff ID" required>
                <input type="password" name="password" placeholder="Password" required>
                <button type="submit" class="btn" style="width: 100%;">Login</button>
            </form>
            <div style="margin-top: 20px; font-size: 0.9rem;">
                <p>Quick Access Demo:</p>
                <a href="lecturer.jsp">Lecturer View</a> | <a href="student.jsp">Student View</a>
            </div>
        </div>
    </div>
</body>
</html>
