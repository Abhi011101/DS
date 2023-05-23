import threading
import time
import random


class TokenRing:
    def __init__(self, num_nodes):
        self.num_nodes = num_nodes
        self.tokens = [False] * num_nodes
        self.current_token_index = 0
        self.tokens[0] = True
        self.lock = threading.Lock()

    def start(self):
        threads = []
        for i in range(self.num_nodes):
            thread = threading.Thread(target=self.node, args=(i,))
            threads.append(thread)
            thread.start()

        for thread in threads:
            print(thread)
            thread.join()

    def node(self, node_index):
        while True:
            time.sleep(random.uniform(0, 1))
            print(f"Node {node_index} wants to access critical section")
            print("=================================")
            self.request_critical_section(node_index)
            self.release_critical_section(node_index)

    def request_critical_section(self, node_index):
        self.lock.acquire()
        if node_index != self.current_token_index:
            while not self.tokens[node_index]:
                self.tokens[self.current_token_index] = False
                print(f"Node {self.current_token_index} is in critical section")
                self.current_token_index = (self.current_token_index + 1) % self.num_nodes
                self.tokens[self.current_token_index] = True

        print(f"Node {node_index} is using critical section")
        self.current_token_index = (self.current_token_index + 1) % self.num_nodes
        self.lock.release()

    def release_critical_section(self, node_index):
        self.tokens[node_index] = False
        self.tokens[self.current_token_index] = True
        print(f"Node {node_index} released critical section")
        print("=================================")


if __name__ == "__main__":
    token_ring = TokenRing(5)
    token_ring.start()