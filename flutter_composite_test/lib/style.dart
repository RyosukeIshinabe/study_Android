import 'package:flutter/material.dart';

class AppStyle {

  static var defaultTextStyle = TextStyle(
    fontSize:16.0,
    color: const Color(0xFF000000),
    fontWeight: FontWeight.w300,
    fontFamily: "Roboto"
  );

  static var smallTextStyle = TextStyle(
    fontSize:12.0,
    color: const Color(0xFF000000),
    fontWeight: FontWeight.w200,
    fontFamily: "Roboto"
  );

  static var largeTextStyle = TextStyle(
    fontSize:20.0,
    color: const Color(0xFF000000),
    fontWeight: FontWeight.w400,
    fontFamily: "Roboto"
  );

  static var bgColor = Color.fromARGB(255, 230, 230, 230);  // gray
  static var mainColor = Color.fromARGB(255, 240, 188, 8);  // orange
  static var accentColor = Color.fromARGB(255, 0, 0, 0);  // black
  static var basicColor = Color.fromARGB(255, 255, 255, 255); // white

}