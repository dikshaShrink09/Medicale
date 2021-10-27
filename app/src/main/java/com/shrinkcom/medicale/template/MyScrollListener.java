package com.shrinkcom.medicale.template;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyScrollListener extends RecyclerView.OnScrollListener {

    private LinearLayoutManager mLayoutManager;

    public MyScrollListener(LinearLayoutManager manager) {
        this.mLayoutManager = manager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

        int visibleItemCount = mLayoutManager.getChildCount();
        int totalItemCount = mLayoutManager.getItemCount();
        int firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

        if (visibleItemCount + firstVisibleItem >= totalItemCount) {
            scrollTheShit(dy * -1, ((View) recyclerView.getChildAt(visibleItemCount - 1)).getWidth());
        }
    }

    public void scrollTheShit(int dy, int widthOfLastChild)
    {

    }


}