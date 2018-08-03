package io.github.domi04151309.powerapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userTheme = preferences.getString("AppStyle", "8");
        switch (userTheme) {
            case "8":
                setTheme(R.style.AppTheme_8);
                break;
            case "7":
                setTheme(R.style.AppTheme_7);
                break;
            case "dark":
                setTheme(R.style.AppTheme_Dark);
                break;
            case "black":
                setTheme(R.style.AppTheme_Black);
                break;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.shutdown).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Process proc = Runtime.getRuntime()
                            .exec(new String[]{ "su", "-c", "reboot -p" });
                    proc.waitFor();
                } catch (Exception ex) {
                    Toast.makeText
                            (MainActivity.this, "Action failed! Please enable root!", Toast.LENGTH_LONG).show();
                }
            }
        });
        findViewById(R.id.reboot).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Process proc = Runtime.getRuntime()
                            .exec(new String[]{ "su", "-c", "reboot" });
                    proc.waitFor();
                } catch (Exception ex) {
                    Toast.makeText(MainActivity.this, "Action failed! Please enable root!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        findViewById(R.id.recovery).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Process proc = Runtime.getRuntime()
                            .exec(new String[]{ "su", "-c", "reboot recovery" });
                    proc.waitFor();
                } catch (Exception ex) {
                    Toast.makeText(MainActivity.this, "Action failed! Please enable root!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        findViewById(R.id.bootloader).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Process proc = Runtime.getRuntime()
                            .exec(new String[]{ "su", "-c", "reboot bootloader" });
                    proc.waitFor();
                } catch (Exception ex) {
                    Toast.makeText(MainActivity.this, "Action failed! Please enable root!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        findViewById(R.id.soft_reboot).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Process proc = Runtime.getRuntime()
                            .exec(new String[]{ "su", "-c", "setprop ctl.restart zygote" });
                    proc.waitFor();
                } catch (Exception ex) {
                    Toast.makeText
                            (MainActivity.this, "Action failed! Please enable root!", Toast.LENGTH_LONG).show();
                }
            }
        });
        findViewById(R.id.system_ui).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Process proc = Runtime.getRuntime()
                            .exec(new String[]{ "su", "-c", "killall com.android.systemui" });
                    proc.waitFor();
                } catch (Exception ex) {
                    Toast.makeText
                            (MainActivity.this, "Action failed! Please enable root!", Toast.LENGTH_LONG).show();
                }
            }
        });
        findViewById(R.id.screen_off).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Process proc = Runtime.getRuntime()
                            .exec(new String[]{ "su", "-c", "input keyevent KEYCODE_POWER" });
                    proc.waitFor();
                } catch (Exception ex) {
                    Toast.makeText(MainActivity.this, "Action failed! Please enable root!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        findViewById(R.id.root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Process p;
                try {
                    p = Runtime.getRuntime().exec("su");
                    DataOutputStream os = new DataOutputStream(p.getOutputStream());
                    os.writeBytes("echo access granted\n");
                    os.writeBytes("exit\n");
                    os.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.prefBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Preferences.class));
            }
        });
    }
}
