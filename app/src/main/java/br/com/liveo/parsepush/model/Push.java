package br.com.liveo.parsepush.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Rudsonlive on 13/01/16.
 */
public class Push implements Parcelable {

    @SerializedName("name")
    private String name;

    @SerializedName("alert")
    private String alert;

    @SerializedName("objectId")
    private String objectId;

    @SerializedName("isShowNotification")
    private boolean isShow;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getName());
        dest.writeString(this.getAlert());
        dest.writeString(this.getObjectId());
        dest.writeByte(isShow() ? (byte) 1 : (byte) 0);
    }

    public Push() {
    }

    protected Push(Parcel in) {
        this.setName(in.readString());
        this.setAlert(in.readString());
        this.setObjectId(in.readString());
        this.setShow(in.readByte() != 0);
    }

    public static final Parcelable.Creator<Push> CREATOR = new Parcelable.Creator<Push>() {
        public Push createFromParcel(Parcel source) {
            return new Push(source);
        }

        public Push[] newArray(int size) {
            return new Push[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
