package com.gudaletsu.basicrpg;

abstract public class Character {
    protected String name;
    protected int lv;
    protected int gold;
    protected int atk;
    protected int def;
    protected int maxHp;
    protected int hp;
    protected int maxMp;
    protected int mp;
    protected int exp;

    // コンストラクタ
    public Character() {
    }

    /*
        ゲッターとセッター
     */

    public String getName() {
        return this.name;
    }
    public int getLv() {
        return this.lv;
    }
    public int getGold() {
        return this.gold;
    }
    public int getAtk() {
        return this.atk;
    }
    public int getDef() {
        return this.def;
    }
    public int getMaxHp() {
        return this.maxHp;
    }
    public int getHp() {
        return this.hp;
    }
    public int getMaxMp() {
        return this.maxMp;
    }
    public int getMp() {
        return this.mp;
    }
    public int getExp() {
        return this.exp;
    }
    public void setName(String newName) {
        this.name = newName;
    }
    public void setLv(int newLv) {
        this.lv = newLv;
    }
    public void setGold(int newGold) {
        this.gold = newGold;
    }
    public void setAtk(int newAtk) {
        this.atk = newAtk;
    }
    public void setDef(int newDef) {
        this.def = newDef;
    }
    public void setMaxHp(int newMaxHp) {
        this.maxHp = newMaxHp;
    }
    public void setHp(int newHp) {
        this.hp = newHp;
    }
    public void setMaxMp(int newMaxMp) {
        this.maxMp = newMaxMp;
    }
    public void setMp(int newMp) {
        this.mp = newMp;
    }
    public void setExp(int newExp) {
        this.exp = newExp;
    }

    /*
        各フィールドの値の変更
     */

    // レベルアップ
    public void lvUp() {
        this.lv++;
    }

    // レベルダウン
    public void lvDown() {
        this.lv--;
    }

    // ゴールドを変更する
    public void changeGold(int amount) {
        this.gold += amount;
        // 最大値を超えないようにする
        if ( this.gold >= 9999 ) {
            this.gold = 9999;
        } else if ( this.gold <= 0 ) {
            this.gold = 0;
        }
    }

    // HPを変更する
    public void changeHp(int amount) {
        this.hp += amount;
        // 最大値を超えないようにする
        if ( this.hp >= this.maxHp ) {
            this.hp = this.maxHp;
        } else if ( this.hp <= 0 ) {
            this.hp = 0;
        }
    }

    // MPを変更する
    public void changeMp(int amount) {
        this.mp += amount;
        // 最大値を超えないようにする
        if ( this.mp >= this.maxMp ) {
            this.mp = this.maxMp;
        } else if ( this.mp <= 0 ) {
            this.mp = 0;
        }
    }

    // 攻撃力を変更する
    public void changeAtk(int amount) {
        this.atk *= amount;
    }

    // 防御力を変更する
    public void changeDef(int amount) {
        this.def *= amount;
    }

    // Expを変更する
    public void changeExp(int amount) {
        this.exp += amount;
    }

    // ステータスをチェックする（抽象メソッド）
    abstract public void checkStatus();
}
