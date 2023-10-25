import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.*;

public class NetworkScannerGUI {
    private JFrame frame;
    private JList<String> ipList;
    private DefaultListModel<String> listModel;
    private JLabel countLabel;


    public NetworkScannerGUI() {
        frame = new JFrame("Network Scanner");
        listModel = new DefaultListModel<>();
        ipList = new JList<>(listModel);
        frame.add(new JScrollPane(ipList));
        countLabel = new JLabel("Devices Found: 0");


        JButton scanButton = new JButton("Scan Network");
        scanButton.addActionListener(e -> scanNetwork());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(scanButton);
        frame.add(buttonPanel, "South");
        frame.add(countLabel, "North");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private void scanNetwork() {
        listModel.clear();

        AtomicInteger deviceCount = new AtomicInteger();

        String baseIp = "192.168.1";
        int timeout = 1000; // Timeout in milliseconds
        int startIp = 1;
        int endIp = 254;

        ExecutorService executorService = Executors.newFixedThreadPool(20);

        for (int i = startIp; i <= endIp; i++) {
            final int currentIp = i;
            executorService.execute(() -> {
                String ipAddress = baseIp + "." + currentIp;
                try {
                    InetAddress address = InetAddress.getByName(ipAddress);
                    if (address.isReachable(timeout)) {
                        listModel.addElement("Device found at IP: " + ipAddress);
                        deviceCount.getAndIncrement();
                        System.out.println(deviceCount);
                        countLabel.setText("Devices Found: " + deviceCount);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        executorService.shutdown();
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new NetworkScannerGUI();
        });
    }
}
