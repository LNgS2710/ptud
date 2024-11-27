package entity;

import java.sql.Date;
import java.util.Objects;

public class NhanVien {
    private String maNV;
    private String tenNV;
    private boolean gioiTinh;
    private String matKhau;
    private Date ngaySinh;
    private String sdt;
    private String cmnd;
    private String chucVu;

    public NhanVien() {
    }
    
    public NhanVien(String maNV){
        this.maNV = maNV;
    }
    
    public NhanVien(String maNV, String tenNV, boolean gioiTinh,String matKhau, Date ngaySinh, String sdt, String cmnd, String chucVu) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.gioiTinh = gioiTinh;
        this.matKhau = matKhau;
        this.ngaySinh = ngaySinh;
        this.sdt = sdt;
        this.cmnd = cmnd;
        this.chucVu = chucVu;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }
    
    public String getMatKhau() {
            return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
    
    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maNV);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        NhanVien other = (NhanVien) obj;
        return Objects.equals(maNV, other.maNV);
    }

    // toString Method
    @Override
    public String toString() {
        return "NhanVien{" +
               "maNV='" + maNV + '\'' +
               ", tenNV='" + tenNV + '\'' +
               ", gioiTinh=" + (gioiTinh ? "Nam" : "Nữ") +
               ", ngaySinh=" + ngaySinh +
               ", sdt='" + sdt + '\'' +
               ", matKhau='" + matKhau + '\'' +
               ", cmnd='" + cmnd + '\'' +
               ", chucVu='" + chucVu + '\'' +
               '}';
    }
}
