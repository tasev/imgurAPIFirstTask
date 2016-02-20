package firsttask.imgur.global.matrix.imgurapitaskonefrommatrix.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import firsttask.imgur.global.matrix.imgurapitaskonefrommatrix.R;
import firsttask.imgur.global.matrix.imgurapitaskonefrommatrix.fragments.SecondActivityFragment;

public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new SecondActivityFragment()).commit();

    }

}
