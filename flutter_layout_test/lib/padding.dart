import 'package:flutter/material.dart';

// paddingのテストです。
// paddingは、ウィジェットの周りに余白を持たせるためのウィジェットです。
// ウィジェット内に組み込んで使うこともあれば、単体でスペース調整に使うこともあります。

class PaddingTest extends StatelessWidget {

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
  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: new AppBar(
        title: new Text('App Name'),
      ),
      body:
      new Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: <Widget>[

            // テストとしてexpandedウィジェットを使用する
            // expandedウィジェットの説明はexpanded.dartにて。

            // paddingなしのexpandedウィジェット
            Expanded(
              child: Container(
                color: Color.fromARGB(255, 255, 255, 0),
                child: Text(
                  'First Item',
                  style: TextStyle(
                      fontSize: 32.0,
                      fontWeight: FontWeight.w400),
                ),
              ),
            ),

            // Paddingウィジェット単体で使用し、スペースを空ける
            Padding(
              padding: EdgeInsets.all(25.0),
            ),

            // paddingなしのexpandedウィジェット
            Expanded(
              child: Container(
                color: Color.fromARGB(255, 255, 125, 0),
                child: Text(
                  'Second Item',
                  style: TextStyle(
                      fontSize: 32.0,
                      fontWeight: FontWeight.w400),
                ),
              ),
            ),

            // Expanded内でpaddingプロパティを使用し、周りに余白をつける
            Expanded(
              child: Padding(
                padding: EdgeInsets.all(25.0),
                child: Container(
                    color: Color.fromARGB(255, 255, 0, 0),
                    child: Text(
                      'Third Item',
                      style: TextStyle(
                          fontSize: 32.0,
                          fontWeight: FontWeight.w400),
                    )
                ),
              ),
            ),

          ]

      ),

    );
  }
}