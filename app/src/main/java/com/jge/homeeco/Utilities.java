package com.jge.homeeco;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.AlertDialog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.jge.homeeco.Database.AppDatabase;
import com.jge.homeeco.Models.Chore;
import com.jge.homeeco.Models.DarkWeather;
import com.jge.homeeco.Models.Person;
import com.jge.homeeco.Models.Prize;
import com.jge.homeeco.ViewModels.PersonViewModel;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class Utilities {

    private static AppDatabase mChoreDatabase;
    public static String BASE_URL = "https://api.darksky.net/forecast";
    public static final String TAG = ChoreListActivity.class.getSimpleName();
    private Gson gson;


    public static android.support.v7.app.AlertDialog.Builder createAlertDialog(String title, final Context context, final Class activityToBeCalled, String positive, String negative, final String toastCancelled, final String toastPositive, final String toastConditional){
        mChoreDatabase = AppDatabase.getInstance(context);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.myDialog));
        alertDialog.setTitle(positive);
        final EditText choreTitle = new EditText(context);
        choreTitle.setTextColor(context.getResources().getColor(R.color.whiteColor));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        choreTitle.setLayoutParams(lp);
        alertDialog.setView(choreTitle);
        alertDialog.setPositiveButton(title, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!choreTitle.getText().toString().equals("")){
                    Toast.makeText(context, toastPositive, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, activityToBeCalled);
                    /********************/
                    final Chore chore = new Chore();
                    chore.setId(generateUIDForChore(context));
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


    private static int generateUIDForChore(Context context){
        return returnSharedPrefCountIdsForChores(context);
    }

    private  static int generateUIDForPerson(Context context){
        return returnSharedPrefCountIdsForPersons(context);
    }


    private static int returnSharedPrefCountIdsForChores(Context context){
        int countIds = 0;
        int restoredInts= 0;
        SharedPreferences.Editor editor = context.getSharedPreferences("choreIds", Context.MODE_PRIVATE).edit();
        SharedPreferences prefs = context.getSharedPreferences("choreIds",Context.MODE_PRIVATE);
        if(prefs == null){
            editor.putInt("choreCountIds",countIds);
            editor.apply();
        }else {
            restoredInts = prefs.getInt("choreCountIds", 0);
            restoredInts = restoredInts + 1;
            editor.putInt("choreCountIds", restoredInts);
            editor.apply();
        }
        return restoredInts;
    }

    private static int returnSharedPrefCountIdsForPersons(Context context){
        int countIds = 0;
        int restoredInts= 0;
        SharedPreferences.Editor editor = context.getSharedPreferences("personIds", Context.MODE_PRIVATE).edit();
        SharedPreferences prefs = context.getSharedPreferences("personIds",Context.MODE_PRIVATE);
        if(prefs == null){
            editor.putInt("personCountIds",countIds);
            editor.apply();
        }else {
            restoredInts = prefs.getInt("personCountIds", 0);
            restoredInts = restoredInts + 1;
            editor.putInt("personCountIds", restoredInts);
            editor.apply();
        }
        return restoredInts;
    }

    private static int generateUIDForPrize(Context context){
        int countIds = 0;
        int restoredInts= 0;
        SharedPreferences.Editor editor = context.getSharedPreferences("prizeIds", Context.MODE_PRIVATE).edit();
        SharedPreferences prefs = context.getSharedPreferences("prizeIds",Context.MODE_PRIVATE);
        if(prefs == null){
            editor.putInt("prizeCountIds",countIds);
            editor.apply();
        }else {
            restoredInts = prefs.getInt("prizeCountIds", 0);
            restoredInts = restoredInts + 1;
            editor.putInt("prizeCountIds", restoredInts);
            editor.apply();
        }
        return restoredInts;
    }

    public static android.support.v7.app.AlertDialog.Builder createPersonAlertDialog(String title, final Context context, final Class activityToBeCalled, String positive, String negative, final String toastCancelled, final String toastPositive, final String toastConditional){
        mChoreDatabase = AppDatabase.getInstance(context);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.myDialog));
        alertDialog.setTitle(positive);
        final EditText personName = new EditText(context);
        personName.setTextColor(context.getResources().getColor(R.color.whiteColor));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        personName.setLayoutParams(lp);
        alertDialog.setView(personName);
        alertDialog.setPositiveButton(title, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!personName.getText().toString().equals("")){
                    Toast.makeText(context, toastPositive, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, activityToBeCalled);
                    /********************/
                    final Person person = new Person();
                    person.setId(generateUIDForPerson(context));
                    person.setPointsAssigned(0);
                    person.setName(personName.getText().toString());
                    /********************/
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("createdPerson", person);
                    intent.putExtra("createdPersonBundle", bundle);
                    intent.putExtra("personName", personName.getText().toString());
                    intent.putExtra("bundleChore", bundle);
                    intent.putExtra("person", person);
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            mChoreDatabase.personDao().insertPerson(person);
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

    public static android.support.v7.app.AlertDialog.Builder addPoints(String title, final Context context, String positive, String negative, final String toastPositive, final Person person, final String toastCancelled, final String toastConditional, final TextView textViewToUpdate, final PersonViewModel personViewModel){
        mChoreDatabase = AppDatabase.getInstance(context);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.myDialog));
        alertDialog.setTitle(title);
        final EditText pointsToAddEdit = new EditText(context);
        pointsToAddEdit.setTextColor(context.getResources().getColor(R.color.whiteColor));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        pointsToAddEdit.setLayoutParams(lp);
        alertDialog.setView(pointsToAddEdit);
        alertDialog.setPositiveButton(positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!pointsToAddEdit.getText().toString().equals("")){
                    final int totalAmountOfPoints = addToCount(person.getPointsAssigned(), (Integer.parseInt(pointsToAddEdit.getText().toString())));
                    Toast.makeText(context, toastPositive, Toast.LENGTH_LONG).show();
                    person.setPointsAssigned(totalAmountOfPoints);
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            mChoreDatabase.personDao().updatePerson(person);
                        }
                    };
                    AppExecutors.getInstance().diskIO().execute(runnable);
                    /**personViewModel.getPersonById(person.getId()).observe(this, new Observer<Person>() {
                        @Override
                        public void onChanged(@Nullable Person person) {
                            textViewToUpdate.setText("Amount of points !: " + person.getPointsAssigned());
                        }
                    });*/
                    textViewToUpdate.setText("Amount of Points: "+person.getPointsAssigned());
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

    public static AlertDialog.Builder addPrize(String title, final Context context, String positive, String negative, final String toastPositive, final String toastCancelled, final String toastConditional){
        mChoreDatabase = AppDatabase.getInstance(context);
        final Prize prize = new Prize();
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.myDialog));
        alertDialog.setTitle(title);
        final EditText editText = new EditText(context);
        editText.setTextColor(context.getResources().getColor(R.color.whiteColor));
        editText.setHint("Name of prize");
        final EditText editTextPoints = new EditText(context);
        editTextPoints.setHint("Points needed to get prize");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        editText.setLayoutParams(lp);
        editTextPoints.setLayoutParams(lp);
        alertDialog.setView(editText);
        alertDialog.setPositiveButton(positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!editText.getText().toString().equals("")){
                    prize.setName(editText.getText().toString());
                    prize.setId(generateUIDForPrize(context));
                    Toast.makeText(context, toastPositive,Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context,toastConditional, Toast.LENGTH_SHORT).show();
                }

                if(!editTextPoints.getText().toString().equals("")){
                    prize.setPoints((Integer.parseInt(editTextPoints.getText().toString())));
                }

                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mChoreDatabase.prizeDao().insertPrize(prize);
                    }
                });
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

    private static int addToCount(int pointsToAdd, int pointsAlreadyThere){
        int total;
        total = pointsToAdd + pointsAlreadyThere;
        return total;
    }

    public static String NetworkWeatherCall(){
        return BASE_URL;

    }
    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }




}
