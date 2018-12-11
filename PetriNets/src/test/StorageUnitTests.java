package test;

import gui.Petrinet2DObjectInterface;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import storage.Storage;
import storage.StorageInterface;

import static org.junit.Assert.assertEquals;

public class StorageUnitTests extends TestCase {
    StorageInterface storage;
    @Before
    public void setUp() throws Exception {
        storage= new Storage();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testRead() {
    }
}
