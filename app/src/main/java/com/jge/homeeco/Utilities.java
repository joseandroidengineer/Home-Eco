package com.jge.homeeco;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.support.v7.app.AlertDialog;

import com.jge.homeeco.Database.AppDatabase;
import com.jge.homeeco.Models.Chore;

import java.util.Random;


public class Utilities {

    public static AppDatabase mChoreDatabase;

    public static android.support.v7.app.AlertDialog.Builder createAlertDialog(String title, final Context context,String positive, String negative, final String toastCancelled, final String toastPositive, final String toastConditional){
        mChoreDatabase = AppDatabase.getInstance(context);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.myDialog));
        alertDialog.setTitle(positive);
        final EditText choreTitle = new EditText(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        choreTitle.setLayoutParams(lp);
        alertDialog.setView(choreTitle);
        alertDialog.setPositiveButton(title, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!choreTitle.getText().toString().equals("")){
                    Toast.makeText(context, toastPositive, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, ChoreDetailActivity.class);
                    /********************/
                    final Chore chore = new Chore();
                    chore.setId(generateUID());
                    chore.setCompleted(false);
                    chore.setDescription("Click the blue button to edit the description of your ");
                    chore.setTitle(choreTitle.getText().toString());
                    /********************/
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("createdChore", chore);
                    intent.putExtra("createdChoreBundle", bundle);
                    intent.putExtra("choreTitle", choreTitle.getText().toString());
                    intent.putExtra("bundleChore", bundle);
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            mChoreDatabase.choreDao().insertChore(chore);
                        }
                    };
                    AppExecutors.getInstance().diskIO().execute(runnable);
                    context.startActivity(intent);
                }else{
                    Toast.makeText(context, toastConditional, Toast.LENGTH_LONG).show();
                }
            }
        });

        alertDialog.setNegativeButton(negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, toastCancelled, Toast.LENGTH_LONG).show();
                dialogInterface.cancel();
            }
        });
        return alertDialog;

    }

    public static int generateUID(){
        Random random = new Random();
        return random.nextInt();

    }
}
