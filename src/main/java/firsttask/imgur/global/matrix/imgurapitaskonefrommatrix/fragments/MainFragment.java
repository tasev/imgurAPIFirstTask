package firsttask.imgur.global.matrix.imgurapitaskonefrommatrix.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import firsttask.imgur.global.matrix.imgurapitaskonefrommatrix.R;
import firsttask.imgur.global.matrix.imgurapitaskonefrommatrix.adapters.ImageGaleryAdapter;
import firsttask.imgur.global.matrix.imgurapitaskonefrommatrix.api.RestApi;
import firsttask.imgur.global.matrix.imgurapitaskonefrommatrix.model.ImageGalleryModel;
import firsttask.imgur.global.matrix.imgurapitaskonefrommatrix.model.ImageModel;
import firsttask.imgur.global.matrix.imgurapitaskonefrommatrix.utils.BackgroundWorker;
import firsttask.imgur.global.matrix.imgurapitaskonefrommatrix.utils.Constants;
import retrofit.Response;

public class MainFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    @Bind(R.id.my_recycler_view) RecyclerView recyclerView;
    private ImageGaleryAdapter myAdapter;
    private RestApi api;
    private ImageGalleryModel imageGalleryModel=new ImageGalleryModel();

    public static MainFragment newInstance(int position) {
        MainFragment f = new MainFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.global_recycler_view, container, false);
        ButterKnife.bind(this, rootView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3, LinearLayoutManager.HORIZONTAL, false));
        getImages(getActivity());
        return rootView;
    }
    public void getImages(final Context context) {
        api = new RestApi((Activity) context);
//        api.checkInternet(new Runnable() {
//            @Override
//            public void run() {
        BackgroundWorker.run(context, "Getting data...", new BackgroundWorker
                .TaskHandler() {
            @Override
            public void onError(Exception e) {
                try {
                    e.printStackTrace();
                    Toast.makeText(context, Constants.cantConnectToServerError, Toast
                            .LENGTH_LONG).show();
                    Log.d("comp", "err");
                } catch (Exception err) {
                    err.printStackTrace();
                }
            }

            @Override
            public void onComplete(Object result) {
                try {
                    Response<ImageGalleryModel> res = (Response<ImageGalleryModel>) result;
                    if (res.code() == 200 || res.code() == 201) {
                        ImageGalleryModel imageGalleryPom = new ImageGalleryModel();
                        imageGalleryPom.data=new ArrayList<ImageModel>();
                        imageGalleryModel = res.body();
                        for(int i =0;i<imageGalleryModel.data.size();i++)
                        {
                            try{
                                if(!imageGalleryModel.data.get(i).is_album){
                                    imageGalleryPom.data.add(imageGalleryModel.data.get(i));
                                }
                            }catch (Exception e){}

                        }
                        myAdapter = new ImageGaleryAdapter(getActivity(),imageGalleryPom);
                        recyclerView.setAdapter(myAdapter);


                    } else {     // if you cant connect to server, get from preferences

                        try {
                            JSONObject obj = new JSONObject(res.errorBody().string());
                            if (obj.has("message")) {
                                Toast.makeText(getActivity(), "" + obj.getString("message"), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), Constants.cantConnectToServerError, Toast.LENGTH_SHORT).show();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }catch (Exception e){}
            }

            @Override
            public Object backgroundTask() throws Exception {
                return api.getImagesViral().execute();
            }
        });
//            }
//        });
    }
}
