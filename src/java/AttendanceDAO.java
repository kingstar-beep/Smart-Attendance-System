
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

    public boolean saveAttendance(
            String sessionCode,
            String imagePath,
            String studentId) {

        String sql = "INSERT INTO attendance "
                + "(session_code, check_in_time, image_path, created_at, student_id) "
                + "VALUES (?, NOW(), ?, NOW(), ?)";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, sessionCode);
            ps.setString(2, imagePath);
            ps.setString(3, studentId);

            int rows = ps.executeUpdate();

            System.out.println("Rows inserted: " + rows);

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean hasAttendance(String sessionCode, String studentId) {

        String sql = "SELECT COUNT(*) FROM attendance "
                + "WHERE session_code = ? AND student_id = ?";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, sessionCode);
            ps.setString(2, studentId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
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

    public List<Attendance> getAttendanceByLecturer(String lecturerId) {

        List<Attendance> list = new ArrayList<>();

        String sql
                = "SELECT a.id, a.student_id, a.session_code, "
                + "a.image_path, a.check_in_time, "
                + "st.name AS student_name, "
                + "s.course "
                + "FROM attendance a "
                + "LEFT JOIN students st "
                + "ON a.student_id = st.student_id "
                + "INNER JOIN sessions s "
                + "ON a.session_code = s.session_code "
                + "WHERE s.lecturer_id = ? "
                + "ORDER BY a.check_in_time DESC";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, lecturerId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Attendance a = new Attendance();

                    a.setId(rs.getInt("id"));

                    a.setStudentId(
                            rs.getString("student_id"));

                    a.setStudentName(
                            rs.getString("student_name"));

                    a.setSessionCode(
                            rs.getString("session_code"));

                    a.setCourse(
                            rs.getString("course"));

                    a.setImagePath(
                            rs.getString("image_path"));

                    a.setCheckInTime(
                            rs.getString("check_in_time"));

                    list.add(a);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Attendance> getAttendanceBySession(String sessionCode) {

        List<Attendance> list = new ArrayList<>();

        String sql
                = "SELECT a.id, a.student_id, a.session_code, "
                + "a.image_path, a.check_in_time, "
                + "st.name AS student_name, "
                + "s.course "
                + "FROM attendance a "
                + "LEFT JOIN students st "
                + "ON a.student_id = st.student_id "
                + "INNER JOIN sessions s "
                + "ON a.session_code = s.session_code "
                + "WHERE a.session_code = ? "
                + "ORDER BY a.check_in_time ASC";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, sessionCode);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Attendance a = new Attendance();

                    a.setId(rs.getInt("id"));

                    a.setStudentId(
                            rs.getString("student_id"));

                    a.setStudentName(
                            rs.getString("student_name"));

                    a.setSessionCode(
                            rs.getString("session_code"));

                    a.setCourse(
                            rs.getString("course"));

                    a.setImagePath(
                            rs.getString("image_path"));

                    a.setCheckInTime(
                            rs.getString("check_in_time"));

                    list.add(a);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

}
