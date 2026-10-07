**A. Project Overview**
**Exact project name:** **Secure Multi-Factor Student Attendance System**
The project has also been referred to more generally as the Smart Attendance System / Smart Attendance in the development and repository discussions.

**Purpose**
A Java EE web-based attendance system designed to improve the reliability of student attendance recording by combining several verification stages:
•	Student authentication
•	QR-code-based attendance sessions
•	Browser-based geographical location validation
•	Real-time image capture
•	Database recording of attendance
•	Lecturer authentication and attendance management
The system was developed as a functional web prototype for demonstrating a more controlled attendance-recording process.

**Problem it solves**
The project was motivated by problems observed with conventional attendance processes, including:
•	Students attending remotely while attendance was recorded on their behalf.
•	Students signing attendance for other students.
•	QR codes being potentially shared between students.
•	Traditional attendance processes providing limited evidence that the student was physically present.
•	The cost and complexity associated with dedicated biometric attendance hardware.
The system therefore attempts to provide multiple pieces of evidence around an attendance event, rather than relying solely on a QR code.

**Target users**
Two actual user roles were implemented:
1.	Student
2.	Lecturer

**B. Technology Stack**
**Programming languages**
•	Java
•	JavaScript
•	HTML
•	CSS
•	SQL

**Java / Java EE technologies**
•	Java Servlets
•	JSP (JavaServer Pages)
•	JDBC
•	Java EE web application architecture
•	JavaBeans/model classes
•	Servlet sessions using HttpSession

**Frontend technologies**
•	JSP
•	HTML5
•	CSS3
•	JavaScript
•	Browser Geolocation API
•	Browser camera/webcam functionality

**Database**
MySQL
JDBC is used to communicate between the Java application and MySQL database.

**Application server**
GlassFish
The project was developed and tested as a Java web application deployed through GlassFish.

**IDE / development tools**
The project was developed using:
•	NetBeans IDE
•	MySQL/database tools
•	Web browsers including:
o	Google Chrome
o	Microsoft Edge
o	Mozilla Firefox

**Libraries / APIs**
ZXing
Used for QR-code generation.

**Browser Geolocation API**
Used to obtain the student's geographical coordinates for location validation.
Importantly, the project uses the browser Geolocation API, not a Google Maps Geolocation API.

**Browser camera/webcam API**
Used for capturing an image during the attendance process.

**C. System Architecture**
The system follows a Java web application structure separating presentation, request handling/business processing, data access and model objects.
Project structure
The repository contains the typical NetBeans Java web application structure, including:
build/
dist/
nbproject/
src/
web/
build.xml
The application source is organised around Java packages, JSP pages and supporting web resources.

**Packages**
The application uses packages/components for areas including:
•	model
•	DAO/data-access classes
•	Servlets 

**Servlets/controllers**
Implemented servlet components during development include:
•	LecturerLoginServlet
•	LecturerDashboardServlet
•	LecturerLogoutServlet
•	CreateSessionServlet
•	LiveAttendanceServlet
•	MarkAttendanceServlet
Other application servlets exist in the project, but these are the principal controllers worked on during the attendance/lecturer functionality.
Services
The attendance processing logic is primarily handled through Servlets and DAO classes.

**DAOs**
The main DAO components include:
•	AttendanceDAO
•	SessionDAO
•	LecturerDAO
These handle database operations for the corresponding entities.
For example, AttendanceDAO.hasAttendance() checks:
SELECT COUNT(*)
FROM attendance
WHERE session_code = ?
AND student_id = ?
This implements the project's agreed attendance rule.

**Models/entities**
The main model classes are:
•	Student
•	Lecturer
•	Session
•	Attendance
The Lecturer model contains fields such as:
private String lecturerId;
private String name;

**JSP/web pages**
The main pages include:
•	lecturerLogin.jsp
•	lecturerDashboard.jsp
•	qr-display.jsp
•	Student attendance/login-related JSP pages
•	map/attendance-related pages from the application workflow
The lecturer dashboard became the main lecturer-facing page rather than relying on the earlier standalone lecturer.jsp.
Configuration
The project includes the normal NetBeans Java web application configuration and database connectivity configuration.

**D. Main Features / Modules**
**1. Student Authentication**
Students authenticate before attempting to record attendance.
This prevents the attendance process from relying solely on possession of a QR code.

**2. Lecturer Authentication**
Lecturers have their own login.
Successful lecturer authentication creates a lecturer session using HttpSession.
The lecturer is then redirected to the lecturer dashboard.
Unauthorised direct access to the lecturer dashboard redirects the user back to the lecturer login page.

**3. Lecturer Dashboard**
The lecturer dashboard provides the lecturer with:
•	Lecturer identity
•	Logout
•	Attendance-session creation
•	Lecturer's sessions
•	Session/course information
•	QR-code access
•	Attendance records
•	Live attendance viewing

**4. Attendance Session Creation**
A lecturer can create an attendance session by providing information including:
•	Course
•	Attendance radius
•	Lecturer's current location
The system generates a unique session code.
The session records include:
•	Session code
•	Lecturer ID
•	Course
•	Latitude
•	Longitude
•	Radius
•	Expiry information
•	Status

**5. Dynamic/Unique QR Attendance Session**
Each generated attendance session has its own unique session code and QR code.
The QR code represents a specific attendance event.

**6. QR Code Scanning**
Students scan/access the QR code associated with the lecturer's attendance session.
The system retrieves the corresponding attendance session.
QR functionality was implemented using ZXing.

**7. Geographical Location Validation**
The student's browser requests their geographical location.
The system obtains:
•	Latitude
•	Longitude
The student's location is compared with the attendance session's permitted geographical area.
Students within the permitted radius can continue.
Students outside the permitted radius are rejected.

**8. Image Capture**
The system uses the browser's camera/webcam capability to capture an image during attendance.
The captured image is stored as part of the attendance record.
The image is treated as supplementary attendance evidence, not facial-recognition or biometric identification.

**9. Attendance Recording**
After the required verification stages are completed, the attendance record is saved to MySQL.
The record associates the attendance with:
•	Student
•	Session
•	Check-in time
•	Captured image

**10. Duplicate Attendance Prevention**
The system prevents the same student from recording attendance more than once for the same session code.
The implemented rule is:
session_code + student_id
A new QR/session for the same course is treated as a new attendance event.

**11. Lecturer Attendance Records**
Lecturers can retrieve attendance records associated with their sessions.
The dashboard therefore allows the lecturer to see which students recorded attendance for a particular attendance session.

**12. Live Attendance**
The lecturer dashboard includes a Live Attendance section.
The lecturer selects an attendance session, and the system retrieves attendance for that particular session.
The dashboard polls the live-attendance servlet periodically, using the implemented five-second polling approach.

**E. User Roles**
**Student**
The student can:
•	Log in.
•	Access an attendance session through its QR code.
•	Provide browser location information.
•	Complete the image-capture stage.
•	Submit attendance.
•	Receive the attendance result.
•	Be prevented from recording twice for the same attendance session.

**Lecturer**
The lecturer can:
•	Log in.
•	Access the lecturer dashboard.
•	View their identity/session.
•	Capture their current location.
•	Create attendance sessions.
•	Specify course/session information.
•	Specify attendance radius.
•	Generate a unique QR attendance session.
•	View QR codes.
•	View attendance records.
•	Select sessions and view live attendance.
•	Log out.

**F. Authentication / Login**
The system has separate student and lecturer authentication workflows.
Lecturer authentication
The lecturer enters:
•	Lecturer ID
•	Password
LecturerLoginServlet authenticates the lecturer through LecturerDAO.
On successful login:
Lecturer
   ↓
LecturerLoginServlet
   ↓
HttpSession
   ↓
LecturerDashboardServlet
   ↓
lecturerDashboard.jsp
The lecturer object is stored in the HTTP session.
The dashboard checks whether an authenticated lecturer session exists.
If it does not, the user is redirected to:
lecturerLogin.jsp
Lecturer logout
LecturerLogoutServlet invalidates the HTTP session and redirects the lecturer to the login page.

**G. Attendance Workflow**
The implemented workflow is essentially:
Student Login
      ↓
Access / Scan QR
      ↓
Validate Session Code
      ↓
Check Existing Attendance
      ↓
Already recorded?
   Yes ↓       ↓ No
 Reject       Capture Location
                    ↓
             Validate Location
                    ↓
              Capture Image
                    ↓
             Save Attendance
                    ↓
              Success
The important attendance rule is:
One student
     +
One session_code
     =
One attendance record
However:
Same student + new session_code
=
New attendance event permitted
This was tested using multiple students and different browsers.

**H. Database**
The main database entities/tables actually discussed are:
**students**
Important fields include:
•	id
•	student_id
•	password
•	name
•	image_path

**lecturers**
Important fields:
•	id
•	lecturer_id
•	name
•	password
There is a unique constraint on:
lecturer_id

**sessions**
Current conceptual structure:
id
session_code
lecturer_id
course
latitude
longitude
radius
expiry_time
status
Relationship:
lecturer
   ↓
sessions
The session stores which lecturer created the attendance event.

**attendance**
Important fields:
id
session_code
check_in_time
image_path
created_at
student_id
Relationships:
Student
   ↓
Attendance
   ↑
Session
The application-level uniqueness rule is:
session_code + student_id
A foreign-key relationship was also established between:
sessions.lecturer_id
        ↓
lecturers.lecturer_id
with:
ON UPDATE CASCADE
ON DELETE SET NULL

**I. Dashboard / Reports**
The implemented dashboard functionality includes:
Lecturer dashboard
•	Lecturer information
•	Attendance-session creation
•	Current lecturer location
•	Session list
•	Course
•	Session code
•	Attendance radius
•	Session status
•	QR-code access
•	Attendance records
•	Live attendance

**Live attendance**
The lecturer can select a session and retrieve the students who have recorded attendance for that particular session.
This was specifically developed to satisfy the requirement that the lecturer should be able to see students who recorded attendance for each lecture/session.

**J. Security**
The following implemented security/verification features:
1. **Student authentication**
Students must authenticate before attendance.
2. **Lecturer authentication**
Lecturer functions are protected behind lecturer login.
3. **Lecturer session control**
The lecturer dashboard checks for an authenticated lecturer session.
4. **QR/session verification**
Attendance is associated with a specific session code.
5. Location validation
The student's location is checked against the session's permitted geographical radius.
6. Duplicate attendance prevention
A student cannot record twice for the same session.
7. Multiple verification stages

The system combines:
Authentication
+
QR session
+
Location
+
Image

**K. Problems Encountered and Solutions**
I encountered a substantial number of practical development/debugging issues.
**HTTP 405 errors**
Some servlet requests initially produced HTTP 405 Method Not Allowed errors.
These were investigated through the servlet request mappings and request handling.
**HTTP 404 errors**
Several URL/servlet routing issues produced 404 errors.
These were resolved by checking servlet mappings and the correct application URLs.
**HTTP 500 errors**
Server-side errors were encountered during development.
The underlying Java/database/application issues were traced through the server output and corrected.
**Image upload FileNotFoundException**
The image-capture process initially encountered a file-path problem.
The upload/storage path was corrected so the captured image could be saved properly.
**Incorrect street/session-related display information**
During development, there were cases where displayed information did not correspond correctly to the underlying record.
The database retrieval/display logic was corrected.
**Attendance duplicate insertion**
The attendance DAO initially contained a duplicate execution problem in the save process.
The duplicate execution was removed so the attendance insert occurs once.

**Duplicate attendance logic**
The correct attendance model had to be clarified.
The final implementation uses:
session_code + student_id
rather than simply student_id.
This allows:
Student 1515
Session A → attendance recorded
Session B → attendance recorded
while preventing:
Student 1515
Session A → first record
Session A → second record rejected

**Lecturer authentication/dashboard access**
Lecturer authentication and session protection were implemented so that the lecturer dashboard is not simply publicly accessible.
Live attendance loading
The live attendance servlet required correction during development.
The important issue identified was a missing:
import model.Session;
in LiveAttendanceServlet.java.
After correcting the servlet, the live attendance functionality was developed around session-specific retrieval.

**Browser differences**
The system was tested across:
•	Chrome
•	Firefox
•	Edge
Some browser-specific behaviour occurred around camera/location permissions and JavaScript execution.

**L. Testing**
Testing was performed throughout development rather than only at the end.
Functional testing
The following functions were tested:
•	Student login
•	Lecturer login
•	Lecturer logout
•	Session creation
•	QR generation
•	QR access
•	Location validation
•	Outside-radius rejection
•	Image capture
•	Attendance recording
•	Duplicate attendance prevention
•	Lecturer attendance retrieval
•	Live attendance

**Integration testing**
Important integration paths tested included:
Lecturer Login → Dashboard
Session Creation → QR Generation
QR → Student Authentication
Authentication → Geolocation
Geolocation → Image Capture
Image Capture → Attendance
Attendance → MySQL
Attendance → Lecturer Dashboard

**Multi-browser testing**
The application was tested using Chrome, Firefox and Edge during debugging.
Multiple-student testing
The attendance model was tested with students including:
•	1515
•	1516
•	1517
•	1518
•	1519
•	1520
•	1521
•	1522
The testing demonstrated that different students could record attendance against a session and that a student could record again when a new QR/session code was generated.
**Performance observations**
The dissertation recorded controlled prototype observations including approximately:
•	Login: under 2 seconds
•	QR generation: under 1 second
•	Attendance: under 3 seconds
•	Database operations: under 1 second

**M. Current Status**
**Completed**
Based on our discussions, these components have been implemented:
•	Java EE web application
•	Student authentication
•	Lecturer authentication
•	Lecturer logout
•	Lecturer dashboard
•	Session creation
•	Unique session codes
•	QR-code generation
•	QR attendance access
•	Browser geolocation
•	Location-radius validation
•	Browser image capture
•	Attendance recording
•	MySQL persistence
•	Duplicate attendance prevention
•	Lecturer-specific session records
•	Lecturer attendance records
•	Session-specific live attendance
•	Multi-browser testing
•	Database relationship between lecturers and sessions

**Working**
The core attendance workflow has been demonstrated as working:
Login
→ QR
→ Session validation
→ Location
→ Image
→ Attendance
→ Database
→ Lecturer records
The session-based attendance metric is also working according to the testing performed.

**N. My Contribution**
My personal contribution to the project was the development of the Smart Attendance System as a Java EE web application.
Worked directly on:
•	Project setup
•	Java EE development
•	JSP pages
•	Servlets
•	Database integration
•	MySQL database design
•	JDBC/DAO implementation
•	Student attendance workflow
•	Lecturer authentication
•	Lecturer dashboard
•	Attendance session generation
•	QR-code integration
•	Browser geolocation integration
•	Image capture
•	Duplicate attendance logic
•	Live attendance functionality
•	Debugging
•	Cross-browser testing
•	Integration of the different components
•	Dissertation documentation and evaluation
Designed and developed a Java EE-based multi-factor student attendance system integrating authenticated access, QR attendance sessions, browser geolocation validation, image capture, MySQL persistence and lecturer-side attendance management.

**O. Demonstrable Skills**
The project followed technical skills:
**Java / Backend**
•	Java
•	Java Servlets
•	JSP
•	Java EE web development
•	Session management
•	MVC-style web application development
•	HTTP request/response handling

**Database**
•	MySQL
•	SQL
•	JDBC
•	DAO pattern
•	Relational database design
•	Foreign keys
•	Database CRUD operations
•	Data validation

**Frontend**
•	HTML5
•	CSS3
•	JavaScript
•	JSP frontend integration
•	Browser APIs
•	Camera integration
•	Geolocation integration

**Application integration**
•	QR-code integration
•	ZXing
•	Browser Geolocation API
•	Webcam/camera API
•	JavaScript-to-backend communication
•	Database-to-dashboard integration

**Debugging**
•	HTTP 404
•	HTTP 405
•	HTTP 500
•	Java exceptions
•	File-path problems
•	Database insertion problems
•	Servlet mapping problems
•	JavaScript/browser issues
•	Cross-browser behaviour

**Software engineering**
•	Requirements analysis
•	System design
•	Database modelling
•	Incremental development
•	Functional testing
•	Integration testing
•	User workflow design
•	Security considerations
•	Technical documentation
P. Portfolio Evidence

**1. Student Login**
<img width="698" height="917" alt="image" src="https://github.com/user-attachments/assets/4625b8c2-9272-4d58-9c6b-950d099e16a3" />
Student authentication interface.

**2. Lecturer Login**
<img width="627" height="921" alt="image" src="https://github.com/user-attachments/assets/d70e022a-2cc6-4672-9003-527684fc951e" />
Lecturer login page.

**3. Lecturer Dashboard**
Show:
•	Lecturer identity
•	Session creation
•	Location
•	Sessions
•	Attendance information
<img width="1793" height="958" alt="image" src="https://github.com/user-attachments/assets/76ffb9c0-dc71-4b6e-8fe6-ddac8e00325e" />

**4. Lecturer Location Capture**
Lecturer location captured successfully
Latitude
Longitude
<img width="1340" height="560" alt="image" src="https://github.com/user-attachments/assets/c2a07fea-ddc5-44bf-84f6-1cdda9223852" />

**5. QR Code Display**
Generated attendance QR code.
<img width="859" height="928" alt="image" src="https://github.com/user-attachments/assets/40b15daf-b9a5-4d26-aec6-14e66927bf2c" />

**6. Student Attendance Workflow**
QR
→ Location: <img width="985" height="601" alt="image" src="https://github.com/user-attachments/assets/9014c715-db43-4d5d-a2d7-11633838d0ee" />

→ Image capture: <img width="663" height="921" alt="image" src="https://github.com/user-attachments/assets/4181f9b6-a165-410d-a176-7912ec08c8dd" />

→ Attendance: <img width="967" height="687" alt="image" src="https://github.com/user-attachments/assets/2bd40302-5ace-400f-8c2e-238704a4d237" />


**7. Image Capture**
Show the camera/image-capture interface.

**8. Successful Attendance**
Show the successful attendance confirmation.

**9. Live Attendance**
Show the lecturer selecting a session and seeing the students who have recorded attendance.

**10. Database / Architecture Evidence**
For GitHub, include:
•	ERD
•	System architecture diagram
•	Activity diagram
•	Sequence diagram
•	Database structure
