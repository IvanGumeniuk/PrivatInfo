package gumeniuk.privatinfo.PrivatApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Device {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("cityRU")
    @Expose
    private String cityRU;
    @SerializedName("cityUA")
    @Expose
    private String cityUA;
    @SerializedName("cityEN")
    @Expose
    private String cityEN;
    @SerializedName("fullAddressRu")
    @Expose
    private String fullAddressRu;
    @SerializedName("fullAddressUa")
    @Expose
    private String fullAddressUa;
    @SerializedName("fullAddressEn")
    @Expose
    private String fullAddressEn;
    @SerializedName("placeRu")
    @Expose
    private String placeRu;
    @SerializedName("placeUa")
    @Expose
    private String placeUa;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("tw")
    @Expose
    private Tw tw;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCityRU() {
        return cityRU;
    }

    public void setCityRU(String cityRU) {
        this.cityRU = cityRU;
    }

    public String getCityUA() {
        return cityUA;
    }

    public void setCityUA(String cityUA) {
        this.cityUA = cityUA;
    }

    public String getCityEN() {
        return cityEN;
    }

    public void setCityEN(String cityEN) {
        this.cityEN = cityEN;
    }

    public String getFullAddressRu() {
        return fullAddressRu;
    }

    public void setFullAddressRu(String fullAddressRu) {
        this.fullAddressRu = fullAddressRu;
    }

    public String getFullAddressUa() {
        return fullAddressUa;
    }

    public void setFullAddressUa(String fullAddressUa) {
        this.fullAddressUa = fullAddressUa;
    }

    public String getFullAddressEn() {
        return fullAddressEn;
    }

    public void setFullAddressEn(String fullAddressEn) {
        this.fullAddressEn = fullAddressEn;
    }

    public String getPlaceRu() {
        return placeRu;
    }

    public void setPlaceRu(String placeRu) {
        this.placeRu = placeRu;
    }

    public String getPlaceUa() {
        return placeUa;
    }

    public void setPlaceUa(String placeUa) {
        this.placeUa = placeUa;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Tw getTw() {
        return tw;
    }

    public void setTw(Tw tw) {
        this.tw = tw;
    }

}
