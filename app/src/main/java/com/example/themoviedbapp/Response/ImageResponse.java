package com.example.themoviedbapp.Response;

import com.example.themoviedbapp.Model.BackdropModel;
import com.example.themoviedbapp.Model.LogoModel;
import com.example.themoviedbapp.Model.PosterModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImageResponse {

    @SerializedName("backdrops")
    @Expose
    private List<BackdropModel> backdrops = null;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("logos")
    @Expose
    private List<LogoModel> logos = null;
    @SerializedName("posters")
    @Expose
    private List<PosterModel> posters = null;

    public List<BackdropModel> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(List<BackdropModel> backdrops) {
        this.backdrops = backdrops;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<LogoModel> getLogos() {
        return logos;
    }

    public void setLogos(List<LogoModel> logos) {
        this.logos = logos;
    }

    public List<PosterModel> getPosters() {
        return posters;
    }

    public void setPosters(List<PosterModel> posters) {
        this.posters = posters;
    }

}
