package bestapphome.e_vijayawada;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import bestapphome.e_vijayawada.json.JSONParser;

public class updatestatus extends Activity implements View.OnClickListener {
    EditText search;
    TextView application_no, grievance_type, applicant_name, mobile_number, concern_officer, aadhar_no, depart_name, ward_no,
            locality, doorno, address_tv, grievance_des_tv, officername, griev_status;
    TextView photo_one, photo_two, photo_three, submit, logout, grievance_remarks;
    public ImageView image_one, image_two, image_three, grievance_photo2, grievance_photo1, grievance_photo3;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    Dialog dialog;
    private Bitmap bitmap;
    SharedPreferences sharedPreferences;
    String officerid, App_status, selectmyimages, userChoosenTask, exactstatus;
    Spinner spinner;
    Bitmap scaledBitmap = null, scaledBitmap2 = null, scaledBitmap3 = null;
    private static String TAG = "PermissionDemo";
    private static final int RECORD_REQUEST_CODE = 101;
    ProgressDialog progress;
    EditText remarks;
    LinearLayout mylinear;
    TableRow grievance_status, grievance_Remark, grievance_phot, grievance_file2, grievance_file3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updatestatus);
        grievance_status = (TableRow) findViewById(R.id.grievance_status);
        grievance_Remark = (TableRow) findViewById(R.id.grievance_Remark);
        grievance_phot = (TableRow) findViewById(R.id.grievance_phot);
        grievance_file2 = (TableRow) findViewById(R.id.grievance_file2);
        grievance_file3 = (TableRow) findViewById(R.id.grievance_file3);
        remarks = (EditText) findViewById(R.id.remarks_et);
        grievance_remarks = (TextView) findViewById(R.id.grievance_remarks);
        mylinear = (LinearLayout) findViewById(R.id.mylinear);
        search = (EditText) findViewById(R.id.search);
        spinner = (Spinner) findViewById(R.id.spinner);
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
        image_one = (ImageView) findViewById(R.id.image_one);
        image_two = (ImageView) findViewById(R.id.image_two);
        image_three = (ImageView) findViewById(R.id.image_three);
        photo_one = (TextView) findViewById(R.id.photo_one);
        photo_two = (TextView) findViewById(R.id.photo_two);
        photo_three = (TextView) findViewById(R.id.photo_three);
        grievance_photo1 = (ImageView) findViewById(R.id.grievance_photo1);
        grievance_photo2 = (ImageView) findViewById(R.id.grievance_photo2);
        grievance_photo3 = (ImageView) findViewById(R.id.grievance_photo3);
        logout = (TextView) findViewById(R.id.logout);
        submit = (Button) findViewById(R.id.submit);

        photo_one.setOnClickListener(updatestatus.this);
        photo_two.setOnClickListener(updatestatus.this);
        photo_three.setOnClickListener(updatestatus.this);
        submit.setOnClickListener(updatestatus.this);
        logout.setOnClickListener(updatestatus.this);
        selectmyimages = "novalue";
        //hidekeyboard();
        sharedPreferences = getSharedPreferences("Userinfo", MODE_PRIVATE);
        officerid = sharedPreferences.getString("intofficerid", null);
        SharedPreferences sharedPreferences = getSharedPreferences("Userinfo", MODE_PRIVATE);
        officername.setText(sharedPreferences.getString("username", ""));
        search.setText("2017-VMC-");
        search.setSelection(search.getText().length());
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (search.getText().toString().length() == 13) {
                    progress = new ProgressDialog(updatestatus.this);
                    progress.setMessage("Fetching data from server..");
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.setIndeterminate(true);
                    progress.setCancelable(false);
                    progress.show();
                    new updatestatus.getstatus("2017-VMC-" + search.getText().toString().substring(9, 13)).execute();
                }
            }
        });

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                App_status = parent.getItemAtPosition(position).toString();
                //  Toast.makeText(getApplicationContext(), parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        clearPreferences();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
        }
    }


    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());

                Uri tempUri = getImageUri(getApplicationContext(), bitmap);

                File finalFile = new File(getRealPathFromURI(tempUri));
                //  compressImage(finalFile.getAbsolutePath().toString());

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                File destination = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".jpg");

                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                switch (selectmyimages) {
                    case "selectmyimage1":
                        //    uploadImage();
                        image_one.setMaxWidth(150);
                        image_one.setMaxHeight(150);
                        // new MainActivity.JSONParsedoitfast(scaledBitmap,"one").execute();
                        scaledBitmap = bitmap;
                        image_one.setImageBitmap(bitmap);
                        break;
                    case "selectmyimage2":
                        //    uploadImage();
                        image_two.setMaxWidth(150);
                        image_two.setMaxHeight(150);
                        //   new MainActivity.JSONParsedoitfast(scaledBitmap2,"two").execute();
                        scaledBitmap2 = bitmap;
                        image_two.setImageBitmap(bitmap);
                        break;
                    case "selectmyimage3":
                        //    uploadImage();
                        image_three.setMaxWidth(150);
                        image_three.setMaxHeight(150);
                        //  new MainActivity.JSONParsedoitfast(scaledBitmap3,"three").execute();
                        scaledBitmap3 = bitmap;
                        image_three.setImageBitmap(bitmap);
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        Log.d("myimage", encodedImage.toString());
        return encodedImage;
    }

    private void uploadImage(final String remarks) {

        class UploadImage extends AsyncTask<Bitmap, Void, String> {

            //  ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // loading = ProgressDialog.show(updatestatus.this, "Uploading...", null, true, true);
            }

            @Override
            protected void onPostExecute(String json) {
                super.onPostExecute(json);
                //  loading.dismiss();
                progress.dismiss();
                Toast.makeText(getApplicationContext(), json.toString(), Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject = new JSONObject(json.toString());
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject result = jsonArray.getJSONObject(i);
                        if (result.getString("CreateEvent").equals("success")) {
                            Intent ii = new Intent(updatestatus.this, updatestatus.class);
                            startActivity(ii);
                            finish();
                            break;
                        } else {

                            showalert("Server Busy Please Try Again.","show");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                    showalert("Server Busy Please Try Again.","show");
                }
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                // Bitmap bitmap1 = params[0];
                String uploadImage1, uploadImage2, uploadImage3;

                if (scaledBitmap == null) {
                    uploadImage1 = "nullimage";
                } else {
                    uploadImage1 = getStringImage(scaledBitmap);
                }
                if (scaledBitmap2 == null) {
                    uploadImage2 = "nullimage";
                } else {
                    uploadImage2 = getStringImage(scaledBitmap2);
                }
                if (scaledBitmap3 == null) {
                    uploadImage3 = "nullimage";
                } else {
                    uploadImage3 = getStringImage(scaledBitmap3);
                }

                String filename = selectmyimages;
                HashMap<String, String> data = new HashMap<>();


                SharedPreferences sharedPreferences1 = getSharedPreferences("app_info", MODE_PRIVATE);

                data.put("intGrivanceid", sharedPreferences1.getString("intGrivanceid", ""));
                data.put("App_No", sharedPreferences1.getString("App_No", ""));
                data.put("Status", App_status);
                data.put("remarks", remarks);
                data.put("GLatitude", "22.22");
                data.put("GLangitude", "22.22");
                data.put("intOfficerid", officerid);

                // data.put("GrievancePhotoFile1", "sdfasdf");
                data.put("GrievancePhotoPath1", uploadImage1);
                data.put("GrievancePhotoPath1", "asdfasdf");
                // data.put("GrievancePhotoFile2", "asdfas");
                data.put("GrievancePhotoPath2", uploadImage2);
                data.put("GrievancePhotoPath2", "asdfsad");
                // data.put("GrievancePhotoFile3", "asdfsdf");
                data.put("GrievancePhotoPath3", uploadImage3);
                data.put("GrievancePhotoPath3", "asdfsd");


                System.out.print(data.toString());
                String result = rh.sendPostRequest("http://208.78.220.51/VMCGMS/UpdateStatusofGreivance.aspx", data);
                return result;
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }

    private void cameraIntent() {
        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(it, REQUEST_CAMERA);
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(updatestatus.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {

                    userChoosenTask = "Take Photo";
                    cameraIntent();
                } else if (options[item].equals("Choose from Gallery")) {
                    userChoosenTask = "Choose from Library";
                    galleryIntent();
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                onSelectFromGalleryResult(data);
            } else if (requestCode == REQUEST_CAMERA) {
                onCaptureImageResult(data);
            }
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");

        Uri tempUri = getImageUri(updatestatus.this, thumbnail);

        // CALL THIS METHOD TO GET THE ACTUAL PATH
        File finalFile = new File(getRealPathFromURI(tempUri));
        bitmap = (Bitmap) data.getExtras().get("data");
        // compressImage(finalFile.getAbsolutePath().toString());
        // ivImage.setImageBitmap(scaledBitmap);

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (selectmyimages) {
            case "selectmyimage1":
                // uploadImage();
                // new MainActivity.JSONParsedoitfast(scaledBitmap,"one").execute();
                image_one.setMaxWidth(150);
                image_one.setMaxHeight(150);
                image_one.setImageBitmap(bitmap);
                scaledBitmap = bitmap;
                break;
            case "selectmyimage2":
                //  uploadImage();
                //   new MainActivity.JSONParsedoitfast(scaledBitmap2,"two").execute();
                image_two.setMaxWidth(150);
                image_two.setMaxHeight(150);
                image_two.setImageBitmap(bitmap);
                scaledBitmap2 = bitmap;
                break;
            case "selectmyimage3":
                //   uploadImage();
                //  new MainActivity.JSONParsedoitfast(scaledBitmap3,"three").execute();
                image_three.setMaxWidth(150);
                image_three.setMaxHeight(150);
                image_three.setImageBitmap(bitmap);
                scaledBitmap3 = bitmap;
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.photo_one:
                selectmyimages = "selectmyimage1";
                selectImage();
                break;
            case R.id.photo_two:
                selectmyimages = "selectmyimage2";
                selectImage();
                break;
            case R.id.photo_three:
                selectmyimages = "selectmyimage3";
                selectImage();
                break;
            case R.id.submit:
                if (selectmyimages.equals("novalue")) {
                    showalert("Please Select Photo","no");
                } else if (App_status.equals("--Select--")) {
                    showalert("Select Application Status","no");
                }/*else if (exactstatus.equals("Redressed") || exactstatus.equals("Rejected")){
                    showalert("Grievance Status Already Updated Thankyou !!! ");
                }*/ else {
                    progress = new ProgressDialog(updatestatus.this);
                    progress.setMessage("Uploading data to server..");
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.setIndeterminate(true);
                    progress.setCancelable(false);
                    progress.show();
                    uploadImage(remarks.getText().toString());
                }
                break;
            case R.id.logout:

                SharedPreferences ss = getSharedPreferences("validuser", MODE_PRIVATE);
                SharedPreferences.Editor ee = ss.edit();
                ee.putString("name", "");
                ee.commit();

                Intent i = new Intent(updatestatus.this, Login.class);
                startActivity(i);
                finish();
        }

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
                    Picasso.with(updatestatus.this)
                            .load("http://" + value.getString("GrievancePhotoPath1"))
                            //this is also optional if some error has occurred in downloading the image this image would be displayed
                            .into(image_one);
                    Picasso.with(updatestatus.this)
                            .load("http://" + value.getString("GrievancePhotoPath2"))
                            //this is also optional if some error has occurred in downloading the image this image would be displayed
                            .into(image_two);
                    Picasso.with(updatestatus.this)
                            .load("http://" + value.getString("GrievancePhotoPath3"))
                            //this is also optional if some error has occurred in downloading the image this image would be displayed
                            .into(image_three);
                    exactstatus = value.getString("Status");

                    SharedPreferences sharedPreferences = getSharedPreferences("app_info", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("intGrivanceid", value.getString("intGrivanceid"));
                    editor.putString("App_No", value.getString("App_No"));
                    editor.commit();

                    if (exactstatus.equals("Rejected") || exactstatus.equals("Redressed")) {
                        mylinear.setVisibility(View.GONE);
                        grievance_status.setVisibility(View.VISIBLE);
                        grievance_Remark.setVisibility(View.VISIBLE);
                        grievance_phot.setVisibility(View.VISIBLE);
                        grievance_file2.setVisibility(View.VISIBLE);
                        grievance_file3.setVisibility(View.VISIBLE);
                        griev_status.setText(":" + value.getString("Status"));

                        grievance_remarks.setText(value.getString("remarks"));
                        Picasso.with(updatestatus.this)
                                .load("http://" + value.getString("GrievancePhotoPath1"))
                                //this is also optional if some error has occurred in downloading the image this image would be displayed
                                .into(grievance_photo1);
                        Picasso.with(updatestatus.this)
                                .load("http://" + value.getString("GrievancePhotoPath2"))
                                //this is also optional if some error has occurred in downloading the image this image would be displayed
                                .into(grievance_photo2);
                        Picasso.with(updatestatus.this)
                                .load("http://" + value.getString("GrievancePhotoPath3"))
                                //this is also optional if some error has occurred in downloading the image this image would be displayed
                                .into(grievance_photo3);
                    } else {

                    }


                }
            } catch (JSONException e) {
                e.printStackTrace();
                //    Toast.makeText(getBaseContext(),"exception",Toast.LENGTH_SHORT).show();

                showalert("Record Not Found !!! For This Grievance Id " + search.getText().toString(),"show");
            }
        }
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

    void showalert(String alert_msg, final String show) {
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(updatestatus.this);
        alertDialogBuilder.setTitle("E_Vijayawada");
        // alertDialogBuilder.setIcon(R.drawable.aplogo);
        // set dialog message
        alertDialogBuilder.setMessage(alert_msg).setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (show.equals("show")){
                            Intent refresh = new Intent(updatestatus.this, updatestatus.class);
                            startActivity(refresh);
                            finish();
                        }
                    }
                });
        // create alert dialog
        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }

    void hidekeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
