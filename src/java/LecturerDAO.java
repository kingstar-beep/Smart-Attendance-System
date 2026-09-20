
import model.Lecturer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Session;

public class LecturerDAO {

    public Lecturer login(String lecturerId, String password) {

        Lecturer lecturer = null;

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM lecturers "
                    + "WHERE lecturer_id = ? AND password = ?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, lecturerId);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                lecturer = new Lecturer();

                lecturer.setLecturerId(
                        rs.getString("lecturer_id")
                );

                lecturer.setName(
                        rs.getString("name")
                );
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lecturer;
    }

    public boolean lecturerExists(String lecturerId) {

        String sql
                = "SELECT COUNT(*) FROM lecturers WHERE lecturer_id = ?";

        try {

            Connection con
                    = DBConnection.getConnection();

            PreparedStatement ps
                    = con.prepareStatement(sql);

            ps.setString(1, lecturerId);

            ResultSet rs
                    = ps.executeQuery();

            boolean exists = false;

            if (rs.next()) {

                exists
                        = rs.getInt(1) > 0;
            }

            rs.close();
            ps.close();
            con.close();

            return exists;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    public boolean registerLecturer(
            String lecturerId,
            String name,
            String password) {

        String sql
                = "INSERT INTO lecturers "
                + "(lecturer_id, name, password) "
                + "VALUES (?, ?, ?)";

        try {

            Connection con
                    = DBConnection.getConnection();

            PreparedStatement ps
                    = con.prepareStatement(sql);

            ps.setString(1, lecturerId);
            ps.setString(2, name);
            ps.setString(3, password);

            int rows
                    = ps.executeUpdate();

            ps.close();
            con.close();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }
}
