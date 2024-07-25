package serversideforxogame;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import serversideforxogame.DTO.Player;

public class DAL {

    static ArrayList<Player> vec = new ArrayList<Player>();

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:derby://localhost:1527/XOGameVerOne", "root", "root");
    }

    public static void login(String userName, String password) {
        String sql = "UPDATE XOGAMEVERONE SET ACTIVE = true WHERE USERNAME = ? AND PASSWORD = ?";
        try (
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            stmt.setString(2, password);
            stmt.executeUpdate();
        } catch (SQLException exception) {
            Logger.getLogger(DAL.class.getName()).log(Level.SEVERE, null, exception);
        }
    }
    

    public static void signUp(String username, String email, String password) throws SQLException {
        String sql = "INSERT INTO XOGAMEVERONE (USERNAME, EMAIL, PASSWORD) VALUES (?, ?, ?)";
        try (
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.executeUpdate();
        }
    }

    public static String checkSignIn(String userName, String password) {
    if (!checkIsActive(userName)) {
        String sql = "SELECT * FROM XOGAMEVERONE WHERE USERNAME = ?";
        try (
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                if (password.equals(rs.getString("PASSWORD"))) {
                    login(userName, password);
                    return "Logged in successfully";
                } else {
                    return "Password is incorrect";
                }
            } else {
                return "UserName is incorrect";
            }
        } catch (SQLException exception) {
            Logger.getLogger(DAL.class.getName()).log(Level.SEVERE, null, exception);
        }
    } else {
        return "This UserName is already signed-in";
    }

    return "Something went wrong";
}

    public static boolean checkIsActive(String userName) {
        String sql = "SELECT ACTIVE FROM XOGAMEVERONE WHERE USERNAME = ?";
        try (
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getBoolean("ACTIVE");
        } catch (SQLException exception) {
            Logger.getLogger(DAL.class.getName()).log(Level.SEVERE, null, exception);
        }

        return false;
    }
    
    public static String checkSignUp(String username, String email,String password) {
    
    String usernameSql = "SELECT * FROM XOGAMEVERONE WHERE USERNAME = ?";
    String emailSql = "SELECT * FROM XOGAMEVERONE WHERE EMAIL = ?";
    
    try (
        Connection conn = getConnection()) {
        try (
            PreparedStatement stmt = conn.prepareStatement(usernameSql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return "Username already exists";
            }
        }
        
        try (PreparedStatement stmt = conn.prepareStatement(emailSql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return "Email already registered";
            }
        }
        signUp(username,email,password);
        return "Registered Successfully";
    } catch (SQLException exception) {
        Logger.getLogger(DAL.class.getName()).log(Level.SEVERE, null, exception);
    }

    return "Something went wrong";
}

    
    public static int getScore(String userName) throws SQLException {
        String sql = "SELECT SCORE FROM XOGAMEVERONE WHERE USERNAME = ?";
        try (
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt("SCORE") : -1;
        }
    }
    
    public static void updateScore(String userName, int score) throws SQLException {
        String sql = "UPDATE XOGAMEVERONE SET SCORE = ? WHERE USERNAME = ?";
        try (
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, score);
            stmt.setString(2, userName);
            stmt.executeUpdate();
        }
    }
    
    public static ArrayList<String> getActivePlayerUsernames() {
    ArrayList<String> activePlayerUsernames = new ArrayList<>();
    String sql = "SELECT USERNAME FROM XOGAMEVERONE WHERE ACTIVE = true";

    try (
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            String userName = rs.getString("USERNAME");
            activePlayerUsernames.add(userName);
        }
    } catch (SQLException exception) {
        Logger.getLogger(DAL.class.getName()).log(Level.SEVERE, null, exception);
    }

    return activePlayerUsernames;
   }

    
    public static ArrayList<String> getInactivePlayerUsernames() throws SQLException {
    ArrayList<String> inactivePlayerUsernames = new ArrayList<>();
    String sql = "SELECT USERNAME FROM XOGAMEVERONE WHERE ACTIVE = false";

    try (
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            String userName = rs.getString("USERNAME");
            inactivePlayerUsernames.add(userName);
        }
        }   
    return inactivePlayerUsernames;
    }
    
     public static void signOut(String userName) throws SQLException {
        String sql = "UPDATE XOGAMEVERONE SET ACTIVE = false WHERE USERNAME = ?";
        try (
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            stmt.executeUpdate();
        }
    }

    
//    public static ArrayList<Player> getActivePlayers() throws SQLException {
//        ArrayList<Player> activePlayersList = new ArrayList<>();
//        String sql = "SELECT * FROM XOGameVerOne WHERE ACTIVE = true";
//        try (
//             Connection conn = getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql);
//             ResultSet rs = stmt.executeQuery()) {
//
//            while (rs.next()) {
//                String userName = rs.getString("USERNAME");
//                String email = rs.getString("EMAIL");
//                String password = rs.getString("PASSWORD");
//                int score = rs.getInt("SCORE");
//                boolean active = rs.getBoolean("ACTIVE");
//                boolean inActive = rs.getBoolean("INACTIVE");
//                Player player = new Player(userName, email, password, score, active, inActive);
//                activePlayersList.add(player);
//            }
//        }
//        return activePlayersList;
//    }

//    public static ArrayList<Player> getInactivePlayers() throws SQLException {
//        ArrayList<Player> inactivePlayersList = new ArrayList<>();
//        String sql = "SELECT * FROM XOGameVerOne WHERE ACTIVE = false";
//        try (
//            Connection conn = getConnection();
//            PreparedStatement stmt = conn.prepareStatement(sql);
//            ResultSet rs = stmt.executeQuery()) {
//
//            while (rs.next()) {
//                String userName = rs.getString("USERNAME");
//                String email = rs.getString("EMAIL");
//                String password = rs.getString("PASSWORD");
//                int score = rs.getInt("SCORE");
//                boolean active = rs.getBoolean("ACTIVE");
//                boolean inActive = rs.getBoolean("INACTIVE");
//                Player player = new Player(userName, email, password, score, active, inActive);
//                inactivePlayersList.add(player);
//            }
//        }
//        return inactivePlayersList;
//    }
//    
   

    /* public static String getEmail(String userName) throws SQLException {
        String sql = "SELECT EMAIL FROM XOGameVerOne WHERE USERNAME = ?";
        try (
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getString("EMAIL") : null;
        }
    }
    
    public static String getUserName(String email) throws SQLException {
        String sql = "SELECT USERNAME FROM XOGameVerOne WHERE EMAIL = ?";
        try (
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getString("USERNAME") : null;
        }
    }*/
}
