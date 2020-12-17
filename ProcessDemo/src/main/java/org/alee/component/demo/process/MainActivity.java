package org.alee.component.demo.process;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.alee.component.demo.process.a.AProcessActivity;
import org.alee.component.demo.process.all.AllProcessActivity;
import org.alee.component.demo.process.b.BProcessActivity;
import org.alee.component.demo.process.main.MainProcessActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.process_activity_main);
        findViewById(R.id.btn_process_scenes_first).setOnClickListener(v -> go(MainProcessActivity.class));
        findViewById(R.id.btn_process_scenes_second).setOnClickListener(v -> go(AProcessActivity.class));
        findViewById(R.id.btn_process_scenes_third).setOnClickListener(v -> go(BProcessActivity.class));
        findViewById(R.id.btn_process_scenes_fourth).setOnClickListener(v -> go(AllProcessActivity.class));
    }

    private void go(Class<? extends Activity> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}