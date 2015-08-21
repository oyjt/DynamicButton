package com.myexample.dynamicbutton;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity implements DynamicButton.OnSendClickListener {

	private EditText etComment;
	private DynamicButton btnSendComment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		etComment = (EditText)findViewById(R.id.etComment);
		btnSendComment = (DynamicButton) findViewById(R.id.btnSendComment);
		btnSendComment.setOnSendClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onSendClickListener(View v) {
		if (validateComment()) {
			etComment.setText(null);
			btnSendComment.setCurrentState(DynamicButton.STATE_DONE);
		}
	}
	
	 private boolean validateComment() {
	        if (TextUtils.isEmpty(etComment.getText())) {
	            btnSendComment.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_error));
	            return false;
	        }

	        return true;
	    }
}
