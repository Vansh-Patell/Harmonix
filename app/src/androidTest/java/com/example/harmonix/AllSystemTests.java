package com.example.harmonix;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import static org.junit.Assert.*;

import com.example.harmonix.AccountTests.AccountCreationTests;
import com.example.harmonix.AccountTests.AccountUpdateTests;
import com.example.harmonix.AccountTests.LoginTests;
import com.example.harmonix.LibraryTests.PlaylistsTests;
import com.example.harmonix.MusicPlayerTests.MusicPlayerTests;
import com.example.harmonix.MusicPlayerTests.QueueTests;
import com.example.harmonix.PersistenceLayer.Database;
import com.example.harmonix.PersistenceLayer.IDatabase;
import com.example.harmonix.PersistenceLayer.RealDatabase;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@LargeTest
@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccountCreationTests.class, LoginTests.class, AccountUpdateTests.class, MusicPlayerTests.class, QueueTests.class, PlaylistsTests.class
})

public class AllSystemTests {

}

