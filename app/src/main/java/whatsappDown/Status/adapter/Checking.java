package whatsappDown.Status.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.afollestad.sectionedrecyclerview.SectionedRecyclerViewAdapter;

/**
 * Created by navee on 3/4/2018.
 */

public class Checking extends SectionedRecyclerViewAdapter<RecyclerView.ViewHolder> {

    @Override
    public int getSectionCount() {
        return 0;
    }

    @Override
    public int getItemCount(int i) {
        return 0;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i, int i1, int i2) {

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }
}