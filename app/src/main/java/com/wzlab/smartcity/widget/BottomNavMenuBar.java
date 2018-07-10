package com.wzlab.smartcity.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wzlab.smartcity.activity.R;
import com.wzlab.smartcity.utils.PixelUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wzlab on 2018/7/9.
 */

public class BottomNavMenuBar extends LinearLayout{
    private static final String TAG = BottomNavMenuBar.class.getSimpleName();
    private BottomNavMenuBar instance;

    private Context mContext;
    private List<MenuItem> menuList;
    private OnItemSelectedListener mOnItemSelectedListener;
    private OnItemReSelectedListener mOnItemReSelectedListener;

    private int[] mListIconRes;//未选中图标
    private int[] mListIconResSelected;//选中图标
    private int mIconWidth, mIconHeight;//图标大小

    private String[] mListText;//的文字
    private int mTextSize;//文字大小
    private int mTextColor = Color.GRAY;//未选中的颜色(默认灰色)
    private int mTextColorSelected = getResources().getColor(R.color.colorPrimary);//选中的颜色(默认红色)

    private int marginTop = 0;//文字和图标的距离

    private int mCount = 0;//菜单数量
    private int mCurrentPosition = 0;//选中的位置


    public BottomNavMenuBar(Context context) {
        this(context, null);
    }

    public BottomNavMenuBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomNavMenuBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        marginTop = PixelUtil.dpToPx(context, 5);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        instance = this;

        setOrientation(HORIZONTAL);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BottomNavMenuBar, defStyleAttr, 0);
        if (typedArray != null) {
            //菜单数量
            mCount = typedArray.getInt(R.styleable.BottomNavMenuBar_menuCount, 0);
            if (mCount > 0) {
                initMenu();
            } else {
                Log.e(TAG, "the menuCount mast greater than 0");
            }

            typedArray.recycle();
        }
    }


    //初始化布局
    private void initMenu() {
        menuList = new ArrayList<>(mCount);
        for (int i = 0; i < mCount; i++) {
            final MenuItem menuItem = new MenuItem(mContext);
            LayoutParams params = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
            params.gravity = Gravity.CENTER;
            menuItem.setLayoutParams(params);
            menuItem.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getSelectedPosition(menuItem);
                    if (position == mCurrentPosition) {  //已经是选中状态
                        BottomNavMenuBar.this.OnItemReClick(position);
                    } else {
                        BottomNavMenuBar.this.OnItemClick(position);
                    }
                }
            });
            menuList.add(menuItem);
            addView(menuItem);
        }
    }


    public interface OnItemSelectedListener {
        void onItemSelected(int position);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.mOnItemSelectedListener = listener;
    }

    public interface OnItemReSelectedListener {
        void onItemReSelected(int position);
    }

    public void setOnItemReSelectedListener(OnItemReSelectedListener listener) {
        this.mOnItemReSelectedListener = listener;
    }

    private void OnItemClick(int position) {
        for (int i = 0; i < mCount; i++) {
            if (i == position) {
                setSelected(position);
            }
        }
        if (mOnItemSelectedListener != null) {
            mOnItemSelectedListener.onItemSelected(position);
        }
    }

    private void OnItemReClick(int position) {
        if (mOnItemReSelectedListener != null) {
            mOnItemReSelectedListener.onItemReSelected(position);
        }
    }

    /**
     * 更改选中状态
     *
     * @param position
     */
    public BottomNavMenuBar setSelected(int position) {
        if (position >= 0 && position < mCount) {
            mCurrentPosition = position;
            refreshState();
        } else {
            Log.e(TAG, "setSelected(int position) ---> the position is not correct");
        }

        return instance;
    }

    /**
     * 刷新状态
     */
    public void refreshState() {
        for (int i = 0; i < mCount; i++) {
            MenuItem menuItem = menuList.get(i);
            if (i == mCurrentPosition) {
                menuItem.setSelected(true);
            } else {
                menuItem.setSelected(false);
            }
        }
    }

    /**
     * 设置未选中的图片
     *
     * @param listIconRes
     * @return
     */
    public BottomNavMenuBar setIconRes(int[] listIconRes) {
        this.mListIconRes = listIconRes;
        if (mCount != mListIconRes.length) {
            Log.e(TAG, "the iconRes length is not equals count");
        } else {
            for (int i = 0; i < mCount; i++) {
                if (mListIconRes[i] != 0) {
                    menuList.get(i).setIcon(mListIconRes[i]);
                }
            }
        }
        return instance;
    }


    /**
     * 设置选中的图片
     *
     * @return
     */
    public BottomNavMenuBar setIconResSelected(int[] listIconResSelected) {
        this.mListIconResSelected = listIconResSelected;
        if (mCount != mListIconResSelected.length) {
            Log.e(TAG, "the iconResSelected length is not equals count");
        } else {
            for (int i = 0; i < mCount; i++) {
                if (mListIconResSelected[i] != 0) {
                    menuList.get(i).setIconSelected(mListIconResSelected[i]);
                }
            }
        }
        return instance;
    }

    /**
     * 设置图片大小
     *
     * @param iconWidth
     * @param iconHeight
     * @return
     */
    public BottomNavMenuBar setIconSize(int iconWidth, int iconHeight) {
        this.mIconWidth = iconWidth;
        this.mIconHeight = iconHeight;
        if (mCount != mListIconResSelected.length) {
            Log.e(TAG, "the iconResSelected length is not equals count");
        } else {
            for (int i = 0; i < mCount; i++) {
                if (mListIconResSelected[i] != 0) {
                    menuList.get(i).setIconSize(mIconWidth, mIconHeight);
                }
            }
        }
        return instance;
    }

    /**
     * 设置某一个图片大小
     *
     * @param iconWidth
     * @param iconHeight
     * @return
     */
    public BottomNavMenuBar setIconSize(int position, int iconWidth, int iconHeight) {
        this.mIconWidth = iconWidth;
        this.mIconHeight = iconHeight;
        if (mCount != mListIconResSelected.length) {
            Log.e(TAG, "the iconResSelected length is not equals count");
        } else {
            if (position >= 0 && position < mCount) {
                menuList.get(position).setIconSize(mIconWidth, mIconHeight);
            } else {
                Log.e(TAG, "setIconSize(int position, int iconWidth, int iconHeight) ---> the position  is not correct");
            }
        }

        return instance;
    }


    /**
     * 设置文字
     *
     * @param listText
     * @return
     */
    public BottomNavMenuBar setTextRes(String[] listText) {
        this.mListText = listText;
        if (mCount != mListText.length) {
            Log.e(TAG, "the textRes length is not equals count");
        } else {
            for (int i = 0; i < mCount; i++) {
                if (mListText[i] != null) {
                    menuList.get(i).setText(mListText[i]);
                }
            }
        }
        return instance;
    }


    /**
     * 设置文字大小
     *
     * @param textSize
     */
    public BottomNavMenuBar setTextSize(int textSize) {
        this.mTextSize = textSize;
        if (mCount != mListText.length) {
            Log.e(TAG, "the textRes length is not equals count");
        } else {
            for (int i = 0; i < mCount; i++) {
                if (mListText[i] != null) {
                    menuList.get(i).setTextSize(mTextSize);
                }
            }
        }

        return instance;
    }

    /**
     * 设置某一位置文字大小
     *
     * @param position
     * @param textSize
     * @return
     */
    public BottomNavMenuBar setTextSize(int position, int textSize) {
        if (mCount != mListText.length) {
            Log.e(TAG, "the textRes length is not equals count");
        } else {
            if (position >= 0 && position < mCount) {
                menuList.get(position).setTextSize(mTextSize);
            } else {
                Log.e("TAG", "setTextSize(int position, int textSize) ----> the position  is not correct");
            }

        }
        return instance;
    }


    /**
     * 文字颜色
     *
     * @param textColor
     */
    public BottomNavMenuBar setTextColor(int textColor) {
        this.mTextColor = textColor;
        if (mCount != mListText.length) {
            Log.e(TAG, "the textRes length is not equals count");
        } else {
            for (int i = 0; i < mCount; i++) {
                if (mListText[i] != null) {
                    menuList.get(i).setTextColor(textColor);
                }
            }
        }

        return instance;
    }

    public BottomNavMenuBar setTextColor(int position, int textColor) {
        if (mCount != mListText.length) {
            Log.e(TAG, "the textRes length is not equals count");
        } else {
            if (position >= 0 && position < mCount) {
                menuList.get(position).setTextColor(textColor);
            } else {
                Log.e(TAG, "setTextColor(int position, int textColor) ---> the position  is not correct ");
            }
        }

        return instance;
    }


    /**
     * 选中的文字颜色
     *
     * @param textColorSelected
     */
    public BottomNavMenuBar setTextColorSelected(int textColorSelected) {
        this.mTextColorSelected = textColorSelected;
        if (mCount != mListText.length) {
            Log.e(TAG, "the textRes length is not equals count");
        } else {
            for (int i = 0; i < mCount; i++) {
                if (mListText[i] != null) {
                    menuList.get(i).setSelectedTextColor(textColorSelected);
                }
            }
        }
        return instance;
    }

    public BottomNavMenuBar setTextColorSelected(int position, int textColorSelected) {
        if (mCount != mListText.length) {
            Log.e(TAG, "the textRes length is not equals count");
        } else {
            if (position >= 0 && position < mCount) {
                menuList.get(position).setSelectedTextColor(textColorSelected);
            } else {
                Log.e(TAG, "setTextColorSelected(int position, int textColorSelected) ---> the position  is not correct ");
            }
        }
        return instance;
    }

    /**
     * 图标和文字之间的距离
     *
     * @param marginTop
     */
    public BottomNavMenuBar setMarginTop(int marginTop) {
        this.marginTop = marginTop;
        if (mCount != mListText.length) {
            Log.e(TAG, "the textRes length is not equals count");
        } else {
            for (int i = 0; i < mCount; i++) {
                if (mListText[i] != null) {
                    menuList.get(i).setMarginTop(marginTop);
                }
            }
        }
        return instance;
    }

    public BottomNavMenuBar setMarginTop(int position, int marginTop) {
        if (mCount != mListText.length) {
            Log.e(TAG, "the textRes length is not equals count");
        } else {
            if (position >= 0 && position < mCount) {
                menuList.get(position).setMarginTop(marginTop);
            } else {
                Log.e(TAG, "setTextColorSelected(int position, int textColorSelected) ---> the position  is not correct ");
            }
        }
        return instance;
    }


    /**
     * 背景颜色
     *
     * @param backColor
     */
    public BottomNavMenuBar setBackColor(int backColor) {
        setBackgroundColor(backColor);
        return instance;
    }

    public BottomNavMenuBar setBackColor(int position, int backColor) {
        if (position >= 0 && position < mCount) {
            menuList.get(position).setBackgroundColor(backColor);
        } else {
            Log.e(TAG, "setBackColor(int position, int backColor) ---> the positon is not correct");
        }
        return instance;
    }


    /**
     * 获取点击的位置
     */
    public int getSelectedPosition(MenuItem item) {
        for (int i = 0; i < mCount; i++) {
            if (item == menuList.get(i)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 设置未读信息
     *
     * @param position
     * @return
     */
    public BottomNavMenuBar setMsg(int position, String msg) {
        if (position >= 0 && position < mCount) {
            menuList.get(position).setMsg(msg);
        } else {
            Log.d(TAG, "setMsg(int position, String msg) ---> the position is not correct");
        }
        return instance;

    }

    /**
     * 隐藏未读信息
     *
     * @param position
     * @return
     */
    public BottomNavMenuBar hideMsg(int position) {
        if (position >= 0 && position < mCount) {
            menuList.get(position).hideMsg();
        } else {
            Log.d(TAG, "hideMsg(int position) ---> the position is not correct");
        }
        return instance;
    }


    /**
     * 显示红点
     *
     * @param position
     * @return
     */
    public BottomNavMenuBar showRedPoint(int position) {
        if (position >= 0 && position < mCount) {
            menuList.get(position).showRedPoint(true);
        } else {
            Log.d(TAG, "showRedPoint(int position) ---> the position is not correct");
        }
        return instance;

    }

    /**
     * 隐藏红点
     *
     * @param position
     * @return
     */
    public BottomNavMenuBar hideRedPoint(int position) {
        if (position >= 0 && position < mCount) {
            menuList.get(position).hideRedPoint();
        } else {
            Log.d(TAG, "hideRedPoint(int position) ---> the position is not correct");
        }
        return instance;

    }






    class MenuItem extends FrameLayout {

        private int mIconWidth, mIconHeight;//图片大小
        private int mIconRes = 0;//图片资源
        private int mIconResSelected = 0;//选中图片资源

        private int mTextColor = Color.GRAY;//未选中的颜色(默认灰色)
        private int mTextColorSelected = BottomNavMenuBar.this.getResources().getColor(R.color.colorPrimary);//选中的颜色(默认红色)
        private int mTextSize = 12;//字体大小
        private String mText = "";//文字


        private View mRootView;
        private ImageView ivIcon;//图标
        private TextView tvText;//文字
        private TextView tvUnReadNum;//未读
        private TextView tvRedPoiont;//红点

        private boolean isSelected = false;//是否选中
        private int marginTop = 0;//文字和图标的距离


        public MenuItem(Context context) {
            this(context, null);
        }

        public MenuItem(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public MenuItem(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            intiView(context);
        }

        private void intiView(Context context) {
            marginTop = PixelUtil.dpToPx(context, 5);
            //加载布局
            mRootView = LayoutInflater.from(context).inflate(R.layout.layout_bottom_nav_menu, this, false);

            ivIcon = (ImageView) mRootView.findViewById(R.id.iv_icon);
            tvText = (TextView) mRootView.findViewById(R.id.tv_text);
            tvUnReadNum = (TextView) mRootView.findViewById(R.id.tv_unred_num);
            tvRedPoiont = (TextView) mRootView.findViewById(R.id.tv_point);
            addView(mRootView);
        }

        /**
         * 刷新状态
         */
        private void refreshState() {
            if (isSelected) {
                if (mIconResSelected != 0 && mTextColorSelected != 0) {
                    ivIcon.setImageResource(mIconResSelected);
                    tvText.setTextColor(mTextColorSelected);
                }

            } else {
                if (mIconRes != 0 && mIconResSelected != 0) {
                    ivIcon.setImageResource(mIconRes);
                    tvText.setTextColor(mTextColor);
                }
            }
            if (mTextSize > 0) {
                tvText.setTextSize(mTextSize);
            }
            tvText.setText(mText);

            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) tvText.getLayoutParams();
            layoutParams.topMargin = marginTop;
            tvText.setLayoutParams(layoutParams);
        }

        /**
         * 设置选中状态
         *
         * @param isSelected
         */
        public void setSelected(boolean isSelected) {
            this.isSelected = isSelected;
            refreshState();
        }


        /**
         * 设置图标
         *
         * @param res
         */
        public void setIcon(@DrawableRes int res) {
            this.mIconRes = res;
        }

        /**
         * 设置选中图标
         *
         * @param res
         */
        public void setIconSelected(@DrawableRes int res) {
            this.mIconResSelected = res;
        }

        /**
         * 设置图标大小
         *
         * @param width
         * @param height
         */
        public void setIconSize(int width, int height) {
            this.mIconWidth = width;
            this.mIconHeight = height;
            LayoutParams params = (LayoutParams) ivIcon.getLayoutParams();
            params.width = mIconWidth;
            params.height = mIconHeight;
            ivIcon.setLayoutParams(params);
        }


        /**
         * 设置文字
         *
         * @param text
         */
        public void setText(String text) {
            this.mText = text;
        }

        /**
         * 设置文字大小
         *
         * @param size
         */
        public void setTextSize(int size) {
            this.mTextSize = size;
        }

        /**
         * 文字颜色
         *
         * @param color
         */

        public void setTextColor(int color) {
            this.mTextColor = color;
        }


        /**
         * 选中的文字颜色
         *
         * @param color
         */
        public void setSelectedTextColor(int color) {
            this.mTextColorSelected = color;
        }


        /**
         * 显示提示
         *
         * @param msg
         */
        public void setMsg(String msg) {
            tvUnReadNum.setVisibility(VISIBLE);
            tvUnReadNum.setText(msg);
            tvRedPoiont.setVisibility(GONE);
        }

        /**
         * 图标和文字之间的距离
         *
         * @param marginTop
         */
        public void setMarginTop(int marginTop) {
            this.marginTop = marginTop;
        }


        /**
         * 隐藏提示
         */
        public void hideMsg() {
            tvUnReadNum.setVisibility(GONE);
        }

        /**
         * 显示红点
         *
         * @param isShow
         */
        public void showRedPoint(boolean isShow) {
            tvRedPoiont.setVisibility(VISIBLE);
            tvUnReadNum.setVisibility(GONE);
        }

        /**
         * 隐藏红点
         */

        public void hideRedPoint() {
            tvRedPoiont.setVisibility(GONE);
        }

        /**
         * 隐藏所有提示
         */
        public void hideAllTips() {
            tvRedPoiont.setVisibility(GONE);
            tvUnReadNum.setVisibility(GONE);
        }


        /**
         * 获取是否选中
         *
         * @return
         */
        public boolean getIsSelected() {
            return isSelected;
        }


    }
}




