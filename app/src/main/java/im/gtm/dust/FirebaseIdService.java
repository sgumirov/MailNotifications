package im.gtm.dust;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FirebaseIdService extends FirebaseInstanceIdService {

  private static final String TAG = FirebaseIdService.class.getSimpleName();
  
  public FirebaseIdService() {
  }

  @Override
  public void onTokenRefresh() {
    String token = FirebaseInstanceId.getInstance().getToken();
    Log.i(TAG, "onTokenRefresh(): token="+token);
    sendToken(token);
  }

  public static void sendToken(String token) {
    String u = "http://im.mad.gd/1.php?token="+token;
    try {
      URL url = new URL(u);
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.connect();
      int code = con.getResponseCode();
      Log.i(TAG, "sendToken: response code="+code+"\n for url="+u);
      con.disconnect();
    } catch (Exception e) {
      Log.e(TAG, "cannot send token to url="+u+": "+e.getMessage(), e);
    }
  }
}
