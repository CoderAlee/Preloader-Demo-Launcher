package org.alee.component.demo.preloader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_scene_one).setOnClickListener(v -> MainActivity.this.go(org.alee.component.demo.process.MainActivity.class));
        findViewById(R.id.btn_scene_two).setOnClickListener(v -> MainActivity.this.go(org.alee.component.demo.component.a.MainActivity.class));
        findViewById(R.id.btn_scene_three).setOnClickListener(v -> go(org.alee.component.demo.complex.dependence.MainActivity.class));
    }

    private void go(Class<? extends Activity> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}