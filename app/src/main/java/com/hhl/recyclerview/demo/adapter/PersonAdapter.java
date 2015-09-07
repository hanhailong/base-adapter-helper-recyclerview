package com.hhl.recyclerview.demo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hhl.recyclerview.demo.R;
import com.hhl.recyclerview.demo.entity.Person;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HanHailong on 15/9/7.
 */
public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {

    private List<Person> dataList = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Person person = dataList.get(position);
        if (person == null) return;
        holder.usernameTv.setText(person.getUsername());
        holder.ageTv.setText(person.getAge() + "岁");
        holder.descTv.setText(person.getDesc());
        ImageLoader.getInstance().displayImage(person.getAvatar(), holder.avatarIv);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView usernameTv, ageTv, descTv;
        ImageView avatarIv;

        public ViewHolder(View itemView) {
            super(itemView);
            usernameTv = (TextView) itemView.findViewById(R.id.tv_username);
            ageTv = (TextView) itemView.findViewById(R.id.tv_age);
            descTv = (TextView) itemView.findViewById(R.id.tv_desc);
            avatarIv = (ImageView) itemView.findViewById(R.id.iv_avatar);
        }
    }
}
