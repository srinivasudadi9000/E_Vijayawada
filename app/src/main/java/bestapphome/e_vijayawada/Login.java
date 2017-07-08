package bestapphome.e_vijayawada;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity implements View.OnClickListener {
    Button btn_login;
    EditText input_usename,input_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        btn_login = (Button)findViewById(R.id.btn_login);
        input_usename = (EditText)findViewById(R.id.input_usename);
        input_password = (EditText)findViewById(R.id.input_password);
        btn_login.setOnClickListener(Login.this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                Intent i = new Intent(getApplicationContext(),home.class);
                startActivity(i);
                break;
        }
    }
}
