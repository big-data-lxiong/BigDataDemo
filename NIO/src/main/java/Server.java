import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author sugon
 */
public class Server implements Runnable {
    private Selector selector;
    private final ByteBuffer buffer = ByteBuffer.allocate(1024); //?

    public Server(int port) {
        try {
            selector = Selector.open();
            ServerSocketChannel ssc = ServerSocketChannel.open();
            //设置服务器为非阻塞方式
            ssc.configureBlocking(false);
            ssc.bind(new InetSocketAddress(port));
            //把服务器通道注册到多路复用选择器上，并监听阻塞状态
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Server start whit port : " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                //会这这里处理事件，也是阻塞的，事件包括客户端连接，客户端发送数据到来,以及客户端断开连接等等
                //若没有事件发生，也会阻塞
                selector.select();
                //System.out.println("阻塞在这");
                //返回所有已经注册到多路复用选择器的通道的SelectionKey
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                //遍历keys
                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    //下一个key，就像数组访问的i++
                    keys.remove();
                    if (key.isValid()) {           //判断key是否有效
                        if (key.isAcceptable()) {  //请求连接事件
                            accept(key);         //处理新客户的连接
                        }
                        if (key.isReadable()) {    //有数据到来
                            read(key);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理客户端连接
     * 服务器为每个客户端生成一个Channel
     * Channel与客户端对接
     * Channel绑定到Selector上
     **/
    private void accept(SelectionKey key) {
        try {
            //获取之前注册的SocketChannel通道
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            //执行阻塞方法，Channel和客户端对接
            SocketChannel sc = ssc.accept();
            //设置模式为非阻塞
            sc.configureBlocking(false);
            sc.register(selector, SelectionKey.OP_READ);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void read(SelectionKey key) {

        try {
            //清空缓冲区的旧数据
            buffer.clear();
            SocketChannel sc = (SocketChannel) key.channel();
            int count = sc.read(buffer);
            if (count == -1) {
                key.channel().close();
                key.cancel();
                return;
            }
            //读取到了数据，将buffer的position复位到0
            buffer.flip();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            String body = new String(bytes).trim();
            System.out.println("Server:" + body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Thread(new Server(8370)).start();
    }

}