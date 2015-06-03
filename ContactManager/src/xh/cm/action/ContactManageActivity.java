package xh.cm.action;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ContactManageActivity extends Activity {
	/** Called when the activity is first created. */
	
	private SQLiteDatabase db;
	private static int dbFlag= 0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //this.init("data/data/xh.cm.action/databases/contactManage.db");
        this.listContact((ListView) findViewById(R.id.contactView)); 
        
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
    		ContextMenuInfo menuInfo) {
    	super.onCreateContextMenu(menu, v, menuInfo);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	menu.add(1, Menu.FIRST+0, 1, "添加");
    	menu.add(1, Menu.FIRST+1, 1, "退出");
    	return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	//super.onOptionsItemSelected(item);
    	switch(item.getItemId()) {
	    	case 1: {	
				Intent intent= new Intent();
				intent.setClassName(getApplicationContext(), "xh.cm.action.ContactAddActivity");
				startActivity(intent);
	    	} break;
	    	case 2: {
	    		this.onDestroy();
	    		this.finish();
	    	}
    	}
    	return true;
    }
    
    public void init(String databases) {
        File f= new File(databases);
        if(f.isFile()&& dbFlag== 0) {
        	f.delete();
        	dbFlag= 1;
        	/*
        	Uri packageUri= Uri.parse("package:xh.cm.action.ContactManageActivity");
        	Intent unIntent= new Intent(Intent.ACTION_DELETE, packageUri);
        	startActivity(unIntent);*/
        }
    }

    private final class listListener implements OnItemClickListener {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			ListView lv= (ListView) parent;
			Cursor c= (Cursor) lv.getItemAtPosition(position);
	        
	        Intent intent= new Intent();
	        intent.setClassName(getApplicationContext(), "xh.cm.action.ContactActivity");
	        Bundle b= new Bundle();
	        b.putInt("_id", c.getInt(c.getColumnIndex("_id")));
	        intent.putExtra("b", b);
	        
	        startActivity(intent);
		}
    	
    }
    public void listContact(ListView tv) {
    	db= new DataHelper(this, null).getReadableDatabase();
    	Cursor c= db.query("contact", null, null, null, null, null, null);
    	//Cursor c= db.rawQuery("select id as _id,  name, phone from contact", null);
		SimpleCursorAdapter adapter= new SimpleCursorAdapter(this, R.layout.contact_list, c
    			, new String[]{"name", "phone"}, new int[]{R.id.name, R.id.phone}, 0);
    	
    	tv.setAdapter(adapter);
    	tv.setOnItemClickListener(new listListener() );
    	
    }
    
}
