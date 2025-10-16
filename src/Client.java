import java.util.Arrays;

public class Client {
    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("Input should be of the form: java -jar Client.jar <serverIp> <serverPort> <functionId> <arg1> [arg2] [arg3] ...");
            System.exit(1);
        }

        String serverIp = args[0];
        int serverPort = Integer.parseInt(args[1]);
        int functionId = Integer.parseInt(args[2]);
        String[] arguments = Arrays.copyOfRange(args, 3, args.length);

        MessagingClient client = new MessagingClient(serverIp, serverPort);
        client.runFunction(functionId, arguments);
    }
}