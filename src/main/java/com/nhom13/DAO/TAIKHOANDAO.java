//@Author: NGUYỄN TRẦN TẤN QUY PTITHCM
package com.nhom13.DAO;

import com.nhom13.model.TAIKHOAN;
import com.nhom13.helper.DatabaseHelper;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class TAIKHOANDAO {

    public List<TAIKHOAN> getAllTAIKHOAN() throws Exception {
        List<TAIKHOAN> Accounts = new ArrayList<>();
        Connection connect = DatabaseHelper.openConnection();
        String sql = "SELECT MATAIKHOAN, MATKHAU, MANV FROM TAIKHOAN";
        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                TAIKHOAN account = new TAIKHOAN();

                account.setMANV(rs.getString("MANV").replaceAll("\\s", ""));
                account.setMATAIKHOAN(rs.getString("MATAIKHOAN").replaceAll("\\s", ""));
                account.setMATKHAU(rs.getString("MATKHAU").replaceAll("\\s", ""));

                Accounts.add(account);
            }
        } catch (SQLException ex) {
        }
        return Accounts;
    }

    public boolean addTAIKHOAN(TAIKHOAN account) throws Exception {
        Connection connect = DatabaseHelper.openConnection();
        String sql = "INSERT INTO TAIKHOAN VALUES(?, ?, ?)";

        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            stm.setString(1, account.getMATAIKHOAN());
            stm.setString(2, account.getMATKHAU());
            stm.setString(3, account.getMANV());

            stm.executeUpdate();
        } catch (SQLException ex) {
            return false;
        }
        return true;
    }

    public void editTAIKHOAN(TAIKHOAN account) throws Exception {
        Connection connect = DatabaseHelper.openConnection();
        String sql = "UPDATE TAIKHOAN SET MATAIKHOAN = ?, MATKHAU = ? WHERE MANV = ?";

        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            stm.setString(1, account.getMATAIKHOAN());
            stm.setString(2, account.getMATKHAU());
            stm.setString(3, account.getMANV());

            stm.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error!");
        }
    }

    public void deleteTAIKHOAN(String MATAIKHOAN) throws Exception {
        Connection connect = DatabaseHelper.openConnection();
        String sql = "DELETE FROM TAIKHOAN WHERE MATAIKHOAN = ?";

        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            stm.setString(1, MATAIKHOAN);

            stm.executeUpdate();
        } catch (SQLException ex) {
        }
    }

    public String getNameStaffByMaNV(String maNV) throws Exception {
        Connection connect = DatabaseHelper.openConnection();
        String sql = "{call getNameStaffByMANV(?, ?)}";
        String name = "UNNAME";
        try {
            CallableStatement cstmt = connect.prepareCall(sql);
            cstmt.setString(1, maNV);
            cstmt.registerOutParameter(2, Types.NVARCHAR);
            cstmt.execute();
            name = cstmt.getString(2);
        } catch (SQLException ex) {
        }
        return name;
    }
}
