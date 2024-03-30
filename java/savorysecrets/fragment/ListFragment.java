package com.zybooks.savorysecrets.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zybooks.savorysecrets.R;
import com.zybooks.savorysecrets.entity.Recipe;
import com.zybooks.savorysecrets.viewmodel.ListFragmentViewModel;
import com.zybooks.savorysecrets.viewmodel.RecipeViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListFragment extends Fragment {
    private RecipeViewModel mRecipeViewModel;
    private String mCategory;
    private RecipeAdapter mRecipeAdapter;

    private ListFragmentViewModel mListFragmentViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("category", mCategory);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        mListFragmentViewModel = new ViewModelProvider(this).get(ListFragmentViewModel.class);

        if (getArguments() != null) {
            mCategory = getArguments().getString("category");
            mListFragmentViewModel.setCategory(mCategory);
        } else {
            mCategory = mListFragmentViewModel.getCategory();
        }

        FloatingActionButton addRecipeFab = view.findViewById(R.id.add_recipe_fab);
        addRecipeFab.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putString("category", mCategory);
            Navigation.findNavController(v).navigate(R.id.action_list_to_add_recipe, args);
        });

        View.OnClickListener onClickListener = itemView -> {
            int selectedRecipeId = (int) itemView.getTag();
            Bundle args = new Bundle();
            args.putInt(DetailFragment.ARG_RECIPE_ID, selectedRecipeId);

            View detailFragmentContainer = view.findViewById(R.id.detail_frag_container);
            if (detailFragmentContainer == null) {
                Navigation.findNavController(itemView).navigate(R.id.show_item_detail, args);
            } else {
                Navigation.findNavController(detailFragmentContainer).navigate(R.id.fragment_detail, args);
            }
        };

        RecyclerView recyclerView = view.findViewById(R.id.recipe_list);
        mRecipeAdapter = new RecipeAdapter(new ArrayList<>(), onClickListener);
        recyclerView.setAdapter(mRecipeAdapter);

        DividerItemDecoration divider = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(divider);

        mRecipeViewModel.getRecipesByCategory(mCategory).observe(getViewLifecycleOwner(), recipes -> {
            mRecipeAdapter.updateList(recipes);
        });

        SearchView searchView = view.findViewById(R.id.search_recipes);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    mRecipeViewModel.getRecipesByCategory(mCategory).observe(getViewLifecycleOwner(), recipes -> {
                        mRecipeAdapter.updateList(recipes);
                    });
                } else {
                    mRecipeViewModel.getRecipesByName(newText).observe(getViewLifecycleOwner(), recipes -> {
                        mRecipeAdapter.updateList(recipes);
                    });
                }
                return true;
            }
        });
    }

    private class RecipeAdapter extends RecyclerView.Adapter<RecipeHolder> {
        private List<Recipe> mRecipes;
        private final View.OnClickListener mOnClickListener;

        public RecipeAdapter(List<Recipe> recipes, View.OnClickListener onClickListener) {
            mRecipes = recipes;
            mOnClickListener = onClickListener;
        }

        @NonNull
        @Override
        public RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new RecipeHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(RecipeHolder holder, int position) {
            Recipe recipe = mRecipes.get(position);
            holder.bind(recipe);
            holder.itemView.setTag(recipe.getId());
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mRecipes.size();
        }

        public void updateList(List<Recipe> newRecipes) {
            mRecipes = newRecipes;
            notifyDataSetChanged();
        }
    }

    private static class RecipeHolder extends RecyclerView.ViewHolder {
        private final TextView mNameTextView;

        public RecipeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_recipe, parent, false));
            mNameTextView = itemView.findViewById(R.id.recipe_name);
        }

        public void bind(Recipe recipe) {
            mNameTextView.setText(recipe.getName());
        }
    }
}
