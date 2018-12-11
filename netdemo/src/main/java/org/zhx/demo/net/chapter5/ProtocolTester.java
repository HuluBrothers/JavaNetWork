package org.zhx.demo.net.chapter5;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 了解URL 和URI 中的模式（协议）
 */
public class ProtocolTester {
    public static void main(String[] args) {

        // hypertext transfer protocol 超文本传输协议
        testProtocol("http://www.adc.org");

        // secure http 安全的http
        testProtocol("https://www.amazon.com/exec/obidos/order2/");

        // file transfer protocol 文本传输协议
        testProtocol("ftp://ibiblio.org/pub/languages/java/javafaq/");

        // Simple Mail Transfer Protocol 简单的邮件传输协议
        testProtocol("mailto:elharo@ibiblio.org");

        // telnet
        testProtocol("telnet://dibner.poly.edu/");

        // local file access 本地文件访问
        testProtocol("file:///etc/passwd");

        // gopher
        testProtocol("gopher://gopher.anc.org.za/");

        // Lightweight Directory Access Protocol 轻量组目录访问协议
        testProtocol("ldap://ldap.itd.umich.edu/o=University%20of%20Michigan,c=US?postalAddress");

        // JAR
        testProtocol("jar:http://cafeaulait.org/books/javaio/ioexamples/javaio.jar!"
                        + "/com/macfaq/io/StreamCopier.class");

        // NFS, Network File System 网络文件系统
        testProtocol("nfs://utopia.poly.edu/usr/tmp/");

        // a custom protocol for JDBC JDBC的定制协议
        testProtocol("jdbc:mysql://luna.ibiblio.org:3306/NEWS");

        // rmi, a custom protocol for remote method invocation 远程方法调用的定制协议
        testProtocol("rmi://ibiblio.org/RenderEngine");

        // custom protocols for HotJava
        testProtocol("doc:/UsersGuide/release.html");
        testProtocol("netdoc:/UsersGuide/release.html");
        testProtocol("systemresource://www.adc.org/+/index.html");
        testProtocol("verbatim:http://www.adc.org/");
    }

    private static void testProtocol(String url) {
        try {
            URL u = new URL(url);
            System.out.println(u.getProtocol() + "\t" + "支持 \t" + " is supported ");
        } catch (MalformedURLException ex) {
            String protocol = url.substring(0, url.indexOf(':'));
            System.out.println(protocol + "\t" + "不支持 \t" + " is not supported");
        }
    }
}
