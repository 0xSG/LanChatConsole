/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lan_chating;
/////0XPULSAR/////S G K //////
import java.io.IOException;
import java.util.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sooryagangarajk
 */
public class LAN_CHATING {

    public static String recvIpAddress;
    public static Scanner input;
    public static DatagramSocket skt;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SocketException, UnknownHostException, IOException {
        input = new Scanner(System.in);
        InetAddress addr = InetAddress.getLocalHost();
        System.out.println(addr);
        skt = new DatagramSocket(5545);
        byte[] msgGotByte = new byte[1000];
        Thread t1 = new Thread(
                new Runnable() {///// S G K /////
                    //THIS THREAD WILL DISPLAY THE MSG FROM THE CLIENT
            @Override
            public void run() {
                while (true) {
                    DatagramPacket gotMsgPkt = new DatagramPacket(msgGotByte, msgGotByte.length);
                    try {
                        skt.receive(gotMsgPkt);
                        System.out.println();
                        System.out.println((gotMsgPkt.getAddress()).toString().substring(1) + ":" + (new String(gotMsgPkt.getData())).substring(0, gotMsgPkt.getLength()));
                    } catch (IOException ex) {/////0XPULSAR/////S G K //////
                        Logger.getLogger(LAN_CHATING.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        });
        t1.start();
        
        //Enter ip address:
        //Eg - 127.0.0.1 or 192.168.1.2

        System.out.print("Enter ip address:");
        recvIpAddress = input.nextLine();
        InetAddress recrIp = InetAddress.getByName(recvIpAddress);
        System.out.println("Now you are chatting with " + recvIpAddress);

        while (true) {
            //THIS IS MAIN THREAD - SENDS THE MSG TO THE CLIENT
            System.out.print("You:");
            String yourMsg = input.next();
            byte[] buffer = new byte[yourMsg.length()];
            buffer = yourMsg.getBytes();
            DatagramPacket myMsgPkt = new DatagramPacket(buffer, buffer.length, recrIp, 5545);//CREATED THE PACKET
            skt.send(myMsgPkt);//SENDS THE DATA PACKET THROUGH THE SOCKET
            try {
                Thread.sleep(150);//REDUCE THE LOAD ON THE MAIN THREAD
            } catch (InterruptedException ex) {
                Logger.getLogger(LAN_CHATING.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

}/////END/////
