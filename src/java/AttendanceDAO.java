
import model.Attendance;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author KINGSTAR
 */
public class AttendanceDAO {

    public void saveAttendance(String sessionCode, String imagePath, String studentId) {

        try {
            Connection con = DBConnection.getConnection();

            con.setAutoCommit(true);

            String sql = "INSERT INTO attendance (session_code, image_path, student_id) VALUES (?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, sessionCode);
            ps.setString(2, imagePath);
            ps.setString(3, studentId);
            int rows = ps.executeUpdate();
            System.out.println("Rows inserted: " + rows);

            ps.executeUpdate();
            // System.out.println("Inside AttendanceDAO");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean hasAttendance(String sessionCode) {

        boolean exists = false;

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM attendance WHERE session_code = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, sessionCode);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                exists = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return exists;
    }

    public List<Attendance> getAllAttendance() {

        List<Attendance> list = new ArrayList<>();

        try {

            Connection con = DBConnection.getConnection();

            String sql
                    = "SELECT\n"
                    + "a.*,\n"
                    + "s.name\n"
                    + "FROM attendance a\n"
                    + "LEFT JOIN students s\n"
                    + "ON a.student_id = s.student_id\n"
                    + "ORDER BY a.check_in_time DESC";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Attendance a = new Attendance();

                a.setStudentName(rs.getString("name"));
                a.setId(rs.getInt("id"));
                a.setStudentId(rs.getString("student_id"));
                a.setSessionCode(rs.getString("session_code"));
                a.setImagePath(rs.getString("image_path"));
                a.setCheckInTime(rs.getString("check_in_time"));

                list.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
