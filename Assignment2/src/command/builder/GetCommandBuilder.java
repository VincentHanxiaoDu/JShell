package command.builder;


import fetching.IDataFetcher;

/**
 * Builder for get command
 */
public class GetCommandBuilder extends CommandBuilder{
  private IDataFetcher IDataFetcher;

  /**
   * Returns the data gotten from a specific URL
   * @return IDataFetcher object containing data
   */
  public IDataFetcher getIDataFetcher() {
    return IDataFetcher;
  }

  /**
   * Sets the IDataFetcher object
   * @param IDataFetcher data to set
   */
  public void setIDataFetcher(IDataFetcher IDataFetcher) {
    this.IDataFetcher = IDataFetcher;
  }
}
