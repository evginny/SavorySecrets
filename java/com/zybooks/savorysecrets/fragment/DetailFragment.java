package com.zybooks.savorysecrets.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zybooks.savorysecrets.R;
import com.zybooks.savorysecrets.entity.Recipe;
import com.zybooks.savorysecrets.repository.RecipeRepository;
import com.zybooks.savorysecrets.viewmodel.RecipeViewModel;

public class DetailFragment extends Fragment {

    private Button mDeleteButton;
    private Button mEditButton;
    private Recipe mCurrentRecipe;
    private RecipeViewModel mRecipeViewModel;
    private Recipe mRecipe;
    public static final String ARG_RECIPE_ID = "recipe_id";

    public DetailFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int recipeId = 1;

        // Get the recipe ID from the fragment arguments
        Bundle args = getArguments();
        if (args != null) {
            recipeId = args.getInt(ARG_RECIPE_ID);
        }
        mRecipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        mRecipeViewModel.getRecipe(recipeId).observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(@Nullable final Recipe recipe) {
                // Update the cached copy of the words in the adapter.
                if (recipe != null) {
                    mCurrentRecipe = recipe;
                    TextView nameTextView = getView().findViewById(R.id.recipe_name);
                    nameTextView.setText(recipe.getName());

                    TextView ingridientsTextView = getView().findViewById(R.id.recipe_ingridient);
                    ingridientsTextView.setText(recipe.getIngredients());

                    TextView instructionsTextView = getView().findViewById(R.id.recipe_instructions);
                    instructionsTextView.setText(recipe.getInstructions());
                }
            }
        });
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_detail, container, false);
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        mDeleteButton = view.findViewById(R.id.button_delete);
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentRecipe != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            mRecipeViewModel.delete(mCurrentRecipe);
                        }
                    }).start();

                    // Navigate back to the list
                    Navigation.findNavController(v).navigateUp();
                    // Create a bundle and put the category in it
//                    Bundle bundle = new Bundle();
//                    bundle.putString("category", mCurrentRecipe.getCategory());
//                    // Navigate back to the list, passing the bundle
//                    Navigation.findNavController(v).navigate(R.id.action_detailFragment_to_listFragment, bundle);
                }
            }
        });

        mEditButton = view.findViewById(R.id.button_edit);
        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentRecipe != null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(EditRecipeFragment.ARG_RECIPE_ID, mCurrentRecipe);
                    Navigation.findNavController(v).navigate(R.id.action_detailFragment_to_editRecipeFragment, bundle);
                }
            }
        });

        return view;
    }
}