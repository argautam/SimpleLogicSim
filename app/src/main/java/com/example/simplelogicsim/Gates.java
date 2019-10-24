package com.example.simplelogicsim;

import android.media.Image;

interface Gates{
    boolean eval();
    int drawGate();

    int getX();
    int getY();
}