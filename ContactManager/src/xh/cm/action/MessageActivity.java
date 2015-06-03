package xh.cm.action;

import java.util.Date;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MessageActivity extends Activity {

	private SQLiteDatabase db;
	private String phone;
	private int _id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message);
		
		Bundle b= getIntent().getBundleExtra("b");
		phone= b.getString("phone");
		_id= b.getInt("_id");
		
		this.init(phone);
		this.bindListener();
	}
	EditText et;
	public void init(String str) {
		et= (EditText) findViewById(R.id.phone);
		et.setText(str);
		et= (EditText) findViewById(R.id.content);
		et.setFocusable(true);
	}
	public void bindListener() {
		Button btn1= (Button) findViewById(R.id.send);
		btn1.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				et= (EditText) findViewById(R.id.phone);
				String add= et.getText().toString();
				et= (EditText) findViewById(R.id.content);
				String con= et.getText().toString();
				
				//*
				SmsManager sm= SmsManager.getDefault();
				PendingIntent pi= PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(), 0);
				sm.sendTextMessage(add, null, con, pi, null);
				//*/
				/*
				Intent intent= new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+ add));
				intent.putExtra("sms_body", con);
				startActivity(intent);
				//*/
				
				Toast.makeText(getApplicationContext(), "发送成功", Toast.LENGTH_LONG).show();

				ContentValues cv= new ContentValues();
				cv.put("phone", phone);
				cv.put("content", con);
				cv.put("createTime", new Date().toString());
				db = new DataHelper(getApplicationContext(), null).getWritableDatabase();
		    	db.insert("message", null, cv);
		    	
		    	
				Button btn2= (Button) findViewById(R.id.back);
				btn2.setText("返回");
			}
		});
		
		Button btn2= (Button) findViewById(R.id.back);
		btn2.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent= new Intent();
				intent.setClassName(getApplicationContext(), "xh.cm.action.ContactActivity");
				
				Bundle b= new Bundle();
				b.putInt("_id", _id);
				intent.putExtra("b", b);
				
				startActivity(intent);
				MessageActivity.this.finish();
			}
		});
	}
}
