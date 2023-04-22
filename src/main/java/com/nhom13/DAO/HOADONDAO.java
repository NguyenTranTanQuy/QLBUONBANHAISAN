//@Author: NGUYỄN TRẦN TẤN QUY PTITHCM
package com.nhom13.DAO;

import com.nhom13.helper.DatabaseHelper;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HOADONDAO {
    public void addHOADON(int giamGia, int soBan) throws Exception{
        Connection connect = DatabaseHelper.openConnection();
        String sql = "INSERT INTO HOADON(GIAMGIA, SOBAN) VALUES(?, ?)";
        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            stm.setInt(1, giamGia);
            stm.setInt(2, soBan);
            
            stm.executeUpdate();
        } catch (SQLException ex){
        }
    }
    
    public int getIDByNumberTable(int soBan) throws Exception {
        int ID = 0;
        Connection connect = DatabaseHelper.openConnection();
        String sql = "{call getIDByNumberTable(?, ?, ?)}";
        try {
            CallableStatement cstmt = connect.prepareCall(sql);
            cstmt.setInt(1, soBan);
            cstmt.registerOutParameter(2, Types.INTEGER);
            cstmt.registerOutParameter(3, Types.DATE);
            cstmt.execute();
            ID = cstmt.getInt(2);
        } catch (SQLException ex) {
        }
        return ID;
    }
    
    public int getCountBill() throws Exception {
        int total = 0;
        Connection connect = DatabaseHelper.openConnection();
        String sql = "SELECT COUNT(*) FROM HOADON";
        try {
            Statement stm = connect.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            rs.next();
            total = rs.getInt(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return total;
    }
    
    public int getCountBillToday() throws Exception {
        int total = 0;
        Connection connect = DatabaseHelper.openConnection();
        String sql = "SELECT COUNT(*) FROM HOADON WITH(INDEX = IX_NGAYLAPHD) WHERE CONVERT(DATE, NGAYLAPHOADON) = CONVERT(DATE, GETDATE())";
        try {
            Statement stm = connect.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            rs.next();
            total = rs.getInt(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return total;
    }
    
    public Map<String, Integer> getTotalRevenueByMonth(int Year, int Month) throws Exception {
        Map<String, Integer> result = new HashMap<String, Integer>();
        Connection connect = DatabaseHelper.openConnection();
        String sql = """
                     SELECT DAY(HD.NGAY), SUM(CTHOADON.SOLUONG * MONAN.GIA * (1 - HD.GIAMGIA / 100)) 
                     FROM (SELECT ID, GIAMGIA, NGAY = CONVERT(DATE, NGAYLAPHOADON) FROM HOADON 
                     WHERE TRANGTHAI = N'Đã thanh toán' AND MONTH(NGAYLAPHOADON) = ? AND YEAR(NGAYLAPHOADON) = ?) HD
                     INNER JOIN CTHOADON ON HD.ID = CTHOADON.IDHD
                     INNER JOIN MONAN ON MONAN.ID = CTHOADON.IDMA
                     GROUP BY HD.NGAY;""";
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
    
    public int getTotalRevenue() throws Exception {
        int total = 0;
        Connection connect = DatabaseHelper.openConnection();
        String sql = """
                     SELECT SUM(CTHOADON.SOLUONG * MONAN.GIA * (1 - HD.GIAMGIA / 100)) 
                     FROM (SELECT ID, GIAMGIA FROM HOADON WHERE TRANGTHAI = N'Đã thanh toán') HD
                     INNER JOIN CTHOADON ON HD.ID = CTHOADON.IDHD
                     INNER JOIN MONAN ON MONAN.ID = CTHOADON.IDMA""";
        try {
            Statement stm = connect.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            rs.next();
            total = rs.getInt(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return total;
    }
    
    public int getTotalRevenueToday() throws Exception {
        int total = 0;
        Connection connect = DatabaseHelper.openConnection();
        String sql = """
                     SELECT SUM(CTHOADON.SOLUONG * MONAN.GIA * (1 - HD.GIAMGIA / 100))
                            FROM (SELECT ID, GIAMGIA FROM HOADON WITH(INDEX = IX_NGAYLAPHD)
                            WHERE TRANGTHAI = N'Đã thanh toán' AND CONVERT(DATE, NGAYLAPHOADON) = CONVERT(DATE, GETDATE())) HD
                            INNER JOIN CTHOADON ON HD.ID = CTHOADON.IDHD
                            INNER JOIN MONAN ON MONAN.ID = CTHOADON.IDMA""";
        try {
            Statement stm = connect.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            rs.next();
            total = rs.getInt(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return total;
    }
    
    public void resetStatusHD(int soBan, int giamGia) throws Exception {
        Connection connect = DatabaseHelper.openConnection();
        String sql = "UPDATE HOADON SET GIAMGIA = ?, TRANGTHAI = N'Đã thanh toán' WHERE SOBAN = ? AND ID = (SELECT MAX(ID) FROM HOADON WHERE SOBAN = ? AND TRANGTHAI != N'Đã thanh toán')";
        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            stm.setInt(1, giamGia);
            stm.setInt(2, soBan);
            stm.setInt(3, soBan);
            
            stm.executeUpdate();
        } catch(SQLException ex){
        }
    }
    
    public List<Integer> showMonth() throws Exception {
        List<Integer> result = new ArrayList<>();
        Connection connect = DatabaseHelper.openConnection();
        String sql = "SELECT DISTINCT NUMBER FROM master..spt_values WHERE NUMBER BETWEEN 1 AND 12";
        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            
            while (rs.next()) {
                result.add(rs.getInt(1));
            }
        } catch (SQLException ex) {
        }
        return result;
    }
    
    public List<Integer> showYear() throws Exception {
        List<Integer> result = new ArrayList<>();
        Connection connect = DatabaseHelper.openConnection();
        String sql = """
                     SELECT YEAR(NGAYLAPHOADON) FROM HOADON WITH(INDEX = IX_NGAYLAPHD)
                     GROUP BY YEAR(NGAYLAPHOADON)
                     ORDER BY YEAR(NGAYLAPHOADON) DESC""";
        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            
            while (rs.next()) {
                result.add(rs.getInt(1));
            }
        } catch (SQLException ex) {
        }
        return result;
    }
}
