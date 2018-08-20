package io.github.domi04151309.powerapp;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Theme.check(this);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}
