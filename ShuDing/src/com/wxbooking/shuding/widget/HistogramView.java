package com.wxbooking.shuding.widget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.wxbooking.shuding.R;
import com.wxbooking.shuding.utils.StringUtils;

public class HistogramView extends View {

	private Paint xLinePaint;// 坐标轴 轴线 画笔：
	private Paint hLinePaint;// 坐标轴水平内部 虚线画笔
	private Paint textPaint;// 绘制文本的画笔
	private Paint paint;// 矩形画笔 柱状图的样式信息

	private int[] aniProgress;// 实现动画的值
	private final int TRUE = 1;// 在柱状图上显示数字
	private int[] textData;// 设置点击事件，显示哪一条柱状的信息
	private Bitmap bitmap;
	private double ratio = 1;

	// 坐标轴底部的星期数
	private String[] xWeeks = new String[] { "", "", "", "", "", "", "" };
	// 坐标轴左侧的数标
	private String[] ySteps = new String[] { "", "", "", "", "" };
	// 数据
	private int[] progress = new int[] { 0, 0, 0, 0, 0, 0, 0 };

	private int flag;// 是否使用动画

	private HistogramAnimation ani;

	public HistogramView(Context context) {
		super(context);
		init();
	}

	public HistogramView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public void setData(JSONArray jsonarray) {
		for (int i = 0; i < jsonarray.length(); i++) {
			try {
				JSONObject obj = (JSONObject) jsonarray.get(i);
				String data = obj.optString("data");
				progress[i] = Integer.parseInt(data);
				String time = obj.optString("time");
				long ltime = Long.parseLong(time);
				if (i == 0) {
					xWeeks[i] = StringUtils.longToDateMMDD(ltime);
				} else if (i == 6) {
					xWeeks[i] = StringUtils.longToDateMMDD(ltime);
				} else {
					xWeeks[i] = StringUtils.longToDateDD(ltime);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		ySteps = getYArr(progress, ySteps.length);
		ratio = getMax(progress) * 1.2;
	}

	/**
	 * 获取 图表Y轴 数据
	 * 
	 * @param arrData
	 *            用户数据
	 * @param arrData
	 *            用户数据
	 * @return arrY
	 */
	private String[] getYArr(int[] arrData, int arrlen) {
		int max = getMax(arrData);
		String[] arrY = new String[arrlen];
		int ev = (int) (max / (1.0 * arrlen - 1)) + 1;
		for (int i = 0; i < arrY.length; i++) {
			arrY[i] = ev * (arrlen - 1 - i) + "";
		}
		return arrY;

	}

	/**
	 * 求数组最大值
	 */
	private int getMax(int[] arr) {

		int max = arr[0];

		for (int i = 0; i < arr.length; i++) {
			if (max < arr[i]) {
				max = arr[i];
			}
		}

		return max;

	}

	private void init() {
		textData = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		aniProgress = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		ani = new HistogramAnimation();
		ani.setDuration(2000);

		xLinePaint = new Paint();
		hLinePaint = new Paint();
		textPaint = new Paint();
		paint = new Paint();

		// 给画笔设置颜色
		xLinePaint.setColor(Color.parseColor("#79c7f5"));
		hLinePaint.setColor(Color.parseColor("#79c7f5"));
		// titlePaint.setColor(Color.BLACK);
		textPaint.setColor(Color.WHITE);

		// 加载画图
		bitmap = BitmapFactory
				.decodeResource(getResources(), R.drawable.column);
	}

	public void start(int flag) {
		this.flag = flag;
		this.startAnimation(ani);
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		int width = getWidth();
		int height = getHeight() - dp2px(35);
		// 绘制底部的线条
		canvas.drawLine(dp2px(30), height + dp2px(3), width - dp2px(15), height
				+ dp2px(3), xLinePaint);

		int leftHeight = height - dp2px(5);// 左侧外周的 需要划分的高度：

		int hPerHeight = leftHeight / 4;// 分成四部分

		hLinePaint.setTextAlign(Align.CENTER);
		// 设置四条虚线
		// for (int i = 0; i < 4; i++) {
		// canvas.drawLine(dp2px(30), dp2px(10) + i * hPerHeight, width
		// - dp2px(30), dp2px(10) + i * hPerHeight, hLinePaint);
		// }

		// for (int i = 0; i < 4; i++) {
		// canvas.drawLine(dp2px(30), dp2px(10) + i * hPerHeight, dp2px(33),
		// dp2px(10) + i * hPerHeight, hLinePaint);
		// }

		// 绘制 Y 周坐标
		textPaint.setTextAlign(Align.RIGHT);
		textPaint.setTextSize(sp2px(12));
		textPaint.setAntiAlias(true);
		textPaint.setStyle(Paint.Style.FILL);
		// 设置左部的数字
		for (int i = 0; i < ySteps.length; i++) {
			canvas.drawText(ySteps[i], dp2px(25), dp2px(13) + i * hPerHeight,
					textPaint);
		}
		// 绘制Y轴线
		canvas.drawLine(dp2px(30), dp2px(10), dp2px(30), height + dp2px(3),
				hLinePaint);

		// 绘制 X 周 做坐标
		int xAxisLength = width - dp2px(10);
		int columCount = xWeeks.length + 1;
		int step = xAxisLength / columCount;

		// 设置底部的数字
		// for (int i = 0; i < columCount - 1; i++) {
		// // text, baseX, baseY, textPaint
		// canvas.drawText(xWeeks[i], dp2px(10) + step * (i + 1), height
		// + dp2px(20), textPaint);
		// }

		// 设置底部的数字
		for (int i = 0; i < columCount - 1; i++) {
			// text, baseX, baseY, textPaint
			if (i == 0) {
				canvas.drawText(xWeeks[i], dp2px(30) + step * (i + 1), height
						+ dp2px(20), textPaint);
			} else if (i == columCount - 2) {
				canvas.drawText(xWeeks[i], dp2px(30) + step * (i + 1), height
						+ dp2px(20), textPaint);
			} else {
				canvas.drawText(xWeeks[i], dp2px(14) + step * (i + 1), height
						+ dp2px(20), textPaint);
			}

		}

		// 绘制矩形
		if (aniProgress != null && aniProgress.length > 0) {
			for (int i = 0; i < aniProgress.length; i++) {// 循环遍历将7条柱状图形画出来
				int value = aniProgress[i];
				paint.setAntiAlias(true);// 抗锯齿效果
				paint.setStyle(Paint.Style.FILL);
				paint.setTextSize(sp2px(15));// 字体大小
				paint.setColor(Color.parseColor("#6DCAEC"));// 字体颜色
				Rect rect = new Rect();// 柱状图的形状

				rect.left = step * (i + 1);
				rect.right = dp2px(15) + step * (i + 1);
				int rh = (int) (leftHeight - leftHeight * (value / ratio));
				rect.top = rh + dp2px(0);
				rect.bottom = height;

				canvas.drawBitmap(bitmap, null, rect, paint);
				// 是否显示柱状图上方的数字
				if (this.textData[i] == TRUE) {
					canvas.drawText(value + "", dp2px(15) + step * (i + 1)
							- dp2px(15), rh - dp2px(5), paint);
				}
			}
		}

	}

	private int dp2px(int value) {
		float v = getContext().getResources().getDisplayMetrics().density;
		return (int) (v * value + 0.5f);
	}

	private int sp2px(int value) {
		float v = getContext().getResources().getDisplayMetrics().scaledDensity;
		return (int) (v * value + 0.5f);
	}

	/**
	 * 设置点击事件，是否显示数字
	 */
	public boolean onTouchEvent(MotionEvent event) {
		int step = (getWidth() - dp2px(30)) / 8;
		int x = (int) event.getX();
		for (int i = 0; i < 7; i++) {
			if (x > (dp2px(15) + step * (i + 1) - dp2px(15))
					&& x < (dp2px(15) + step * (i + 1) + dp2px(15))) {
				textData[i] = 1;
				for (int j = 0; j < 7; j++) {
					if (i != j) {
						textData[j] = 0;
					}
				}
				if (Looper.getMainLooper() == Looper.myLooper()) {
					invalidate();
				} else {
					postInvalidate();
				}
			}
		}
		return super.onTouchEvent(event);
	}

	/**
	 * 集成animation的一个动画类
	 * 
	 * @author 李垭超
	 */
	private class HistogramAnimation extends Animation {
		protected void applyTransformation(float interpolatedTime,
				Transformation t) {
			super.applyTransformation(interpolatedTime, t);
			if (interpolatedTime < 1.0f && flag == 2) {
				for (int i = 0; i < aniProgress.length; i++) {
					aniProgress[i] = (int) (progress[i] * interpolatedTime);
				}
			} else {
				for (int i = 0; i < aniProgress.length; i++) {
					aniProgress[i] = progress[i];
				}
			}
			invalidate();
		}
	}

}