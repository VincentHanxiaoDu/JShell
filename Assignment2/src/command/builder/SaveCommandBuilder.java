package command.builder;

import serialization.IObjectSerializer;

/**
 * Builder for save command
 */
public class SaveCommandBuilder extends CommandBuilder {

  private IObjectSerializer serializer;

  /**
   * Set object serializer
   * @param serializer The serializer
   */
  public void setSerializer(IObjectSerializer serializer) {
    this.serializer = serializer;
  }

  /**
   * Get current serializer
   * @return The serializer
   */
  public IObjectSerializer getSerializer() {
    return this.serializer;
  }

}
