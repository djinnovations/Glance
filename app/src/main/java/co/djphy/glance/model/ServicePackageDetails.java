package co.djphy.glance.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ServicePackageDetails implements Parcelable {

    public String packageId;
    public String packageName;
    public String bgImageUrl;
    public int itemViewType;
    public TreeMap<String, String> subPackagesMap = new TreeMap<>();

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
        int size = in.readInt();
        for(int i = 0; i < size; i++){
            String key = in.readString();
            String value = in.readString();
            subPackagesMap.put(key,value);
        }
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
        //dest.writeMap(subPackagesMap);
        dest.writeInt(subPackagesMap.size());
        for(Map.Entry<String,String> entry : subPackagesMap.entrySet()){
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
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
