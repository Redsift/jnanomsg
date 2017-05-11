package nanomsg;

import java.nio.ByteBuffer;
import java.util.EnumSet;

import nanomsg.exceptions.IOException;

import nanomsg.Nanomsg.SocketOption;
import nanomsg.Nanomsg.SocketFlag;
import nanomsg.Nanomsg.SocketType;
import nanomsg.Nanomsg.Domain;

import nanomsg.bus.BusSocket;
import nanomsg.reqrep.RepSocket;
import nanomsg.reqrep.ReqSocket;
import nanomsg.pipeline.PushSocket;
import nanomsg.pipeline.PullSocket;
import nanomsg.pubsub.PubSocket;
import nanomsg.pubsub.SubSocket;


public interface Socket {
  void close() throws IOException;
  void bind(final String endpoint) throws IOException;
  void connect(final String endpoint) throws IOException;

  /**
   * Send a message with option to set blocking flag.
   *
   * @param data a `ByteBuffer` that represents a message.
   * @param blocking set blocking or non blocking flag.
   * @return number of sended bytes.
   */
  int send(final ByteBuffer data, final EnumSet<SocketFlag> flagSet) throws IOException;

  /**
   * Send a message.
   *
   * This operation is blocking by default.
   *
   * @param data byte buffer that represents a message.
   * @return number of sended bytes.
   */
  int send(final ByteBuffer data) throws IOException;

  /**
   * Send a message with option to set blocking flag.
   *
   * @param data string value that represents a message.
   * @param blocking set blocking or non blocking flag.
   * @return number of sended bytes.
   */
  int send(final String data, final EnumSet<SocketFlag> flagSet)
    throws IOException;

  /**
   * Send a message.
   *
   * This operation is blocking by default.
   *
   * @param data string value that represents a message.
   * @return number of sended bytes.
   */
  int send(final String data) throws IOException;

  /**
   * Send a message with option to set blocking flag.
   *
   * @param data a bytes array that represents a message.
   * @param blocking set blocking or non blocking flag.
   * @return number of sended bytes.
   */
  int send(final byte[] data, final EnumSet<SocketFlag> flagSet) throws IOException;

  /**
   * Send a message.
   *
   * This operation is blocking by default.
   *
   * @param data a bytes array that represents a message.
   * @return number of sended bytes.
   */
  int send(final byte[] data) throws IOException;

  /**
   * Receive a message with option for set blocking flag.
   *
   * This method uses utf-8 encoding for converts a bytes array
   * to string.
   *
   * @param blocking set blocking or non blocking flag.
   * @return receved data as unicode string.
   */
  String recvString(final EnumSet<SocketFlag> flagSet) throws IOException;

  /**
   * Receive a message with option for set blocking flag.
   *
   * This method uses utf-8 encoding for converts a bytes array
   * to string. This operation is blocking by default.
   *
   * @return receved data as unicode string.
   */
  String recvString() throws IOException;

  /**
   * Receive a message with option for set blocking flag.
   *
   * @param blocking set blocking or non blocking flag.
   * @return receved data as bytes array
   */
  byte[] recvBytes(final EnumSet<SocketFlag> flagSet) throws IOException;

  /**
   * Receive a message.
   *
   * This operation is blocking by default.
   *
   * @return receved data as bytes array
   */
  byte[] recvBytes() throws IOException;

  /**
   * Subscribe to a particular topic.
   *
   * WARNING This method is only supported on SubSocket.
   *
   * @param topic the topic represented as string.
   */
  void subscribe(final String topic)
    throws IOException;

  /**
   * Subscribe to a particular topic.
   *
   * WARNING This method is only supported on SubSocket.
   *
   * @param topic the topic represented as byte array.
   */
  void subscribe(final byte[] topic) throws IOException;

  /**
   * Unsubscribe from a particular topic.
   *
   * WARNING This method is only supported on SubSocket.
   *
   * @param topic the topic represented as String.
   */
  void unsubscribe(final String topic)
    throws IOException;

  /**
   * Unsubscribe from a particular topic.
   *
   * WARNING This method is only supported on SubSocket.
   *
   * @param topic the topic represented as byte array.
   */
  void unsubscribe(final byte[] topic) throws IOException;

  /**
   * Get socket file descriptor.
   *
   * @return file descriptor.
   */
  int getFd();

  /**
   * Get read file descriptor.
   *
   * @return file descriptor.
   */
  int getRcvFd() throws IOException;

  /**
   * Get write file descriptor.
   *
   * @return file descriptor.
   */
  int getSndFd() throws IOException;

  // /**
  //  * Set send timeout option to the socket.
  //  */
  // void setSendTimeout(final int ms);

  // /**
  //  * Set recv timeout option to the socket.
  //  */
  // void setRecvTimeout(final int ms);

  void setSocketOpt(SocketOption type, Object value);

  public static Socket create(SocketType type) {
    return create(type, Domain.AF_SP);
  }

  public static Socket create(SocketType type, Domain domain) {
    if (type == SocketType.NN_REQ) {
      return new ReqSocket(domain);
    } else if (type == SocketType.NN_REP) {
      return new RepSocket(domain);
    } else if (type == SocketType.NN_SUB) {
      return new SubSocket(domain);
    } else if (type == SocketType.NN_PUB) {
      return new PubSocket(domain);
    } else if (type == SocketType.NN_PUSH) {
      return new PushSocket(domain);
    } else if (type == SocketType.NN_PULL) {
      return new PushSocket(domain);
    } else {
      return new BusSocket(domain);
    }
  }
}
