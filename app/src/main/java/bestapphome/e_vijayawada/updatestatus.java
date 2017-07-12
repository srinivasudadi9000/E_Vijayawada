package bestapphome.e_vijayawada;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class updatestatus extends AppCompatActivity {
EditText search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updatestatus);
        search = (EditText)findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (search.getText().toString().length()==13){
                    Toast.makeText(getApplicationContext(),"three",Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(),"34",Toast.LENGTH_SHORT).show();

                }
            }
        });
      }
}
