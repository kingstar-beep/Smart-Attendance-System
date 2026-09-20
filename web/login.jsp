<%-- 
    Document   : login
    Created on : Apr 19, 2026, 6:39:37 AM
    Author     : KINGSTAR
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>

        <title>Student Login</title>

        <style>

            body{
                margin:0;
                padding:0;
                font-family:Arial, sans-serif;
                background:#f4f6f9;
            }

            .header{
                background:#002147;
                color:white;
                text-align:center;
                padding:25px;
            }

            .header h1{
                margin:0;
            }

            .login-container{
                width:400px;
                margin:50px auto;
                background:white;
                padding:30px;
                border-radius:10px;
                box-shadow:0 0 15px rgba(0,0,0,0.15);
            }

            .login-container h2{
                text-align:center;
                color:#002147;
            }

            input{
                width:100%;
                padding:12px;
                margin-top:8px;
                margin-bottom:15px;
                border:1px solid #ccc;
                border-radius:5px;
            }

            button{
                width:100%;
                padding:12px;
                background:#F5B800;
                border:none;
                font-weight:bold;
                cursor:pointer;
                border-radius:5px;
            }

            button:hover{
                background:#d9a600;
            }

            .error{
                color:red;
                text-align:center;
                margin-bottom:15px;
            }

            .features{
                margin-top:20px;
            }

            .features p{
                margin:5px 0;
                color:#555;
            }

            .footer{
                text-align:center;
                margin-top:20px;
                color:#777;
            }

        </style>

    </head>

    <body>

        <div class="header">

            <h1>University of Greenwich</h1>

            <p>Smart Attendance Management System</p>

        </div>

        <div class="login-container">

            <h2>Student Login</h2>

            <%
                String error
                        = (String) request.getAttribute("error");

                if (error != null) {
            %>

            <div class="error">
                <%= error%>
            </div>

            <%
                }
            %>
            <div>
                <span style="align-content: center;">
                    <h4> Welcome to the Attendance Portal, verify your identity by Login with your student ID to validate your attendance.</h4>

                </span>
            </div>
            <form action="LoginServlet" method="post">

                <label>Student ID</label>

                <input
                    type="text"
                    name="studentId"
                    required>

                <label>Password</label>

                <input
                    type="password"
                    name="password"
                    required>

                <button type="submit">

                    Login

                </button>

            </form>

            <div style="text-align:center; margin-top:20px;">

                <p>
                    New student?
                    <a href="studentRegister.jsp"
                       style="color:#002147; font-weight:bold; text-decoration:none;">
                        Register here
                    </a>
                </p>

            </div>

            <div class="features">

                <p>✔ Secure Student Authentication</p>

                <p>✔ QR Code Attendance Verification</p>

                <p>✔ GPS Location Validation</p>

                <p>✔ Image-Based Confirmation</p>

            </div>

        </div>

        <div class="footer">

            MSc Computing Project 2026

        </div>

    </body>

</html>
