package com.xiaopo.flying.stickerview;

import java.io.Serializable;

public class Photos implements Serializable {
    public Photos(int image) {
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    private int image;
}
