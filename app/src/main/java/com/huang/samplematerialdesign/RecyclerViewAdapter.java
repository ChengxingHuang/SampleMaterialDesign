package com.huang.samplematerialdesign;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by huang on 2018/6/7.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MainViewHolder>{

    private Context mContext;
    private ArrayList<FileInfo> mFileInfoList;
    private RecyclerViewItemClick mRecyclerViewItemClick;

    public interface RecyclerViewItemClick{
        void onClickPrepare(final RecyclerViewAdapter.MainViewHolder holder, final View view, final int position);
        void onClickDone(final RecyclerViewAdapter.MainViewHolder holder, final View view, final int position);
    }

    public RecyclerViewAdapter(Context context, ArrayList<FileInfo> fileInfoList, RecyclerViewItemClick click){
        mContext = context;
        mFileInfoList = fileInfoList;
        mRecyclerViewItemClick = click;
    }

    @Override
    @NonNull
    public RecyclerViewAdapter.MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewAdapter.MainViewHolder holder, final int position) {
        holder.mFileTypeImage.setImageDrawable(mFileInfoList.get(position).getFileTypeImage());
        holder.mFileNameText.setText(mFileInfoList.get(position).getFileName());
        holder.mFileCountText.setText("0");
        holder.mLastDateText.setText(mFileInfoList.get(position).getFileLastModifiedDate());
        holder.mLastTimeText.setText(mFileInfoList.get(position).getFileLastModifiedTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerViewItemClick.onClickPrepare(holder, v, position);
                //打开文件或者文件夹
                mRecyclerViewItemClick.onClickDone(holder, v, position);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFileInfoList.size();
    }

    class MainViewHolder extends RecyclerView.ViewHolder {
        public ImageView mFileTypeImage;
        public TextView mFileNameText;
        public TextView mFileCountText;
        public TextView mLastDateText;
        public TextView mLastTimeText;
        public ImageView mOptionMenu;

        public MainViewHolder(View itemView) {
            super(itemView);
            mFileTypeImage = itemView.findViewById(R.id.file_type_image);
            mFileNameText = itemView.findViewById(R.id.file_name_text);
            mFileCountText = itemView.findViewById(R.id.file_count_text);
            mLastDateText = itemView.findViewById(R.id.last_change_date_text);
            mLastTimeText = itemView.findViewById(R.id.last_change_time_text);
            mOptionMenu = itemView.findViewById(R.id.option_menu_image);
        }
    }
}