package com.gudaletsu.poker;

import java.util.Scanner;

public class Field {

    private Deck unUsedDeck = new Deck();	// 未使用のカードを格納するデッキ
    private Deck usedDeck = new Deck();  // 使用済みのカードを格納するデッキ
    private Deck onFieldDeck = new Deck();   // フィールド上に表示するためのデッキ
    private final int ALLCARD = 52;    // トランプの最大枚数
    private final int FIELDCARD = 5;    // フィールド上のカードの最大枚数
    private final int SHAFFLE = 2;    // シャッフルの最大回数
    private int round;	// 第*回戦
    private Scanner scan = new Scanner(System.in);	// 入力用クラス

    // コンストラクタ
    public Field() {
        unUsedDeck.createTrump();   // トランプのカードを全て作成して未使用デッキとして保存
        onFieldDeck.makeDeck(FIELDCARD);    // フィールド用のデッキを作成
        usedDeck.makeDeck(ALLCARD); // 使用済みデッキを作成
        round = 1;  // ラウンドを初期化
    }

    // 初期化
    public void init() {
        unUsedDeck.setNull();
        usedDeck.setNull();
        onFieldDeck.setNull();
        round = 1;
    }

    // シャッフル回数のゲッター
    public int getSHAFFLE() {
        return this.SHAFFLE;
    }

    /*
     *	未使用のデッキ用
     */

    // カード枚数をreturn
    public int getLengthOfUnUsedDeck() {
        return this.unUsedDeck.getDeckLength();
    }

    // nullではないカードを1枚引き、元のデッキから消す
    public Card takeCardFromUnUsedDeck() {
        return unUsedDeck.takeCardFromDeckAndErase();
    }

    /*
        使用済みデッキ用
     */

    // カードを1枚受け取ってデッキにセット
    public void setCardToUsedDeck(Card card) {
        usedDeck.insertCard(card);
    }

    // カード枚数をreturn
    public int getLengthOfUsedDeck() {
        return this.usedDeck.getDeckLength();
    }

    /*
     *	フィールドデッキ用
     */

    // カード枚数をreturn
    public int getLengthOfOnFieldDeck() {
        return this.onFieldDeck.getDeckLength();
    }

    // 指定のカードのシャッフル値を変更
    public void changeShuffleOfFieldDeck(int num) {
        onFieldDeck.changeShuffleOfCardFromDeck(num);
    }

    // ゲッター
    public Deck getOnFieldDeck() {
        return onFieldDeck;
    }

    // 同じマークのカードの配列をゲット
    public int[] getSameMarkOfCardFromOnFieldDeck() {
        return onFieldDeck.getSameMarkOfCardFromDeck();
    }

    // 同じ数字のカードの配列をゲット
    public int[] getSameNumOfCardFromOnFieldDeck() {
        return onFieldDeck.getSameNumOfCardFromDeck();
    }

    // 指定の番号のカードを表現する文字列を返す
    public String getStringOfCardFromOnFieldDeck(int num) {
        return onFieldDeck.displayCardFromDeck(num);
    }

    // 指定の番号のカードのSTAYorCHANGEを返す
    public String getStringStayOrChangeFromOnFieldDeck(int num) {
        return onFieldDeck.displayStayOrChangeFromDeck(num);
    }

    /*
        デッキ間の移動
     */

    // フィールドデッキに足りない枚数のカードを未使用デッキから引く
    public void moveCardFromUnUsedToField() {
        while ( onFieldDeck.getDeckLength() == FIELDCARD ) {
            onFieldDeck.insertCard(unUsedDeck.takeCardFromDeckAndErase());
        }
    }

    // フィールドデッキ内のシャッフル=trueのカードを使用済みデッキへ移し、元のデッキから消す
    public void moveShuffleCardFromFieldToUsedDeck() {
        for ( int i = 0; i < FIELDCARD; i++ ) {
            if ( onFieldDeck.getShuffleOfCardFromDeck(i) ) {
                usedDeck.insertCard(onFieldDeck.getCardFromDeckAndErase(i));
            }
        }
    }

    // フィールドデッキ内の全てのカードを使用済みデッキへ移す
    public void moveCardFromFieldToUsedDeck() {
        for ( int i = 0; i < FIELDCARD; i++ ) {
            usedDeck.insertCard(onFieldDeck.getCardFromDeckAndErase(i));
        }
    }

    /*
     *	その他必要な操作
     */

    // ゲームを続けるかどうか聞く
    public boolean selectContinue() {
        System.out.println("続けますか？");
        String selectContinueStr = scan.next();	// 入力を代入

        // 条件を満たした値が入力されるまで繰り返す
        while ( !selectContinueStr.equals("Y") && !selectContinueStr.equals("N") ) {
            System.out.println("入力が正しくありません");
            selectContinueStr = scan.next();
        }

        if ( selectContinueStr.equals("Y") ) {
            return true;
        } else {
            return false;
        }
    }

    /*
     *	ラウンド用
     */

    // ゲッター
    public int getRound() {
        return round;
    }

    // セッター
    public void setRound(int round) {
        this.round = round;
    }

    // 表示のみ
    public void displayRound() {
        System.out.println("### " + round + "/" + SHAFFLE + "回目のシャッフルです ###");
    }

    // インクリメントのみ
    public void incrementRound() {
        this.round++;
    }

    // 表示用のStringをreturn
    public String returnRoundString() {
        return "### " + round + "/" + SHAFFLE + "回目のシャッフルです ###";
    }

    // 表示用のStringをreturnしてインクリメント
    public String returnRoundStringAndIncrement() {
        this.round++;
        return "### " + round + "/" + SHAFFLE + "回目のシャッフルです ###";
    }



}
