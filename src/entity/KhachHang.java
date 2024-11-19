package entity;

import java.sql.Date;
import java.util.Objects;

public class KhachHang {

    private String makhachhang;
    private String tenkhachhang;
    private boolean gioitinh;
    private String sodienthoai;
    private Date ngaysinh;
    private String cmnd;

    public String getMakhachhang() {
        return makhachhang;
    }

    public void setMakhachhang(String makhachhang) {
        this.makhachhang = makhachhang;
    }

    public String getTenkhachhang() {
        return tenkhachhang;
    }

    public void setTenkhachhang(String tenkhachhang) {
        this.tenkhachhang = tenkhachhang;
    }

    public boolean isGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(boolean gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public Date getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(Date ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public KhachHang() {
    }

    public KhachHang(String makhachhang, String tenkhachhang, boolean gioitinh, String sodienthoai, Date ngaysinh, String cmnd) {
        this.makhachhang = makhachhang;
        this.tenkhachhang = tenkhachhang;
        this.gioitinh = gioitinh;
        this.sodienthoai = sodienthoai;
        this.ngaysinh = ngaysinh;
        this.cmnd = cmnd;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.makhachhang);
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
        final KhachHang other = (KhachHang) obj;
        return Objects.equals(this.makhachhang, other.makhachhang);
    }
    
    
}
