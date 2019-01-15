import 'package:flutter/material.dart';
import 'poker.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Poker',
      theme: ThemeData(
        primarySwatch: Colors.grey,
        accentColor: Colors.yellowAccent,
      ),
      home: Poker(),
    );
  }
}


