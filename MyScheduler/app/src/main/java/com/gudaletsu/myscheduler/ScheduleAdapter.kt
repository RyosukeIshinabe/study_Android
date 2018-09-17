package com.gudaletsu.myscheduler

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.realm.OrderedRealmCollection
import io.realm.RealmBaseAdapter

// リストビューのセル自体とセルに表示するデータの制御を行う「アダプター」を用意する
// 通常はListAdapterインターフェースを実装したクラスとして用意するが、
// 今回はデータベースから取得した結果をリストビューに表示するため、
// データベース用の専用アダプター「RealmBaseAdapter」を使用することにする

class ScheduleAdapter(data: OrderedRealmCollection<Schedule>?) : RealmBaseAdapter<Schedule>(data) {

    // インナークラスとしてViewHolderを用意する（後ほどgetView内部で使用）
    // 「android.R.layout.simple_list_item_2」が適用されたviewを受け取り、
    // その内部に配置されているtext1、text2のIDを持つテキストビューの内容をメンバ変数として保持する
    inner class ViewHolder(cell: View) {
        val date = cell.findViewById<TextView>(android.R.id.text1)
        val title = cell.findViewById<TextView>(android.R.id.text2)
    }

    // テーブルのセルを作成し、値をセットする処理
    // リストビューのセルのデータが必要になる度に呼び出され、表示するビューを戻り値として返す
    // 引数1：リストビューのセルの位置を受け取る
    // 引数2：すでに作成済みのセルを表すビューを受け取る。初めて作成する場合はnullがはいる
    // 引数3：親のリストビューを受け取る
    // 第二引数は、指定のセルが初めて呼び出される場合にはnullが入り、スクロールした場合には、見えなくなったセルが入る
    // なぜそんなことするかは、メモリを節約するため。セルをたくさん作るとアプリが重くなる
    // そのため、中のメソッドでここにnullが入っているかどうかで処理を分ける
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder

        // 第二引数のconvertViewの中身によって処理を変える
        when (convertView) {

            // nullの場合。つまり初めて表示する場合
            null -> {

                // LayoutInflaterクラスは、XMLファイルからビューを作成する機能を提供する
                // ここではまず、LayoutInflaterクラスのメソッドであるfromを使ってインスタンスを作成
                // 引数には、getViewの第三引数として渡される親クラスのリストビュー渡す
                val inflater = LayoutInflater.from(parent?.context)

                // 実際にXMLファイルから画面レイアウトを作成するのがinflateメソッド
                // 引数1：ビューを作成したいレイアウトXMLのリソースID
                // 　今回使用しているandroid.R.layout.simple_list_item_2はAndroid SDKにもともと用意されているXMLファイル
                // 引数2：第三引数がfalseの場合、作成するビューをアタッチするビューを指定する
                // 　今回はgetViewの第三引数として渡される親クラスのリストビュー渡す
                // 引数3：XMLファイルから作成したビューを返したい場合はfalseを指定する
                view = inflater.inflate(android.R.layout.simple_list_item_2, parent, false)

                // 21行目で作成したViewHolderクラスに、「android.R.layout.simple_list_item_2」が適用された「view」を渡して
                // インスタンスを作成する
                viewHolder = ViewHolder(view)

                // セル用ビューのタグの中に保持
                // java：setTag(tag) 　kotlin：.tag = tag（プロパティが利用できる）
                // tag：ビューにつけるタグ
                view.tag = viewHolder
                // こうすることで、contentViewを使い回す時は、contentViewからfindViewByIdでビューを取り出すのではなく、
                // タグとしてつけておいたViewHolderからtagプロパティを取り出すことで効率的に処理が行える（後述のelseを参照）
            }

            // nullじゃない場合。つまりスクロールなどで見えなくなったセルを使い回す場合
            else -> {

                // 第二引数で受け取った、すでに作成済みのビューを受け取る
                view = convertView

                // 新規作成した時に設定したtagプロパティを取り出し、viewHolderインスタンスを取り出す
                viewHolder = view.tag as ViewHolder
            }
        }

        // RealmBaseAdapterクラスのadapterDateプロパティにはデータのリストが入っている
        // 安全呼び出し演算子 ?. でnullではない場合のみrun関数を実行
        adapterData?.run {

            // getViewメソッドの第一引数のpositionで、何番目のデータを取得するのか指定する
            val schedule = get(position)

            // 取り出したデータの日付列をデータフォーマッターで書式を指定し、viewHolderのdateのテキストビューにセット
            viewHolder.date.text = DateFormat.format("yyyy/MM/dd", schedule.date)
            // 取り出したデータのタイトル列を、viewHolderのdateのテキストビューにセット
            viewHolder.title.text = schedule.title
        }

        return view

    }
}