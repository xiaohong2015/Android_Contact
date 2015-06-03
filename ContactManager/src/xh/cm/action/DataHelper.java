package xh.cm.action;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHelper extends SQLiteOpenHelper{

	private final static String name= "contactManage.db";
	private final static int version= 1;
	
	public DataHelper(Context context, CursorFactory factory) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String[] sql= new String[] {
			"create table if not exists contact(_id integer primary key autoincrement"
				+ ", name varchar"
				+ ", mark varchar"
				+ ", isFavorite int"
				+ ", phone varchar);"
			,
			
			"create table if not exists message(_id integer primary key autoincrement"
				+ ", content varchar"
				+ ", createTime varchar"
				+ ", phone varchar);"
		};
		String[] init= new String[] {
			"insert into contact(_id, name, phone) values(1, '小洪', '15766227845');",
			"insert into contact(_id, name, phone) values(2, '乔恩', '36363636363');",
			"insert into contact(_id, name, phone) values(3, '小艺', '13450196860');"
		};
		
		for(String s: sql) {
			db.execSQL(s);
		}
		for(String s: init) {
			db.execSQL(s);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		System.out.println("no working");
	}
}
