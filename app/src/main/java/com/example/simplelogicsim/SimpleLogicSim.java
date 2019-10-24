package com.example.simplelogicsim;

import android.view.Display;
import android.graphics.Point;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.app.Activity;
import android.os.Bundle;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

public class SimpleLogicSim extends Activity {
    int numberHorizontalPixels;
    int numberVerticalPixels;
    int blockSize;
    ImageView gameView;
    Bitmap blankBitmap;
    Canvas canvas;
    Paint paint;
    float xTouched;
    float yTouched;
    int gridWidth = 40;
    int gridHeight;
    ArrayList<Gates> gatesList = new ArrayList<Gates>();
    // Array List that stores the user input for wiring.
    ArrayList<Integer> gateWire = new ArrayList<Integer>();
    // Counts the number of input the circuit needs.
    int inputNodes;
    // Flag used to see if the circuit is complete.
    boolean onOrOff = false;


    // List of Wires and gates for saving and loading circuits.
    ArrayList<Gates> saveList1 = new ArrayList<Gates>();
    ArrayList<Integer> saveWire1 = new ArrayList<Integer>();
    ArrayList<Gates> saveList2 = new ArrayList<Gates>();
    ArrayList<Integer> saveWire2 = new ArrayList<Integer>();
    ArrayList<Gates> saveList3 = new ArrayList<Gates>();
    ArrayList<Integer> saveWire3 = new ArrayList<Integer>();

    int State = 0;

    public boolean onTouchEvent(MotionEvent motionEvent) {
        // Checking what button the user touched
        if ((motionEvent.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
            xTouched = motionEvent.getX();
            yTouched = motionEvent.getY();


            if (xTouched >= 0 && xTouched <= numberHorizontalPixels / 8) {
                if (switchTesting())
                {
                    if (yTouched >= 0 && yTouched <= (numberVerticalPixels / 8)) {
                        if (gatesList.size() < 8)
                        gatesList.add(new AND());
                    } else if (yTouched >= (numberVerticalPixels / 8) && yTouched <= (numberVerticalPixels / 8) * 2) {
                        if (gatesList.size() < 8)
                        gatesList.add(new OR());

                    }else if ((yTouched >= (numberVerticalPixels / 8) * 2 && yTouched <= (numberVerticalPixels / 8) * 3)) {
                        if (gatesList.size() < 8)
                            gatesList.add(new NOT());
                    }
                }

                if (yTouched >= (numberVerticalPixels / 8) * 3 && yTouched <= (numberVerticalPixels / 8) * 4) {
                    if (gatesList.size() < 8)
                        gatesList.add(new Switch(true));
                }
                else if ((yTouched >= (numberVerticalPixels / 8) * 4 && yTouched <= (numberVerticalPixels / 8) * 5)) {
                    if (gatesList.size() < 8)
                        gatesList.add(new Switch(false));

                } else if (yTouched >= (numberVerticalPixels / 8) * 5 && yTouched <= (numberVerticalPixels / 8) * 6) {
                    if (gateWire.size() == inputNodes)
                    evaluate();


                } else if (yTouched > (numberVerticalPixels / 8 * 6) && yTouched < (numberVerticalPixels / 8 * 7)) {
                    deleteGate();


                } else if (yTouched > (numberVerticalPixels / 8 * 7) && yTouched < (numberVerticalPixels / 8 * 8)) {
                    gatesList.clear();
                    gateWire.clear();
                    onOrOff = false;

                }
            }

            // Checking what inputs were touched.
            if (!gatesList.isEmpty())
            {
                if (inputNodes > gateWire.size())
                {
                    if ((yTouched >= 0) && (yTouched <= numberVerticalPixels/8))
                    {
                        if (xTouched >= numberHorizontalPixels/8 && xTouched <= numberHorizontalPixels/9*2)
                        {
                            if (gatesList.size() > 1)
                            gateWire.add(0);
                        }
                        else if (xTouched >= numberHorizontalPixels/9*2 && xTouched <= numberHorizontalPixels/9*3)
                        {
                            if (gatesList.size() > 2)
                            gateWire.add(1);
                        }
                        else if (xTouched >= numberHorizontalPixels/9*3 && xTouched <= numberHorizontalPixels/9*4)
                        {
                            if (gatesList.size() > 3)
                            gateWire.add(2);
                        }
                        else if (xTouched >= numberHorizontalPixels/9*4 && xTouched <= numberHorizontalPixels/9*5)
                        {
                            if (gatesList.size() > 4)
                            gateWire.add(3);
                        }
                        else if (xTouched >= numberHorizontalPixels/9*5 && xTouched <= numberHorizontalPixels/9*6)
                        {
                            if (gatesList.size() > 5)
                            gateWire.add(4);
                        }
                        else if (xTouched >= numberHorizontalPixels/9*6 && xTouched <= numberHorizontalPixels/9*7)
                        {
                            if (gatesList.size() > 6)
                            gateWire.add(5);
                        }
                        else if (xTouched >= numberHorizontalPixels/9*7 && xTouched <= numberHorizontalPixels/9*8)
                        {
                            if (gatesList.size() > 7)
                            gateWire.add(6);
                        }
                    }
                }
            }

            if ((yTouched > numberVerticalPixels/8) && (yTouched < (numberVerticalPixels/8)*2))
            {
                if (xTouched >= numberHorizontalPixels/8 && xTouched <= numberHorizontalPixels/9*2)
                {



                    saveList1.clear();
                    saveWire1.clear();

                    saveList1 = new ArrayList<Gates>(gatesList);
                    saveWire1 = new ArrayList<Integer>(gateWire);

                }
                if (xTouched >= numberHorizontalPixels/9*2 && xTouched <= numberHorizontalPixels/9*3)
                {

                    saveList2.clear();
                    saveWire2.clear();
                    saveList2 = new ArrayList<Gates>(gatesList);
                    saveWire2 = new ArrayList<Integer>(gateWire);

                }
                if (xTouched >= numberHorizontalPixels/9*3 && xTouched <= numberHorizontalPixels/9*4)
                {

                    saveList3.clear();
                    saveWire3.clear();
                    saveList3 = new ArrayList<Gates>(gatesList);
                    saveWire3 = new ArrayList<Integer>(gateWire);
                    
                }
                if (xTouched >= numberHorizontalPixels/9*4 && xTouched <= numberHorizontalPixels/9*5) {


                    gatesList.clear();
                    gateWire.clear();
                    gateWire = new ArrayList<Integer>(saveWire1);
                    gatesList = new ArrayList<Gates>(saveList1);
                    onOrOff = false;

                }
                if (xTouched >= numberHorizontalPixels/9*5 && xTouched <= numberHorizontalPixels/9*6)
                {

                    gatesList.clear();
                    gateWire.clear();
                    gateWire = new ArrayList<Integer>(saveWire2);
                    gatesList = new ArrayList<Gates>(saveList2);
                    onOrOff = false;

                }
                if (xTouched >= numberHorizontalPixels/9*6 && xTouched <= numberHorizontalPixels/9*7)
                {

                    gatesList.clear();
                    gateWire.clear();
                    gateWire = new ArrayList<Integer>(saveWire3);
                    gatesList = new ArrayList<Gates>(saveList3);
                    onOrOff = false;

                }
            }



        }
        wire();
        draw();
        return true;
    }

    // Method that deletes gates.
    void deleteGate()
    {
        Gates tempGate;
        if (gatesList.size() > 0) {
            tempGate = gatesList.get(gatesList.size() - 1);
            gatesList.remove(gatesList.size() - 1);


            if ((tempGate instanceof AND) || (tempGate instanceof OR)){
                gateWire.remove(gateWire.size() - 1);
                gateWire.remove(gateWire.size() - 1);
            }

            else if (tempGate instanceof NOT){
                gateWire.remove(gateWire.size() - 1);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        numberHorizontalPixels = size.x;
        numberVerticalPixels = size.y;
        gridHeight = size.y / (size.x / gridWidth);
        blockSize = size.x / gridWidth;

        // Initialize all the objects ready for drawing
        blankBitmap = Bitmap.createBitmap(numberHorizontalPixels,
                numberVerticalPixels,
                Bitmap.Config.ARGB_8888);

        canvas = new Canvas(blankBitmap);
        gameView = new ImageView(this);
        paint = new Paint();

        // Tell Android to set our drawing
        // as the view for this app
        setContentView(gameView);

        draw();
    }

    // Method that wires the circuit.
    void wire()
    {
        int countInput = 0;
        boolean flag = false;
        int forWire = 0;

        //Checking how many input is required by the circuit.
        if (!gatesList.isEmpty())
        {
            for (int i = 0; i < gatesList.size(); i++)
            {
                if ((gatesList.get(i) instanceof AND) || (gatesList.get(i) instanceof  OR)) {
                    countInput += 2;
                }
                if (gatesList.get(i) instanceof  NOT)
                {
                    countInput += 1;
                }
            }

            // Wiring the circuits according the input that is received.
            if(countInput == gateWire.size())
            {
                 for (int i =0; i < gatesList.size(); i++)
                {
                    if (i != 0)
                    {
                        forWire++;
                    }

                    if (gatesList.get(i) instanceof AND)
                    {
                        ((AND) gatesList.get(i)).setA(gatesList.get(gateWire.get(forWire)));
                        ((AND) gatesList.get(i)).setB(gatesList.get(gateWire.get(forWire+1)));
                        flag = true;
                    }

                    else if (gatesList.get(i) instanceof  OR)
                    {
                        ((OR) gatesList.get(i)).setA(gatesList.get(gateWire.get(forWire)));
                        ((OR) gatesList.get(i)).setB(gatesList.get(gateWire.get(forWire+1)));
                        flag = true;
                    }
                    else if (gatesList.get(i) instanceof  NOT)
                    {
                        ((NOT) gatesList.get(i)).setSource(gatesList.get(gateWire.get(forWire)));
                    }
                    else if (gatesList.get(i) instanceof Switch)
                    {
                        forWire --;
                    }

                    if (flag)
                    {
                        forWire++;
                    }
                    flag = false;
                }
            }
        }
        inputNodes = countInput;
    }

    // Checking to see if there is a switch in the circuit first before other gates can be added.
    boolean switchTesting()
    {
        boolean temp = false;
        if (!gatesList.isEmpty())
        {
            for (int i = 0; i < gatesList.size(); i++)
            {
                if (gatesList.get(i) instanceof Switch)
                temp = true;
            }
        }
        return temp;
    }

    // This method helps to decide coordinates for gates on the canvas.
    void setCoordinates() {

        int forHeight = 1;
        int forWidth = 3;

        if (!gatesList.isEmpty()) {

            for (int i = 0; i < gatesList.size(); i++) {
                if (i % 4 == 0) {
                    forHeight += 2;
                    forWidth = 3;
                }

                if (gatesList.get(i) instanceof AND) {
                    ((AND) gatesList.get(i)).setX((numberHorizontalPixels / 12 * forWidth));
                    ((AND) gatesList.get(i)).setY((numberVerticalPixels / 8) * forHeight);
                } else if (gatesList.get(i) instanceof OR) {
                    ((OR) gatesList.get(i)).setX((numberHorizontalPixels / 12 * forWidth));
                    ((OR) gatesList.get(i)).setY((numberVerticalPixels / 8) * forHeight);
                } else if (gatesList.get(i) instanceof NOT) {
                    ((NOT) gatesList.get(i)).setX((numberHorizontalPixels / 12 * forWidth));
                    ((NOT) gatesList.get(i)).setY((numberVerticalPixels / 8) * forHeight);
                } else if (gatesList.get(i) instanceof Switch) {
                    ((Switch) gatesList.get(i)).setX((numberHorizontalPixels / 12 * forWidth));
                    ((Switch) gatesList.get(i)).setY((numberVerticalPixels / 8) * forHeight);
                }
                forWidth += 2;
            }
        }
    }

    void draw() {

        setCoordinates();

        gameView.setImageBitmap(blankBitmap);

        // Wipe the screen with a white color
        canvas.drawColor(Color.argb(255, 255, 255, 255));

        // Set paint color as Black
        paint.setColor(Color.argb(255, 0, 0, 0));

        canvas.drawLine(numberHorizontalPixels / 8, 0, numberHorizontalPixels / 8, numberVerticalPixels, paint);
        paint.setTextSize(blockSize - 3);

        String[] Labels = {"AND", "OR", "NOT", "SW:TRUE","SW:FALSE" ,"START", "DELETE", "RESET", ""};

        // Drawing tables fot the Labels.
        for (int i = 0; i <= 8; i++) {
            canvas.drawText(Labels[i], 10, (numberVerticalPixels / 8) + (i * (numberVerticalPixels / 8)) - 20, paint);
            canvas.drawLine(0, (numberVerticalPixels / 8) + (i * (numberVerticalPixels / 8)),
                    numberHorizontalPixels / 8, (numberVerticalPixels / 8) + (i * (numberVerticalPixels / 8)), paint);
        }

        canvas.drawLine(numberHorizontalPixels/8, numberVerticalPixels/8, numberHorizontalPixels/8*7, numberVerticalPixels/8,paint);
        canvas.drawText(String.format("INPUT NEEDED: %d",inputNodes), (numberHorizontalPixels/8) + 15, (numberVerticalPixels/8)* 7,paint );
        canvas.drawText((String.format("INPUT RECEIVED: %d",gateWire.size())), (numberHorizontalPixels/8) +15, ((numberVerticalPixels/8)* 8) -30 ,paint);

        String[] Labels2 = {"1","2","3","4","5","6","7",""};

        paint.setTextSize(blockSize - 2);
        for (int i = 0; i < 8 ; i++)
        {
            canvas.drawText(Labels2[i], (numberHorizontalPixels / 8) + (i * (numberHorizontalPixels / 9))+25, 50 , paint);
            canvas.drawLine((numberHorizontalPixels / 8) + (i * (numberHorizontalPixels / 9)),0,(numberHorizontalPixels / 8) + (i * (numberHorizontalPixels / 9)),
                    numberVerticalPixels/8,paint);
        }

        // Drawing boxes and labels for the SAVE and LOAD.
        String[] Labels3 = {"Save1","Save2","Save3","Load1","Load2","Load3",""};
        canvas.drawLine(numberHorizontalPixels/8, (numberVerticalPixels/8)*2, (numberHorizontalPixels/9)*7 , (numberVerticalPixels/8)*2,paint);
        for (int i = 0; i < 7; i++)
        {
            canvas.drawText(Labels3[i], (numberHorizontalPixels / 8) + (i * (numberHorizontalPixels / 9))+25, (numberVerticalPixels/8)+50 , paint);
            canvas.drawLine((numberHorizontalPixels / 8) + (i * (numberHorizontalPixels / 9)),(numberVerticalPixels/8),(numberHorizontalPixels / 8) + (i * (numberHorizontalPixels / 9)),
                    (numberVerticalPixels/8)*2,paint);
        }

        //I learnt to draw image from https://www.youtube.com/watch?v=ziMD-VvZRg4
        //canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.switch1),400,250,null);
        if (!gatesList.isEmpty())
            for (int i = 0; i < gatesList.size(); i++) {
                if (gatesList.get(i) instanceof Switch)
                {
                    canvas.drawText((String.format("%b",((Switch) gatesList.get(i)).state)), gatesList.get(i).getX() , gatesList.get(i).getY(),paint);
                }
                    canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), gatesList.get(i).drawGate()), gatesList.get(i).getX(), gatesList.get(i).getY(), null);

            }

        // Code for printing LED. Green color if the LED is on and red if it is off.
        if (onOrOff)
        {
            paint.setColor(Color.argb(255, 0, 255, 0));

        }
        else{
            paint.setColor(Color.argb(255, 255, 0, 0));
        }
        canvas.drawCircle(numberHorizontalPixels / 8 * 7, numberVerticalPixels / 8 * 2, 50, paint);
        paint.setColor(Color.argb(255, 0, 0, 0));
        canvas.drawText("LED",numberHorizontalPixels / 8 * 7, numberVerticalPixels / 8 * 2, paint);


        // Drawing lines between gates
        if (gateWire.size() == inputNodes)
        {
            for (int i = 0; i < gatesList.size(); i ++)
            {
                if ((gatesList.get(i) instanceof  AND))
                {
                    paint.setColor(Color.argb(255, 255, 0, 0));
                    canvas.drawLine(gatesList.get(i).getX(),gatesList.get(i).getY() + 20 ,((AND) gatesList.get(i)).getA().getX()+100, ((AND) gatesList.get(i)).getA().getY(),paint);
                    canvas.drawLine(gatesList.get(i).getX(),gatesList.get(i).getY() + 45,((AND) gatesList.get(i)).getB().getX(), ((AND) gatesList.get(i)).getB().getY(),paint);
                }
                if ((gatesList.get(i) instanceof  OR))
                {
                    paint.setColor(Color.argb(255, 0, 0, 255));
                    canvas.drawLine(gatesList.get(i).getX(),gatesList.get(i).getY() + 20 ,((OR) gatesList.get(i)).getA().getX()+100, ((OR) gatesList.get(i)).getA().getY(),paint);
                    canvas.drawLine(gatesList.get(i).getX(),gatesList.get(i).getY()+ 45,((OR) gatesList.get(i)).getB().getX()+100, ((OR) gatesList.get(i)).getB().getY(),paint);
                }
                if (gatesList.get(i) instanceof  NOT)
                {
                    paint.setColor(Color.argb(255, 255, 0, 255));
                    canvas.drawLine(gatesList.get(i).getX(), gatesList.get(i).getY() + 30, ((NOT) gatesList.get(i)).getN().getX()+100, ((NOT) gatesList.get(i)).getN().getY(),paint);
                }
            }
        }

    }

    // Method that evaluates the Circuit.
    void evaluate() {
        for (int i = 0; i < gatesList.size(); i++) {
            gatesList.get(i).eval();
            onOrOff = gatesList.get(i).eval();
        }
    }

}



