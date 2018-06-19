package webapp.util;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import webapp.model.Resume;
import webapp.model.Section;
import webapp.model.StringSection;

import static webapp.util.TestDataOneResume.R1;


public class JsonParserTest {

    @Test
    public void testResume() {
        String json = JsonParser.write(R1);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        Assert.assertEquals(R1, resume);
    }

    @Test
    public void write() {
        Section section1 = new StringSection("Objective1");
        String json = JsonParser.write(section1, Section.class);
        System.out.println(json);
        Section section2 = JsonParser.read(json, Section.class);
        Assert.assertEquals(section1, section2);
    }

}