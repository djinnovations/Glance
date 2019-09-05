package co.djphy.glance.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.TreeMap;

public class ServicePackageDetails implements Parcelable {

    public String packageId;
    public String packageName;
    public String bgImageUrl;
    public int itemViewType;
    public TreeMap<String, String> subPackagesMap;

    public ServicePackageDetails(int itemViewType, String packageId, String packageName,
                                 String bgImageUrl, TreeMap<String, String> subPackagesMap) {
        this.packageId = packageId;
        this.packageName = packageName;
        this.bgImageUrl = bgImageUrl;
        this.subPackagesMap = subPackagesMap;
        this.itemViewType = itemViewType;
    }

    protected ServicePackageDetails(Parcel in) {
        packageId = in.readString();
        packageName = in.readString();
        bgImageUrl = in.readString();
        itemViewType = in.readInt();
        subPackagesMap = (TreeMap) in.readValue(TreeMap.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(packageId);
        dest.writeString(packageName);
        dest.writeString(bgImageUrl);
        dest.writeInt(itemViewType);
        dest.writeValue(subPackagesMap);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ServicePackageDetails> CREATOR = new Parcelable.Creator<ServicePackageDetails>() {
        @Override
        public ServicePackageDetails createFromParcel(Parcel in) {
            return new ServicePackageDetails(in);
        }

        @Override
        public ServicePackageDetails[] newArray(int size) {
            return new ServicePackageDetails[size];
        }
    };
}
