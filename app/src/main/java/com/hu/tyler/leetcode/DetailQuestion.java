package com.hu.tyler.leetcode;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

import static com.hu.tyler.leetcode.MainActivity.mainContext;
import static com.hu.tyler.leetcode.R.id.imageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailQuestion extends Fragment {
    TextView descrp;
    TextView title;
   // TextView solution;
    TextView example;
    ImageView imageView;
    public String temp;
    int reset = 0;

    public DetailQuestion() {
        // Required empty public constructor
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle saveInstanceState)
    {
        super.onViewCreated(view, saveInstanceState);
        descrp = (TextView) view.findViewById(R.id.detailQuestionDescription);
        title = (TextView) view.findViewById(R.id.detailQuestionTitle);
        //solution = (TextView) view.findViewById(R.id.detailQuestionAnswer) ;
        example = (TextView) view.findViewById(R.id.detailQuestionExample) ;
        temp = getArguments().getString("description");
        descrp.setText(temp);
        temp = getArguments().getString("title");
        getActivity().setTitle(temp);
        title.setText(temp);
        temp = getArguments().getString("example");
        example.setText(temp);
        //temp = getArguments().getString("solution");
        title.setPaintFlags(title.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        Log.v("XXX", "@onViewCreated - this should come 2nd" );

        Button fragSolution = (Button) view.findViewById(R.id.fragSolutionButton);
        fragSolution.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Toast.makeText(mainContext,"this aint finish", Toast.LENGTH_SHORT).show();
                SmallSolution answer = new SmallSolution();
                Bundle bundle = new Bundle();
                bundle.putString("solution", getArguments().getString("solution"));
                bundle.putString("title",getArguments().getString("title"));
                answer.setArguments(bundle);
                android.support.v4.app.FragmentTransaction fragmentManagerX2 = getActivity().getSupportFragmentManager().beginTransaction();

                fragmentManagerX2.replace(R.id.fragmentHolder, answer);
                fragmentManagerX2.addToBackStack(null).commit();
            }
        });

        ///adding a picture
        if(getArguments().getString("extra")!="")
        {
            try{
                new DownloadImageTask((ImageView) view.findViewById(R.id.detailQuestionExamplePicture))
                        .execute(getArguments().getString("extra"));
                BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                dr.setCornerRadius(Math.min(dr.getMinimumWidth(),dr.getMinimumHeight()));

                imageView.setImageDrawable(dr);

                //imageView.setImageBitmap(getRoundedShape(bitmap));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }




    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_question, container, false);
    }

}



/// OLD CODE THATS NOT USEFULL ANYMORE
//        Button solutionButt = (Button) view.findViewById(R.id.solutionButton) ;
//        solutionButt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getActivity(), Solution.class);
//               // Bundle bundle = new Bundle();
//                //bundle.putString("solution", getArguments().getString("solution"));
//                i.putExtra("solution",getArguments().getString("solution"));
//                getActivity().startActivity(i);
//            }
//        });

/*
        solution.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                if(reset == 0)
                {
                    solution.setText(temp);
                    solution.setTextSize(10);
                    Log.v("XXX", "@detailquestions - before  ViewGroup.LayoutParams params = solution.getLayoutParams()" );
                    ViewGroup.LayoutParams params = solution.getLayoutParams();
                    Log.v("XXX", "@detailquestions - after  ViewGroup.LayoutParams params = solution.getLayoutParams()" );
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                    solution.setLayoutParams(params);
                    Log.v("XXX", "@detailquestions - after solution.setLayoutParams(params);" );
                    solution.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    Log.v("XXX", "@detailquestions - after solution.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;" );
                    reset = 1;
                }
                else
                {

                    solution.setText("Long Touch Here to view answer");
                    solution.setTextSize(20);
                    //solution.setWidth(250);
                    //solution.setHeight(100);

                    reset = 0;
                }

                return false;
            }
        });

        */