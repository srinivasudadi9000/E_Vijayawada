package bestapphome.e_vijayawada;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import bestapphome.e_vijayawada.json.JSONParser;

public class Login extends Activity implements View.OnClickListener {
    Button btn_login;
    EditText input_usename, input_password;
    ImageView logo;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        clearPreferences();
        btn_login = (Button) findViewById(R.id.btn_login);
        input_usename = (EditText) findViewById(R.id.input_usename);
        input_password = (EditText) findViewById(R.id.input_password);
        btn_login.setOnClickListener(Login.this);
        logo = (ImageView) findViewById(R.id.applogo);

      /*  Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blinking);
        logo.startAnimation(myAnim);*/
     /*   ObjectAnimator anim = (ObjectAnimator) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.flipping);
        anim.setTarget(logo);
        anim.setDuration(3000);

        anim.start();*/

        input_usename.setOnClickListener(Login.this);

     }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if (input_usename.getText().length() == 0) {
                    showalert("UserId should not be empty");

                } else if (input_password.getText().length() == 0) {
                    showalert("Password should not be empty");

                } else {

                    progress = new ProgressDialog(this);
                    progress.setMessage("Authenticating User..");
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.setIndeterminate(true);
                    progress.setCancelable(false);
                    progress.show();
                    new Login.getstatus(input_usename.getText().toString(), input_password.getText().toString()).execute();

                }
                break;
            case R.id.input_usename:
                Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                input_usename.startAnimation(myAnim);
                break;
        }
    }

    private class getstatus extends AsyncTask<String, String, JSONObject> {

        private ProgressDialog pDialog;
        private ArrayList<NameValuePair> nameValuePairs;
        private JSONObject json;
        String OffUserid, OffPassword;

        public getstatus(String OffUserid, String OffPassword) {
            this.OffPassword = OffPassword;
            this.OffUserid = OffUserid;
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... arg0) {
            nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("OffUserid", OffUserid));
            nameValuePairs.add(new BasicNameValuePair("OffPassword", OffPassword));
            json = JSONParser.makeServiceCall("http://208.78.220.51/VMCGMS/LoginService.aspx", 1, nameValuePairs);

            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            // Toast.makeText(getApplicationContext(), json.toString(), Toast.LENGTH_SHORT).show();
            progress.dismiss();
            try {
                JSONArray jsonObject = json.getJSONArray("users");
                for (int i = 0; i < jsonObject.length(); i++) {
                    JSONObject value = jsonObject.getJSONObject(i);
                    //    Toast.makeText(getApplicationContext(), value.getString("intUserid").toString(), Toast.LENGTH_SHORT).show();

                    SharedPreferences sharedPreferences = getSharedPreferences("Userinfo", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("intUserid", value.getString("intUserid"));
                    editor.putString("username", value.getString("username"));
                    editor.putString("user_id", value.getString("user_id"));
                    editor.putString("userlevel", value.getString("userlevel"));
                    editor.putString("intofficerid", String.valueOf(value.getInt("intOfficerid")));
                    editor.commit();
                    Intent ii = new Intent(getApplicationContext(), updatestatus.class);
                    startActivity(ii);
                    finish();
                    SharedPreferences ss = getSharedPreferences("validuser", MODE_PRIVATE);
                    SharedPreferences.Editor ee = ss.edit();
                    ee.putString("name", "true");
                    ee.commit();
                }
            } catch (JSONException e) {
                showalert("Invalid Password ");
            }
        }
    }

    void showalert(String alert_msg) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Login.this);
        alertDialogBuilder.setTitle("E_Vijayawada");
        ///  alertDialogBuilder.setIcon(R.drawable.aplogo);
        // set dialog message
        alertDialogBuilder.setMessage(alert_msg).setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }

    void hidekeyboard() {
        InputMethodManager inputManager = (InputMethodManager) Login.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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
