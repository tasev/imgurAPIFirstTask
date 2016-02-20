package firsttask.imgur.global.matrix.imgurapitaskonefrommatrix.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;

public class BackgroundWorker {

    private ProgressDialog dialog = null;
    private Handler handler = new Handler();
    private Thread thread;
    private TaskHandler taskHandler;

    public BackgroundWorker(Context context, String title) {
        if (title != "")
            this.dialog = ProgressDialog.show(context, "", title, true);
    }

    public BackgroundWorker(Context context, int resTitle) {
        String title = context.getString(resTitle);
        this.dialog = ProgressDialog.show(context, "", title, true);
    }

    public void run(TaskHandler task) {
        this.taskHandler = task;
        (thread = new Thread(new Runnable() {
            private Object result;

            @Override
            public void run() {
                try {
                    if (taskHandler != null)
                        result = taskHandler.backgroundTask();
                    dismissDialog();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (taskHandler != null)
                                taskHandler.onComplete(result);
                            if (dialog != null)
                                dialog.dismiss();
                        }
                    });
                } catch (final Exception e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (taskHandler != null)
                                taskHandler.onError(e);
                            if (dialog != null)
                                dialog.dismiss();
                        }
                    });
                }

            }
        })).start();

    }

    public ProgressDialog getDialog() {
        return dialog;
    }

    public static BackgroundWorker run(Context context, String title, TaskHandler taskHandler) {
        BackgroundWorker worker = new BackgroundWorker(context, title);
        worker.run(taskHandler);
        return worker;
    }

    public static BackgroundWorker run(Context context, int resTitle, TaskHandler taskHandler) {
        BackgroundWorker worker = new BackgroundWorker(context, resTitle);
        worker.run(taskHandler);
        return worker;
    }

    public void dismissDialog() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (dialog != null)
                        if (dialog.isShowing())
                            dialog.dismiss();
                } catch (Exception e) {
                }
            }
        });
    }

    public void abort() {
        taskHandler = null;
        try {
            thread.interrupt();
        } catch (Exception e) {
        }
        try {
            if (thread.isAlive())
                thread.interrupt();
        } catch (Exception e) {
        }
    }

    public interface TaskHandler {
        public Object backgroundTask() throws Exception;

        public void onComplete(Object result);

        public void onError(Exception e);
    }

}
