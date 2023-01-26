package com.example.realtetris;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Random;

public class Game extends AppCompatActivity {

    private boolean playPause = false;
    private boolean start = false;
    private int id;
    private final int height = 20;
    private final int width = 10;
    private int[][] board;
    private int[][] board01;
    private int[][] boardColor;
    private TableLayout table;
    private Tetraminoes actualTetra;
    public Object lock = this;
    public boolean firstclick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.board = null;
        this.table = table;
        Button play = (Button) findViewById(R.id.play);

        Thread t = new Thread(){
            @Override
            public void run() {
                while(true) {
                    while (isStart()) {
                        try {
                            Thread.sleep(400);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        setActualTetra(startGame());
                        if (!possibleSet()) {
                            setStart(false);
                        }
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        while (moveDown()) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            while (!isPlayPause()) {
                                //do nothing
                            }
                        }

                        setTetraminoe();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView tv = (TextView) findViewById(R.id.textView);
                                tv.setText(String.valueOf(Integer.parseInt(tv.getText().toString()) + 1));
                            }
                        });
                        eliminateLines();
                    }
                }
            }
        };

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv = (TextView) findViewById(R.id.play);
                if(isStart() && isPlayPause()){
                    setPlayPause(false);
                    tv.setText("pause");
                }else if(isStart() && !isPlayPause()){
                    setPlayPause(true);
                    tv.setText("play");
                }
                if(isStart() == false && isPlayPause()){
                    setStart(true);
                    setPlayPause(true);
                    tv.setText("play");
                    TextView tx = (TextView) findViewById(R.id.textView);
                    tx.setText("0");
                    for(int i = 0; i < height; i++){
                        for(int j= 0; j < width; j++){
                            board01[i][j] = 0;
                            boardColor[i][j] = R.drawable.textview_border;
                            TextView text = (TextView) findViewById(board[i][j]);
                            text.setBackgroundResource(R.drawable.textview_border);
                        }
                    }
                }
                if(!firstclick){
                    setStart(true);
                    setPlayPause(true);
                    tv.setText("play");
                    firstclick = true;
                    RelativeLayout rel = (RelativeLayout) findViewById(R.id.Rel);
                    System.out.println(rel.getHeight() + " " + rel.getWidth());
                    RelativeLayout game = (RelativeLayout) findViewById(R.id.Game);
                    System.out.println("Game: " + game.getHeight() + " " + game.getWidth());
                    int relheight = (rel.getHeight() - 10) / height;
                    int relwidth = (rel.getWidth() - 10) / width;
                    int size = relheight;
                    if (relheight > relwidth) {
                        size = relwidth;
                    }
                    System.out.println("size: " + size);
                    board = initializeBoard(size);
                    board01 = initializeBoard01();
                    boardColor = initializeBoardColor();
                    showGame();
                    t.start();
                }
            }

        });



        Button left = (Button) findViewById(R.id.left);
        left.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                if(isPlayPause()){
                    for(Coordinate c: actualTetra.getTab()){
                        TextView tv = (TextView) findViewById(board[c.getI()][c.getJ()]);
                        tv.setBackgroundResource(R.drawable.textview_border);
                    }
                    boolean move = true;
                    for(Coordinate c: actualTetra.getTab()){
                        if((!(c.getJ()-1 >= 0)) || board01[c.getI()][c.getJ()-1]==1){
                            move = false;
                        }
                    }
                    if(move){
                        for(Coordinate c: actualTetra.getTab()){
                            //board01[c.getI()][c.getJ()]=0;
                            c.setJ(c.getJ()-1);
                            //board01[c.getI()][c.getJ()]=1;
                        }
                    }
                    for(Coordinate c: actualTetra.getTab()){
                        TextView tv = (TextView) findViewById(board[c.getI()][c.getJ()]);
                        tv.setBackgroundResource(actualTetra.getColor());
                    }
                }

            }

        });

        Button right = (Button) findViewById(R.id.right);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlayPause()){
                    for(Coordinate c: actualTetra.getTab()){
                        TextView tv = (TextView) findViewById(board[c.getI()][c.getJ()]);
                        tv.setBackgroundResource(R.drawable.textview_border);
                    }
                    boolean move = true;
                    for(Coordinate c: actualTetra.getTab()){
                        if((!(c.getJ()+1 < width)) || board01[c.getI()][c.getJ()+1]==1){
                            move = false;
                        }
                    }
                    if(move){
                        for(Coordinate c: actualTetra.getTab()){
                            //board01[c.getI()][c.getJ()]=0;
                            c.setJ(c.getJ()+1);
                            //board01[c.getI()][c.getJ()]=1;
                        }
                    }
                    for(Coordinate c: actualTetra.getTab()){
                        TextView tv = (TextView) findViewById(board[c.getI()][c.getJ()]);
                        tv.setBackgroundResource(actualTetra.getColor());
                    }
                }

            }
        });

        Button rotate = (Button) findViewById(R.id.rotate);
        rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPlayPause()){
                    for(Coordinate c: actualTetra.getTab()){
                        TextView tv = (TextView) findViewById(board[c.getI()][c.getJ()]);
                        tv.setBackgroundResource(R.drawable.textview_border);
                    }
                    getActualTetra().rotate(board01);
                    for(Coordinate c: actualTetra.getTab()){
                        TextView tv = (TextView) findViewById(board[c.getI()][c.getJ()]);
                        tv.setBackgroundResource(actualTetra.getColor());
                    }
                }
            }
        });

        Button speed = (Button) findViewById(R.id.speed);
        speed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlayPause()){
                    for(Coordinate c: actualTetra.getTab()){
                        TextView tv = (TextView) findViewById(board[c.getI()][c.getJ()]);
                        tv.setBackgroundResource(R.drawable.textview_border);
                    }
                    boolean move = true;
                    for(Coordinate c: actualTetra.getTab()){
                        if((!(c.getI()+1 < height)) || board01[c.getI()+1][c.getJ()]==1){
                            move = false;
                        }
                    }
                    if(move){
                        for(Coordinate c: actualTetra.getTab()){
                            //board01[c.getI()][c.getJ()]=0;
                            c.setI(c.getI()+1);
                            //board01[c.getI()][c.getJ()]=1;
                        }
                    }
                    for(Coordinate c: actualTetra.getTab()){
                        TextView tv = (TextView) findViewById(board[c.getI()][c.getJ()]);
                        tv.setBackgroundResource(actualTetra.getColor());
                    }
                }
            }
        });
    }

    public boolean lineOnlyOne(int i){
        for(int j = 0; j < width; j++){
            if(board01[i][j]==0){
                return false;
            }
        }
        return true;
    }

    public void changeLines(int line){
        if(line != 0){
            for(int i=line; i > 0; i--){
                for(int j=0; j < width; j++){
                    board01[i][j]=board01[i-1][j];
                    boardColor[i][j]=boardColor[i-1][j];
                    TextView tv = (TextView) findViewById(board[i][j]);
                    tv.setBackgroundResource(boardColor[i][j]);
                }
            }
        }
        for(int j=0; j < width ;j++){
            board01[0][j] = 0;
            boardColor[0][j] = R.drawable.textview_border;
            TextView tv = (TextView) findViewById(board[0][j]);
            tv.setBackgroundResource(boardColor[0][j]);
        }
    }

    public void eliminateLines(){
        boolean enter = true;
        while(enter){
            enter = false;
            for(int i=0; i < height; i++){
                if(lineOnlyOne(i)){
                    enter = true;
                    changeLines(i);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView tv = (TextView) findViewById(R.id.textView);
                            tv.setText(String.valueOf(Integer.parseInt(tv.getText().toString()) + 1));
                        }
                    });
                }
            }
        }

    }

    boolean possibleSet(){
        for(Coordinate c: actualTetra.getTab()){
            if(board01[c.getI()][c.getJ()] == 1){
                return false;
            }
        }
        return true;
    }

    public void setTetraminoe(){
        for(Coordinate c: actualTetra.getTab()){
            board01[c.getI()][c.getJ()]=1;
            boardColor[c.getI()][c.getJ()]=actualTetra.getColor();
        }
    }

    boolean moveDown(){
        for(Coordinate c: actualTetra.getTab()){
            TextView tv = (TextView) findViewById(board[c.getI()][c.getJ()]);
            tv.setBackgroundResource(R.drawable.textview_border);
        }
        boolean move = true;
        for(Coordinate c: actualTetra.getTab()){
            if((!(c.getI()+1 < height)) || board01[c.getI()+1][c.getJ()]==1){
                move = false;
            }
        }
        if(move){
            for(Coordinate c: actualTetra.getTab()){
                //board01[c.getI()][c.getJ()]=0;
                c.setI(c.getI()+1);
                //board01[c.getI()][c.getJ()]=1;
            }
        }
        for(Coordinate c: actualTetra.getTab()){
            TextView tv = (TextView) findViewById(board[c.getI()][c.getJ()]);
            tv.setBackgroundResource(actualTetra.getColor());
        }
        return move;
    }

    public Tetraminoes startGame(){
        Random random = new Random();
        int nb;
        nb = random.nextInt(7);
        TetraminoeType tt = TetraminoeType.BAR;
        int i = 0;
        for (TetraminoeType myVar : TetraminoeType.values()) {
            if(i == nb){
                tt = myVar;
            }
            i++;
        }
        Tetraminoes new_tetra = new Tetraminoes(tt);
        for(Coordinate c: new_tetra.getTab()){
            TextView tv = (TextView) this.findViewById(this.board[c.getI()][c.getJ()]);
            tv.setBackgroundResource(new_tetra.getColor());
        }
        return  new_tetra;
    }


    public int[][] initializeBoard(int size) {
        int[][] new_board = new int[height][width];
        TableLayout tl = (TableLayout) findViewById(R.id.table);
        for (int k = 0; k < height; k++) {
            TableRow row = new TableRow(this);
            for (int i = 0; i < width; i++) {
                TextView tv = new TextView(this);
                tv.setText("");
                tv.setHeight(size);
                tv.setWidth(size);
                tv.setVisibility(View.INVISIBLE);
                tv.setId(View.generateViewId());
                new_board[k][i] = tv.getId();
                tv.setBackgroundResource(R.drawable.textview_border);
                row.addView(tv);
            }
            tl.addView(row);
        }
        return new_board;
    }

    public void showGame() {
        for (int k = 0; k < height; k++) {
            for (int i = 0; i < width; i++) {
                TextView tv = (TextView) this.findViewById(this.board[k][i]);
                tv.setVisibility(View.VISIBLE);
            }
        }
    }

    public int[][] initializeBoard01() {
        int[][] new_board01 = new int[height][width];
        {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    new_board01[i][j] = 0;
                }
            }
            return new_board01;
        }

    }

    public int[][] initializeBoardColor() {
        int[][] new_board01 = new int[height][width];
        {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    new_board01[i][j] = R.drawable.textview_border;
                }
            }
            return new_board01;
        }

    }

    public Tetraminoes getActualTetra() {
        return actualTetra;
    }

    public void setActualTetra(Tetraminoes actualTetra) {
        this.actualTetra = actualTetra;
    }

    public boolean isPlayPause() {
        return playPause;
    }

    public void setPlayPause(boolean playPause) {
        this.playPause = playPause;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }
}