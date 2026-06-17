/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import model.Student;
import java.util.Base64;
import java.io.FileOutputStream;
import javax.servlet.http.HttpSession;

/**
 *
 * @author KINGSTAR
 */
@WebServlet("/MarkAttendanceServlet")
public class MarkAttendanceServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String sessionCode = request.getParameter("code");

        if (sessionCode == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        request.setAttribute("code", sessionCode);
        request.getRequestDispatcher("student.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession httpSession = request.getSession(false);

        if (httpSession == null || httpSession.getAttribute("student") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Student student = (Student) httpSession.getAttribute("student");
        String studentId = student.getStudentId();

        String sessionCode = request.getParameter("code");
        String imageData = request.getParameter("imageData");
        String imagePath = saveImage(imageData, sessionCode);

        System.out.println("Code received: " + request.getParameter("code"));

        //  double userLat = Double.parseDouble(request.getParameter("lat"));
        //  double userLng = Double.parseDouble(request.getParameter("lng"));
        // TODO: validate session in database
        SessionDAO dao = new SessionDAO();
        Session session = dao.getSessionByCode(sessionCode);

        if (session == null) {
            request.setAttribute("message", "Invalid or expired session");
            request.getRequestDispatcher("student.jsp").forward(request, response);
            return;
        }

        String latStr = request.getParameter("lat");
        String lngStr = request.getParameter("lng");

        if (latStr == null || latStr.isEmpty() || lngStr == null || lngStr.isEmpty()) {
            request.setAttribute("message", "Location not captured. Try again.");
            request.getRequestDispatcher("student.jsp").forward(request, response);
            return;
        }
        double userLat = Double.parseDouble(latStr);
        double userLng = Double.parseDouble(lngStr);

        // STEP 4: GEO VALIDATION
        double distance = calculateDistance(
                userLat, userLng,
                session.getLatitude(), session.getLongitude()
        );

        //Confirm Geo Logic (Optional Test)
        System.out.println("User Location: " + userLat + ", " + userLng);
        System.out.println("Session Location: " + session.getLatitude() + ", " + session.getLongitude());
        System.out.println("Distance: " + distance);
        System.out.println("DO POST TRIGGERED");
        System.out.println("Allowed Radius: " + session.getRadius());

        if (distance > session.getRadius()) {
            request.setAttribute("message", "You are outside allowed location!");
            //request.setAttribute("message", "NEW VERSION RUNNING!!!");
            request.getRequestDispatcher("student.jsp").forward(request, response);
            return;
        }

        // Save attendance
        AttendanceDAO attendanceDAO = new AttendanceDAO();
        if (attendanceDAO.hasAttendance(sessionCode)) {
            request.setAttribute("message", "Attendance already recorded!");
        } else {
            attendanceDAO.saveAttendance(sessionCode, imagePath, studentId);
            request.setAttribute("message",
                    "<h2>Attendance Successfully Recorded</h2>"
                    + "<br>✔ Student Identity Verified"
                    + "<br>✔ QR Code Authentication Passed"
                    + "<br>✔ Geo-Location Confirmed"
                    + "<br>✔ Image Verification Captured"
                    + "<br>✔ Attendance Logged Successfully");
        }

        /*   if (System.currentTimeMillis() > session.getExpiryTime()) {
            request.setAttribute("message", "Session has expired!");
            request.getRequestDispatcher("student.jsp").forward(request, response);
            return;
        } */
        // request.setAttribute("message", "Attendance recorded successfully!");
        request.getRequestDispatcher("student.jsp").forward(request, response);
    }

    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {

        final int R = 6371; // Earth radius in KM

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }

    public String saveImage(String base64Image, String sessionCode) {

        try {
            String[] parts = base64Image.split(",");
            byte[] imageBytes = Base64.getDecoder().decode(parts[1]);

            String filePath = "C:/attendance_images/" + sessionCode + "_" + System.currentTimeMillis() + ".png";

            FileOutputStream fos = new FileOutputStream(filePath);
            fos.write(imageBytes);
            fos.close();

            return filePath;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
