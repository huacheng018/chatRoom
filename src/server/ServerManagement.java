package server;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author: 枠成
 * @Data:2019-06-04
 * @Description:server
 * @version:1.0
 */
public class ServerManagement implements  Runnable{

    //加入容器实现群聊管理
    private static CopyOnWriteArrayList<Channel> all = new CopyOnWriteArrayList<Channel>();

    @Override
    public void run() {
        try {
            //打开server服务，方法在下一个
            openServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openServer() throws IOException {
        System.out.println("-----Server-----");
        //1 指定端口 使用ServerSocket创建服务器
        ServerSocket server = new ServerSocket(8888);

        //2 多线程阻塞式等待连接 accept
        while (true) {
            Socket client = server.accept();
            System.out.println("一个客户端建立了连接");
            Channel channel = new Channel(client);
            all.add(channel);//交给容器管理所有的成员
            //开启
            new Thread(channel).start();
        }
    }

    //一个用户，一个Channel，封装的用户内部类
    static class Channel implements Runnable {

        /**dis为输入流
         * dos为输出流
         */
        private DataInputStream dis;
        private DataOutputStream dos;
        private Socket client;
        private boolean isRunning;
        private  String name;

        //封装
        public Channel(Socket client){
            this.client = client;
            //流处理
            try {
                dis = new DataInputStream(client.getInputStream());
                dos = new DataOutputStream(client.getOutputStream());
                isRunning = true;
                //获取名称
                this.name = receive();
                //发送 欢迎你的到来
                this.send("欢迎"+name+"的到来");
                //发给其他人
                sendOthers(this.name+"进入呆瓜集中营",true);
            } catch (IOException e) {
                System.out.println("--服务器端Channel问题--");
                release();
            }
        }

        //接收消息
        private String receive(){
            String message = "";
            try {
                message = dis.readUTF();
            } catch (Exception e) {
                System.out.println("--服务器端接收问题--");
                release();
            }
            return message;
        }

        //发送消息
        private void send(String msg){
            try {
                dos.writeUTF(msg);
                dos.flush();
            } catch (IOException e) {
                System.out.println("--服务器端发送问题--");
                release();
            }
        }

        /**
         * 群聊发送消息:获取自己的消息发给其他人
         * 私聊约定数据格式：@xxx:msg
         * @param msg
         * @param isSystem
         */
        private void sendOthers(String msg,boolean isSystem){
            //判断是否有@
            Boolean isPrivate = msg.startsWith("@");

            if (isPrivate){//如果是私聊
                //获取“:”的位置
                int index = msg.indexOf(":");
                //获取目标和数据
                String targetName = msg.substring(1,index);
                msg = msg.substring(index+1);
                for (Channel other:all){
                    if (other.name.equals(targetName)){//私聊消息
                        other.send(this.name+"私聊你个死鬼："+msg);
                    }
                }
            }else{
                for (Channel other:all){
//                    //过滤自己
//                    if (other==this){
//                        continue;
//                    }
                    if (!isSystem) {//判断是否系统消息
                        other.send(this.name + "：\n" + msg);//群聊消息
                    }else {
                        other.send(msg);//系统消息
                    }
                }
            }
        }

        //释放资源
        private void release(){
            this.isRunning = false;
            Utils.closeAll(dis,dos,client);
            //退出，移出容器
            all.remove(this);
            sendOthers(this.name+"退出聊天室",false);
        }

        @Override
        public void run(){
            while (isRunning){
                String msg = receive();
                if (!msg.equals("")){
                    sendOthers(msg,false);
                }
                //服务器端显示在线用户
                ServerManagement.getInstacnce().checkLogining(ServerManagement.getInstacnce().loginPeople);
            }
        }
    }

    /**
     * 显示在线人员，用的是群里师兄教的单例模式
     * 暂用textArea显示，有空余时间再升级TabelView
     */

    private static ServerManagement instacnce;

    public static ServerManagement getInstacnce(){
        return instacnce;
    }

    public ServerManagement(){
        instacnce = this;
    }

    @FXML
    public TextArea loginPeople;

    public void checkLogining(TextArea textArea){
        textArea.clear();
        //遍历容器
        for (int i = 0;i<all.size();i++){
            String strName = all.get(i).name;
            textArea.appendText((i+1)+"、"+strName+"\n\n");
        }
    }
}
