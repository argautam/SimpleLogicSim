package com.example.simplelogicsim;


public class NOT implements Gates
{
    int x;
    int y;
    Gates n;

    public NOT(){}

    public NOT(Gates a)
    {
        this.setSource(a);
    }

    public void setSource(Gates n) {this.n = n;}

    public Gates getN() {
        return n;
    }

    public boolean eval() {return !n.eval();}

    public int drawGate() {return R.drawable.not;}

    public void setX(int a) {x = a;}
    public void setY(int a) {y = a;}

    public int getX() {return x;}
    public int getY() {return y;}
}
