import 'package:flutter/material.dart';

// ナビゲーション（複数画面の切り替え）のテストです。
// ナビゲーションは、Navigatorクラスを使用します。 Navigatorクラスにはpushとpopが用意されています。
// push：現在の表示をプッシュ（一時保管）し、起動先に表示を切り返える
// pop：最後に保管した移動元をポップ（取り出す）し、表示を戻す
// 本サンプルでは、画面間の値の受け渡し方法も併せてテストします。

class NavigationTest extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: new FirstScreen(),
    );
  }
}

// 1枚目の画面
class FirstScreen extends StatefulWidget {
  // コンストラクタ
  FirstScreen({Key key}) : super(key: key);
  @override
  _FirstScreenState createState() => new _FirstScreenState();
}

class _FirstScreenState extends State<FirstScreen> {

  // TextField（入力フォーム）のためのコントローラ。値変更などを感知する
  final _controller = TextEditingController();
  // 入力された文字を代入するための変数
  String _input;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Home'),
      ),

      body: Column(
        children: <Widget>[
          Text('Home Screen'),
          Padding(
            padding: EdgeInsets.all(10.0),
          ),

          // 入力フォーム。controllerでコントローラを指定、onChangedで値が変更された時に呼ぶメソッドを指定
          TextField(
            controller: _controller,
            onChanged: changeField,
          ),
        ],
      ),

      // 画面下部のナビゲーションバー
      bottomNavigationBar: BottomNavigationBar(
        // 現在選択されている（青く表示する）アイコンのインデックス番号
        currentIndex: 1,
        // ナビゲーションバーに表示するアイコンはBottomNavigationBarItemで指定する
        items: <BottomNavigationBarItem>[
          BottomNavigationBarItem(
            title: Text('Home'),
            icon: Icon(Icons.home),
          ),
          BottomNavigationBarItem(
            title: Text('next'),
            icon: Icon(Icons.navigate_next),
          ),
          // メニューを増やしたい場合はここに追加
        ],

        // タップした時の動作を定義。引数には、タップされたアイコンのインデックス番号が入る
        onTap: (int value) {
          if ( value == 1 ) {
            // Navigator.pushで次の画面に移動する
            // 第一引数には、contextを指定
            // 第二引数には、MaterialPageRouteクラスのインスタンス（PageRouteクラスのサブクラス）を指定
            // これにより、Navigatorに現在のPageRouteを保管している
            // また、起動先のクラスの引数に入力した文字を渡すことで、ページ間のデータのやり取りが可能になる
            Navigator.push(
              context, MaterialPageRoute(builder: (context) => SecondScreen(_input)),
            );
          }
        }
      ),
    );
  }
  // 変数「_input」に、入力された文字を代入
  void changeField(String val) => _input = val;
}

// 2枚目の画面
class SecondScreen extends StatelessWidget {
  final String _value;

  // コンストラクタ。前の画面から引数で受け取った文字列を、メンバ変数に格納している
  SecondScreen(this._value);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Next'),
      ),
      body: Center(
        child: Text(
          'you typed: $_value',
        ),
      ),

      bottomNavigationBar: BottomNavigationBar(
        currentIndex: 0,
        items: <BottomNavigationBarItem>[
          BottomNavigationBarItem(
            title: Text('prev'),
            icon: Icon(Icons.navigate_before),
          ),
          BottomNavigationBarItem(
            title: Text('?'),
            icon: Icon(Icons.android),
          ),
        ],
        onTap: (int value) {
          // Navigator.pop(context)で、Navigatorに最後に保管されたPageRouteを取り出し、戻している
          if ( value == 0 ) Navigator.pop(context);
        }
      ),
    );
  }
}
