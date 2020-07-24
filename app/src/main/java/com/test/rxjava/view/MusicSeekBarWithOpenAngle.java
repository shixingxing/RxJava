package com.test.rxjava.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * +--------------------------------------+
 * + @author Catherine Liu
 * +--------------------------------------+
 * + 2020/3/29 14:40
 * +--------------------------------------+
 * + Des: 音乐播放控制器 带有开口的
 * +--------------------------------------+
 */

public class MusicSeekBarWithOpenAngle extends androidx.appcompat.widget.AppCompatSeekBar {

    private static int INVALID_PROGRESS_VALUE = -1;
    private int max = 100;
    private int progress = 0;
    private int mProgressWidth = dpToPx(2);
    private int mArcWidth = dpToPx(4);
    /**
     * 开始角度的位置
     */
    private int mStartAngle = 0;
    /**
     * 结束开口的位置
     */
    private int mSweepAngle = 360;
    /**
     * 初始thumb的位置
     */
    private int mRotation = 0;
    private boolean mRoundedEdges = true;
    private boolean mClockwise = true;
    private boolean mEnabled = true;
    private int mArcRadius = 0;
    private float mProgressSweep = 0.0F;
    private RectF mArcRect = new RectF();
    private RectF outRect = new RectF();
    private Paint mArcPaint;
    private Paint mProgressPaint;
    private int mTranslateX;
    private int mTranslateY;
    private int mThumbXPos;
    private int mThumbYPos;
    /**
     * thumb 半径
     */
    private int thumbRadius = dpToPx(4);
    /**
     * thumb画笔
     */
    private Paint thumbPaint;

    private double mTouchAngle;
    private MusicPlayerProgressListener musicPlayerProgressListener;


    public int dpToPx(float dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * 内圆距离外圆的距离
     */
    private int insideCircleDistance = dpToPx(14);

    public MusicSeekBarWithOpenAngle(Context context) {
        super(context);
        this.init();

    }

    public MusicSeekBarWithOpenAngle(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();

    }

    public MusicSeekBarWithOpenAngle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();

    }


    /**
     * 刻度的一种颜色
     */
    private int gapLineColor = Color.parseColor("#DA88DA");

    /**
     * 刻度画笔
     */
    private Paint linePaint;

    private int arcColor = Color.parseColor("#00000000");

    private void init() {
        this.progress = Math.min(this.progress, this.max);
        this.progress = Math.max(this.progress, 0);
        this.mSweepAngle = Math.min(this.mSweepAngle, 360);
        this.mSweepAngle = Math.max(this.mSweepAngle, 0);
        this.mProgressSweep = (float) this.progress / (float) this.max * (float) this.mSweepAngle;
        this.mStartAngle = this.mStartAngle > 360 ? 0 : this.mStartAngle;
        this.mStartAngle = Math.max(this.mStartAngle, 0);
        /**
         * 外环画笔
         */
        this.mArcPaint = new Paint();
        this.mArcPaint.setColor(arcColor);
        this.mArcPaint.setAntiAlias(true);
        this.mArcPaint.setStyle(Paint.Style.STROKE);
        this.mArcPaint.setStrokeWidth((float) this.mArcWidth);

        /**
         * 进度画笔
         */
        this.mProgressPaint = new Paint();
        this.mProgressPaint.setColor(Color.WHITE);
        this.mProgressPaint.setAntiAlias(true);
        this.mProgressPaint.setStyle(Paint.Style.STROKE);
        this.mProgressPaint.setStrokeWidth((float) this.mProgressWidth);
        if (this.mRoundedEdges) {
            this.mArcPaint.setStrokeCap(Paint.Cap.ROUND);
            this.mProgressPaint.setStrokeCap(Paint.Cap.ROUND);
        }

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(1.5f);
        linePaint.setColor(gapLineColor);
        linePaint.setAntiAlias(true);


        thumbPaint = new Paint();
        thumbPaint.setStyle(Paint.Style.FILL);
        thumbPaint.setStrokeWidth(thumbRadius);
        thumbPaint.setColor(Color.parseColor("#4EC7FF"));
        thumbPaint.setAntiAlias(true);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!this.mClockwise) {
            canvas.scale(-1.0F, 1.0F, this.mArcRect.centerX(), this.mArcRect.centerY());
            canvas.scale(-3.0F, 3.0F, this.outRect.centerX(), this.outRect.centerY());
        }

        int arcStart = this.mStartAngle + -90 + this.mRotation;
        int arcSweep = this.mSweepAngle;
        canvas.drawArc(this.mArcRect, (float) arcStart, (float) arcSweep, false, this.mArcPaint);
        canvas.drawArc(this.mArcRect, (float) arcStart, this.mProgressSweep, false, this.mProgressPaint);
        //绘制长度为5的实线后再绘制长度2的空白区域，0位间隔
        linePaint.setPathEffect(new DashPathEffect(new float[]{dpToPx(5), dpToPx(2)}, 0));
        canvas.drawArc(this.outRect, (float) arcStart, (float) arcSweep, false, linePaint);
        canvas.drawCircle(this.mTranslateX - this.mThumbXPos, (this.mTranslateY - this.mThumbYPos), thumbRadius, thumbPaint);

    }

    private SweepGradient sweepGradient;


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int[] colors = {
                Color.parseColor("#4EC7FF"), Color.parseColor("#DD0ECA"),
                Color.parseColor("#4EC7FF")
        };
        //设置需要的颜色，首位颜色必须一致。
        sweepGradient = new SweepGradient(w / 2, h / 2, colors, new float[]{0f, 0.5f, 1f});
        mArcPaint.setShader(sweepGradient);
        mProgressPaint.setShader(sweepGradient);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = getDefaultSize(this.getSuggestedMinimumHeight(), heightMeasureSpec);
        int width = getDefaultSize(this.getSuggestedMinimumWidth(), widthMeasureSpec);
        int min = Math.min(width, height);
        float top = 0.0F;
        float left = 0.0F;
        this.mTranslateX = (int) ((float) width * 0.5F);
        this.mTranslateY = (int) ((float) height * 0.5F);
        int arcDiameter = min - this.getPaddingLeft();
        this.mArcRadius = arcDiameter / 2;
        top = (float) (height / 2 - arcDiameter / 2);
        left = (float) (width / 2 - arcDiameter / 2);
        this.mArcRect.set(left, top, left + (float) arcDiameter, top + (float) arcDiameter);
        /**
         * 内环
         */
//        this.outRect.set(left + insideCircleDistance, top + insideCircleDistance, left + (float) arcDiameter - insideCircleDistance, top + (float) arcDiameter - insideCircleDistance);
        this.outRect.set(left + insideCircleDistance, top + insideCircleDistance, left + (float) arcDiameter - insideCircleDistance, top + (float) arcDiameter - insideCircleDistance);
        int arcStart = (int) this.mProgressSweep + this.mStartAngle + this.mRotation + 90;
        this.mThumbXPos = (int) ((double) this.mArcRadius * Math.cos(Math.toRadians((double) arcStart)));
        this.mThumbYPos = (int) ((double) this.mArcRadius * Math.sin(Math.toRadians((double) arcStart)));
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.mEnabled) {
            this.getParent().requestDisallowInterceptTouchEvent(true);
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    this.updateOnTouch(event);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    this.setPressed(false);
                    this.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
                default:
                    break;
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        this.invalidate();
    }

    private void updateOnTouch(MotionEvent event) {
        this.setPressed(true);
        this.mTouchAngle = this.getTouchDegrees(event.getX(), event.getY());
        int progress = this.getProgressForAngle(this.mTouchAngle);
        this.onProgressRefresh(progress, true);
    }

    private double getTouchDegrees(float xPos, float yPos) {
        float x = xPos - (float) this.mTranslateX;
        float y = yPos - (float) this.mTranslateY;
        x = this.mClockwise ? x : -x;
        //Math.PI/2 =1.5707963267948966D
        double angle = Math.toDegrees(Math.atan2(y, x) + Math.PI / 2 - Math.toRadians(this.mRotation));
        if (angle < 0.0D) {
            angle += 360.0D;
        }

        angle -= (double) this.mStartAngle;
        return angle;
    }

    private int getProgressForAngle(double angle) {
        int touchProgress = (int) Math.round((double) this.valuePerDegree() * angle);
        touchProgress = touchProgress < 0 ? INVALID_PROGRESS_VALUE : touchProgress;
        touchProgress = touchProgress > this.max ? INVALID_PROGRESS_VALUE : touchProgress;
        return touchProgress;
    }

    private float valuePerDegree() {
        return (float) this.max / (float) this.mSweepAngle;
    }

    private void onProgressRefresh(int progress, boolean fromUser) {
        this.updateProgress(progress, fromUser);
    }

    private void updateThumbPosition() {
        int thumbAngle = (int) ((float) this.mStartAngle + this.mProgressSweep + (float) this.mRotation + 90.0F);
        this.mThumbXPos = (int) ((double) this.mArcRadius * Math.cos(Math.toRadians((double) thumbAngle)));
        this.mThumbYPos = (int) ((double) this.mArcRadius * Math.sin(Math.toRadians((double) thumbAngle)));
    }

    private void updateProgress(int progress, boolean fromUser) {
        if (progress != INVALID_PROGRESS_VALUE) {
            if (this.musicPlayerProgressListener != null && fromUser) {
                this.musicPlayerProgressListener.onProgress(progress);
            }

            progress = Math.min(progress, this.max);
            progress = Math.max(progress, 0);
            this.progress = progress;
            this.mProgressSweep = (float) progress / (float) this.max * (float) this.mSweepAngle;
            this.updateThumbPosition();
            this.invalidate();
        }
    }

    @Override
    public boolean isEnabled() {
        return this.mEnabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.mEnabled = enabled;
    }

    public void setChangeListener(MusicPlayerProgressListener musicPlayerProgressListener) {
        this.musicPlayerProgressListener = musicPlayerProgressListener;
    }

    @Override
    public void setProgress(int progress) {
        this.progress = progress;
        onProgressRefresh(progress, false);
        invalidate();
    }

    @Override
    public void setMax(int max) {
        this.max = max;
        invalidate();
    }

    private interface MusicPlayerProgressListener {
        void onProgress(int progress);
    }
}
