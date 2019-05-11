package com.example.shopee.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class accounts implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer Id;
    @SerializedName("Username")
    @Expose
    private String Username;
    @SerializedName("Password")
    @Expose
    private String Password;
    @SerializedName("Phone")
    @Expose
    private String Phone;
    @SerializedName("Address")
    @Expose
    private String Address;
    @SerializedName("Name")
    @Expose
    private String Name;
    @SerializedName("Image")
    @Expose
    private String Image;

    public accounts(Integer id, String username, String password, String phone, String address, String name, String image) {
        Id = id;
        Username = username;
        Password = password;
        Phone = phone;
        Address = address;
        Name = name;
        Image = image;
    }

    protected accounts(Parcel in) {
        if (in.readByte() == 0) {
            Id = null;
        } else {
            Id = in.readInt();
        }
        Username = in.readString();
        Password = in.readString();
        Phone = in.readString();
        Address = in.readString();
        Name = in.readString();
        Image = in.readString();
    }

    public static final Creator<accounts> CREATOR = new Creator<accounts>() {
        @Override
        public accounts createFromParcel(Parcel in) {
            return new accounts(in);
        }

        @Override
        public accounts[] newArray(int size) {
            return new accounts[size];
        }
    };

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (Id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(Id);
        }
        dest.writeString(Username);
        dest.writeString(Password);
        dest.writeString(Phone);
        dest.writeString(Address);
        dest.writeString(Name);
        dest.writeString(Image);
    }
}
