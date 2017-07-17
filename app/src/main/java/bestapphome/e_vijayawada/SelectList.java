package bestapphome.e_vijayawada;

import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;

public class SelectList extends Activity implements View.OnClickListener{
    Button btn_submit, btn_submit2;
    private AnimatorSet mSetLeftIn;
  RelativeLayout select_rl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_list);
        select_rl = (RelativeLayout)findViewById(R.id.select_rl);

        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit2 = (Button) findViewById(R.id.knowstatus);

        Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomin);
        // Animation slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        btn_submit.startAnimation(slideUp);

        Animation slideUp2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomin);
        // Animation slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        btn_submit2.startAnimation(slideUp2);


        btn_submit.setOnClickListener(SelectList.this);
        btn_submit2.setOnClickListener(SelectList.this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_submit:
                Intent i = new Intent(SelectList.this, Dashboard.class);
                startActivity(i);
                finish();
                break;
            case R.id.knowstatus:
                Intent ii = new Intent(SelectList.this,updatestatus.class);
                ii.putExtra("class","selectview");
                ii.putExtra("app_no","button");
                startActivity(ii);
                finish();
                break;

        }
    }
}
