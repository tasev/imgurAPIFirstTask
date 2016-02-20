package firsttask.imgur.global.matrix.imgurapitaskonefrommatrix.api;


import firsttask.imgur.global.matrix.imgurapitaskonefrommatrix.model.ImageGalleryModel;
import retrofit.Call;
import retrofit.http.GET;

public interface ApiService {

    @GET("/3/gallery/r/pics/")
    Call<ImageGalleryModel> getImagesViral();

}
