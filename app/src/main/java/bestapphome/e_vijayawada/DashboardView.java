package bestapphome.e_vijayawada;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class DashboardView extends AppCompatActivity implements View.OnClickListener {
ListView dashboardDril_list;
    RelativeLayout logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_view);
        dashboardDril_list = (ListView)findViewById(R.id.dashboardDril_list);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
           /* case R.id.logout:
                Intent i = new Intent(DashboardView.this,Login.class);
                startActivity(i);
                break;*/
        }
    }
}
