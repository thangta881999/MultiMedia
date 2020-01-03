package com.example.multimedia;

public class Kenh {
    private String TenKenh;
    private int Hinh;

    public Kenh(String tenKenh, int hinh) {
        TenKenh = tenKenh;
        Hinh = hinh;
    }

    public String getTenKenh() {
        return TenKenh;
    }

    public void setTenKenh(String tenKenh) {
        TenKenh = tenKenh;
    }

    public int getHinh() {
        return Hinh;
    }

    public void setHinh(int hinh) {
        Hinh = hinh;
    }
}

