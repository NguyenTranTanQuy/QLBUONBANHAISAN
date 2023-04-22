//@Author: NGUYỄN TRẦN TẤN QUY PTITHCM
package com.nhom13.DAO;

import com.nhom13.helper.DatabaseHelper;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class CTHOADONDAO {

    public void addCTHOADON(int IDHD, int IDMA) throws Exception {
        Connection connect = DatabaseHelper.openConnection();
        String sql = "INSERT INTO CTHOADON VALUES(?, ?, 1)";

        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            stm.setInt(1, IDHD);
            stm.setInt(2, IDMA);

            stm.executeUpdate();
        } catch (SQLException ex) {
        }
    }

    public void deleteCTHOADON(int IDHD, int IDMA) throws Exception {
        Connection connect = DatabaseHelper.openConnection();
        String sql = "DELETE FROM CTHOADON WHERE IDHD = ? AND IDMA = ?";
        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            stm.setInt(1, IDHD);
            stm.setInt(2, IDMA);

            stm.executeUpdate();
        } catch (SQLException ex) {
        }
    }

    public void addFoodToBill(int IDHD, int IDMA) throws Exception {
        Connection connect = DatabaseHelper.openConnection();
        String sql = "UPDATE CTHOADON SET SOLUONG = SOLUONG + 1 WHERE IDHD = ? AND IDMA = ?";

        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            stm.setInt(1, IDHD);
            stm.setInt(2, IDMA);

            stm.executeUpdate();
        } catch (SQLException ex) {
        }
    }

    public void reduceFoodToBill(int IDHD, int IDMA) throws Exception {
        Connection connect = DatabaseHelper.openConnection();
        String sql = "UPDATE CTHOADON SET SOLUONG = SOLUONG - 1 WHERE IDHD = ? AND IDMA = ?";

        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            stm.setInt(1, IDHD);
            stm.setInt(2, IDMA);

            stm.executeUpdate();
        } catch (SQLException ex) {
        }
    }

    public int getQuantityFoodOfBill(int IDHD, int IDMA) throws Exception {
        int quantity = 0;
        Connection connect = DatabaseHelper.openConnection();
        String sql = "{call getQuantityFoodOfBill(?, ?, ?)}";
        try {
            CallableStatement cstmt = connect.prepareCall(sql);
            cstmt.setInt(1, IDHD);
            cstmt.setInt(2, IDMA);
            cstmt.registerOutParameter(3, Types.INTEGER);
            cstmt.execute();
            quantity = cstmt.getInt(3);
        } catch (SQLException ex) {
        }
        return quantity;
    }
}
