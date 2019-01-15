import 'package:flutter/material.dart';

class Card {
  static final int KEY_OF_CLUB = 0;
  static final int KEY_OF_DIA = 1;
  static final int KEY_OF_HEART = 2;
  static final int KEY_OF_SPADE = 3;

  int mark;
  int number;
  bool shuffle;

  // コンストラクタ
  Card(int mark, int number, bool shuffle) {
    this.mark = mark;
    this.number = number;
    this.shuffle = shuffle;
  }

  // カードを表現する文字列を返す
  String cardToString() {
    String shuffleStatus;
    if ( this.shuffle ) {
      shuffleStatus = "[CHANGE]";
    } else {
      shuffleStatus = "[STAY]";
    }
    String markString;
    if ( this.mark == Card.KEY_OF_CLUB ) {
      markString = "CLUB";
    } else if ( this.mark == Card.KEY_OF_DIA ) {
      markString = "DIA";
    } else if ( this.mark == Card.KEY_OF_HEART ) {
      markString = "HEART";
    } else if ( this.mark == Card.KEY_OF_SPADE ) {
      markString = "SPADE";
    }
    return shuffleStatus + "\n" + markString + "\n" + this.number.toString();
  }

  // シャッフル値を反転する
  void changeShuffleStatus() {
    this.shuffle = !this.shuffle;
  }
}