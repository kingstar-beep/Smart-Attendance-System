<%-- 
    Document   : loginSuccess
    Created on : Apr 19, 2026, 4:36:23 PM
    Author     : KINGSTAR
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="model.Student" %> 

<%
  //  Student student = (Student) session.getAttribute("student");

 //   String name = (student != null) ? student.getName() : "Unknown";
 //   String studentId = (student != null) ? student.getStudentId() : "N/A";

  //  String date = new SimpleDateFormat("dd MMM yyyy HH:mm").format(new Date());
%>

<%
Student student = (Student) session.getAttribute("student");

String name = "Unknown";
String studentId = "N/A";

if(student != null){
    name = student.getName();
    studentId = student.getStudentId();
}

String date = new SimpleDateFormat("dd MMM yyyy HH:mm").format(new Date());

String loginTime =
new SimpleDateFormat("dd MMM yyyy HH:mm")
.format(new Date());
%>

<!DOCTYPE html>

<html>

<head>

<title>Student Dashboard</title>

<style>

body{
    margin:0;
    font-family:Arial,sans-serif;
    background:#f4f6f9;
}

.header{
    background:#002147;
    color:white;
    padding:20px;
    text-align:center;
}

.dashboard{
    width:900px;
    margin:30px auto;
}

.profile-card{
    background:white;
    padding:25px;
    border-radius:10px;
    box-shadow:0 0 10px rgba(0,0,0,0.1);

    display:flex;
    align-items:center;
}

.profile-img{
    width:150px;
    height:150px;
    border-radius:50%;
    border:4px solid #002147;
}

.profile-info{
    margin-left:30px;
}

.profile-info h2{
    margin:0;
    color:#002147;
}

.card-container{
    display:flex;
    justify-content:space-between;
    margin-top:25px;
}

.card{
    width:30%;
    background:white;
    padding:20px;
    border-radius:10px;
    box-shadow:0 0 10px rgba(0,0,0,0.1);
    text-align:center;
}

.card h3{
    color:#002147;
}

.btn{
    display:block;
    width:250px;
    margin:30px auto;
    padding:15px;
    text-align:center;
    text-decoration:none;
    background:#F5B800;
    color:black;
    font-weight:bold;
    border-radius:8px;
}

.btn:hover{
    background:#d9a600;
}

.footer{
    text-align:center;
    margin-top:30px;
    color:#666;
}

</style>

</head>

<body>

<div class="header">

<h1>University of Greenwich</h1>

<p>Smart Attendance Management System</p>

</div>

<div class="dashboard">

<div class="profile-card">

<%
student = (model.Student)session.getAttribute("student");
%>

<img src="<%= student.getImagePath() %>"
     class="profile-img">

<div class="profile-info">

<h2><%= name %></h2>

<p>
<b>Student ID:</b>
<%= studentId %>
</p>

<p>
<b>University:</b>
University of Greenwich
</p>

<p>
<b>Login Time:</b>
<%= loginTime %>
</p>

</div>

</div>

<div class="card-container">

<div class="card">

<h3>Current Location</h3>

<p id="locationText">

Detecting...

</p>

</div>

<div class="card">

<h3>Authentication</h3>

<p style="color:green;">
Verified
</p>

</div>

<div class="card">

<h3>Attendance Status</h3>

<p>
Ready for Check-In
</p>

</div>

</div>

<a href="student.jsp"
   class="btn">

Proceed to Attendance

</a>

</div>

<div class="footer">

MSc Computing Project 2026

</div>

<script>

navigator.geolocation.getCurrentPosition(

function(position){

document.getElementById("locationText")
.innerHTML =

position.coords.latitude +
"<br>" +
position.coords.longitude;

},

function(){

document.getElementById("locationText")
.innerHTML =

"Location unavailable";

}

);

</script>

</body>

</html>