package co.djphy.glance.model;

import java.util.Set;

import co.djphy.glance.modules.customise.OptionValue;

public class QRTdataObject {

    public String issueDescription;
    public String phoneNumber;
    public Set<OptionValue> primarySelected;

    public QRTdataObject(String issueDescription, String phoneNumber,
                         Set<OptionValue> primarySelected) {
        this.issueDescription = issueDescription;
        this.phoneNumber = phoneNumber;
        this.primarySelected = primarySelected;
    }
}
