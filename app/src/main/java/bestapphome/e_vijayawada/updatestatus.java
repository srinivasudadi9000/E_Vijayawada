package bestapphome.e_vijayawada;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import bestapphome.e_vijayawada.json.JSONParser;

public class updatestatus extends Activity {
    EditText search;
    TextView application_no, grievance_type, applicant_name, mobile_number, concern_officer, aadhar_no, depart_name, ward_no,
            locality, doorno, address_tv, grievance_des_tv,officername;
    SharedPreferences sharedPreferences;
    String officerid;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updatestatus);
        search = (EditText) findViewById(R.id.search);
        spinner = (Spinner)findViewById(R.id.spinner);
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
        sharedPreferences = getSharedPreferences("Userinfo",MODE_PRIVATE);
        officerid =sharedPreferences.getString("intofficerid",null);
        SharedPreferences sharedPreferences = getSharedPreferences("Userinfo", MODE_PRIVATE);
        officername.setText(": "+sharedPreferences.getString("username",""));
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (search.getText().toString().length() == 13) {
                     Toast.makeText(getApplicationContext(), "three", Toast.LENGTH_SHORT).show();
                     Toast.makeText(getApplicationContext(), "34", Toast.LENGTH_SHORT).show();
                    new updatestatus.getstatus(search.getText().toString()).execute();
                }
            }
        });

         final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



    }
    private class getstatus extends AsyncTask<String, String, JSONObject> {

        private ProgressDialog pDialog;
        private ArrayList<NameValuePair> nameValuePairs;
        private JSONObject json;
        String id;

        public getstatus(String grievanceid) {
            this.id = grievanceid;
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
            Toast.makeText(getApplicationContext(),json.toString(),Toast.LENGTH_SHORT).show();
            try {
                JSONArray jsonObject = json.getJSONArray("users");
                for (int i = 0; i < jsonObject.length(); i++) {
                    JSONObject value = jsonObject.getJSONObject(i);
                    //    Toast.makeText(getApplicationContext(), value.getString("intUserid").toString(), Toast.LENGTH_SHORT).show();
                     application_no.setText(value.getString("App_No"));
                    grievance_type.setText(value.getString("intGrivanceid"));
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
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
