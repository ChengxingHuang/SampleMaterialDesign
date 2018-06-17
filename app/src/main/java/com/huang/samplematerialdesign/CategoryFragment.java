package com.huang.samplematerialdesign;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by huang on 2018/5/30.
 */

// TODO: 2018/6/12 用notifyItemChanged会有刷新效果，notifyDataSetChanged则不会
// 更新recyclerView数据
// mCategoryCount[1] = 100;
// mCategoryRecyclerViewAdapter.notifyItemChanged(1);
// mCategoryRecyclerViewAdapter.notifyDataSetChanged();

public class CategoryFragment extends Fragment {

    private static final int[] CATEGORY_ICON = {R.drawable.category_icon_image, R.drawable.category_icon_music, R.drawable.category_icon_video,
            R.drawable.category_icon_document, R.drawable.category_icon_apk, R.drawable.category_icon_zip,
            R.drawable.category_icon_favorite, R.drawable.category_icon_qq, R.drawable.category_icon_wechat,};
    private static final int[] CATEGORY_TITLE = {R.string.category_image, R.string.category_music, R.string.category_video,
            R.string.category_document, R.string.category_apk, R.string.category_archive,
            R.string.category_favorite, R.string.category_qq, R.string.category_wechat};

    private Context mContext;
    private RecyclerView mRecyclerView;
    private Button mPhoneChartBtn;

    private CategoryRecyclerViewAdapter mCategoryRecyclerViewAdapter;
    private RecyclerViewItemDecoration mRecyclerViewItemDecoration;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    private DefaultItemAnimator mDefaultItemAnimator;
    private GridLayoutManager mGridLayoutManager;

    private ArrayList<FileInfo> mFileInfoList = new ArrayList<>();
    private int[] mCategoryCount = new int[CATEGORY_ICON.length];
    private boolean mIsShowList = false;

    protected BackHandlerInterface mBackHandlerInterface;
    public interface BackHandlerInterface{
        void setCategoryFragment(CategoryFragment fragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mBackHandlerInterface = (BackHandlerInterface) getActivity();
        initData();
    }

    @Override
    public void onStart() {
        super.onStart();
        mBackHandlerInterface.setCategoryFragment(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        mRecyclerView = view.findViewById(R.id.category_recycler_view);
        mPhoneChartBtn = view.findViewById(R.id.phone_chart);
        mPhoneChartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mRecyclerViewItemDecoration = new RecyclerViewItemDecoration(mContext);
        mDefaultItemAnimator = new DefaultItemAnimator();
        mLinearLayoutManager = new LinearLayoutManager(mContext);
        mGridLayoutManager = new GridLayoutManager(mContext, 3);
        mCategoryRecyclerViewAdapter = new CategoryRecyclerViewAdapter();

        mRecyclerView.setAdapter(mCategoryRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        return view;
    }

    protected void initData() {
        FileInfo fileInfo = new FileInfo(mContext, "/storage/emulated/0");
        mFileInfoList.add(fileInfo);

        mRecyclerViewAdapter = new RecyclerViewAdapter(mContext, mFileInfoList, new RecyclerViewAdapter.RecyclerViewItemClick(){

            @Override
            public void onClickPrepare(RecyclerViewAdapter.MainViewHolder holder, View view, int position) {

            }

            @Override
            public void onClickDone(RecyclerViewAdapter.MainViewHolder holder, View view, int position) {

            }
        });
    }

    public boolean onBackPressed(){
        if(mIsShowList){
            showCategoryGrid();
            return true;
        }
        return false;
    }

    private void showCategoryList(){
        mIsShowList = true;
        mPhoneChartBtn.setVisibility(View.GONE);

        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(mRecyclerViewItemDecoration);
        mRecyclerView.setItemAnimator(mDefaultItemAnimator);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }

    private void showCategoryGrid(){
        mIsShowList = false;
        mPhoneChartBtn.setVisibility(View.VISIBLE);

        mRecyclerView.removeItemDecoration(mRecyclerViewItemDecoration);
        mRecyclerView.setAdapter(mCategoryRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
    }

    class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryViewHolder>{

        @NonNull
        @Override
        public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new CategoryViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item_category, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
            holder.mCategoryImage.setImageResource(CATEGORY_ICON[position]);
            holder.mCategoryText.setText(CATEGORY_TITLE[position]);
            holder.mCategoryCount.setText(mCategoryCount[position] + "");

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showCategoryList();
                }
            });
        }

        @Override
        public int getItemCount() {
            return CATEGORY_ICON.length;
        }
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        public ImageView mCategoryImage;
        public TextView mCategoryText;
        public TextView mCategoryCount;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            mCategoryImage = itemView.findViewById(R.id.category_image);
            mCategoryText = itemView.findViewById(R.id.category_title);
            mCategoryCount = itemView.findViewById(R.id.category_count);
        }
    }
}
