import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class NetworkScannerGUI {
    private JFrame frame;
    private JList<String> ipList;
    private DefaultListModel<String> listModel;

    public NetworkScannerGUI() {
        frame = new JFrame("Network Scanner");
        listModel = new DefaultListModel<>();
        ipList = new JList<>(listModel);
        frame.add(new JScrollPane(ipList));

        JButton scanButton = new JButton("Scan Network");
        scanButton.addActionListener(e -> scanNetwork());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(scanButton);
        frame.add(buttonPanel, "South");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setVisible(true);
    }

    private void scanNetwork() {
        listModel.clear();

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
