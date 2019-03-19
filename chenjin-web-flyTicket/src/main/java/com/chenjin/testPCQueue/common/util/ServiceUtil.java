package com.chenjin.testPCQueue.common.util;

import java.rmi.RemoteException;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;
import org.apache.axis.Constants;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

public class ServiceUtil
{
    public static String callWebService(String wsdlUrl, String targetNamespace, String methodName, String[] data)
            throws ServiceException, RemoteException
    {
        Service service = new Service();

        Call call = (Call)service.createCall();
        call.setTargetEndpointAddress(wsdlUrl);
        call.setOperationName(new QName(targetNamespace, methodName));

        call.addParameter("sign", Constants.XSD_STRING, ParameterMode.IN);
        call.addParameter("dataType", Constants.XSD_STRING, ParameterMode.IN);
        call.addParameter("data", Constants.XSD_STRING, ParameterMode.IN);
        call.setReturnClass(String.class);

        String result = (String)call.invoke(data);
        return result;
    }
}