package com.maf.base.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.maf.adapter.BaseRecycleAdapter;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.CropSquareTransformation;
import jp.wasabeef.glide.transformations.CropTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.MaskTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import jp.wasabeef.glide.transformations.gpu.BrightnessFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.ContrastFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.InvertFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.KuwaharaFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.PixelationFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SepiaFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SketchFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SwirlFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.ToonFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation;
import maf.com.mafproject.R;

/**
 * 项目名称：maflibrary
 * 类描述：
 * 创建人：zgmao
 * 创建时间：2017/4/10
 * 修改人：zgmao
 * 修改时间：2017/4/10
 * 修改备注：
 * Created by zgmao on 2017/4/10.
 */
public class GlideImageAdapter extends BaseRecycleAdapter<Integer,
        ImageViewHolder> {
    private Context mContext;

    private List<Type> mDataSet;

    public enum Type {
        Defalult,
        Mask,
        NinePatchMask,
        CropTop,
        CropCenter,
        CropBottom,
        CropSquare,
        CropCircle,
        ColorFilter,
        Grayscale,
        RoundedCorners,
        Blur,
        Toon,
        Sepia,
        Contrast,
        Invert,
        Pixel,
        Sketch,
        Swirl,
        Brightness,
        Kuawahara,
        Vignette
    }

    public GlideImageAdapter(Context context, List<Integer> list) {
        super(context, list);
        mContext = context;
    }

    public GlideImageAdapter(Context context, List<Integer> list, List<Type>
            dataSet) {
        this(context, list);
        mDataSet = dataSet;
    }

    @Override
    protected int getResourceId() {
        return R.layout.item_image;
    }

    @Override
    protected ImageViewHolder getViewHolder(View view) {
        ImageViewHolder viewHolder = new ImageViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        int imageId = list.get(position);
        Type type = mDataSet.get(position);
        holder.textView.setText("item " + type.name());
        switch (type) {
            case Mask: {
                Glide.with(mContext)
                        .load(imageId)
                        .bitmapTransform(new CenterCrop(mContext),
                                new MaskTransformation(mContext, R.drawable
                                        .mask_starfish))
                        .into(holder.imageView);
                break;
            }
            case NinePatchMask: {
                Glide.with(mContext)
                        .load(imageId)
                        .bitmapTransform(new CenterCrop(mContext),
                                new MaskTransformation(mContext, R.drawable
                                        .mask_chat_right))
                        .into(holder.imageView);
                break;
            }
            case CropTop:
                Glide.with(mContext)
                        .load(imageId)
                        .bitmapTransform(
                                new CropTransformation(mContext, 300, 100,
                                        CropTransformation.CropType.TOP))
                        .into(holder.imageView);
                break;
            case CropCenter:
                Glide.with(mContext)
                        .load(imageId)
                        .bitmapTransform(new CropTransformation(mContext,
                                300, 100))
                        .into(holder.imageView);
                break;
            case CropBottom:
                Glide.with(mContext)
                        .load(imageId)
                        .bitmapTransform(
                                new CropTransformation(mContext, 300, 100,
                                        CropTransformation.CropType.BOTTOM))
                        .into(holder.imageView);

                break;
            case CropSquare:
                Glide.with(mContext)
                        .load(imageId)
                        .bitmapTransform(new CropSquareTransformation(mContext))
                        .into(holder.imageView);
                break;
            case CropCircle:
                Glide.with(mContext)
                        .load(imageId)
                        .bitmapTransform(new CropCircleTransformation(mContext))
                        .into(holder.imageView);
                break;
            case ColorFilter:
                Glide.with(mContext)
                        .load(imageId)
                        .bitmapTransform(new ColorFilterTransformation
                                (mContext, Color.argb(80, 255, 0, 0)))
                        .into(holder.imageView);
                break;
            case Grayscale:
                Glide.with(mContext)
                        .load(imageId)
                        .bitmapTransform(new GrayscaleTransformation(mContext))
                        .into(holder.imageView);
                break;
            case RoundedCorners:
                Glide.with(mContext)
                        .load(imageId)
                        .bitmapTransform(new RoundedCornersTransformation
                                (mContext, 30, 0,
                                        RoundedCornersTransformation
                                                .CornerType.BOTTOM))
                        .into(holder.imageView);
                break;
            case Blur:
                Glide.with(mContext)
                        .load(imageId)
                        .bitmapTransform(new BlurTransformation(mContext, 25))
                        .into(holder.imageView);
                break;
            case Toon:
                Glide.with(mContext)
                        .load(imageId)
                        .bitmapTransform(new ToonFilterTransformation(mContext))
                        .into(holder.imageView);
                break;
            case Sepia:
                Glide.with(mContext)
                        .load(imageId)
                        .bitmapTransform(new SepiaFilterTransformation
                                (mContext))
                        .into(holder.imageView);
                break;
            case Contrast:
                Glide.with(mContext)
                        .load(imageId)
                        .bitmapTransform(new ContrastFilterTransformation
                                (mContext, 2.0f))
                        .into(holder.imageView);
                break;
            case Invert:
                Glide.with(mContext)
                        .load(imageId)
                        .bitmapTransform(new InvertFilterTransformation
                                (mContext))
                        .into(holder.imageView);
                break;
            case Pixel:
                Glide.with(mContext)
                        .load(imageId)
                        .bitmapTransform(new PixelationFilterTransformation
                                (mContext, 20))
                        .into(holder.imageView);
                break;
            case Sketch:
                Glide.with(mContext)
                        .load(imageId)
                        .bitmapTransform(new SketchFilterTransformation
                                (mContext))
                        .into(holder.imageView);
                break;
            case Swirl:
                Glide.with(mContext)
                        .load(imageId)
                        .bitmapTransform(
                                new SwirlFilterTransformation(mContext, 0.5f,
                                        1.0f, new PointF(0.5f, 0.5f)))
                        .into(holder.imageView);
                break;
            case Brightness:
                Glide.with(mContext)
                        .load(imageId)
                        .bitmapTransform(new BrightnessFilterTransformation
                                (mContext, 0.5f))
                        .into(holder.imageView);
                break;
            case Kuawahara:
                Glide.with(mContext)
                        .load(imageId)
                        .bitmapTransform(new KuwaharaFilterTransformation
                                (mContext, 25))
                        .into(holder.imageView);
                break;
            case Vignette:
                Glide.with(mContext)
                        .load(imageId)
                        .bitmapTransform(new VignetteFilterTransformation
                                (mContext, new PointF(0.5f, 0.5f),
                                        new float[]{0.0f, 0.0f, 0.0f}, 0f, 0.75f))
                        .into(holder.imageView);
                break;
            default:
                Glide.with(context).load(imageId).placeholder(R.drawable
                        .ic_launcher)
                        .error(R.drawable.ic_launcher).animate(R.anim.glide_anim)
                        .centerCrop().fitCenter().into(holder.imageView);
                break;
        }

    }
}
