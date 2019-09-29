package co.djphy.glance.model;

import java.util.List;

public class CancelSubscriptionDataObject {

    public String packageName;
    public String packageId;
    public int itemViewType;
    public String subscriptionType;

    public CancelSubscriptionDataObject(int itemViewType, String packageName, String packageId) {
        this.packageName = packageName;
        this.packageId = packageId;
        this.itemViewType = itemViewType;
    }
}
