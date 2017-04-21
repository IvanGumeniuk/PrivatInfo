package gumeniuk.privatinfo.Database;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ваня on 21.04.2017.
 */

public class City extends RealmObject {
    @PrimaryKey
    private String id;
    private String name;
    private RealmList<ATM> atms;
    private RealmList<TSO> tsos;

    public City(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<ATM> getAtms() {
        return atms;
    }

    public void setAtms(RealmList<ATM> atms) {
        this.atms = atms;
    }

    public RealmList<TSO> getTsos() {
        return tsos;
    }

    public void setTsos(RealmList<TSO> tsos) {
        this.tsos = tsos;
    }
}
