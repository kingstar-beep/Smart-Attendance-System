<%-- 
    Document   : qr-display
    Created on : Apr 9, 2026, 5:31:08 PM
    Author     : KINGSTAR
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Attendance QR</title>
        <link rel="stylesheet" href="css/style.css">
        <!-- <meta http-equiv="refresh" content="60"> -->
    </head>
    <body style="background: var(--dark); color: white;">
        <div class="container" style="text-align: center;">
            <div class="header">
                <h1>University of Greenwich</h1>
                <p>Smart Attendance Management System</p>
            </div>
            <!--  <div class="card" style="display: inline-block; padding: 40px; background: white;">
                  <div style="width: 300px; height: 300px; background: #eee; border: 2px dashed #ccc; display: flex; align-items: center; justify-content: center; color: #333;">
                      <div style="text-align: center;">
                          <div style="font-size: 50px;">📱</div>
                          <p>[QR CODE DATA]</p>
                      </div>
                  </div>
              </div> -->
            <%
                String sessionCode = (String) request.getAttribute("sessionCode");
                String qrData = (String) request.getAttribute("qrData");
                String code = request.getParameter("code");
                String qrData1 = "http://localhost:8080/SmartAttendance/MarkAttendanceServlet?code=" + code;

                //out.print(code);
                //  out.print(qrData1);
%>

            <div class="qr-card">

                <h2>Attendance Session Active</h2>

                <img src="GenerateQRServlet?code=<%= qrData%>"
                     width="300">

                <h3>
                    Session Code:
                    <%= qrData%>
                  <!--  <%= sessionCode%> -->                 
                </h3>

                <p>
                    Current Radius:
                    <b><%= request.getParameter("radius")%> metres</b>
                </p>

            </div>

            <!--
                        <p>QR Data: <%= qrData%></p>
            
                        <h2>Scan this QR</h2>
                       <!-- <img src="GenerateQRServlet?code=<%=sessionCode%>" /> 
                        <img src="GenerateQRServlet?code=<%=qrData%>" />
                        <a href="lecturer.jsp" style="color: white; margin-top: 20px; display: block; opacity: 0.6;">Return to Dashboard</a>
                    
            -->

            <div class="security-card">

                <h3>Verification Layers</h3>

                <p>✔ QR Code Authentication</p>

                <p>✔ Geo-Fencing Validation</p>

                <p>✔ Student Login Verification</p>

                <p>✔ Image Capture Confirmation</p>

            </div>

            <div class="status-card">

                <h3>Status</h3>

                <p style="color:green;">
                    ● Session Active
                </p>

            </div>

            <a href="LecturerDashboardServlet">

                <button>
                    View Attendance Dashboard
                </button>

            </a>

        </div>


    </body>
</html>