//@Author: NGUYỄN TRẦN TẤN QUY PTITHCM
package com.nhom13.DAO;

import com.nhom13.model.NHANVIEN;
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

public class NHANVIENDAO {
    public List<NHANVIEN> getAllNHANVIEN() throws Exception {
        List<NHANVIEN> staff = new ArrayList<>();
        Connection connect = DatabaseHelper.openConnection();
        String sql = "SELECT MANV, TENNV, NGAYSINH, SDT, GIOITINH, CHUCVU, HINHANH FROM NHANVIEN";
        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                NHANVIEN nv = new NHANVIEN();

                nv.setMANV(rs.getString("MANV").replaceAll("\\s", ""));
                nv.setTENNV(rs.getString("TENNV"));
                nv.setNGAYSINH(rs.getString("NGAYSINH"));
                nv.setSDT(rs.getString("SDT"));
                nv.setGIOITINH(rs.getString("GIOITINH"));
                nv.setCHUCVU(rs.getString("CHUCVU"));
                nv.setHINHANH(rs.getString("HINHANH"));
                
                staff.add(nv);
            }
        } catch (SQLException ex) {
            System.out.println("Error SQL Server!");
        }
        return staff;
    }
    
    public boolean addNHANVIEN(NHANVIEN nv) throws Exception {
        Connection connect = DatabaseHelper.openConnection();
        String sql = "INSERT INTO NHANVIEN VALUES(?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            stm.setString(1, nv.getMANV());
            stm.setString(2, nv.getTENNV());
            stm.setString(3, nv.getNGAYSINH());
            stm.setString(4, nv.getSDT());
            stm.setString(5, nv.getGIOITINH());
            stm.setString(6, nv.getCHUCVU());
            stm.setString(7, nv.getHINHANH());

            stm.executeUpdate();
        } catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, "Mã nhân viên đã tồn tại hoặc có một vài thông tin không chính xác", "Không hợp lệ!",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    public void editNHANVIEN(NHANVIEN nv, String oldMaNV) throws Exception {
        Connection connect = DatabaseHelper.openConnection();
        String sql = "UPDATE NHANVIEN SET MANV = ?, TENNV = ?, NGAYSINH = ?, SDT = ?, GIOITINH = ?, CHUCVU = ?, HINHANH = ? WHERE MANV = ?";
        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            stm.setString(1, nv.getMANV());
            stm.setString(2, nv.getTENNV());
            stm.setString(3, nv.getNGAYSINH());
            stm.setString(4, nv.getSDT());
            stm.setString(5, nv.getGIOITINH());
            stm.setString(6, nv.getCHUCVU());
            stm.setString(7, nv.getHINHANH());
            stm.setString(8, oldMaNV);

            stm.executeUpdate();
        } catch(SQLException ex) {
        }
    }
    
    public void deleteNHANVIEN(String maNV) throws Exception {
        Connection connect = DatabaseHelper.openConnection();
        String sql = "DELETE FROM NHANVIEN WHERE MANV = ?";
        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            stm.setString(1, maNV);

            stm.executeUpdate();
        } catch(SQLException ex) {
        }
    }
    
    public List<String> getAccountAndPassWordOfStaff(String maNV) throws Exception {
        List<String> information = new ArrayList<>();
        String account;
        String password;
        Connection connect = DatabaseHelper.openConnection();
        String sql = "{call getAccountAndPassWordOfStaff(?, ?, ?)}";
        try {
            CallableStatement cstmt = connect.prepareCall(sql);
            cstmt.setString(1, maNV);
            cstmt.registerOutParameter(2, Types.NVARCHAR);
            cstmt.registerOutParameter(3, Types.NVARCHAR);
            cstmt.execute();
            account = cstmt.getString(2);
            password = cstmt.getString(3);
            information.add(account.trim());
            information.add(password.trim());
        } catch (SQLException ex) {
        }
        return information;
    }
    
    public List<NHANVIEN> searchAllNHANVIENByMANV(String maNV) throws Exception {
        List<NHANVIEN> staff = new ArrayList<>();

        Connection connect = DatabaseHelper.openConnection();
        String sql = "SELECT MANV, TENNV, NGAYSINH, SDT, GIOITINH, CHUCVU, HINHANH FROM NHANVIEN WHERE MANV LIKE ?";
        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            stm.setString(1, "%" + maNV + "%");
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                NHANVIEN nv = new NHANVIEN();

                nv.setMANV(rs.getString("MANV").replaceAll("\\s", ""));
                nv.setTENNV(rs.getString("TENNV"));
                nv.setNGAYSINH(rs.getString("NGAYSINH"));
                nv.setSDT(rs.getString("SDT"));
                nv.setGIOITINH(rs.getString("GIOITINH"));
                nv.setCHUCVU(rs.getString("CHUCVU"));
                nv.setHINHANH(rs.getString("HINHANH"));
                
                staff.add(nv);
            }
        } catch (SQLException ex) {
        }
        return staff;
    }
    
    public List<NHANVIEN> searchAllNHANVIENByTENNV(String tenNV) throws Exception {
        List<NHANVIEN> staff = new ArrayList<>();

        Connection connect = DatabaseHelper.openConnection();
        String sql = "SELECT MANV, TENNV, NGAYSINH, SDT, GIOITINH, CHUCVU, HINHANH FROM NHANVIEN WHERE TENNV LIKE ?";
        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            stm.setString(1, "%" + tenNV + "%");
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                NHANVIEN nv = new NHANVIEN();

                nv.setMANV(rs.getString("MANV").replaceAll("\\s", ""));
                nv.setTENNV(rs.getString("TENNV"));
                nv.setNGAYSINH(rs.getString("NGAYSINH"));
                nv.setSDT(rs.getString("SDT"));
                nv.setGIOITINH(rs.getString("GIOITINH"));
                nv.setCHUCVU(rs.getString("CHUCVU"));
                nv.setHINHANH(rs.getString("HINHANH"));
                
                staff.add(nv);
            }
        } catch (SQLException ex) {
        }
        return staff;
    }
    
    public List<NHANVIEN> searchAllNHANVIENByNAMSINH(String namSinh) throws Exception {
        List<NHANVIEN> staff = new ArrayList<>();

        Connection connect = DatabaseHelper.openConnection();
        String sql = "SELECT MANV, TENNV, NGAYSINH, SDT, GIOITINH, CHUCVU, HINHANH FROM NHANVIEN WHERE YEAR(NGAYSINH) = ?";
        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            stm.setInt(1, Integer.parseInt(namSinh));
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                NHANVIEN nv = new NHANVIEN();

                nv.setMANV(rs.getString("MANV").replaceAll("\\s", ""));
                nv.setTENNV(rs.getString("TENNV"));
                nv.setNGAYSINH(rs.getString("NGAYSINH"));
                nv.setSDT(rs.getString("SDT"));
                nv.setGIOITINH(rs.getString("GIOITINH"));
                nv.setCHUCVU(rs.getString("CHUCVU"));
                nv.setHINHANH(rs.getString("HINHANH"));
                
                staff.add(nv);
            }
        } catch (SQLException ex) {
        }
        return staff;
    }
    
    public List<NHANVIEN> searchAllNHANVIENBySDT(String SDT) throws Exception {
        List<NHANVIEN> staff = new ArrayList<>();

        Connection connect = DatabaseHelper.openConnection();
        String sql = "SELECT MANV, TENNV, NGAYSINH, SDT, GIOITINH, CHUCVU, HINHANH FROM NHANVIEN WHERE SDT LIKE ?";
        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            stm.setString(1, "%" + SDT + "%");
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                NHANVIEN nv = new NHANVIEN();

                nv.setMANV(rs.getString("MANV").replaceAll("\\s", ""));
                nv.setTENNV(rs.getString("TENNV"));
                nv.setNGAYSINH(rs.getString("NGAYSINH"));
                nv.setSDT(rs.getString("SDT"));
                nv.setGIOITINH(rs.getString("GIOITINH"));
                nv.setCHUCVU(rs.getString("CHUCVU"));
                nv.setHINHANH(rs.getString("HINHANH"));
                
                staff.add(nv);
            }
        } catch (SQLException ex) {
        }
        return staff;
    }
    
    public List<NHANVIEN> searchAllNHANVIENByGIOITINH(String gioiTinh) throws Exception {
        List<NHANVIEN> staff = new ArrayList<>();

        Connection connect = DatabaseHelper.openConnection();
        String sql = "SELECT MANV, TENNV, NGAYSINH, SDT, GIOITINH, CHUCVU, HINHANH FROM NHANVIEN WHERE GIOITINH LIKE ?";
        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            stm.setString(1, "%" + gioiTinh + "%");
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                NHANVIEN nv = new NHANVIEN();

                nv.setMANV(rs.getString("MANV").replaceAll("\\s", ""));
                nv.setTENNV(rs.getString("TENNV"));
                nv.setNGAYSINH(rs.getString("NGAYSINH"));
                nv.setSDT(rs.getString("SDT"));
                nv.setGIOITINH(rs.getString("GIOITINH"));
                nv.setCHUCVU(rs.getString("CHUCVU"));
                nv.setHINHANH(rs.getString("HINHANH"));
                
                staff.add(nv);
            }
        } catch (SQLException ex) {
        }
        return staff;
    }
    
    public List<NHANVIEN> searchAllNHANVIENByCHUCVU(String chucVu) throws Exception {
        List<NHANVIEN> staff = new ArrayList<>();

        Connection connect = DatabaseHelper.openConnection();
        String sql = "SELECT MANV, TENNV, NGAYSINH, SDT, GIOITINH, CHUCVU, HINHANH FROM NHANVIEN WHERE CHUCVU LIKE ?";
        try {
            PreparedStatement stm = connect.prepareStatement(sql);
            stm.setString(1, "%" + chucVu + "%");
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                NHANVIEN nv = new NHANVIEN();

                nv.setMANV(rs.getString("MANV").replaceAll("\\s", ""));
                nv.setTENNV(rs.getString("TENNV"));
                nv.setNGAYSINH(rs.getString("NGAYSINH"));
                nv.setSDT(rs.getString("SDT"));
                nv.setGIOITINH(rs.getString("GIOITINH"));
                nv.setCHUCVU(rs.getString("CHUCVU"));
                nv.setHINHANH(rs.getString("HINHANH"));
                
                staff.add(nv);
            }
        } catch (SQLException ex) {
        }
        return staff;
    }
    
    public int getTotalEmployee() throws Exception {
        int total = 0;
        Connection connect = DatabaseHelper.openConnection();
        String sql = "SELECT COUNT(*) FROM NHANVIEN WHERE CHUCVU != N'Quản lý'";
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
