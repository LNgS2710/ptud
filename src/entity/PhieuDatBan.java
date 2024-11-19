package entity;

import java.util.Date;
import java.util.Objects;

public class PhieuDatBan {
    private String maPhieuDat; // Primary key
    private String maKhachHang; // Foreign key referencing KhachHang
    private String maBan; // Foreign key referencing Ban
    private Date ngayDat; // Date of the reservation
    private int soCho; // Number of guests
    private boolean trangThai; // Status of the reservation (e.g., confirmed, canceled)

    // Default constructor
    public PhieuDatBan() {}

    // Constructor with parameters
    public PhieuDatBan(String maPhieuDat, String maKhachHang, String maBan, Date ngayDat, int soCho, boolean trangThai) {
        this.maPhieuDat = maPhieuDat;
        this.maKhachHang = maKhachHang;
        this.maBan = maBan;
        this.ngayDat = ngayDat;
        this.soCho = soCho;
        this.trangThai = trangThai;
    }

    // Getters and setters
    public String getMaPhieuDat() {
        return maPhieuDat;
    }

    public void setMaPhieuDat(String maPhieuDat) {
        this.maPhieuDat = maPhieuDat;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getMaBan() {
        return maBan;
    }

    public void setMaBan(String maBan) {
        this.maBan = maBan;
    }

    public Date getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(Date ngayDat) {
        this.ngayDat = ngayDat;
    }

    public int getSoCho() {
        return soCho;
    }

    public void setSoCho(int soCho) {
        this.soCho = soCho;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.maPhieuDat);
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
        final PhieuDatBan other = (PhieuDatBan) obj;
        return Objects.equals(this.maPhieuDat, other.maPhieuDat);
    }
    
    
}
