package com.griddynamics.spingtesting.dirtycontext;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Run Count Tests in Order")
@SelectClasses({
        CounterATest.class,    // will run first
        CounterBTest.class     // then this
})
public class OrderedTestSuite {
    // no code needed—just run this suite!
}