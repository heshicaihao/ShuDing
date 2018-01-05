package com.wxbooking.shuding.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

public class ChartView extends View {

	public int XPoint = 40; // 原点的X坐标
	public int YPoint = 260; // 原点的Y坐标
	public int XScale = 55; // X的刻度长度
	public int YScale = 40; // Y的刻度长度
	public int XLength = 380; // X轴的长度
	public int YLength = 240; // Y轴的长度
	public String[] XLabel; // X的刻度
	public String[] YLabel; // Y的刻度
	public String[] Data; // 数据
	public String Title; // 显示的标题

	public Paint picPaint;
	public Paint linePaint;
	float screenensity;

	public ChartView(Context context) {
		super(context);
		init(context);
	}

	public ChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public void setData(String[] XLabels, String[] YLabels, String[] AllData,
			String strTitle, int picWidth, int picHeight, Activity content) {

		screenensity = getScreenensity(content);

		XLabel = XLabels;
		YLabel = YLabels;
		Data = AllData;
		Title = strTitle;

		XPoint = (int) (screenensity * 20 * 2);
		YPoint = (int) (screenensity * picHeight);

		XLength = (int) (picWidth - XPoint-screenensity *30);
		YLength = (int) (YPoint - screenensity * 10);

		XScale = XLength / XLabels.length + 1;
		YScale = YLength / YLabels.length + 1;

	}
	
	private void init(Context context) {
		picPaint = new Paint();
		picPaint.setStyle(Paint.Style.STROKE);
		picPaint.setAntiAlias(true);// 去锯齿
//		picPaint.setColor(Color.BLACK);
		picPaint.setColor(Color.parseColor("#79c7f5"));
		picPaint.setTextSize(16); // 颜色
		linePaint = new Paint();
		linePaint.setStyle(Paint.Style.STROKE);
		linePaint.setAntiAlias(true);// 去锯齿
		linePaint.setColor(Color.WHITE);
		linePaint.setTextSize(16); // 设置轴文字大小
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);// 重写onDraw方法

		// 设置Y轴
		canvas.drawLine(XPoint, YPoint - YLength, XPoint, YPoint, picPaint); // 轴线
		for (int i = 0; i * YScale < YLength; i++) {
			canvas.drawLine(XPoint, YPoint - i * YScale, XPoint + 5, YPoint - i
					* YScale, picPaint); // 刻度
			try {
				canvas.drawText(YLabel[i], XPoint - 22,
						YPoint - i * YScale + 5, picPaint); // 文字
			} catch (Exception e) {
			}
		}
		canvas.drawLine(XPoint, YPoint - YLength, XPoint - 3, YPoint - YLength
				+ 6, picPaint); // 箭头
		canvas.drawLine(XPoint, YPoint - YLength, XPoint + 3, YPoint - YLength
				+ 6, picPaint);
		// 设置X轴
		canvas.drawLine(XPoint, YPoint, XPoint + XLength, YPoint, picPaint); // 轴线
		for (int i = 0; i * XScale < XLength; i++) {
			canvas.drawLine(XPoint + i * XScale, YPoint, XPoint + i * XScale,
					YPoint - 5, picPaint); // 刻度
			try {
				canvas.drawText(XLabel[i], XPoint + i * XScale - 10,
						YPoint + 20, picPaint); // 文字
				// 数据值
				if (i > 0 && YCoord(Data[i - 1]) != -999
						&& YCoord(Data[i]) != -999) // 保证有效数据
					canvas.drawLine(XPoint + (i - 1) * XScale,
							YCoord(Data[i - 1]), XPoint + i * XScale,
							YCoord(Data[i]), linePaint);
				canvas.drawCircle(XPoint + i * XScale, YCoord(Data[i]), 2,
						linePaint);
			} catch (Exception e) {
			}
		}
		canvas.drawLine(XPoint + XLength, YPoint, XPoint + XLength - 6,
				YPoint - 3, picPaint); // 箭头
		canvas.drawLine(XPoint + XLength, YPoint, XPoint + XLength - 6,
				YPoint + 3, picPaint);
		linePaint.setTextSize(16);
		canvas.drawText(Title, 150*screenensity, 50*screenensity, linePaint);
	}

	private int YCoord(String y0) // 计算绘制时的Y坐标，无数据时返回-999
	{
		int y;
		try {
			y = Integer.parseInt(y0);
		} catch (Exception e) {
			return -999; // 出错则返回-999
		}
		try {
			return YPoint - y * YScale / Integer.parseInt(YLabel[1]);
		} catch (Exception e) {
		}
		return y;
	}
	
	/**
	 * 获取手机屏幕 密度
	 * 
	 * @return height
	 */
	public static float getScreenensity(Activity context) {
		DisplayMetrics metric = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(metric);
		float density = metric.density;
		return density;
	}
}