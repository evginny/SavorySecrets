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

public class AddRecipeFragment extends Fragment {

    private RecipeViewModel mRecipeViewModel;
    private String mCategory; //= "Dinner";

    private EditText mNameEditText;
    private EditText mIngredientsEditText;
    private EditText mInstructionsEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.addrecipefragment, container, false);

        mNameEditText = view.findViewById(R.id.name_edit_text);
        mIngredientsEditText = view.findViewById(R.id.ingredients_edit_text);
        mInstructionsEditText = view.findViewById(R.id.instructions_edit_text);

        Button saveButton = view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(v -> {
            String name = mNameEditText.getText().toString();
            String ingredients = mIngredientsEditText.getText().toString();
            String instructions = mInstructionsEditText.getText().toString();

            // Add validation for the form inputs here

            Recipe newRecipe = new Recipe(name, ingredients, instructions, mCategory);
            //mRecipeViewModel.insert(newRecipe);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mRecipeViewModel.insert(newRecipe);
                }
            }).start();

            // Navigate back to the list
            Navigation.findNavController(v).navigateUp();
//            Bundle bundle = new Bundle();
//            bundle.putString("category", mCategory);
//            // Navigate back to the list, passing the bundle
//            Navigation.findNavController(v).navigate(R.id.action_go_back_to_list_fragment, bundle);
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // Initialize the ViewModel
        mRecipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);

        // Get the selected category from the navigation arguments
        if (getArguments() != null) {
            mCategory = getArguments().getString("category");
        }

    }
}
