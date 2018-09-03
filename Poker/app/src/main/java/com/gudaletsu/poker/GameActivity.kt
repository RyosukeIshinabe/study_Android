package com.gudaletsu.poker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class GameActivity : AppCompatActivity() {

    var myHand: Hand = Hand()    // 手を生成
    var myField: Field = Field()    // フィールドを生成
    var myCoin: Coin = Coin()    // コインを生成
    var rank: Int = 0 // ランク

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // 前のアクティビティからBETを取り出す
        val bet = intent.getIntExtra("BET", 1)

        // ゲームを起動
        gaming()

        // コインを共有プリファレンスに保存
    }

    // ポーカーゲームを行う処理
    fun gaming() {

        // 途中breakするまで繰り返す
        while (true) {

            myField.moveCardFromUnUsedToField()    // 未使用カードを最大5枚引いてフィールドデッキへ移動
            myCoin.inputBet()  // コインをBET

            do {
                myField.displayRoundAndIncrement() // ラウンドを表示してインクリメント

                // ここにフィールドカードとシャッフル値を取得するのをかく

                // ラウンド数がシャッフル最大回数以下の場合繰り返す
            } while (myField.getRound() <= myField.getSHAFFLE())

            // フィールド上のデッキを精査してランクを取得
            rank = myHand.checkAllHand(myField.getSameMarkOfCardFromOnFieldDeck(), myField.getSameNumOfCardFromOnFieldDeck())
            myCoin.multiplicate(rank)  // BETを倍がけ（役がない場合=つまり負けた場合はここでBETが0になる）

            // BETが0になった場合は続けるか聞く前にゲームを終了
            if (myCoin.getBet() <= 0) {
                break
            }

            // 続けるか聞いて、続けないを選択した場合は、BETを所持コインに変換してゲーム終了
            if (!myField.selectContinue()) {
                myCoin.changeFromBetToCoin()
                break

                // BET維持のまま続ける場合
            } else {
                rank = 0   // ランクをリセット
                myField.init()    // 全てのデッキとラウンド数をリセット
            }
        }

        // ゲーム終了した後の処理
        rank = 0   // ランクをリセット
        myField.init()    // 全てのデッキとラウンド数をリセット
        myCoin.setBet(0)   // betをリセット
    }
}
