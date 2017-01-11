// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Protocol.proto

package se.umu.cs.ads.fildil.proto.autogen;

/**
 * Protobuf type {@code Chunk}
 */
public  final class Chunk extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:Chunk)
    ChunkOrBuilder {
  // Use Chunk.newBuilder() to construct.
  private Chunk(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private Chunk() {
    id_ = 0;
    buf_ = com.google.protobuf.ByteString.EMPTY;
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private Chunk(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    int mutable_bitField0_ = 0;
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          default: {
            if (!input.skipField(tag)) {
              done = true;
            }
            break;
          }
          case 8: {

            id_ = input.readInt32();
            break;
          }
          case 18: {

            buf_ = input.readBytes();
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return se.umu.cs.ads.fildil.proto.autogen.Protocol.internal_static_Chunk_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return se.umu.cs.ads.fildil.proto.autogen.Protocol.internal_static_Chunk_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            se.umu.cs.ads.fildil.proto.autogen.Chunk.class, se.umu.cs.ads.fildil.proto.autogen.Chunk.Builder.class);
  }

  public static final int ID_FIELD_NUMBER = 1;
  private int id_;
  /**
   * <code>optional int32 id = 1;</code>
   */
  public int getId() {
    return id_;
  }

  public static final int BUF_FIELD_NUMBER = 2;
  private com.google.protobuf.ByteString buf_;
  /**
   * <code>optional bytes buf = 2;</code>
   */
  public com.google.protobuf.ByteString getBuf() {
    return buf_;
  }

  private byte memoizedIsInitialized = -1;
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (id_ != 0) {
      output.writeInt32(1, id_);
    }
    if (!buf_.isEmpty()) {
      output.writeBytes(2, buf_);
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (id_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(1, id_);
    }
    if (!buf_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(2, buf_);
    }
    memoizedSize = size;
    return size;
  }

  private static final long serialVersionUID = 0L;
  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof se.umu.cs.ads.fildil.proto.autogen.Chunk)) {
      return super.equals(obj);
    }
    se.umu.cs.ads.fildil.proto.autogen.Chunk other = (se.umu.cs.ads.fildil.proto.autogen.Chunk) obj;

    boolean result = true;
    result = result && (getId()
        == other.getId());
    result = result && getBuf()
        .equals(other.getBuf());
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptorForType().hashCode();
    hash = (37 * hash) + ID_FIELD_NUMBER;
    hash = (53 * hash) + getId();
    hash = (37 * hash) + BUF_FIELD_NUMBER;
    hash = (53 * hash) + getBuf().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static se.umu.cs.ads.fildil.proto.autogen.Chunk parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static se.umu.cs.ads.fildil.proto.autogen.Chunk parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static se.umu.cs.ads.fildil.proto.autogen.Chunk parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static se.umu.cs.ads.fildil.proto.autogen.Chunk parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static se.umu.cs.ads.fildil.proto.autogen.Chunk parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static se.umu.cs.ads.fildil.proto.autogen.Chunk parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static se.umu.cs.ads.fildil.proto.autogen.Chunk parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static se.umu.cs.ads.fildil.proto.autogen.Chunk parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static se.umu.cs.ads.fildil.proto.autogen.Chunk parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static se.umu.cs.ads.fildil.proto.autogen.Chunk parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(se.umu.cs.ads.fildil.proto.autogen.Chunk prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code Chunk}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:Chunk)
      se.umu.cs.ads.fildil.proto.autogen.ChunkOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return se.umu.cs.ads.fildil.proto.autogen.Protocol.internal_static_Chunk_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return se.umu.cs.ads.fildil.proto.autogen.Protocol.internal_static_Chunk_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              se.umu.cs.ads.fildil.proto.autogen.Chunk.class, se.umu.cs.ads.fildil.proto.autogen.Chunk.Builder.class);
    }

    // Construct using se.umu.cs.ads.fildil.proto.autogen.Chunk.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    public Builder clear() {
      super.clear();
      id_ = 0;

      buf_ = com.google.protobuf.ByteString.EMPTY;

      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return se.umu.cs.ads.fildil.proto.autogen.Protocol.internal_static_Chunk_descriptor;
    }

    public se.umu.cs.ads.fildil.proto.autogen.Chunk getDefaultInstanceForType() {
      return se.umu.cs.ads.fildil.proto.autogen.Chunk.getDefaultInstance();
    }

    public se.umu.cs.ads.fildil.proto.autogen.Chunk build() {
      se.umu.cs.ads.fildil.proto.autogen.Chunk result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public se.umu.cs.ads.fildil.proto.autogen.Chunk buildPartial() {
      se.umu.cs.ads.fildil.proto.autogen.Chunk result = new se.umu.cs.ads.fildil.proto.autogen.Chunk(this);
      result.id_ = id_;
      result.buf_ = buf_;
      onBuilt();
      return result;
    }

    public Builder clone() {
      return (Builder) super.clone();
    }
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.setField(field, value);
    }
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof se.umu.cs.ads.fildil.proto.autogen.Chunk) {
        return mergeFrom((se.umu.cs.ads.fildil.proto.autogen.Chunk)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(se.umu.cs.ads.fildil.proto.autogen.Chunk other) {
      if (other == se.umu.cs.ads.fildil.proto.autogen.Chunk.getDefaultInstance()) return this;
      if (other.getId() != 0) {
        setId(other.getId());
      }
      if (other.getBuf() != com.google.protobuf.ByteString.EMPTY) {
        setBuf(other.getBuf());
      }
      onChanged();
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      se.umu.cs.ads.fildil.proto.autogen.Chunk parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (se.umu.cs.ads.fildil.proto.autogen.Chunk) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private int id_ ;
    /**
     * <code>optional int32 id = 1;</code>
     */
    public int getId() {
      return id_;
    }
    /**
     * <code>optional int32 id = 1;</code>
     */
    public Builder setId(int value) {
      
      id_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>optional int32 id = 1;</code>
     */
    public Builder clearId() {
      
      id_ = 0;
      onChanged();
      return this;
    }

    private com.google.protobuf.ByteString buf_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <code>optional bytes buf = 2;</code>
     */
    public com.google.protobuf.ByteString getBuf() {
      return buf_;
    }
    /**
     * <code>optional bytes buf = 2;</code>
     */
    public Builder setBuf(com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      buf_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>optional bytes buf = 2;</code>
     */
    public Builder clearBuf() {
      
      buf_ = getDefaultInstance().getBuf();
      onChanged();
      return this;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }


    // @@protoc_insertion_point(builder_scope:Chunk)
  }

  // @@protoc_insertion_point(class_scope:Chunk)
  private static final se.umu.cs.ads.fildil.proto.autogen.Chunk DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new se.umu.cs.ads.fildil.proto.autogen.Chunk();
  }

  public static se.umu.cs.ads.fildil.proto.autogen.Chunk getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<Chunk>
      PARSER = new com.google.protobuf.AbstractParser<Chunk>() {
    public Chunk parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new Chunk(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<Chunk> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<Chunk> getParserForType() {
    return PARSER;
  }

  public se.umu.cs.ads.fildil.proto.autogen.Chunk getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
