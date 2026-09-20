
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author KINGSTAR
 */
/*
import java.sql.Connection;
import java.sql.PreparedStatement;
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
 */
import model.Session;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SessionDAO {

    // SAVE SESSION
    public boolean saveSession(Session session) {

        String sql = "INSERT INTO sessions "
                + "(session_code, lecturer_id, course, latitude, longitude, "
                + "radius, expiry_time, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, session.getSessionCode());
            ps.setString(2, session.getLecturerId());
            ps.setString(3, session.getCourse());
            ps.setDouble(4, session.getLatitude());
            ps.setDouble(5, session.getLongitude());
            ps.setDouble(6, session.getRadius());

            if (session.getExpiryTime() > 0) {
                ps.setLong(7, session.getExpiryTime());
            } else {
                ps.setNull(7, java.sql.Types.BIGINT);
            }

            ps.setString(8, session.getStatus());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // GET SESSION BY QR/SESSION CODE
    public Session getSessionByCode(String code) {

        Session session = null;

        String sql = "SELECT * FROM sessions WHERE session_code = ?";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, code);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    session = new Session();

                    session.setSessionCode(
                            rs.getString("session_code"));

                    session.setLecturerId(
                            rs.getString("lecturer_id"));

                    session.setCourse(
                            rs.getString("course"));

                    session.setLatitude(
                            rs.getDouble("latitude"));

                    session.setLongitude(
                            rs.getDouble("longitude"));

                    session.setRadius(
                            rs.getDouble("radius"));

                    session.setExpiryTime(
                            rs.getLong("expiry_time"));

                    session.setStatus(
                            rs.getString("status"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return session;
    }

    // GET SESSIONS CREATED BY A PARTICULAR LECTURER
    public List<Session> getSessionsByLecturer(String lecturerId) {

        List<Session> sessions = new ArrayList<>();

        String sql = "SELECT * FROM sessions "
                + "WHERE lecturer_id = ? "
                + "ORDER BY id DESC";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, lecturerId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Session session = new Session();

                    session.setSessionCode(
                            rs.getString("session_code"));

                    session.setLecturerId(
                            rs.getString("lecturer_id"));

                    session.setCourse(
                            rs.getString("course"));

                    session.setLatitude(
                            rs.getDouble("latitude"));

                    session.setLongitude(
                            rs.getDouble("longitude"));

                    session.setRadius(
                            rs.getDouble("radius"));

                    session.setExpiryTime(
                            rs.getLong("expiry_time"));

                    session.setStatus(
                            rs.getString("status"));

                    if ("ACTIVE".equalsIgnoreCase(
                            session.getStatus())
                            && session.getExpiryTime() > 0
                            && System.currentTimeMillis()
                            >= session.getExpiryTime()) {

                        markSessionExpired(
                                session.getSessionCode()
                        );

                        session.setStatus(
                                "EXPIRED"
                        );
                    }

                    sessions.add(session);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return sessions;
    }

    public boolean closeSession(
            String sessionCode,
            String lecturerId) {

        String sql
                = "UPDATE sessions "
                + "SET status = 'CLOSED' "
                + "WHERE session_code = ? "
                + "AND lecturer_id = ? "
                + "AND status = 'ACTIVE'";

        try (
                Connection con
                = DBConnection.getConnection(); PreparedStatement ps
                = con.prepareStatement(sql)) {

            ps.setString(
                    1,
                    sessionCode
            );

            ps.setString(
                    2,
                    lecturerId
            );

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    public boolean markSessionExpired(
            String sessionCode) {

        String sql
                = "UPDATE sessions "
                + "SET status = 'EXPIRED' "
                + "WHERE session_code = ? "
                + "AND status = 'ACTIVE'";

        try (
                Connection con
                = DBConnection.getConnection(); PreparedStatement ps
                = con.prepareStatement(sql)) {

            ps.setString(
                    1,
                    sessionCode
            );

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }
}
