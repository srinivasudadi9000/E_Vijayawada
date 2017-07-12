package bestapphome.e_vijayawada;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


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

                new Login.getstatus(input_usename.getText().toString(), input_usename.getText().toString()).execute();
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

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

}
