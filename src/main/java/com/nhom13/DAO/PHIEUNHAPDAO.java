package com.nhom13.DAO;

import com.nhom13.model.PHIEUNHAP;
import com.nhom13.helper.DatabaseHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PHIEUNHAPDAO {

    public List<PHIEUNHAP> getAllPhieuNhap() throws Exception {
        List<PHIEUNHAP> Notes = new ArrayList<>();
        Connection connect = DatabaseHelper.openConnection();
        String sql = "SELECT ID, FORMAT(NGAYNHAP,'dd/MM/yyyy hh:mm tt') FROM PHIEUNHAP";

        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                PHIEUNHAP note = new PHIEUNHAP();

                note.setID(rs.getInt(1));
                note.setNGAYNHAP(rs.getString(2));

                Notes.add(note);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return Notes;
    }

    public void addPhieuNhap(int IDPN) throws Exception {
        Connection connect = DatabaseHelper.openConnection();
        String sql = "INSERT INTO PHIEUNHAP(ID) VALUES(?)";

        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            stm.setInt(1, IDPN);

            stm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deletePhieuNhap(int IDPN) throws Exception {
        Connection connect = DatabaseHelper.openConnection();
        String sqlPN = "DELETE FROM PHIEUNHAP WHERE ID = ?";
        String sqlCTPN = "DELETE FROM CTPHIEUNHAP WHERE IDPN = ?";

        try {
            PreparedStatement stm = connect.prepareStatement(sqlCTPN);
            stm.setInt(1, IDPN);
            
            stm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        try {
            PreparedStatement stm = connect.prepareStatement(sqlPN);
            stm.setInt(1, IDPN);

            stm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
