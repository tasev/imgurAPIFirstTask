package firsttask.imgur.global.matrix.imgurapitaskonefrommatrix.utils;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import firsttask.imgur.global.matrix.imgurapitaskonefrommatrix.R;

public class CheckConnection {

    public static int resume = 0;

    public static Boolean CheckInternetConnectivity(final Activity con, final boolean show, final Runnable callback) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeConnection = connectivityManager.getActiveNetworkInfo();

            if (activeConnection == null)
                throw new Exception();
            if (!activeConnection.isConnectedOrConnecting())
                throw new Exception();
            return true;
        } catch (Exception ex) {

            if (show) {
                Builder builder = new Builder(con);
                builder.setTitle(con.getString(R.string.check_internet_title)).setMessage(con.getString(R.string.check_internet_message))
                        .setPositiveButton(con.getString(R.string.check_internet_settings), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (CheckInternetConnectivity(con, show, callback))
                                    callback.run();
                                // setResume(1);
                                // con.startActivity(new
                                // Intent(Settings.ACTION_WIRELESS_SETTINGS));
                            }
                        }).setNegativeButton(con.getString(R.string.check_internet_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // dialog.dismiss();
                        con.finish();
                    }
                }).setCancelable(false);
                builder.create().show();
            }
            return false;
        }
    }

    public static Boolean CheckWifi(final Activity con) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
            // dNetworkInfo activeConnection =
            // connectivityManager.getActiveNetworkInfo();
            // For 3G check
            boolean is3g = false;
            try {
                is3g = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
            } catch (Exception e) {
                is3g = false;
            }

            if (is3g)
                throw new Exception();

            return true;
        } catch (Exception ex) {
            return false;
        }
    }

//	public static boolean checkPlayServices(Activity context) {
//		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
//		if (resultCode != ConnectionResult.SUCCESS) {
//			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
//				GooglePlayServicesUtil.getErrorDialog(resultCode, context, PLAY_SERVICES_RESOLUTION_REQUEST).show();
//			} else {
//				Log.i("PLAY_SERVICES", "This device is not supported.");
//				context.finish();
//			}
//			return false;
//		}
//		return true;
//	}

    public static void setResume(int resume) {
        CheckConnection.resume = resume;
    }

    public static int getResume() {
        return resume;
    }
}
