package im.gtm.dust;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Date;
import java.util.Map;

public class NotificationsService extends FirebaseMessagingService 
{
  private final String TAG = NotificationsService.class.getSimpleName();

  public NotificationsService() {
  }

  @Override
  public void onMessageReceived(RemoteMessage remoteMessage) {
    super.onMessageReceived(remoteMessage);
    Map<String,String> kv = remoteMessage.getData();
    String title = kv.get("title"),body = kv.get("body");
    long time = Long.parseLong(kv.get("time"));
    Log.i(TAG, String.format("notification received: %s, %s, %s", new Date(time), title, body));
    for (String k : kv.keySet()) {
      Log.i(TAG, k+"="+kv.get(k));
    }
  }
}
