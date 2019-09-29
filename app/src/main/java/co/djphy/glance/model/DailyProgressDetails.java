package co.djphy.glance.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Map;
import java.util.TreeMap;

public class DailyProgressDetails extends ServicePackageDetails implements Parcelable {

    public DailyProgressDetails(int itemViewType, String packageId, String packageName,
                                String bgImageUrl, TreeMap<String, String> subPackagesMap) {
        super(itemViewType, packageId, packageName, bgImageUrl, subPackagesMap);
    }

    //use this
    public DailyProgressDetails(int itemViewType, String packageId,
                                String packageName, String bgImageUrl,
                                TreeMap<String, String> subPackagesMap,
                                TreeMap<String, ChecklistData> subPackagesChecklistMap) {
        super(itemViewType, packageId, packageName, bgImageUrl, subPackagesMap);
        this.subPackagesChecklistMap = subPackagesChecklistMap;
    }

    public TreeMap<String, ChecklistData> subPackagesChecklistMap = new TreeMap<>();
    public String subPackageName;
    public String subPackageId;
    public boolean isOverAllPackageComplete;

    protected DailyProgressDetails(Parcel in) {
        super(in);
        int size = in.readInt();
        subPackagesChecklistMap = new TreeMap<>();
        subPackageName = in.readString();
        subPackageId = in.readString();
        isOverAllPackageComplete = in.readByte() != 0x00;
        for (int i = 0; i < size; i++) {
            String key = in.readString();
            ChecklistData value = in.readParcelable(ChecklistData.class.getClassLoader());
            subPackagesChecklistMap.put(key, value);
        }
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(subPackageName);
        dest.writeString(subPackageId);
        dest.writeInt(subPackagesChecklistMap.size());
        dest.writeByte((byte) (isOverAllPackageComplete ? 0x01 : 0x00));
        for(Map.Entry<String,ChecklistData> entry : subPackagesChecklistMap.entrySet()){
            dest.writeString(entry.getKey());
            dest.writeParcelable(entry.getValue(), flags);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<DailyProgressDetails> CREATOR = new Parcelable.Creator<DailyProgressDetails>() {
        @Override
        public DailyProgressDetails createFromParcel(Parcel in) {
            return new DailyProgressDetails(in);
        }

        @Override
        public DailyProgressDetails[] newArray(int size) {
            return new DailyProgressDetails[size];
        }
    };


    public static class ChecklistData implements Parcelable {
        public String taskName;
        public boolean isComplete;
        public String comment;

        public ChecklistData(String taskName, boolean isComplete, String comment) {
            this.taskName = taskName;
            this.isComplete = isComplete;
            this.comment = comment;
        }

        protected ChecklistData(Parcel in) {
            taskName = in.readString();
            isComplete = in.readByte() != 0x00;
            comment = in.readString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(taskName);
            dest.writeByte((byte) (isComplete ? 0x01 : 0x00));
            dest.writeString(comment);
        }

        @SuppressWarnings("unused")
        public static final Parcelable.Creator<ChecklistData> CREATOR = new Parcelable.Creator<ChecklistData>() {
            @Override
            public ChecklistData createFromParcel(Parcel in) {
                return new ChecklistData(in);
            }

            @Override
            public ChecklistData[] newArray(int size) {
                return new ChecklistData[size];
            }
        };
    }
}
