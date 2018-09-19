package com.gudaletsu.basicrpg;
import java.util.Random;

public class Field {
    private static int magnification = 0;

    // エンカウント判定（一歩ごとに呼ばれる）敵と遭遇したらtrue
    public boolean randomEncounter() {
        Random random = new Random();
        int threshold = random.nextInt(10) * magnification;
        if ( threshold >= 30 ) {
            magnification = 0;
            return true;
        } else {
            magnification++;
            return false;
        }
    }



}
