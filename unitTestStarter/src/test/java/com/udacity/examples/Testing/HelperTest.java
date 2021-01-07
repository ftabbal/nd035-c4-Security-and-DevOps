package com.udacity.examples.Testing;


import org.junit.Test;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class HelperTest {

    @Test
    public void verify_getCount_noEmptyEntries() {
        List<String> stringList = Arrays.asList("Testing", "this", "method");
        long actual = Helper.getCount(stringList);
        assertEquals(3, actual);
    }

    @Test
    public void verify_getStats() {
        List<Integer> yrsOfExperience = Arrays.asList(13,4,15,6,17,8,19,1,2,3);
        IntSummaryStatistics stats = Helper.getStats(yrsOfExperience);
        assertEquals(19, stats.getMax());
    }

    @Test
    public void verify_getMergedList() {
        List<String> stringList = Arrays.asList("Testing", "this", "method");
        String actual = Helper.getMergedList(stringList);
        String expected = "Testing, this, method";
        assertEquals(expected, actual);

    }
}
