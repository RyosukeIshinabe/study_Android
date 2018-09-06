package com.gudaletsu.poker;

import java.util.Scanner;

public class Coin {

    private int quantity;   // 持っているコイン
    private int bet;	// BETされたコイン
    private final int ERROR = -99;	// エラー用
    public static Scanner scan = new Scanner(System.in);	// 入力用クラス

    // コンストラクタ
    public Coin() {
        init();
    }

    // 初期化
    public void init() {
        this.quantity = 0;
        this.bet = 0;
    }

    // 指定の枚数のコインを所持コインにプラスする
    public void giveCoin(int coin) {
        this.quantity += coin;
    }

    /*
        フィールドのゲッターとセッター
     */

    // コインのゲッター
    public int getQuantity() {
        return this.quantity;
    }

    // BETのゲッター
    public int getBet() {
        return this.bet;
    }

    // コインのセッター
    public void setQuantity(int newQuantity) {
        this.quantity = newQuantity;
    }

    // BETのセッター
    public void setBet(int newBet) {
        this.bet = newBet;
    }

    /*
        その他の処理
     */

    // 所持コインからBETに換金する
    public void changeFromCoinToBet(int newBet) {
        if ( newBet > this.quantity ) {
            System.out.println("所持コインが足りません。");
        } else {
            this.quantity -= newBet;
            this.bet = newBet;
        }
    }

    // BETから所持コインに換金する
    public void changeFromBetToCoin() {
        this.quantity += this.bet;
        this.bet = 0;
    }

    // BETの倍がけ処理
    public void multiplicate(int multiplication) {
        this.bet = this.bet * multiplication;
    }

    // BETを入力させる処理
    public void inputBet() {
        int betInt;

        // 正しい入力があるまで繰り返す
        do {
            System.out.println("手持ちコイン：" + getQuantity());
            System.out.println("いくらBETしますか？");
            String betStr = scan.next();    // 入力を待つ
            betInt = checkInputBet(betStr);	// 正規表現チェックしてint型に変換
        } while ( betInt != ERROR );

        changeFromCoinToBet(betInt);	// BETして所持コインを引く
    }

    // BETの正規表現
    public int checkInputBet(String betStr) {

        if ( betStr.matches("^[0-9]{1,4}$") ) {	// 入力された文字が数字の1桁〜4桁だった場合
            int betInt = Integer.parseInt(betStr);	// int型に変換

            // 1以上で、かつ所持コインより小さければ
            if ( betInt >= 1 && betInt <= this.getQuantity() ) {
                return betInt;  // その値を返す
                // 条件を満たさない場合はERROR定数を返す
            } else {
                return ERROR;
            }
        } else {
            return ERROR;
        }
    }
}
