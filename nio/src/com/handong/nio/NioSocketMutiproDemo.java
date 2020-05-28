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
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class NioSocketMutiproDemo {
	
	private ServerSocketChannel server = null ;
	private Selector selector1 = null;
	private Selector selector2 = null;
	private Selector selector3 = null;
	int port = 9090;
	
	public void initServer() {
		try {
			server = ServerSocketChannel.open();
			server.configureBlocking(false);
			server.bind(new InetSocketAddress(port));
			selector1 = Selector.open();
			selector2 = Selector.open();
			selector3 = Selector.open();
			server.register(selector1, SelectionKey.OP_ACCEPT);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		NioSocketMutiproDemo service = new NioSocketMutiproDemo();
		service.initServer();
		NioThread T1 = new NioThread(service.selector1,2);
		NioThread T2 = new NioThread(service.selector2);
		NioThread T3 = new NioThread(service.selector3);
		T1.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		T2.start();
		T3.start();
		System.out.println("服务器启动了。。。。。");
	}
	

}


class NioThread extends Thread{
	Selector selector  = null ;
	static int selectors  = 0 ;
	boolean boss = true;
	
	int id = 0;
	
	static BlockingDeque<SocketChannel> [] queue;
	
	static AtomicInteger idx = new AtomicInteger();
	
	NioThread(Selector sel, int n){ // 这个构造是给boss用的
		this.selector  = sel ;
		this.selectors = n;
		
		queue = new LinkedBlockingDeque[selectors];
		
		for(int i = 0; i < n; i++) {
			queue[i] = new LinkedBlockingDeque<>();
		}
		
		System.out.println("Boss  启动  ");
		
	}
	
	NioThread(Selector sel){
		this.selector = sel;
		id = idx.getAndIncrement() % selectors;
		System.out.println("worker id = "+id+" 启动 ");
		
	}
	
	
	
	public void acceptHandler(SelectionKey key) {
		ServerSocketChannel sss = (ServerSocketChannel)key.channel();
		try {
			SocketChannel client = sss.accept();
			client.configureBlocking(false);
			int num = idx.getAndIncrement()%selectors;
			queue[num].add(client);
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
	
	@Override
	public void run() {
		while(true) {
			try {
				while(selector.select(10)>0) {
					Set<SelectionKey> selectedKeys = selector.selectedKeys();
					Iterator<SelectionKey> iterator = selectedKeys.iterator();
					while(iterator.hasNext()) {
						SelectionKey key = iterator.next();
						iterator.remove();
						// 一般就判断这两个状态
						if(key.isAcceptable()) {
							acceptHandler(key);
						}else if(key.isReadable()) {
							readHandler(key);
						}
					}
				}
				boss = false;
				if(boss && !queue[id].isEmpty()) { // 因为有三个线程，boss不参与，只有worker根据分配，分别注册自己的client
					ByteBuffer buffer = ByteBuffer.allocate(8192);
					SocketChannel client = null;
					try {
						client = queue[id].take();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					client.register(selector, SelectionKey.OP_READ, buffer);	
					System.out.println("--------------------------------");
					System.out.println("新客户端--"+client.socket().getPort()+"分配到worker："+id);
					System.out.println("---------------------------------");
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}






