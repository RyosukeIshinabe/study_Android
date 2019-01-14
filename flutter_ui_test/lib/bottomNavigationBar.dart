import 'package:flutter/material.dart';

// BottomNavigationBarのテストです。
// BottomNavigationBarは、下部に表示されるナビゲーションバーです。
// BottomNavigationBarにBottomNavigationBarItemウィジェットを組み込んで使用します。
// 本サンプルでは、タップしたメニューに応じて中央のテキストが変わるようにします。

class BottomNavigationBarTest extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: 'Generated App',
      theme: new ThemeData(
        primarySwatch: Colors.blue,
        primaryColor: const Color(0xFF2196f3),
        accentColor: const Color(0xFF2196f3),
        canvasColor: const Color(0xFFfafafa),
      ),
      home: new MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key}) : super(key: key);
  @override
  _MyHomePageState createState() => new _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  String _message;
  int _index = 0;

  void initState() {
    _message = 'ready.';
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: new AppBar(
        title: new Text('App Name'),
      ),

      // ここがテキスト部分。下部メニューがタップされると変更される
      body: Center(
        child: Text(
          _message,
          style: const TextStyle(fontSize: 28.0),
        )
      ),

      // ここが下部メニュー部分。
      bottomNavigationBar: new BottomNavigationBar(
        // 現在選択されている項目のインデックス。これに設定されたインデックス（int）のアイコンが選択状態で表示される
        currentIndex: _index,

        // itemsに全てのメニューアイコンが格納される
        items: <BottomNavigationBarItem>[

          // BottomNavigationBarItemウィジェットでアイコンとタイトルを指定する
          // タップした時のメソッドはアイコンごとに個別に指定はできず、bottomNavigationBar内のonTapプロパティで一括で指定するしかない
          new BottomNavigationBarItem(
            icon: const Icon(Icons.android),
            title: new Text('Left'),
          ),
          new BottomNavigationBarItem(
            icon: const Icon(Icons.favorite),
            title: new Text('Right'),
          )
        ],

        // タップされた時に呼ばれるメソッドを指定する。全てのアイコンで呼ばれるため、動作を変えたい場合はメンバ変数を使うこと
        // 引数には（ここには何も書かれていないが）、タップされたアイコンのインデックス番号（0〜）が自動で渡される
        onTap: tapBottomIcon,
      ),
    );
  }

  // タップされた時に呼ばれるメソッド。メソッド名はなんでもよい
  void tapBottomIcon(int value) {
    var items = ['Android', 'Heart'];
    setState(() {
      _index = value;
      _message = 'you tapped: ' + items[_index];
    });
  }
}