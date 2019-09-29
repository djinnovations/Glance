package co.djphy.glance.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class WeeklyReportDetails extends ServicePackageDetails {

    public WeeklyReportDetails(int itemViewType, String packageId, String packageName, String bgImageUrl, TreeMap<String, String> subPackagesMap) {
        super(itemViewType, packageId, packageName, bgImageUrl, subPackagesMap);
    }

    public WeeklyReportDetails(int itemViewType, String packageId,
                               String packageName, String bgImageUrl,
                               TreeMap<String, String> subPackagesMap,
                               List<Object> dailyProgressDetails) {
        super(itemViewType, packageId, packageName, bgImageUrl, subPackagesMap);
        this.dailyProgressDetails = dailyProgressDetails;
    }

    public String fromTo;
    public String day;
    public String packageShortName;
    public boolean isFirstPackage;
    public boolean isOverAllPackageComplete;
    private List<Object> dailyProgressDetails = new ArrayList<>();

    public List<Object> getDailyProgressDetails() {
        return new ArrayList<>(dailyProgressDetails);
    }

    protected WeeklyReportDetails(Parcel in) {
        super(in);
        fromTo = in.readString();
        packageShortName = in.readString();
        day = in.readString();
        isFirstPackage = in.readByte() != 0x00;
        isOverAllPackageComplete = in.readByte() != 0x00;
        dailyProgressDetails = in.readArrayList(DailyProgressDetails.class.getClassLoader());
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
        dest.writeByte((byte) (isOverAllPackageComplete ? 0x01 : 0x00));
        dest.writeList(dailyProgressDetails);
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
