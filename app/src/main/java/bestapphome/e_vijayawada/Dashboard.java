package bestapphome.e_vijayawada;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Dashboard extends Activity {
    ProgressBar progress1,progress2,progress3,progress4,progress5;
    TextView PendingBefore,PendingAfter,RedressedBefore,RedressedAfter,Rejected;
    Handler progressHandler = new Handler();
    int i = 0;
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
                        Toast.makeText(getBaseContext(),"kasldfkasld",Toast.LENGTH_SHORT).show();
                    }

                }

            }
        }).start();


    }
}
