package com.gudaletsu.mysize

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.preference.PreferenceScreen
import android.view.View
import android.view.ViewParent
import android.widget.AdapterView
import android.widget.RadioButton
import android.widget.SeekBar
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_height.*

class HeightActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_height)

        /*
            スピナー用
         */

        // AdapterView.OnItemSelectedListenerインターフェースを継承
        // このインターフェースを継承したクラスはonItemSelectedとonNothingSelectedをoverrideしなければならない
        // ここではオブジェクト式により無名インナークラスを使って直接値を渡している
        spinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {

                    // 項目が選択されずにビューが閉じられた時の処理（何もしない）
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }

                    // スピナーで項目が選択された時の処理
                    // onItemSelected(選択が発生した親ビュー, 選択されたビュー, 選択されたビューの位置, 選択された項目のID）
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                        // parentをSpinner型にキャスト。parentはJavaの変数なので安全キャスト演算子[as?]を使用する
                        val spinner = parent as? Spinner

                        // スピナーで選択した項目を変数に取得。変数spinnerはnullの可能性があるので安全呼び出し演算子[?]を使う
                        val item = spinner?.selectedItem as? String

                        // スピナーで選択した値を身長に設定
                        // スコープ関数letを使用。?.と一緒に使うことでletのラムダ式内では自分自身（この場合はitem）がnullでないことが保証される
                        item?.let {
                            if ( it.isNotEmpty() ) height.text = it
                        }
                    }
                }

        /*
            シークバー用
         */

        // 共有プリファレンス作成してapplyを実行（簡略記述）
        PreferenceManager.getDefaultSharedPreferences(this).apply {

            // 共有プリファレンスからキー："HEIGHT"の値（つまり身長）を取得（第二引数は値がなかった場合のデフォルト値）
            val heightVal = getInt("HEIGHT", 160)

            // 身長のテキストビュー「height」に表示（intをStringに変換してsetText）
            height.setText(heightVal.toString())

            // シークバーのビュー「seekBar」に代入
            // kotlinではprogressプロパティでアクセスできる（JavaではsetProgressメソッドを使う必要がある）
            seekBar.progress = heightVal
        }

        // シークバーのビュー「seekBar」を触った（値が変わった）時のメソッドを記述
        // SeekBar.OnSeekBarChangeListenerインターフェースを継承
        // このインターフェースを継承したクラスは以下3つのメソッドをoverrideしなければならない
        seekBar.setOnSeekBarChangeListener(

                // 引数にはSeekBar.OnSeekBarChangeListenerインターフェースを継承したobjectを指定（オブジェト式）
                object : SeekBar.OnSeekBarChangeListener {

                    // シークバーの値を変更した時の処理
                    // onProgressChanged（操作されたシークバー: SeekBar?, 設定された値: Int, ユーザに夜変更かどうか: Boolean)
                    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                        // ユーザーによりシークバーの値が変更されたら、シークバーの値（progress）をheightのテキストビューに表示
                        // ここはCharSequence型なので、toStringで文字列に変換してからtextプロパティに代入
                        height.text = progress.toString()
                    }

                    // シークバーに触れた時の処理（何もしない）
                    override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    }

                    // シークバーを離した時の処理（何もしない）
                    override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    }
                }
        )

        /*
            ラジオボタン用
         */

        // ラジオボタングループ「radioGroup」を触った（値が変わった）時のメソッドを記述（SAM変換）
        radioGroup.setOnCheckedChangeListener {

            // 引数checkdIdに、選択されたラジオボタンのIDが渡されて、これを元にfindViewByIdを使ってラジオボタンを取得し、TextViewに表示
            // findViewById(Rクラスに定義された該当するリソースID: Int)
            // findViewByIdはジェネリックメソッド。Kotlinで実行する場合、メソッド名の後にキャスト先の型を<>で囲んで記述する
            group, checkedId -> height.text = findViewById<RadioButton>(checkedId).text
        }
    }

    // このアクティビティ（HeightActivity）が非表示になった時に呼ばれる
    override fun onPause() {
        super.onPause()

        // TextViewの値を共有プリファレンスに保存
        PreferenceManager.getDefaultSharedPreferences(this).edit()

                // 第二引数は、CharSequence型ではtoIntメソッドを直接使用できないので、いったんString型に変換してからInt型に変換している
                .putInt("HEIGHT", height.text.toString().toInt())
                .apply()
    }


}


