package com.spisoft.verticalmenu;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {

    private final int mode;
    private final int iWidth, iHeight;
    private final Drawable itemBox, itemSelBox;
    private final int sPosition;
    private List<SPMenuList.sitems> list;
    public static int PropertyAdapterModeList = 0;
    public static int PropertyAdapterModeHead = 1;
    private OnItemClickListener mItemClickListener;
    private float txtSize;
    private Typeface textTypeface;
    private int txtColor, itemSelTextColor;
    private int icnSize, iconTopMargin;
    private int lastPosition = 0;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private RelativeLayout vBase;
        private ImageView vIcon;
        private TextView vTitle;

        public MyViewHolder(View view) {
            super(view);

            vBase = view.findViewById(R.id.rowBase);
            vIcon = view.findViewById(R.id.ivIcon);
            vTitle = view.findViewById(R.id.ivText);

            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(view, getLayoutPosition());
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemLongClick(view, getLayoutPosition());
                return true;
            }
            return false;
        }
    }

    public MenuAdapter(Context context, List<SPMenuList.sitems> CodeList, int mode, int itemWidth, int itemHeight, int textColor, float textSize,
                       Typeface textTypeface, int iconSize, int iconTopMargin, Drawable itmBox, Drawable itemSelBox, int itemSelTextColor, int selPosition) {
        this.context = context;

        this.list = CodeList;
        this.mode = mode;

        this.iWidth = itemWidth;
        this.iHeight = itemHeight;
        this.txtColor = textColor;
        this.itemSelTextColor = itemSelTextColor;
        this.txtSize = textSize;
        this.textTypeface = textTypeface;
        this.icnSize = iconSize;
        this.iconTopMargin = iconTopMargin;

        this.itemBox = itmBox;
        this.itemSelBox = itemSelBox;
        this.sPosition = selPosition;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (mode){
            default:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_menu_list, parent, false);
                break;
//            case 1:
//                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_peroperty_head, parent, false);
//                break;
        }

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.itemView.clearAnimation();

        SPMenuList.sitems sitem = list.get(position);
        ViewGroup.LayoutParams parm = new LinearLayout.LayoutParams(iWidth, iHeight);
        holder.vBase.setLayoutParams(parm);
        if(position == sPosition)
            holder.vBase.setBackground(itemSelBox);
        else
            holder.vBase.setBackground(itemBox);

        holder.vTitle.setText(sitem.getmText());
        if(sitem.getmIconUrl() != null){
            Glide.with(this.context)
                    .load(sitem.getmIconUrl())
                    .into(holder.vIcon);
        }
        else
            holder.vIcon.setImageResource(sitem.getmIcon());

        holder.vIcon.getLayoutParams().height = icnSize;
        holder.vIcon.getLayoutParams().width = icnSize;

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, iconTopMargin, 0, 0);
        holder.vIcon.setLayoutParams(lp);

        holder.vTitle.setSelected(true);
        if(position == sPosition)
            holder.vTitle.setTextColor(itemSelTextColor);
        else
            holder.vTitle.setTextColor(txtColor);
        holder.vTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, txtSize);
        if (textTypeface != null)
            holder.vTitle.setTypeface(textTypeface);

        Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        holder.itemView.startAnimation(animation);

        lastPosition = position;

    }

    @Override
    public int getItemCount() {
        if(list != null)
            return list.size();
        else
            return 0;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    // for both short and long click
    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

}
