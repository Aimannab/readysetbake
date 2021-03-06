package com.example.android.readysetbake;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

/**
 * Created by Aiman Nabeel on 28/06/2018.
 */

//Using this class in RecipeDetailFragment class
public class BakeWidgetService extends IntentService{

    public static String ACTION_BAKING_INGREDIENTS =
            "FROM_ACTIVITY_INGREDIENTS_LIST";

    public BakeWidgetService() {
        super("UpdateBakingService");
    }

    public static void startBakingIngredientsService(Context context, ArrayList<String> ingredientsList) {
        Intent intent = new Intent(context, BakeWidgetService.class);
        intent.putExtra(ACTION_BAKING_INGREDIENTS, ingredientsList);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            ArrayList<String> ingredientsList = intent.getExtras().getStringArrayList(ACTION_BAKING_INGREDIENTS);
                handleActionUpdateBakingIngredientsWidget(ingredientsList);
        }

    }

    //Ref: https://developer.android.com/reference/android/appwidget/AppWidgetManager.html#ACTION_APPWIDGET_UPDATE
    //Sent when it is time to update your AppWidget.
    //This may be sent in response to a new instance for this AppWidget provider having been instantiated, the requested update interval having lapsed, or the system booting.
    private void handleActionUpdateBakingIngredientsWidget(ArrayList<String> ingredientsList) {
        Intent intent = new Intent(this, ReadySetBakeWidgetProvider.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        intent.putExtra(ACTION_BAKING_INGREDIENTS, ingredientsList);
        sendBroadcast(intent);
    }
}
