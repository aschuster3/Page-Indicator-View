package com.centaurii.views.pageindicator.lib;

import com.centaurii.views.pageindicator.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class PageIndicator extends View
{
    private Paint paint;
    
    private int numIndicators, 
                indicatorColor, 
                bgColor, 
                currentPosition;

    private float indicatorRadius;

    private Drawable currentViewImage, 
                     viewImage;

    private boolean useDrawables = false;

    public PageIndicator(Context context)
    {
        super(context);
        paint = new Paint();
        paint.setColor(0xFF000000);
        setNumIndicators(3);
        setIndicatorColor(0xFF000000);
        setBgColor(0xFFFFFFFF);
        setCurrentPosition(0);
        setIndicatorRadius(pxFromDp(5));
    }

    public PageIndicator(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(attrs);
    }

    public PageIndicator(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs)
    {
        paint = new Paint();
        paint.setColor(indicatorColor);
        
        TypedArray arr = getContext().obtainStyledAttributes(attrs,
                R.styleable.PageIndicator);

        setNumIndicators(arr
                .getInt(R.styleable.PageIndicator_num_indicators, 3));
        setIndicatorColor(arr.getColor(R.styleable.PageIndicator_color,
                0xFF000000));
        setBgColor(arr.getColor(R.styleable.PageIndicator_bgcolor, 0xFFFFFFFF));

        setIndicatorRadius(arr.getDimension(
                R.styleable.PageIndicator_indicator_radius, pxFromDp(5)));

        setCurrentViewImage(arr
                .getDrawable(R.styleable.PageIndicator_current_view_image));
        setViewImage(arr.getDrawable(R.styleable.PageIndicator_view_image));

        if (currentViewImage != null && viewImage != null)
        {
            useDrawables = true;
        } 
        else if (currentViewImage != null ^ viewImage != null)
        {
            Log.w("PageIndicator",
                    "You need to set the currentViewImage and viewImage to "
                            + "use drawables as your indicators.");
        }

        arr.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {

        int minWidth = (int) (numIndicators * ((indicatorRadius * 2) + 1));
        int minHeight = (int) (indicatorRadius * 2);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        width = widthSize;
        if(widthSize < minWidth)
        {
            Log.w("PageIndicator", "Does not meet minimum width requirements. " + 
                    "Your indicatorRadius might be too high for this view location.");
        }

        
        height = heightSize;
        if(heightSize < minHeight)
        {
            Log.w("PageIndicator", "Does not meet minimum height requirements. " + 
                    "Your indicatorRadius might be too high for this view location.");
        }

        setMeasuredDimension(width, height);
    }
    
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawColor(bgColor);
        
        if(useDrawables)
        {
            Log.w("PageIndicator", "Drawables not yet implemented");
        }
        else
        {
            float   verticalCenter = canvas.getHeight()/2f,
                    horizontalFraction = canvas.getWidth()/((float)numIndicators + 1);
            
            for(int i = 0; i < numIndicators; i++)
            {
                if(i == currentPosition)
                {
                    paint.setStyle(Paint.Style.FILL_AND_STROKE);
                }
                canvas.drawCircle(horizontalFraction * (i+1), verticalCenter, indicatorRadius, paint);
                
                if(i == currentPosition)
                {
                    paint.setStyle(Paint.Style.STROKE);
                }
            }
        }
    }

    public int getNumIndicators()
    {
        return numIndicators;
    }

    public void setNumIndicators(int numIndicators)
    {
        this.numIndicators = numIndicators;
    }

    public int getIndicatorColor()
    {
        return indicatorColor;
    }

    public void setIndicatorColor(int indicatorColor)
    {
        this.indicatorColor = indicatorColor;
        paint.setColor(indicatorColor);
    }

    public int getBgColor()
    {
        return bgColor;
    }

    public int getCurrentPosition()
    {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition)
    {
        this.currentPosition = currentPosition;
    }

    public float getIndicatorRadius()
    {
        return indicatorRadius;
    }

    public void setIndicatorRadius(float indicatorRadius)
    {
        this.indicatorRadius = indicatorRadius;
    }

    public void setBgColor(int bgColor)
    {
        this.bgColor = bgColor;
    }

    public Drawable getCurrentViewImage()
    {
        return currentViewImage;
    }

    public void setCurrentViewImage(Drawable currentViewImage)
    {
        this.currentViewImage = currentViewImage;
    }

    public Drawable getViewImage()
    {
        return viewImage;
    }

    public void setViewImage(Drawable viewImage)
    {
        this.viewImage = viewImage;
    }

    private float pxFromDp(float dp)
    {
        return dp * getContext().getResources().getDisplayMetrics().density;
    }
}
