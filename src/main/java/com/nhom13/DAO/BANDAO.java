//@Author: NGUYỄN TRẦN TẤN QUY PTITHCM
package com.nhom13.DAO;

import com.nhom13.model.BAN;
import com.nhom13.helper.DatabaseHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

public class BANDAO {

    public List<BAN> getAllBAN() throws Exception {
        List<BAN> tables = new ArrayList<>();
        Connection connect = DatabaseHelper.openConnection();
        String sql = "SELECT ID, TENBAN, TRANGTHAI FROM BAN";

        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                BAN table = new BAN();
                table.setSOBAN(rs.getInt("ID"));
                table.setTENBAN(rs.getString("TENBAN"));
                table.setTRANGTHAI(rs.getString("TRANGTHAI"));

                tables.add(table);
            }
        } catch (SQLException ex) {
        }
        return tables;
    }

    public void addBAN(BAN table) throws Exception {
        System.out.println(table.getSOBAN());
        Connection connect = DatabaseHelper.openConnection();
        String sql = "INSERT INTO BAN VALUES(?, ?, ?)";

        try {
            PreparedStatement stm = connect.prepareStatement(sql);

            stm.setInt(1, table.getSOBAN());
            stm.setString(2, table.getTENBAN());
            stm.setString(3, table.getTRANGTHAI());
            
            stm.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Kiểm tra lại thông tin của bạn!", "Không hợp lệ!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void editBAN (BAN table) throws Exception {
        Connection connect = DatabaseHelper.openConnection();
        String sql = "UPDATE BAN SET TENBAN = ?, TRANGTHAI = ? WHERE ID = ?";
        
        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            
            stm.setString(1, table.getTENBAN());
            stm.setString(2, table.getTRANGTHAI());
            stm.setInt(3, table.getSOBAN());
            
            stm.executeUpdate();
        } catch (SQLException ex) {}
    }
    
    public void deleteBAN (int IDBan) throws Exception {
        Connection connect = DatabaseHelper.openConnection();
        String sql = "DELETE FROM BAN WHERE ID = ?";
        
        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            stm.setInt(1, IDBan);
            stm.executeUpdate();
        } catch (SQLException ex) {}
    }
    
    public void setStatusBAN(int IDBan) throws Exception {
        Connection connect = DatabaseHelper.openConnection();
        String sql = "UPDATE BAN SET TRANGTHAI = N'Có khách' WHERE ID = ?";
        
        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            
            stm.setInt(1, IDBan);
            stm.executeUpdate();
        } catch(SQLException ex) {}
    }
    
    public void resetStatusBAN(int IDBan) throws Exception {
        Connection connect = DatabaseHelper.openConnection();
        String sql = "UPDATE BAN SET TRANGTHAI = N'Trống' WHERE ID = ?";
        
        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            
            stm.setInt(1, IDBan);
            stm.executeUpdate();
        } catch(SQLException ex) {}
    }
    
    public int getTotalTableWithCustomers() throws Exception {
        int total = 0;
        Connection connect = DatabaseHelper.openConnection();
        String sql = "SELECT COUNT(*) FROM BAN WHERE TRANGTHAI != N'Trống'";
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
    
    public Map<String, Integer> getTop5Table(int Year, int Month) throws Exception {
        Map<String, Integer> result = new HashMap<>();
        Connection connect = DatabaseHelper.openConnection();
        String sql = """
                     SELECT TOP 5 HD.SOBAN, DOANHTHU = SUM(CTHD.SOLUONG * MA.GIA * (1 - HD.GIAMGIA / 100))
                     FROM (SELECT ID, SOBAN, GIAMGIA FROM HOADON WITH(INDEX = IX_NGAYLAPHD) WHERE TRANGTHAI = N'Đã thanh toán' AND MONTH(NGAYLAPHOADON) = ? AND YEAR(NGAYLAPHOADON) = ?) HD
                     INNER JOIN CTHOADON CTHD ON HD.ID = CTHD.IDHD
                     INNER JOIN (SELECT ID, GIA FROM MONAN) MA ON MA.ID = CTHD.IDMA
                     GROUP BY HD.SOBAN
                     ORDER BY DOANHTHU DESC""";
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
