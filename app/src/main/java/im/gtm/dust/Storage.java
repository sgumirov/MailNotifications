package im.gtm.dust;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by phoenix on 2/7/17.
 */

public class Storage {

  private String tableName = "notifications";
  private SQLiteDatabase myDB;

  void init(Context context){
    try {
      myDB = context.openOrCreateDatabase("DatabaseName", MODE_PRIVATE, null);

      /* Create a Table in the Database. */
      myDB.execSQL("CREATE TABLE IF NOT EXISTS "
          + tableName
          + " (recvd INT(8), title VARCHAR, body VARCHAR);");
    }catch (Exception e){}
  }

  public void insert(long time, String title, String body){
    myDB.execSQL(String.format(("INSERT INTO "
        + tableName
        + " (recvd, title, body)"
        + " VALUES (%d, %s, %s);"), time, title, body));

  }

  public ArrayList<Record> select(){
    Cursor c = myDB.rawQuery("SELECT * FROM " + tableName, null);

    int rcvI = c.getColumnIndex("recvd");
    int titleI = c.getColumnIndex("title");
    int bodyI = c.getColumnIndex("body");

    c.moveToFirst();
    ArrayList<Record> list = new ArrayList<>();
    if (c != null) {
      do {
        long date = c.getLong(rcvI);
        String title = c.getString(titleI);
        String body = c.getString(bodyI);
        list.add(new Record(date, title, body));
      } while (c.moveToNext());
    }
    return list;
  }
}

class Record{
  public long date;
  public String title, body;

  public Record(long date, String title, String body) {
    this.date = date;
    this.title = title;
    this.body = body;
  }
}
