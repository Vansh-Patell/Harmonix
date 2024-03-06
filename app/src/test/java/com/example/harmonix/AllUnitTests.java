package com.example.harmonix;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import com.example.harmonix.DataLayerTests.*;
import com.example.harmonix.LogicLayerTests.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({ UserTest.class, AccountsTest.class, MusicPlayerTest.class })

public class AllUnitTests {

}