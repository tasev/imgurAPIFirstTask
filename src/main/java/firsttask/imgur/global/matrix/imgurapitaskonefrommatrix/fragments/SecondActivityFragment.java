package firsttask.imgur.global.matrix.imgurapitaskonefrommatrix.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import firsttask.imgur.global.matrix.imgurapitaskonefrommatrix.R;
import firsttask.imgur.global.matrix.imgurapitaskonefrommatrix.activities.SecondActivity;
import firsttask.imgur.global.matrix.imgurapitaskonefrommatrix.listeners.OnDownloadFinished;
import firsttask.imgur.global.matrix.imgurapitaskonefrommatrix.utils.DownloadFileAsync;

public class SecondActivityFragment extends Fragment implements OnDownloadFinished {

    private static final String ARG_POSITION = "position";
    @Bind(R.id.asincConunter) TextView txtAsyncCounter;
    @Bind(R.id.imgMatrix) ImageView imgMatrix;
    public DownloadFileAsync downloadFileAsync = new DownloadFileAsync();
    public static SecondActivityFragment newInstance(int position) {
        SecondActivityFragment f = new SecondActivityFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public SecondActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_main, container, false);
        ButterKnife.bind(this, rootView);
        imgMatrix.setVisibility(View.VISIBLE);
        txtAsyncCounter.setVisibility(View.VISIBLE);
        downloadFileAsync.initialize(getActivity(), this);
        downloadFileAsync.execute("");
        return rootView;
    }

    @Override
    public void inDownloadFinished(String finishedString) {
        if(finishedString.equalsIgnoreCase("finish")) {
                txtAsyncCounter.setText("Finished");
                Toast.makeText(getActivity(), "Downloading Finished!", Toast.LENGTH_SHORT).show();
        }
        else{
            try {
                txtAsyncCounter.setText(finishedString+"%");
            }catch (Exception e){}
        }
    }

}
