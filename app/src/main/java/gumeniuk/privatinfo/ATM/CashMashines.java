package gumeniuk.privatinfo.ATM;

import java.util.List;

public class CashMashines {

    private String city;
    private String address;
    private List<CashMashineInfo> cashMashineInfo = null;

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

    public List<CashMashineInfo> getCashMashineInfo() {
        return cashMashineInfo;
    }

    public void setCashMashineInfo(List<CashMashineInfo> cashMashineInfo) {
        this.cashMashineInfo = cashMashineInfo;
    }

}
