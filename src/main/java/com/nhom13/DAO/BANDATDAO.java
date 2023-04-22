//@Author: NGUYỄN TRẦN TẤN QUY PTITHCM
package com.nhom13.DAO;

import com.nhom13.model.BANDAT;
import com.nhom13.helper.DatabaseHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BANDATDAO {

    public List<BANDAT> getAllBanDat() throws Exception {
        List<BANDAT> reservationTables = new ArrayList<>();
        Connection connect = DatabaseHelper.openConnection();
        String sql = "SELECT ID, TENKHACHHANG, SDT, NGAYDAT, THOIGIANDAT, SOLUONGNGUOI, BANSO, TRANGTHAI, GHICHU FROM BANDAT WHERE NGAYDAT >= CAST(GETDATE() AS DATE)";
        try {

            PreparedStatement stm = connect.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                BANDAT reservationTable = new BANDAT();
                reservationTable.setID(rs.getInt("ID"));
                reservationTable.setTENKHACHHANG(rs.getString("TENKHACHHANG"));
                reservationTable.setSDT(rs.getString("SDT"));
                reservationTable.setNGAYDAT(rs.getString("NGAYDAT"));
                reservationTable.setTHOIGIANDAT(rs.getString("THOIGIANDAT"));
                reservationTable.setSOLUONGNGUOI(rs.getInt("SOLUONGNGUOI"));
                reservationTable.setBANSO(rs.getString("BANSO"));
                reservationTable.setTRANGTHAI(rs.getString("TRANGTHAI"));
                reservationTable.setGHICHU(rs.getString("GHICHU"));

                reservationTables.add(reservationTable);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return reservationTables;
    }

    public void addBanDat(BANDAT reservationTable) throws Exception {
        Connection connect = DatabaseHelper.openConnection();
        String sql = "INSERT INTO BANDAT VALUES( ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            stm.setString(1, reservationTable.getTENKHACHHANG());
            stm.setString(2, reservationTable.getSDT());
            stm.setString(3, reservationTable.getNGAYDAT());
            stm.setString(4, reservationTable.getTHOIGIANDAT());
            stm.setInt(5, reservationTable.getSOLUONGNGUOI());
            stm.setString(6, reservationTable.getBANSO());
            stm.setString(7, reservationTable.getTRANGTHAI());
            stm.setString(8, reservationTable.getGHICHU());

            stm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void editBanDat(BANDAT reservationTable) throws Exception {
        Connection connect = DatabaseHelper.openConnection();
        String sql = "UPDATE BANDAT SET TENKHACHHANG = ?, SDT = ?, NGAYDAT = ?, THOIGIANDAT = ?, SOLUONGNGUOI = ?, BANSO = ?, TRANGTHAI = ?, GHICHU = ? WHERE ID = ?";

        try {
            PreparedStatement stm = connect.prepareStatement(sql);

            stm.setString(1, reservationTable.getTENKHACHHANG());
            stm.setString(2, reservationTable.getSDT());
            stm.setString(3, reservationTable.getNGAYDAT());
            stm.setString(4, reservationTable.getTHOIGIANDAT());
            stm.setInt(5, reservationTable.getSOLUONGNGUOI());
            stm.setString(6, reservationTable.getBANSO());
            stm.setString(7, reservationTable.getTRANGTHAI());
            stm.setString(8, reservationTable.getGHICHU());
            stm.setInt(9, reservationTable.getID());
            stm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteBanDat(int IDBD) throws Exception {
        Connection connect = DatabaseHelper.openConnection();
        String sql = "DELETE FROM BANDAT WHERE ID = ?";

        try {
            PreparedStatement stm = connect.prepareStatement(sql);

            stm.setInt(1, IDBD);
            stm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
