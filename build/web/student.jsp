<%-- 
    Document   : student
    Created on : Apr 9, 2026, 5:28:32 PM
    Author     : KINGSTAR
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Student Portal</title>
        <link rel="stylesheet" href="css/style.css">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <style>

        body{
            margin:0;
            font-family:Arial,sans-serif;
            background:#f4f6f9;
        }

        .header{
            background:#002147;
            color:white;
            text-align:center;
            padding:20px;
        }

        .container{
            width:900px;
            margin:30px auto;
        }

        .card{
            background:white;
            padding:25px;
            border-radius:10px;
            box-shadow:0 0 10px rgba(0,0,0,0.1);
        }

        h2{
            color:#002147;
        }

        video{
            width:100%;
            border-radius:10px;
            border:2px solid #ddd;
        }

        button{
            padding:12px 25px;
            background:#F5B800;
            border:none;
            font-weight:bold;
            cursor:pointer;
            border-radius:5px;
        }

        button:hover{
            background:#d9a600;
        }

        .status{
            margin-top:15px;
            color:green;
            font-weight:bold;
        }

        .features{
            margin-top:20px;
        }

        .features p{
            margin:6px 0;
        }

        .message{
            background:#e8f5e9;
            color:#2e7d32;
            padding:10px;
            border-radius:5px;
            margin-bottom:15px;
        }

    </style>
    <body>

        <div class="header">

            <h1>University of Greenwich</h1>

            <p>Smart Attendance Management System</p>

        </div>

        <div class="container">

            <div class="card">

                <h2>Attendance Verification Portal</h2>

                <%
                    String msg
                            = (String) request.getAttribute("message");

                    if (msg != null) {
                %>

                <div class="message">
                    <%= msg%>
                </div>

                <%
                    }
                %>

                <p>

                    <b>Session Code:</b>

                    <%= request.getAttribute("code")%>

                </p>

                <p class="status">

                    📍 GPS Location Verification Active

                </p>

                <form action="MarkAttendanceServlet"
                      method="post">

                    <input type="hidden"
                           name="code"
                           value="<%= request.getAttribute("code")%>">

                    <input type="hidden"
                           id="lat"
                           name="lat">

                    <input type="hidden"
                           id="lng"
                           name="lng">

                    <input type="hidden"
                           id="imageData"
                           name="imageData">

                    <h3>Camera Verification</h3>

                    <video id="video"
                           autoplay></video>

                    <br><br>

                    <button class="captureimage" type="button"
                            onclick="captureImage()">

                        Capture Photo

                    </button>

                    <br><br>

                    <button class="submitattendance"
                            type="submit"
                            id="submitBtn"
                            style="display:none;">

                        Submit Attendance

                    </button>

                </form>

                <div class="features">

                    <h3>Verification Layers</h3>

                    <p>✔ Student Login Authentication</p>

                    <p>✔ QR Code Validation</p>

                    <p>✔ GPS Location Verification</p>

                    <p>✔ Image Confirmation</p>

                </div>

            </div>

        </div>      
        <script>

            navigator.geolocation.getCurrentPosition(
                    function (position) {

                        document.getElementById("lat").value =
                                position.coords.latitude;

                        document.getElementById("lng").value =
                                position.coords.longitude;

                    }

            );

            navigator.mediaDevices.getUserMedia({
                video: true
            })

                    .then(function (stream) {

                        document.getElementById("video")
                                .srcObject = stream;

                    });

            function captureImage() {

                const canvas = document.createElement("canvas");
                const video = document.getElementById("video");

                if (!video.videoWidth || !video.videoHeight) {

                    alert("Camera is not ready. Please wait a moment and try again.");
                    return;
                }

                canvas.width = video.videoWidth;
                canvas.height = video.videoHeight;

                const context = canvas.getContext("2d");

                context.drawImage(
                        video,
                        0,
                        0,
                        canvas.width,
                        canvas.height
                        );

                const imageData =
                        canvas.toDataURL("image/png");

                document.getElementById("imageData").value =
                        imageData;

                // Photo has now been successfully captured
                document.getElementById("submitBtn").style.display =
                        "inline-block";

                alert("Photo captured successfully. You can now submit your attendance.");
            }

        </script>          
    </body>
</html>
