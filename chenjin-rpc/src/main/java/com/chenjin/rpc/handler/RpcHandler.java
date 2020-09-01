package com.chenjin.rpc.handler;

import com.chenjin.rpc.pojo.RpcRequest;
import com.chenjin.rpc.pojo.RpcResponse;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * 使用RpcHandler中处理 RPC 请求，只需扩展 Netty 的SimpleChannelInboundHandler抽象类即可
 */
public class RpcHandler extends SimpleChannelInboundHandler<RpcRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RpcHandler.class);
    private final Map<String, Object> handlerMap;

    public RpcHandler(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest request) throws Exception {
        RpcResponse response = new RpcResponse();
        response.setRequestId(request.getRequestId());
        Object result = null;
        try {
            result = handle(request);
        } catch (Throwable throwable) {
            response.setError(throwable);
        }
        response.setResult(result);
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);

    }

    private Object handle(RpcRequest request) throws  Throwable {
        String className = request.getClassName();
        Object serviceBean = handlerMap.get(className);
        Class<?> serviceClass = serviceBean.getClass();
        String methodName = request.getMethodName();
        Object[] parameters = request.getParameters();
        Class<?>[] parameterTypes = request.getParameterTypes();
        FastClass serviceFastClass = FastClass.create(serviceClass);
        FastMethod method = serviceFastClass.getMethod(methodName, parameterTypes);
        Object invoke = method.invoke(serviceBean, parameters);
        return invoke;
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOGGER.error("rpcHandler出现异常", cause);
        ctx.close();
    }
}
