package com.xiaopo.flying.stickerview;

public class Constraint {

    //<editor-fold desc="4 infor can luu">
    //left
    private Dimen top;
    private Dimen left;

    //size
    private Dimen width;
    private Dimen height;
    //</editor-fold>


    public Constraint(Dimen left, Dimen top, Dimen width, Dimen height) {
        this.top = top;
        this.left = left;
        this.width = width;
        this.height = height;
    }

    public Dimen getTop ()
    {
        return top;
    }

    public void setTop (Dimen top)
    {
        this.top = top;
    }

    public Dimen getLeft()
    {
        return left;
    }

    public void setLeft(Dimen left)
    {
        this.left = left;
    }

    public Dimen getWidth ()
    {
        return width;
    }

    public void setWidth (Dimen width)
    {
        this.width = width;
    }

    public Dimen getHeight ()
    {
        return height;
    }

    public void setHeight (Dimen height)
    {
        this.height = height;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [top = "+top+", left = "+ left +", width = "+width+", height = "+height+"]";
    }
}
