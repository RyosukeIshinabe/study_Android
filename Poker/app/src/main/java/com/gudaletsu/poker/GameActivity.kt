package com.gudaletsu.poker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {

    var myHand: Hand = Hand()    // 手を生成
    var myField: Field = Field()    // フィールドを生成
    var myCoin: Coin = Coin()    // コインを生成
    var rank: Int = 0 // ランク

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        /*
            コインの準備
         */

        // 共有プリファレンスのインスタンスを用意
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        // 共有プリファレンスからコインを取得して、メンバ変数にセット
        pref.apply { myCoin.quantity = getInt("COIN", 100) }

        /*
            BETの準備
         */

        // 前画面で保存しておいたインテントからBETを取り出す
        val bet = intent.getIntExtra("BET", 1)
        // betをメンバ変数にセットして、その分手元のコインから引く
        myCoin.changeFromCoinToBet(bet)

        /*
            メソッドの定義
         */

        // カードをタップした時のメソッドを定義
        imvw_card1.setOnClickListener { tapCard(0) }
        imvw_card2.setOnClickListener { tapCard(1) }
        imvw_card3.setOnClickListener { tapCard(2) }
        imvw_card4.setOnClickListener { tapCard(3) }
        imvw_card5.setOnClickListener { tapCard(4) }

        // 確定ボタンをタップした時のメソッドを定義
        btn_submit.setOnClickListener { shuffleCard() }

        // endボタンが押された時のメソッドを定義
        btn_endGame.setOnClickListener { endGameTapped() }

        // 勝利後・敗北後のボタンを押した時のメソッドを定義
        btn_continue.setOnClickListener { continueTapped() }
        btn_refunds.setOnClickListener { refunds() }
        btn_lose.setOnClickListener { lose() }

        // ゲームスタート
        gaming()
    }

    // ゲームスタート
    private fun gaming() {

        // コイン、BET、役一覧をテキストビューに表示
        txvw_myBet.text = myCoin.bet.toString()
        txvw_myCoin.text = myCoin.quantity.toString()
        txvw_rule.text = myHand.returnRuleString()

        // 未使用カードを最大5枚引いてフィールドデッキへ移動
        myField.moveCardFromUnUsedToField()

        // ラウンド数を表示
        txvw_msg.text = myField.returnRoundString()

        // カードの画像を表示
        displayCardImage()

        // STAYorCHANGEのテキストを表示
        displayStayOrChange()

    }

    // endボタンが押された時のメソッド
    private fun endGameTapped() {

        // ゲーム終了した後の処理
        rank = 0   // ランクをリセット
        myField.init()    // 全てのデッキとラウンド数をリセット
        myCoin.bet = 0   // betをリセット

        // コインをセーブして
        saveData()
        // メインアクティビティに移動
        finish()
    }

    // コインをセーブするメソッド
    private fun saveData() {
        // 共有プリファレンスに所持コインを追加（まとめて書いてみた）
        PreferenceManager.getDefaultSharedPreferences(this)
            .edit() // 編集するおまじない
            .putInt("COIN", myCoin.quantity)    // データを書き込み
            .apply()    // 実行するおまじない
    }

    // このアクティビティが非表示になった時に呼ばれるメソッド
    override fun onPause() {
        super.onPause()
        PreferenceManager.getDefaultSharedPreferences(this)
                .edit()
                .putInt("COIN", myCoin.quantity)
                .apply()
    }

    // カードを表示するメソッド
    private fun displayCardImage() {

        // 1枚目のカードを表現するStringを取得（例：card_spade_01）
        val image1 = myField.getStringOfCardFromOnFieldDeck(0)
        // 取得したStringから、drawableフォルダ内のリソースID（int型）を特定して格納
        val rsid1 = resources.getIdentifier(image1, "drawable", packageName)
        // リソースIDを使用してイメージビューの画像を変更
        imvw_card1.setImageResource(rsid1)

        // 以下、2〜5枚目のカードも同じことを行う
        val image2 = myField.getStringOfCardFromOnFieldDeck(1)
        val rsid2 = resources.getIdentifier(image2, "drawable", packageName)
        imvw_card2.setImageResource(rsid2)
        val image3 = myField.getStringOfCardFromOnFieldDeck(2)
        val rsid3 = resources.getIdentifier(image3, "drawable", packageName)
        imvw_card3.setImageResource(rsid3)
        val image4 = myField.getStringOfCardFromOnFieldDeck(3)
        val rsid4 = resources.getIdentifier(image4, "drawable", packageName)
        imvw_card4.setImageResource(rsid4)
        val image5 = myField.getStringOfCardFromOnFieldDeck(4)
        val rsid5 = resources.getIdentifier(image5, "drawable", packageName)
        imvw_card5.setImageResource(rsid5)

    }

    // STAYorCHANGEを表示するメソッド
    private fun displayStayOrChange() {
        txvw_sorc1.text = myField.getStringStayOrChangeFromOnFieldDeck(0)
        txvw_sorc2.text = myField.getStringStayOrChangeFromOnFieldDeck(1)
        txvw_sorc3.text = myField.getStringStayOrChangeFromOnFieldDeck(2)
        txvw_sorc4.text = myField.getStringStayOrChangeFromOnFieldDeck(3)
        txvw_sorc5.text = myField.getStringStayOrChangeFromOnFieldDeck(4)
    }

    // カードをタップした時のメソッド（引数でどのカードをタップしたか受け取る）
    private fun tapCard(num: Int) {
        // タップしたカードのシャッフル値を変更
        myField.changeShuffleOfFieldDeck(num)
        // STAYorCHANGEのテキストビューを更新
        displayStayOrChange()
    }

    // カードをシャッフルした時のメソッド
    private fun shuffleCard() {
        // シャッフル値=trueのカードを使用済みに移し、元のデッキから消す
        myField.moveShuffleCardFromFieldToUsedDeck()
        // 足りない分のカードを未使用デッキから補充する
        myField.moveCardFromUnUsedToField()
        // カードを表示する
        displayCardImage()
        // STAYorCHANGEのテキストビューを更新
        displayStayOrChange()

        // もしroundが2よりも大きかった場合
        if ( myField.round >= 2 ) {
            // 評価メソッドへ移動
            judge()
        // それ以外の場合
        } else {
            // ラウンド数をインクリメント
            myField.incrementRound()
            // ラウンド表示のテキストビューを更新
            txvw_msg.text = myField.returnRoundString()
        }
    }

    // 評価メソッド
    private fun judge() {
        // 役のランクを取得
        rank = myHand.checkAllHand(myField.sameMarkOfCardFromOnFieldDeck, myField.sameNumOfCardFromOnFieldDeck)

        // ランクをテキストビューに表示
        txvw_msg.text = rank.toString() + "倍でした！\nBETを" + rank.toString() + "倍にしといたよ"
        // BET×RANKを実行（役がない場合=つまり負けた場合はここでBETが0になる）
        myCoin.multiplicate(rank)
        // BETのテキストビューを更新
        txvw_myBet.text = myCoin.bet.toString()

        // 全てのカードを使用済みへ移動
        myField.moveCardFromFieldToUsedDeck()

        // 勝ちだった場合
        if ( myCoin.bet > 0 ) {
            // 選択肢ボタンを表示する
            btn_continue.visibility = View.VISIBLE
            btn_refunds.visibility = View.VISIBLE

        // 負けだった場合
        } else {
            // 選択肢ボタンを表示する
            btn_lose.visibility = View.VISIBLE
        }
    }

    // 勝利した後に続けるメソッド
    private fun continueTapped() {
        // 選択肢ボタンを非表示にする
        btn_continue.visibility = View.INVISIBLE
        btn_refunds.visibility = View.INVISIBLE

        // ランク、デッキ、ラウンド数をリセット
        rank = 0
        myField.init()
        // ゲームスタート
        gaming()
    }

    // 勝利した後に利益確定するメソッド
    private fun refunds() {
        // 選択肢ボタンを非表示にする
        btn_continue.visibility = View.INVISIBLE
        btn_refunds.visibility = View.INVISIBLE

        // BETをコインに換金
        myCoin.changeFromBetToCoin()
        // 終了処理
        endGameTapped()
    }

    // 負けた時のメソッド
    private fun lose() {
        // 選択肢ボタンを非表示にする
        btn_lose.visibility = View.INVISIBLE
        // 終了処理
        endGameTapped()
    }

}
