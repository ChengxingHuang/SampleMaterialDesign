package com.huang.samplematerialdesign;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by huang on 2018/5/30.
 */

public class ListFragment extends Fragment implements RecyclerViewAdapter.RecyclerViewItemClick {

    private ArrayList<FileInfo> mFileInfoList = new ArrayList<>();
    private Context mContext;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFab;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    private int mEnterLevel;
    protected BackHandlerInterface mBackHandlerInterface;

    public interface BackHandlerInterface{
        void setListFragment(ListFragment fragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBackHandlerInterface = (BackHandlerInterface)getActivity();
        mContext = getActivity();
        initData();
    }

    @Override
    public void onStart() {
        super.onStart();
        mBackHandlerInterface.setListFragment(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        mRecyclerViewAdapter = new RecyclerViewAdapter(mContext, mFileInfoList, this);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.addItemDecoration(new RecyclerViewItemDecoration(mContext));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mFab = view.findViewById(R.id.floatingActionButton);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("huangcx", "fab click");
            }
        });

        return view;
    }

    @Override
    public void onClickPrepare(RecyclerViewAdapter.MainViewHolder holder, View view, int position) {
        mEnterLevel++;
    }

    @Override
    public void onClickDone(RecyclerViewAdapter.MainViewHolder holder, View view, int position) {

    }

    protected void initData() {
        FileInfo fileInfo = new FileInfo(mContext, "/storage/emulated/0/Moviesaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaae");
        mFileInfoList.add(fileInfo);
        fileInfo = new FileInfo(mContext, "/storage/emulated/0/Android");
        mFileInfoList.add(fileInfo);
        fileInfo = new FileInfo(mContext, "/storage/emulated/0/Download");
        mFileInfoList.add(fileInfo);
        fileInfo = new FileInfo(mContext, "/storage/emulated/0/Music");
        mFileInfoList.add(fileInfo);
        fileInfo = new FileInfo(mContext, "/storage/emulated/0/Pictures");
        mFileInfoList.add(fileInfo);
    }

    public boolean onBackPressed(){
        if(mEnterLevel > 0){
            Log.d("huangcx", "mEnterLevel = " + mEnterLevel);
            mEnterLevel--;
            return true;
        }
        return false;
    }
}