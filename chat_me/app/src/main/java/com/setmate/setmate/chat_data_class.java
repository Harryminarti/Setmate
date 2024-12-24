package com.setmate.setmate;

import androidx.annotation.Keep;

@Keep
public class chat_data_class {

    String massage,sender_id;

    Boolean type;

    public chat_data_class() {
    }

    public chat_data_class(String massage, String sender_id, Boolean type) {
        this.massage = massage;
        this.sender_id = sender_id;
        this.type = type;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }
}
