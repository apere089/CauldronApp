package fiu.team5cen4010.cauldron.Adapter;

import android.app.Notification;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import fiu.team5cen4010.cauldron.BackgroundWorker;
import fiu.team5cen4010.cauldron.Database.Database;
import fiu.team5cen4010.cauldron.HomeScreen.HomeActivity;
import fiu.team5cen4010.cauldron.MainActivity;
import fiu.team5cen4010.cauldron.Model.Recipe;
import fiu.team5cen4010.cauldron.Profile.ProfileActivity;
import fiu.team5cen4010.cauldron.R;

 class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public ImageView imageid;
    public TextView name, description, cookingtime, totalcost;
    ImageButton favorite_heart_button;
    List<Recipe> recipes = new ArrayList<Recipe>();
    Context ctx;
     ProfileActivity prof = new ProfileActivity();
     HomeActivity home = new HomeActivity();
     MainActivity main = new MainActivity();
     static  ArrayList<String> favorites = new ArrayList<String>();




    public SearchViewHolder(View itemView, Context ctx, List<Recipe> recipes) {
        super(itemView);
        this.ctx = ctx;
        this.recipes = recipes;
        itemView.setOnClickListener(this);
        imageid = (ImageView) itemView.findViewById(R.id.imageid);
        name = (TextView) itemView.findViewById(R.id.name);
        description = (TextView) itemView.findViewById(R.id.description);
        cookingtime = (TextView) itemView.findViewById(R.id.cookingtime);
        totalcost = (TextView) itemView.findViewById(R.id.totalcost);
        favorite_heart_button = (ImageButton) itemView.findViewById(R.id.favorite_heart_button);
        favorite_heart_button.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();
        Recipe recipe = this.recipes.get(position);

        if (v.getId() == R.id.favorite_heart_button && home.active == true ) {

             home.favorites = home.users.getUserList().get(home.userIndex).getFavorites();
            if(home.favorites.contains(recipe.getName())){
                Toast.makeText(ctx, "This recipe is already your favorite!", Toast.LENGTH_SHORT).show();
            }else{
                home.favorites.add(recipe.getName());
                home.users.getUserList().get(home.userIndex).setFavorites(home.favorites);
                Toast.makeText(ctx, "Recipe Saved to Favorites!", Toast.LENGTH_SHORT).show();
                favorite_heart_button.setImageResource(R.drawable.ic_favorite_complete_24dp);
                main.users = home.users;

                //Save favorites through php sql database in background
                String type = "setfavorites";
                String username =  home.users.getUserList().get(home.userIndex).getName();
                String favorite = recipe.getName();
                BackgroundWorker backgroundWorker = new BackgroundWorker(ctx);
                backgroundWorker.execute(type,username,favorite);
            }
        }else if(v.getId() == R.id.favorite_heart_button && main.active == true){
            home.favorites = main.users.getUserList().get(main.userIndex).getFavorites();
            if(home.favorites.contains(recipe.getName())){
                Toast.makeText(ctx, "This recipe is already your favorite!", Toast.LENGTH_SHORT).show();
            }else{
                home.favorites.add(recipe.getName());
                main.users.getUserList().get(main.userIndex).setFavorites(home.favorites);
                Toast.makeText(ctx, "Recipe Saved to Favorites!", Toast.LENGTH_SHORT).show();
                favorite_heart_button.setImageResource(R.drawable.ic_favorite_complete_24dp);
                home.users = main.users;

                //Save favorites through php sql database in background
                String type = "setfavorites";
                String username =  main.users.getUserList().get(main.userIndex).getName();
                String favorite = recipe.getName();
                BackgroundWorker backgroundWorker = new BackgroundWorker(ctx);
                backgroundWorker.execute(type,username,favorite);

            }

        }
         else {
            Resources res = this.ctx.getResources();
            int image_resourceId = res.getIdentifier("fiu.team5cen4010.cauldron:drawable/" + recipe.getImageid(), null, null);

            //Develop further to get nutrition image through a button inside recipeview
            int nutritionimage_resourceId = res.getIdentifier("fiu.team5cen4010.cauldron:drawable/" + recipe.getNutritionimage(), null, null);
            try {
                String ingredients = IOUtils.toString(ctx.getAssets().open(recipe.getIngredients()), StandardCharsets.UTF_8);
                String instructions = IOUtils.toString(ctx.getAssets().open(recipe.getInstruction()), StandardCharsets.UTF_8);

                Intent GOTOrecipeView = new Intent(this.ctx, RecipeView.class);
                GOTOrecipeView.putExtra("imageid", image_resourceId);
                GOTOrecipeView.putExtra("name", recipe.getName());
                GOTOrecipeView.putExtra("description", recipe.getDescription());
                GOTOrecipeView.putExtra("ingredients", ingredients);
                GOTOrecipeView.putExtra("instructions", instructions);
                GOTOrecipeView.putExtra("nutrition", nutritionimage_resourceId);

                //Info for the middle icons
                GOTOrecipeView.putExtra("numcalories", recipe.getNumcalories());
                GOTOrecipeView.putExtra("numservings", recipe.getNumservings());
                GOTOrecipeView.putExtra("numtime", recipe.getCookingtime());

                //Pass database object and index from HomeActivity
                GOTOrecipeView.putExtra("userdatabase", home.users);
                GOTOrecipeView.putExtra("userindex", home.userIndex);

                this.ctx.startActivity(GOTOrecipeView);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {

    private Context context;
    private List<Recipe> recipes;


    public SearchAdapter(Context context, List<Recipe> recipes) {
        this.context = context;
        this.recipes = recipes;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.layout_item,parent,false);
        return new SearchViewHolder(itemView,context,recipes);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        Resources res = this.context.getResources();
        int resourceId = res.getIdentifier("fiu.team5cen4010.cauldron:drawable/" + recipes.get(position).getImageid(), null, null);

        holder.imageid.setImageResource(resourceId);
        holder.name.setText(recipes.get(position).getName());
        holder.description.setText(recipes.get(position).getDescription());
        holder.cookingtime.setText(recipes.get(position).getCookingtimeToString());
        holder.totalcost.setText(recipes.get(position).getTotalcostToString());


        if (holder.home.favorites.isEmpty()) {

                holder.favorite_heart_button.setImageResource(R.drawable.ic_favorite_non_complete_24dp);


        } else if (!holder.home.favorites.isEmpty()){
            if (holder.home.favorites.contains(holder.name.getText().toString())) {
                holder.favorite_heart_button.setImageResource(R.drawable.ic_favorite_complete_24dp);
            }else{
                holder.favorite_heart_button.setImageResource(R.drawable.ic_favorite_non_complete_24dp);
            }
        }

    }
    @Override
    public int getItemCount() {
        return recipes.size();
    }
}
