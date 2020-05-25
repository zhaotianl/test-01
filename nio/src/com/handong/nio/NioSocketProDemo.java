package com.handong.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioSocketProDemo {
	
	private ServerSocketChannel server = null ;
	private Selector selector = null;
	int port = 9090;
	
	public void initServer() {
		try {
			server = ServerSocketChannel.open();
			server.configureBlocking(false);
			server.bind(new InetSocketAddress(port));
			selector = Selector.open();
			server.register(selector, SelectionKey.OP_ACCEPT);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void start() {
		initServer();
		System.out.println("服务器启动了。。。。。。。。");
		while(true) {
			try {
				while(selector.select(0)>0) {// 问过内核了 有没有事件 ， 回复 有：
					Set<SelectionKey> selectedKeys = selector.selectedKeys();// 从多路复用器中取出有效的key
					Iterator<SelectionKey> iterator = selectedKeys.iterator();
					while(iterator.hasNext()) {
						SelectionKey key = iterator.next();
						iterator.remove();
						// 一般就判断这两个状态
						if(key.isAcceptable()) {
							acceptHandler(key);
						}else if(key.isReadable()) {
							
							
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public void acceptHandler(SelectionKey key) {
		ServerSocketChannel sss = (ServerSocketChannel)key.channel();
		try {
			SocketChannel client = sss.accept();
			client.configureBlocking(false);
			ByteBuffer buffer = ByteBuffer.allocate(8192);
			client.register(selector, SelectionKey.OP_ACCEPT, buffer);
			System.out.println("---------------------------");
			System.out.println("新客户端：--------"+client.getRemoteAddress());
			System.out.println("---------------------------");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void readHandler(SelectionKey key) {
		SocketChannel client = (SocketChannel)key.channel();
		ByteBuffer buffer = (ByteBuffer)key.attachment();
		buffer.clear();
		int read = 0;
		while(true) {
			try {
				read = client.read(buffer);
				if(read>0) {
					buffer.flip();
					while(buffer.hasRemaining()) {
						client.write(buffer);
					}
					buffer.clear();
				}else if(read == 0) {
					break;
				}else { // -1 close_wait bug 死循环 CPU到100% 
					client.close();
					break;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public static void main(String[] args) {
		NioSocketProDemo nioSocketProDemo = new NioSocketProDemo();
		nioSocketProDemo.start();
	}


}
