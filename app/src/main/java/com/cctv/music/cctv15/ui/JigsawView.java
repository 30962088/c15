package com.cctv.music.cctv15.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JigsawView extends View {

    public static interface OnJigsawViewChangeListener {

        public void onJigsawViewChange(boolean checked);

    }

    private static class JigsawTranslate {
        private int pos;
        private int x;
        private int y;

        public JigsawTranslate(int pos) {
            this.pos = pos;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    public JigsawView(Context context) {
        super(context);
    }

    public JigsawView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public JigsawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    private OnJigsawViewChangeListener onJigsawViewChangeListener;

    public void setOnJigsawViewChangeListener(OnJigsawViewChangeListener onJigsawViewChangeListener) {
        this.onJigsawViewChangeListener = onJigsawViewChangeListener;
    }

    private List<Bitmap> list;

    private int[] position = new int[9];

    private int blank = position.length - 1;

    private int size;

    private JigsawTranslate translate;

    public void init(Bitmap bp) {
        size = getWidth() / 3;
        for (int i = 0; i < position.length; i++) {
            position[i] = i;
        }
        translate = null;
        debugArray(position);
//            shuffleArray(position);
        Bitmap bitmap = Bitmap.createScaledBitmap(bp, getWidth(), getHeight(), true);
        list = createBitmaps(bitmap, size);
        invalidate();
        gameEnable = true;

    }


    private enum POSTION {
        UP, DOWN, LEFT, RIGHT
    }

    private boolean lock = false;

    private void translate(final int index, final POSTION postion) {
        if (!lock) {
            lock = true;
            ValueAnimator animator = ValueAnimator.ofInt(0, size);
            translate = new JigsawTranslate(index);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (int) animation.getAnimatedValue();
                    switch (postion) {
                        case LEFT:
                            translate.x = -value;
                            break;
                        case RIGHT:
                            translate.x = value;
                            break;
                        case UP:
                            translate.y = -value;
                            break;
                        case DOWN:
                            translate.y = value;
                            break;
                    }
                    invalidate();
                }
            });
            animator.setDuration(300);

            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    lock = false;
                    changePos(getBlankIndex(), index);
                    checkOk();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animator.start();
        }
    }

    private boolean gameEnable = false;

    public void setGameEnable(boolean gameEnable) {
        this.gameEnable = gameEnable;
    }

    private void checkOk() {
        if (onJigsawViewChangeListener != null) {
            boolean checked = true;
            for (int i = 0; i < position.length; i++) {
                if (i != position[i]) {
                    checked = false;
                    break;
                }
            }
            onJigsawViewChangeListener.onJigsawViewChange(checked);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                int j = position[i];
                if (j != blank) {
                    Bitmap bitmap = list.get(j);
                    int y = (i / 3) * size;
                    int x = (i % 3) * size;
                    if (translate != null && translate.pos == i) {
//                    Log.d("zzm","pos:"+translate.pos+",x:"+translate.x+",y:"+translate.y);
                        x += translate.x;
                        y += translate.y;
                    }
                    canvas.drawBitmap(bitmap, x, y, null);
                }
            }
        }

        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gameEnable && event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX(), y = event.getY();
            int col = getRect(x, getWidth()), row = getRect(y, getHeight());
            int pos = (row * 3) + col;
            /*Log.d("zzm","x:"+x+",y:"+y+",w:"+getWidth()+",h:"+getHeight());
            Log.d("zzm","row:"+row+",col:"+col);
            Log.d("zzm","pos:"+pos);*/
            onclick(pos, new Point(row, col));
        }
        return false;
    }

    private void onclick(int index, Point pos) {
        POSTION p = getPOSTION(pos);
        if (p != null) {
            translate(index, p);
        }
    }

    private void changePos(int p1, int p2) {
        int temp = position[p2];
        position[p2] = position[p1];
        position[p1] = temp;
        invalidate();
    }

    private POSTION getPOSTION(Point pos) {
        Point blankPos = getBlankPos();
        POSTION res = null;
        for (int i = 0; i <= 3; i++) {
            Point p1 = new Point(blankPos);
            switch (i) {
                case 0:
                    res = POSTION.UP;
                    p1.x++;
                    break;
                case 1:
                    res = POSTION.DOWN;
                    p1.x--;
                    break;
                case 2:
                    res = POSTION.LEFT;
                    p1.y++;
                    break;
                case 3:
                    p1.y--;
                    res = POSTION.RIGHT;
                    break;
            }
            if (!p1.equals(pos.x, pos.y)) {
                res = null;
            } else {
                break;
            }
        }
        return res;
    }

    private Point getBlankPos() {

        int index = getBlankIndex();

        int row = index % 3, col = index / 3;

        return new Point(col, row);

    }

    private int getBlankIndex() {
        int index = 0;
        for (int i = 0; i < position.length; i++) {
            if (position[i] == blank) {
                index = i;
            }
        }
        return index;
    }


    private int getRect(float x, int total) {
        int row = 0;
        for (int i = 0, j = 0; i < total; i += size, j++) {
            if (x >= i && x <= i + size) {
                row = j;
                break;
            }
        }
        return row;
    }

    private static List<Bitmap> createBitmaps(Bitmap bitmap, int size) {
        List<Bitmap> list = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            int x = (i % 3) * size;
            int y = (i / 3) * size;
            Bitmap b = Bitmap.createBitmap(bitmap, x, y, size, size);
            list.add(b);
        }
        return list;

    }

    private static void debugArray(int[] array) {
        array[array.length - 1] = array.length - 2;
        array[array.length - 2] = array.length - 1;
    }

    private static void shuffleArray(int[] array) {
        int index;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            index = random.nextInt(i + 1);
            if (index != i) {
                array[index] ^= array[i];
                array[i] ^= array[index];
                array[index] ^= array[i];
            }
        }
    }

}
