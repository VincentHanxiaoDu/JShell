package test;

import static org.junit.Assert.assertEquals;

import command.Get;
import command.builder.GetCommandBuilder;
import fetching.IDataFetcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import test.mock.MockOutputIO;
import test.mock.MockParameters;
import test.mock.MockRemoteDataFetcher;


/**
 * Unit tests for get
 */
public class GetTest {

  private IDataFetcher remoteDatafetcher;
  private GetCommandBuilder builder;
  private MockOutputIO errorOut;
  private MockOutputIO standardOut;
  private MockParameters parameters;

  @Before
  public void setUp() {
    // Construct mock objects
    remoteDatafetcher = new MockRemoteDataFetcher();
    errorOut = new MockOutputIO();
    standardOut = new MockOutputIO();
    parameters = new MockParameters();
    // Construct the builder
    builder = new GetCommandBuilder();
    builder.setIDataFetcher(remoteDatafetcher);
    builder.setErrorOut(errorOut);
    builder.setStandardOut(standardOut);
    builder.setParameters(parameters);
  }

  @After
  public void tearDown() {
    remoteDatafetcher = null;
    errorOut = null;
    standardOut = null;
    builder = null;
  }

  @Test
  public void testGetWithValidUrl() {
    parameters.setCommandName("get");
    parameters.setArguments("https://docs.oracle.com/javase/8/docs/api.html");
    Get get = Get.createWithBuilder(builder);
    get.run();
    assertEquals("", standardOut.content);
  }


  @Test
  public void testGetWithInvalidUrl() {
    parameters.setCommandName("get");
    parameters.setArguments("Invalid URL");
    Get get = Get.createWithBuilder(builder);
    get.run();
    assertEquals("ERROR: get: Invalid URL: Invalid URL",
        errorOut.buffer);
  }

  @Test
  public void testGetWithFailedtoGetInputStream() {
    parameters.setCommandName("get");
    parameters.setArguments("Failed to get input stream");
    Get get = Get.createWithBuilder(builder);
    get.run();
    assertEquals("ERROR: get: Failed to get input stream.",
        errorOut.buffer);
  }

  @Test
  public void testGetWithInvalidFileName() {
    parameters.setCommandName("get");
    parameters.setArguments("Invalid file name");
    Get get = Get.createWithBuilder(builder);
    get.run();
    assertEquals(
        "ERROR: get: Invalid file name: The type of the "
            + "file is not supported.",
        errorOut.buffer);
  }


}