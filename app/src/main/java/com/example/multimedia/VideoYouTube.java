package com.example.multimedia;

public class VideoYouTube {
    private String Title;
    private  String Thumbnail;
    private  String IdVideol;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
    }

    public String getIdVideol() {
        return IdVideol;
    }

    public void setIdVideol(String idVideol) {
        IdVideol = idVideol;
    }

    public VideoYouTube(String title, String thumbnail, String idVideol) {
        Title = title;
        Thumbnail = thumbnail;
        IdVideol = idVideol;
    }
}
