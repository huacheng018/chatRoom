package chat.groupChat;

import server.ServerManagement;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/** 多线程封装发送端
 * 1 发送消息
 * 2 从控制台获取消息
 * 3 释放资源
 * 4 重写run
 * @Author: 枠成
 * @Data:2019-05-27
 * @Description:version2
 * @version:1.0
 */
public class Send implements Runnable{

    private DataOutputStream dos;
    private Socket client;
    private boolean isRunning;
    private String name;

    //构造器
    public Send(Socket client, String name) {
        this.client = client;
        this.isRunning = true;
        this.name = name;
        this.isRunning = true;
        //处理流
        try {
            dos = new DataOutputStream(client.getOutputStream());
            //发送用户名
            send(name);
        } catch (IOException e) {
            System.out.println("---客户发送端封装位置出现问题---");
            this.release();
        }
    }

    //从sendText获取文本
    public String getStrFromSendText() {
        return Controller.getInstacnce().sendText.getText();
    }

    //发送消息
    private void send(String msg) {
        try {
            dos.writeUTF(msg);
            dos.flush();
        } catch (IOException e) {
            System.out.println("--客户发送端发送问题--");
            release();
        }
    }

    @Override
    public void run() {
        while (isRunning) {
            //获取消息
            String message = getStrFromSendText();
            if (!message.equals("")&& Controller.getInstacnce().isSending) {
                send(message);

                //清空文本框
                Controller.getInstacnce().sendText.clear();
                //归位为false
                Controller.getInstacnce().isSending = false;
            }

//            //显示在线用户——实现失败，无法执行，不catch会跳空指针——
//                 可能单例双开会有跳bug，也可能Controller的命名本身就不能瞎改
//            try {
//                ServerManagement.getInstacnce().checkLogining(Controller.getInstacnce().loginPeople2);
//            }catch (Exception e){
//
//            }

            //睡一下，避免重复多发还有溢出
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //释放资源
    private void release() {
        this.isRunning = false;
        Utils.closeAll(dos,client);
    }
}
