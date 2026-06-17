
import java.sql.Connection;
import java.sql.PreparedStatement;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author KINGSTAR
 */
import java.sql.*;

public class SessionDAO {

    // SAVE SESSION
    public void saveSession(Session session) {

        try {
            Connection con = DBConnection.getConnection();

String sql = "INSERT INTO sessions (session_code, latitude, longitude, radius, expiry_time) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, session.getSessionCode());
            ps.setDouble(2, session.getLatitude());
            ps.setDouble(3, session.getLongitude());
            ps.setDouble(4, session.getRadius());
            ps.setLong(5, session.getExpiryTime());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔥 THIS IS STEP 2 (PUT IT HERE)
    public Session getSessionByCode(String code) {

        Session session = null;

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM sessions WHERE session_code = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, code);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                session = new Session();
                session.setSessionCode(rs.getString("session_code"));
                session.setLatitude(rs.getDouble("latitude"));
                session.setLongitude(rs.getDouble("longitude"));
                session.setRadius(rs.getDouble("radius"));
                
                // VERY IMPORTANT LINE TO MAKE THE EXPIRATION EFFECTIVE
               // session.setExpiryTime(rs.getLong("expiry_time"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return session;
    }
    
}