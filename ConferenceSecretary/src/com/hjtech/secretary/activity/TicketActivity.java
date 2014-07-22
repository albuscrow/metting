package com.hjtech.secretary.activity;

import com.hjtech.secretary.R;

import android.app.Activity;
import android.os.Bundle;

public class TicketActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initUI();
	}

	private void initUI() {
		this.setContentView(R.layout.activity_ticket);
	}
}
