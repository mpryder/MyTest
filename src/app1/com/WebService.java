package app1.com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.logentries.android.AndroidLogger;

public class WebService extends IntentService{
	public static final String BROADCAST_ACTION = "app1.com.BROADCAST";
	public static final String SERVER_URL = "http://c9a9c7380f4e4fd482102c8a3fc2d32a.cloudapp.net/ServiceApp";
	public static final String EXTRA_KEY_OUT = "EXTRA_OUT";
	public static final String TEXT_INPUT = "inText";
	AndroidLogger logger = AndroidLogger.getLogger(getBaseContext(), "637a335a-8a6a-40d5-a44e-579da6cf711d", false);
	    															 
	public WebService(){
		super("app1.com.WebService");
		
	}
	
	@Override
	protected void onHandleIntent (Intent intent){
		//start of Web Service in milliseconds
		long start2 = System.currentTimeMillis();
		
		String host = SERVER_URL + "/myServlet"; 
		String a = intent.getStringExtra(TEXT_INPUT);
		
		//complete url with query string where java servlet is hosted
		String url = host + "?param1="+a;
		try{
			String answer = downloadUrl(url);
			
			//end of Web Service in milliseconds
			long end2 = System.currentTimeMillis();
			
			//Total time taken to be logged on log logentries website
			long t = end2 - start2;
			
			//Total time in seconds
	        double tS = (end2 - start2)/1000;
	        
	        //Time stamp logged
	        logger.info("With parameter: "+a+" Web service time:" +t+" mS or "+tS+" S");
	        
	        //answer from servlet broadcast or sent to Main Activity
			Intent broadcastIntent = new Intent(BROADCAST_ACTION);
			broadcastIntent.putExtra(EXTRA_KEY_OUT, answer);
			LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(broadcastIntent);
		}catch(IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
		}   	
	}
		
	//Opens url connection to download answer from servlet
		protected String downloadUrl(String strUrl) throws IOException{
	        String data = "";
	        InputStream iStream = null;
	        HttpURLConnection urlConnection = null;
	       
	        try{
	            URL url = new URL(strUrl);
	            urlConnection = (HttpURLConnection) url.openConnection();
	            urlConnection.connect();
	 
	            iStream = urlConnection.getInputStream();
	 
	            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
	            StringBuffer sb = new StringBuffer();
	            String line = " ";
	            while( ( line = br.readLine()) != null){
	                sb.append(line).append("\r\n");
	                
	            }
	            data = sb.toString();
	            br.close();	 
	        }catch(Exception e){
	            Log.d("Exception while fetching data", e.toString());
	        }finally{
	            iStream.close();
	            urlConnection.disconnect();
	        }
	        return data;
	}
}
