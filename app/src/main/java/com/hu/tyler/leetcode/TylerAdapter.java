package com.hu.tyler.leetcode;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.app.FragmentManager;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.Fragment;


import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by tyler on 7/2/2017.
 */


public class TylerAdapter extends RecyclerView.Adapter<TylerAdapter.ViewHolder> {


    private List<ListItem> xxx;
    final public Context context01;
    android.support.v4.app.FragmentTransaction fragmentManagerX;
    MainActivity myActivity = new MainActivity();

    /// this construction gets the context and array
    public TylerAdapter(List<ListItem> xxx, Context context1) {
        this.xxx = xxx;
        this.context01 = context1;
        // this.fragmentManagerX = myActivity.getSupportFragmentManager().beginTransaction();

    }

    public TylerAdapter(List<ListItem> xxx, Context context1, android.support.v4.app.FragmentTransaction fragmentManager) {
        this.xxx = xxx;
        this.context01 = context1;
        this.fragmentManagerX = fragmentManager;
        // myActivity = (MainActivity)context1;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_template, parent, false);
        return new ViewHolder(v, fragmentManagerX, context01);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ListItem x = xxx.get(position);
        holder.title.setText(x.getTitle()); // sets the title in the XML
        holder.rank.setText(x.getRank()); // sets the difficulty level in the XML
        if (x.getRank().equalsIgnoreCase("Easy")) {
            holder.rank.setTextColor(Color.parseColor("#7C9F36"));
            //change holder.rank.textcolor to green
        } else if (x.getRank().equalsIgnoreCase("Hard")) {
            holder.rank.setTextColor(Color.parseColor("#D20000"));
            //change holder.rank.textcolor to red
        } else if (x.getRank().equalsIgnoreCase("Medium")) {
            holder.rank.setTextColor(Color.parseColor("#000080"));
            //change holder.rank.textcolor to red
        }

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {

                                                       // it's important to remember that you need the context of the viewholder
                                                       // to be passed into this Intent i if you are expecting to be successful in
                                                       // going to the next activity.
                                                       // not sure why but lets remember to figure that out later

                                                       // this little putExtra thing is how you pass data from one activity to another
                                                       // it's awful honestly.

                                                       //FragmentTransaction fragmentTransactionx = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                                                       //this Above did not work!
                                                       Log.d("XXX", "@onBindViewHolder - before new DetailQuestion()");
                                                       DetailQuestion fragment = new DetailQuestion();
                                                       Log.d("XXX", "@onBindViewHolder - before new Bundle()");
                                                       Bundle bundle = new Bundle();
                                                       bundle.putString("description", x.getDescription());
                                                       bundle.putString("solution", x.getSolution());
                                                       bundle.putString("title", x.getTitle());
                                                       bundle.putString("example", x.getExample());
                                                       bundle.putString("extra", x.getExtra());
                                                       Log.d("XXX", "@onBindViewHolder - before new fragment.setArguments(bundle)");
                                                       fragment.setArguments(bundle);
                                                      // MainActivity myActivity2 = new MainActivity();
                                                       Log.d("XXX", "@onBindViewHolder - before fragmentManager.replace(R.id.fragmentHolder, fragment)");
                                                       //holder.fragmentManager = context01.getSupportFragmentManager().beginTransaction();

                                                       //                                                         android.support.v4.app.FragmentTransaction fragmentManagerX2;

                                                       MainActivity myActivity2 = (MainActivity) holder.context2; /*this took me almost 8 hours to debuggg!!!! this was needed to successfully
                                                            create another FragmentTransaction which is below... ohhh lordy jesus, god, thank you all!!
                                                            FOR FUTURE REFERENCE:
                                                            every time you switch to a new fragment, you need to create another new Fragment Transaction!!!
                                                            you are not allowed to reuse the old one
                                                            ALSO:
                                                             the transaction is to happen to itemView, it needs a context from there but we need to cast this
                                                            with the mainActivity because that has implements NavigationView.OnNavigationItemSelectedListener
                                                            */
                                                       android.support.v4.app.FragmentTransaction fragmentManagerX2 = myActivity2.getSupportFragmentManager().beginTransaction();
                                                       //holder.fragmentManager.replace(R.id.fragmentHolder, fragment);
                                                       fragmentManagerX2.replace(R.id.fragmentHolder, fragment);
                                                       // holder.fragmentManager.replace(R.id.fragmentHolder, fragment.instantiate(holder.context2, fragment.getClass().getName()));

                                                       Log.d("XXX", "@onBindViewHolder - before holder.fragmentManager.commit();");

                                                       try {
                                                           fragmentManagerX2.addToBackStack(null).commit();
                                                           myActivity2.getSupportFragmentManager().executePendingTransactions(); /// not sure if this is necessary but sometimes fragments stop transitioning so adding this here to "reset" things

                                                           Log.d("XXX", "myActivity2.getSupportFragmentManager().executePendingTransactions() was executed");
                                                           // holder.fragmentManager.addToBackStack(null).commit();
                                                           // holder.fragmentManager.commit();
                                                       } catch (Exception e) {
                                                           e.printStackTrace();
                                                       }
                                                       Log.d("XXX", "@onBindViewHolder - after holder.fragmentManager.commit();");
                                                       Log.d("XXX", "item position: " + position);
                                                   }
                                               }
        );

    }

    @Override
    public int getItemCount() {
        return xxx.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        int holder = 0;
        private final Context context2;
        public TextView title;
        public TextView rank;
        public LinearLayout linearLayout;


        public android.support.v4.app.FragmentTransaction fragmentManager;

        public ViewHolder(View item, FragmentTransaction fragmentManager, Context context) {

            super(item);
            this.fragmentManager = fragmentManager;
            this.context2 = itemView.getContext();
            title = (TextView) itemView.findViewById(R.id.title);
            rank = (TextView) itemView.findViewById(R.id.rank_view);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.rowTemplateLinearLayout);
        }

        public TextView getTitle() {
            return title;
        }

        public TextView getRank() {
            return rank;
        }

        public Context getContext() {
            return context2;
        }
    }
}
