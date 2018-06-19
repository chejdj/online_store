package com.chejdj.online_store.tools;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2018/4/11.
 */

public class Goods extends BmobObject {
    private String name;
    private String category;
    private String price;
    private String discrible;
    private String contacts;
    private BmobFile pic;
    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getPrice() {
        return price;
    }

    public String getDiscrible() {
        return discrible;
    }

    public BmobFile getPicture() {
        return pic;
    }

    public String getContacts() {
        return contacts;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDiscrible(String discrible) {
        this.discrible = discrible;
    }

    public void setPicture(BmobFile pic) {
        this.pic=pic;
    }
}
