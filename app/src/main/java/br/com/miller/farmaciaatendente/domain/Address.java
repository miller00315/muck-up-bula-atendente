package br.com.miller.farmaciaatendente.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class Address implements Parcelable {

    private String address;
    private long latitude;
    private long longitude;
    private String city;
    private  String State;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.address);
        dest.writeLong(this.latitude);
        dest.writeLong(this.longitude);
        dest.writeString(this.city);
        dest.writeString(this.State);
    }

    public Address() {
    }

    protected Address(Parcel in) {
        this.address = in.readString();
        this.latitude = in.readLong();
        this.longitude = in.readLong();
        this.city = in.readString();
        this.State = in.readString();
    }

    public static final Parcelable.Creator<Address> CREATOR = new Parcelable.Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel source) {
            return new Address(source);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
}
