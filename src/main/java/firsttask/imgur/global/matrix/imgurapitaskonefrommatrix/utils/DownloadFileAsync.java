package firsttask.imgur.global.matrix.imgurapitaskonefrommatrix.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.WindowManager;

import firsttask.imgur.global.matrix.imgurapitaskonefrommatrix.listeners.OnDownloadFinished;

public class DownloadFileAsync extends AsyncTask<String, String, String> {
    public ProgressDialog mProgressDialog;
    Context context;
    OnDownloadFinished onDownloadFinished;
    int count=Constants.dialogCounter;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage("Downloading files..");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               destroyTask();
            }
        });
        WindowManager.LayoutParams lp = mProgressDialog.getWindow().getAttributes();
        lp.dimAmount = 0.0F;
        lp.screenBrightness = 1.0F;
        mProgressDialog.getWindow().setAttributes(lp);
        mProgressDialog.show();
    }

    @Override
    protected String doInBackground(String... aurl) {

        try {

            long total = 0;

            while (total<count) {
                total ++;
                try {
                    if(isCancelled())
                        break;
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                publishProgress("" + (total * 100) / count);
            }
        } catch (Exception e) {
            Log.d("ANDRO_ASYNC", e.getMessage());
            mProgressDialog.dismiss();
        }
        return null;

    }

    protected void onProgressUpdate(String... progress) {
        try {
            Log.d("ANDRO_ASYNC", progress[0]);
            onDownloadFinished.inDownloadFinished(progress[0] + "");
            mProgressDialog.setProgress(Integer.parseInt(progress[0]));
        }catch (Exception e){

        }
    }

    @Override
    protected void onPostExecute(String unused) {
        try {
            destroyTask();
        }catch (Exception e){}
        try {
            onDownloadFinished.inDownloadFinished("finish");
        }catch (Exception e){

        }
    }

    public void  destroyTask(){
        try{
            onDownloadFinished.inDownloadFinished("finish");
            this.cancel(true);
            mProgressDialog.dismiss();
        }catch (Exception e){}
    }

    public void initialize(Context context, OnDownloadFinished onDownloadFinished) {
        this.context = context;
        this.onDownloadFinished = onDownloadFinished;
    }
}
