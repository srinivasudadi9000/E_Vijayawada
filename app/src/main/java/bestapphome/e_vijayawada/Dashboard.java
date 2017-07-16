package bestapphome.e_vijayawada;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import bestapphome.e_vijayawada.json.JSONParser;

public class Dashboard extends Activity {
    ProgressBar progress1, progress2, progress3, progress4, progress5;
    TextView PendingBefore, PendingAfter, RedressedBefore, RedressedAfter, Rejected;
    Handler progressHandler = new Handler();
    int i = 0;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        progress1 = (ProgressBar) findViewById(R.id.progress1);
        progress2 = (ProgressBar) findViewById(R.id.progress2);
        progress3 = (ProgressBar) findViewById(R.id.progress3);
        progress4 = (ProgressBar) findViewById(R.id.progress4);
        progress5 = (ProgressBar) findViewById(R.id.progress5);
        PendingBefore = (TextView) findViewById(R.id.PendingBefore);
        PendingAfter = (TextView) findViewById(R.id.PendingAfter);
        RedressedBefore = (TextView) findViewById(R.id.RedressedBefore);
        RedressedAfter = (TextView) findViewById(R.id.RedressedAfter);
        Rejected = (TextView) findViewById(R.id.Rejected);

      /*  progress = new ProgressDialog(Dashboard.this);
        progress.setMessage("Fetching data from server..");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();
*/

        new Thread(new Runnable() {
            public void run() {
                while (i < 100) {
                    i += 2;
                    progressHandler.post(new Runnable() {
                        public void run() {
                            progress1.setProgress(i);
                            PendingBefore.setText("" + i + " %");
                            progress2.setProgress(i);
                            PendingAfter.setText("" + i + " %");
                            progress3.setProgress(i);
                            RedressedBefore.setText("" + i + " %");
                            progress4.setProgress(i);
                            RedressedAfter.setText("" + i + " %");
                            progress5.setProgress(i);
                            Rejected.setText("" + i + " %");
                        }
                    });
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Toast.makeText(getBaseContext(), "kasldfkasld", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        }).start();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("Userinfo", MODE_PRIVATE);
                new Dashboard.getstatus(sharedPreferences.getString("intofficerid", null)).execute();
                Animation slideUp2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomin);
                PendingBefore.startAnimation(slideUp2);
                PendingAfter.startAnimation(slideUp2);
                RedressedBefore.startAnimation(slideUp2);
                RedressedAfter.startAnimation(slideUp2);
                Rejected.startAnimation(slideUp2);
                //Do something after 100ms
                Toast.makeText(getBaseContext(),"alskdflasd",Toast.LENGTH_SHORT).show();
            }
        }, 3000);


    }

    private class getstatus extends AsyncTask<String, String, JSONObject> {

        private ProgressDialog pDialog;
        private ArrayList<NameValuePair> nameValuePairs;
        private JSONObject json;
        String id;

        public getstatus(String officerid) {
            this.id = officerid;
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... arg0) {
            nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("intOfficerid", id));
            json = JSONParser.makeServiceCall("http://208.78.220.51/VMCGMS/GrievanceDashboardService.aspx", 1, nameValuePairs);

            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            // Toast.makeText(getApplicationContext(), json.toString(), Toast.LENGTH_SHORT).show();
          //  progress.dismiss();
            try {
                JSONArray jsonObject = json.getJSONArray("dashboard");
                for (int i = 0; i < jsonObject.length(); i++) {
                    JSONObject value = jsonObject.getJSONObject(i);
                    //    Toast.makeText(getApplicationContext(), value.getString("intUserid").toString(), Toast.LENGTH_SHORT).show();
                     PendingBefore.setText(value.getString("PendingBefor")+" %");
                    PendingAfter.setText(value.getString("PendingAfter")+" %");
                    RedressedBefore.setText(value.getString("RedressedBefor")+" %");
                    RedressedAfter.setText(value.getString("RedressedAfter")+" %");
                    Rejected.setText(value.getString("Rejected")+" %");

                }
            } catch (JSONException e) {
                e.printStackTrace();

                showalert("Server Busy At This Moment !!","hai");
            }
        }
    }

    void showalert(String alert_msg, final String show) {
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(Dashboard.this);
        alertDialogBuilder.setTitle("E_Vijayawada");
        // alertDialogBuilder.setIcon(R.drawable.aplogo);
        // set dialog message
        alertDialogBuilder.setMessage(alert_msg).setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        // create alert dialog
        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }

}
