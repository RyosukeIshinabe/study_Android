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
        this.name = "スライム Lv." + this.lv;
        checkStatus();
    }

    // レベル指定あり初期化
    public void init(int lv) {
        this.lv = lv;
        this.name = "スライム Lv." + this.lv;
        checkStatus();
    }

    // レベルを元にステータスを変更
    public void checkStatus() {
        switch ( this.lv ) {
            case 1:
                this.atk = 2;
                this.def = 2;
                this.maxHp = this.hp = 8;
                this.maxMp = this.mp = 0;
            case 2:
                this.atk = 4;
                this.def = 5;
                this.maxHp = this.hp = 16;
                this.maxMp = this.mp = 1;
            case 3:
                this.atk = 7;
                this.def = 9;
                this.maxHp = this.hp = 26;
                this.maxMp = this.mp = 2;
            case 4:
                this.atk = 11;
                this.def = 14;
                this.maxHp = this.hp = 38;
                this.maxMp = this.mp = 3;
            case 5:
                this.atk = 16;
                this.def = 20;
                this.maxHp = this.hp = 52;
                this.maxMp = this.mp = 4;
            case 6:
                this.atk = 22;
                this.def = 27;
                this.maxHp = this.hp = 68;
                this.maxMp = this.mp = 5;
            default:
                this.atk = 29;
                this.def = 35;
                this.maxHp = this.hp = 86;
                this.maxMp = this.mp = 6;
        }
    }


}
