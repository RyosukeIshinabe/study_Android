import 'package:flutter/material.dart';

// expandedのテストです。
// expandedは、画面の端からは暇で全体に表示させるためのウィジェットです。
// ColumnまたはRowの中に埋め込んで使用します。

class ExpandedTest extends StatelessWidget {

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

            // 埋め込みたいexpandedウィジェットを指定する

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

            Expanded(
              child: Container(
                color: Color.fromARGB(255, 255, 125, 0),
                child: Text(
                  'Secound Item',
                  style: TextStyle(
                      fontSize: 32.0,
                      fontWeight: FontWeight.w400),
                ),
              ),
            ),

            Expanded(
              child: Container(
                color: Color.fromARGB(255, 255, 0, 0),
                child: Text(
                  'Third Item',
                  style: TextStyle(
                      fontSize: 32.0,
                      fontWeight: FontWeight.w400),
                ),
              ),
            ),

          ]

      ),

    );
  }
}