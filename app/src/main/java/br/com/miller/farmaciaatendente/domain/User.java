package br.com.miller.farmaciaatendente.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class User implements Parcelable {

    private String id_firebase;
    private String name;
    private String email;
    private String surname;
    private String city;
    private String phone;
    private Address address;
    private Date birth_date;
    private String storeId;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_firebase() {
        return id_firebase;
    }

    public void setId_firebase(String id_firebase) {
        this.id_firebase = id_firebase;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id_firebase);
        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeString(this.surname);
        dest.writeString(this.city);
        dest.writeString(this.phone);
        dest.writeParcelable(this.address, flags);
        dest.writeLong(this.birth_date != null ? this.birth_date.getTime() : -1);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.id_firebase = in.readString();
        this.name = in.readString();
        this.email = in.readString();
        this.surname = in.readString();
        this.city = in.readString();
        this.phone = in.readString();
        this.address = in.readParcelable(Address.class.getClassLoader());
        long tmpBirth_date = in.readLong();
        this.birth_date = tmpBirth_date == -1 ? null : new Date(tmpBirth_date);
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
