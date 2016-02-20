package firsttask.imgur.global.matrix.imgurapitaskonefrommatrix.utils;

import android.app.Activity;
import android.util.Log;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

import okio.Buffer;

public class LoggingInterceptor implements Interceptor {
    private Activity activity;

    public LoggingInterceptor(Activity activity) {
        this.activity = activity;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        // Add Authorization(Teken) header to all requests
        request = request.newBuilder().addHeader("Authorization", "Client-ID " + Constants.MY_IMGUR_CLIENT_ID).build();


        long t1 = System.nanoTime();

        //prai problemi bodyToString(request)
        //  if(request.body() != null && request.body().contentLength() < 1024)
        Log.d("Retrofit", String.format("Sending request %s on %s%n%s", request.url(), chain
                .connection(), request.headers()) + " Params " + bodyToString(request));

        Response response = chain.proceed(request);

        String responseBodyString = response.body().string();
        long t2 = System.nanoTime();
        Log.d("Retrofit", String.format("Received response for %s in %.1fms%n%s", response.request
                ().url(), (t2 - t1) / 1e6d, response.headers()) + "Body " + responseBodyString);

        return response.newBuilder().body(ResponseBody.create(response.body().contentType(),
                responseBodyString)).build();
    }


    private String bodyToString(final Request request) {

        try {
            final Buffer buffer = new Buffer();
            request.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        } catch (NullPointerException e) {
            return "did not work nullPointer";
        } catch (OutOfMemoryError e) {
            return "OutOfMemoryError ";
        }
    }
}
