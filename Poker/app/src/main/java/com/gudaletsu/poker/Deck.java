package com.gudaletsu.poker;

import java.util.Random;

public class Deck {

    private Card[] deck;	// カードを入れるための箱（0〜51）
    private static final int MAXNUM = 13;	// 数字の種類
    private static final int MAXMARK = 4;	// 使うマークの種類
    private static final int ALLCARD = MAXNUM * MAXMARK;

    // コンストラクタ
    public Deck() {
    }

    // トランプのカードを全て作成
    public void createTrump() {
        deck = new Card[ALLCARD];
        int index = 0;	// 配列のkey用
        for ( int i = 0; i < MAXMARK; i++ ) {
            for ( int j = 1; j <= MAXNUM; j++ ) {
                deck[index] = new Card(i,j,false);
                index++;
            }
        }
    }

    // 指定されたカード枚数のdeckを用意
    public void makeDeck(int quantity) {
        deck = new Card[quantity];
    }

    /*
        フィールドのゲッターとセッター
     */

    // デッキを取得
    public Card[] getDeck() {
        return deck;
    }

    // デッキをset
    public void setDeck(Card[] deck) {
        this.deck = deck;
    }

    // 最大枚数を取得
    public int getALLCARD() {
        return ALLCARD;
    }

    /*
        メソッド
     */

    // 自身のデッキから指定された番号のカードを取得
    // 参照するだけ。取得元のデッキからカードを消したい場合は...AndErase()を使うこと
    public Card getCardFromDeck(int index) {
        return this.deck[index];
    }

    // 自身のデッキから指定された番号のカードを取得して元のデッキから消す
    public Card getCardFromDeckAndErase(int index) {
        Card refuge = deck[index];	// 引いたカードを一旦避難
        deck[index] = null;	// 引いたカードの場所をnullにする
        return refuge;	// 避難していたカードをreturn
    }

    // 自身のデッキから指定された番号のカードのマークのみを取得する
    public int getMarkOfCardFromDeck(int index) {
        return this.deck[index].getMark();
    }

    // 自身のデッキから指定された番号のカードのナンバーのみを取得する
    public int getNumberOfCardFromDeck(int index) {
        return this.deck[index].getNumber();
    }

    // 自身のデッキから指定された番号のカードのシャッフル値のみを取得する
    public boolean getShuffleOfCardFromDeck(int index) {
        return this.deck[index].getShuffle();
    }

    // 自身のデッキの指定された番号のカードのシャッフル値を変更する
    public void changeShuffleOfCardFromDeck(int index) {
        this.deck[index].changeShuffle();
    }

    // 自身のデッキから指定された番号のカードを文字としてreturnする
    public String displayCardFromDeck(int index) {
        return deck[index].toString();
    }

    // 自身のデッキから指定された番号のカードのSTAYorCHANGEをreturnする
    public String displayStayOrChangeFromDeck(int index) {
        return deck[index].toStringStayOrChange();
    }

    // 自身のデッキに格納された（null以外の）カードの枚数を取得
    public int getDeckLength() {
        int count = 0;
        for ( int i = 0; i < this.deck.length; i++ ) {
            if ( deck[i] != null ) {
                count++;
            }
        }
        return count;
    }

    // 自身のデッキからランダムな未使用（nullではない）カードを引いて、元のデッキから消す
    public Card takeCardFromDeckAndErase() {
        if ( this.getDeckLength() == 0 ) {	// 残り枚数が0ならnullを返す
            return null;
        }

        Random rand = new Random();	// ランダムな数字を作成するためのクラス
        int index;

        do {
            index = rand.nextInt(this.deck.length);	// ランダムな数字を作成して
        } while ( deck[index] == null );	// そのカードがnullだった場合do{}内を繰り返す

        Card refuge = deck[index];	// 引いたカードを一旦避難
        deck[index] = null;	// 引いたカードの場所をnullにする
        return refuge;	// 避難していたカードをreturn
    }

    // 自身のデッキの末尾にカードを挿入する
    public void insertCard(Card card) {
        for ( int i = 0; i < this.deck.length; i++ ) {
            if ( this.deck[i] == null ) {
                this.deck[i] = card;
                break;
            }
        }
    }

    // 自身のデッキ内のカードを全て表示する
    public void displayDeck() {
        for ( int i = 0; i < this.deck.length; i++ ) {
            System.out.println(this.deck[i].toString());
        }
    }

    // 自身のデッキ内の1枚目をreturn（消去はしない）
    public Card getFirstCardFromDeck() {
        return this.deck[0];
    }

    // 自身のデッキ内の1枚目をreturnして消去
    public Card getFirstCardFromDeckAndErase() {
        Card refuge = deck[0];	// 引いたカードを一旦避難
        deck[0] = null;	// 引いたカードの場所をnullにする
        return refuge;
    }

    // 自身のデッキと、引数で指定されたデッキの枚数を比較する（自分の方が多い2、少ない1、同じ0）
    public int compareCardLength(Deck otherDeck) {
        if ( getDeckLength() > otherDeck.getDeckLength() ) {
            return 2;
        } else if ( getDeckLength() < otherDeck.getDeckLength() ) {
            return 1;
        } else {
            return 0;
        }
    }

    // デッキ内のカードを全て破棄
    public void setNull() {
        for ( int i = 0; i < getDeckLength(); i++ ) {
            deck[i] = null;
        }
    }

    // デッキ内のカードを調べ、マーク（0〜3）をキー、枚数を値にした配列を返す
    public int[] getSameMarkOfCardFromDeck() {

        // マークの種類分の配列を用意
        int[] sameMarkDeck = new int[MAXMARK];
        for ( int i = 0; i < MAXMARK; i++ ) {
            sameMarkDeck[i] = 0;
        }

        // デッキ内の全てのカードを調べて同じマークがあった場合インクリメント
        for ( int j = 0; j < this.getDeckLength(); j++ ) {
            int mark = this.deck[j].getMark();
            sameMarkDeck[mark]++;
        }
        return sameMarkDeck;
    }

    // デッキ内のカードを調べ、数字（1〜13）をキー、枚数を値にした配列を返す
    public int[] getSameNumOfCardFromDeck() {

        // 数字の種類分の配列を用意
        int[] sameNumkDeck = new int[MAXNUM];
        for ( int i = 0; i < MAXNUM; i++ ) {
            sameNumkDeck[i] = 0;
        }

        // デッキ内の全てのカードを調べて同じマークがあった場合インクリメント
        for ( int j = 0; j < this.getDeckLength(); j++ ) {
            int num = this.deck[j].getMark();
            sameNumkDeck[num]++;
        }
        return sameNumkDeck;
    }


}
