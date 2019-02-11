import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter/rendering.dart';
import 'dart:typed_data';
import 'dart:async';
import 'dart:ui' as ui;

// 画像を表示するテストです。
// 事前にプロジェクト内に「assets」というフォルダを作成し、その中に画像を入れておいてください。
// 必要なimportにも注意してください。
// また、「pubspec.yaml」内の「flutter:」の後に、以下を埋め込んで置かないと、画像を認識しません。
/*
 *  flutter:
 *    assets:
 *      - assets/画像のファイル名.jpg
 *      - assets/画像のファイル名.jpg
 *      ……
 */
// 本サンプルでは、RenderBoxWidgetを使用して画像を描写します。
// RenderBoxWidgetやRenderBoxの使い方は、renderObject.dartを参照してください。

class LoadAssetsImageTest extends StatelessWidget {
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

// 表示を担当するのが「RenderObjectWidget」クラス（を継承した独自クラス）
class MyRenderBoxWidget extends SingleChildRenderObjectWidget {

  // createRenderObjectメソッドで返されたオブジェクトが画面に表示される
  RenderObject createRenderObject(BuildContext context) {
    return _MyRenderBox();
  }
}

// 実際に描画するのがRenderBoxクラス（を継承した独自クラス）
class _MyRenderBox extends RenderBox {
  ui.Image _img;

  // タップした位置にレンダリングされるオブジェクトがあることを確認するためのもの
  // これが失敗すると例外を発生するので、今回はオーバーライドして回避している
  @override
  bool hitTest(HitTestResult result, { @required Offset position }) {
    return true;
  }

  // コンストラクタ
  _MyRenderBox() {
    loadAssetImage('sample_image.jpg');
  }

  // 画像をロードするメソッド
  // 引数で画像のファイル名を受け取り、rootBundle.loadで読み込み、then（処理が完了したなら）を実行
  // rootBundleは、トップレベル（どこかでも利用可能な）プロパティで、AssetBundleクラスのインスタンスが代入されている
  // その中のloadメソッドは、引数に指定したリソースを読み込んでオブジェクトを返す。戻り値はFuture<ByteData>
  // Futureとは、非同期処理で使われる。非同期なので直後に返されないが、実行が完了された時に戻り値を受け取るので、
  // "将来返される戻り値"を受け取るために使われる
  // ただしここでは戻り値を受け取るのではなく、完了次第thenで続けて続けて処理をしている
  loadAssetImage(String fname) => rootBundle.load("assets/$fname").then( (bd) {

    // Unit8Listは、イメージのコーデックに関するクラス
    // thenで受け取ったByteDataのbufferプロパティ（ByteBufferインスタンス）を、Unit8Listインスタンスに格納
    Uint8List u8lst = Uint8List.view(bd.buffer);

    // instantiateImageCodeは、データを元にイメージのオブジェクトを復元する。戻り値はFuture<Codec>
    ui.instantiateImageCodec(u8lst).then( (codec) {

      // getNextFrameは、次のアニメーションフレームを取得する。戻り値はFuture<FrameInfo>
      codec.getNextFrame().then( (frameInfo) {

        // FrameInfoからimageプロパティを取り出せば、ui.Imageクラスのインスタンスが取り出せる
        _img = frameInfo.image;
        // markNeedsPaint()は表示更新の通知を送る。これにより画面が更新される
        markNeedsPaint();
        print("_img created: $_img");
      });
    });
  });

  // RenderBoxの中では、paintメソッドをオーバーライドする必要がある
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
    Paint p = Paint();
    Offset off = Offset(dx + 50.0, dy + 50.0);
    if (_img != null ) {  // 画像が適切に読み込まれているかどうか判断
      // 描画メソッド。[Canvas].drawImage([ui.Image], [Offset], [Paint]);
      c.drawImage(_img, off, p);
      print('draw _img.');
    } else {  // 画像が見つからない場合
      print('_img is null.');
    }

    // ちなみにここでは画像幅を指定していないため、大きい画像だとはみ出る
    // 幅を指定したい場合は以下のようにする
    Paint p2 = Paint();
    Offset off2 = Offset(dx + 50.0, dy + 50.0);
    // Rectで、描画する領域を指定する。ここ（r）では、描画先の描画領域を指定している
    Rect r = Rect.fromLTWH(dx + 50.0, dy + 50.0, 200.0, 200.0);
    if (_img != null ) {
      // ここ（r0）では、描画元の描画領域を指定。画像全体を描画するなら、画像の幅を指定する
      Rect r0 = Rect.fromLTWH(0.0, 0.0, _img.width.toDouble(), _img.height.toDouble());
      // 描画メソッド。drawImageではなくdrawImageRectであることに注意
      // [Canvas].drawImageRect([ui.Image], [Rect（描画元）], [Rect（描画先）], [Paint]);
      c.drawImageRect(_img, r0, r, p);
      print('draw _img.');
    } else {
      print('_img is null.');
    }

  }
}