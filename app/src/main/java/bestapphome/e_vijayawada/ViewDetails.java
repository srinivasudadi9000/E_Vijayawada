package bestapphome.e_vijayawada;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

public class ViewDetails extends Activity {
    TextView application_no, grievance_type, applicant_name, mobile_number, concern_officer, aadhar_no, depart_name, ward_no,
            locality, doorno, address_tv, grievance_des_tv, officername, griev_status;
    TextView photo_one, photo_two, photo_three, submit, logout, grievance_remarks;
    public ImageView back, image_two, image_three, grievance_photo2, grievance_photo1, grievance_photo3;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_details);
        clearPreferences();
        grievance_remarks = (TextView) findViewById(R.id.grievance_remarks);
        back = (ImageView)findViewById(R.id.back);
        griev_status = (TextView) findViewById(R.id.griev_status);
        officername = (TextView) findViewById(R.id.officername);
        application_no = (TextView) findViewById(R.id.application_no);
        grievance_type = (TextView) findViewById(R.id.grievance_type);
        applicant_name = (TextView) findViewById(R.id.applicant_name);
        mobile_number = (TextView) findViewById(R.id.mobile_number);
        concern_officer = (TextView) findViewById(R.id.concern_officer);
        aadhar_no = (TextView) findViewById(R.id.aadhar_no);
        depart_name = (TextView) findViewById(R.id.depart_name);
        ward_no = (TextView) findViewById(R.id.ward_no);
        doorno = (TextView) findViewById(R.id.doorno);
        address_tv = (TextView) findViewById(R.id.address_tv);
        grievance_des_tv = (TextView) findViewById(R.id.grievance_des_tv);
        locality = (TextView) findViewById(R.id.locality);


        grievance_photo1 = (ImageView) findViewById(R.id.grievance_photo1);
        grievance_photo2 = (ImageView) findViewById(R.id.grievance_photo2);
        grievance_photo3 = (ImageView) findViewById(R.id.grievance_photo3);
       // logout = (TextView) findViewById(R.id.dashbord_logout);
        submit = (Button) findViewById(R.id.submit);

        progress = new ProgressDialog(ViewDetails.this);
        progress.setMessage("Fetching data from server..");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();

        if (internet()){
            new ViewDetails.getstatus(getIntent().getStringExtra("id").toString(),getIntent().getStringExtra("app_number").toString()).execute();
        }else {
          showalert("Please Check Your Internet Connection..!!","notso");
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewDetails.this,Dashboard.class);
                startActivity(i);
                finish();
            }
        });
     /*   logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences ss = getSharedPreferences("validuser", MODE_PRIVATE);
                SharedPreferences.Editor ee = ss.edit();
                ee.putString("name", "");
                ee.commit();
            }
        });*/
    }

    private class getstatus extends AsyncTask<String, String, JSONObject> {

        private ProgressDialog pDialog;
        private ArrayList<NameValuePair> nameValuePairs;
        private JSONObject json;
        String id,officerid;

        public getstatus(String officerid,String grievanceid) {
            this.id = grievanceid;this.officerid = officerid;
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... arg0) {
            nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("intGrievanceid", id));
            nameValuePairs.add(new BasicNameValuePair("intOfficerid", officerid));
            json = JSONParser.makeServiceCall("http://208.78.220.51/VMCGMS/GrievanceDetbyNumber.aspx", 1, nameValuePairs);

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
                    application_no.setText(value.getString("App_No"));
                    grievance_type.setText(value.getString("ServiceName"));
                    applicant_name.setText(value.getString("ApplicantName"));
                    mobile_number.setText(value.getString("ApplMobile"));
                    concern_officer.setText(value.getString("OfficerName"));
                    aadhar_no.setText(value.getString("OfficerPhoneNo"));
                    depart_name.setText(value.getString("DepartmentName"));
                    ward_no.setText(value.getString("WardNo"));
                    locality.setText(value.getString("LocalityName"));
                    doorno.setText(value.getString("DoorNo"));
                    address_tv.setText(value.getString("ApplAddress"));
                    grievance_des_tv.setText(value.getString("GrievanceDesc"));

                        griev_status.setText(  value.getString("Status"));

                        grievance_remarks.setText(value.getString("remarks"));

                        Picasso.with(ViewDetails.this)
                                .load("http://" + value.getString("GrievancePhotoPath1"))
                                .resize(100,100)
                                //this is also optional if some error has occurred in downloading the image this image would be displayed
                                .into(grievance_photo1);
                        Picasso.with(ViewDetails.this)
                                .load("http://" + value.getString("GrievancePhotoPath2"))
                                .resize(100,100)
                                //this is also optional if some error has occurred in downloading the image this image would be displayed
                                .into(grievance_photo2);
                        Picasso.with(ViewDetails.this)
                                .load("http://" + value.getString("GrievancePhotoPath3"))
                                .resize(100,100)
                                //this is also optional if some error has occurred in downloading the image this image would be displayed
                                .into(grievance_photo3);

                    if (grievance_photo1.getDrawable() == null){
                        //Image doesn´t exist.
                       // Toast.makeText(getBaseContext(),"image not attatched",Toast.LENGTH_SHORT).show();
                        Picasso.with(ViewDetails.this)
                                .load("http://" + value.getString("GrievancePhotoPath1"))
                                .resize(100,100)
                                //this is also optional if some error has occurred in downloading the image this image would be displayed
                                .into(grievance_photo1);
                        Picasso.with(ViewDetails.this)
                                .load("http://" + value.getString("GrievancePhotoPath2"))
                                .resize(120, 120)
                                //this is also optional if some error has occurred in downloading the image this image would be displayed
                                .into(grievance_photo2);
                        Picasso.with(ViewDetails.this)
                                .load("http://" + value.getString("GrievancePhotoPath3"))
                                .resize(120, 120)
                                //this is also optional if some error has occurred in downloading the image this image would be displayed
                                .into(grievance_photo3);
                    }else{
                        //Image Exists!.
                    }
                    }

             } catch (JSONException e) {
                e.printStackTrace();

            }
        }
    }
    public Boolean internet(){
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else
            connected = false;

        return connected;
    }
    void showalert(String alert_msg, final String show) {
        final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(ViewDetails.this);
        alertDialogBuilder.setTitle("103 VMC");
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
