package chat.groupChat;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/** 多线程封装接收端
* 1 接收消息
 * 2 释放资源
 * 3 重写run
 * @Author: 枠成
 * @Data:2019-05-27
 * @Description:version2
 * @version:1.0
 */
public class Receive implements Runnable{

    private DataInputStream dis ;
    private Socket client;
    private boolean isRunning;

    //构造器-接收
    public Receive(Socket client){
        this.client = client;
        this.isRunning = true;
        //流处理
        try {
            dis = new DataInputStream(client.getInputStream());
        } catch (IOException e) {
            System.out.println("---客户接收端Socket问题---");
            release();
        }
    }

    //接收消息
    private String receive(){
        String msg = "";
        try {
            msg = dis.readUTF();
        } catch (IOException e) {
            System.out.println("--客户接收端接收问题--");
            release();
        }
        return msg;
    }

    @Override
    public void run() {
        while (isRunning){
            String msg = receive();
            if (!msg.equals("")){
                Controller.getInstacnce().receivText.appendText("\n\n"+msg);
            }
        }
    }

    //释放资源
    private void release(){
        this.isRunning = false;
        Utils.closeAll(dis,client);
    }
}
