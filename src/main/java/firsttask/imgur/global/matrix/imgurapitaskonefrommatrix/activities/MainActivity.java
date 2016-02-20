package firsttask.imgur.global.matrix.imgurapitaskonefrommatrix.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import firsttask.imgur.global.matrix.imgurapitaskonefrommatrix.R;
import firsttask.imgur.global.matrix.imgurapitaskonefrommatrix.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new MainFragment()).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem mItem=menu.findItem(R.id.action_send);
        mItem.setOnMenuItemClickListener(
                new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        startActivity(new Intent(MainActivity.this,SecondActivity.class));
                        return false;
                    }
                });
        return true;
    }
}
