package com.gudaletsu.basicrpg;

public class Player extends Character {

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

    // レベルを元にステータスを変更
    public void checkStatus() {
        switch ( this.lv ) {
            case 1:
                this.atk = 3;
                this.def = 3;
                this.maxHp = this.hp = 30;
                this.maxMp = this.mp = 5;
            case 2:
                this.atk = 8;
                this.def = 6;
                this.maxHp = this.hp = 44;
                this.maxMp = this.mp = 12;
            case 3:
                this.atk = 14;
                this.def = 10;
                this.maxHp = this.hp = 62;
                this.maxMp = this.mp = 19;
            case 4:
                this.atk = 21;
                this.def = 15;
                this.maxHp = this.hp = 84;
                this.maxMp = this.mp = 26;
            case 5:
                this.atk = 29;
                this.def = 21;
                this.maxHp = this.hp = 110;
                this.maxMp = this.mp = 33;
            case 6:
                this.atk = 38;
                this.def = 28;
                this.maxHp = this.hp = 140;
                this.maxMp = this.mp = 40;
            default:
                this.atk = 99;
                this.def = 36;
                this.maxHp = this.hp = 184;
                this.maxMp = this.mp = 47;
        }
    }

    // バトル後の一連の処理
    public void afterBattle(int getExp, int getGold) {
        changeExp(getExp);  // 入手したExpを加算
        checkLv();  // 経験値累計を元にレベルを変更
        checkStatus();  // レベルを元にステータスを変更
        changeGold(getGold);    // ゴールドを加算
    }

    // 死亡した時の処理
    public void die() {

    }

}

