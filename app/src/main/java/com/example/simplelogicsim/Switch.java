package com.example.simplelogicsim;

public class Switch implements Gates
{
    int x;
    int y;
    boolean state;

    public Switch (boolean state) { this.state = state;}

    public void toggle() {this.state = !this.state;}

    public boolean eval() {return this.state;}

    public int drawGate() {return R.drawable.switch1;}


    public void setX(int a) {x = a;}
    public void setY(int a) {y = a;}

    public int getX() {return x;}
    public int getY() {return y;}
}
