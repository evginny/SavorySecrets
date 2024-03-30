package com.zybooks.savorysecrets.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.widget.TextView;

import com.zybooks.savorysecrets.R;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoriesFragment extends Fragment {

    private List<String> categories = Arrays.asList("Breakfast", "Lunch", "Dinner", "Beverages", "Appetizers", "Desserts");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_categories, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.categories_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new CategoryAdapter(categories));

        return rootView;
    }

    private final Map<String, Integer> categoryImages = new HashMap<String, Integer>() {{
        put("Breakfast", R.drawable.breakfast);
        put("Lunch", R.drawable.lunch);
        put("Dinner", R.drawable.dinner);
        put("Beverages", R.drawable.beverages);
        put("Appetizers", R.drawable.appetizers);
        put("Desserts", R.drawable.dessert);
    }};
    private class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder> {

        private final List<String> mCategories;

        public CategoryAdapter(List<String> categories) {
            mCategories = categories;
        }

        @NonNull
        @Override
        public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CategoryHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(CategoryHolder holder, int position) {
            String category = mCategories.get(position);
            holder.bind(category);
        }

        @Override
        public int getItemCount() {
            return mCategories.size();
        }
    }

        private class CategoryHolder extends RecyclerView.ViewHolder {

            private final TextView mNameTextView;

            public CategoryHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.list_item_recipe, parent, false));
                mNameTextView = itemView.findViewById(R.id.recipe_name);
                ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
                layoutParams.height = parent.getHeight() / categories.size();
                itemView.setLayoutParams(layoutParams);
            }

            public void bind(String category) {
                // mNameTextView.setText(category); // This line was removed
                int imageResource = categoryImages.get(category); // get the image resource ID
                itemView.setBackgroundResource(imageResource); // set it as the background
                itemView.setOnClickListener(v -> {
                    Bundle args = new Bundle();
                    args.putString("category", category);
                    Navigation.findNavController(v).navigate(R.id.action_categories_to_list, args);
                });
            }
        }



    }
