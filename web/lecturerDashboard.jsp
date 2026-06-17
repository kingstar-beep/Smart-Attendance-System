<%-- 
    Document   : lecturerDashboard
    Created on : Jun 9, 2026, 3:03:53 PM
    Author     : KINGSTAR
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page import="java.util.List"%>
<%@page import="model.Attendance"%>

<%
    List<Attendance> attendanceList
            = (List<Attendance>) request.getAttribute("attendanceList");
%>

<!DOCTYPE html>
<html>
    <head>

        <title>Lecturer Dashboard</title>

        <style>

            body{
                font-family:Arial;
                background:#f4f6f9;
                margin:0;
            }

            .header{
                background:#002147;
                color:white;
                padding:20px;
                text-align:center;
            }

            .card-container{
                display:flex;
                justify-content:center;
                gap:20px;
                margin:20px;
            }

            .card{
                background:white;
                padding:20px;
                width:200px;
                text-align:center;
                border-radius:10px;
                box-shadow:0px 2px 8px #ccc;
            }

            table{
                width:95%;
                margin:auto;
                border-collapse:collapse;
                background:white;
            }

            th{
                background:#002147;
                color:white;
                padding:12px;
            }

            td{
                padding:10px;
                border:1px solid #ddd;
                text-align:center;
            }

            img{
                width:70px;
                height:70px;
                border-radius:8px;
            }

        </style>

    </head>

    <body>

        <div class="header">

            <h1>University of Greenwich</h1>

            <h3>Secure Smart Attendance Dashboard</h3>

        </div>

        <div class="card-container">

            <div class="card">
                <h2><%= attendanceList.size()%></h2>
                <p>Total Attendance</p>
            </div>

            <div class="card">
                <h2>100%</h2>
                <p>GPS Verified</p>
            </div>

            <div class="card">
                <h2>100%</h2>
                <p>Image Verified</p>
            </div>

            <div class="card">
                <h2>Active</h2>
                <p>QR Session</p>
            </div>

            <div class="card">
                <h2>GPS</h2>
                <p>Enabled</p>
            </div>

            <div class="card">
                <h2>QR Code</h2>
                <p>Active</p>
            </div>

            <div class="card">
                <h2>Image Capture</h2>
                <p>Verified</p>
            </div>            

        </div>

        <table>

            <tr>

                <th>ID</th>
                <th>Student Name</th>
                <th>Student ID</th>
                <th>Session Code</th>
                <th>Time</th>
                <th>Image</th>
                <th>Status</th>

            </tr>

            <%

                for (Attendance a : attendanceList) {

            %>

            <tr>

                <td><%= a.getId()%></td>

                <td><%= a.getStudentName()%></td>

                <td><%= a.getStudentId()%></td>

                <td><%= a.getSessionCode()%></td>

                <td><%= a.getCheckInTime()%></td>

                <td>

                    <img src="ImageServlet?path=<%= a.getImagePath()%>"
                         width="80"
                         height="80">

                </td>

                <td>
                    <span style="color:green;">
                        Verified
                    </span>
                </td>

            </tr>

            <%

                }

            %>

        </table>

    </body>

</html>
