package edu.exersises.loadbalancer.edu.exersises.loadbalancer.http;

import edu.exersises.loadbalancer.lb.RequestDispatcher;
import edu.exersises.loadbalancer.util.edu.exersises.loadbalancer.configdata.ConfigData;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by rsmirnov on 8/1/14.
 */
public class DumbHttpServer implements Runnable {


    protected ConfigData appData = null;
    protected ServerSocket serverSocket = null;
    protected boolean      isStopped    = false;
    protected Thread       runningThread= null;
    protected ThreadPoolExecutor threadPool;
    protected RequestDispatcher requestDispatcher;


    public DumbHttpServer(ConfigData appData) {
        this.appData = appData;
        this.threadPool =
                ExecutorServiceProvider.getInstance(appData.getInitial_threads(), appData.getMax_threads()).getExecutorService();
        this.requestDispatcher = RequestDispatcher.getInstance(appData);

    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.appData.getListen_port());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isStopped() {
        return isStopped;
    }

    public void setStopped(boolean isStopped) {
        this.isStopped = isStopped;
    }

    @Override
    public void run() {
        synchronized(this){
            this.runningThread = Thread.currentThread();
        }
        openServerSocket();
        while(!isStopped()) {
            System.out.println("Processing started");
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                if(isStopped()) {
                    System.out.println("Server Stopped.") ;
                    return;
                }
                throw new RuntimeException(
                        "Error accepting client connection", e);
            }
            this.threadPool.execute(
                    new HttpWorker(clientSocket, this.requestDispatcher));
            System.out.println("DumbHttp servers threads:" + this.threadPool.getActiveCount());

        }
        this.threadPool.shutdown();
        System.out.println("Server Stopped.") ;
    }
}
