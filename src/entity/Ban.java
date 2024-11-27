package entity;

import java.util.Objects;

public class Ban {

    private String maBan;
    private int soThuTu;
    private String viTri;
    private int soCho;
    private String trangThai;

    public Ban() {
    }

    public Ban(String maBan, int soThuTu, String viTri, int soCho, String trangThai) {
        this.maBan = maBan;
        this.soThuTu = soThuTu;
        this.viTri = viTri;
        this.soCho = soCho;
        this.trangThai = trangThai;
    }

    public String getMaBan() {
        return maBan;
    }

    public void setMaBan(String maBan) {
        this.maBan = maBan;
    }

    public int getSoThuTu() {
        return soThuTu;
    }

    public void setSoThuTu(int soThuTu) {
        this.soThuTu = soThuTu;
    }

    public String getViTri() {
        return viTri;
    }

    public void setViTri(String viTri) {
        this.viTri = viTri;
    }

    public int getSoCho() {
        return soCho;
    }

    public void setSoCho(int soCho) {
        this.soCho = soCho;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maBan);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Ban other = (Ban) obj;
        return Objects.equals(maBan, other.maBan);
    }

    @Override
    public String toString() {
        return "Ban{" +
                "maBan='" + maBan + '\'' +
                ", soThuTu=" + soThuTu +
                ", viTri='" + viTri + '\'' +
                ", soCho=" + soCho +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
}
