import 'package:flutter/material.dart';
import 'style.dart';
import 'package:percent_indicator/percent_indicator.dart';

class Home {

  static Center homeContents = new Center(
    child: Column(
      mainAxisAlignment: MainAxisAlignment.start,
      mainAxisSize: MainAxisSize.max,
      crossAxisAlignment: CrossAxisAlignment.center,
      children: <Widget>[

        Container(
          color: const Color(0xFFfafafa),
          margin: const EdgeInsets.all(20.0),
          alignment: Alignment.center,
          child: Row(
            mainAxisAlignment: MainAxisAlignment.start,
            mainAxisSize: MainAxisSize.max,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[

              Padding(
                padding: EdgeInsets.all(10.0),
              ),

              CircularPercentIndicator(
                radius: 120.0,
                lineWidth: 13.0,
                animation: true,
                percent: 0.7,
                center: new Text(
                    "70.0%",
                    style: AppStyle.defaultTextStyle
                ),
                circularStrokeCap: CircularStrokeCap.round,
                progressColor: AppStyle.mainColor,
              ),

              Padding(
                padding: EdgeInsets.all(10.0),
              ),

              Text(
                "70%のユーザーがぁ" + "\n" +
                "次のアクション：あああ",
                style: AppStyle.defaultTextStyle,
              ),
            ]
          ),
        ),

        Container(
          color: const Color(0xFFfafafa),
          margin: const EdgeInsets.all(20.0),
          alignment: Alignment.center,
          child: Text("ヒャッハーーー", style: AppStyle.defaultTextStyle,),
        ),
      ]
    ),
  );

}