package com.gudaletsu.basicrpg;

public class Battle {
    private final int MAXENEMY = 4;
    private Player player;
    private Enemy enemy;

    // コンストラクタ
    public Battle() {
    }

    // 対戦相手指定済みコンストラクタ
    public Battle(Player player) {
        this.player = player;
        this.enemy = new Enemy();
    }

    // 敵のインスタンスを取得する
    public Character getEnemy() {
        return this.enemy;
    }

    // 先頭の開始
    public void start() {

    }

    // 攻撃（あとでロジック調整する）ダメージ量をreturn
    public int attack(boolean ofPlayerTurn, double amount) {
        if ( ofPlayerTurn ) {   // プレイヤーの攻撃
            int damege = this.player.getAtk() * 2 - this.enemy.getDef();
            damege *= amount;
            this.enemy.changeHp(damege * -1);
            return damege;
        } else {    // 敵の攻撃
            int damege = this.enemy.getAtk() * 2 - this.player.getDef();
            damege *= amount;
            this.player.changeHp(damege * -1);
            return damege;
        }
    }

    // 回復（あとでロジック調整する）回復量をreturn
    public int cure() {
        int mpCost = 3;   // 消費MP
        if ( mpCost > this.player.getMp() ) {
            return 0;
        } else {
            int recovery = this.player.getLv() * 8; // 回復量
            this.player.changeHp(recovery); // HPを変更
            this.player.changeMp(mpCost * -1); // MPを変更
            return recovery;
        }
    }

}
