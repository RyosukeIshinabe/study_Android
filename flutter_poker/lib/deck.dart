import 'package:flutter/material.dart';
import 'dart:math';
import 'trump.dart';
import 'poker.dart';

class Deck {
  List<Trump> deck = new List();

  // コンストラクタ
  Deck() {
    init();
  }

  // 未使用デッキの初期化
  void init() {
    for (int i = 0; i < Poker.MAX_MARK; i++) {
      for (int j = 0; j < Poker.MAX_NUMBER; j++) {
        this.deck.add(Trump(i, j, false));
      }
    }
  }

  // 引数で指定されたカードを表現する文字列を返す
  String cardToString(int cardNum) {
    return this.deck[cardNum].cardToString();
  }

  // 引数で指定されたカードのシャッフル値を取得する
  bool getShuffleStatus(int cardNum) {
    return this.deck[cardNum].shuffle;
  }

  // 引数で指定されたカードのシャッフル値を反転する
  void changeShuffleStatus(int cardNum) {
    this.deck[cardNum].changeShuffleStatus();
  }

  // デッキ内のnull以外のカードの枚数を取得する
  int getDeckLength() {
    int count = 0;
    for ( int i = 0; i < this.deck.length; i++ ) {
      if ( deck[i] != null ) {
        count++;
      }
    }
    return count;
  }

  // デッキの中からnull以外のランダムなカードを取り出して消す
  Trump takeCardFromDeckAndErase() {
    if ( this.getDeckLength() == 0 ) {	// 残り枚数が0ならnullを返す
      return null;
    }

    Random rand = new Random();	// ランダムな数字を作成するためのクラス
    int index;

    do {
      index = rand.nextInt(Poker.MAX_CARD);	// ランダムな数字を作成して
      print('created rand number is: ' + index.toString());
    } while ( deck[index] == null );	// そのカードがnullだった場合do{}内を繰り返す

    Trump refuge = deck[index];	// 引いたカードを一旦避難
    deck[index] = null;	// 引いたカードの場所をnullにする
    return refuge;	// 避難していたカードをreturn
  }

  // デッキ内のカードを調べ、マーク（0〜3）をキー、枚数を値にした配列を返す
  List<int> getSameMarkOfCardFromDeck() {

    // マークの種類分の配列を用意
    List<int> sameMarkDeck = new List(Poker.MAX_MARK);
    for ( int i = 0; i < Poker.MAX_MARK; i++ ) {
      sameMarkDeck[i] = 0;
    }

    // デッキ内の全てのカードを調べて同じマークがあった場合インクリメント
    for ( int j = 0; j < this.getDeckLength(); j++ ) {
      int mark = this.deck[j].mark;
      sameMarkDeck[mark]++;
    }
    return sameMarkDeck;
  }

  // デッキ内のカードを調べ、数字（1〜13）をキー、枚数を値にした配列を返す
  List<int> getSameNumOfCardFromDeck() {

    // 数字の種類分の配列を用意
    List<int> sameNumDeck = new List(Poker.MAX_NUMBER);
    for ( int i = 0; i < Poker.MAX_NUMBER; i++ ) {
      sameNumDeck[i] = 0;
    }

    // デッキ内の全てのカードを調べて同じマークがあった場合インクリメント
    for ( int j = 0; j < this.getDeckLength(); j++ ) {
      int num = this.deck[j].number;
      sameNumDeck[num]++;
    }
    return sameNumDeck;
  }

  // デッキの末尾にカードを挿入する
  void insertCard(Trump trump) {
    for ( int i = 0; i < this.deck.length; i++ ) {
      if ( this.deck[i] == null ) {
        this.deck[i] = trump;
        break;
      }
    }
  }

  // デッキ内の任意のカードを破棄
  void setNull(int cardNum) {
    deck[cardNum] = null;
  }

  // デッキ内のカードを全て破棄
  void setNullAll() {
    for ( int i = 0; i < getDeckLength(); i++ ) {
      deck[i] = null;
    }
  }
}
