package com.example.simplelogicsim;

public class OR implements Gates
{
    int x;
    int y;
    Gates a,b;

    public OR(){}
    public OR(Gates a, Gates b) {this.setA(a); this.setB(b);}

    public void setA(Gates n)    {this.a = n;}
    public void setB(Gates n)    {this.b = n;}

    public Gates getA() {
        return a;
    }

    public Gates getB() {
        return b;
    }

    public boolean eval()        {return a.eval() || b.eval();}

    public int drawGate() {return R.drawable.or;}

    public void setX(int a) {x = a;}
    public void setY(int a) {y = a;}

    public int getX() {return x;}
    public int getY() {return y;}

}
