## Android Circle Progress View
### ScreenShot
![circleprogress](https://github.com/pihat/Android_CircleProgress/blob/master/screenshot/circleprogress.gif)    
### Including in your project
#### Using Maven
Just need to add the following dependency to your `build.gradle` .

`compile 'com.pihat:circleprogress:0.91'`
### How to use?
#### In XML
##### Attributions
* circleStrokeWidth
* roundColor
* roundProgressColor
* valueText
* valueTextColor
* valueTextSize
* despText
* despTextColor
* despTextSize
* valueTextIsDisplayable
* despTextIsDisplayable
* style(STROKE or FILL )
        
##### Example
```
<com.ihat.pihat.circleprogress.CircleProgress
        android:id="@+id/cpv_finance"
        android:layout_width="50dp"
        android:layout_height="50dp"
        app:valueTextSize="13sp"
        app:despTextSize="07sp"
        app:roundProgressColor="@color/colorPrimaryDark"
        app:valueTextColor="@color/colorAccent"
        app:circleStrokeWidth="2dp"/>
```

        
#### In Java
##### Method
*  setValueText(String)
*  setValueTextColor(int)
*  setValueTextSize(int)
*  setDespText(String)
*  setDespTextSize(int)
* setDespTextColor(int)
* setValueTextIsDisplayable(boolean)
* setDespTextIsDisplayable(boolean)
* setRoundColor(int)
* setRoundProgressColor(int)
* setCircleStrokeWidth(int)
* setCircleStyle(int)   (Stroke - 0, FILL - 1)
* setSweepAngle(int)
* setInterpolator(Interpolator)
* setAnimDuration(int)
* forceInvalidate(void)
*  setSweepValue(int)  

##### Example
```
        holder.cpv.setAnimDuration(2000);
        holder.cpv.setInterpolator(new AccelerateDecelerateInterpolator());
        holder.cpv.setSweepValue(value);
        holder.cpv.setValueText(beanList.get(position).getNum() + "万");
        holder.cpv.anim();
```
