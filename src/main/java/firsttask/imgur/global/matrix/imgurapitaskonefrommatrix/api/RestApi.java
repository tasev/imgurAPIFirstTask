package firsttask.imgur.global.matrix.imgurapitaskonefrommatrix.api;

import android.app.Activity;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.ResponseBody;


import firsttask.imgur.global.matrix.imgurapitaskonefrommatrix.model.ImageGalleryModel;
import firsttask.imgur.global.matrix.imgurapitaskonefrommatrix.utils.CheckConnection;
import firsttask.imgur.global.matrix.imgurapitaskonefrommatrix.utils.LoggingInterceptor;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;


public class RestApi {

    private Activity activity;
    private static final String URL = "https://api.imgur.com/";

    public RestApi(Activity activity) {
        this.activity = activity;
    }

    public ApiService request() {


        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(new LoggingInterceptor(activity));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        ApiService service = retrofit.create(ApiService.class);

        return service;
    }

    public void checkInternet(Runnable callback) {
        if (CheckConnection.CheckInternetConnectivity(activity, true, callback))
            callback.run();
    }

    public Call<ImageGalleryModel> getImagesViral() {
        return request().getImagesViral();
    }

}