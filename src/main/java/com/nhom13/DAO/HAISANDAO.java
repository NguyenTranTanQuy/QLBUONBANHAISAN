//@Author: NGUYỄN TRẦN TẤN QUY PTITHCM
package com.nhom13.DAO;

import com.nhom13.model.HAISAN;
import com.nhom13.helper.DatabaseHelper;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class HAISANDAO {

    public List<HAISAN> getAllHAISAN() throws Exception {
        List<HAISAN> seaFood = new ArrayList<>();

        Connection connect = DatabaseHelper.openConnection();
        String sql = "SELECT MAHS, TENHS, GIA, HINHANH FROM HAISAN";
        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                HAISAN haisan = new HAISAN();

                haisan.setMAHS(rs.getString("MAHS").replaceAll("\\s", ""));
                haisan.setTENHS(rs.getString("TENHS"));
                haisan.setGIA(rs.getInt("GIA"));
                haisan.setHINHANH(rs.getString("HINHANH"));

                seaFood.add(haisan);
            }
        } catch (SQLException ex) {
        }
        return seaFood;
    }

    public boolean addHAISAN(HAISAN seaFood) throws Exception {
        Connection connect = DatabaseHelper.openConnection();
        String sql = "INSERT INTO HAISAN VALUES(?, ?, ?, ?)";

        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            stm.setString(1, seaFood.getMAHS());
            stm.setString(2, seaFood.getTENHS());
            stm.setInt(3, seaFood.getGIA());
            stm.setString(4, seaFood.getHINHANH());

            stm.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Mã hải sản đã tồn tại hoặc có một vài thông tin không đúng", "Không hợp lệ!",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public void editHAISAN(HAISAN seaFood) throws Exception {
        Connection connect = DatabaseHelper.openConnection();
        String sql = "UPDATE HAISAN SET TENHS = ?, GIA = ?, HINHANH = ? WHERE MAHS = ?";

        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            stm.setString(1, seaFood.getTENHS());
            stm.setInt(2, seaFood.getGIA());
            stm.setString(3, seaFood.getHINHANH());
            stm.setString(4, seaFood.getMAHS());

            stm.executeUpdate();
        } catch (SQLException ex) {
        }
    }

    public void deleteHAISAN(String maHS) throws Exception {
        Connection connect = DatabaseHelper.openConnection();
        String sql = "DELETE FROM HAISAN WHERE MAHS = ?";

        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            stm.setString(1, maHS);

            stm.executeUpdate();
        } catch (SQLException ex) {
        }
    }

    public HAISAN getHAISANByMAHS(String maHS) throws Exception {
        HAISAN seaFood = new HAISAN();
        Connection connect = DatabaseHelper.openConnection();
        String sql = "SELECT TENHS, GIA, HINHANH FROM HAISAN WHERE MAHS = ?";

        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            stm.setString(1, maHS);

            ResultSet rs = stm.executeQuery();
            seaFood.setTENHS(rs.getString("TENHS"));
            seaFood.setGIA(rs.getInt("GIA"));
            seaFood.setHINHANH(rs.getString("HINHANH"));
            seaFood.setMAHS(maHS);

        } catch (SQLException ex) {
        }

        return seaFood;
    }

    public List<HAISAN> searchAllHAISANByMAHS(String maHS) throws Exception {
        List<HAISAN> seaFood = new ArrayList<>();

        Connection connect = DatabaseHelper.openConnection();
        String sql = "SELECT MAHS, TENHS, GIA, HINHANH FROM HAISAN WHERE MAHS LIKE ?";
        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            stm.setString(1, "%" + maHS + "%");
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                HAISAN haisan = new HAISAN();

                haisan.setMAHS(rs.getString("MAHS").replaceAll("\\s", ""));
                haisan.setTENHS(rs.getString("TENHS"));
                haisan.setGIA(rs.getInt("GIA"));
                haisan.setHINHANH(rs.getString("HINHANH"));
                seaFood.add(haisan);
            }
        } catch (SQLException ex) {
        }
        return seaFood;
    }

    public List<HAISAN> searchAllHAISANByTENHS(String tenHS) throws Exception {
        List<HAISAN> seaFood = new ArrayList<>();

        Connection connect = DatabaseHelper.openConnection();
        String sql = "SELECT MAHS, TENHS, GIA, HINHANH FROM HAISAN WHERE TENHS LIKE ?";
        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            stm.setString(1, "%" + tenHS + "%");
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                HAISAN haisan = new HAISAN();

                haisan.setMAHS(rs.getString("MAHS").replaceAll("\\s", ""));
                haisan.setTENHS(rs.getString("TENHS"));
                haisan.setGIA(rs.getInt("GIA"));
                haisan.setHINHANH(rs.getString("HINHANH"));

                seaFood.add(haisan);
            }
        } catch (SQLException ex) {
        }
        return seaFood;
    }

    public List<HAISAN> sortAllHAISANByTENHS() throws Exception {
        List<HAISAN> seaFood = new ArrayList<>();

        Connection connect = DatabaseHelper.openConnection();
        String sql = "SELECT MAHS, TENHS, GIA, HINHANH FROM HAISAN ORDER BY TENHS";
        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                HAISAN haisan = new HAISAN();

                haisan.setMAHS(rs.getString("MAHS").replaceAll("\\s", ""));
                haisan.setTENHS(rs.getString("TENHS"));
                haisan.setGIA(rs.getInt("GIA"));
                haisan.setHINHANH(rs.getString("HINHANH"));

                seaFood.add(haisan);
            }
        } catch (SQLException ex) {
        }
        return seaFood;
    }

    public List<HAISAN> searchAllHAISANByGIA(String GIA) throws Exception {
        List<HAISAN> seaFood = new ArrayList<>();

        Connection connect = DatabaseHelper.openConnection();
        String sql = "SELECT MAHS, TENHS, GIA, HINHANH FROM HAISAN WHERE GIA = ?";
        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            stm.setString(1, GIA);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                HAISAN haisan = new HAISAN();

                haisan.setMAHS(rs.getString("MAHS").replaceAll("\\s", ""));
                haisan.setTENHS(rs.getString("TENHS"));
                haisan.setGIA(rs.getInt("GIA"));
                haisan.setHINHANH(rs.getString("HINHANH"));

                seaFood.add(haisan);
            }
        } catch (SQLException ex) {
        }
        return seaFood;
    }

    public List<HAISAN> sortAllHAISANByGIA() throws Exception {
        List<HAISAN> seaFood = new ArrayList<>();

        Connection connect = DatabaseHelper.openConnection();
        String sql = "SELECT MAHS, TENHS, GIA, HINHANH FROM HAISAN ORDER BY GIA";
        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                HAISAN haisan = new HAISAN();

                haisan.setMAHS(rs.getString("MAHS").replaceAll("\\s", ""));
                haisan.setTENHS(rs.getString("TENHS"));
                haisan.setGIA(rs.getInt("GIA"));
                haisan.setHINHANH(rs.getString("HINHANH"));

                seaFood.add(haisan);
            }
        } catch (SQLException ex) {
        }
        return seaFood;
    }

    public List<HAISAN> sortAllHAISANBySOLUONG() throws Exception {
        List<HAISAN> seaFood = new ArrayList<>();

        Connection connect = DatabaseHelper.openConnection();
        String sql = """
                     SELECT HS.MAHS, HS.TENHS, HS.GIA, HS.HINHANH, ISNULL(SUM(CTPN.SOLUONG), 0) AS SOLUONG
                     FROM (SELECT MAHS, TENHS, GIA, HINHANH FROM HAISAN) HS LEFT JOIN (SELECT MAHS, SOLUONG FROM CTPHIEUNHAP) CTPN ON HS.MAHS = CTPN.MAHS
                     GROUP BY HS.MAHS, HS.TENHS, HS.GIA, HS.HINHANH
                     ORDER BY SOLUONG""";
        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                HAISAN haisan = new HAISAN();

                haisan.setMAHS(rs.getString("MAHS").replaceAll("\\s", ""));
                haisan.setTENHS(rs.getString("TENHS"));
                haisan.setGIA(rs.getInt("GIA"));
                haisan.setHINHANH(rs.getString("HINHANH"));

                seaFood.add(haisan);
            }
        } catch (SQLException ex) {
        }
        return seaFood;
    }

    public List<String> getMountAndUnitOfSeaFood(String maHS) throws Exception {
        List<String> information = new ArrayList<>();
        String soLuong;
        String donVi;
        Connection connect = DatabaseHelper.openConnection();
        String sql = "{call getMountAndUnitOfSeaFood(?, ?, ?)}";
        try {
            CallableStatement cstmt = connect.prepareCall(sql);
            cstmt.setString(1, maHS);
            cstmt.registerOutParameter(2, Types.NVARCHAR);
            cstmt.registerOutParameter(3, Types.NVARCHAR);
            cstmt.execute();
            soLuong = cstmt.getString(2);
            donVi = cstmt.getString(3);
            
            information.add(soLuong);
            information.add(donVi);
        } catch (SQLException ex) {
        }
        return information;
    }
    
    public int getTotalSeaFood() throws Exception {
        int total = 0;
        Connection connect = DatabaseHelper.openConnection();
        String sql = "SELECT COUNT(MAHS) FROM HAISAN";
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
    
    public int getTotalSeaFoodInventory() throws Exception {
        int total = 0;
        Connection connect = DatabaseHelper.openConnection();
        String sql = "SELECT COUNT(HAISAN.MAHS) FROM HAISAN, (SELECT DISTINCT MAHS FROM CTPHIEUNHAP) CTPN WHERE HAISAN.MAHS = CTPN.MAHS";
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
}
