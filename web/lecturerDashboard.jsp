<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Lecturer"%>
<%@page import="model.Attendance"%>
<%@page import="model.Session"%>
<%@page import="java.util.List"%>

<%
    Lecturer lecturer
            = (Lecturer) session.getAttribute("lecturer");

    List<Attendance> attendanceList
            = (List<Attendance>) request.getAttribute("attendanceList");

    List<Session> sessionList
            = (List<Session>) request.getAttribute("sessionList");

    if (attendanceList == null) {
        attendanceList = new java.util.ArrayList<Attendance>();
    }

    if (sessionList == null) {
        sessionList = new java.util.ArrayList<Session>();
    }
%>

<!DOCTYPE html>

<html>

    <head>

        <title>Lecturer Dashboard</title>

        <style>

            body {
                margin: 0;
                font-family: Arial, sans-serif;
                background: #f4f6f9;
                color: #333;
            }

            .header {
                background: #002147;
                color: white;
                padding: 20px;
                text-align: center;
            }

            .header h1 {
                margin: 0;
            }

            .topbar {
                background: white;
                padding: 15px 30px;
                display: flex;
                justify-content: space-between;
                align-items: center;
                box-shadow: 0 2px 5px #ddd;
            }

            .logout {
                background: #c62828;
                color: white;
                padding: 9px 18px;
                text-decoration: none;
                border-radius: 5px;
                font-weight: bold;
            }

            .container {
                width: 95%;
                max-width: 1200px;
                margin: 25px auto;
            }

            .section {
                background: white;
                padding: 25px;
                margin-bottom: 25px;
                border-radius: 10px;
                box-shadow: 0 2px 8px #ddd;
            }

            .section h2 {
                color: #002147;
                margin-top: 0;
            }

            label {
                display: block;
                font-weight: bold;
                margin-top: 12px;
            }

            input {
                width: 100%;
                padding: 11px;
                margin-top: 6px;
                box-sizing: border-box;
                border: 1px solid #ccc;
                border-radius: 5px;
            }

            button {
                margin-top: 18px;
                padding: 12px 20px;
                background: #F5B800;
                border: none;
                border-radius: 5px;
                font-weight: bold;
                cursor: pointer;
            }

            button:hover {
                background: #d9a600;
            }

            .cards {
                display: flex;
                gap: 20px;
                flex-wrap: wrap;
                margin-bottom: 25px;
            }

            .card {
                flex: 1;
                min-width: 180px;
                background: white;
                padding: 20px;
                border-radius: 10px;
                text-align: center;
                box-shadow: 0 2px 8px #ddd;
            }

            .card h2 {
                color: #002147;
                font-size: 28px;
                margin: 5px;
            }

            .session-card {
                border: 1px solid #ddd;
                padding: 18px;
                border-radius: 8px;
                margin-top: 12px;
                background: #fafafa;
            }

            .active {
                color: green;
                font-weight: bold;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 15px;
            }

            th {
                background: #002147;
                color: white;
                padding: 12px;
            }

            td {
                padding: 10px;
                border: 1px solid #ddd;
                text-align: center;
            }

            .student-image {
                width: 60px;
                height: 60px;
                object-fit: cover;
                border-radius: 7px;
            }

            .error {
                background: #ffebee;
                color: #c62828;
                padding: 12px;
                border-radius: 5px;
                margin-bottom: 15px;
            }

        </style>

    </head>

    <body>

        <div class="header">

            <h1>University of Greenwich</h1>

            <p>Secure Smart Attendance Management System</p>

        </div>


        <div class="topbar">

            <div>

                <strong>
                    Lecturer:
                    <%= lecturer != null
                            ? lecturer.getName()
                            : "Lecturer"%>
                </strong>

                <br>

                <small>
                    Lecturer ID:
                    <%= lecturer != null
                            ? lecturer.getLecturerId()
                            : ""%>
                </small>

            </div>

            <a href="LecturerLogoutServlet"
               class="logout">
                Logout
            </a>

        </div>


        <div class="container">


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


            <!-- DASHBOARD METRICS -->

            <div class="cards">

                <div class="card">

                    <h2>
                        <%= sessionList.size()%>
                    </h2>

                    <p>Attendance Sessions</p>

                </div>


                <div class="card">

                    <h2>
                        <%= attendanceList.size()%>
                    </h2>

                    <p>Total Attendance</p>

                </div>


                <div class="card">

                    <h2>QR</h2>

                    <p>Attendance Authentication</p>

                </div>


                <div class="card">

                    <h2>GPS</h2>

                    <p>Location Verification</p>

                </div>

            </div>


            <!-- CREATE ATTENDANCE SESSION -->

            <div class="section">

                <h2>Create Attendance Session</h2>

                <p>
                    Create a new attendance session for the current lecture.
                    Each generated QR code represents a separate attendance
                    event.
                </p>


                <form action="CreateSessionServlet"
                      method="post">


                    <label>
                        Course Name
                    </label>

                    <input type="text"
                           name="course"
                           placeholder="e.g. COMP1804"
                           required>


                    <label>
                        Attendance Radius (Metres)
                    </label>

                    <input type="number"
                           name="radius"
                           value="100"
                           min="1"
                           required>

                    <label>
                        Attendance Session Duration
                    </label>

                    <select name="duration"
                            required
                            style="width:100%;
                            padding:11px;
                            margin-top:6px;
                            border:1px solid #ccc;
                            border-radius:5px;">

                        <option value="5">
                            5 minutes
                        </option>

                        <option value="10">
                            10 minutes
                        </option>

                        <option value="15">
                            15 minutes
                        </option>

                        <option value="30" selected>
                            30 minutes
                        </option>

                        <option value="45">
                            45 minutes
                        </option>

                        <option value="60">
                            60 minutes
                        </option>

                        <option value="0">
                            No automatic expiry
                        </option>

                    </select>

                    <input type="hidden"
                           id="lat"
                           name="lat">


                    <input type="hidden"
                           id="lng"
                           name="lng">


                    <button type="submit">

                        Generate QR Attendance Session

                    </button>

                </form>

                <!-- <p id="locationStatus">
                     📍 Detecting lecturer location...
                 </p> -->

                <div id="locationStatus">
                    📍 Detecting lecturer location...
                </div>

                <div id="lecturerCoordinates"
                     style="margin-top:8px; font-size:14px; color:#555;">
                </div>

                <a id="mapLink"
                   href="#"
                   target="_blank"
                   style="display:none; margin-top:8px;">
                    View current location on map
                </a>

            </div>


            <!-- ATTENDANCE SESSIONS -->

            <div class="section">

                <h2>Lecturer Attendance Sessions</h2>

                <%
                    if (sessionList.isEmpty()) {
                %>

                <p>
                    No attendance sessions have been created yet.
                </p>

                <%
                } else {

                    for (Session s : sessionList) {
                %>

                <div class="session-card">

                    <h3>
                        <%= s.getCourse() != null
                                ? s.getCourse()
                                : "Course not specified"%>
                    </h3>

                    <p>
                        <strong>Session:</strong>
                        <%= s.getSessionCode()%>
                    </p>

                    <p>
                        <strong>Radius:</strong>
                        <%= s.getRadius()%> metres
                    </p>

                    <p class="active">
                        ● <%= s.getStatus() != null
                                ? s.getStatus()
                                : "ACTIVE"%>
                    </p>

                    <a href="GenerateQRServlet?code=<%= s.getSessionCode()%>"
                       target="_blank">

                        View QR Code

                    </a>

                </div>

                <%
                        }
                    }
                %>

            </div>

            <!-- Live Attendance -->
            <div class="section">

                <h2>Live Attendance</h2>

                <p>
                    Select an attendance session to view students
                    who have recorded attendance.
                </p>

                <select id="liveSessionSelect"
                        style="width:100%; padding:11px;">

                    <option value="">
                        Select Attendance Session
                    </option>

                    <%
                        for (Session s : sessionList) {
                    %>

                    <option value="<%= s.getSessionCode()%>">

                        <%= s.getCourse()%>
                        -
                        <%= s.getSessionCode()%>

                    </option>

                    <%
                        }
                    %>

                </select>


                <div style="margin-top:20px;">

                    <strong>
                        Students Present:
                    </strong>

                    <span id="presentCount">
                        0
                    </span>

                </div>


                <table>

                    <thead>

                        <tr>

                            <th>Photo</th>

                            <th>Student ID</th>

                            <th>Student Name</th>

                            <th>Attendance Time</th>

                            <th>Status</th>

                        </tr>

                    </thead>

                    <tbody id="liveAttendanceBody">

                        <tr>

                            <td colspan="5">
                                Select an attendance session.
                            </td>

                        </tr>

                    </tbody>

                </table>

            </div>

            <!-- ATTENDANCE ANALYTICS -->

            <div class="section">

                <h2>Attendance Analytics</h2>

                <p>
                    Attendance analytics provide a session-level overview
                    of student attendance recorded through each QR attendance
                    event.
                </p>

                <div class="cards">

                    <div class="card">

                        <h2 id="analyticsTotalAttendance">
                            0
                        </h2>

                        <p>Total Attendance Records</p>

                    </div>


                    <div class="card">

                        <h2 id="analyticsTotalSessions">
                            0
                        </h2>

                        <p>Sessions with Attendance</p>

                    </div>

                </div>


                <div style="margin-top:25px;">

                    <h3>Attendance by Session</h3>

                    <table>

                        <thead>

                            <tr>

                                <th>Course</th>

                                <th>Session</th>

                                <th>Students Present</th>

                                <th>First Attendance</th>

                                <th>Latest Attendance</th>

                            </tr>

                        </thead>

                        <tbody id="analyticsTableBody">

                            <tr>

                                <td colspan="5">
                                    Loading attendance analytics...
                                </td>

                            </tr>

                        </tbody>

                    </table>

                </div>


                <div style="margin-top:30px;">

                    <h3>Attendance Distribution</h3>

                    <div id="analyticsChart">

                        Loading chart...

                    </div>

                </div>

            </div>


            <!-- ATTENDANCE RECORDS -->

            <div class="section">

                <h2>Student Attendance Records</h2>

                <table>

                    <tr>

                        <th>Student ID</th>

                        <th>Student Name</th>

                        <th>Course</th>

                        <th>Session</th>

                        <th>Attendance Time</th>

                        <th>Image</th>

                        <th>Status</th>

                    </tr>


                    <%
                        if (attendanceList.isEmpty()) {
                    %>

                    <tr>

                        <td colspan="7">
                            No attendance records available.
                        </td>

                    </tr>

                    <%
                    } else {

                        for (Attendance a : attendanceList) {
                    %>

                    <tr>

                        <td>
                            <%= a.getStudentId()%>
                        </td>

                        <td>
                            <%= a.getStudentName() != null
                                    ? a.getStudentName()
                                    : "Unknown"%>
                        </td>

                        <td>
                            <%= a.getCourse() != null
                                    ? a.getCourse()
                                    : "N/A"%>
                        </td>

                        <td>
                            <%= a.getSessionCode()%>
                        </td>

                        <td>
                            <%= a.getCheckInTime()%>
                        </td>

                        <td>

                            <%
                                if (a.getImagePath() != null
                                        && !a.getImagePath().isEmpty()) {
                            %>

                            <img class="student-image"
                                 src="ImageServlet?path=<%= a.getImagePath()%>">

                            <%
                            } else {
                            %>

                            No image

                            <%
                                }
                            %>

                        </td>

                        <td>

                            <span style="color:green;font-weight:bold;">
                                Verified
                            </span>

                        </td>

                    </tr>

                    <%
                            }
                        }
                    %>

                </table>

            </div>

        </div>


        <script>

            navigator.geolocation.getCurrentPosition(
                    function (position) {

                        const latitude =
                                position.coords.latitude;

                        const longitude =
                                position.coords.longitude;

                        // Store values for session creation
                        document.getElementById("lat").value =
                                latitude;

                        document.getElementById("lng").value =
                                longitude;

                        // Display lecturer location
                        document.getElementById("locationStatus").innerHTML =
                                "📍 Lecturer location captured successfully.";

                        document.getElementById("lecturerCoordinates").innerHTML =
                                "<strong>Current location:</strong><br>" +
                                "Latitude: " + latitude.toFixed(6) + "<br>" +
                                "Longitude: " + longitude.toFixed(6);

                        // Google Maps link
                        const mapLink =
                                "https://www.google.com/maps?q="
                                + latitude
                                + ","
                                + longitude;

                        const link =
                                document.getElementById("mapLink");

                        link.href = mapLink;
                        link.style.display = "inline-block";

                    },
                    function (error) {

                        document.getElementById("locationStatus").innerHTML =
                                "⚠ Location access denied. Please enable location services.";

                        document.getElementById("lecturerCoordinates").innerHTML =
                                "Current lecturer location could not be detected.";

                        document.getElementById("mapLink").style.display =
                                "none";
                    },
                    {
                        enableHighAccuracy: true,
                        timeout: 10000,
                        maximumAge: 0
                    }

            );

        </script>

        <script>

            let liveAttendanceTimer = null;

            function loadLiveAttendance() {

                const select =
                        document.getElementById("liveSessionSelect");

                const sessionCode =
                        select.value;

                const tbody =
                        document.getElementById(
                                "liveAttendanceBody"
                                );

                const count =
                        document.getElementById(
                                "presentCount"
                                );

                if (!sessionCode) {

                    count.innerHTML = "0";

                    tbody.innerHTML =
                            "<tr>" +
                            "<td colspan='5'>" +
                            "Select an attendance session." +
                            "</td>" +
                            "</tr>";

                    return;
                }

                fetch(
                        "LiveAttendanceServlet?sessionCode="
                        + encodeURIComponent(sessionCode),
                        {
                            method: "GET",
                            cache: "no-store"
                        }
                )
                        .then(response => {

                            if (!response.ok) {
                                throw new Error(
                                        "Unable to load attendance."
                                        );
                            }

                            return response.json();

                        })
                        .then(records => {

                            count.innerHTML =
                                    records.length;

                            if (records.length === 0) {

                                tbody.innerHTML =
                                        "<tr>" +
                                        "<td colspan='5'>" +
                                        "No students have recorded attendance yet." +
                                        "</td>" +
                                        "</tr>";

                                return;
                            }

                            let html = "";

                            records.forEach(record => {

                                let photoHtml;

                                if (record.imagePath
                                        && record.imagePath.trim() !== "") {

                                    photoHtml =
                                            "<img class='student-image' " +
                                            "src='ImageServlet?path=" +
                                            encodeURIComponent(record.imagePath) +
                                            "' " +
                                            "alt='Student attendance photo'>";

                                } else {

                                    photoHtml =
                                            "<span>No image</span>";
                                }


                                html +=
                                        "<tr>" +
                                        "<td>" +
                                        photoHtml +
                                        "</td>" +
                                        "<td>" +
                                        escapeHtml(record.studentId) +
                                        "</td>" +
                                        "<td>" +
                                        escapeHtml(record.studentName) +
                                        "</td>" +
                                        "<td>" +
                                        escapeHtml(record.checkInTime) +
                                        "</td>" +
                                        "<td>" +
                                        "<strong style='color:green;'>✓ Present</strong>" +
                                        "</td>" +
                                        "</tr>";
                            });

                            tbody.innerHTML = html;

                        })
                        .catch(error => {

                            console.error(error);

                            tbody.innerHTML =
                                    "<tr>" +
                                    "<td colspan='5'>" +
                                    "Unable to load live attendance." +
                                    "</td>" +
                                    "</tr>";

                        });
            }


            function escapeHtml(value) {

                if (!value) {
                    return "";
                }

                return String(value)
                        .replace(/&/g, "&amp;")
                        .replace(/</g, "&lt;")
                        .replace(/>/g, "&gt;")
                        .replace(/"/g, "&quot;")
                        .replace(/'/g, "&#039;");
            }


            document
                    .getElementById("liveSessionSelect")
                    .addEventListener(
                            "change",
                            function () {

                                loadLiveAttendance();

                                if (liveAttendanceTimer) {
                                    clearInterval(
                                            liveAttendanceTimer
                                            );
                                }

                                liveAttendanceTimer =
                                        setInterval(
                                                loadLiveAttendance,
                                                5000
                                                );
                            }
                    );

        </script>

        <script>

            function loadAttendanceAnalytics() {

                const tableBody =
                        document.getElementById(
                                "analyticsTableBody"
                                );

                const totalAttendance =
                        document.getElementById(
                                "analyticsTotalAttendance"
                                );

                const totalSessions =
                        document.getElementById(
                                "analyticsTotalSessions"
                                );

                const chart =
                        document.getElementById(
                                "analyticsChart"
                                );


                fetch(
                        "AttendanceAnalyticsServlet",
                        {
                            method: "GET",
                            cache: "no-store"
                        }
                )
                        .then(response => {

                            if (!response.ok) {

                                throw new Error(
                                        "Unable to load attendance analytics."
                                        );
                            }

                            return response.json();

                        })
                        .then(data => {

                            /*
                             * Total attendance records
                             */
                            let attendanceCount = 0;

                            data.forEach(session => {

                                attendanceCount +=
                                        session.totalStudents;

                            });


                            totalAttendance.innerHTML =
                                    attendanceCount;

                            totalSessions.innerHTML =
                                    data.length;


                            /*
                             * No attendance
                             */
                            if (data.length === 0) {

                                tableBody.innerHTML =
                                        "<tr>" +
                                        "<td colspan='5'>" +
                                        "No attendance data available yet." +
                                        "</td>" +
                                        "</tr>";

                                chart.innerHTML =
                                        "<p>No attendance data available.</p>";

                                return;
                            }


                            /*
                             * Build analytics table
                             */
                            let tableHTML = "";


                            data.forEach(session => {

                                tableHTML +=
                                        "<tr>" +
                                        "<td>" +
                                        escapeHtml(
                                                session.course
                                                ) +
                                        "</td>" +
                                        "<td>" +
                                        escapeHtml(
                                                session.sessionCode
                                                ) +
                                        "</td>" +
                                        "<td>" +
                                        "<strong>" +
                                        session.totalStudents +
                                        "</strong>" +
                                        "</td>" +
                                        "<td>" +
                                        escapeHtml(
                                                session.firstAttendance
                                                ) +
                                        "</td>" +
                                        "<td>" +
                                        escapeHtml(
                                                session.latestAttendance
                                                ) +
                                        "</td>" +
                                        "</tr>";

                            });


                            tableBody.innerHTML =
                                    tableHTML;


                            /*
                             * Build simple attendance chart
                             */
                            let chartHTML = "";


                            data.forEach(session => {

                                const width =
                                        Math.max(
                                                session.totalStudents * 35,
                                                5
                                                );

                                chartHTML +=
                                        "<div style='margin-bottom:18px;'>" +
                                        "<div style='margin-bottom:5px;'>" +
                                        "<strong>" +
                                        escapeHtml(
                                                session.course
                                                ) +
                                        "</strong> — " +
                                        escapeHtml(
                                                session.sessionCode
                                                ) +
                                        " (" +
                                        session.totalStudents +
                                        " students)" +
                                        "</div>" +
                                        "<div style='" +
                                        "height:24px;" +
                                        "width:" +
                                        width +
                                        "px;" +
                                        "max-width:100%;" +
                                        "background:#2563eb;" +
                                        "border-radius:5px;" +
                                        "'></div>" +
                                        "</div>";

                            });


                            chart.innerHTML =
                                    chartHTML;

                        })
                        .catch(error => {

                            console.error(error);

                            tableBody.innerHTML =
                                    "<tr>" +
                                    "<td colspan='5'>" +
                                    "Unable to load attendance analytics." +
                                    "</td>" +
                                    "</tr>";

                            chart.innerHTML =
                                    "<p>Unable to load attendance analytics.</p>";

                        });
            }


            /*
             * Load analytics when dashboard opens.
             */
            document.addEventListener(
                    "DOMContentLoaded",
                    function () {

                        loadAttendanceAnalytics();

                    }
            );

        </script>

    </body>

</html>