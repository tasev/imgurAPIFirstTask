package firsttask.imgur.global.matrix.imgurapitaskonefrommatrix.adapters;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;
import firsttask.imgur.global.matrix.imgurapitaskonefrommatrix.R;
import firsttask.imgur.global.matrix.imgurapitaskonefrommatrix.model.ImageGalleryModel;
import firsttask.imgur.global.matrix.imgurapitaskonefrommatrix.model.ImageModel;


public class ImageGaleryAdapter extends RecyclerView.Adapter<ImageGaleryAdapter.TextViewHolder> {
    private Context context;
   ImageGalleryModel imageGalleryModel;

    public ImageGaleryAdapter(Context context,ImageGalleryModel imageGalleryModel) {
        this.context = context;
        this.imageGalleryModel=imageGalleryModel;
    }

    @Override
    public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new TextViewHolder(view);
    }
    public void updateRecyclerView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();// notify adapter
                // Toast for task completion
            }
        }, 0);
    }
    @Override
    public void onBindViewHolder(final TextViewHolder holder, final int position) {
        ImageModel data= new ImageModel();
        try{
            data=imageGalleryModel.data.get(position);
            Picasso.with(context).load(data.link).fit().centerInside().into(holder.image);
        }catch (Exception e){}
    }

    @Override
    public int getItemCount() {
        try {
            return imageGalleryModel.data.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public class TargetPhoneGallery implements Target {
        private final WeakReference<ContentResolver> resolver;
        private final String name;
        private final String desc;

        public TargetPhoneGallery(ContentResolver r, String name, String desc) {
            this.resolver = new WeakReference<ContentResolver>(r);
            this.name = name;
            this.desc = desc;
        }

        @Override
        public void onPrepareLoad(Drawable arg0) {
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom arg1) {
            ContentResolver r = resolver.get();
            if (r != null) {
                MediaStore.Images.Media.insertImage(r, bitmap, name, desc);
            }
        }

        @Override
        public void onBitmapFailed(Drawable arg0) {
        }
    }


    public class TextViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.image) ImageView image;
        @Bind(R.id.container) RelativeLayout container;


        public TextViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }


    @Override
    public void onViewAttachedToWindow(TextViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(TextViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.container.clearAnimation();
    }
}