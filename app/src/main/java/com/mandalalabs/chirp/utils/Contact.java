package com.mandalalabs.chirp.utils;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import com.mandalalabs.chirp.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// Container class to hold Contact information.
public class Contact implements Serializable {
    private String mName;
    private int mThumbnailDrawable;
    private String mNumber;

    public Contact(String name, int thumbnailDrawable, String number) {
        mName = name;
        mThumbnailDrawable = thumbnailDrawable;
        mNumber = number;
    }

    public String getName() {
        return mName;
    }

    public int getThumbnailDrawable() {
        return mThumbnailDrawable;
    }

    public String getNumber() {
        return mNumber;
    }

    // Returns a list of contacts
    public static List<Contact> getContacts() {
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("Ravi", R.drawable.contact_one, "4153508881"));
        contacts.add(new Contact("Sailu", R.drawable.contact_two, "4153508882"));
        contacts.add(new Contact("Rohan", R.drawable.contact_three, "4153508883"));
        contacts.add(new Contact("Deva", R.drawable.contact_four, "4153508884"));
        contacts.add(new Contact("Archana", R.drawable.contact_five, "4153508885"));
        contacts.add(new Contact("Rams", R.drawable.contact_six, "4153508886"));
        contacts.add(new Contact("Radhika", R.drawable.contact_seven, "4153508887"));
        contacts.add(new Contact("Sunil", R.drawable.contact_eight, "4153508888"));
        contacts.add(new Contact("Atti", R.drawable.contact_nine, "4153508889"));
        contacts.add(new Contact("Vasu", R.drawable.contact_ten, "4153508890"));
        return contacts;
    }

    public static Contact getRandomContact(Context context) {

        Resources resources = context.getResources();

        TypedArray contactNames = resources.obtainTypedArray(R.array.contact_names);
        int name = (int) (Math.random() * contactNames.length());

        TypedArray contactThumbnails = resources.obtainTypedArray(R.array.contact_thumbnails);
        int thumbnail = (int) (Math.random() * contactThumbnails.length());

        TypedArray contactNumbers = resources.obtainTypedArray(R.array.contact_numbers);
        int number = (int) (Math.random() * contactNumbers.length());

        return new Contact(contactNames.getString(name), contactThumbnails.getResourceId(thumbnail, R.drawable.contact_one), contactNumbers.getString(number));
    }
}