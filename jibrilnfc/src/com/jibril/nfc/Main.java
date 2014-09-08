package com.jibril.nfc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.StrictMode;
import android.provider.Settings.Secure;
import android.speech.tts.TextToSpeech;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jibril.nfc.R;
import com.jibril.nfc.UserEmailFetcher;


@SuppressLint({ "ParserError", "ParserError" })
public class Main extends Activity{
	TextToSpeech ttobj;
	TextToSpeech tts;
	TextView idtv, usernametv, passwordtv, mobilenotv, emailtv, fullnametv, androididtv, simidtv, imeitv, datetv;
	TextView amounttv, lrt1tv, lrt2tv, chargetv, balancetv;
	EditText etResponse;
	String ip = "192.168.0.101";
	private String rt;

	NfcAdapter adapter;
	PendingIntent pendingIntent;
	IntentFilter writeTagFilters[];
	boolean writeMode;
	Tag mytag;
	Context ctx;
    IntentFilter[] mNdefExchangeFilters;
    String android_id, simid, imei, phone, email;
	private String serverip;
	EditText serveriptxt;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		
		idtv = (TextView)findViewById(R.id.idtv);
		usernametv = (TextView)findViewById(R.id.usernametv);
		passwordtv = (TextView)findViewById(R.id.passwordtv);
		mobilenotv = (TextView)findViewById(R.id.mobilenotv);
		emailtv = (TextView)findViewById(R.id.emailtv);
		fullnametv = (TextView)findViewById(R.id.fullnametv);
		androididtv = (TextView)findViewById(R.id.androididtv);
		simidtv = (TextView)findViewById(R.id.simidtv);
		imeitv = (TextView)findViewById(R.id.imeitv);
		datetv = (TextView)findViewById(R.id.datetv);
		amounttv = (TextView)findViewById(R.id.amounttv);		
		lrt1tv = (TextView)findViewById(R.id.lrt1tv);
		lrt2tv = (TextView)findViewById(R.id.lrt2tv);
		chargetv = (TextView)findViewById(R.id.chargetv);
		balancetv = (TextView)findViewById(R.id.balancetv);

		
		etResponse = (EditText) findViewById(R.id.etResponse);
		
		android_id = Secure.getString(getContentResolver(), Secure.ANDROID_ID);        
        TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        simid =tm.getSimSerialNumber();
        imei = tm.getDeviceId();
        phone = tm.getLine1Number();
        
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy); 
        email = UserEmailFetcher.getEmail(getApplicationContext());
        
        serveriptxt = (EditText) findViewById(R.id.serveriptxt);
        serveriptxt.setText(ip);
        
        
        

		ctx=this;
		Button btnWrite = (Button) findViewById(R.id.button);
		final TextView message = (TextView)findViewById(R.id.serveriptxt);

		btnWrite.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				try {
					if(mytag==null){
						Toast.makeText(ctx, ctx.getString(R.string.error_detected), Toast.LENGTH_LONG ).show();
					}else{
						//TODO WITE NFC
						write(message.getText().toString(),mytag);
						Toast.makeText(ctx, ctx.getString(R.string.ok_writing), Toast.LENGTH_LONG ).show();
					}
				} catch (IOException e) {
					Toast.makeText(ctx, ctx.getString(R.string.error_writing), Toast.LENGTH_LONG ).show();
					e.printStackTrace();
				} catch (FormatException e) {
					Toast.makeText(ctx, ctx.getString(R.string.error_writing) , Toast.LENGTH_LONG ).show();
					e.printStackTrace();
				}
			}
		});

		adapter = NfcAdapter.getDefaultAdapter(this);
		pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
		tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
		writeTagFilters = new IntentFilter[] { tagDetected };

        // Intent filters for reading a note from a tag or exchanging over p2p.
        IntentFilter ndefDetected = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            ndefDetected.addDataType("text/plain");
        } catch (MalformedMimeTypeException e) { }
        mNdefExchangeFilters = new IntentFilter[] { ndefDetected };
        
        /**
        Button registerbtn = (Button) findViewById(R.id.registerbtn);
        registerbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Toast.makeText(ctx, "you click register", Toast.LENGTH_LONG ).show();
				Intent i = new Intent(getApplicationContext(), Register.class);
				startActivity(i);
			}
		});
        */
        
        //Db db = new Db(ctx);
        //User user = db.getUser(1);
        //Log.d("JibrilNFC", "user2name="+user.getFullname());
		
	}

	private void write(String text, Tag tag) throws IOException, FormatException {
		NdefRecord[] records = { createRecord(text) };
		NdefMessage  message = new NdefMessage(records);
		Ndef ndef = Ndef.get(tag);
		ndef.connect();
		ndef.writeNdefMessage(message);
		ndef.close();
	}

	private NdefRecord createRecord(String text) throws UnsupportedEncodingException {
		String lang       = "en";
		byte[] textBytes  = text.getBytes();
		byte[] langBytes  = lang.getBytes("US-ASCII");
		int    langLength = langBytes.length;
		int    textLength = textBytes.length;
		byte[] payload    = new byte[1 + langLength + textLength];

		// set status byte (see NDEF spec for actual bits)
		payload[0] = (byte) langLength;

		// copy langbytes and textbytes into payload
		System.arraycopy(langBytes, 0, payload, 1,              langLength);
		System.arraycopy(textBytes, 0, payload, 1 + langLength, textLength);

		NdefRecord recordNFC = new NdefRecord(NdefRecord.TNF_WELL_KNOWN,  NdefRecord.RTD_TEXT,  new byte[0], payload);

		return recordNFC;
	}

	@Override
	protected void onNewIntent(Intent intent){
        // NDEF exchange mode
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            NdefMessage[] msgs = getNdefMessages(intent);

			NdefMessage[] messages = getNdefMessages(getIntent());
	        byte[] payload = messages[0].getRecords()[0].getPayload();
	        String placeId = new String(payload);
	        Toast.makeText(this, placeId, Toast.LENGTH_LONG).show();
	        
            //promptForContent(msgs[0]);
        }

		if(NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())){
			mytag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);    

			Parcelable[] data = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
			//String s = "NFC text: '";
			String s = "";
			String tagid = ByteArrayToHexString(intent.getByteArrayExtra(NfcAdapter.EXTRA_ID));
	        s+=tagid+"=";
	        if (data != null) {
	            try {
	                for (int i = 0; i < data.length; i++) {
	                    NdefRecord [] recs = ((NdefMessage)data[i]).getRecords();
	                    for (int j = 0; j < recs.length; j++) {
	                        if (recs[j].getTnf() == NdefRecord.TNF_WELL_KNOWN &&
	                            Arrays.equals(recs[j].getType(), NdefRecord.RTD_TEXT)) {
	                            byte[] payload = recs[j].getPayload();
	                            String textEncoding = ((payload[0] & 0200) == 0) ? "UTF-8" : "UTF-16";
	                            int langCodeLen = payload[0] & 0077;
	 
	                            s += new String(payload, langCodeLen + 1, payload.length - langCodeLen - 1, textEncoding);
	                        }
	                    }
	                }
	            } catch (Exception e) {
	                Log.e("TagDispatch", e.toString());
	            }
	        }
	        //s+="'";
	        //TODO READ NFC
	        ///String url = "http://192.168.0.10/android/register.php?mobileno="+phone+"&simid="+simid+"&aid="+android_id+"&imei="+imei+"&email="+email;
	        ///String url = "http://192.168.0.10/nfcserver/transact.php?mobileno="+phone+"&tagid="+tagid;
	        //String url = "http://"+serverip+"/json.php?mobileno="+phone+"&tagid="+tagid;
	        serverip = serveriptxt.getText().toString();
	        String url = "http://"+serverip+":8080/nfc/json.jsp?mobileno="+phone+"&tagid="+tagid;;
	        if(serverip.equals("192.168.0.5")) url = "http://"+serverip+":8080/nfc/json.jsp?mobileno="+phone+"&tagid="+tagid;
	        Log.d("url=", url);
	        ///String rtn = makeGetRequest(url); // getInputStreamFromUrl(url); // 
	        //String rtn = rtn1.toString();
	       /// Log.d("JibrilNfc_url=", url);
	        ///Log.d("JibrilNfc2=", rtn);
	        
			//Toast.makeText(this, this.getString(R.string.ok_detection) + mytag.toString(), Toast.LENGTH_LONG ).show();
			///Toast.makeText(this, s, Toast.LENGTH_LONG ).show();
			///Toast.makeText(this, rtn, Toast.LENGTH_LONG ).show();
			
			
			
			
			///new HttpAsyncTask().execute("http://hmkcode.appspot.com/rest/controller/get.json");
			new HttpAsyncTask().execute(url);
			       
		}
	}
	
	@Override
	public void onPause(){
		super.onPause();
		WriteModeOff();
	}

	@Override
	public void onResume(){
		super.onResume();
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
	        NdefMessage[] messages = getNdefMessages(getIntent());
	        byte[] payload = messages[0].getRecords()[0].getPayload();
	        String placeId = new String(payload);
	        Toast.makeText(this, placeId, Toast.LENGTH_LONG).show();
	    }
		WriteModeOn();
	}
	NdefMessage[] getNdefMessages(Intent intent) {
	    // Parse the intent
	    NdefMessage[] msgs = null;
	    String action = intent.getAction();
	    if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
	        || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
	        Parcelable[] rawMsgs = 
	            intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
	        if (rawMsgs != null) {
	            msgs = new NdefMessage[rawMsgs.length];
	            for (int i = 0; i < rawMsgs.length; i++) {
	                msgs[i] = (NdefMessage) rawMsgs[i];
	            }
	        } else {
	            // Unknown tag type
	            byte[] empty = new byte[] {};
	            NdefRecord record = 
	                new NdefRecord(NdefRecord.TNF_UNKNOWN, empty, empty, empty);
	            NdefMessage msg = new NdefMessage(new NdefRecord[] {
	                record
	            });
	            msgs = new NdefMessage[] {
	                msg
	            };
	        }
	    } else {
	        Log.d("eroare", "Unknown intent.");
	        finish();
	    }
	    return msgs;
	}
	private void WriteModeOn(){
		writeMode = true;
        //adapter.enableForegroundNdefPush(MainActivity.this, getNoteAsNdef());
		adapter.enableForegroundDispatch(this, pendingIntent, mNdefExchangeFilters, null);
		adapter.enableForegroundDispatch(this, pendingIntent, writeTagFilters, null);
	}

	private void WriteModeOff(){
		writeMode = false;
		adapter.disableForegroundDispatch(this);
	}
	
	private String ByteArrayToHexString(byte [] inarray) {
	    int i, j, in;
	    String [] hex = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
	    String out= "";
	
	    for(j = 0 ; j < inarray.length ; ++j) 
	        {
	        in = (int) inarray[j] & 0xff;
	        i = (in >> 4) & 0x0f;
	        out += hex[i];
	        i = in & 0x0f;
	        out += hex[i];
	        }
	    return out;
	}
	
    private void makePostRequest() {

		HttpClient httpClient = new DefaultHttpClient();

		HttpPost httpPost = new HttpPost("www.example.com"); // replace with
																// your url

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);

		nameValuePair.add(new BasicNameValuePair("username", "test_user"));

		nameValuePair.add(new BasicNameValuePair("password", "123456789"));

		// Encoding data

		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
		} catch (UnsupportedEncodingException e) {
			// log exception
			e.printStackTrace();
		}

		// making request

		try {
			HttpResponse response = httpClient.execute(httpPost);
			// write response to log
			Log.d("Http Post Response:", response.toString());
		} catch (ClientProtocolException e) {
			// Log exception
			e.printStackTrace();
		} catch (IOException e) {
			// Log exception
			e.printStackTrace();
		}

	}
    
    private String makeGetRequest(String url) {
    	String rtn = "";
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		HttpResponse response;
		try {
			response = client.execute(request);
			rtn = response.toString();
			Log.d("Response of GET request", response.toString());
		} catch (ClientProtocolException e) {
			rtn = e.toString();
			e.printStackTrace();
		} catch (IOException e) {
			rtn = e.toString();
			e.printStackTrace();
		}
		
		return rtn;

	}
    
    public static InputStream getInputStreamFromUrl(String url) {
    	  InputStream content = null;
    	  try {
    	    HttpClient httpclient = new DefaultHttpClient();
    	    HttpResponse response = httpclient.execute(new HttpGet(url));
    	    content = response.getEntity().getContent();
    	    Log.d("return=", content.toString());
    	  } catch (Exception e) {
    	    Log.d("[GET REQUEST]", "Network exception", e);
    	  }
    	    return content;
    	}


    
    
    
    
    
    
    
    
    
    
    
    public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {
 
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
 
            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
 
            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
 
            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
 
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
 
        return result;
    }
 
    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
 
        inputStream.close();
        return result;
 
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
 
            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
            
            Gson gson = new Gson();
            
            String rt = result.substring(4);
            
    	    User user2 = gson.fromJson(rt, User.class);
    	    
    	    amounttv.setText("Amount: "+ user2.getAmount());
    	    lrt1tv.setText("LRT 1: "+ user2.getLrt1());
    	    lrt2tv.setText("LRT 2: "+ user2.getLrt2());
    	    chargetv.setText("Charged: "+ user2.getCharge());
    	    balancetv.setText("Balance: "+ user2.getBalance());
    	    
    	    idtv.setText("UserID: " + user2.getId());
            usernametv.setText("Username: "+ user2.getUsername());
            passwordtv.setText("Password: " + user2.getPassword());
            mobilenotv.setText("Mobile No: " + user2.getMobileno());
            emailtv.setText("Email: " + user2.getEmail());
            fullnametv.setText("Fullname: " + user2.getFullname());
            androididtv.setText("Android ID: " + user2.getAndroidid());
            simidtv.setText("Sim No: " + user2.getSimid());
            imeitv.setText("IMEI No: " + user2.getImei());
            datetv.setText("Date Register: " + user2.getDatereg());
            
            tts = new TextToSpeech(ctx, null);
            tts.speak(user2.getUsername()+"Thank you for using Jibril NFC", TextToSpeech.QUEUE_FLUSH, null);
            
            //ttobj.speak("Thank you for using Jibril Mobile NFC", TextToSpeech.QUEUE_FLUSH, null);
       }
    }
 
}