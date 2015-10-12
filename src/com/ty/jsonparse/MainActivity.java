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
		//Ana thread i�inde network i�lemleri yap�ld��� i�in olu�maktad�r.(android.os.NetworkOnMainThreadException Hatas� ��z�m�)
				if (android.os.Build.VERSION.SDK_INT > 11) {
					StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
					StrictMode.setThreadPolicy(policy);
					}
//				 Httppost i�lemleri i�in NameValuePair listesi gereklidir. Post edilirken header a nelerin Post edilece�i liste olarak g�nderilir
//				 ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//				 nameValuePairs.add(new BasicNameValuePair("email","ty@ty.com")); 
//				 nameValuePairs.add(new BasicNameValuePair("password", "123123"));
//				 wb.webService(gu.url+"get_tag_index","POST",nameValuePairs,MainActivity.class);
				
				//a�a��da sadece veri �ekme i�lemi ger�ekle�tirilmi�tir.
			    wb.webService(gu.url+"get_tag_index","GET",null,MainActivity.class);
			    aDialog();
			    
			    //Parse i�lemleri
				try {
					//URL den gelen JSON de�eri String bir de�ere atand�. String de�er JSON Objesine Parse edildi
					 JSONObject jObj = new JSONObject(wb.mesajlar);
					 //JSON objesinin i�inde String olarak tan�mlanm�� status eleman�n�n de�erine ula�t�k.
					 String status = jObj.getString("status");
					 //Log da gelen de�eri ekrana bast�k
					 Log.i("Gelen Status De�eri", status);
					 
					 //Parse etti�imiz objenin i�inde JSONArray bulunmakta ona eri�mek i�in de Objeji Array e Parse etmemiz gerekmektedir.
					 //Array'in ad� "tags"
					 JSONArray jArray = jObj.getJSONArray("tags");
					 //Array in de�erini Log da bast�k
					 Log.i("Gelen Array De�eri", jArray.toString());
					 
					 // Array in i�indeki elemana eri�ebilmek i�in Array i tekrar Obje olarak Parse etmek gerekiyor.
					 //jArray.getJSONObject(0); buradaki "0" de�eri Array 'in ilk eleman�d�r. D�ng� i�inde di�er Array elemanlar�n�n de�erlerine de ula��labilir.
					 JSONObject jArrayObj = jArray.getJSONObject(0);
					 //Array in i�ine ula�t�k. Obje olarak Parse ettik sadece i�indeki istedi�imiz de�eri �a��rmak kald�.
					 //jArrayObj.getString("id") Array in i�indeki "id" de�erine ula�t�k
					 Log.i("Gelen Array Id De�eri", jArrayObj.getString("id"));
					 
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
