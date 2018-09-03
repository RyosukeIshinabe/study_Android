package com.gudaletsu.poker;

public class Card {

    private int mark;   // マーク（0〜3）
    private int number; // 数字（1〜13）
    private boolean shuffle; // シャッフルするかどうか
    private static final int SPADE = 3;	// スペード
    private static final int HEART = 2;	// ハート
    private static final int DIA = 1;	// ダイア
    private static final int CLUB = 0;	// クラブ

    // コンストラクタとゲッターセッター
    public Card() {
    }

    public Card(int mark, int number, boolean shuffle) {
        this.mark = mark;
        this.number = number;
        this.shuffle = shuffle;
    }
    public int getMark() {
        return mark;
    }
    public void setMark(int mark) {
        this.mark = mark;
    }
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    /*
        シャッフル用
     */

    // シャッフル値をセット
    public void setShuffle(boolean shuffle) {
        this.shuffle = shuffle;
    }

    // シャッフル値をゲット
    public boolean getShuffle() {
        return this.shuffle;
    }

    // シャッフル値を変更
    public void changeShuffle() {
        if ( this.shuffle == true ) {
            this.shuffle = false;
        } else if ( this.shuffle == false ) {
            this.shuffle = true;
        }
    }

    /*
        その他必要なメソッド
     */

    // カードを表現する文字列
    public String toString() {
        String markStr = "";
        String numberStr;

        // マーク用
        switch ( mark ) {
            case SPADE:
                markStr = "スペード";
                break;
            case HEART:
                markStr = "ハート";
                break;
            case DIA:
                markStr = "ダイヤ";
                break;
            case CLUB:
                markStr = "クラブ";
                break;
        }

        // 数字用
        switch ( number ) {
            case 11:
                numberStr = "J";
                break;
            case 12:
                numberStr = "Q";
                break;
            case 13:
                numberStr = "K";
                break;
            case 1:
                numberStr = "A";
                break;
            default:
                numberStr = String.valueOf(number);
                break;
        }
        return markStr + "の" + numberStr;
    }

    // カードの強さを比較する（引数のカードよりも自身のカードが強ければtrue）
    public boolean compareCard(Card otherCard) {
        if ( this.number < otherCard.number ) {
            return true;
        } else if ( this.number > otherCard.number ) {
            return false;
        } else if ( this.mark < otherCard.mark ) {	// 数字が同じ場合マークで比較する
            return true;
        } else {
            return false;
        }
    }

}
