package com.xiaopo.flying.stickerview;

public class Dimen {


    private float percentage;
    private Double constant;

    public Dimen(float percentage, Double constant) {
        this.percentage = percentage;
        this.constant = constant;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public Double getConstant() {
        return constant;
    }

    public void setConstant(Double constant) {
        this.constant = constant;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [constant = "+constant+", percentage = "+percentage+"]";
    }

    public Double caculateDime(Double oldParent, Double newParent) {
        return (constant / oldParent) * newParent;

    }
}

