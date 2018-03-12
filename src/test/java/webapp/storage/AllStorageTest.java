package webapp.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
                ArrayStorageTest.class,
                SortedArrayStorageTest.class,
                ListStorageTest.class,
                MapUUIDStorageTest.class,
                MapResumeStorageTest.class,
                ObjectStreamPathStorageTest.class,
                ObjectStreamFileStorageTest.class
        })
public class AllStorageTest {
}
