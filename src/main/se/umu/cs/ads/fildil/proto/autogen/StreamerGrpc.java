package se.umu.cs.ads.fildil.proto.autogen;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.0.1)",
    comments = "Source: Protocol.proto")
public class StreamerGrpc {

  private StreamerGrpc() {}

  public static final String SERVICE_NAME = "Streamer";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<se.umu.cs.ads.fildil.proto.autogen.ChunkRequest,
      se.umu.cs.ads.fildil.proto.autogen.Chunk> METHOD_REQUEST_CHUNK =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "Streamer", "requestChunk"),
          io.grpc.protobuf.ProtoUtils.marshaller(se.umu.cs.ads.fildil.proto.autogen.ChunkRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(se.umu.cs.ads.fildil.proto.autogen.Chunk.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<se.umu.cs.ads.fildil.proto.autogen.Empty,
      se.umu.cs.ads.fildil.proto.autogen.Chunk> METHOD_POLL =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "Streamer", "poll"),
          io.grpc.protobuf.ProtoUtils.marshaller(se.umu.cs.ads.fildil.proto.autogen.Empty.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(se.umu.cs.ads.fildil.proto.autogen.Chunk.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<se.umu.cs.ads.fildil.proto.autogen.Chunk,
      se.umu.cs.ads.fildil.proto.autogen.ReceiveChunkReply> METHOD_RECEIVE_CHUNK =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "Streamer", "receiveChunk"),
          io.grpc.protobuf.ProtoUtils.marshaller(se.umu.cs.ads.fildil.proto.autogen.Chunk.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(se.umu.cs.ads.fildil.proto.autogen.ReceiveChunkReply.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static StreamerStub newStub(io.grpc.Channel channel) {
    return new StreamerStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static StreamerBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new StreamerBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static StreamerFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new StreamerFutureStub(channel);
  }

  /**
   */
  public static abstract class StreamerImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *Request a specific chunk
     * </pre>
     */
    public void requestChunk(se.umu.cs.ads.fildil.proto.autogen.ChunkRequest request,
        io.grpc.stub.StreamObserver<se.umu.cs.ads.fildil.proto.autogen.Chunk> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_REQUEST_CHUNK, responseObserver);
    }

    /**
     * <pre>
     *Return the highest numbered chunk the node has received
     * </pre>
     */
    public void poll(se.umu.cs.ads.fildil.proto.autogen.Empty request,
        io.grpc.stub.StreamObserver<se.umu.cs.ads.fildil.proto.autogen.Chunk> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_POLL, responseObserver);
    }

    /**
     * <pre>
     *Force-feed a specific chunk
     * </pre>
     */
    public void receiveChunk(se.umu.cs.ads.fildil.proto.autogen.Chunk request,
        io.grpc.stub.StreamObserver<se.umu.cs.ads.fildil.proto.autogen.ReceiveChunkReply> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_RECEIVE_CHUNK, responseObserver);
    }

    @java.lang.Override public io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_REQUEST_CHUNK,
            asyncUnaryCall(
              new MethodHandlers<
                se.umu.cs.ads.fildil.proto.autogen.ChunkRequest,
                se.umu.cs.ads.fildil.proto.autogen.Chunk>(
                  this, METHODID_REQUEST_CHUNK)))
          .addMethod(
            METHOD_POLL,
            asyncUnaryCall(
              new MethodHandlers<
                se.umu.cs.ads.fildil.proto.autogen.Empty,
                se.umu.cs.ads.fildil.proto.autogen.Chunk>(
                  this, METHODID_POLL)))
          .addMethod(
            METHOD_RECEIVE_CHUNK,
            asyncUnaryCall(
              new MethodHandlers<
                se.umu.cs.ads.fildil.proto.autogen.Chunk,
                se.umu.cs.ads.fildil.proto.autogen.ReceiveChunkReply>(
                  this, METHODID_RECEIVE_CHUNK)))
          .build();
    }
  }

  /**
   */
  public static final class StreamerStub extends io.grpc.stub.AbstractStub<StreamerStub> {
    private StreamerStub(io.grpc.Channel channel) {
      super(channel);
    }

    private StreamerStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StreamerStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new StreamerStub(channel, callOptions);
    }

    /**
     * <pre>
     *Request a specific chunk
     * </pre>
     */
    public void requestChunk(se.umu.cs.ads.fildil.proto.autogen.ChunkRequest request,
        io.grpc.stub.StreamObserver<se.umu.cs.ads.fildil.proto.autogen.Chunk> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_REQUEST_CHUNK, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *Return the highest numbered chunk the node has received
     * </pre>
     */
    public void poll(se.umu.cs.ads.fildil.proto.autogen.Empty request,
        io.grpc.stub.StreamObserver<se.umu.cs.ads.fildil.proto.autogen.Chunk> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_POLL, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *Force-feed a specific chunk
     * </pre>
     */
    public void receiveChunk(se.umu.cs.ads.fildil.proto.autogen.Chunk request,
        io.grpc.stub.StreamObserver<se.umu.cs.ads.fildil.proto.autogen.ReceiveChunkReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_RECEIVE_CHUNK, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class StreamerBlockingStub extends io.grpc.stub.AbstractStub<StreamerBlockingStub> {
    private StreamerBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private StreamerBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StreamerBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new StreamerBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *Request a specific chunk
     * </pre>
     */
    public se.umu.cs.ads.fildil.proto.autogen.Chunk requestChunk(se.umu.cs.ads.fildil.proto.autogen.ChunkRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_REQUEST_CHUNK, getCallOptions(), request);
    }

    /**
     * <pre>
     *Return the highest numbered chunk the node has received
     * </pre>
     */
    public se.umu.cs.ads.fildil.proto.autogen.Chunk poll(se.umu.cs.ads.fildil.proto.autogen.Empty request) {
      return blockingUnaryCall(
          getChannel(), METHOD_POLL, getCallOptions(), request);
    }

    /**
     * <pre>
     *Force-feed a specific chunk
     * </pre>
     */
    public se.umu.cs.ads.fildil.proto.autogen.ReceiveChunkReply receiveChunk(se.umu.cs.ads.fildil.proto.autogen.Chunk request) {
      return blockingUnaryCall(
          getChannel(), METHOD_RECEIVE_CHUNK, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class StreamerFutureStub extends io.grpc.stub.AbstractStub<StreamerFutureStub> {
    private StreamerFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private StreamerFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StreamerFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new StreamerFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *Request a specific chunk
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<se.umu.cs.ads.fildil.proto.autogen.Chunk> requestChunk(
        se.umu.cs.ads.fildil.proto.autogen.ChunkRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_REQUEST_CHUNK, getCallOptions()), request);
    }

    /**
     * <pre>
     *Return the highest numbered chunk the node has received
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<se.umu.cs.ads.fildil.proto.autogen.Chunk> poll(
        se.umu.cs.ads.fildil.proto.autogen.Empty request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_POLL, getCallOptions()), request);
    }

    /**
     * <pre>
     *Force-feed a specific chunk
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<se.umu.cs.ads.fildil.proto.autogen.ReceiveChunkReply> receiveChunk(
        se.umu.cs.ads.fildil.proto.autogen.Chunk request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_RECEIVE_CHUNK, getCallOptions()), request);
    }
  }

  private static final int METHODID_REQUEST_CHUNK = 0;
  private static final int METHODID_POLL = 1;
  private static final int METHODID_RECEIVE_CHUNK = 2;

  private static class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final StreamerImplBase serviceImpl;
    private final int methodId;

    public MethodHandlers(StreamerImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_REQUEST_CHUNK:
          serviceImpl.requestChunk((se.umu.cs.ads.fildil.proto.autogen.ChunkRequest) request,
              (io.grpc.stub.StreamObserver<se.umu.cs.ads.fildil.proto.autogen.Chunk>) responseObserver);
          break;
        case METHODID_POLL:
          serviceImpl.poll((se.umu.cs.ads.fildil.proto.autogen.Empty) request,
              (io.grpc.stub.StreamObserver<se.umu.cs.ads.fildil.proto.autogen.Chunk>) responseObserver);
          break;
        case METHODID_RECEIVE_CHUNK:
          serviceImpl.receiveChunk((se.umu.cs.ads.fildil.proto.autogen.Chunk) request,
              (io.grpc.stub.StreamObserver<se.umu.cs.ads.fildil.proto.autogen.ReceiveChunkReply>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    return new io.grpc.ServiceDescriptor(SERVICE_NAME,
        METHOD_REQUEST_CHUNK,
        METHOD_POLL,
        METHOD_RECEIVE_CHUNK);
  }

}
