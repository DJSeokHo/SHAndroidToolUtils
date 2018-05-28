package com.swein.framework.module.devicestoragescanner.data;

import android.os.Parcel;
import android.os.Parcelable;

public class StorageInfoData implements Parcelable {

    // path
    public String path;

    // state
    public String mounted;

    // is remove able
    public boolean removable;

    // size
    public long totalSize;

    // usable size
    public long usableSize;

    // storage information
    public String description;


    @Override
    public String toString() {
        return "storage path = " + path + "\n" + "mounted? = " + mounted + "\n" + "removable? = " + removable + "\n" + "total size = " + totalSize + "\n" + "use able size = " + usableSize + "\n" + "description = " + description;
    }

    public StorageInfoData() {}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(path);
        dest.writeString(mounted);
        dest.writeByte(removable ? (byte) 1 : (byte) 0);
        dest.writeLong(totalSize);
        dest.writeLong(usableSize);
    }

    private StorageInfoData(Parcel in) {
        this.path = in.readString();
        this.mounted = in.readString();
        this.removable = in.readByte() != 0;
        this.totalSize = in.readLong();
        this.usableSize = in.readLong();
    }

    // package able storage data info
    public static final Parcelable.Creator<StorageInfoData> CREATOR = new Parcelable.Creator<StorageInfoData>() {

        @Override
        public StorageInfoData createFromParcel(Parcel source) {
            return new StorageInfoData(source);
        }

        @Override
        public StorageInfoData[] newArray(int size) {
            return new StorageInfoData[size];
        }
    };




}
