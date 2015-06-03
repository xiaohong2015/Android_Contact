package xh.cm.action;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ContactAddActivity extends Activity {

	private SQLiteDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_add);
		
		this.bindListener();
	}
	EditText et;
	public void bindListener() {
		Button btn1= (Button) findViewById(R.id.add);
		btn1.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				ContentValues cv= new ContentValues();
				
				et= (EditText) findViewById(R.id.name);
				cv.put("name", et.getText().toString());
				
				et= (EditText) findViewById(R.id.phone);
				cv.put("phone", et.getText().toString());
				
				et= (EditText) findViewById(R.id.mark);
				cv.put("mark", et.getText().toString());
				
				et= (EditText) findViewById(R.id.isFavorite);
				if(et.getText().toString().contains("1")) {
					cv.put("isFavorite", 1);
				}

				db= new DataHelper(getApplicationContext(), null).getWritableDatabase();
		    	db.insert("contact", null, cv);
		    	
		    	
				Button btn2= (Button) findViewById(R.id.back);
				btn2.callOnClick();
			}
		});
		
		Button btn2= (Button) findViewById(R.id.back);
		btn2.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent= new Intent();
				intent.setClassName(getApplicationContext(), "xh.cm.action.ContactManageActivity");
				
				startActivity(intent);
				ContactAddActivity.this.finish();
			}
		});
	}
}
