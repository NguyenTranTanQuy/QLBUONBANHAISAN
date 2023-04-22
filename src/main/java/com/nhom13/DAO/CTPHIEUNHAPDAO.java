package com.nhom13.DAO;

import com.nhom13.model.CTPHIEUNHAP;
import com.nhom13.helper.DatabaseHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CTPHIEUNHAPDAO {

    public List<CTPHIEUNHAP> getALLCTPhieuNhap(int IDPN) throws Exception {
        List<CTPHIEUNHAP> noteDetails = new ArrayList<>();
        Connection connect = DatabaseHelper.openConnection();
        String sql = "SELECT IDPN, MAHS, SOLUONG, DONGIA, DONVI FROM CTPHIEUNHAP WHERE IDPN = ?";

        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            stm.setInt(1, IDPN);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                CTPHIEUNHAP noteDetail = new CTPHIEUNHAP();

                noteDetail.setIDPN(rs.getInt("IDPN"));
                noteDetail.setMAHS(rs.getString("MAHS").trim());
                noteDetail.setSOLUONG(rs.getInt("SOLUONG"));
                noteDetail.setDONGIA(rs.getInt("DONGIA"));
                noteDetail.setDONVI(rs.getString("DONVI"));

                noteDetails.add(noteDetail);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return noteDetails;
    }

    public void addCTPhieuNhap(CTPHIEUNHAP noteDetail) throws Exception {
        Connection connect = DatabaseHelper.openConnection();
        String sql = "INSERT INTO CTPHIEUNHAP VALUES(?, ?, ?, ?, ?)";

        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            stm.setInt(1, noteDetail.getIDPN());
            stm.setString(2, noteDetail.getMAHS());
            stm.setInt(3, noteDetail.getSOLUONG());
            stm.setInt(4, noteDetail.getDONGIA());
            stm.setString(5, noteDetail.getDONVI());
            stm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void editCTPhieuNhap(CTPHIEUNHAP noteDetail) throws Exception {
        Connection connect = DatabaseHelper.openConnection();
        String sql = "UPDATE CTPHIEUNHAP SET IDPN = ?, MAHS = ?, SOLUONG = ?,DONGIA = ?, DONVI = ? WHERE IDPN = ? AND MAHS = ?";

        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            stm.setInt(1, noteDetail.getIDPN());
            stm.setString(2, noteDetail.getMAHS());
            stm.setInt(3, noteDetail.getSOLUONG());
            stm.setInt(4, noteDetail.getDONGIA());
            stm.setString(5, noteDetail.getDONVI());
            stm.setInt(6, noteDetail.getIDPN());
            stm.setString(7, noteDetail.getMAHS());
            stm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteCTPhieuNhap(int IDPN, String maHS) throws Exception {
        Connection connect = DatabaseHelper.openConnection();
        String sql = "DELETE FROM CTPHIEUNHAP WHERE IDPN = ? AND MAHS = ?";

        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            stm.setInt(1, IDPN);
            stm.setString(2, maHS);

            stm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}
