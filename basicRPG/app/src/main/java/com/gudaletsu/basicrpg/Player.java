package com.gudaletsu.basicrpg;

import android.util.Log;

import java.io.Serializable;

public class Player extends Character implements Serializable {

    private final int EXP_FOR_LV1 = 0;
    private final int EXP_FOR_LV2 = 10;
    private final int EXP_FOR_LV3 = 30;
    private final int EXP_FOR_LV4 = 70;
    private final int EXP_FOR_LV5 = 150;
    private final int EXP_FOR_LV6 = 310;
    private final int EXP_FOR_LV7 = 630;

    // コンストラクタ
    public Player() {
        super();
        init();
    }

    // 初期化
    public void init() {
        this.name = "勇者";
        this.lv = 1;
        this.gold = 100;
        this.exp = 0;
        checkStatus();
    }

    // 経験値を元にレベルを変更
    public void checkLv() {
        if ( this.exp < EXP_FOR_LV2) {
            if ( this.lv != 1 ) {
                setLv(1);
                checkStatus();
            }
        } else if ( this.exp < EXP_FOR_LV3) {
            if ( this.lv != 2 ) {
                setLv(2);
                checkStatus();
            }
        } else if ( this.exp < EXP_FOR_LV4) {
            if ( this.lv != 3 ) {
                setLv(3);
                checkStatus();
            }
        } else if ( this.exp < EXP_FOR_LV5) {
            if ( this.lv != 4 ) {
                setLv(4);
                checkStatus();
            }
        } else if ( this.exp < EXP_FOR_LV6) {
            if ( this.lv != 5 ) {
                setLv(5);
                checkStatus();
            }
        } else if ( this.exp < EXP_FOR_LV7) {
            if ( this.lv != 6 ) {
                setLv(6);
                checkStatus();
            }
        }
    }

    // レベルを元にステータスを変更
    public void checkStatus() {
        switch ( this.lv ) {
            case 1:
                this.atk = 3;
                this.def = 3;
                this.maxHp = this.hp = 30;
                this.maxMp = this.mp = 5;
                break;
            case 2:
                this.atk = 8;
                this.def = 6;
                this.maxHp = this.hp = 44;
                this.maxMp = this.mp = 12;
                break;
            case 3:
                this.atk = 14;
                this.def = 10;
                this.maxHp = this.hp = 62;
                this.maxMp = this.mp = 19;
                break;
            case 4:
                this.atk = 21;
                this.def = 15;
                this.maxHp = this.hp = 84;
                this.maxMp = this.mp = 26;
                break;
            case 5:
                this.atk = 29;
                this.def = 21;
                this.maxHp = this.hp = 110;
                this.maxMp = this.mp = 33;
                break;
            case 6:
                this.atk = 38;
                this.def = 28;
                this.maxHp = this.hp = 140;
                this.maxMp = this.mp = 40;
                break;
            default:
                this.atk = 99;
                this.def = 99;
                this.maxHp = this.hp = 999;
                this.maxMp = this.mp = 99;
                break;
        }
    }

    // 次or前のレベルまでの経験値を取得（引数が正の値：次のレベル、負の値：前のレベル）
    public int getNecessaryExpForNextLevel(int amount) {
        int needExp = 0;

        // 次のレベルに上がるために必要な経験値
        if ( amount > 0 ) {
            switch ( this.lv ) {
                case 1:
                    needExp = EXP_FOR_LV2 - this.exp;
                    break;
                case 2:
                    needExp = EXP_FOR_LV3 - this.exp;
                    break;
                case 3:
                    needExp = EXP_FOR_LV4 - this.exp;
                    break;
                case 4:
                    needExp = EXP_FOR_LV5 - this.exp;
                    break;
                case 5:
                    needExp = EXP_FOR_LV6 - this.exp;
                    break;
                case 6:
                    needExp = EXP_FOR_LV7 - this.exp;
                    break;
                default:
                    needExp = 0;
            }
            return needExp;

        // 前のレベルに戻るために必要な経験値
        } else if ( amount < 0 ) {
            switch ( this.lv ) {
                case 1:
                    needExp = 0;
                    break;
                case 2:
                    needExp = EXP_FOR_LV1 - this.exp;
                    break;
                case 3:
                    needExp = EXP_FOR_LV2 - this.exp;
                    break;
                case 4:
                    needExp = EXP_FOR_LV3 - this.exp;
                    break;
                case 5:
                    needExp = EXP_FOR_LV4 - this.exp;
                    break;
                case 6:
                    needExp = EXP_FOR_LV5 - this.exp;
                    break;
                case 7:
                    needExp = EXP_FOR_LV6 - this.exp;
                    break;
                default:
                    needExp = 0;
            }
            return needExp;

        } else {
            return 0;
        }
    }

}

