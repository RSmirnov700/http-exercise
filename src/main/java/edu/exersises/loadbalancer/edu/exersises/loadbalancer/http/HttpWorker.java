package edu.exersises.loadbalancer.edu.exersises.loadbalancer.http;

import edu.exersises.loadbalancer.lb.RequestDispatcher;
import org.apache.commons.lang.time.StopWatch;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseFactory;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.StringTokenizer;

/**
 * Created by rsmirnov on 8/13/14.
 */
public class HttpWorker implements Runnable {
    static final String HTML_START = "<html><title>This is your LB</title><body>";

    private static final String HTML_END = "</body></html>";
    private Socket clientSocket = null;
    private RequestDispatcher requestDispatcher = null;
    private StopWatch timer;


    public HttpWorker(Socket clientSocket, RequestDispatcher rd) {
        this.clientSocket = clientSocket;
        this.requestDispatcher = rd;
        timer = new StopWatch();
    }


    @Override
    public void run() {
        BufferedReader input = null;
        DataOutputStream output = null;
        try {
            timer.start();
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new DataOutputStream(clientSocket.getOutputStream());
            String requestString = input.readLine();
            if (requestString != null) {
                StringTokenizer tokenizer = new StringTokenizer(requestString);
                String httpMethod = tokenizer.nextToken();
                String httpQueryString = tokenizer.nextToken();
                String dispatcherResponse = requestDispatcher.dispatchRequest(httpQueryString);
                timer.suspend();
                sendResponse("HttpWorker: request processed within " + timer.toString() + " ms<br>"
                        + dispatcherResponse, output);
                timer.resume();
                timer.stop();
                System.out.println("Request processed: " + timer.toString());
                System.out.println(dispatcherResponse);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (output != null) try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (input != null) try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void sendResponse(String responseString, DataOutputStream outToClient) throws IOException {

        String body = HTML_START + responseString + HTML_END;
        StringBuffer sBody = new StringBuffer("HTTP/1.1 200 OK\r\n")
                .append("Content-Length: " + body.length() + "\r\n")
                .append("Content-Type: text/html\r\n\r\n\r\n").append(body);

        try {
            if (!clientSocket.isClosed() && clientSocket.isConnected()) {
                outToClient.writeBytes(sBody.toString());
                outToClient.flush();
            }
        } catch (SocketException se) {
            //just ignore it....
        }
    }
}
