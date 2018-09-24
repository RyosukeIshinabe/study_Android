package com.gudaletsu.basicrpg;

public class Enemy extends Character {

    // コンストラクタ
    public Enemy() {
        super();
        init();
    }

    // レベル指定ありコンストラクタ
    public Enemy(int lv) {
        super();
        init(lv);
    }

    // 初期化
    public void init() {
        this.lv = 1;
        this.name = "スライム";
        checkStatus();
    }

    // レベル指定あり初期化
    public void init(int lv) {
        this.lv = lv;
        this.name = "スライム";
        checkStatus();
    }

    // レベルを元にステータスを変更
    public void checkStatus() {
        switch ( this.lv ) {
            case 1:
                this.atk = 3;
                this.def = 2;
                this.maxHp = this.hp = 10;
                this.maxMp = this.mp = 0;
                this.exp = 2;
                this.gold = 1;
                break;
            case 2:
                this.atk = 6;
                this.def = 5;
                this.maxHp = this.hp = 22;
                this.maxMp = this.mp = 1;
                this.exp = 4;
                this.gold = 3;
                break;
            case 3:
                this.atk = 10;
                this.def = 9;
                this.maxHp = this.hp = 36;
                this.maxMp = this.mp = 2;
                this.exp = 6;
                this.gold = 5;
                break;
            case 4:
                this.atk = 15;
                this.def = 14;
                this.maxHp = this.hp = 52;
                this.maxMp = this.mp = 3;
                this.exp = 8;
                this.gold = 7;
                break;
            case 5:
                this.atk = 21;
                this.def = 20;
                this.maxHp = this.hp = 70;
                this.maxMp = this.mp = 4;
                this.exp = 10;
                this.gold = 9;
                break;
            case 6:
                this.atk = 28;
                this.def = 27;
                this.maxHp = this.hp = 90;
                this.maxMp = this.mp = 5;
                this.exp = 12;
                this.gold = 11;
                break;
            default:
                this.atk = 36;
                this.def = 35;
                this.maxHp = this.hp = 112;
                this.maxMp = this.mp = 6;
                this.exp = 14;
                this.gold = 13;
                break;
        }
    }


}
