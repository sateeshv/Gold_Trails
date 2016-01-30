package sateesh.com.goldtrail02.Network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Sateesh on 30-01-2016.
 */
public class InternetCheck {
    private Context _context;

    public InternetCheck(Context context) {
        this._context = context;
    }

    public boolean isConnectingToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null)

                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }

        }
        return false;
    }
}
