package io.github.domi04151309.powerapp;

import android.annotation.TargetApi;
import android.os.Build;
import android.service.quicksettings.TileService;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.N)
public class QSReboot extends TileService{

    @Override
    public void onClick() {
        boolean isCurrentlyLocked = isLocked();
        if (!isCurrentlyLocked) {
            try {
                Process p = Runtime.getRuntime()
                        .exec(new String[]{ "su", "-c", "reboot" });
                p.waitFor();
            } catch (Exception ex) {
                Toast.makeText
                        (this, "Action failed! Please enable root!", Toast.LENGTH_LONG).show();
            }
        }
    }

}