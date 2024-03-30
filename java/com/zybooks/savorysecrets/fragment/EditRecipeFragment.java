package com.zybooks.savorysecrets.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.zybooks.savorysecrets.R;
import com.zybooks.savorysecrets.entity.Recipe;
import com.zybooks.savorysecrets.viewmodel.RecipeViewModel;

public class EditRecipeFragment extends Fragment{
    private RecipeViewModel mRecipeViewModel;
    private Recipe mCurrentRecipe;
    private EditText mNameEditText;
    private EditText mIngredientsEditText;
    private EditText mInstructionsEditText;

    public static final String ARG_RECIPE_ID = "recipe_id";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_recipe, container, false);

        mNameEditText = view.findViewById(R.id.edit_name_edit_text);
        mIngredientsEditText = view.findViewById(R.id.edit_ingredients_edit_text);
        mInstructionsEditText = view.findViewById(R.id.edit_instructions_edit_text);

        Button saveButton = view.findViewById(R.id.edit_save_button);
        saveButton.setOnClickListener(v -> {
            String name = mNameEditText.getText().toString();
            String ingredients = mIngredientsEditText.getText().toString();
            String instructions = mInstructionsEditText.getText().toString();
            mCurrentRecipe.setName(name);
            mCurrentRecipe.setIngredients(ingredients);
            mCurrentRecipe.setInstructions(instructions);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    mRecipeViewModel.update(mCurrentRecipe);
                }
            }).start();

            // Navigate back to the detail view
            Navigation.findNavController(v).navigateUp();
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the ViewModel
        mRecipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);

        // Get the selected recipe from the navigation arguments
        if (getArguments() != null) {
            mCurrentRecipe = (Recipe) getArguments().getSerializable(ARG_RECIPE_ID);
            mNameEditText.setText(mCurrentRecipe.getName());
            mIngredientsEditText.setText(mCurrentRecipe.getIngredients());
            mInstructionsEditText.setText(mCurrentRecipe.getInstructions());
        }
    }
}
