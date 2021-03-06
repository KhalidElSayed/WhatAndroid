package what.gui;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap.CompressFormat;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;

/**
 * @author Gwindow
 * @since Aug 2, 2012 9:31:43 PM
 */
public class MyImageLoader {
	private final ImageLoader imageLoader;
	private final Context context;
	private File cacheDir;
	private DisplayImageOptions options;
	private int defaultImageResource;

	public MyImageLoader(Context context) {
		this(context, R.drawable.noartwork);
	}

	public MyImageLoader(Context context, int defaultImageResource) {
		this.context = context;
		this.defaultImageResource = defaultImageResource;
		imageLoader = ImageLoader.getInstance();
		initImageLoader();
	}

	private void initImageLoader() {
		cacheDir = StorageUtils.getOwnCacheDirectory(context, "UniversalImageLoader/Cache");

		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		ImageLoaderConfiguration config =
				new ImageLoaderConfiguration.Builder(context)
						.memoryCacheExtraOptions(480, 800)
						.discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75)
						// Can slow ImageLoader, use it carefully (Better don't use it)
						.threadPoolSize(5).threadPriority(Thread.MIN_PRIORITY + 2).denyCacheImageMultipleSizesInMemory()
						.offOutOfMemoryHandling().memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
						// You can pass your own memory cache implementation
						.discCache(new UnlimitedDiscCache(cacheDir))
						// You can pass your own disc cache implementation
						.discCacheFileNameGenerator(new HashCodeFileNameGenerator())
						.defaultDisplayImageOptions(DisplayImageOptions.createSimple()).enableLogging().build();
		imageLoader.init(config);

		options =
				new DisplayImageOptions.Builder().showStubImage(defaultImageResource).showImageForEmptyUri(defaultImageResource)
						.cacheInMemory().cacheOnDisc().imageScaleType(ImageScaleType.POWER_OF_2).build();
		// transform(matrix);
	}

	public void displayImage(String imageUrl, ImageView imageView) {
		imageLoader.displayImage(imageUrl, imageView, options);
	}

	public void displayImage(String imageUrl, ImageView imageView, int resource, ImageLoadingListener imageLoadingListener) {
		options =
				new DisplayImageOptions.Builder().showStubImage(resource).showImageForEmptyUri(resource).cacheInMemory()
						.cacheOnDisc().imageScaleType(ImageScaleType.POWER_OF_2).build();
		imageLoader.displayImage(imageUrl, imageView, options, imageLoadingListener);
	}

	public void displayImage(String imageUrl, ImageView imageView, int resource) {
		options =
				new DisplayImageOptions.Builder().showStubImage(resource).showImageForEmptyUri(resource).cacheInMemory()
						.cacheOnDisc().imageScaleType(ImageScaleType.POWER_OF_2).build();
		imageLoader.displayImage(imageUrl, imageView, options);
	}

	public void displayImage(String imageUrl, ImageView imageView, ImageLoadingListener imageLoadingListener) {
		imageLoader.displayImage(imageUrl, imageView, options, imageLoadingListener);
	}
}
