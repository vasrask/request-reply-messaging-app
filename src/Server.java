public class Server {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Input should be of the form: java -jar Server.jar <port>");
            System.exit(1);
        }
        int port = Integer.parseInt(args[0]);

        MessagingServer server = new MessagingServer(port);
        server.start(port);
    }
}