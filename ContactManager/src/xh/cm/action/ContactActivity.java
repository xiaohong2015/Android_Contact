package xh.cm.action;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ContactActivity extends Activity {

	private SQLiteDatabase db;
	private int _id;	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact);
		
		Bundle b= getIntent().getBundleExtra("b");
		_id= b.getInt("_id");
		
		this.loadId(_id);
		this.bindListener();
		
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(1, Menu.FIRST+0, 1, "编辑");
		menu.add(1, Menu.FIRST+1, 1, "删除");
		menu.add(1, Menu.FIRST+2, 1, "返回");
		
		return true;
	}
	
	private EditText et;
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//return super.onOptionsItemSelected(item);
		switch(item.getItemId()) {
			case 1: {
				if(item.getTitle().equals("编辑")) {
					item.setTitle("保存");
		    		et= (EditText) findViewById(R.id._id);
		    		et.setEnabled(true);
			    	et= (EditText) findViewById(R.id.name);
		    		et.setEnabled(true);
			    	et= (EditText) findViewById(R.id.mark);
		    		et.setEnabled(true);
			    	et= (EditText) findViewById(R.id.isFavorite);
		    		et.setEnabled(true);
			    	et= (EditText) findViewById(R.id.phone);
		    		et.setEnabled(true);
				} else {
					item.setTitle("编辑");
					ContentValues cv= new ContentValues();
		    		et= (EditText) findViewById(R.id._id);
		    		_id= Integer.parseInt(et.getText().toString());
			    	et.setEnabled(false);
			    	
			    	et= (EditText) findViewById(R.id.name);
					cv.put("name", et.getText().toString());
			    	et.setEnabled(false);
			    	
			    	et= (EditText) findViewById(R.id.mark);
					cv.put("mark", et.getText().toString());
			    	et.setEnabled(false);
			    	
			    	et= (EditText) findViewById(R.id.isFavorite);
					cv.put("isFavorite", et.getText().toString());
			    	et.setEnabled(false);
			    	
			    	et= (EditText) findViewById(R.id.phone);
					cv.put("phone", et.getText().toString());
			    	et.setEnabled(false);

			    	db= new DataHelper(getApplicationContext(), null).getWritableDatabase();
			    	db.update("contact", cv, "_id=?", new String[]{String.valueOf(_id)});
				}
			} break;
			
			case 2: {
				new AlertDialog.Builder(ContactActivity.this).setTitle("确定要删除吗?")
					.setIcon(R.drawable.delete)
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						
						public void onClick(DialogInterface dialog, int which) { 
							db= new DataHelper(getApplicationContext(), null).getWritableDatabase();
					    	db.delete("contact", "_id=?", new String[]{String.valueOf(_id)});
					    	

					    	Intent intent= new Intent();
					    	intent.setClassName(getApplicationContext(), "xh.cm.action.ContactManageActivity");
					    	startActivity(intent);
					    	ContactActivity.this.finish();
						}
					})
					.setNegativeButton("取消", null)
					.show();
			} break;
			case 3: {
				Intent intent= new Intent();
				intent.setClassName(getApplicationContext(), "xh.cm.action.ContactManageActivity");
				
				startActivity(intent);
				ContactActivity.this.finish();
			} break;
		}
		return true;
	}
	
    public void loadId(int _id) {
    	
    	db= new DataHelper(this, null).getReadableDatabase();
    	//Cursor c= db.rawQuery("select * from contact where _id=?", new String[]{String.valueOf(_id)});
    	Cursor c= db.query("contact", null, "_id=?", new String[]{String.valueOf(_id)}, null, null, null);
    	
    	if(c.moveToFirst()) {
	    	
    		et= (EditText) findViewById(R.id._id);    		
	    	et.setText(String.valueOf(c.getInt(c.getColumnIndex("_id"))));
	    	et.setEnabled(false);
	    	et.setTextColor(Color.GREEN);
	    	
	    	et= (EditText) findViewById(R.id.name);
	    	et.setText(c.getString(c.getColumnIndex("name")));
	    	et.setEnabled(false);
	    	et.setTextColor(Color.GREEN);
	    	
	    	et= (EditText) findViewById(R.id.mark);
	    	et.setText(c.getString(c.getColumnIndex("mark")));
	    	et.setEnabled(false);
	    	et.setTextColor(Color.GREEN);

	    	et= (EditText) findViewById(R.id.isFavorite);
	    	String isF= (c.getInt(c.getColumnIndex("isFavorite"))== 1)?"收藏":"";
	    	et.setText(isF);
	    	et.setEnabled(false);
	    	et.setTextColor(Color.GREEN);
	    	
	    	et= (EditText) findViewById(R.id.phone);
	    	et.setText(c.getString(c.getColumnIndex("phone")));
	    	et.setEnabled(false);
	    	et.setTextColor(Color.GREEN);
	    	
    	}
    	
    }

    public void bindListener() {

    	Button btn2= (Button) findViewById(R.id.send);
    	btn2.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent= new Intent();
				intent.setClassName(getApplicationContext(), "xh.cm.action.MessageActivity");
				
				Bundle b= new Bundle();
				et= (EditText) findViewById(R.id.phone);
				b.putString("phone", et.getText().toString());
				b.putInt("_id", _id);
				intent.putExtra("b", b);
				
				ContactActivity.this.startActivity(intent);
			}
		});
    	
    	Button btn4= (Button) findViewById(R.id.call);
    	btn4.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				et= (EditText) findViewById(R.id.phone);
				Intent intent= new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ et.getText()));
				startActivity(intent);
			}
		});
    }

}
