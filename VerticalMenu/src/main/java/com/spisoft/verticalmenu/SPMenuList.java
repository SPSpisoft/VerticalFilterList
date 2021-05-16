package com.spisoft.verticalmenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SPMenuList extends LinearLayout {
    private Context mContext;
    private List<sitems> ListItems;
    private int SelItem = 0;
    private RecyclerView MenuList;
    private MenuAdapter menuAdapter;
    private OnItemSelectListener mItemSelectListener;
    private float DEFAULT_SIZE = 100f;
    private float itemWidth, itemHeight;
    private int DEFAULT_TEXT_COLOR = Color.BLACK;
    private int itemTextColor;
    private int DEFAULT_TEXT_SIZE = 14;
    private int itemTextSize;
    private int DEFAULT_ICON_SIZE = 50;
    private int iconSize;
    private Drawable itemBox = null, itemSelBox = null;
    private LinearLayoutManager layoutManager;

    public SPMenuList(Context context) {
        super(context);
        init(context, null);
    }

    public SPMenuList(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SPMenuList(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SPMenuList(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View rootView = inflate(context, R.layout.sps_menu_list, SPMenuList.this);
        MenuList = rootView.findViewById(R.id.menuList);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);

//        MenuList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false) {
//            @Override
//            public void onLayoutCompleted(final RecyclerView.State state) {
//                super.onLayoutCompleted(state);
//                final int firstVisibleItemPosition = findFirstVisibleItemPosition();
//                final int lastVisibleItemPosition = findLastVisibleItemPosition();
//                int itemsShown = lastVisibleItemPosition - firstVisibleItemPosition + 1;
//                //if all items are shown, hide the fast-scroller
//                fastScroller.setVisibility(adapter.getItemCount() > itemsShown ? View.VISIBLE : View.GONE);
//            }
//        });

        MenuList.setLayoutManager(layoutManager);

        mContext = context;

        //-------------------------------------------
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.SPMenuList, -1, 0);

        itemWidth = a.getDimension(R.styleable.SPMenuList_item_width, DEFAULT_SIZE);
        itemHeight = a.getDimension(R.styleable.SPMenuList_item_height, DEFAULT_SIZE);

        itemTextColor = a.getColor(R.styleable.SPMenuList_text_color, DEFAULT_TEXT_COLOR);
        itemTextSize = a.getDimensionPixelSize(R.styleable.SPMenuList_text_size, DEFAULT_TEXT_SIZE);

        iconSize = a.getDimensionPixelSize(R.styleable.SPMenuList_icon_size, DEFAULT_ICON_SIZE);

        int drawableItemBox = a.getResourceId(R.styleable.SPMenuList_item_box, -1);
        if(drawableItemBox >= 0) itemBox = AppCompatResources.getDrawable(context, drawableItemBox);

        int drawableItemSelBox = a.getResourceId(R.styleable.SPMenuList_item_sel_box, -1);
        if(drawableItemSelBox >= 0) itemSelBox = AppCompatResources.getDrawable(context, drawableItemSelBox);

        a.recycle();
        invalidate();
    }

    private SPMenuList selItem(int citem){
        this.SelItem = citem;
        invalidate();
//        menuAdapter.notifyDataSetChanged();
        listItems(ListItems);
//        layoutManager.smoothScrollToPosition(MenuList, null , citem);
//        layoutManager.setSmoothScrollbarEnabled(true);
        if(MenuList.getHeight() > 0)
            layoutManager.scrollToPositionWithOffset(citem, MenuList.getHeight()/2-(int)itemHeight);
        else
            layoutManager.scrollToPositionWithOffset(citem, 0);

        if(mItemSelectListener != null)
            mItemSelectListener.onItemSelect(citem);
        return this;
    }

    public int getItem(){
        return this.SelItem;
    }

    public SPMenuList listItems(List<sitems> items){
        this.ListItems = items;
//        if(itemHeight == 0) itemHeight = MenuList.getHeight()/ListItems.size();
        final float scale = getContext().getResources().getDisplayMetrics().density;
        menuAdapter = new MenuAdapter(mContext, ListItems, 0,
                (int) (itemWidth * scale + 0.5f), (int) (itemHeight * scale + 0.5f),
                itemTextColor, itemTextSize, iconSize, itemBox, itemSelBox, this.SelItem);
        MenuList.setAdapter(menuAdapter);
        menuAdapter.notifyDataSetChanged();
        menuAdapter.SetOnItemClickListener(new MenuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                selItem(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        invalidate();
        return this;
    }

    public interface OnItemSelectListener {
        void onItemSelect( int position);
    }

    public void SetOnItemClickListener(final OnItemSelectListener mItemSelectListener, int defaultSel) {
        this.mItemSelectListener = mItemSelectListener;
        selItem(defaultSel);
    }

    public static class sitems{
        private int mIcon;
        private String mIconUrl = null;
        private String mText;

        public sitems(int micon, String mtext){
            this.mIcon = micon;
            this.mText = mtext;
        }

        public sitems(String miconUrl, String mtext){
            this.mIconUrl = miconUrl;
            this.mText = mtext;
        }

        public String getmIconUrl() {
            return mIconUrl;
        }

        public void setmIconUrl(String mIconUrl) {
            this.mIconUrl = mIconUrl;
        }

        public int getmIcon() {
            return mIcon;
        }

        public void setmIcon(int mIcon) {
            this.mIcon = mIcon;
        }

        public String getmText() {
            return mText;
        }

        public void setmText(String mText) {
            this.mText = mText;
        }
    }
}
