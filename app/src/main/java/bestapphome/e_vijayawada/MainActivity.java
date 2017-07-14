package bestapphome.e_vijayawada;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clearPreferences();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("validuser", MODE_PRIVATE);
               // Toast.makeText(MainActivity.this,sharedPreferences.getString("name",""),Toast.LENGTH_SHORT).show();
              if ( sharedPreferences.getString("name","").equals("")){
                  Intent i = new Intent(getApplicationContext(),Login.class);
                  startActivity(i);
                  //Do something after 100ms
              }else {
                  Intent ee = new Intent(getApplicationContext(),updatestatus.class);
                  startActivity(ee);
              }
            }
        }, 3000);

    }
    private void clearPreferences() {
        try {
            // clearing app data
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("pm clear YOUR_APP_PACKAGE_GOES HERE");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
