package entity;

import java.util.Date;
import java.util.Objects;

public class PhieuDatBan {
    private String maPDB;
    private Ban ban;
    private KhachHang khachHang;
    private NhanVien nhanVienLap;
    private Date thoiGianDat;
    private Date thoiGianNhanBan;
    private boolean trangThai;

    public PhieuDatBan(String maPDB, Ban ban, KhachHang khachHang, NhanVien nhanVienLap, Date thoiGianDat, Date thoiGianNhanBan, boolean trangThai) {
        this.maPDB = maPDB;
        this.ban = ban;
        this.khachHang = khachHang;
        this.nhanVienLap = nhanVienLap;
        this.thoiGianDat = thoiGianDat;
        this.thoiGianNhanBan = thoiGianNhanBan;
        this.trangThai = trangThai;
    }

    public PhieuDatBan(Ban ban, KhachHang khachHang, NhanVien nhanVienLap, Date thoiGianNhanBan) {
        this.ban = ban;
        this.khachHang = khachHang;
        this.nhanVienLap = nhanVienLap;
        this.thoiGianDat = new Date();
        this.thoiGianNhanBan = thoiGianNhanBan;
        this.trangThai = true;
    }

    public PhieuDatBan() {
    }


    public String getMaPDB() {
        return maPDB;
    }

    public void setMaPDB(String maPDB) {
        this.maPDB = maPDB;
    }

    public Ban getBan() {
        return ban;
    }

    public void setBan(Ban ban) {
        this.ban = ban;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public NhanVien getNhanVienLap() {
        return nhanVienLap;
    }

    public void setNhanVienLap(NhanVien nhanVienLap) {
        this.nhanVienLap = nhanVienLap;
    }

    public Date getThoiGianDat() {
        return thoiGianDat;
    }

    public void setThoiGianDat(Date thoiGianDat) {
        this.thoiGianDat = thoiGianDat;
    }

    public Date getThoiGianNhanBan() {
        return thoiGianNhanBan;
    }

    public void setThoiGianNhanBan(Date thoiGianNhanBan) {
        this.thoiGianNhanBan = thoiGianNhanBan;
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
        hash = 37 * hash + Objects.hashCode(this.maPDB);
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
        return Objects.equals(this.maPDB, other.maPDB);
    }

    @Override
    public String toString() {
        return "PhieuDatBan{" + "maPDB=" + maPDB + ", ban=" + ban + ", khachHang=" + khachHang + ", nhanVienLap=" + nhanVienLap + ", thoiGianDat=" + thoiGianDat + ", thoiGianNhaBan=" + thoiGianNhanBan + ", trangThai=" + trangThai + '}';
    }
    
    
}
