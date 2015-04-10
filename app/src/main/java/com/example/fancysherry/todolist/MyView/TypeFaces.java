package com.example.fancysherry.todolist.MyView;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import java.util.Hashtable;

/**
 * Created by fancysherry on 14-11-16.
 */
public class TypeFaces {


        private static final Hashtable<String, Typeface> cache = new Hashtable<String, Typeface>();

        public static Typeface getTypeFace(Context context, String assetPath) {
            synchronized (cache) {
                if (!cache.containsKey(assetPath)) {
                    try {
                        Typeface typeFace = Typeface.createFromAsset(
                                context.getAssets(), assetPath);
                        cache.put(assetPath, typeFace);
                    } catch (Exception e) {
                        Log.e("TypeFaces", "Typeface not loaded.");
                        return null;
                    }
                }
                return cache.get(assetPath);
            }
        }
    }

