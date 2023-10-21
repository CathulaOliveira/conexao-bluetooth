import javax.bluetooth.*;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import java.io.IOException;
import java.io.OutputStream;

public class Main {

    public static void main(String[] args) {
        try {
            LocalDevice localDevice = LocalDevice.getLocalDevice();
            DiscoveryAgent discoveryAgent = localDevice.getDiscoveryAgent();

            // Descubra dispositivos Bluetooth disponíveis
            RemoteDevice[] devices = discoveryAgent.retrieveDevices(DiscoveryAgent.NOT_DISCOVERABLE);

            for (RemoteDevice device : devices) {
                System.out.println("Dispositivo encontrado: " + device.getFriendlyName(true));

                // Aqui você pode adicionar lógica para escolher um dispositivo específico
                // por meio de comparação com o nome do dispositivo ou endereço MAC.

                if (device.getFriendlyName(true).equals("T")) {
                    String deviceAddress = device.getBluetoothAddress();

                    // Conecte-se ao dispositivo
                    String connectionURL = "btspp://" + deviceAddress + ":1;authenticate=false;encrypt=false;master=true";
                    StreamConnection connection = (StreamConnection) Connector.open(connectionURL);
                    OutputStream outputStream = connection.openOutputStream();

                    // Envie dados para o dispositivo
                    String message = "Hello, Bluetooth World!";
                    outputStream.write(message.getBytes());
                    outputStream.flush();

                    // Feche a conexão
                    outputStream.close();
                    connection.close();
                }
            }
        } catch (BluetoothStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
