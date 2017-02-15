package app1.com;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.logentries.android.AndroidLogger;

public class AppIntentService extends IntentService{
	
	public static final String ACTION_AppIntentService = "app1.com.RESPONSE";
	public static final String EXTRA_KEY_OUT = "EXTRA_OUT";
	public static final String TEXT_INPUT = "inText";
	
	AndroidLogger logger = AndroidLogger.getLogger(getBaseContext(), "637a335a-8a6a-40d5-a44e-579da6cf711d", false);
																	  
	public AppIntentService(){
		super("app1.com.AppIntentService");
	}
	
	@Override
	protected void onHandleIntent(Intent intent){ 
		long start1 = System.currentTimeMillis();
		String a = intent.getStringExtra(TEXT_INPUT);
		int b = Integer.parseInt(a);
		
		int [][] ans = Computation.matrice(b,b);
		
		//time stamp for the start of Computation class
		long start = System.nanoTime();
		String k = Computation.toString(ans);
		//time stamp for the end of Computation class
		long end = System.nanoTime();
		
		
		Intent intentResponse = new Intent();
		intentResponse.setAction(ACTION_AppIntentService);
		//intentResponse.addCategory(Intent.CATEGORY_DEFAULT);
		intentResponse.putExtra(EXTRA_KEY_OUT, k);
		LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intentResponse);	
		
		long end1 = System.currentTimeMillis();
		long T = end-start;
		long Tm = (end-start)/1000000;
		long t = end1 - start1;
		double tS = (end1-start1)/1000;
				logger.info("With parameter: "+b+" Local Computation time: "+(T)+ " nS or "+(Tm)+" mS. Local service time: " +t+" mS or " +tS+" S");
	}
		
}
