package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import util.Parameters;
import util.exceptions.InvalidNumberOfArgumentsException;

/**
 * Test for Parameters
 * Coverage report:
 * Class  100%
 * Method 100%
 * Line   100%
 */
public class ParametersTest {

  @Test
  public void testParametersShouldReturnNullForCommandNameIfNoneExists() {
    ArrayList<String> al = new ArrayList<>();
    Parameters params = new Parameters(al);
    assertEquals(null, params.getCommandName());
  }

  @Test
  public void testParametersShouldReturnCorrectCommandName() {
    ArrayList<String> al = new ArrayList<>();
    al.add("mycommand");
    Parameters params = new Parameters(al);
    assertEquals("mycommand", params.getCommandName());
  }

  @Test
  public void testParametersShouldReturnNullForArgumentIfNoneExists() {
    ArrayList<String> al = new ArrayList<>();
    Parameters params = new Parameters(al);
    assertEquals(null, params.getArgument(0));
  }

  @Test
  public void testParametersShouldReturnCorrectArgument() {
    ArrayList<String> al = new ArrayList<>();
    al.add("mycommand");
    al.add("arg1");
    Parameters params = new Parameters(al);
    assertEquals("arg1", params.getArgument(0));
  }

  @Test
  public void testParametersShouldReturnCorrectArgumentArrayIfNoneExists() {
    ArrayList<String> al = new ArrayList<>();
    al.add("mycommand");
    Parameters params = new Parameters(al);
    assertEquals(0, params.getArguments().length);
  }

  @Test
  public void testParametersShouldReturnCorrectArgumentArray() {
    ArrayList<String> al = new ArrayList<>();
    al.add("mycommand");
    al.add("arg1");
    al.add("arg2");
    Parameters params = new Parameters(al);
    String[] args = params.getArguments();
    assertEquals(2, params.getArguments().length);
    assertEquals("arg1", params.getArguments()[0]);
    assertEquals("arg2", params.getArguments()[1]);
  }

  @Test(expected = InvalidNumberOfArgumentsException.class)
  public void testParametersShouldAssertCorrectArgumentLength()
          throws InvalidNumberOfArgumentsException {
    ArrayList<String> al = new ArrayList<>();
    al.add("mycommand");
    al.add("arg1");
    al.add("arg2");
    Parameters params = new Parameters(al);
    params.assertArgumentLength(3);
  }

  @Test
  public void testGetRedirectionParametersShouldReturnNullIfNoneExists() {
    ArrayList<String> al = new ArrayList<>();
    al.add("mycommand");
    al.add("arg1");
    al.add(">>");
    al.add("123");
    al.add("54");
    Parameters params = new Parameters(al);
    params.extractRedirectionParameters();
    assertEquals(true,
            params.getRedirectionParameters() == null);
  }

  @Test
  public void testShouldReturnIfHasAppendRedirection() {
    ArrayList<String> al = new ArrayList<>();
    al.add("mycommand");
    al.add("arg1");
    al.add(">>");
    al.add("123");
    Parameters params = new Parameters(al);
    params.extractRedirectionParameters();
    String[] redirectionParams = params.getRedirectionParameters();
    assertEquals(2, redirectionParams.length);
    assertEquals(">>", redirectionParams[0]);
    assertEquals("123", redirectionParams[1]);
  }

  @Test
  public void testShouldReturnIfHasOverwriteRedirection() {
    ArrayList<String> al = new ArrayList<>();
    al.add("cmd");
    al.add(">");
    al.add("456");
    Parameters params = new Parameters(al);
    params.extractRedirectionParameters();
    String[] redirectionParams = params.getRedirectionParameters();
    assertEquals(2, redirectionParams.length);
    assertEquals(">", redirectionParams[0]);
    assertEquals("456", redirectionParams[1]);
  }

}
