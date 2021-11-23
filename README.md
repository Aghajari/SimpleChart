# SimpleChart
<img src="./images/1.png" width=300 title="Screen">  <img src="./images/2.png" width=300 title="Screen">

Simple Line, Circle, Bar chart for Android

## LineChart
```xml
<com.aghajari.simplechart.LineChart
    android:id="@+id/line_chart"
    android:layout_width="350dp"
    android:layout_height="200dp"
    android:layout_gravity="center" />
```
```java
LineChart lineChart = findViewById(R.id.line_chart);
lineChart.setLineColor(...);
lineChart.setPointColor(...);
lineChart.addData(8f, 10f, 5f, 7f, 4f, 6f);
```

## CircleChart
```xml
<com.aghajari.simplechart.CircleChart
    android:id="@+id/circle_chart"
    android:layout_width="200dp"
    android:layout_height="200dp"
    android:layout_gravity="center" />
```
```java
CircleChart circleChart = findViewById(R.id.circle_chart);
circleChart.addData(0xFF7AA6E8, Color.WHITE, 150, 40);
circleChart.addData(0xFFD273A2, Color.WHITE, 125, 35);
circleChart.addData(0xFFE2B263, Color.WHITE, 100, 25);
```

## BarChart
```xml
<com.aghajari.simplechart.BarChart
    android:id="@+id/bar_chart"
    android:layout_width="300dp"
    android:layout_height="200dp"
    android:layout_gravity="center" />
```
```java
BarChart barChart = findViewById(R.id.bar_chart);
barChart.setMaxValue(100);
barChart.addData(60, "Say");
barChart.addData(40, "Hello");
barChart.addData(80, "World");
barChart.addData(30, ":)");
```

License
=======

    Copyright 2020 Amir Hossein Aghajari
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


<div align="center">
  <img width="64" alt="LCoders | AmirHosseinAghajari" src="https://user-images.githubusercontent.com/30867537/90538314-a0a79200-e193-11ea-8d90-0a3576e28a18.png">
  <br><a>Amir Hossein Aghajari</a> • <a href="mailto:amirhossein.aghajari.82@gmail.com">Email</a> • <a href="https://github.com/Aghajari">GitHub</a>
</div>
