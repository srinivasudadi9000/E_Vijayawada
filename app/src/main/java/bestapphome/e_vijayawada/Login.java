package bestapphome.e_vijayawada;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
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
                Intent i = new Intent(getApplicationContext(), home.class);
                startActivity(i);
                break;
            case R.id.input_usename:
                Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                input_usename.startAnimation(myAnim);
                break;
        }
    }
}
