/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Thành Trung
 */
public class KhuyenMai {
    private String maKM; //
    private String moTa;
    private float chietKhau; //
    private boolean trangThai; // 
    
    public KhuyenMai() {
        super();
    }

    public KhuyenMai(String maKM) {
        this.maKM = maKM;
    }
    public KhuyenMai(String maKM, String moTa, float chietKhau, boolean trangThai) {
        this.maKM = maKM;
        this.moTa = moTa;
        this.chietKhau = chietKhau;
        this.trangThai = trangThai;
    }

    public String getMaKM() {
        return maKM;
    }

    public void setMaKM(String maKM) {
        this.maKM = maKM;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public float getChietKhau() {
        return chietKhau;
    }

    public void setChietKhau(float chietKhau) {
        this.chietKhau = chietKhau;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

}
