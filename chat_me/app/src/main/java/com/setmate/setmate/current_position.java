package com.setmate.setmate;


import androidx.annotation.Keep;

@Keep
public class current_position {
 private int positions =1;

    public current_position() {
    }

    public current_position(int positions) {
        this.positions = positions;
    }

    public int getPositions() {
        return positions;
    }

    public void setPositions(int positions) {
        this.positions = positions;
    }
}
