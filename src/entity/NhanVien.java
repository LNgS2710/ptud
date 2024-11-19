package entity;

import java.util.Objects;

public class NhanVien {
    private String ma;          // Mã nhân viên (Employee ID)
    private String ten;         // Tên nhân viên (Employee Name)
    private String email;       // Email
    private String soDienThoai; // Số điện thoại (Phone Number)
    private boolean trangThai;  // Trạng thái (Active/Inactive)

    // Constructor

    public NhanVien() {
    }
    
    
    public NhanVien(String ma, String ten, String email, String soDienThoai, boolean trangThai) {
        this.ma = ma;
        this.ten = ten;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.trangThai = trangThai;
    }

    // Getters and Setters
    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.ma);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NhanVien other = (NhanVien) obj;
        return Objects.equals(this.ma, other.ma);
    }
    
    
}
