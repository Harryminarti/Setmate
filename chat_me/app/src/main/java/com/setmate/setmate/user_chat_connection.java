package com.setmate.setmate;

import androidx.annotation.Keep;

@Keep
public class user_chat_connection {

    private String receiver_id;
    private  Boolean type;

    public user_chat_connection() {
    }

    public user_chat_connection(String receiver_id, Boolean type) {
        this.receiver_id = receiver_id;
        this.type = type;
    }

    public String getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(String receiver_id) {
        this.receiver_id = receiver_id;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }
}
