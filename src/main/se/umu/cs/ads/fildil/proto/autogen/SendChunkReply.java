// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Protocol.proto

package se.umu.cs.ads.fildil.proto.autogen;

/**
 * Protobuf type {@code SendChunkReply}
 */
public  final class SendChunkReply extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:SendChunkReply)
    SendChunkReplyOrBuilder {
  // Use SendChunkReply.newBuilder() to construct.
  private SendChunkReply(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private SendChunkReply() {
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private SendChunkReply(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
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
    return se.umu.cs.ads.fildil.proto.autogen.Protocol.internal_static_SendChunkReply_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return se.umu.cs.ads.fildil.proto.autogen.Protocol.internal_static_SendChunkReply_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            se.umu.cs.ads.fildil.proto.autogen.SendChunkReply.class, se.umu.cs.ads.fildil.proto.autogen.SendChunkReply.Builder.class);
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
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    memoizedSize = size;
    return size;
  }

  private static final long serialVersionUID = 0L;
  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof se.umu.cs.ads.fildil.proto.autogen.SendChunkReply)) {
      return super.equals(obj);
    }
    se.umu.cs.ads.fildil.proto.autogen.SendChunkReply other = (se.umu.cs.ads.fildil.proto.autogen.SendChunkReply) obj;

    boolean result = true;
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptorForType().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static se.umu.cs.ads.fildil.proto.autogen.SendChunkReply parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static se.umu.cs.ads.fildil.proto.autogen.SendChunkReply parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static se.umu.cs.ads.fildil.proto.autogen.SendChunkReply parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static se.umu.cs.ads.fildil.proto.autogen.SendChunkReply parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static se.umu.cs.ads.fildil.proto.autogen.SendChunkReply parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static se.umu.cs.ads.fildil.proto.autogen.SendChunkReply parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static se.umu.cs.ads.fildil.proto.autogen.SendChunkReply parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static se.umu.cs.ads.fildil.proto.autogen.SendChunkReply parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static se.umu.cs.ads.fildil.proto.autogen.SendChunkReply parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static se.umu.cs.ads.fildil.proto.autogen.SendChunkReply parseFrom(
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
  public static Builder newBuilder(se.umu.cs.ads.fildil.proto.autogen.SendChunkReply prototype) {
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
   * Protobuf type {@code SendChunkReply}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:SendChunkReply)
      se.umu.cs.ads.fildil.proto.autogen.SendChunkReplyOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return se.umu.cs.ads.fildil.proto.autogen.Protocol.internal_static_SendChunkReply_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return se.umu.cs.ads.fildil.proto.autogen.Protocol.internal_static_SendChunkReply_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              se.umu.cs.ads.fildil.proto.autogen.SendChunkReply.class, se.umu.cs.ads.fildil.proto.autogen.SendChunkReply.Builder.class);
    }

    // Construct using se.umu.cs.ads.fildil.proto.autogen.SendChunkReply.newBuilder()
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
      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return se.umu.cs.ads.fildil.proto.autogen.Protocol.internal_static_SendChunkReply_descriptor;
    }

    public se.umu.cs.ads.fildil.proto.autogen.SendChunkReply getDefaultInstanceForType() {
      return se.umu.cs.ads.fildil.proto.autogen.SendChunkReply.getDefaultInstance();
    }

    public se.umu.cs.ads.fildil.proto.autogen.SendChunkReply build() {
      se.umu.cs.ads.fildil.proto.autogen.SendChunkReply result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public se.umu.cs.ads.fildil.proto.autogen.SendChunkReply buildPartial() {
      se.umu.cs.ads.fildil.proto.autogen.SendChunkReply result = new se.umu.cs.ads.fildil.proto.autogen.SendChunkReply(this);
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
      if (other instanceof se.umu.cs.ads.fildil.proto.autogen.SendChunkReply) {
        return mergeFrom((se.umu.cs.ads.fildil.proto.autogen.SendChunkReply)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(se.umu.cs.ads.fildil.proto.autogen.SendChunkReply other) {
      if (other == se.umu.cs.ads.fildil.proto.autogen.SendChunkReply.getDefaultInstance()) return this;
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
      se.umu.cs.ads.fildil.proto.autogen.SendChunkReply parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (se.umu.cs.ads.fildil.proto.autogen.SendChunkReply) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
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


    // @@protoc_insertion_point(builder_scope:SendChunkReply)
  }

  // @@protoc_insertion_point(class_scope:SendChunkReply)
  private static final se.umu.cs.ads.fildil.proto.autogen.SendChunkReply DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new se.umu.cs.ads.fildil.proto.autogen.SendChunkReply();
  }

  public static se.umu.cs.ads.fildil.proto.autogen.SendChunkReply getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<SendChunkReply>
      PARSER = new com.google.protobuf.AbstractParser<SendChunkReply>() {
    public SendChunkReply parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new SendChunkReply(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<SendChunkReply> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<SendChunkReply> getParserForType() {
    return PARSER;
  }

  public se.umu.cs.ads.fildil.proto.autogen.SendChunkReply getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
