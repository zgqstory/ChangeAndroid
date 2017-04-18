package com.story.change.android.mvp.bean.user;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by story on 2017/3/10 0010 下午 2:09.
 * 用户信息类
 */
public class User implements Parcelable {
    private String phone;
    private String name;
    private String pwd;
    private String avatar;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.phone);
        dest.writeString(this.name);
        dest.writeString(this.pwd);
        dest.writeString(this.avatar);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.phone = in.readString();
        this.name = in.readString();
        this.pwd = in.readString();
        this.avatar = in.readString();
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
