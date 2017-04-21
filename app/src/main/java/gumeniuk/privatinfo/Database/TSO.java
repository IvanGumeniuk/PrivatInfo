package gumeniuk.privatinfo.Database;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ваня on 21.04.2017.
 */

public class TSO extends RealmObject {
    @PrimaryKey
    private String id;
    private String city;
    private String fullAddress;
    private String place;
    private String latitude;
    private String longitude;
    private Shedule shedule;

    public TSO() {
    }

    public TSO(String id, String city, String fullAddress, String place, String latitude, String longitude, Shedule shedule) {
        this.id = id;
        this.city = city;
        this.fullAddress = fullAddress;
        this.place = place;
        this.latitude = latitude;
        this.longitude = longitude;
        this.shedule = shedule;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
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

    public Shedule getShedule() {
        return shedule;
    }

    public void setShedule(Shedule shedule) {
        this.shedule = shedule;
    }
}
