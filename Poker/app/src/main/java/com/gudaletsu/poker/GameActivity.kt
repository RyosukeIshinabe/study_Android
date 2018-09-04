package com.gudaletsu.poker

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_game.*
import org.jetbrains.anko.startActivity

class GameActivity : AppCompatActivity() {

    var myHand: Hand = Hand()    // 手を生成
    var myField: Field = Field()    // フィールドを生成
    var myCoin: Coin = Coin()    // コインを生成
    var rank: Int = 0 // ランク

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        /*
            情報の表示
         */

        // BETをインテントから取り出す
        val bet = intent.getIntExtra("BET", 1)

        // コインを共有プリファレンスから取り出す
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        // 取り出したコインをセット
        pref.apply { myCoin.quantity = getInt("COIN", 100) }

        // betをセットして、その分手元のコインから引く
        myCoin.changeFromCoinToBet(bet)

        // テキストビューに表示
        txvw_myBet.setText(bet.toString())
        txvw_myCoin.setText(myCoin.quantity.toString())
        txvw_rule.setText(myHand.returnRuleString())

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

        /*
            ゲームスタート
         */

        // 未使用カードを最大5枚引いてフィールドデッキへ移動
        myField.moveCardFromUnUsedToField()

        // ラウンドを表示
        txvw_msg.text = myField.returnRoundString()

        // カードを表示
        displayCardImage()

        // STAYorCHANGEを表示
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
        startActivity<MainActivity>()
    }

    @SuppressLint("CommitPrefEdits")
    // コインをセーブするメソッド
    fun saveData() {
        PreferenceManager.getDefaultSharedPreferences(this)
        .edit()
        .putInt("COIN", myCoin.quantity)
        .apply()
    }

    // このアクティビティが非表示になった時に呼ばれるメソッド
    override fun onPause() {
        super.onPause()
        // コインを共有プリファレンスに保存
        PreferenceManager.getDefaultSharedPreferences(this)
                .edit()
                .putInt("COIN", myCoin.quantity)
                .apply()
    }

    // カードを表示するメソッド
    private fun displayCardImage() {

        // 1枚目カードを表現するStringを取得
        val image1 = myField.getStringOfCardFromOnFieldDeck(1)
        // 取得したStringから、drawableフォルダ内のリソースID（int型）を特定して格納
        val rsid1 = resources.getIdentifier(image1, "drawable", packageName)
        // リソースIDを使用してイメージビューの画像を変更
        imvw_card1.setImageResource(rsid1)

        // 以下、2〜5枚目のカードも同じことを行う
        val image2 = myField.getStringOfCardFromOnFieldDeck(2)
        val rsid2 = resources.getIdentifier(image2, "drawable", packageName)
        imvw_card2.setImageResource(rsid2)
        val image3 = myField.getStringOfCardFromOnFieldDeck(3)
        val rsid3 = resources.getIdentifier(image3, "drawable", packageName)
        imvw_card3.setImageResource(rsid3)
        val image4 = myField.getStringOfCardFromOnFieldDeck(4)
        val rsid4 = resources.getIdentifier(image4, "drawable", packageName)
        imvw_card4.setImageResource(rsid4)
        val image5 = myField.getStringOfCardFromOnFieldDeck(5)
        val rsid5 = resources.getIdentifier(image5, "drawable", packageName)
        imvw_card5.setImageResource(rsid5)

    }

    // STAYorCHANGEを表示するメソッド
    private fun displayStayOrChange() {
        txvw_sorc1.setText(myField.getStringStayOrChangeFromOnFieldDeck(1))
        txvw_sorc1.setText(myField.getStringStayOrChangeFromOnFieldDeck(2))
        txvw_sorc1.setText(myField.getStringStayOrChangeFromOnFieldDeck(3))
        txvw_sorc1.setText(myField.getStringStayOrChangeFromOnFieldDeck(4))
        txvw_sorc1.setText(myField.getStringStayOrChangeFromOnFieldDeck(5))
    }

    // カードをタップした時のメソッド
    fun tapCard(num: Int) {
        myField.changeShuffleOfFieldDeck(num)   // シャッフル値を変更
        displayStayOrChange()   //  STAYorCHANGEを更新
    }

    // カードをシャッフルした時のメソッド
    fun shuffleCard() {
        // シャッフル値=trueのカードを使用済みに移し、元のデッキから消す
        myField.moveShuffleCardFromFieldToUsedDeck()
        // 足りない分のカードを未使用デッキから補充する
        myField.moveCardFromUnUsedToField()
        // カードを表示する
        displayCardImage()
        // STAYorCHANGEを更新
        displayStayOrChange()
        // ラウンド数をインクリメント
        myField.incrementRound()

        // もしroundが2よりも大きかった場合
        if ( myField.getRound() > 2 ) {
            // 評価メソッドへ移動
            judge()
        }

        // ラウンドを更新
        txvw_msg.text = myField.returnRoundString()
    }

    // 評価メソッド
    fun judge() {
        rank = myHand.checkAllHand(myField.sameMarkOfCardFromOnFieldDeck, myField.sameNumOfCardFromOnFieldDeck)
        myCoin.multiplicate(rank)  // BETを倍がけ（役がない場合=つまり負けた場合はここでBETが0になる）

        // ランクを表示
        txvw_msg.text = rank.toString() + "ポイントでした"

        // 全てのカードを使用済みへ移動
        myField.moveCardFromFieldToUsedDeck()
    }

}
