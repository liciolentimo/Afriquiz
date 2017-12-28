package com.lentimosystems.afriquiz;

import android.content.Context;
import android.content.Intent;
import android.icu.util.ULocale;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lentimosystems.afriquiz.Interface.ItemClickListener;
import com.lentimosystems.afriquiz.ViewHolder.CategoryViewHolder;
import com.lentimosystems.afriquiz.Model.Category;
import com.lentimosystems.afriquiz.Common.Common;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.squareup.picasso.Picasso;

import java.util.Locale;


public class  CategoryFragment extends Fragment {
    View myFragment;

    RecyclerView listCategory;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Category,CategoryViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference categories;

        public static CategoryFragment newInstance(){
            CategoryFragment categoryFragment = new CategoryFragment();
            return categoryFragment;
        }

        //Ctrl + O

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        categories = database.getReference("Category");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
       myFragment = inflater.inflate(R.layout.fragment_category,container,false);

        listCategory = (RecyclerView)myFragment.findViewById(R.id.listCategory);
        listCategory.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(container.getContext());
        listCategory.setLayoutManager(layoutManager);
        
        loadCategories();
        
        
        return myFragment;
    }

    private void loadCategories() {
            adapter = new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(
                    Category.class,
                    R.layout.category_layout,
                    CategoryViewHolder.class,
                    categories
            ) {
                @Override
                protected void populateViewHolder(CategoryViewHolder viewHolder, final Category model, int position) {
                    viewHolder.category_name.setText(model.getName());
                    Picasso.with(getActivity())
                            .load(model.getImage())
                            .into(viewHolder.category_image);
                    viewHolder.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {
                            //Toast.makeText(getActivity(),String.format("%s|%s",adapter.getRef(position).getKey(),model.getName()) , Toast.LENGTH_SHORT).show();
                            Intent startGame = new Intent(getActivity(),Start.class);
                            Common.categoryId = adapter.getRef(position).getKey();
                            startActivity(startGame);

                        }
                    });
                    
                }
            };
            adapter.notifyDataSetChanged();
            listCategory.setAdapter(adapter);
    }
}
