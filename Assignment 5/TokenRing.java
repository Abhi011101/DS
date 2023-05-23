import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TokenRing {
    private int numNodes;
    private boolean[] tokens;
    private int currentTokenIndex;
    private Object lock;

    public TokenRing(int numNodes) {
        this.numNodes = numNodes;
        this.tokens = new boolean[numNodes];
        this.currentTokenIndex = 0;
        this.tokens[0] = true;
        this.lock = new Object();
    }

    public void start() {
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < numNodes; i++) {
            final int finalNodeIndex = i;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    node(finalNodeIndex);
                }
            });
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void node(int nodeIndex) {
        while (true) {
            try {
                Thread.sleep(new Random().nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Node " + nodeIndex + " wants to access critical section");
            System.out.println("=================================");
            requestCriticalSection(nodeIndex);
            releaseCriticalSection(nodeIndex);
        }
    }

    public void requestCriticalSection(int nodeIndex) {
        synchronized (lock) {
            if (nodeIndex != currentTokenIndex) {
                while (!tokens[nodeIndex]) {
                    tokens[currentTokenIndex] = false;
                    System.out.println("Node " + currentTokenIndex + " is in critical section");
                    currentTokenIndex = (currentTokenIndex + 1) % numNodes;
                    tokens[currentTokenIndex] = true;
                }
            }

            System.out.println("Node " + nodeIndex + " is using critical section");
            currentTokenIndex = (currentTokenIndex + 1) % numNodes;
        }
    }

    public void releaseCriticalSection(int nodeIndex) {
        synchronized (lock) {
            tokens[nodeIndex] = false;
            tokens[currentTokenIndex] = true;
            System.out.println("Node " + nodeIndex + " released critical section");
            System.out.println("=================================");
        }
    }

    public static void main(String[] args) {
        TokenRing tokenRing = new TokenRing(5);
        tokenRing.start();
    }
}
