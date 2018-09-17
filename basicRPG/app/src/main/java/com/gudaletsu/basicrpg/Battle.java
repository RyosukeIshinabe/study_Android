package com.gudaletsu.basicrpg;

public class Battle {
    private final int MAXENEMY = 4;
    private Player player;
    private Enemy enemy;

    public Battle(Player player) {
        this.player = player;
        this.enemy = new Enemy();
    }

    // 攻撃
    public void attack(Character attackSideCharactor, Character defenceSideCharactor) {
        defenceSideCharactor.setHp(attackSideCharactor.getAtk() * 2 - defenceSideCharactor.getDef());
    }

}
