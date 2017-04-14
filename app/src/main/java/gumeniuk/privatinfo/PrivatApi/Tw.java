package gumeniuk.privatinfo.PrivatApi;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tw {

    @SerializedName("mon")
    @Expose
    private String mon;
    @SerializedName("tue")
    @Expose
    private String tue;
    @SerializedName("wed")
    @Expose
    private String wed;
    @SerializedName("thu")
    @Expose
    private String thu;
    @SerializedName("fri")
    @Expose
    private String fri;
    @SerializedName("sat")
    @Expose
    private String sat;
    @SerializedName("sun")
    @Expose
    private String sun;
    @SerializedName("hol")
    @Expose
    private String hol;

    public String getMon() {
        return mon;
    }

    public void setMon(String mon) {
        this.mon = mon;
    }

    public String getTue() {
        return tue;
    }

    public void setTue(String tue) {
        this.tue = tue;
    }

    public String getWed() {
        return wed;
    }

    public void setWed(String wed) {
        this.wed = wed;
    }

    public String getThu() {
        return thu;
    }

    public void setThu(String thu) {
        this.thu = thu;
    }

    public String getFri() {
        return fri;
    }

    public void setFri(String fri) {
        this.fri = fri;
    }

    public String getSat() {
        return sat;
    }

    public void setSat(String sat) {
        this.sat = sat;
    }

    public String getSun() {
        return sun;
    }

    public void setSun(String sun) {
        this.sun = sun;
    }

    public String getHol() {
        return hol;
    }

    public void setHol(String hol) {
        this.hol = hol;
    }

}
