
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Student;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author KINGSTAR
 */
public class StudentDAO {
    public Student login(String studentId, String password) {

    Student student = null;

    try {
        Connection con = DBConnection.getConnection();

        String sql = "SELECT * FROM students WHERE student_id=? AND password=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, studentId);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            student = new Student();
            student.setStudentId(rs.getString("student_id"));
            student.setName(rs.getString("name"));
            student.setImagePath(rs.getString("image_path")
);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return student;
}
}
