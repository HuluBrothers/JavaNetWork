package org.zhx.demo.net.chapter11;

import java.io.IOException;
import java.net.SocketOption;
import java.nio.channels.*;

public class OptionSupport {
    public static void main(String[] args) throws IOException {
        printOptions(SocketChannel.open());
        printOptions(ServerSocketChannel.open());
        printOptions(AsynchronousSocketChannel.open());
        printOptions(AsynchronousServerSocketChannel.open());
        printOptions(DatagramChannel.open());
    }

    private static void printOptions(NetworkChannel channel)  {
        System.out.println(channel.getClass().getSimpleName() + " supports:");
        for (SocketOption<?> option : channel.supportedOptions()) {
            try {
                System.out.println(option.name() + ": " + channel.getOption(option));
            } catch (Exception e) {
                System.out.println(option.name() + ":" + e.getMessage());
            }
        }
        System.out.println();

        try {
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
