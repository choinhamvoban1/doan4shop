package com.example.shopee.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChiTietHoaDonBan {

    @SerializedName("id")
    @Expose
    private Integer Id;
    //------------------------
    @SerializedName("idSanPham")
    @Expose
    private Integer IdSanPham;
    //-------------------------
    @SerializedName("idHoaDon")
    @Expose
    private Integer IdHoaDon;
    //---------------------------
    @SerializedName("soLuong")
    @Expose
    private Integer SoLuong;
    //--------------------------
    @SerializedName("donGia")
    @Expose
    private Integer DonGia;
    //---------------------------
    @SerializedName("thanhTien")
    @Expose
    private Integer ThanhTien;

    @SerializedName("tenSp")
    @Expose
    private String TenSp;

    @SerializedName("hinhAnh")
    @Expose
    private String HinhAnh;

    public ChiTietHoaDonBan(Integer id, Integer idSanPham, Integer idHoaDon, Integer soLuong, Integer donGia, Integer thanhTien, String tenSp, String hinhAnh) {
        Id = id;
        IdSanPham = idSanPham;
        IdHoaDon = idHoaDon;
        SoLuong = soLuong;
        DonGia = donGia;
        ThanhTien = thanhTien;
        TenSp = tenSp;
        HinhAnh = hinhAnh;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getIdSanPham() {
        return IdSanPham;
    }

    public void setIdSanPham(Integer idSanPham) {
        IdSanPham = idSanPham;
    }

    public Integer getIdHoaDon() {
        return IdHoaDon;
    }

    public void setIdHoaDon(Integer idHoaDon) {
        IdHoaDon = idHoaDon;
    }

    public Integer getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(Integer soLuong) {
        SoLuong = soLuong;
    }

    public Integer getDonGia() {
        return DonGia;
    }

    public void setDonGia(Integer donGia) {
        DonGia = donGia;
    }

    public Integer getThanhTien() {
        return ThanhTien;
    }

    public void setThanhTien(Integer thanhTien) {
        ThanhTien = thanhTien;
    }

    public String getTenSp() {
        return TenSp;
    }

    public void setTenSp(String tenSp) {
        TenSp = tenSp;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        HinhAnh = hinhAnh;
    }
}