package com.gudaletsu.rssreader

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // リストビューに表示するサンプルデータを用意
        val items = Array(20, { i -> "記事$i" })

        // アダプタークラスのインスタンスを作成し、データをセット
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items)

        // リストビューにアダプターをセット
        myListView.adapter = adapter

        // リストがクリックされた時の処理
        myListView.setOnItemClickListener { _, view, _, _ ->
            val textView = view.findViewById<TextView>(android.R.id.text1)
            Toast.makeText(this, "Clicked: ${textView.text}", Toast.LENGTH_SHORT).show()
        }
    }
}
