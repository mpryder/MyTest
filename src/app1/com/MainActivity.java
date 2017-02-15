package app1.com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.net.URL;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("unused")
public class MainActivity extends Activity{

	private TextView textView1;
	static TextView textView2;
	static EditText text;
	
	private static String logtag = "MainActivity";
	AnswerReceiver mReceiver;
	IntentFilter mFilter;
	
	AnswerReceiver mReceiver2;
	IntentFilter mfilter2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textView1 = (TextView) findViewById(R.id.textView1);
		textView2 = (TextView) findViewById(R.id.textView2);
		text = (EditText) findViewById(R.id.editText1);
	
		
		Button buttonStart = (Button)findViewById(R.id.buttonStart);
		buttonStart.setOnClickListener(startListener);
		
		Button buttonStop = (Button)findViewById(R.id.buttonStop);
		buttonStop.setOnClickListener(stopListener);
		
		Button buttonReset = (Button)findViewById(R.id.button1);
		buttonReset.setOnClickListener(resetListener);
		
		mReceiver = new AnswerReceiver();
		mFilter = new IntentFilter(WebService.BROADCAST_ACTION);
		LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mReceiver, mFilter);
		
		mReceiver2 = new AnswerReceiver();
		mfilter2 = new IntentFilter(AppIntentService.ACTION_AppIntentService);
		LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mReceiver2, mfilter2);
		
	}
	
	@Override 
	protected void onDestroy(){
		super.onDestroy();
		unregisterReceiver(mReceiver);
		unregisterReceiver(mReceiver2);
	}
	
	private OnClickListener startListener = new OnClickListener(){
		public void onClick(View v){
			String inputText = text.getText().toString();
			
			Intent intentAppIntentService = new Intent(MainActivity.this, AppIntentService.class); 
			intentAppIntentService.putExtra(AppIntentService.TEXT_INPUT, inputText);
			startService(intentAppIntentService);
			
		}
	};
	
	private OnClickListener stopListener = new OnClickListener(){
		public void onClick(View v){
			String inputText = text.getText().toString();
			
			Intent intentWebService = new Intent(MainActivity.this, WebService.class);
			intentWebService.putExtra(WebService.TEXT_INPUT,  inputText);
			startService(intentWebService);
			//Toast.makeText(MainActivity.this, "Running on server.", Toast.LENGTH_LONG).show();
			}
	};
	
	private OnClickListener resetListener = new OnClickListener(){
		public void onClick(View v){
			textView2.setText(" ");
			}
	};
	
	public class myBroadcastReceiver extends BroadcastReceiver{
		public static final String PROCESS_RESPONSE = "app1.com.intent.action.PROCESS_RESPONSE";
		@Override
		public void onReceive(Context context, Intent intent){
			String result = intent.getStringExtra(AppIntentService.EXTRA_KEY_OUT);
			textView2.setText(result);
		}
	}
	
	private class AnswerReceiver extends BroadcastReceiver{
		public static final String PROCESS_RESPONSE = "app1.com.intent.action.PROCESS_RESPONSE"; 
        @Override
        public void onReceive(Context context, Intent intent) {
            String answer = intent.getStringExtra(WebService.EXTRA_KEY_OUT);
            textView2.setText(answer);
    		
        }
    }
	
}
