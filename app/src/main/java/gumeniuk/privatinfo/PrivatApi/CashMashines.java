package gumeniuk.privatinfo.PrivatApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CashMashines {


    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("devices")
    @Expose
    private List<Device> devices = null;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

}
