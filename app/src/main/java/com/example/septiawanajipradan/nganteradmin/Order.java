package com.example.septiawanajipradan.nganteradmin;

/**
 * Created by Septiawan Aji Pradan on 7/14/2017.
 */

public class Order {
    private String idOrder;
    private String namaPemesan;
    private String pesanan;
    private String jamAntar;
    private String noTelp;
    private String lokasiAntar;
    private String waktuPesan;
    private String kategori;
    private String status;
    private String info;

    public Order(String idOrder,String namaPemesan,String pesanan,String jamAntar,String noTelp,String lokasiAntar,String waktuPesan,String status,String info){
        this.idOrder = idOrder;
        this.namaPemesan = namaPemesan;
        this.pesanan = pesanan;
        this.jamAntar = jamAntar;
        this.noTelp = noTelp;
        this.lokasiAntar = lokasiAntar;
        this.waktuPesan = waktuPesan;
        this.setStatus(status);
        this.info = info;
    }
    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getNamaPemesan() {
        return namaPemesan;
    }

    public void setNamaPemesan(String namaPemesan) {
        this.namaPemesan = namaPemesan;
    }

    public String getPesanan() {
        return pesanan;
    }

    public void setPesanan(String pesanan) {
        this.pesanan = pesanan;
    }

    public String getJamAntar() {
        return jamAntar;
    }

    public void setJamAntar(String jamAntar) {
        this.jamAntar = jamAntar;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getLokasiAntar() {
        return lokasiAntar;
    }

    public void setLokasiAntar(String lokasiAntar) {
        this.lokasiAntar = lokasiAntar;
    }

    public String getWaktuPesan() {
        return waktuPesan;
    }

    public void setWaktuPesan(String waktuPesan) {
        this.waktuPesan = waktuPesan;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
