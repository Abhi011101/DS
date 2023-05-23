import java.util.ArrayList;
import java.util.List;

class Node implements Runnable {
    private int id;
    private int time;
    private List<Node> network;
    
    public Node(int id, int time) {
        this.id = id;
        this.time = time;
        this.network = new ArrayList<>();
    }
    
    public void addNeighbor(Node neighbor) {
        network.add(neighbor);
    }
    
    public void syncClocks() {
        int sum = time;
        int numNodes = 1;
        
        // Calculate the sum of times across the network
        for (Node neighbor : network) {
            sum += neighbor.getTime();
            numNodes++;
        }
        
        // Calculate the average time
        int averageTime = sum / numNodes;
        
        // Update the time of each node
        for (Node neighbor : network) {
            neighbor.setTime(averageTime);
        }
    }
    
    public int getId() {
        return id;
    }
    
    public int getTime() {
        return time;
    }
    
    public void setTime(int time) {
        this.time = time;
    }
    
    @Override
    public void run() {
        System.out.println("Node " + id + " started with time: " + time);
        
        // Synchronize clocks every 2 seconds
        while (true) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            syncClocks();
            
            System.out.println("Node " + id + " synchronized time: " + time);
        }
    }
}

public class BerkeleyClockSync {
    public static void main(String[] args) {
        // Create nodes
        Node node1 = new Node(1, 10);
        Node node2 = new Node(2, 15);
        Node node3 = new Node(3, 20);
        
        // Add neighbors
        node1.addNeighbor(node2);
        node1.addNeighbor(node3);
        node2.addNeighbor(node1);
        node2.addNeighbor(node3);
        node3.addNeighbor(node1);
        node3.addNeighbor(node2);
        
        // Start nodes in separate threads
        Thread thread1 = new Thread(node1);
        Thread thread2 = new Thread(node2);
        Thread thread3 = new Thread(node3);
        
        thread1.start();
        thread2.start();
        thread3.start();
        
        // Wait for threads to complete
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
