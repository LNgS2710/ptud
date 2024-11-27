package entity;

import java.util.Objects;

public class MonAn {

    public String ten;
    public String ma;
    public double gia;
    public String loai;
    public int soLuong;
    public boolean trangThai;
    public String imgPath;

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public MonAn(String ten, String ma, double gia, String loai, int soLuong, boolean trangThai, String imgPath) {
        this.ten = ten;
        this.ma = ma;
        this.gia = gia;
        this.loai = loai;
        this.soLuong = soLuong;
        this.trangThai = trangThai;
        this.imgPath = imgPath;
    }

    public MonAn(String ten, String ma, String loai, boolean trangThai) {
        this.ten = ten;
        this.ma = ma;
        this.loai = loai;
        this.trangThai = trangThai;
    }
    
    public MonAn(){};

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.ten);
        hash = 37 * hash + Objects.hashCode(this.ma);
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
        final MonAn other = (MonAn) obj;
        if (!Objects.equals(this.ten, other.ten)) {
            return false;
        }
        return Objects.equals(this.ma, other.ma);
    }

}
