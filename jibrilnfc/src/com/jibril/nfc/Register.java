package com.jibril.nfc;

import com.google.gson.Gson;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends Activity {
	Context ctx;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		
		ctx = this;
		Button savebtn = (Button) findViewById(R.id.savebtn);
		final TextView username = (TextView)findViewById(R.id.usernametxt);
		final TextView password = (TextView)findViewById(R.id.passwordtxt);
		final TextView mobileno = (TextView)findViewById(R.id.mobilenotxt);
		final TextView email = (TextView)findViewById(R.id.emailtxt);
		final TextView fullname = (TextView)findViewById(R.id.fullnametxt);
		
		
		
		savebtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String user1 = username.getText().toString();
				String pass1 = password.getText().toString();
				String mobile = mobileno.getText().toString();
				String mail = email.getText().toString();
				String name = fullname.getText().toString();
				
				User user = new User();
				user.setId(1);
				user.setUsername(user1);
				user.setPassword(pass1);
				user.setMobileno(mobile);
				user.setEmail(mail);
				user.setFullname(name);
				
				Db db = new Db(ctx);
				db.addUser(user);
				
				//User user2 = db.getUser(1);
				
				Gson gson = new Gson();
				String userjson =  gson.toJson(user);
				
				Toast.makeText(ctx, "you click submit:"+name, Toast.LENGTH_LONG ).show();
				Log.d("JibrilNFC", "user2name="+user.getFullname());
				//Intent i = new Intent(getApplicationContext(), Register.class);
				//startActivity(i);
			}
		});
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
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
}
