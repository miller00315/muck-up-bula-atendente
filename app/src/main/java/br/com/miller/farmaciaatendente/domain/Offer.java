package br.com.miller.farmaciaatendente.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class Offer implements Parcelable {

    private int id;
    private double value;
    private int quantity;
    private String description;
    private String title;
    private String type;
    private String city;
    private double sendValue;
    private String image;
    private String indication;
    private String noIndication;
    private String active;
    private String store;
    private int storeId;
    private int departamentId;
    private String cartId;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getSendValue() {
        return sendValue;
    }

    public void setSendValue(double sendValue) {
        this.sendValue = sendValue;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIndication() {
        return indication;
    }

    public void setIndication(String indication) {
        this.indication = indication;
    }

    public String getNoIndication() {
        return noIndication;
    }

    public void setNoIndication(String noIndication) {
        this.noIndication = noIndication;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getDepartamentId() {
        return departamentId;
    }

    public void setDepartamentId(int departamentId) {
        this.departamentId = departamentId;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeDouble(this.value);
        dest.writeInt(this.quantity);
        dest.writeString(this.description);
        dest.writeString(this.title);
        dest.writeString(this.type);
        dest.writeString(this.city);
        dest.writeDouble(this.sendValue);
        dest.writeString(this.image);
        dest.writeString(this.indication);
        dest.writeString(this.noIndication);
        dest.writeString(this.active);
        dest.writeString(this.store);
        dest.writeInt(this.storeId);
        dest.writeInt(this.departamentId);
        dest.writeString(this.cartId);
    }

    public Offer() {
    }

    protected Offer(Parcel in) {
        this.id = in.readInt();
        this.value = in.readDouble();
        this.quantity = in.readInt();
        this.description = in.readString();
        this.title = in.readString();
        this.type = in.readString();
        this.city = in.readString();
        this.sendValue = in.readDouble();
        this.image = in.readString();
        this.indication = in.readString();
        this.noIndication = in.readString();
        this.active = in.readString();
        this.store = in.readString();
        this.storeId = in.readInt();
        this.departamentId = in.readInt();
        this.cartId = in.readString();
    }

    public static final Parcelable.Creator<Offer> CREATOR = new Parcelable.Creator<Offer>() {
        @Override
        public Offer createFromParcel(Parcel source) {
            return new Offer(source);
        }

        @Override
        public Offer[] newArray(int size) {
            return new Offer[size];
        }
    };
}
