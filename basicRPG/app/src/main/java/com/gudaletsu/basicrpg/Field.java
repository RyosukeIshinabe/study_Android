package com.gudaletsu.basicrpg;
import android.util.Log;

import java.util.Random;

public class Field {
    private static int magnification = 0;
    public static final int COLUMN = 11;
    public static final int ROW = 12;

    // マップの地形
    private int[][] terrain = {
         //  A  B  C  D  E  F  G  H  I  J  K  L
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},  // 1
            {0, 2, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0},  // 2
            {0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0},  // 3
            {0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0},  // 4
            {0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0},  // 5
            {0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0},  // 6
            {0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},  // 7
            {0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0},  // 8
            {0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0},  // 9
            {0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0},  // 10
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},  // 11
    };

    public int getTerrain(int column, int row) {
        return this.terrain[column][row];
    }

    public void setTerrain(int column, int row, int status) {
        this.terrain[column][row] = status;
    }

    // マップ1マスを表現する文字列を返す（マップ描画用）
    public String getTerrainString(int column, int row) {
        String columnStr = "";
        String rowStr = "";
        switch (column) {
            case 0:
                columnStr = "1";
                break;
            case 1:
                columnStr = "2";
                break;
            case 2:
                columnStr = "3";
                break;
            case 3:
                columnStr = "4";
                break;
            case 4:
                columnStr = "5";
                break;
            case 5:
                columnStr = "6";
                break;
            case 6:
                columnStr = "7";
                break;
            case 7:
                columnStr = "8";
                break;
            case 8:
                columnStr = "9";
                break;
            case 9:
                columnStr = "10";
                break;
            case 10:
                columnStr = "11";
                break;
            case 11:
                columnStr = "12";
                break;
            case 12:
                columnStr = "13";
                break;
            default:
                columnStr = "0";
        }
        switch (row) {
            case 0:
                rowStr = "A";
                break;
            case 1:
                rowStr = "B";
                break;
            case 2:
                rowStr = "C";
                break;
            case 3:
                rowStr = "D";
                break;
            case 4:
                rowStr = "E";
                break;
            case 5:
                rowStr = "F";
                break;
            case 6:
                rowStr = "G";
                break;
            case 7:
                rowStr = "H";
                break;
            case 8:
                rowStr = "I";
                break;
            case 9:
                rowStr = "J";
                break;
            case 10:
                rowStr = "K";
                break;
            case 11:
                rowStr = "L";
                break;
            case 12:
                rowStr = "M";
                break;
            default:
                rowStr = "A";
        }
        return rowStr + columnStr;
    }

    // エンカウント判定（一歩ごとに呼ばれる）敵と遭遇したらtrue
    public boolean randomEncounter() {
        Log.d("event", "[debug] encounter called");
        Random random = new Random();
        int threshold = random.nextInt(10) * magnification;
        Log.d("event", "[debug] magnification is : " + magnification);
        Log.d("event", "[debug] created threshold is : " + threshold);
        if ( threshold >= 30 ) {
            magnification = 0;
            return true;
        } else {
            magnification++;
            return false;
        }
    }

    // プレイヤーが動く処理（引数は方向。0：上、1：右、2：下、3：左）
    public boolean walkPlayer(int direction) {
        Log.d("event", "[debug] walk called");

        // 現在の座標を受け取る
        int currentX = returnPlayerPosision(true);
        int currentY = returnPlayerPosision(false);

        if ( direction == 0 ) { // 上方向に動く場合
            if ( getTerrain((currentY-1),currentX) == 1 ) { // 1マス上の地形が1なら
                setTerrain((currentY-1),currentX,2);    // 1マス上を2にして
                setTerrain((currentY),currentX,1);  // 現在のマスを1にする
                return true;
            } else {    // 1マス上の地形が1以外なら
                Log.d("event", "[debug] can not move forward in that direction");
            }
        } else if ( direction == 2 ) {  // 下方向に動く場合
            if ( getTerrain((currentY+1),currentX) == 1 ) {
                setTerrain((currentY+1),currentX,2);
                setTerrain((currentY),currentX,1);
                return true;
            } else {
                Log.d("event", "[debug] can not move forward in that direction");
            }
        } else if ( direction == 1 ) {  // 右方向に動く場合
            if ( getTerrain(currentY,(currentX+1)) == 1 ) {
                setTerrain(currentY,(currentX+1),2);
                setTerrain(currentY,currentX,1);
                return true;
            } else {
                Log.d("event", "[debug] can not move forward in that direction");
            }
        } else if ( direction == 3 ) {  // 左方向に動く場合
            if ( getTerrain(currentY,(currentX-1)) == 1 ) {
                setTerrain(currentY,(currentX-1),2);
                setTerrain(currentY,currentX,1);
                return true;
            } else {
                Log.d("event", "[debug] can not move forward in that direction");
            }
        }
        return false;
    }

    // プレイヤーの位置を特定する
    public int returnPlayerPosision(boolean directionX) {
        for (int i = 0; i < COLUMN; i++ ) {
            for (int j = 0; j < ROW; j++ ) {
                if ( this.terrain[i][j] == 2 ) {
                    if ( directionX ) {
                        return j;
                    } else if ( !directionX ) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }



}
