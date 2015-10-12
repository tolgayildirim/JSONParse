package com.ty.jsonparse;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ty.globalurl.GlobalURL;
import com.ty.webservice.WebService;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {
	GlobalURL gu = new GlobalURL();
	WebService wb = new WebService();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//Ana thread içinde network iþlemleri yapýldýðý için oluþmaktadýr.(android.os.NetworkOnMainThreadException Hatasý çözümü)
				if (android.os.Build.VERSION.SDK_INT > 11) {
					StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
					StrictMode.setThreadPolicy(policy);
					}
//				 Httppost iþlemleri için NameValuePair listesi gereklidir. Post edilirken header a nelerin Post edileceði liste olarak gönderilir
//				 ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//				 nameValuePairs.add(new BasicNameValuePair("email","ty@ty.com")); 
//				 nameValuePairs.add(new BasicNameValuePair("password", "123123"));
//				 wb.webService(gu.url+"get_tag_index","POST",nameValuePairs,MainActivity.class);
				
				//aþaðýda sadece veri çekme iþlemi gerçekleþtirilmiþtir.
			    wb.webService(gu.url+"get_tag_index","GET",null,MainActivity.class);
			    aDialog();
			    
			    //Parse iþlemleri
				try {
					//URL den gelen JSON deðeri String bir deðere atandý. String deðer JSON Objesine Parse edildi
					 JSONObject jObj = new JSONObject(wb.mesajlar);
					 //JSON objesinin içinde String olarak tanýmlanmýþ status elemanýnýn deðerine ulaþtýk.
					 String status = jObj.getString("status");
					 //Log da gelen deðeri ekrana bastýk
					 Log.i("Gelen Status Deðeri", status);
					 
					 //Parse ettiðimiz objenin içinde JSONArray bulunmakta ona eriþmek için de Objeji Array e Parse etmemiz gerekmektedir.
					 //Array'in adý "tags"
					 JSONArray jArray = jObj.getJSONArray("tags");
					 //Array in deðerini Log da bastýk
					 Log.i("Gelen Array Deðeri", jArray.toString());
					 
					 // Array in içindeki elemana eriþebilmek için Array i tekrar Obje olarak Parse etmek gerekiyor.
					 //jArray.getJSONObject(0); buradaki "0" deðeri Array 'in ilk elemanýdýr. Döngü içinde diðer Array elemanlarýnýn deðerlerine de ulaþýlabilir.
					 JSONObject jArrayObj = jArray.getJSONObject(0);
					 //Array in içine ulaþtýk. Obje olarak Parse ettik sadece içindeki istediðimiz deðeri çaðýrmak kaldý.
					 //jArrayObj.getString("id") Array in içindeki "id" deðerine ulaþtýk
					 Log.i("Gelen Array Id Deðeri", jArrayObj.getString("id"));
					 
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
	public void aDialog() {
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();

		// Setting Dialog Title
		alertDialog.setTitle("Mesaj");
		alertDialog.setMessage(wb.mesajlar);

		
		// Setting Icon to Dialog

		// Setting OK Button
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// Write your code here to execute after dialog closed
				Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
			}
		});

		// Showing Alert Message
		alertDialog.show();
		
	}
}
