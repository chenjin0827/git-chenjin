package com.chenjin.testPCQueue.common.framework.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IpUtil
{
    private static Logger logger = LoggerFactory.getLogger(IpUtil.class);

    public static String getIpAddr(HttpServletRequest request)
    {
        String ipAddress = null;

        ipAddress = request.getHeader("x-forwarded-for");
        if ((ipAddress == null) || (ipAddress.length() == 0) ||
                ("unknown"
                        .equalsIgnoreCase(ipAddress)))
        {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if ((ipAddress == null) || (ipAddress.length() == 0) ||
                ("unknown"
                        .equalsIgnoreCase(ipAddress)))
        {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if ((ipAddress == null) || (ipAddress.length() == 0) ||
                ("unknown"
                        .equalsIgnoreCase(ipAddress)))
        {
            ipAddress = request.getRemoteAddr();
            if ((ipAddress.equals("127.0.0.1")) ||
                    (ipAddress
                            .equals("0:0:0:0:0:0:0:1")))
            {
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }

        }

        if ((ipAddress != null) && (ipAddress.length() > 15))
        {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    public static String getGuessUniqueIP()
    {
        try
        {
            Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface)interfaces.nextElement();
                Enumeration inetAddresses = ni.getInetAddresses();

                while (inetAddresses.hasMoreElements()) {
                    InetAddress address = (InetAddress)inetAddresses.nextElement();
                    if (((address instanceof Inet4Address)) &&
                            (!"127.0.0.1".equals(address.getHostAddress())))
                        return address.getHostAddress();
                }
            }
        }
        catch (Exception e)
        {
            logger.error("Get IP Error", e);
        }
        return null;
    }

    public String getIPInfo() {
        StringBuilder sb = new StringBuilder();
        try
        {
            Enumeration interfaces = NetworkInterface.getNetworkInterfaces();

            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface)interfaces.nextElement();

                sb.append(new StringBuilder().append("Interface ").append(ni.getName()).append(":\r\n").toString());

                Enumeration inetAddresses = ni.getInetAddresses();

                while (inetAddresses.hasMoreElements()) {
                    InetAddress address = (InetAddress)inetAddresses.nextElement();

                    sb.append("Address");

                    if ((address instanceof Inet4Address))
                        sb.append("(v4)");
                    else {
                        sb.append("(v6)");
                    }

                    sb.append(new StringBuilder().append(":address=").append(address.getHostAddress()).append(" name=").append(address.getHostName()).append("\r\n").toString());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(getGuessUniqueIP());
    }
}