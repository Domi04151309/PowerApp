package io.github.domi04151309.powerapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageButton btn1 = (ImageButton) findViewById(R.id.shutdown);
        btn1.setOnClickListener(new View.OnClickListener() {
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
        final ImageButton btn2 = (ImageButton) findViewById(R.id.reboot);
        btn2.setOnClickListener(new View.OnClickListener() {
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
        final ImageButton btn3 = (ImageButton) findViewById(R.id.soft_reboot);
        btn3.setOnClickListener(new View.OnClickListener() {
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
        final ImageButton btn4 = (ImageButton) findViewById(R.id.recovery);
        btn4.setOnClickListener(new View.OnClickListener() {
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
        final ImageButton btn5 = (ImageButton) findViewById(R.id.bootloader);
        btn5.setOnClickListener(new View.OnClickListener() {
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
        final ImageButton btn6 = (ImageButton) findViewById(R.id.screen_off);
        btn6.setOnClickListener(new View.OnClickListener() {
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

        Button btn7 = findViewById(R.id.root);
        btn7.setOnClickListener(new View.OnClickListener() {
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
    }
}
