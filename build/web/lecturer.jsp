<%-- 
    Document   : lecturer
    Created on : Apr 9, 2026, 5:27:51 PM
    Author     : KINGSTAR
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>

<title>Lecturer Dashboard</title>

<style>

body{
    margin:0;
    font-family:Arial, sans-serif;
    background:#f4f6f9;
}

.header{
    background:#002147;
    color:white;
    text-align:center;
    padding:20px;
}

.header h1{
    margin:0;
}

.container{
    width:600px;
    margin:40px auto;
    background:white;
    padding:30px;
    border-radius:10px;
    box-shadow:0 0 10px rgba(0,0,0,0.1);
}

input{
    width:100%;
    padding:10px;
    margin-top:5px;
    margin-bottom:15px;
}

button{
    width:100%;
    padding:12px;
    background:#F5B800;
    border:none;
    font-weight:bold;
    cursor:pointer;
}

button:hover{
    background:#d9a600;
}

.footer{
    text-align:center;
    margin-top:30px;
    color:#777;
}

.card-section{
    display:flex;
    justify-content:space-between;
    margin-top:30px;
}

.card{
    width:30%;
    background:#002147;
    color:white;
    padding:15px;
    text-align:center;
    border-radius:8px;
}

</style>

</head>

<body>

<div class="header">
    <h1>University of Greenwich</h1>
    <p>Smart Attendance Management System</p>
</div>

<div class="container">

<h2>Create Attendance Session</h2>

<form action="CreateSessionServlet" method="post">

    <label>Course Name</label>
    <input type="text" name="course" required>

    <label>Attendance Radius (Metres)</label>
    <input type="number" name="radius" value="100" required>

    <input type="hidden" id="lat" name="lat">
    <input type="hidden" id="lng" name="lng">

    <button type="submit">
        Generate QR Attendance Session
    </button>

</form>

<div class="card-section">

    <div class="card">
        QR Code
    </div>

    <div class="card">
        GPS Check
    </div>

    <div class="card">
        Image Verify
    </div>

</div>

</div>

<div class="footer">
    MSc Computing Project 2026
</div>

<script>

navigator.geolocation.getCurrentPosition(function(pos){

    // Hidden values submitted
    document.getElementById("lat").value =
        pos.coords.latitude;

    document.getElementById("lng").value =
        pos.coords.longitude;

    // Visible values shown to lecturer
    document.getElementById("latDisplay").value =
        pos.coords.latitude;

    document.getElementById("lngDisplay").value =
        pos.coords.longitude;

}, function(error){

    alert("Location access denied. Please enable GPS.");

});

</script>

</body>
</html>