
package gumeniuk.privatinfo.TSO;

import java.util.List;

public class Terminals {

    private String city;
    private String address;
    private List<TerminalInfo> terminalInfo = null;

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

    public List<TerminalInfo> getTerminalInfo() {
        return terminalInfo;
    }

    public void setTerminalInfo(List<TerminalInfo> terminalInfo) {
        this.terminalInfo = terminalInfo;
    }

}
