# AlpayRing
类似支付宝芝麻信用仪表盘

![pic1](https://github.com/ldoublem/AlpayRing/blob/master/app/pic/1.png)

![pic2](https://github.com/ldoublem/AlpayRing/blob/master/app/pic/2.png)

![gif1](https://github.com/ldoublem/AlpayRing/blob/master/app/pic/gif1.gif)

## RingView Usage 使用
```
        rv_view.setPointer(true); show pointer
        //rv_view.setPointer(false);
        rv_view.setValue(value, new RingView.OnProgerssChange() {
            @Override
            public void OnProgerssChange(float interpolatedTime) {

            }
        },animDuration);
```


