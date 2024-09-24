/**
* Assignment 1
* Andrew T. Pipo
**/
import java.io.* ;
import java.net.* ;

public final class WebServer
{
    public static void main(String argv[]) throws Exception
    {
        int port = 15001;
        Socket socket;
        // Establish the listen socket
        ServerSocket serverSocket = new ServerSocket(port);
        // Process HTTP service requests in an infinite loop
        while (true) { 
            // listen for a TCP connection request 
            socket = serverSocket.accept();  
            // Construct object to process HTTP request message
            HttpRequest request = new HttpRequest(socket);

            // Create a new thread
            Thread thread = new Thread(request);

            // Start thread
            thread.start();
        }
    }
}

final class HttpRequest implements Runnable
{
    final static String CRLF = "\r\n";
    Socket socket;
    // Constructor
    public HttpRequest(Socket socket) throws Exception 
    {
    this.socket = socket;
    }
    // Implement the run() method of the Runnable interface.
    public void run()
    {
        try {
            processRequest();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    private void processRequest() throws Exception
    {
        // Get a reference to the socket's input and output streams.
        InputStream is = socket.getInputStream();
        DataOutputStream os = new DataOutputStream(socket.getOutputStream());
        // Set up input stream filters.
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        // Get the request line of the HTTP request message.
        String requestLine = br.readLine();
        // Display the request line.
        System.out.println();
        System.out.println(requestLine); 

        // Get and display the header lines.
        String headerLine = null;
        while ((headerLine = br.readLine()).length() != 0) {
            System.out.println(headerLine);
        }

        os.close();
        br.close();
        socket.close();
    }

}