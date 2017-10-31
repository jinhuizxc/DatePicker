# DatePicker
项目中抽离的日期选择器

  每当我们在做日期选择器的时候通常是用原生的日期选择器，但是远远满足不了项目需求，那么当我们自己去写的时候困难还是有的，今天为大家分享一下自定义的日期选择器，分享交流开心你我他！

## 项目效果图
<img src = "https://github.com/jinhuizxc/DatePicker/blob/master/screenshots/img1.jpg">
## GIF
<img src = "https://github.com/jinhuizxc/DatePicker/blob/master/screenshots/2.gif">

##  项目目录结构
<img src = "https://github.com/jinhuizxc/DatePicker/blob/master/screenshots/img2.jpg">

## 代码块-简要分析
主界面布局很简单，主要介绍选择日期的点击事件。

* 1.选择日期的点击事件：
```
 ll_selectedtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] str = new String[10];
                final ChangeDatePopwindow mChangeBirthDialog = new ChangeDatePopwindow(MainActivity.this);
//                mChangeBirthDialog.setDate("2017", "6", "20");
                mChangeBirthDialog.showAtLocation(frameLayout, Gravity.TOP, 0, 400);
                mChangeBirthDialog.setBirthdayListener(new ChangeDatePopwindow.OnBirthListener() {

                    @Override
                    public void onClick(String year, String month, String day) {

                        selectDate(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day));
                    }
                });
            }
        });
```
* 2.自定义的popwindow （由于代码比较多这里就不详细说明了，具体可以建项目源码。）
```
年/月/日的监听方法都是类似的贴出其中一个：
wvYear.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                String currentText = (String) mYearAdapter.getItemText(wheel.getCurrentItem());
                selectYear = currentText;
                setTextviewSize(currentText, mYearAdapter);
                currentYear = currentText.substring(0, currentText.length() - 1).toString();
                Log.d("currentYear==", currentYear);
                setYear(currentYear);
                initMonths(Integer.parseInt(month));
                mMonthAdapter = new CalendarTextAdapter(context, arry_months, 0, maxTextSize, minTextSize);
                wvMonth.setVisibleItems(5);
                wvMonth.setViewAdapter(mMonthAdapter);
                wvMonth.setCurrentItem(0);

                calDays(currentYear, month);
            }
        });
```
* 3.自定义的wheelView 代码比较多有1000行代码，如果你需要用直接粘过来就可以了，不做详细描述。
```
滚轮wheelview的监听部分
// Scrolling listener
	WheelScroller.ScrollingListener scrollingListener = new WheelScroller.ScrollingListener() {
		@Override
		public void onStarted() {
			isScrollingPerformed = true;
			notifyScrollingListenersAboutStart();
		}

		@Override
		public void onScroll(int distance) {
			doScroll(distance);

			int height = getHeight();
			if (scrollingOffset > height) {
				scrollingOffset = height;
				scroller.stopScrolling();
			} else if (scrollingOffset < -height) {
				scrollingOffset = -height;
				scroller.stopScrolling();
			}
		}

		@Override
		public void onFinished() {
			if (isScrollingPerformed) {
				notifyScrollingListenersAboutEnd();
				isScrollingPerformed = false;
			}

			scrollingOffset = 0;
			invalidate();
		}

		@Override
		public void onJustify() {
			if (Math.abs(scrollingOffset) > WheelScroller.MIN_DELTA_FOR_SCROLLING) {
				scroller.scroll(scrollingOffset, 0);
			}
		}
	};
```
* 4.项目中用到的适配器，继承关系比较复杂，希望读者好好学习理解啦。
```
在wheelview里面主要用到的是CalendarTextAdapter，继承自AbstractWheelTextAdapter1，贴出部分代码，代码比较多，望读者好好体会了。
 class CalendarTextAdapter extends AbstractWheelTextAdapter1 {
        ArrayList<String> list;

        protected CalendarTextAdapter(Context context, ArrayList<String> list, int currentItem, int maxsize, int minsize) {
            super(context, R.layout.item_birth_year, NO_RESOURCE, currentItem, maxsize, minsize);
            this.list = list;
            setItemTextResource(R.id.tempValue);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return list.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            return list.get(index) + "";
        }
    }
```

## 如果你觉得以上对你有帮助，欢迎给个star!

## 关于我 [简书](http://www.jianshu.com/u/e0d050a2120f)|[csdn](http://blog.csdn.net/jinhui157)




