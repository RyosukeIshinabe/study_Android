import 'package:flutter/material.dart';
import 'deck.dart';

class Poker extends StatefulWidget {
  static final int MAX_MARK = 4;
  static final int MAX_NUMBER = 13;
  static final int MAX_CARD = MAX_MARK * MAX_NUMBER;

  static final int MAX_FIELD_CARD_LENGTH = 5;  // フィールド上のデッキの最大数
  static final int MAX_SHUFFLABLE_TIME = 2;  // シャッフルできる最大値

  @override
  _PokerState createState() => _PokerState();
}

class _PokerState extends State<Poker> {
  Deck deck; // デッキ
  String message; // 下部に表示されるメッセージ用変数
  int shufflableTime; // 現在のシャッフル回数
  int coin = 100; // 所持コイン。テストで固定
  int bet = 10; // ベット。テストで固定
  String card1; // カード用のテキストビュー
  String card2; // カード用のテキストビュー
  String card3; // カード用のテキストビュー
  String card4; // カード用のテキストビュー
  String card5; // カード用のテキストビュー
  Widget cardInfo1; // テステス

  // アプリ全体通して使用するテキストスタイルを予め設定し各ウィジェットで使い回す
  var defaultTextStyle = TextStyle(
    fontSize:12.0,
    fontWeight: FontWeight.w200,
    fontFamily: "Roboto",);
  var largeTextStyle = TextStyle(
    fontSize:16.0,
    fontWeight: FontWeight.w300,
    fontFamily: "Roboto",);

  // 最初だけ呼ばれる初期化メソッド
  @override
  void initState() {
    super.initState();
    shufflableTime = 1;  // シャッフル回数をリセット
    message = 'ゲームを開始します。' + '\n' +
<<<<<<< HEAD
        shufflableTime.toString() + '/' + Poker.MAX_SHUFFLABLE_TIME.toString() + ' 回目のシャッフルです。';
    deck = new Deck();  // 未使用デッキを初期化
    changeCardFromUnUsedToField();  // 未使用デッキからフィールドにカードを補填
    reloadCard();
=======
        'カードをタップしてSTAYorCHANGEを切り替えられます' +
        'SHUFFLEボタンでCHANGEのカードを捨てて補填します' +
        'SHUFFLEは最大2回まで可能です。' +
        shufflableTime.toString() + '/' + Poker.maxShufflableTime.toString() + ' 回目のシャッフルです。';
    unUsedDeck = new Deck();  // 未使用デッキを初期化
    fieldDeck = new Deck(); // フィールドデッキを初期化
    moveCardFromUnUsedToField();  // 未使用デッキからフィールドにカードを補填
    card1 = fieldDeck.deck[0].cardToString();
    card2 = fieldDeck.deck[1].cardToString();
    card3 = fieldDeck.deck[2].cardToString();
    card4 = fieldDeck.deck[3].cardToString();
    card5 = fieldDeck.deck[4].cardToString();
>>>>>>> cc0696514d85b3de3d616e4b68e9aa57b5ab97ac
  }

  // ウィジェット（画面の部品）の組み立て
  @override
  Widget build(BuildContext context) {

    return Scaffold(
      appBar: AppBar(
        title: Text('Flutter Poker'),
        leading: BackButton(color: Colors.white),
      ),

      body: Column(
        mainAxisAlignment: MainAxisAlignment.start,
        mainAxisSize: MainAxisSize.max,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: <Widget>[

          Padding(
            padding: EdgeInsets.only(top: 20.0),
          ),

          Text(
            "COIN: " + coin.toString(),
            style: largeTextStyle,
          ),
          Text(
            "BET: " + bet.toString(),
            style: largeTextStyle,
          ),

          Padding(
            padding: EdgeInsets.all(10.0),
          ),

          Text(
            "1ペア：***   2ペア：***" + "\n"
            "3カード：***   4カード：***",
            style: largeTextStyle,
          ),

          Padding(
            padding: EdgeInsets.all(10.0),
          ),

          // カードの行（1枚目と2枚目）
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            mainAxisSize: MainAxisSize.max,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[

              // 1枚目のカード
              RaisedButton(
                key:null,
                onPressed:card01Pressed,
                color: Colors.black12,
                child: Padding(
                  padding: EdgeInsets.all(10.0),
                  child: Text(
                      card1,
                      style: largeTextStyle
                  ),
                ),
              ),

              // 2枚目のカード
              RaisedButton(
                key:null,
                onPressed:card02Pressed,
                color: Colors.black12,
                child: Padding(
                  padding: EdgeInsets.all(10.0),
                  child: Text(
                      card2,
                      style: largeTextStyle
                  ),
                ),
              ),
            ]
          ),

          Padding(
            padding: EdgeInsets.all(5.0),
          ),

          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            mainAxisSize: MainAxisSize.max,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[

              // 3枚目のカード
              RaisedButton(
                key:null,
                onPressed:card03Pressed,
                color: Colors.black12,
                child: Padding(
                  padding: EdgeInsets.all(10.0),
                  child: Text(
                      card3,
                      style: largeTextStyle
                  ),
                ),
              ),

              // 4枚目のカード
              RaisedButton(
                key:null,
                onPressed:card04Pressed,
                color: Colors.black12,
                child: Padding(
                  padding: EdgeInsets.all(10.0),
                  child: Text(
                      card4,
                      style: largeTextStyle
                  ),
                ),
              ),

              // 5枚目のカード
              RaisedButton(
                key:null,
                onPressed:card05Pressed,
                color: Colors.black12,
                child: Padding(
                  padding: EdgeInsets.all(10.0),
                  child: Text(
                      card5,
                      style: largeTextStyle
                  ),
                ),
              ),
            ]
          ),

          Padding(
            padding: EdgeInsets.all(10.0),
          ),

          // シャッフル決定ボタン
          RaisedButton(
            key:null,
            onPressed:fixButtonPressed,
            color: Colors.black12,
            child: Padding(
              padding: EdgeInsets.all(10.0),
              child: Text(
                  "SHUFFLE",
                  style: largeTextStyle
              ),
            ),
          ),

          Padding(
            padding: EdgeInsets.all(10.0),
          ),

          Text(
            message,
            style: largeTextStyle,
          ),

        ]
      ),
    );
  }

  // カードがタップされたときの処理
  void card01Pressed() {
    deck.changeShuffleStatusOfFieldCard(0);
    setState(() {
      card1 = deck.pickFieldCard(0).cardToString();;
    });
  }
  void card02Pressed() {
    deck.changeShuffleStatusOfFieldCard(1);
    setState(() {
      card2 = deck.pickFieldCard(1).cardToString();;
    });
  }
  void card03Pressed() {
    deck.changeShuffleStatusOfFieldCard(2);
    setState(() {
      card3 = deck.pickFieldCard(2).cardToString();;
    });
  }
  void card04Pressed() {
    deck.changeShuffleStatusOfFieldCard(3);
    setState(() {
      card4 = deck.pickFieldCard(3).cardToString();;
    });
  }
  void card05Pressed() {
    deck.changeShuffleStatusOfFieldCard(4);
    setState(() {
      card5 = deck.pickFieldCard(4).cardToString();;
    });
  }

  // すべてのカードのテキストを更新
  void reloadCard() {
    setState(() {
      card1 = deck.pickFieldCard(0).cardToString();
      card2 = deck.pickFieldCard(1).cardToString();
      card3 = deck.pickFieldCard(2).cardToString();
      card4 = deck.pickFieldCard(3).cardToString();
      card5 = deck.pickFieldCard(4).cardToString();
      cardInfo1 = cardInfo(0);
    });
  }

  // シャッフルボタンをタップした時の処理
  void fixButtonPressed() {
    if ( shufflableTime == Poker.MAX_SHUFFLABLE_TIME ) {
      gameSet();
    } else {
      shuffle(); // シャッフル
      changeCardFromUnUsedToField();  // 足りないカードを補填
      reloadCard();  // カードのテキストを更新
      shufflableTime++;
      setState(() {
        message = 'シャッフルしました。' + '\n' +
            shufflableTime.toString() + '/' + Poker.MAX_SHUFFLABLE_TIME.toString() + ' 回目のシャッフルです。';
      });
    }
  }

  // フィールドデッキに足りない枚数のカードを未使用デッキから引く
  void changeCardFromUnUsedToField() {
    while ( deck.countFiledCard() < Poker.MAX_FIELD_CARD_LENGTH ) {
      print('引く前のカードの枚数は:' + deck.countFiledCard().toString() );
      deck.takeCardFromUnusedCard();
      print('引いた後のカードの枚数は:' + deck.countFiledCard().toString() );
    }
  }

  // フィールドデッキ内のシャッフル値がtrueのカードをすべてフィールド=falseにする
  void shuffle() {
    for ( int i = 0; i < deck.getDeckLength(); i++ ) {
      if ( deck.cards[i].field && deck.cards[i].shuffle ) {
        deck.cards[i].changeFieldStatus();
      }
    }
  }

  // ゲーム終了
  void gameSet() {
    print('----- ゲームが終了しました。-----');
  }

  // カード情報をColumn状態で返す（シャッフル値<Icon>, マーク<Icon>, 数字<String>）
  Widget cardInfo(int index) {
    return Column(
        mainAxisAlignment: MainAxisAlignment.start,
        mainAxisSize: MainAxisSize.max,
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: <Widget>[
          new Icon(deck.returnIconOfShuffle(index)),
          new Icon(deck.returnIconOfMark(index)),
          new Text(deck.returnStringOfNumber(index), style: largeTextStyle),
        ]
    );
  }
}


class Rank {
  final int rankOfOnePair = 1;	// 2
  final int rankOfTwoPair = 3;	// 5
  final int rankOfThreeCard = 5;	// 10
  final int rankOfStraight = 7;	// 15
  final int rankOfFlush = 8;	// 20
  final int rankOfFullhouse = 10;	// 25
  final int rankOfFourCard = 20;	// 50
  final int rankOfStraightFlush = 30;	// 75
}

