package com.posin.weight.ui.activity;

import com.posin.svr.IScaleService;
import com.posin.weight.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AdViewActivity extends Activity {

	private static final String TAG = "AdView";

	private IScaleService iWeight = null;

	private View mAdView;
	private TextView mValuesView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ad_view);

		LinearLayout parent = (LinearLayout) findViewById(R.id.layout_container);
		//mView = findViewById(R.id.view_ad_drawing);
		mAdView = new AdView(this);
		mAdView.setBackgroundColor(Color.GRAY);
		parent.addView(mAdView, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

		mValuesView = (TextView) findViewById(R.id.txt_values);

		Button btn;

		btn = (Button) this.findViewById(R.id.btn_close);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AdViewActivity.this.finish();
			}
		});

		bindIWeight();

		if(iWeight == null) {
			showError("错误", "无法连接称重服务程序");
			return;
		}

		new Thread(mReaderThread).start();
	}

	private static final int MSG_UPDATE_VIEW = 0x101;

	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
				case MSG_UPDATE_VIEW:
					mAdView.invalidate();
					break;
			}
			super.handleMessage(msg);
		}
	};

	private static final int ID_TARE_WEIGHT = 0; //皮重
	private static final int ID_WEIGHT = 1; //净重
	private static final int ID_NUMBER_TARE_WEIGHT = 2; //数字皮重
	private static final int ID_ZERO_OK_MARK = 3;  //开机零点是否正常
	private static final int ID_SIGN_MARK = 4;   //符号   ture 为正  false 负
	private static final int ID_ZERO_MARK = 5 ;  //零位标志
	private static final int ID_STEADY_MARK = 6; //稳定标志
	private static final int ID_TARE_MARK = 7;   //去皮状态
	private static final int ID_OVERLOAD_MARK = 8;  //超载状态
	private static final int ID_OPEN_ZERO_HIGH_MARK = 9;  //开机零点高标志
	private static final int ID_OPEN_ZERO_LOW_MARK = 10;  //开机零点低标志
	private static final int ID_POINT_NUMBER = 11; //小数点的位数
	private static final int ID_MAX_RANGE = 12; //最大量程范围
	private static final int ID_MIN_DIVISION = 13; //最小分度值
	private static final int ID_TIME_MILLISECONDS = 14; //当前的时间按毫秒计算; 最后一次读重量时的时间值
	private static final int MAX_WEIGHT_ITEMS_COUNT = 15;

	private static final int ID_AD = 15;
	private static final int ID_AD_RAW = 16;
	private static final int MAX_ID_COUNT = 17;

	private final Object mLock = new Object();
	private boolean mExitPending = false;

	private boolean exitPending() {
		synchronized(mLock) {
			return mExitPending;
		}
	}

	private void exit() {
		synchronized(mLock) {
			mExitPending = true;
		}
	}

	private final Runnable mReaderThread = new Runnable() {
		@Override
		public void run() {
			try {
				while(!exitPending()) {
					int[] w = iWeight.readValues(MAX_ID_COUNT);
					if(w == null) {
						Log.e(TAG, "failed to read weight.");
						continue;
					}
					int ad = 0;
					int adRaw = 0;
					int weight = 0;
					for(int i=0; i<w.length/2; i++) {
						int id = w[i*2];
						int value = w[i*2+1];
						switch(id) {
							case ID_AD:
								ad = value;
								break;
							case ID_AD_RAW:
								adRaw = value;
								break;
							case ID_WEIGHT:
								weight = value;
								break;
						}
					}
					if(ad != 0 && adRaw != 0) {
						addAd(ad, adRaw, weight);
					} else {
						Log.e(TAG, "failed to read ad");
					}
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	};

	//private static final int MAX_AD_COUNT = 30; // 每秒10个，约3秒
	private static final int MAX_AD_COUNT = 50; // 每秒10个，约3秒

	private final int[] mAdArray = new int[MAX_AD_COUNT];
	private final int[] mAdRawArray = new int[MAX_AD_COUNT];
	private int mAdAverage = 0;
	private int mAdCount = 0;
	private int mWeight = 0;

	private void addAd(int ad, int adRaw, int weight) {
		Log.d(TAG, "add : " + ad + ", " + adRaw);
		synchronized(mLock) {
			if(mAdCount < MAX_AD_COUNT) {
				mAdArray[mAdCount] = ad;
				mAdRawArray[mAdCount] = adRaw;
				mAdCount++;
			} else {

				/* if(Math.abs(ad - mAdAverage) > 50) {
					mAdCount = 0;
					mAdArray[mAdCount] = ad;
					mAdRawArray[mAdCount] = adRaw;
					mAdCount++;
				} else */
				{
					for(int i=1; i<MAX_AD_COUNT; i++) {
						mAdArray[i-1] = mAdArray[i];
						mAdRawArray[i-1] = mAdRawArray[i];
					}
					mAdArray[MAX_AD_COUNT-1] = ad;
					mAdRawArray[MAX_AD_COUNT-1] = adRaw;
				}
			}

			if(mAdCount > 2) {
				mAdAverage = 0;
				for(int i=0; i<mAdCount; i++)
					mAdAverage += mAdArray[i];
				mAdAverage /= mAdCount;
			} else {
				mAdAverage = ad;
			}
			mWeight = weight;
		}

		mHandler.removeMessages(MSG_UPDATE_VIEW);
		mHandler.obtainMessage(MSG_UPDATE_VIEW).sendToTarget();
	}

	private static final int DIFF_RANGE = 50;

	private static final int VIEW_OFFSET_X = 100;
	private static final int VIEW_OFFSET_Y = 100;

	private final class AdView extends View {
		public AdView(Context context) {
			super(context);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);

			int count;
			int[] ad = new int[MAX_AD_COUNT];
			int[] adRaw = new int[MAX_AD_COUNT];
			synchronized(mLock) {
				count = mAdCount;
				if(count > 0) {
					System.arraycopy(mAdArray, 0, ad, 0, count);
					System.arraycopy(mAdRawArray, 0, adRaw, 0, count);
				}
			}

			if(count < 2)
				return;

			int sumAd = 0;
			int sumRaw = 0;
			int midAd = 0;
			int midRaw = 0;
			if(count > 10) {
				for(int i=count-1; i>count-11; i--) {
					sumAd += ad[i];
					sumRaw += adRaw[i];
				}
				midAd = sumAd / 10;
				midRaw = sumRaw / 10;
			} else {
				for(int i=0; i<count; i++) {
					sumAd += ad[i];
					sumRaw += adRaw[i];
				}
				midAd = sumAd / count;
				midRaw = sumRaw / count;
			}

			//int refAd = (midAd/10)*10;
			midAd = (midRaw/25)*25;

			int maxAd = midAd + DIFF_RANGE;
			int minAd = midAd - DIFF_RANGE;

			//int diffAd = Math.abs(maxAd - minAd);
			int diffAd = DIFF_RANGE * 2;
			if(diffAd == 0)
				diffAd = 1;

			int w = canvas.getWidth()-VIEW_OFFSET_X;
			int h = canvas.getHeight();
			int midH = h/2;

			Paint paint = new Paint();
			paint.setAntiAlias(true);
			paint.setStyle(Paint.Style.FILL);
			paint.setStrokeWidth(2);

			//paint.setColor(Color.GREEN);
			//float refY = ((float)refAd-midAd) / DIFF_RANGE * midH + midH;
			//canvas.drawLine(VIEW_OFFSET_X, refY, w, refY, paint);

			paint.setColor(Color.GREEN);
			float rawY = ((float)midRaw-midAd) / DIFF_RANGE * midH + midH - VIEW_OFFSET_Y;
			canvas.drawLine(VIEW_OFFSET_X, rawY, w, rawY, paint);

			paint.setColor(Color.YELLOW);
			canvas.drawLine(VIEW_OFFSET_X, midH - VIEW_OFFSET_Y, w, midH - VIEW_OFFSET_Y, paint);

			paint.setColor(Color.RED);
			for(int i=1; i<count; i++) {
				float x0 = (float)(i-1)*w/MAX_AD_COUNT + VIEW_OFFSET_X;
				float x1 = (float)i*w/MAX_AD_COUNT + VIEW_OFFSET_X;
				float y0 = ((float)ad[i-1] - midAd) / DIFF_RANGE * midH + midH - VIEW_OFFSET_Y;
				float y1 = ((float)ad[i] - midAd) / DIFF_RANGE * midH + midH - VIEW_OFFSET_Y;

				canvas.drawLine(x0, y0, x1, y1, paint);
				canvas.drawCircle(x0, y0, 10, paint);
				if(i == count-1)
					canvas.drawCircle(x1, y1, 10, paint);
			}

			paint.setColor(Color.BLUE);
			for(int i=1; i<count; i++) {
				float x0 = (float)(i-1)*w/MAX_AD_COUNT + VIEW_OFFSET_X;
				float x1 = (float)i*w/MAX_AD_COUNT + VIEW_OFFSET_X;
				float y0 = ((float)adRaw[i-1] - midAd) / DIFF_RANGE * midH + midH - VIEW_OFFSET_Y;
				float y1 = ((float)adRaw[i] - midAd) / DIFF_RANGE * midH +midH - VIEW_OFFSET_Y;

				canvas.drawLine(x0, y0, x1, y1, paint);
				canvas.drawCircle(x0, y0, 10, paint);
				if(i == count-1)
					canvas.drawCircle(x1, y1, 10, paint);
			}

			String strMid = "" + midAd;
			String strHalfHigh = "" + (midAd+DIFF_RANGE/2);
			String strHalfLow = "" + (midAd-DIFF_RANGE/2);

			paint.setTextSize(30.0f);
			paint.setColor(Color.BLACK);

			//String strRef = "" + refAd;
			//canvas.drawText(strRef, 2, refY, paint);

			String strRaw = "" + midRaw;
			canvas.drawText(strRaw, 2, rawY, paint);

			canvas.drawLine(0, midH - VIEW_OFFSET_Y, 90, midH - VIEW_OFFSET_Y, paint);
			canvas.drawText(strMid, 2, midH+2 - VIEW_OFFSET_Y, paint);

			canvas.drawLine(0, midH*1.5f - VIEW_OFFSET_Y, 90, midH*1.5f - VIEW_OFFSET_Y, paint);
			canvas.drawText(strHalfHigh, 2, midH*1.5f+2 - VIEW_OFFSET_Y, paint);

			canvas.drawLine(0, midH*0.5f - VIEW_OFFSET_Y, 90, midH*0.5f - VIEW_OFFSET_Y, paint);
			canvas.drawText(strHalfLow, 2, midH*0.5f+2 - VIEW_OFFSET_Y, paint);

			String values = " " + mWeight + ", " + ad[count-1] + ", " + adRaw[count-1];//String.format("%d, %d", ad[count-1], adRaw[count-1]);
			mValuesView.setText(values);
		}
	}

	private void showError(String title, String msg) {
		android.content.DialogInterface.OnClickListener c = new android.content.DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				AdViewActivity.this.finish();
			}
		};

		new AlertDialog.Builder(this).setTitle(title).setMessage(msg)
				.setPositiveButton("确定", c).show();
	}

	private static final String SVR_NAME = "minipos.escale";

	private void bindIWeight() {
		try {
			IBinder b = ServiceManager.getService(SVR_NAME);
			if(b == null)
				throw new RemoteException("failed to connect to " + SVR_NAME);
			iWeight = IScaleService.Stub.asInterface(b);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onDestroy() {
		exit();
		super.onDestroy();
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		//getMenuInflater().inflate(R.menu.ad_view, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
}
