package co.djphy.glance.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.TreeMap;

public class WeeklyProgressDetails extends DailyProgressDetails implements Parcelable {

    public WeeklyProgressDetails(int itemViewType, String packageId, String packageName, String bgImageUrl, TreeMap<String, String> subPackagesMap) {
        super(itemViewType, packageId, packageName, bgImageUrl, subPackagesMap);
    }

    public WeeklyProgressDetails(int itemViewType, String packageId, String packageName, String bgImageUrl, TreeMap<String, String> subPackagesMap, TreeMap<String, ChecklistData> subPackagesChecklistMap) {
        super(itemViewType, packageId, packageName, bgImageUrl, subPackagesMap, subPackagesChecklistMap);
    }

    public String fromTo;
    public String day;
    public String packageShortName;
    public boolean isFirstPackage;


    protected WeeklyProgressDetails(Parcel in) {
        super(in);
        fromTo = in.readString();
        packageShortName = in.readString();
        day = in.readString();
        isFirstPackage = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fromTo);
        dest.writeString(day);
        dest.writeString(packageShortName);
        dest.writeByte((byte) (isFirstPackage ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<WeeklyProgressDetails> CREATOR = new Parcelable.Creator<WeeklyProgressDetails>() {
        @Override
        public WeeklyProgressDetails createFromParcel(Parcel in) {
            return new WeeklyProgressDetails(in);
        }

        @Override
        public WeeklyProgressDetails[] newArray(int size) {
            return new WeeklyProgressDetails[size];
        }
    };
}
