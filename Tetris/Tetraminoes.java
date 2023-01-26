package com.example.realtetris;

enum TetraminoeType{
    SQUARE,
    L_SHAPE_LEFT,
    L_SHAPE_RIGHT,
    Z_SHAPE_LEFT,
    Z_SHAPE_RIGHT,
    T_SHAPE,
    BAR;
}

public class Tetraminoes {
    private Coordinate[] tab;
    private int color;
    private TetraminoeType type;
    private int position = 0;
    public int width = 10;
    public int height = 20;

    public Tetraminoes(TetraminoeType t){
        this.type = t;
        switch(t){
            case SQUARE:
                tab = new Coordinate[]{
                        new Coordinate(0, 4),
                        new Coordinate(0, 5),
                        new Coordinate(1, 4),
                        new Coordinate(1, 5)
                };
                color = R.drawable.textview_square;
                break;
            case BAR:
                tab = new Coordinate[]{
                        new Coordinate(0, 3),
                        new Coordinate(0, 4),
                        new Coordinate(0, 5),
                        new Coordinate(0, 6)
                };
                color = R.drawable.textview_bar;
                break;
            case L_SHAPE_LEFT:
                tab = new Coordinate[]{
                        new Coordinate(0, 3),
                        new Coordinate(1, 3),
                        new Coordinate(1, 4),
                        new Coordinate(1, 5)
                };
                color = R.drawable.textview_l_left;
                break;
            case L_SHAPE_RIGHT:
                tab = new Coordinate[]{
                        new Coordinate(0, 5),
                        new Coordinate(1, 3),
                        new Coordinate(1, 4),
                        new Coordinate(1, 5)
                };
                color = R.drawable.textview_l_right;
                break;
            case Z_SHAPE_LEFT:
                tab = new Coordinate[]{
                        new Coordinate(0, 4),
                        new Coordinate(1, 3),
                        new Coordinate(1, 4),
                        new Coordinate(0, 5)
                };
                color = R.drawable.textview_z_left;
                break;
            case Z_SHAPE_RIGHT:
                tab = new Coordinate[]{
                        new Coordinate(0, 4),
                        new Coordinate(0, 3),
                        new Coordinate(1, 4),
                        new Coordinate(1, 5)
                };
                color = R.drawable.textview_z_right;
                break;
            case T_SHAPE:
                tab = new Coordinate[]{
                        new Coordinate(0, 4),
                        new Coordinate(1, 3),
                        new Coordinate(1, 4),
                        new Coordinate(1, 5)
                };
                color = R.drawable.textview_t;
                break;
        }
    }

    public TetraminoeType getType() {
        return type;
    }

    public void rotate(int[][] board){
        switch(type){
            case SQUARE:
                break;
            case BAR:
                tab = rotateBar(board);
                break;
            case T_SHAPE:
                tab = rotateT(board);
                break;
            case Z_SHAPE_LEFT:
                tab = rotateZLEFT(board);
                break;
            case Z_SHAPE_RIGHT:
                tab = rotateZRight(board);
                break;
            case L_SHAPE_LEFT:
                tab = rotateLLeft(board);
                break;
            case L_SHAPE_RIGHT:
                tab = rotateLRight(board);
                break;
        }
    }

    public boolean possibleRotation(int [][] board, Coordinate[] tabX){
        for(Coordinate c: tabX){
            if(!(c.getI() < height && c.getI() >= 0)) {
                return false;
            }
            if(!(c.getJ() < width && c.getJ() >= 0)) {
                return false;
            }
            if(board[c.getI()][c.getJ()] == 1){
                return false;
            }
        }
        return true;
    }

    public Coordinate[] rotateBar(int [][] board){
        if(getPosition() == 0){
            int x = tab[0].getI();
            int y = tab[0].getJ();
            Coordinate[] newTab = new Coordinate[]{
                    new Coordinate(x, y),
                    new Coordinate(x+1, y),
                    new Coordinate(x+2, y),
                    new Coordinate(x+3, y)
            };
            if(possibleRotation(board,newTab)){
                setPosition(1);
                return newTab;
            }
            return tab;
        }else {
            int x = tab[0].getI();
            int y = tab[0].getJ();
            Coordinate[] newTab = new Coordinate[]{
                    new Coordinate(x, y),
                    new Coordinate(x, y+1),
                    new Coordinate(x, y+2),
                    new Coordinate(x, y+3)
            };
            if(possibleRotation(board,newTab)){
                setPosition(0);
                return newTab;
            }
            return tab;
        }
    }

    public Coordinate[] rotateZRight(int [][] board){
        if(getPosition() == 0){
            int x = tab[0].getI();
            int y = tab[0].getJ();
            Coordinate[] newTab = new Coordinate[]{
                    new Coordinate(x, y),
                    new Coordinate(x-1, y+1),
                    new Coordinate(x+1, y),
                    new Coordinate(x, y+1)
            };
            if(possibleRotation(board,newTab)){
                setPosition(1);
                return newTab;
            }
            return tab;
        }else {
            int x = tab[0].getI();
            int y = tab[0].getJ();
            Coordinate[] newTab = new Coordinate[]{
                    new Coordinate(x, y),
                    new Coordinate(x, y-1),
                    new Coordinate(x+1, y),
                    new Coordinate(x+1, y+1)
            };
            if(possibleRotation(board,newTab)){
                setPosition(0);
                return newTab;
            }
            return tab;
        }
    }

    public Coordinate[] rotateZLEFT(int [][] board){
        if(getPosition() == 1){
            int x = tab[0].getI();
            int y = tab[0].getJ();
            Coordinate[] newTab = new Coordinate[]{
                    new Coordinate(x, y),
                    new Coordinate(x+1, y-1),
                    new Coordinate(x+1, y),
                    new Coordinate(x, y+1)
            };
            if(possibleRotation(board,newTab)){
                setPosition(0);
                return newTab;
            }
            return tab;
        }else if(getPosition() == 0) {
            int x = tab[0].getI();
            int y = tab[0].getJ();
            Coordinate[] newTab = new Coordinate[]{
                    new Coordinate(x, y),
                    new Coordinate(x, y+1),
                    new Coordinate(x+1, y+1),
                    new Coordinate(x-1, y)
            };
            if(possibleRotation(board,newTab)){
                setPosition(1);
                return newTab;
            }
            return tab;
        }
        return tab;
    }

    public Coordinate[] rotateLLeft(int [][] board){
        if(getPosition() == 0){
            int x = tab[0].getI();
            int y = tab[0].getJ();
            Coordinate[] newTab = new Coordinate[]{
                    new Coordinate(x-1, y),
                    new Coordinate(x+1, y),
                    new Coordinate(x, y),
                    new Coordinate(x-1, y+1)
            };
            if(possibleRotation(board,newTab)){
                setPosition(1);
                return newTab;
            }
            return tab;
        }else if(getPosition() == 1){
            int x = tab[0].getI();
            int y = tab[0].getJ();
            Coordinate[] newTab = new Coordinate[]{
                    new Coordinate(x, y),
                    new Coordinate(x, y+1),
                    new Coordinate(x, y-1),
                    new Coordinate(x+1, y+1)
            };
            if(possibleRotation(board,newTab)){
                setPosition(2);
                return newTab;
            }
            return tab;
        }else if(getPosition() == 2){
            int x = tab[0].getI();
            int y = tab[0].getJ();
            Coordinate[] newTab = new Coordinate[]{
                    new Coordinate(x, y),
                    new Coordinate(x-1, y),
                    new Coordinate(x+1, y),
                    new Coordinate(x+1, y-1)
            };
            if(possibleRotation(board,newTab)){
                setPosition(3);
                return newTab;
            }
            return tab;
        }else {
            int x = tab[0].getI();
            int y = tab[0].getJ();
            Coordinate[] newTab = new Coordinate[]{
                    new Coordinate(x, y-1),
                    new Coordinate(x+1, y-1),
                    new Coordinate(x+1, y),
                    new Coordinate(x+1, y+1)
            };
            if(possibleRotation(board,newTab)){
                setPosition(0);
                return newTab;
            }
            return tab;
        }
    }

    public Coordinate[] rotateLRight(int [][] board){
        if(getPosition() == 0){
            int x = tab[0].getI();
            int y = tab[0].getJ();
            Coordinate[] newTab = new Coordinate[]{
                    new Coordinate(x, y-1),
                    new Coordinate(x+1, y-1),
                    new Coordinate(x-1, y-1),
                    new Coordinate(x+1, y)
            };
            if(possibleRotation(board,newTab)){
                setPosition(1);
                return newTab;
            }
            return tab;
        }else if(getPosition() == 1){
            int x = tab[0].getI();
            int y = tab[0].getJ();
            Coordinate[] newTab = new Coordinate[]{
                    new Coordinate(x, y),
                    new Coordinate(x, y-1),
                    new Coordinate(x, y+1),
                    new Coordinate(x+1, y-1)
            };
            if(possibleRotation(board,newTab)){
                setPosition(2);
                return newTab;
            }
            return tab;
        }else if(getPosition() == 2){
            int x = tab[0].getI();
            int y = tab[0].getJ();
            Coordinate[] newTab = new Coordinate[]{
                    new Coordinate(x, y),
                    new Coordinate(x+1, y),
                    new Coordinate(x-1, y),
                    new Coordinate(x-1, y-1)
            };
            if(possibleRotation(board,newTab)){
                setPosition(3);
                return newTab;
            }
            return tab;
        }else {
            int x = tab[0].getI();
            int y = tab[0].getJ();
            Coordinate[] newTab = new Coordinate[]{
                    new Coordinate(x, y+1),
                    new Coordinate(x+1, y+1),
                    new Coordinate(x+1, y),
                    new Coordinate(x+1, y-1)
            };
            if(possibleRotation(board,newTab)){
                setPosition(0);
                return newTab;
            }
            return tab;
        }
    }

    public Coordinate[] rotateT(int [][] board){
        if(getPosition() == 0){
            int x = tab[0].getI();
            int y = tab[0].getJ();
            Coordinate[] newTab = new Coordinate[]{
                    new Coordinate(x, y),
                    new Coordinate(x-1, y),
                    new Coordinate(x+1, y),
                    new Coordinate(x, y+1)
            };
            if(possibleRotation(board,newTab)){
                setPosition(1);
                return newTab;
            }
            return tab;
        }else if(getPosition() == 1){
            int x = tab[0].getI();
            int y = tab[0].getJ();
            Coordinate[] newTab = new Coordinate[]{
                    new Coordinate(x, y),
                    new Coordinate(x, y-1),
                    new Coordinate(x, y+1),
                    new Coordinate(x+1, y)
            };
            if(possibleRotation(board,newTab)){
                setPosition(2);
                return newTab;
            }
            return tab;
        }else if(getPosition() == 2){
            int x = tab[0].getI();
            int y = tab[0].getJ();
            Coordinate[] newTab = new Coordinate[]{
                    new Coordinate(x, y),
                    new Coordinate(x-1, y),
                    new Coordinate(x+1, y),
                    new Coordinate(x, y-1)
            };
            if(possibleRotation(board,newTab)){
                setPosition(3);
                return newTab;
            }
            return tab;
        }else {
            int x = tab[0].getI();
            int y = tab[0].getJ();
            Coordinate[] newTab = new Coordinate[]{
                    new Coordinate(x, y),
                    new Coordinate(x+1, y),
                    new Coordinate(x+1, y-1),
                    new Coordinate(x+1, y+1)
            };
            if(possibleRotation(board,newTab)){
                setPosition(0);
                return newTab;
            }
            return tab;
        }
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Coordinate[] getTab() {
        return tab;
    }

    public void setTab(Coordinate[] tab) {
        this.tab = tab;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
