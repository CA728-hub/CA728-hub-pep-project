package DAO;

import java.sql.*; 
import Model.Account;
import Util.ConnectionUtil;
import java.util.ArrayList;
import java.util.List;  



public class AccountDAO {

    public Account insertUser(Account account) {
    Connection connection = ConnectionUtil.getConnection();
    String sql = "INSERT INTO account (username, password) VALUES (?, ?)";

    try {
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, account.getUsername());
        ps.setString(2, account.getPassword());

        int rows = ps.executeUpdate();
        if (rows > 0) {
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                account.setAccount_id(rs.getInt(1)); // <- this is the fix
                return account;                     // <- return updated object
            }
        }
    } catch (SQLException e) {
        System.out.println("Error: " + e.getMessage());
    }

    return null;
}






    public Account getUserByUsername(String username) {

        Connection connection = ConnectionUtil.getConnection();

        String sql = "SELECT * FROM account WHERE username = ?"; 

        
        try {

            PreparedStatement ps = connection.prepareStatement(sql); 

            ps.setString(1, username);
             

            ResultSet rs = ps.executeQuery(); 

            if (rs.next()) {
                Account accounts = new Account(); 
                accounts.setAccount_id(rs.getInt("account_id")); 
                accounts.setUsername(rs.getString("username"));
                accounts.setPassword(rs.getString("password")); 
                return accounts;  
            }



            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null; 
    }








    public Account registerUser(Account account) {
    if (account.getUsername() == null || account.getUsername().isBlank()) return null;
    if (account.getPassword() == null || account.getPassword().length() < 4) return null;

    
    String checkSql = "SELECT * FROM account WHERE username = ?";
    try (Connection connection = ConnectionUtil.getConnection();
         PreparedStatement checkPs = connection.prepareStatement(checkSql)) {

        checkPs.setString(1, account.getUsername());
        ResultSet rs = checkPs.executeQuery();

        if (rs.next()) {
            return null; 
        }
    } catch (SQLException e) {
        System.out.println("Registration Check Error: " + e.getMessage());
        return null;
    }

    
    String insertSql = "INSERT INTO account (username, password) VALUES (?, ?)";

    try (Connection connection = ConnectionUtil.getConnection();
         PreparedStatement ps = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {

        ps.setString(1, account.getUsername());
        ps.setString(2, account.getPassword());

        int rows = ps.executeUpdate();
        if (rows > 0) {
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                account.setAccount_id(keys.getInt(1));
                return account;
            }
        }
    } catch (SQLException e) {
        System.out.println("Registration Insert Error: " + e.getMessage());
    }

    return null;
}

}