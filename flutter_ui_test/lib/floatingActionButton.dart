import 'package:flutter/material.dart';

// FloatingActionButtonのテストです。
// FloatingActionButtonは、宙に浮いているタイプのボタンのウィジェットです。
// FloatingActionButtonはコンテナではありません。中に別のウィジェットを組み込むことはできません。

class FloatingActionButtonTest extends StatelessWidget {

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

  var _message;
  static var _janken = <String>['グー','チョキ','パー'];

  // 最初だけ呼ばれるメソッド
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
      body:
      new Center(
        child:

        new Column(
            mainAxisAlignment: MainAxisAlignment.start,
            mainAxisSize: MainAxisSize.max,
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: <Widget>[

              // paddingウィジェットを使用して、その中に表示するテキストを指定
              new Padding(
                padding: const EdgeInsets.all(20.0),
                child: Text(
                  _message,
                  style: TextStyle(fontSize:32.0,
                      fontWeight: FontWeight.w400),
                ),
              ),

              // ボタンのウィジェット。onPressedプロパティで押した時のメソッド名を指定する
              new FloatingActionButton(
                child: Icon(Icons.android),
                onPressed: buttonPressed,
              ),
            ]
        ),
      ),
    );
  }

  // ボタンを押した時のメソッド。メソッド名はなんでもいい
  void buttonPressed() {
    setState(() {
      // janken配列をシャッフルして先頭の値を取り出す
      _message = (_janken..shuffle()).first;
    });
  }
}