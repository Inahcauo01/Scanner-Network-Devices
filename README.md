# Simple Network Scanner

This is a simple Java application that allows you to scan your local network to discover devices and display the count of devices found in a graphical user interface (GUI) using Java Swing.

## Prerequisites

To run this program, you need:

- Java Development Kit (JDK) installed on your system
- A basic understanding of your local network configuration

## Getting Started

1. Clone or download the project from the repository:

    ```
    git clone https://github.com/yourusername/network-scanner.git
    ```

2. Compile the Java code:

    ```
    javac NetworkScannerWithGUI.java
    ```

3. Run the application:

    ```
    java NetworkScannerWithGUI
    ```

4. The GUI application will open, allowing you to scan your local network.

## How to Use

1. Click the "Scan Network" button to initiate the network scan.

2. The program will scan the network for devices and display their IP addresses in the list.

3. The count of devices found will be displayed at the top of the window.

4. The list will update in real-time as devices are found.

## Customize

You can customize the following aspects of the program:

- `baseIp`: Set the base IP address of your local network (e.g., "192.168.1").
- `timeout`: Set the timeout for checking device reachability (in milliseconds).
- `startIp` and `endIp`: Define the range of IP addresses to scan within your network.

Please ensure you have the necessary permissions and rights to scan your network, as this activity may require administrative access.

## Contributing

If you'd like to contribute to this project, feel free to fork the repository, make your changes, and submit a pull request.


