# PanelView
android PanelView DashBoard 仪表盘 汽车仪表盘 气压仪表盘
=======
求star  
![image](https://github.com/githubwing/PanelView/raw/master/perview.gif)
###How To Use
add a CirclePercentView into your XML.

```
   <com.wingsofts.panelview.PanelView
        android:id="@+id/panelView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
    <com.wingsofts.panelview.PanelView
        android:id="@+id/panelView2"
        wing:arcColor="#EF1C63"
        android:text="当前速度"
        android:textSize="18sp"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
```
you can change percent by call method
```
setPercent();
setText();
setTextSize();
setArcColor();
setPointerColor();
setArcWidth();

```
