# Secure Multi-Factor Student Attendance System

A Java EE web-based student attendance system designed to improve the
reliability of attendance recording by combining authenticated access,
QR-code attendance sessions, browser-based geographical validation,
real-time image capture, and database-backed attendance records.

> **Project name:** Secure Multi-Factor Student Attendance System\
> **Repository/project reference:** Smart Attendance System

## Project Overview

### Purpose

The system was developed as a functional web prototype for a more
controlled student attendance process. It combines:

-   Student authentication
-   Lecturer authentication
-   QR-code-based attendance sessions
-   Browser-based geographical location validation
-   Real-time image capture
-   MySQL attendance recording
-   Lecturer attendance management
-   Session-specific live attendance

### Problem It Addresses

The project was motivated by limitations in conventional attendance
processes, including:

-   Students attending remotely while attendance is recorded on their
    behalf
-   Students signing attendance for other students
-   QR codes potentially being shared between students
-   Limited evidence that a student was physically present
-   The cost and complexity associated with dedicated biometric
    attendance hardware

Rather than relying solely on a QR code, the system combines several
pieces of evidence around an attendance event.

### Target Users

Two user roles were implemented:

1.  **Student**
2.  **Lecturer**

------------------------------------------------------------------------

## Technology Stack

### Backend

-   Java
-   Java EE
-   Java Servlets
-   JSP (JavaServer Pages)
-   JDBC
-   JavaBeans/model classes
-   HTTP sessions (`HttpSession`)

### Frontend

-   HTML5
-   CSS3
-   JavaScript
-   JSP
-   Browser Geolocation API
-   Browser camera/webcam functionality

### Database

-   MySQL
-   SQL
-   JDBC

### Application Server

-   GlassFish

### QR / Browser APIs

-   ZXing --- QR-code generation
-   Browser Geolocation API --- geographical location validation
-   Browser camera/webcam API --- attendance image capture

> The project uses the **browser Geolocation API**, not the Google Maps
> Geolocation API.

### Development Tools

-   NetBeans IDE
-   MySQL/database tools
-   Google Chrome
-   Microsoft Edge
-   Mozilla Firefox

------------------------------------------------------------------------

## System Architecture

The application follows a Java web application structure separating
presentation, request handling, database access and model objects.

``` text
Student / Lecturer
        |
        v
   JSP / Web UI
        |
        v
Java Servlets / Controllers
        |
        v
     DAO Layer
        |
        v
   MySQL Database
```

The application source is organised around Java packages, JSP pages and
supporting web resources.

### Main Packages / Components

-   `model`
-   DAO / data-access classes
-   Servlets / controllers
-   JSP pages
-   Supporting web resources

### Principal Servlets

The main servlet components worked on during development include:

-   `LecturerLoginServlet`
-   `LecturerDashboardServlet`
-   `LecturerLogoutServlet`
-   `CreateSessionServlet`
-   `LiveAttendanceServlet`
-   `MarkAttendanceServlet`

### DAO Components

-   `AttendanceDAO`
-   `SessionDAO`
-   `LecturerDAO`

For example, `AttendanceDAO.hasAttendance()` checks attendance using:

``` sql
SELECT COUNT(*)
FROM attendance
WHERE session_code = ?
AND student_id = ?
```

This implements the project's session-based duplicate attendance rule.

### Main Models / Entities

-   `Student`
-   `Lecturer`
-   `Session`
-   `Attendance`

### Main JSP / Web Pages

-   `lecturerLogin.jsp`
-   `lecturerDashboard.jsp`
-   `qr-display.jsp`
-   Student attendance/login-related JSP pages
-   Map/attendance-related pages used within the workflow

------------------------------------------------------------------------

# Main Features

## 1. Student Authentication

Students authenticate before attempting to record attendance.

This prevents the attendance process from relying solely on possession
of a QR code.

## 2. Lecturer Authentication

Lecturers have a separate login workflow.

After successful authentication:

``` text
Lecturer
   |
   v
LecturerLoginServlet
   |
   v
HttpSession
   |
   v
LecturerDashboardServlet
   |
   v
lecturerDashboard.jsp
```

Unauthorised direct access to the lecturer dashboard redirects the user
to the lecturer login page.

### Lecturer Logout

`LecturerLogoutServlet` invalidates the HTTP session and redirects the
lecturer to the login page.

## 3. Lecturer Dashboard

The lecturer dashboard provides:

-   Lecturer identity
-   Logout
-   Attendance-session creation
-   Lecturer's sessions
-   Course/session information
-   QR-code access
-   Attendance records
-   Live attendance

## 4. Attendance Session Creation

A lecturer can create an attendance session using information including:

-   Course
-   Attendance radius
-   Lecturer's current location

The system generates a unique session code.

A session records information including:

-   Session code
-   Lecturer ID
-   Course
-   Latitude
-   Longitude
-   Radius
-   Expiry information
-   Status

## 5. Dynamic / Unique QR Attendance Sessions

Each attendance session has its own unique session code and QR code.

The QR code represents a specific attendance event.

## 6. QR Code Access

Students access the QR code associated with the lecturer's attendance
session.

The system retrieves the corresponding attendance session.

QR functionality was implemented using **ZXing**.

## 7. Geographical Location Validation

The student's browser requests their geographical location.

The system obtains:

-   Latitude
-   Longitude

The student's location is compared with the permitted geographical area
defined for the attendance session.

``` text
Student Location
       |
       v
Compare with Session Location
       |
       v
Within Radius?
   /          \
 Yes           No
  |             |
Continue       Reject
```

Students within the permitted radius can continue. Students outside the
permitted radius are rejected.

## 8. Image Capture

The system uses browser camera/webcam functionality to capture an image
during attendance.

The captured image is stored as part of the attendance record.

**Important:** the image is treated as supplementary attendance
evidence. The project does **not** implement facial recognition or
biometric identification.

## 9. Attendance Recording

After the required verification stages are completed, the attendance
record is saved to MySQL.

The record associates the attendance with:

-   Student
-   Session
-   Check-in time
-   Captured image

## 10. Duplicate Attendance Prevention

The system prevents the same student from recording attendance more than
once for the same session.

The implemented rule is:

``` text
session_code + student_id
```

Therefore:

``` text
Student + Session A  → Attendance recorded
Student + Session A  → Second attempt rejected

Student + Session B  → New attendance event permitted
```

A new session/QR code represents a new attendance event.

## 11. Lecturer Attendance Records

Lecturers can retrieve attendance records associated with their
sessions.

The lecturer can therefore see which students recorded attendance for a
particular attendance session.

## 12. Live Attendance

The lecturer dashboard includes a Live Attendance section.

The lecturer selects an attendance session and the system retrieves
attendance for that particular session.

The implemented workflow uses periodic polling, with the live-attendance
interface developed around a five-second polling approach.

------------------------------------------------------------------------

# Attendance Workflow

The implemented attendance workflow is:

``` text
Student Login
     |
     v
Access / Scan QR
     |
     v
Validate Session Code
     |
     v
Check Existing Attendance
     |
     +----------------------+
     |                      |
 Already Recorded?          No
     |                      |
    Yes                     v
     |                Capture Location
   Reject                   |
                            v
                    Validate Location
                            |
                            v
                      Capture Image
                            |
                            v
                     Save Attendance
                            |
                            v
                         Success
```

### Core Attendance Rule

``` text
One student
     +
One session_code
     =
One attendance record
```

A new session code allows the same student to record attendance for a
new attendance event.

------------------------------------------------------------------------

# User Roles

## Student

A student can:

-   Log in
-   Access an attendance session through its QR code
-   Provide browser location information
-   Complete the image-capture stage
-   Submit attendance
-   Receive the attendance result
-   Be prevented from recording twice for the same attendance session

## Lecturer

A lecturer can:

-   Log in
-   Access the lecturer dashboard
-   View lecturer identity/session information
-   Capture current location
-   Create attendance sessions
-   Specify course/session information
-   Specify attendance radius
-   Generate a unique QR attendance session
-   View QR codes
-   View attendance records
-   Select sessions and view live attendance
-   Log out

------------------------------------------------------------------------

# Database Design

The main database entities discussed and implemented are:

## `students`

Important fields include:

-   `id`
-   `student_id`
-   `password`
-   `name`
-   `image_path`

## `lecturers`

Important fields include:

-   `id`
-   `lecturer_id`
-   `name`
-   `password`

A unique constraint is applied to `lecturer_id`.

## `sessions`

The session structure includes:

-   `id`
-   `session_code`
-   `lecturer_id`
-   `course`
-   `latitude`
-   `longitude`
-   `radius`
-   `expiry_time`
-   `status`

Relationship:

``` text
Lecturer
   |
   v
Sessions
```

## `attendance`

Important fields include:

-   `id`
-   `session_code`
-   `check_in_time`
-   `image_path`
-   `created_at`
-   `student_id`

Relationship:

``` text
Student
   |
   v
Attendance
   ^
   |
Session
```

The application-level uniqueness rule is:

``` text
session_code + student_id
```

A foreign-key relationship was also established between:

``` text
sessions.lecturer_id
        |
        v
lecturers.lecturer_id
```

with:

-   `ON UPDATE CASCADE`
-   `ON DELETE SET NULL`

------------------------------------------------------------------------

# Lecturer Dashboard and Reporting

The implemented lecturer dashboard includes:

-   Lecturer information
-   Attendance-session creation
-   Current lecturer location
-   Session list
-   Course
-   Session code
-   Attendance radius
-   Session status
-   QR-code access
-   Attendance records
-   Live attendance

### Live Attendance

A lecturer can select a session and retrieve the students who have
recorded attendance for that particular session.

This functionality was specifically developed to allow session-specific
attendance visibility.

------------------------------------------------------------------------

# Security and Verification

The project implements several authentication and verification stages.

### 1. Student Authentication

Students must authenticate before attendance.

### 2. Lecturer Authentication

Lecturer functions are protected behind lecturer login.

### 3. Lecturer Session Control

The lecturer dashboard checks for an authenticated lecturer session.

### 4. QR / Session Verification

Attendance is associated with a specific session code.

### 5. Location Validation

The student's location is checked against the permitted geographical
radius.

### 6. Duplicate Attendance Prevention

A student cannot record attendance twice for the same session.

### 7. Multiple Verification Stages

The system combines:

``` text
Authentication
      +
QR Session
      +
Location
      +
Image
      =
Attendance Evidence
```

------------------------------------------------------------------------

# Problems Encountered and Solutions

The project involved substantial practical debugging and incremental
development.

## HTTP 405 Errors

Some servlet requests initially produced HTTP 405 Method Not Allowed
errors.

These were investigated through servlet request mappings and request
handling.

## HTTP 404 Errors

Several URL/servlet routing issues produced 404 errors.

These were resolved by checking servlet mappings and application URLs.

## HTTP 500 Errors

Server-side errors occurred during development.

The underlying Java, database and application issues were investigated
through server output and corrected.

## Image Upload `FileNotFoundException`

The image-capture process initially encountered a file-path problem.

The upload/storage path was corrected so captured images could be saved
properly.

## Incorrect Display Information

There were cases where displayed information did not correspond
correctly to the underlying record.

The database retrieval and display logic was corrected.

## Duplicate Attendance Insertion

The attendance DAO initially contained a duplicate execution problem.

The duplicate execution was removed so that the attendance insert occurs
once.

## Duplicate Attendance Logic

The final attendance model uses:

``` text
session_code + student_id
```

rather than simply `student_id`.

This allows:

``` text
Student 1515
Session A → attendance recorded
Session B → attendance recorded
```

while preventing:

``` text
Student 1515
Session A → first record
Session A → second record rejected
```

## Lecturer Authentication and Dashboard Access

Lecturer authentication and session protection were implemented so that
the lecturer dashboard is not publicly accessible.

## Live Attendance Loading

The live attendance servlet required correction during development,
including a missing:

``` java
import model.Session;
```

in `LiveAttendanceServlet.java`.

The functionality was then developed around session-specific attendance
retrieval.

## Browser Differences

Testing across Chrome, Firefox and Edge revealed some browser-specific
behaviour around camera/location permissions and JavaScript execution.

------------------------------------------------------------------------

# Testing

Testing was performed throughout development.

## Functional Testing

The following functions were tested:

-   Student login
-   Lecturer login
-   Lecturer logout
-   Session creation
-   QR generation
-   QR access
-   Location validation
-   Outside-radius rejection
-   Image capture
-   Attendance recording
-   Duplicate attendance prevention
-   Lecturer attendance retrieval
-   Live attendance

## Integration Testing

Important integration paths included:

``` text
Lecturer Login
      ↓
Dashboard
      ↓
Session Creation
      ↓
QR Generation
      ↓
QR / Student Authentication
      ↓
Geolocation
      ↓
Image Capture
      ↓
Attendance
      ↓
MySQL
      ↓
Lecturer Dashboard
```

## Multi-Browser Testing

The application was tested using:

-   Google Chrome
-   Mozilla Firefox
-   Microsoft Edge

## Multiple-Student Testing

Testing included student records such as:

-   1515
-   1516
-   1517
-   1518
-   1519
-   1520
-   1521
-   1522

Testing demonstrated that different students could record attendance
against a session and that a student could record attendance again when
a new QR/session code was generated.

## Prototype Performance Observations

Controlled prototype observations recorded approximately:

  Operation                 Observed Time
  --------------------- -----------------
  Login                   Under 2 seconds
  QR generation            Under 1 second
  Attendance              Under 3 seconds
  Database operations      Under 1 second

These are development/prototype observations rather than production
performance benchmarks.

------------------------------------------------------------------------

# Current Status

## Completed

Based on the project development record, the following components were
implemented:

-   Java EE web application
-   Student authentication
-   Lecturer authentication
-   Lecturer logout
-   Lecturer dashboard
-   Attendance session creation
-   Unique session codes
-   QR-code generation
-   QR attendance access
-   Browser geolocation
-   Location-radius validation
-   Browser image capture
-   Attendance recording
-   MySQL persistence
-   Duplicate attendance prevention
-   Lecturer-specific session records
-   Lecturer attendance records
-   Session-specific live attendance
-   Multi-browser testing
-   Database relationship between lecturers and sessions

## Core Workflow

The core attendance workflow was demonstrated as working:

``` text
Login
  ↓
QR
  ↓
Session Validation
  ↓
Location
  ↓
Image
  ↓
Attendance
  ↓
Database
  ↓
Lecturer Records
```

The session-based attendance model was also demonstrated through
testing.

------------------------------------------------------------------------

# My Contribution

My contribution included hands-on development of the Smart Attendance
System as a Java EE web application.

Areas worked on included:

-   Project setup
-   Java EE development
-   JSP pages
-   Servlets
-   Database integration
-   MySQL database design
-   JDBC / DAO implementation
-   Student attendance workflow
-   Lecturer authentication
-   Lecturer dashboard
-   Attendance session generation
-   QR-code integration
-   Browser geolocation integration
-   Image capture
-   Duplicate attendance logic
-   Live attendance functionality
-   Debugging
-   Cross-browser testing
-   Integration of application components
-   Technical/dissertation documentation and evaluation

### Project Development Summary

Designed and developed a Java EE-based multi-factor student attendance
system integrating authenticated access, QR attendance sessions, browser
geolocation validation, image capture, MySQL persistence and
lecturer-side attendance management.

------------------------------------------------------------------------

# Demonstrable Skills

## Java / Backend

-   Java
-   Java Servlets
-   JSP
-   Java EE web development
-   HTTP session management
-   MVC-style web application development
-   HTTP request/response handling

## Database

-   MySQL
-   SQL
-   JDBC
-   DAO pattern
-   Relational database design
-   Foreign keys
-   Database CRUD operations
-   Data validation

## Frontend

-   HTML5
-   CSS3
-   JavaScript
-   JSP frontend integration
-   Browser APIs
-   Camera integration
-   Geolocation integration

## Application Integration

-   QR-code integration
-   ZXing
-   Browser Geolocation API
-   Webcam/camera API
-   JavaScript-to-backend communication
-   Database-to-dashboard integration

## Debugging

Practical debugging experience included:

-   HTTP 404
-   HTTP 405
-   HTTP 500
-   Java exceptions
-   File-path problems
-   Database insertion problems
-   Servlet mapping problems
-   JavaScript/browser issues
-   Cross-browser behaviour

## Software Engineering

-   Requirements analysis
-   System design
-   Database modelling
-   Incremental development
-   Functional testing
-   Integration testing
-   User workflow design
-   Security considerations
-   Technical documentation

------------------------------------------------------------------------

# Portfolio Evidence

## 1. Student Login

![Student
Login](https://github.com/user-attachments/assets/4625b8c2-9272-4d58-9c6b-950d099e16a3)

Student authentication interface.

## 2. Lecturer Login

![Lecturer
Login](https://github.com/user-attachments/assets/d70e022a-2cc6-4672-9003-527684fc951e)

Lecturer login page.

## 3. Lecturer Dashboard

![Lecturer
Dashboard](https://github.com/user-attachments/assets/76ffb9c0-dc71-4b6e-8fe6-ddac8e00325e)

Lecturer dashboard showing lecturer identity, session creation,
location, sessions and attendance information.

## 4. Lecturer Location Capture

![Lecturer Location
Capture](https://github.com/user-attachments/assets/c2a07fea-ddc5-44bf-84f6-1cdda9223852)

Lecturer location capture showing latitude and longitude.

## 5. QR Code Display

![QR Code
Display](https://github.com/user-attachments/assets/40b15daf-b9a5-4d26-aec6-14e66927bf2c)

Generated attendance QR code.

## 6. Student Attendance Workflow

### QR / Location

![Attendance
Location](https://github.com/user-attachments/assets/9014c715-db43-4d5d-a2d7-11633838d0ee)

### Image Capture

![Attendance Image
Capture](https://github.com/user-attachments/assets/4181f9b6-a165-410d-a176-7912ec08c8dd)

### Attendance

![Attendance
Submission](https://github.com/user-attachments/assets/2bd40302-5ace-400f-8c2e-238704a4d237)

## 7. Image Capture

![Image
Capture](https://github.com/user-attachments/assets/f694aa77-f542-4bab-b940-ddb204a183ad)

Camera/image-capture interface.

## 8. Successful Attendance

![Successful
Attendance](https://github.com/user-attachments/assets/170a1cf3-f2e0-45bf-98d0-774e3bd0a448)

Successful attendance confirmation.

## 9. Live Attendance

![Live
Attendance](https://github.com/user-attachments/assets/1c85f27d-ed22-440a-a78c-3b3a4a92d46c)

Lecturer selecting a session and viewing students who have recorded
attendance.

## 10. Additional Architecture Evidence

For a complete portfolio presentation, the repository can include:

-   ERD
-   System architecture diagram: 
-   Activity diagram
-   Sequence diagram
-   Database structure

<img width="1408" height="768" alt="architacture of the system" src="https://github.com/user-attachments/assets/8d4df607-7a85-4bc0-bf60-f394d3a992c1" />

<img width="787" height="866" alt="Attendance Verification Sequence" src="https://github.com/user-attachments/assets/897985c3-b9ba-4941-a5e9-99fb0f93f9cc" />

------------------------------------------------------------------------

# Project Structure

The project follows a typical NetBeans Java web application structure:

``` text
Smart-Attendance-System/
│
├── nbproject/
├── src/
│   ├── model/
│   ├── dao/
│   └── ...
│
├── web/
│   ├── JSP pages
│   ├── CSS
│   ├── JavaScript
│   └── supporting web resources
│
├── build.xml
└── README.md
```

Generated folders such as `build/` and `dist/` should not be committed
to the repository; they can be regenerated by the NetBeans build
process.

------------------------------------------------------------------------

# Project Status Summary

**Status:** Functional Java EE web prototype

The system demonstrates a multi-stage attendance workflow combining:

``` text
Student Authentication
        +
QR Attendance Session
        +
Geographical Validation
        +
Image Capture
        +
MySQL Persistence
        +
Lecturer Attendance Management
```

The project demonstrates practical experience in Java EE web
development, database integration, browser APIs, QR-code integration,
authentication/session management, debugging, testing and full
application workflow integration.
