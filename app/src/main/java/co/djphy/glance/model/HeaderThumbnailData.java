package co.djphy.glance.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by User on 02-02-2018.
 */

public class HeaderThumbnailData implements Parcelable{

    private String headerTitle;
    //private List<HorizontalViewData> dataList;
    private List<Object> dataList;
    private int viewType;

    public int getViewType() {
        return viewType;
    }

    public HeaderThumbnailData(int viewType, String headerTitle, List<Object> dataList) {
        this.viewType = viewType;
        this.headerTitle = headerTitle;
        this.dataList = dataList;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public List<Object> getDataList() {
        return dataList;
    }

    protected HeaderThumbnailData(Parcel in){
        headerTitle = in.readString();
        viewType = in.readInt();
        dataList = in.readArrayList(Object.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(headerTitle);
        dest.writeList(dataList);
        dest.writeInt(viewType);
    }

    @SuppressWarnings("unused")
    public static final Creator<HeaderThumbnailData> CREATOR = new Creator<HeaderThumbnailData>() {
        @Override
        public HeaderThumbnailData createFromParcel(Parcel in) {
            return new HeaderThumbnailData(in);
        }

        @Override
        public HeaderThumbnailData[] newArray(int size) {
            return new HeaderThumbnailData[size];
        }
    };



    public static class HorizontalViewData implements Parcelable {

        private int viewType;
        private String id;
        private String thumbnailUrl;
        private String extraData;
        private String type;
        private boolean isFree;
        private String title;
        private String title1;

        public HorizontalViewData(int viewType, String id) {
            this.viewType = viewType;
            this.id = id;
        }

        public HorizontalViewData(int viewType, String id, String thumbnailUrl,
                                  String extraData, String type, String title) {
            this.viewType = viewType;
            this.id = id;
            this.thumbnailUrl = thumbnailUrl;
            this.extraData = extraData;
            this.type = type;
            this.title = title;
        }

        public int getViewType() {
            return viewType;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getThumbnailUrl() {
            return thumbnailUrl;
        }

        public void setThumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
        }

        public String getExtraData() {
            return extraData;
        }

        public void setExtraData(String extraData) {
            this.extraData = extraData;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public boolean isFree() {
            return isFree;
        }

        public void setFree(boolean free) {
            isFree = free;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle1() {
            return title1;
        }

        public void setTitle1(String title1) {
            this.title1 = title1;
        }

        protected HorizontalViewData(Parcel in) {
            id = in.readString();
            thumbnailUrl = in.readString();
            extraData = in.readString();
            type = in.readString();
            isFree = in.readByte() != 0x00;
            title = in.readString();
            title1 = in.readString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(thumbnailUrl);
            dest.writeString(extraData);
            dest.writeString(type);
            dest.writeByte((byte) (isFree ? 0x01 : 0x00));
            dest.writeString(title);
            dest.writeString(title1);
        }

        @SuppressWarnings("unused")
        public static final Creator<HorizontalViewData> CREATOR = new Creator<HorizontalViewData>() {
            @Override
            public HorizontalViewData createFromParcel(Parcel in) {
                return new HorizontalViewData(in);
            }

            @Override
            public HorizontalViewData[] newArray(int size) {
                return new HorizontalViewData[size];
            }
        };
    }

}
