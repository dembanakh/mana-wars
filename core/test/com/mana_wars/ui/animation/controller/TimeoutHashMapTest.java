package com.mana_wars.ui.animation.controller;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

public class TimeoutHashMapTest {

    private TimeoutMap<Integer, D> map;

    @Before
    public void setup() {
        map = new TimeoutHashMap<>();
    }

    @Test
    public void testEmptyMap_Immediate() {
        assertEquals(0, map.size());
    }

    @Test
    public void testEmptyMap_100sec() {
        for (int i = 0; i < 100; ++i) {
            map.update(1);
        }
        assertEquals(0, map.size());
    }

    @Test
    public void testNonEmptyAfterAdd_Immediate() {
        map.add(10, Collections.singletonList(new D(100)).iterator());
        assertNotEquals(0, map.size());
    }

    @Test
    public void testAdd_EmptyIterator() {
        map.add(1, Collections.emptyIterator());
        assertEquals(0, map.size());
    }

    @Test
    public void testAdd() {
        map.add(1, Collections.singletonList(new D(100)).iterator());
        assertEquals(1, map.size());
        assertTrue(map.containsKey(1));

        map.add(2, Collections.singletonList(new D(100)).iterator());
        assertEquals(2, map.size());
        assertTrue(map.containsKey(2));
    }

    @Test
    public void testAdd_SameKey() {
        D d1 = new D(100);
        D d2 = new D(100);
        map.add(1, Collections.singletonList(d1).iterator());
        map.add(1, Collections.singletonList(d2).iterator());

        assertEquals(d2, map.get(1));
    }

    @Test
    public void testNonEmptyAfterAdd_beforeDuration() {
        map.add(10, Collections.singletonList(new D(100)).iterator());
        for (int i = 0; i < 10; ++i) {
            map.update(1);
        }
        assertNotEquals(0, map.size());
    }

    @Test
    public void testBecomeEmptyAfterAdd_afterDuration() {
        map.add(10, Collections.singletonList(new D(100)).iterator());
        for (int i = 0; i < 100; ++i) {
            map.update(1);
        }
        assertEquals(0, map.size());
    }

    @Test
    public void testNonEmptyAfterAddMultiple_beforeDuration() {
        map.add(10, Arrays.asList(new D(100), new D(100)).iterator());
        for (int i = 0; i < 10; ++i) {
            map.update(1);
        }
        assertEquals(1, map.size());
        for (int i = 0; i < 100; ++i) {
            map.update(1);
        }
        assertEquals(1, map.size());
    }

    @Test
    public void testBecomeEmptyAfterAddMultiple_afterDuration() {
        map.add(10, Arrays.asList(new D(100), new D(100)).iterator());
        for (int i = 0; i < 200; ++i) {
            map.update(1);
        }
        assertEquals(0, map.size());
    }

    @Test
    public void testGet_beforeDuration() {
        D d = new D(100);
        map.add(10, Collections.singletonList(d).iterator());
        for (int i = 0; i < 10; ++i) {
            map.update(1);
        }

        assertEquals(d, map.get(10));
    }

    @Test
    public void testGet_afterDuration() {
        D d = new D(100);
        map.add(10, Collections.singletonList(d).iterator());
        for (int i = 0; i < 100; ++i) {
            map.update(1);
        }

        assertNull(map.get(10));
    }

    @Test
    public void testGetMultiple_beforeDuration() {
        D d1 = new D(100);
        D d2 = new D(100);
        map.add(10, Arrays.asList(d1, d2).iterator());
        for (int i = 0; i < 10; ++i) {
            map.update(1);
        }
        assertEquals(d1, map.get(10));
        for (int i = 0; i < 100; ++i) {
            map.update(1);
        }
        assertEquals(d2, map.get(10));
    }

    @Test
    public void testGetMultiple_afterDuration() {
        D d1 = new D(100);
        D d2 = new D(100);
        map.add(10, Arrays.asList(d1, d2).iterator());
        for (int i = 0; i < 200; ++i) {
            map.update(1);
        }
        assertNull(map.get(10));
    }

    @Test
    public void testRemainingTime_beforeDuration() {
        D d = new D(100);
        map.add(10, Collections.singletonList(d).iterator());
        for (int i = 0; i < 10; ++i) {
            map.update(1);
        }

        assertEquals(90, map.getRemainingTime(10), Double.MIN_VALUE);
    }

    @Test
    public void testRemainingTime_afterDuration() {
        D d = new D(100);
        map.add(10, Collections.singletonList(d).iterator());
        for (int i = 0; i < 110; ++i) {
            map.update(1);
        }

        assertEquals(0, map.getRemainingTime(10), Double.MIN_VALUE);
    }

    @Test
    public void testRemainingTimeMultiple_beforeDuration() {
        D d1 = new D(100);
        D d2 = new D(100);
        map.add(10, Arrays.asList(d1, d2).iterator());
        for (int i = 0; i < 10; ++i) {
            map.update(1);
        }
        assertEquals(90, map.getRemainingTime(10), Double.MIN_VALUE);
        for (int i = 0; i < 110; ++i) {
            map.update(1);
        }
        assertEquals(80, map.getRemainingTime(10), Double.MIN_VALUE);
    }

    @Test
    public void testRemainingTimeMultiple_afterDuration() {
        D d1 = new D(100);
        D d2 = new D(100);
        map.add(10, Arrays.asList(d1, d2).iterator());
        for (int i = 0; i < 210; ++i) {
            map.update(1);
        }
        assertEquals(0, map.getRemainingTime(10), Double.MIN_VALUE);
    }

    @Test
    public void testRemove_Simple() {
        D d1 = new D(100);
        D d2 = new D(100);
        map.add(1, Collections.singletonList(d1).iterator());
        map.add(2, Collections.singletonList(d2).iterator());

        map.remove(1);
        assertNull(map.get(1));

        map.remove(2);
        assertNull(map.get(2));
    }

    @Test
    public void testRemove_Immediate() {
        D d = new D(100);
        map.add(10, Collections.singletonList(d).iterator());
        map.remove(10);

        assertEquals(0, map.size());
    }

    @Test
    public void testRemove_beforeDuration() {
        D d = new D(100);
        map.add(10, Collections.singletonList(d).iterator());
        for (int i = 0; i < 10; ++i) {
            map.update(1);
        }
        map.remove(10);

        assertEquals(0, map.size());
    }

    @Test
    public void testRemoveMultiple_Immediate() {
        D d1 = new D(100);
        D d2 = new D(100);
        map.add(10, Arrays.asList(d1, d2).iterator());
        map.remove(10);

        assertEquals(0, map.size());
    }

    @Test
    public void testRemoveMultiple_beforeDuration() {
        D d1 = new D(100);
        D d2 = new D(100);
        map.add(10, Arrays.asList(d1, d2).iterator());
        for (int i = 0; i < 110; ++i) {
            map.update(1);
        }
        map.remove(10);

        assertEquals(0, map.size());
    }

    private static class D implements Durationable {
        private final double duration;
        private D(double duration) {
            this.duration = duration;
        }
        @Override
        public double getDuration() {
            return duration;
        }
    }
}