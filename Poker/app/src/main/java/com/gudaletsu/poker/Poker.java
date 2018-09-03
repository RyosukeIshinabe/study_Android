package com.gudaletsu.poker;

public class Poker {

    public static Hand myHand = new Hand();	// 手を生成
    public static Field myField;	// フィールドを生成
    public static Coin myCoin;	// コインを生成
    public static int rank; // ランク

    public void gameStart() {

        // 途中breakするまで繰り返す
        while ( true ) {

            myField = new Field();	// カードを初期化
            myField.moveCardFromUnUsedToField();    // 未使用カードを最大5枚引いてフィールドデッキへ移動
            myCoin.inputBet();  // コインをBET

            do {
                myField.displayRoundAndIncrement(); // ラウンドを表示してインクリメント

                // ここにフィールドカードとシャッフル値を取得するのをかく

                // ラウンド数がシャッフル最大回数以下の場合繰り返す
            } while ( myField.getRound() <= myField.getSHAFFLE() );

            // フィールド上のデッキを精査してランクを取得
            rank = myHand.checkAllHand(myField.getSameMarkOfCardFromOnFieldDeck(),myField.getSameNumOfCardFromOnFieldDeck());
            myCoin.multiplicate(rank);  // BETを倍がけ（役がない場合=つまり負けた場合はここでBETが0になる）

            // BETが0になった場合は続けるか聞く前にゲームを終了
            if ( myCoin.getBet() <= 0 ) {
                break;
            }

            // 続けるか聞いて、続けないを選択した場合は、BETを所持コインに変換してゲーム終了
            if ( !myField.selectContinue() ) {
                myCoin.changeFromBetToCoin();
                break;

            // BET維持のまま続ける場合
            } else {
                rank = 0;   // ランクをリセット
                myField.init();    // 全てのデッキとラウンド数をリセット
            }
        }

        // ゲーム終了した後の処理
        rank = 0;   // ランクをリセット
        myField.init();    // 全てのデッキとラウンド数をリセット
        myCoin.setBet(0);   // betをリセット
    }
}
