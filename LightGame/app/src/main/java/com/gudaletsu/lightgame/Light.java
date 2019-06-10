package com.gudaletsu.lightgame;

public class Light {

    private boolean lightsOn;
    private int[] aaa = new int[100];

    // コンストラクタ（引数で点灯/消灯を受け取る）
    Light(boolean defaultLightsOn) {
        this.lightsOn = defaultLightsOn;
    }

    // 引数がなかった場合は消灯にしておく
    Light() {
        this.lightsOn = false;
    }

    // ゲッター
    public boolean isLightsOn() {
        return lightsOn;
    }

    // セッター
    public void setLightsOn(boolean newLightStatus) {
        lightsOn = newLightStatus;
    }

    // 点灯/消灯を逆にする
    public void switchLight() {
        this.lightsOn = !this.lightsOn;
    }

    // デバッグ用：点灯/消灯 確認用
    public void displayLightStatus() {
        System.out.println("点灯ステータスは:" + this.lightsOn + " です");
    }

    public void loopTest() {
        for ( int i = 0; i <= aaa.length; i++ ) {

        }
    }
}
