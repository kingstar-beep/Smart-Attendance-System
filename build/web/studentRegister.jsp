<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>

    <title>Student Registration</title>

    <style>

        body {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
            background: #f4f6f9;
        }

        .header {
            background: #002147;
            color: white;
            text-align: center;
            padding: 25px;
        }

        .header h1 {
            margin: 0;
        }

        .register-container {
            width: 400px;
            margin: 50px auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0,0,0,0.15);
        }

        .register-container h2 {
            text-align: center;
            color: #002147;
        }

        label {
            display: block;
            margin-top: 12px;
        }

        input {
            width: 100%;
            padding: 12px;
            margin-top: 8px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }

        button {
            width: 100%;
            padding: 12px;
            background: #F5B800;
            border: none;
            font-weight: bold;
            cursor: pointer;
            border-radius: 5px;
        }

        button:hover {
            background: #d9a600;
        }

        .error {
            color: #c62828;
            background: #ffebee;
            padding: 10px;
            border-radius: 5px;
            text-align: center;
            margin-bottom: 15px;
        }

        .success {
            color: #2e7d32;
            background: #e8f5e9;
            padding: 10px;
            border-radius: 5px;
            text-align: center;
            margin-bottom: 15px;
        }

        .login-link {
            text-align: center;
            margin-top: 20px;
        }

        .login-link a {
            color: #002147;
            font-weight: bold;
            text-decoration: none;
        }

        .footer {
            text-align: center;
            margin-top: 20px;
            color: #777;
        }

    </style>

</head>

<body>

    <div class="header">

        <h1>University of Greenwich</h1>

        <p>Smart Attendance Management System</p>

    </div>

    <div class="register-container">

        <h2>Student Registration</h2>

        <%
            String error =
                    (String) request.getAttribute("error");

            String success =
                    (String) request.getAttribute("success");

            if (error != null) {
        %>

            <div class="error">
                <%= error %>
            </div>

        <%
            }

            if (success != null) {
        %>

            <div class="success">
                <%= success %>
            </div>

        <%
            }
        %>

        <form action="StudentRegisterServlet"
              method="post">

            <label>Student ID</label>

            <input type="text"
                   name="studentId"
                   maxlength="50"
                   required>

            <label>Full Name</label>

            <input type="text"
                   name="name"
                   maxlength="100"
                   required>

            <label>Password</label>

            <input type="password"
                   name="password"
                   minlength="4"
                   required>

            <label>Confirm Password</label>

            <input type="password"
                   name="confirmPassword"
                   minlength="4"
                   required>

            <button type="submit">
                Register
            </button>

        </form>

        <div class="login-link">

            Already registered?

            <a href="login.jsp">
                Return to Student Login
            </a>

        </div>

    </div>

    <div class="footer">

        MSc Computing Project 2026

    </div>

</body>

</html>