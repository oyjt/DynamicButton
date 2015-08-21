package com.myexample.dynamicbutton;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewAnimator;

/**
 * Created by ouyang on 29.5.15.
 */
public class DynamicButton extends ViewAnimator implements View.OnClickListener {
	public static final int STATE_SEND = 0;
	public static final int STATE_DONE = 1;
	public static final int STATE_SENDING = 2;
	public static final int STATE_ENABLE = 3;
	public static final int STATE_DISABLE = 4;

	private static final long RESET_STATE_DELAY_MILLIS = 2000;

	private int currentState;

	private OnSendClickListener onSendClickListener;

	private TextView tvSend;
	
	private String textSending;
	private String textSend;

	private Runnable revertStateRunnable = new Runnable() {
		@Override
		public void run() {
			setCurrentState(STATE_SEND);
		}
	};

	public DynamicButton(Context context) {
		super(context);
		init(null);
	}

	public DynamicButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SynamicButton);
		init(a);
	}

	private void init(TypedArray a) {
		LayoutInflater.from(getContext()).inflate(R.layout.view_dynamic_button, this, true);
		tvSend = (TextView) findViewById(R.id.tvSend);
		TextView tvDone = (TextView) findViewById(R.id.tvDone);

		if (a != null) {
			ColorStateList textColor = a.getColorStateList(R.styleable.SynamicButton_textColor);
			float textSize = a.getDimensionPixelSize(R.styleable.SynamicButton_textSize, 20);
			String textDone = a.getString(R.styleable.SynamicButton_textDone);
			textSend = a.getString(R.styleable.SynamicButton_textSend);
			textSending = a.getString(R.styleable.SynamicButton_textSending);

			tvSend.setTextColor(textColor != null ? textColor : ColorStateList.valueOf(0xFF000000));
			tvSend.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
			if (!TextUtils.isEmpty(textSend)) {
				tvSend.setText(textSend);
			}
			tvDone.setTextColor(textColor != null ? textColor : ColorStateList.valueOf(0xFF000000));
			tvDone.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
			if (!TextUtils.isEmpty(textDone)) {
				tvDone.setText(textDone);
			}

			a.recycle();
		}
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		currentState = STATE_SEND;
		super.setOnClickListener(this);
	}

	@Override
	protected void onDetachedFromWindow() {
		removeCallbacks(revertStateRunnable);
		super.onDetachedFromWindow();
	}

	public void setCurrentState(int state) {
		if (state == currentState) {
			return;
		}

		currentState = state;
		switch (state) {
		case STATE_DONE:
			setEnabled(false);
			postDelayed(revertStateRunnable, RESET_STATE_DELAY_MILLIS);
			setInAnimation(getContext(), R.anim.slide_in_done);
			setOutAnimation(getContext(), R.anim.slide_out_send);
			break;
		case STATE_SEND:
			if (!TextUtils.isEmpty(textSend)&&!TextUtils.equals(tvSend.getText(), textSending)) {
				tvSend.setText(textSend);
			}
			setEnabled(true);
			setInAnimation(getContext(), R.anim.slide_in_send);
			setOutAnimation(getContext(), R.anim.slide_out_done);
			break;
		case STATE_SENDING:
			setEnabled(false);
			tvSend.setText(textSending);
			break;
		case STATE_ENABLE:
			setEnabled(true);
			if (tvSend.getVisibility() == GONE) {
				tvSend.setVisibility(VISIBLE);
			}
			break;
		case STATE_DISABLE:
			setEnabled(false);
			if (tvSend.getVisibility() == VISIBLE) {
				tvSend.setVisibility(GONE);
			}
			break;
		default:
			break;
		}
		showNext();
	}

	@Override
	public void onClick(View v) {
		if (onSendClickListener != null) {
			onSendClickListener.onSendClickListener(this);
		}
	}

	public void setOnSendClickListener(OnSendClickListener onSendClickListener) {
		this.onSendClickListener = onSendClickListener;
	}

	@Override
	public void setOnClickListener(OnClickListener l) {
		// Do nothing, you have you own onClickListener implementation
		// (OnSendClickListener)
	}

	public interface OnSendClickListener {
		public void onSendClickListener(View v);
	}
}