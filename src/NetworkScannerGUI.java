import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class NetworkScannerGUI {
    private JFrame frame;
    private JTable deviceTable;
    private DefaultTableModel tableModel;
    private JLabel countLabel;

    public NetworkScannerGUI() {
        frame = new JFrame("Network Scanner");
        tableModel = new DefaultTableModel();
        tableModel.addColumn("IP");
        tableModel.addColumn("Host Name");
        tableModel.addColumn("MAC Address");
        deviceTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(deviceTable);
        countLabel = new JLabel("Devices Found: 0");

        JButton scanButton = new JButton("Scan Network");
        scanButton.addActionListener(e -> scanNetwork());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(scanButton);

        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(countLabel, BorderLayout.NORTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private void scanNetwork() {
        tableModel.setRowCount(0); // Clear the table

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
                        String hostName = address.getHostName();
                        String macAddress = getMacAddress(address);
                        tableModel.addRow(new Object[]{ipAddress, hostName, macAddress});
                        deviceCount.getAndIncrement();
                        countLabel.setText("Devices Found: " + deviceCount);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        executorService.shutdown();
    }

    private String getMacAddress(InetAddress address) {
        try {
            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(address);
            if (networkInterface != null) {
                byte[] mac = networkInterface.getHardwareAddress();
                if (mac != null) {
                    StringBuilder macAddress = new StringBuilder();
                    for (int i = 0; i < mac.length; i++) {
                        macAddress.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? ":" : ""));
                    }
                    return macAddress.toString();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "N/A";
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new NetworkScannerGUI();
        });
    }
}
