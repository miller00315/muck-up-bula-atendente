package br.com.miller.farmaciaatendente.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

public class Buy implements Parcelable {

    private String id;
    private int storeId;
    private int payMode = 0;
    private int cardFlag;
    private double troco;
    private String storeName;
    private String userName;
    private String storeCity;
    private String userCity;
    private String userId;
    private Double totalValue;
    private Double sendValue;
    private Date solicitationDate, deliverDate, receiverDate;
    private ArrayList<Offer> offers;
    private String address;
    private String observations;
    private String status;

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservation(String observations) {
        this.observations = observations;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getPayMode() {
        return payMode;
    }

    public void setPayMode(int payMode) {
        this.payMode = payMode;
    }

    public int getCardFlag() {
        return cardFlag;
    }

    public void setCardFlag(int cardFlag) {
        this.cardFlag = cardFlag;
    }

    public double getTroco() {
        return troco;
    }

    public void setTroco(double troco) {
        this.troco = troco;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreCity() {
        return storeCity;
    }

    public void setStoreCity(String storeCity) {
        this.storeCity = storeCity;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public Double getSendValue() {
        return sendValue;
    }

    public void setSendValue(Double sendValue) {
        this.sendValue = sendValue;
    }

    public Date getSolicitationDate() {
        return solicitationDate;
    }

    public void setSolicitationDate(Date solicitationDate) {
        this.solicitationDate = solicitationDate;
    }

    public Date getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(Date deliverDate) {
        this.deliverDate = deliverDate;
    }

    public Date getReceiverDate() {
        return receiverDate;
    }

    public void setReceiverDate(Date receiverDate) {
        this.receiverDate = receiverDate;
    }

    public ArrayList<Offer> getOffers() {
        return offers;
    }

    public void setOffers(ArrayList<Offer> offers) {
        this.offers = offers;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeInt(this.storeId);
        dest.writeInt(this.payMode);
        dest.writeInt(this.cardFlag);
        dest.writeDouble(this.troco);
        dest.writeString(this.storeName);
        dest.writeString(this.userName);
        dest.writeString(this.storeCity);
        dest.writeString(this.userCity);
        dest.writeString(this.userId);
        dest.writeValue(this.totalValue);
        dest.writeValue(this.sendValue);
        dest.writeLong(this.solicitationDate != null ? this.solicitationDate.getTime() : -1);
        dest.writeLong(this.deliverDate != null ? this.deliverDate.getTime() : -1);
        dest.writeLong(this.receiverDate != null ? this.receiverDate.getTime() : -1);
        dest.writeList(this.offers);
        dest.writeString(this.address);
        dest.writeString(this.observations);
    }

    public Buy() {
    }

    protected Buy(Parcel in) {
        this.id = in.readString();
        this.storeId = in.readInt();
        this.payMode = in.readInt();
        this.cardFlag = in.readInt();
        this.troco = in.readDouble();
        this.storeName = in.readString();
        this.userName = in.readString();
        this.storeCity = in.readString();
        this.userCity = in.readString();
        this.userId = in.readString();
        this.totalValue = (Double) in.readValue(Double.class.getClassLoader());
        this.sendValue = (Double) in.readValue(Double.class.getClassLoader());
        long tmpSolicitationDate = in.readLong();
        this.solicitationDate = tmpSolicitationDate == -1 ? null : new Date(tmpSolicitationDate);
        long tmpDeliverDate = in.readLong();
        this.deliverDate = tmpDeliverDate == -1 ? null : new Date(tmpDeliverDate);
        long tmpReceiverDate = in.readLong();
        this.receiverDate = tmpReceiverDate == -1 ? null : new Date(tmpReceiverDate);
        this.offers = new ArrayList<Offer>();
        in.readList(this.offers, Offer.class.getClassLoader());
        this.address = in.readString();
        this.observations = in.readString();
    }

    public static final Parcelable.Creator<Buy> CREATOR = new Parcelable.Creator<Buy>() {
        @Override
        public Buy createFromParcel(Parcel source) {
            return new Buy(source);
        }

        @Override
        public Buy[] newArray(int size) {
            return new Buy[size];
        }
    };
}
