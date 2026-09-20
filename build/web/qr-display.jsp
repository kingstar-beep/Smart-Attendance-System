<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    String sessionCode
            = (String) request.getAttribute("sessionCode");

    String qrData
            = (String) request.getAttribute("qrData");

    String course
            = (String) request.getAttribute("course");

    Object radiusObject
            = request.getAttribute("radius");

    String radius
            = radiusObject != null
                    ? radiusObject.toString()
                    : "N/A";

    long expiryTime = 0;

    Object expiryObject
            = request.getAttribute("expiryTime");

    if (expiryObject != null) {

        try {

            expiryTime
                    = Long.parseLong(
                            expiryObject.toString()
                    );

        } catch (NumberFormatException e) {

            expiryTime = 0;
        }
    }
%>

<!DOCTYPE html>

<html>

    <head>

        <title>Attendance QR</title>

        <link rel="stylesheet"
              href="css/style.css">

        <style>

            body {
                background: #002147;
                color: white;
                font-family: Arial, sans-serif;
                text-align: center;
            }

            .container {
                max-width: 800px;
                margin: 30px auto;
                padding: 20px;
            }

            .card {
                background: white;
                color: #333;
                padding: 30px;
                border-radius: 12px;
                margin-top: 25px;
            }

            .qr {
                width: 300px;
                height: 300px;
                margin: 20px auto;
            }

            .active {
                color: green;
                font-weight: bold;
            }

            .button {
                display: inline-block;
                background: #F5B800;
                color: #111;
                padding: 12px 20px;
                border-radius: 5px;
                text-decoration: none;
                font-weight: bold;
                margin-top: 20px;
            }

        </style>

    </head>

    <body>

        <div class="container">

            <h1>University of Greenwich</h1>

            <h2>Secure Smart Attendance</h2>


            <div class="card">

                <h2 id="sessionTitle">
                    Attendance Session Active
                </h2>

                <h3>
                    Course:
                    <%= course != null
                            ? course
                            : "Not specified"%>
                </h3>

                <p>
                    <strong>Session Code:</strong><br>

                    http://localhost:8080/SmartAttendance/MarkAttendanceServlet?code=<%= sessionCode%>
                </p>

                <p>
                    <strong>Attendance Radius:</strong>

                    <%= radius%> metres
                </p>


                <!-- QR contains the complete attendance URL -->

                <img class="qr"
                     src="GenerateQRServlet?code=<%= qrData%>"
                     alt="Attendance QR Code">


                <div id="sessionStatus"
                     class="active"
                     style="font-size:20px; margin-top:20px;">

                    ● Attendance Session Active

                </div>


                <div id="countdown"
                     style="font-size:28px;
                     font-weight:bold;
                     margin-top:15px;">

                </div>


                <form action="CloseSessionServlet"
                      method="post"
                      id="closeSessionForm"
                      style="margin-top:20px;">

                    <input type="hidden"
                           name="sessionCode"
                           value="<%= sessionCode%>">

                    <button type="submit"
                            class="button"
                            style="border:none; cursor:pointer;"
                            onclick="return confirm('Are you sure you want to close this attendance session?');">

                        Close Attendance Session

                    </button>

                </form>

            </div>


            <div class="card">

                <h3>Verification Layers</h3>

                <p>✔ Student Login Authentication</p>

                <p>✔ QR Code Authentication</p>

                <p>✔ GPS Location Verification</p>

                <p>✔ Image Capture Confirmation</p>

                <p>✔ Attendance Database Logging</p>

            </div>


            <a class="button"
               href="LecturerDashboardServlet">

                Return to Lecturer Dashboard

            </a>

        </div>

        <script>

            const expiryTime =
            <%= expiryTime%>;

            const countdown =
                    document.getElementById(
                            "countdown"
                            );

            const sessionStatus =
                    document.getElementById(
                            "sessionStatus"
                            );

            const sessionTitle =
                    document.getElementById(
                            "sessionTitle"
                            );

            const closeForm =
                    document.getElementById(
                            "closeSessionForm"
                            );


            function updateCountdown() {

                // 0 means manual control
                if (expiryTime === 0) {

                    countdown.innerHTML =
                            "Manual session control";

                    return;
                }


                const now =
                        new Date().getTime();

                const remaining =
                        expiryTime - now;


                if (remaining <= 0) {

                    countdown.innerHTML =
                            "Attendance session expired";

                    sessionStatus.innerHTML =
                            "● Attendance Session Expired";

                    sessionStatus.style.color =
                            "#c62828";

                    sessionTitle.innerHTML =
                            "Attendance Session Expired";

                    if (closeForm) {
                        closeForm.style.display =
                                "none";
                    }

                    return;
                }


                const totalSeconds =
                        Math.floor(
                                remaining / 1000
                                );

                const minutes =
                        Math.floor(
                                totalSeconds / 60
                                );

                const seconds =
                        totalSeconds % 60;


                countdown.innerHTML =
                        "Time Remaining: "
                        + minutes
                        + ":"
                        + String(seconds)
                        .padStart(2, "0");

            }


            updateCountdown();


            if (expiryTime > 0) {

                setInterval(
                        updateCountdown,
                        1000
                        );
            }

        </script>

    </body>

</html>