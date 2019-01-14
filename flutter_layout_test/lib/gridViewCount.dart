import 'package:flutter/material.dart';

// gridview.countのテストです。
// gridView.countは、複数のウィジェットを列数を指定してタイル状に並べるものです。
// gridView.countは列数を指定して表示するものであり、ウィジェットのサイズを指定して配置する場合はgridView.extentを使います。

class GridViewCountTest extends StatelessWidget {

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

  // 組み込みたいタイルをその数だけ用意する

  // シンプルな、色背景に文字が書かれただけのタイルを作成
  var _textGrid = <Widget>[
    // タイルの数だけコンテナを増やす
    Container(
        color: Colors.red,
        child: Text("RED", style: TextStyle(fontSize: 32.0))
    ),
    Container(
        color: Colors.green,
        child: Text("GREEN", style: TextStyle(fontSize: 32.0))
    ),
    Container(
        color: Colors.blue,
        child: Text("BLUE", style: TextStyle(fontSize: 32.0))
    ),
    Container(
        color: Colors.yellow,
        child: Text("YELLOW", style: TextStyle(fontSize: 32.0))
    ),
    Container(
        color: Colors.purple,
        child: Text("PURPLE", style: TextStyle(fontSize: 32.0))
    ),
  ];

  // web上の画像もタイル常に表示できるか調査
  var _imageGrid = <Widget>[
    Container(
      color: Colors.red,
      child:
      Image.network(
        'https://www.necoichi.co.jp/files/topics/6239_ext_01_0.jpg',
        fit:BoxFit.fill,
      ),
      padding: const EdgeInsets.all(0.0),
      alignment: Alignment.center,
    ),
    Container(
      color: Colors.green,
      child:
      Image.network(
        'http://www.asakusanekoen.com/images/mugi.jpeg',
        fit:BoxFit.fill,
      ),
      padding: const EdgeInsets.all(0.0),
      alignment: Alignment.center,
    ),
  ];


  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: new AppBar(
        title: new Text('App Name'),
      ),
      body: new GridView.count(
        crossAxisCount: 3,  // 列数
        mainAxisSpacing: 10.0,  // 行間
        crossAxisSpacing: 10.0,  // 列間
        padding: const EdgeInsets.all(10.0), // 全体の周りの余白
        children: _imageGrid,  // 組み込むウィジェットを指定
      ),

    );
  }
}