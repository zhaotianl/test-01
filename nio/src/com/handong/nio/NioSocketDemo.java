package com.handong.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;

public class NioSocketDemo {
	
	public static void main(String[] args) throws Exception {
		LinkedList<SocketChannel> clients = new LinkedList<>();
		ServerSocketChannel ss = ServerSocketChannel.open();
		ss.bind(new InetSocketAddress(9090));
		ss.configureBlocking(false);// 配置成非阻塞
		while (true) {
			Thread.sleep(1000);
			SocketChannel cilent = ss.accept();// 不会阻塞
			if (cilent == null) {
				System.out.println("null......");
			} else {
				cilent.configureBlocking(false);
				int port = cilent.socket().getPort();
				System.out.println("client port......" + port);
				clients.add(cilent);
			}
			// ---------------------------------------------- boss ---- worker ------ netty 二者也可组合 -------------------------------------------
			ByteBuffer buffer = ByteBuffer.allocateDirect(4096); // 可以在堆里 也可以在堆外
			for (SocketChannel c : clients) {   // 因为是单线程 所以是串行化读取
				int num = c.read(buffer); // 1 表示读到了数据 0表示可以直接跳过-1   这个过程不会阻塞
				if (num > 0) {
					buffer.flip();
					byte[] aaa = new byte[buffer.limit()];
					buffer.get(aaa);
					String b = new String(aaa);
					System.out.println(c.socket().getPort() + "  :  " + b);
					buffer.clear();
				}
			}
		}

	}

}
