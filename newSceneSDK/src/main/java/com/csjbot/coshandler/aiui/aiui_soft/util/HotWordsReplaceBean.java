package com.csjbot.coshandler.aiui.aiui_soft.util;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author ShenBen
 * @date 2020/1/21 11:08
 * @email 714081644@qq.com
 */
public class HotWordsReplaceBean implements Parcelable {

    private String hotWordsName;

    private List<String> replaceList;

    public HotWordsReplaceBean() {

    }

    public void readFromParcel(Parcel reply) {
        this.hotWordsName = reply.readString();
        this.replaceList = reply.createStringArrayList();

    }

    public HotWordsReplaceBean(String hotWordsName, List<String> replaceList) {
        this.hotWordsName = hotWordsName;
        this.replaceList = replaceList;
    }

    public String getHotWordsName() {
        return hotWordsName;
    }

    public void setHotWordsName(String hotWordsName) {
        this.hotWordsName = hotWordsName;
    }

    public List<String> getReplaceList() {
        return replaceList;
    }

    public void setReplaceList(List<String> replaceList) {
        this.replaceList = replaceList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.hotWordsName);
        dest.writeStringList(this.replaceList);
    }

    protected HotWordsReplaceBean(Parcel in) {
        this.hotWordsName = in.readString();
        this.replaceList = in.createStringArrayList();
    }

    public static final Creator<HotWordsReplaceBean> CREATOR = new Creator<HotWordsReplaceBean>() {
        @Override
        public HotWordsReplaceBean createFromParcel(Parcel source) {
            return new HotWordsReplaceBean(source);
        }

        @Override
        public HotWordsReplaceBean[] newArray(int size) {
            return new HotWordsReplaceBean[size];
        }
    };
}
