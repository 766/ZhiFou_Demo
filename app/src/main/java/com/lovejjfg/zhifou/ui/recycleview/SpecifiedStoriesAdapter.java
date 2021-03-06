package com.lovejjfg.zhifou.ui.recycleview;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.lovejjfg.powerrecycle.RefreshRecycleAdapter;
import com.lovejjfg.powerrecycle.holder.NewBottomViewHolder;
import com.lovejjfg.zhifou.R;
import com.lovejjfg.zhifou.data.model.DailyStories;
import com.lovejjfg.zhifou.data.model.Story;
import com.lovejjfg.zhifou.ui.recycleview.holder.DateViewHolder;
import com.lovejjfg.zhifou.ui.recycleview.holder.HeaderViewPagerHolder;
import com.lovejjfg.zhifou.ui.recycleview.holder.StoryViewHolder;
import com.lovejjfg.zhifou.util.UIUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lovejjfg on 2016/2/21.
 */
public class SpecifiedStoriesAdapter extends RefreshRecycleAdapter<StoriesRecycleAdapter.Item> {
    public static final String TAG = SpecifiedStoriesAdapter.class.getSimpleName();
    @NonNull
    protected List<StoriesRecycleAdapter.Item> mTmpItem;
    @Nullable
    private OnItemClickListener listener;
    public HeaderViewPagerHolder headerViewPagerHolder;


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class Type {
        public static final int TYPE_HEADER = 3;
        public static final int TYPE_DATE = 1;
        public static final int TYPE_STORY = 2;
    }

    public SpecifiedStoriesAdapter() {
        mTmpItem = new ArrayList<>();
    }

    public void setList(DailyStories dailyStories) {
        list.clear();
        appendList(dailyStories);
    }

    public void appendList(DailyStories dailyStories) {
        int positionStart = list.size();

        if (positionStart == 0 && null != dailyStories.getTopStories()) {
            StoriesRecycleAdapter.Item headerItem = new StoriesRecycleAdapter.Item();
            headerItem.setType(Type.TYPE_HEADER);
            headerItem.setStories(dailyStories.getTopStories());
            list.add(headerItem);
        }
        StoriesRecycleAdapter.Item dateItem = new StoriesRecycleAdapter.Item();
        dateItem.setType(Type.TYPE_DATE);
        dateItem.setDate(dailyStories.getDate());
        list.add(dateItem);
        List<Story> stories = dailyStories.getStories();
        for (int i = 0, num = stories.size(); i < num; i++) {
            StoriesRecycleAdapter.Item storyItem = new StoriesRecycleAdapter.Item();
            storyItem.setType(Type.TYPE_STORY);
            storyItem.setStory(stories.get(i));
            list.add(storyItem);
        }

        int itemCount = list.size() - positionStart;

        if (positionStart == 0) {
            notifyDataSetChanged();
        } else {
            notifyItemRangeChanged(positionStart, itemCount + 1);
        }
    }


    @Override
    public RecyclerView.ViewHolder onViewHolderCreate(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case Type.TYPE_HEADER:
                itemView = UIUtils.inflate(R.layout.recycler_header_viewpager, parent);
                headerViewPagerHolder = new HeaderViewPagerHolder(itemView, list.get(0).getStories());
                return headerViewPagerHolder;
            case Type.TYPE_DATE:
                itemView = UIUtils.inflate(R.layout.recycler_item_date, parent);
                return new DateViewHolder(itemView);
            case Type.TYPE_STORY:
                itemView = UIUtils.inflate(R.layout.recycler_item_story, parent);
                return new StoryViewHolder(itemView);
            default:
                return null;
        }
    }

    @Override
    public void onViewHolderBind(final RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewTypes(position);
        StoriesRecycleAdapter.Item item;
        if (list.size() == 0 || position >= list.size() - 1) {
            return;
        }
        item = list.get(position);
        switch (viewType) {
            case Type.TYPE_HEADER:
                ((HeaderViewPagerHolder) holder).bindHeaderView();
                break;
            case Type.TYPE_DATE:
                if (item != null) {
                    ((DateViewHolder) holder).bindDateView(item.getDate());
                }
                break;
            case Type.TYPE_STORY:
                assert item != null;
                ((StoryViewHolder) holder).bindStoryView(item.getStory());
                final StoriesRecycleAdapter.Item finalItem = item;
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != listener) {
                            listener.onItemClick(holder.itemView, ((StoryViewHolder) holder).getImage(), Integer.valueOf(finalItem.getStory().getId()));
                        }
                    }
                });
                break;
        }
    }

    @Override
    public int getItemViewTypes(int position) {
        if (position < list.size()
                && list.size() > 0) {
            return list.get(position).getType();
        } else {
            return -1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onBottomViewHolderCreate(View loadMore) {
        return new NewBottomViewHolder(loadMore, null);
    }


//    @Override
//    public void appendList(Item item) {
//
//    }

    @Override
    public void onBottomViewHolderBind(RecyclerView.ViewHolder holder, int state) {
        ((NewBottomViewHolder) holder).bindDateView(state);
    }


    public StoriesRecycleAdapter.Item getItem(int position) {
        return list.get(position);
    }

    public String getTitleBeforePosition(int position) {
        mTmpItem.clear();
        //subList [0 , position)
        mTmpItem.addAll(list.subList(0, position + 1));
        Collections.reverse(mTmpItem);
        for (StoriesRecycleAdapter.Item item : mTmpItem) {
            if (item.getType() == Type.TYPE_DATE) {
                return item.getDate();
            }
        }
        //L.i(TAG, "POSITION = " + position);
        return "";
    }

    // TODO: 2016-03-01 这个方法需要重新写
    public String getTitleAtPosition(int position) {
        mTmpItem.clear();
        //subList [0 , position)
        mTmpItem.addAll(list.subList(0, position + 1));
        Collections.reverse(mTmpItem);
        for (StoriesRecycleAdapter.Item item : mTmpItem) {
            if (item.getType() == Type.TYPE_DATE) {
                return item.getDate();
            }
        }
        //L.i(TAG, "POSITION = " + position);
        return null;
    }

}
