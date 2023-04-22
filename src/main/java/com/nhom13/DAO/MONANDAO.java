//@Author: NGUYỄN TRẦN TẤN QUY PTITHCM
package com.nhom13.DAO;

import com.nhom13.model.MONAN;
import com.nhom13.model.PHIEUNHAPMON;
import com.nhom13.helper.DatabaseHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

public class MONANDAO {

    public List<MONAN> getAllMONAN() throws Exception {
        List<MONAN> food = new ArrayList<>();
        Connection connect = DatabaseHelper.openConnection();
        String sql = "SELECT ID, TENMONAN, GIA, MAHS, HINHANH FROM MONAN";

        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                MONAN ma = new MONAN();

                ma.setID(rs.getInt("ID"));
                ma.setTENMONAN(rs.getString("TENMONAN").trim());
                ma.setGIA(rs.getInt("GIA"));
                ma.setMAHS(rs.getString("MAHS").trim());
                ma.setHINHANH(rs.getString("HINHANH").trim());

                food.add(ma);
            }
        } catch (SQLException ex) {
            System.out.println("Error!");
        }
        return food;
    }

    public boolean addMONAN(MONAN food) throws Exception {
        Connection connect = DatabaseHelper.openConnection();
        String sql = "INSERT INTO MONAN(TENMONAN, GIA, MAHS, HINHANH) VALUES( ?, ?, ?, ?)";

        try {
            PreparedStatement stm = connect.prepareStatement(sql);

            stm.setString(1, food.getTENMONAN());
            stm.setInt(2, food.getGIA());
            stm.setString(3, food.getMAHS());
            stm.setString(4, food.getHINHANH());

            stm.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Mã món ăn đã tồn tại hoặc có một vài thông tin không đúng", "Không hợp lệ!",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;

    }

    public void editMONAN(MONAN food) throws Exception {
        Connection connect = DatabaseHelper.openConnection();
        String sql = "UPDATE MONAN SET TENMONAN = ?, GIA = ?, MAHS = ?, HINHANH = ? WHERE ID = ?";

        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            stm.setString(1, food.getTENMONAN());
            stm.setInt(2, food.getGIA());
            stm.setString(3, food.getMAHS());
            stm.setString(4, food.getHINHANH());
            stm.setInt(5, food.getID());

            stm.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("ERROR!!");
        }
    }

    public void deleteMONAN(int ID) throws Exception {
        Connection connect = DatabaseHelper.openConnection();
        String sql = "DELETE FROM MONAN WHERE ID = ?";

        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            stm.setInt(1, ID);

            stm.executeUpdate();
        } catch (SQLException ex) {
        }
    }

    public List<PHIEUNHAPMON> getInformationBill(int soBan) throws Exception {
        List<PHIEUNHAPMON> pnMon = new ArrayList<>();
        Connection connect = DatabaseHelper.openConnection();
        String sql = """
                     SELECT HD.ID, MA.TENMONAN, CTHD.SOLUONG, CTHD.SOLUONG * MA.GIA
                                          FROM (SELECT ID, TENMONAN, GIA FROM MONAN) MA
                                          INNER JOIN (SELECT IDHD, IDMA, SOLUONG FROM CTHOADON) CTHD ON MA.ID = CTHD.IDMA
                                          INNER JOIN (SELECT ID, GIAMGIA, SOBAN, TRANGTHAI FROM HOADON WHERE SOBAN = ? AND TRANGTHAI = N'Chưa thanh toán') HD ON CTHD.IDHD = HD.ID
                                          INNER JOIN (SELECT ID, TRANGTHAI FROM BAN WHERE ID = ?) BAN ON BAN.ID = HD.SOBAN ORDER BY MA.TENMONAN""";
        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            stm.setInt(1, soBan);
            stm.setInt(2, soBan);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                PHIEUNHAPMON ma = new PHIEUNHAPMON();

                ma.setID(rs.getInt(1));
                ma.setTenMonAn(rs.getString(2).trim());
                ma.setSoLuong(rs.getInt(3));
                ma.setThanhTien(rs.getInt(4));

                pnMon.add(ma);
            }
        } catch (SQLException ex) {
            System.out.println("Error!");
        }
        return pnMon;
    }
    
    public Map<String, Integer> getTop5SeaFood(int Year, int Month) throws Exception {
        Map<String, Integer> result = new HashMap<String, Integer>();
        Connection connect = DatabaseHelper.openConnection();
        String sql = """
                     SELECT TOP 5 MA.TENMONAN, SUM(CTHD.SOLUONG) FROM
                     (SELECT ID FROM HOADON WITH(INDEX = IX_NGAYLAPHD) WHERE TRANGTHAI = N'Đã thanh toán' AND MONTH(NGAYLAPHOADON) = ? AND YEAR(NGAYLAPHOADON) = ?) HD,
                     MONAN MA, CTHOADON CTHD
                     WHERE MA.ID = CTHD.IDMA AND HD.ID = CTHD.IDHD
                     GROUP BY MA.TENMONAN
                     ORDER BY SUM(CTHD.SOLUONG) DESC""";
        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            stm.setInt(1, Month + 1);
            stm.setInt(2, Year);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                result.put(rs.getString(1), rs.getInt(2));
            }
        } catch (SQLException ex) {
        }
        return result;
    }
}
