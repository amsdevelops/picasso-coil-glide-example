package com.devsoldatenkov.picassoglidecoil

import android.graphics.Bitmap
import android.graphics.Paint
import android.os.Build.VERSION.SDK_INT
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load
import coil.request.ImageRequest
import coil.transform.BlurTransformation
import coil.transform.CircleCropTransformation
import coil.transform.GrayscaleTransformation
import coil.transform.RoundedCornersTransformation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.devsoldatenkov.picassoglidecoil.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import com.squareup.picasso.Transformation

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val catUrl = "https://cdn22.img.ria.ru/images/07e5/06/18/1738448523_0:21:864:669_1920x0_80_0_0_9920bbedf3cb1a14b1358e0677d01106.jpg"
        val gifUrl = "https://upload.wikimedia.org/wikipedia/ru/archive/6/6b/20210505175821%21NyanCat.gif"
        //Picasso
        Picasso.get()
            .load(gifUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .resize(250, 250)
            .centerCrop()
//            .transform(object : Transformation {
//                override fun transform(source: Bitmap?): Bitmap {
//                    source.
//                }
//
//                override fun key(): String = "key"
//
//            })
            .into(binding.image)

        //Coil
        //Coil is an acronym for: Coroutine Image Loader.
        val request = ImageRequest.Builder(this)
            .data(catUrl)
            .crossfade(true)
//            .transformations(BlurTransformation(this, 2f, 6f))
//            .transformations(CircleCropTransformation())
//            .transformations(GrayscaleTransformation())
//            .transformations(RoundedCornersTransformation(20f))
            .target(binding.image)
            .build()

        val imageLoader = ImageLoader.Builder(context = this)
            .componentRegistry {
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder(this@MainActivity))
                } else {
                    add(GifDecoder())
                }
            }
            .build()

        imageLoader.enqueue(request)

        binding.image.load(gifUrl) {
            crossfade(1000)
            placeholder(R.drawable.ic_launcher_foreground)
            transformations(CircleCropTransformation())
        }

        //Glide
        //lifecycle aware onStart onStop
        //Restart on connection reestablished
        Glide.with(this)
            .load(gifUrl)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade(2000))
//            .transform(CircleCrop())
            .transform(MultiTransformation(CenterCrop(), RoundedCorners(20)))
            .transform()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(binding.image)


    }
}