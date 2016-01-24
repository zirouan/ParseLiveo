package br.com.liveo.parsepush.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.liveo.parsepush.R;
import br.com.liveo.parsepush.interfaces.OnItemClickListener;
import br.com.liveo.parsepush.model.Push;

/**
 * Created by Rudsonlive on 13/01/16.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private int mBackground;
    private Context mContext;
    private List<Push> mPushList;

    private static OnItemClickListener mOnItemClickListener;

    public MainAdapter(Context context, List<Push> pushList) {
        this.mContext = context;
        this.mPushList = pushList;

        if (context != null) {
            TypedValue mTypedValue = new TypedValue();
            context.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, mTypedValue, true);
            this.mBackground = mTypedValue.resourceId;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View mContainer;

        public TextView mTitle;
        public TextView mSubTitle;
        public TextView mCounter;

        public ViewHolder(View view) {
            super(view);

            mContainer = view;

            mTitle = (TextView) view.findViewById(R.id.txtTitle);
            mCounter= (TextView) view.findViewById(R.id.txtCounter);
            mSubTitle = (TextView) view.findViewById(R.id.txtSubTitle);

            mContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });
        }
    }

    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_item, parent, false);
        view.setBackgroundResource(this.mBackground);
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Push push = mPushList.get(position);

        holder.mTitle.setText(push.getName());
        holder.mSubTitle.setText(push.getAlert());

        holder.mCounter.setText(String.valueOf(position));
        GradientDrawable gradientDrawable = (GradientDrawable) holder.mCounter.getBackground();
        gradientDrawable.setColor(ContextCompat.getColor(mContext, position % 2 == 0 ? R.color.colorAccent : R.color.colorDivider));
    }

    @Override
    public int getItemCount() {
        return (mPushList == null ? 0 : mPushList.size());
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}
