package com.jge.homeeco;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.jge.homeeco.Database.AppDatabase;
import com.jge.homeeco.Models.Chore;
import com.jge.homeeco.ViewModels.ChoreViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class WidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetItemFactory(getApplicationContext(), intent);
    }

    class WidgetItemFactory implements RemoteViewsFactory{
        private Context context;
        private int appWidgetId;
        private String [] exampleData = {"one", "two", "three", "four", "five", "six"};
        private List<String> exam;
        private List<Chore> choreList;
        private AppDatabase choreDataBase;

        WidgetItemFactory(Context context, Intent intent){
            this.context = context;
            this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

        }

        @Override
        public void onCreate() {
            //Connect to database
            choreDataBase = AppDatabase.getInstance(context);
        }

        @Override
        public void onDataSetChanged() {
            choreList = choreDataBase.choreDao().loadAllChores().getValue();
        }

        @Override
        public void onDestroy() {
            //close database

        }

        @Override
        public int getCount() {
            return choreList == null ? 0 :choreList.size();
        }

        @Override
        public RemoteViews getViewAt(int i) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.listview_widget_item);
            views.setTextViewText(R.id.widget_item_tv, choreList.get(i).getTitle());
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
