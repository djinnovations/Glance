package co.djphy.glance.model;

import java.util.ArrayList;
import java.util.List;

public class OwnedPackagesDataObject {

    public String purchasedDate;
    public String nextBillingDate;
    public String packageName;
    public int itemViewType;
    public List<String> subServicesList = new ArrayList<>();

    public OwnedPackagesDataObject(int itemViewType, String purchasedDate, String nextBillingDate
            , String packageName, List<String> subServicesList) {
        this.itemViewType = itemViewType;
        this.purchasedDate = purchasedDate;
        this.nextBillingDate = nextBillingDate;
        this.packageName = packageName;
        this.subServicesList = subServicesList;
    }
}
