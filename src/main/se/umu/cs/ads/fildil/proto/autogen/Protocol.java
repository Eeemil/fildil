// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Protocol.proto

package se.umu.cs.ads.fildil.proto.autogen;

public final class Protocol {
  private Protocol() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_Chunk_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Chunk_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_PeerInfo_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_PeerInfo_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_PeerInfo_PeersEntry_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_PeerInfo_PeersEntry_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ChunkRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ChunkRequest_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\016Protocol.proto\" \n\005Chunk\022\n\n\002id\030\001 \001(\005\022\013\n" +
      "\003buf\030\002 \001(\014\"\222\001\n\010PeerInfo\022\024\n\014highestChunk\030" +
      "\001 \001(\005\022\014\n\004uuid\030\002 \001(\t\022\017\n\007address\030\003 \001(\t\022#\n\005" +
      "peers\030\004 \003(\0132\024.PeerInfo.PeersEntry\032,\n\nPee" +
      "rsEntry\022\013\n\003key\030\001 \001(\t\022\r\n\005value\030\002 \001(\t:\0028\001\"" +
      "\032\n\014ChunkRequest\022\n\n\002id\030\001 \001(\0052S\n\010Streamer\022" +
      "\'\n\014requestChunk\022\r.ChunkRequest\032\006.Chunk\"\000" +
      "\022\036\n\004poll\022\t.PeerInfo\032\t.PeerInfo\"\000B0\n\"se.u" +
      "mu.cs.ads.fildil.proto.autogenB\010Protocol" +
      "P\001b\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_Chunk_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_Chunk_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Chunk_descriptor,
        new java.lang.String[] { "Id", "Buf", });
    internal_static_PeerInfo_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_PeerInfo_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_PeerInfo_descriptor,
        new java.lang.String[] { "HighestChunk", "Uuid", "Address", "Peers", });
    internal_static_PeerInfo_PeersEntry_descriptor =
      internal_static_PeerInfo_descriptor.getNestedTypes().get(0);
    internal_static_PeerInfo_PeersEntry_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_PeerInfo_PeersEntry_descriptor,
        new java.lang.String[] { "Key", "Value", });
    internal_static_ChunkRequest_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_ChunkRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ChunkRequest_descriptor,
        new java.lang.String[] { "Id", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
