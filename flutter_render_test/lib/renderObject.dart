import 'package:flutter/material.dart';
import 'package:flutter/gestures.dart';

// RenderObjectWidgetとRenderBoxのテストです。
// RenderObjectWidgetは描画するオブジェクトを表示するためのウィジェット、
// RenderBoxは実際に描画を担当するオブジェクトです。
// 本サンプルでは、2つの四角形を重なり合わせて表示させてみます。

class RenderObjectTest extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);
  final String title;
  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  @override
  Widget build(BuildContext context) {

    return Scaffold(
      backgroundColor: Color.fromARGB(255, 255, 255, 255),
      appBar: AppBar(
        title: Text('App Name'),
      ),
      body: Center(
        child: MyRenderBoxWidget(),
      ),
    );
  }
}

// RenderObjectWidgetは、implementされたクラスを使用する
// ここでは、1つのオブジェクトを内部に持つRenderObjectWidgetであるSingleChildRenderObjectWidgetを使用する
// 抽象クラスであるため、createRenderObjectメソッドが必ず必要
class MyRenderBoxWidget extends SingleChildRenderObjectWidget {

  // createRenderObjectメソッドで返されたオブジェクトが表示される
  RenderObject createRenderObject(BuildContext context) {
    return _MyRenderBox();
  }
}

// 実際に描画するのがRenderBoxクラス。ここでも、RenderBoxを継承したクラスを用意して使用する

class _MyRenderBox extends RenderBox {

  // ヒットされた位置にあるレンダリングオブジェクトを返すもので、
  // タップした位置にレンダリングされるオブジェクトがあることを確認するためのもの
  // これが失敗すると例外を発生するので、今回はオーバーライドして回避している
  @override
  bool hitTest(HitTestResult result, { @required Offset position }) {
    return true;
  }

  // RenderBoxの中では、paintメソッドをオーバーライドする
  // paintメソッドでは、PaintingContextとOffsetというクラスのインスタンスが渡される
  // PaintingContextは描画用のコンテキストで、描画に関するオブジェクトがここに含まれる
  // Offsetは、このRenderBoxを組み込んだRenderObjectWidgetが、画面のどのあたりに表示されているかを示す
  @override
  void paint(PaintingContext context, Offset offset) {

    // canvasは、描画のメソッドなどがまとめられているクラス。これは引数のcontextから取り出す
    Canvas c = context.canvas;

    // dxとdyは、描画のオフセット値。描画するエリアがどこ位置から始まっているかを示す。
    int dx = offset.dx.toInt();
    int dy = offset.dy.toInt();

    // ここからが実際の描画
    // まずはペイントクラスのインスタンスを作成
    Paint p = Paint();

    // まず1つ目の四角形
    // PaintingStyle.fill：図形内部を塗りつぶす
    p.style = PaintingStyle.fill;
    // Paintのcolorプロパティで色を指定する
    p.color = Color.fromARGB(150, 0, 200, 255);
    // 描く図形の領域は、Rectクラスを使用する。fromLTWHで位置と大きさを指定してRectを作成する
    // fromLTWH(横位置, 縦位置, 幅, 高さ);
    Rect r = Rect.fromLTWH(dx + 50.0, dy + 50.0, 150.0, 150.0);
    // 最後に、キャンバスクラスのdrawRectメソッドで描画する。第一引数にはRect、第二引数にはPantを指定
    c.drawRect(r, p);

    // 2つ目の四角形
    // PaintingStyle.stroke：輪郭線のみを描く
    p.style = PaintingStyle.stroke;
    // Paintのcolorプロパティで色を指定する
    p.color = Color.fromARGB(150, 200, 0, 255);
    // PaintのstrokeWidthプロパティで線分の太さを指定する
    p.strokeWidth = 10.0;
    // 描く図形の領域は、Rectクラスを使用する。fromLTWHで位置と大きさを指定してRectを作成する
    // fromLTWH(横位置, 縦位置, 幅, 高さ);
    r = Rect.fromLTWH(dx + 100.0, dy + 100.0, 150.0, 150.0);
    // 最後に、キャンバスクラスのdrawRectメソッドで描画する。第一引数にはRect、第二引数にはPantを指定
    c.drawRect(r, p);
  }
}