package gumeniuk.privatinfo.Database;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ваня on 21.04.2017.
 */

public class Shedule extends RealmObject {
    @PrimaryKey
    private String id;
    private String mon;
    private String tue;
    private String wed;
    private String thu;
    private String fri;
    private String sat;
    private String sun;
    private String hol;

    public Shedule() {
    }

    public Shedule(String id, String mon, String tue, String wed, String thu, String fri, String sat, String sun, String hol) {
        this.id = id;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thu = thu;
        this.fri = fri;
        this.sat = sat;
        this.sun = sun;
        this.hol = hol;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setMon(String mon) {
        this.mon = mon;
    }

    public void setTue(String tue) {
        this.tue = tue;
    }

    public void setWed(String wed) {
        this.wed = wed;
    }

    public void setThu(String thu) {
        this.thu = thu;
    }

    public void setFri(String fri) {
        this.fri = fri;
    }

    public void setSat(String sat) {
        this.sat = sat;
    }

    public void setSun(String sun) {
        this.sun = sun;
    }

    public void setHol(String hol) {
        this.hol = hol;
    }

    @Override
    public String toString() {
        return "Расписание:" +
                "\n mon=" + mon +
                "\n tue=" + tue +
                "\n wed=" + wed +
                "\n thu=" + thu +
                "\n fri=" + fri +
                "\n sat=" + sat +
                "\n sun=" + sun +
                "\n hol=" + hol;
    }
}
