package com.example.android.readysetbake;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.ArrayList;

import static com.example.android.readysetbake.BakeWidgetService.ACTION_BAKING_INGREDIENTS;


/**
 * Created by Aiman Nabeel on 28/06/2018.
 */

/**
 * Implementation of App Widget functionality.
 */
public class ReadySetBakeWidgetProvider extends AppWidgetProvider {

    static ArrayList<String> ingredientsList = new ArrayList<>();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        //CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_gridview);
        //views.setTextViewText(R.id.appwidget_text, widgetText);

        //Creating Pending Intent for MainActivity
        Intent intent = new Intent(context, RecipeDetailActivity.class);

        //Adding category
        intent.addCategory(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT|Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        views.setPendingIntentTemplate(R.id.widget_gridview, pendingIntent);

        // Setting the GridWidgetService intent to act as the adapter for the GridView
        Intent gridViewIntent = new Intent(context, GridWidgetService.class);
        views.setRemoteAdapter(R.id.widget_gridview, gridViewIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    //Won't be using this method because appWidgetIds[] is required, instead using custom method onUpdateBakingWidgets here
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        // for (int appWidgetId : appWidgetIds) {
        //updateAppWidget(context, appWidgetManager, ingredientsList, appWidgetId);
        // }
    }

    public static void onUpdateBakingWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    //Using BakeWidgetService class here
    @Override
    public void onReceive(Context context, Intent intent) {
        //Setting ingredients list in the widget
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, ReadySetBakeWidgetProvider.class));

        final String action = intent.getAction();

        ingredientsList = intent.getExtras().getStringArrayList(ACTION_BAKING_INGREDIENTS);
        //Triggers a data refresh in the widget
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidetIds, R.id.widget_gridview);

        ReadySetBakeWidgetProvider.onUpdateBakingWidgets(context, appWidgetManager, appWidetIds);
        super.onReceive(context, intent);
    }
}
