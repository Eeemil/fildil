// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Protocol.proto

package se.umu.cs.ads.fildil.proto.autogen;

public interface PeerInfoOrBuilder extends
    // @@protoc_insertion_point(interface_extends:PeerInfo)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>optional int32 highestChunk = 1;</code>
   */
  int getHighestChunk();

  /**
   * <code>optional string uuid = 2;</code>
   */
  java.lang.String getUuid();
  /**
   * <code>optional string uuid = 2;</code>
   */
  com.google.protobuf.ByteString
      getUuidBytes();

  /**
   * <code>optional string address = 3;</code>
   */
  java.lang.String getAddress();
  /**
   * <code>optional string address = 3;</code>
   */
  com.google.protobuf.ByteString
      getAddressBytes();

  /**
   * <pre>
   *&lt;uuid, address:port&gt;
   * </pre>
   *
   * <code>map&lt;string, string&gt; peers = 4;</code>
   */
  int getPeersCount();
  /**
   * <pre>
   *&lt;uuid, address:port&gt;
   * </pre>
   *
   * <code>map&lt;string, string&gt; peers = 4;</code>
   */
  boolean containsPeers(
      java.lang.String key);
  /**
   * Use {@link #getPeersMap()} instead.
   */
  @java.lang.Deprecated
  java.util.Map<java.lang.String, java.lang.String>
  getPeers();
  /**
   * <pre>
   *&lt;uuid, address:port&gt;
   * </pre>
   *
   * <code>map&lt;string, string&gt; peers = 4;</code>
   */
  java.util.Map<java.lang.String, java.lang.String>
  getPeersMap();
  /**
   * <pre>
   *&lt;uuid, address:port&gt;
   * </pre>
   *
   * <code>map&lt;string, string&gt; peers = 4;</code>
   */

  java.lang.String getPeersOrDefault(
      java.lang.String key,
      java.lang.String defaultValue);
  /**
   * <pre>
   *&lt;uuid, address:port&gt;
   * </pre>
   *
   * <code>map&lt;string, string&gt; peers = 4;</code>
   */

  java.lang.String getPeersOrThrow(
      java.lang.String key);
}