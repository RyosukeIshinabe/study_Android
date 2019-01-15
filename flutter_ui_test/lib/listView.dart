import 'package:flutter/material.dart';

// listViewのテストです。
// listViewはリストを表示するウィジェットです。

class ListViewTest extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: 'Generated App',
      theme: ThemeData(
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
  int _index;

  void initState() {
    super.initState();
    _message = 'ready.';
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('App Name'),
      ),
      body: Column(
        mainAxisAlignment: MainAxisAlignment.start,
        mainAxisSize: MainAxisSize.max,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: <Widget>[

          Text(
            _message,
            style: TextStyle(fontSize:32.0),
          ),

          // シンプルな文字表示のためだけのリストビュー
          ListView(
            shrinkWrap: true, // 追加される項目に応じて大きさを自動調整するかどうか
            padding: const EdgeInsets.all(20.0),
            children: <Widget>[
              Text(
                'First item',
                style: TextStyle(fontSize: 24.0)
              ),
              Text(
                'Second item',
                style: TextStyle(fontSize: 24.0)
              ),
              Text(
                'Third item',
                style: TextStyle(fontSize: 24.0)
              ),
            ],
          ),

          Padding(
            padding: EdgeInsets.all(25.0),
          ),

          // タップ操作を追加するならば、ListView専用のウィジェットListTileを使用する
          ListView(
            shrinkWrap: true,
            padding: const EdgeInsets.all(20.0),
            children: <Widget>[
              ListTile(
                // ListTile左側に表示されるアイコン
                leading: const Icon(Icons.android),

                // ListTileに表示される文字列
                title: const Text('first item'),

                // selectedがtrueとなっている項目が選択状態（色付き）になる
                // ここでは、_indexに入っている値と[1]を比べている
                selected: _index == 1,

                // タップされた時の処理
                // ここでは、_indexに1を代入したあとtapTile()メソッドを呼び出している
                onTap: () {
                  _index = 1;
                  tapTile();
                },
              ),

              ListTile(
                leading: const Icon(Icons.favorite),
                title: const Text('second item'),
                selected: _index == 2,
                onTap: () {
                  _index = 2;
                  tapTile();
                },
              ),
              ListTile(
                leading: const Icon(Icons.favorite_border),
                title: const Text('third item'),
                selected: _index == 3,
                onTap: () {
                  _index = 3;
                  tapTile();
                },
              ),
            ],
          ),
        ]
      ),
    );
  }

  void tapTile() {
    setState(() {
      _message = 'you tapped: No, $_index.';
    });
  }
}