class Node {
    int key, height;
    Node left, right;

    Node(int d) {
        key = d;
        height = 1;
    }
}

class AVLTree {
    Node root;

    int height(Node N) { // get the height of the node
        if (N == null) {
            return 0;
        }
        return N.height;
    }

    int max(int a, int b) { // get the maximum of two integers
        return (a > b) ? a : b;
    }

    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        tree.root = tree.insertNode(tree.root, 33);
        tree.root = tree.insertNode(tree.root, 13);
        tree.root = tree.insertNode(tree.root, 53);
        tree.root = tree.insertNode(tree.root, 9);
        tree.root = tree.insertNode(tree.root, 21);
        tree.root = tree.insertNode(tree.root, 61);
        tree.root = tree.insertNode(tree.root, 8);
        tree.root = tree.insertNode(tree.root, 11);
        tree.printTree(tree.root, "", true);
        tree.root = tree.deleteNode(tree.root, 13);
        System.out.println("After Deletion: ");
        tree.printTree(tree.root, "", true);
    }

    Node rightRotate(Node y) { // right rotate the node
        Node x = y.left;
        Node T2 = x.right;
        x.right = y;
        y.left = T2;
        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;
        return x;
    }

    Node leftRotate(Node x) { // left rotate the node
        Node y = x.right;
        Node T2 = y.left;
        y.left = x;
        x.right = T2;
        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;
        return y;
    }

    int getBalanceFactor(Node N) { // get the balance factor of the node
        if (N == null) {
            return 0;
        }

        return height(N.left) - height(N.right);
    }

    Node insertNode(Node node, int key) { // insert a node
        if (node == null) {
            return (new Node(key));
        }

        if (key < node.key) {
            node.left = insertNode(node.left, key);
        } else if (key > node.key) {
            node.right = insertNode(node.right, key);
        } else {
            return node;
        }

        node.height = 1 + max(height(node.left), height(node.right));
        int balanceFactor = getBalanceFactor(node);
        if (balanceFactor > 1) {
            if (key < node.left.key) {
                return rightRotate(node);
            } else if (key > node.left.key) {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
        }
        if (balanceFactor < -1) {
            if (key > node.right.key) {
                return leftRotate(node);
            } else if (key < node.right.key) {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
        }
        return node;
    }

    Node nodeWithMimumValue(Node node) { // get the node with the minimum value
        Node current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    Node deleteNode(Node root, int key) { // delete a node
        if (root == null) {
            return root;
        }
        if (key < root.key) {
            root.left = deleteNode(root.left, key);
        } else if (key > root.key) {
            root.right = deleteNode(root.right, key);
        } else {
            if ((root.left == null) || (root.right == null)) {
                Node temp = null;
                if (temp == root.left) {
                    temp = root.right;
                } else {
                    temp = root.left;
                }
                if (temp == null) {
                    temp = root;
                    root = null;
                } else {
                    root = temp;
                }
            } else {
                Node temp = nodeWithMimumValue(root.right);
                root.key = temp.key;
                root.right = deleteNode(root.right, temp.key);
            }
        }
        if (root == null) {
            return root;
        }
        root.height = max(height(root.left), height(root.right)) + 1;
        int balanceFactor = getBalanceFactor(root);
        if (balanceFactor > 1) {
            if (getBalanceFactor(root.left) >= 0) {
                return rightRotate(root);
            } else {
                root.left = leftRotate(root.left);
                return rightRotate(root);
            }
        }
        if (balanceFactor < -1) {
            if (getBalanceFactor(root.right) <= 0) {
                return leftRotate(root);
            } else {
                root.right = rightRotate(root.right);
                return leftRotate(root);
            }
        }
        return root;
    }

    void preOrder(Node node) { // pre-order traversal
        if (node != null) {
            System.out.print(node.key + " ");
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    void inOrder(Node node) { // in-order traversal
        if (node != null) {
            inOrder(node.left);
            System.out.print(node.key + " ");
            inOrder(node.right);
        }
    }

    void postOrder(Node node) { // post-order traversal
        if (node != null) {
            postOrder(node.left);
            postOrder(node.right);
            System.out.print(node.key + " ");
        }
    }

    private void printTree(Node node, String indent, boolean last) { // print the tree
        if (node != null) {
            System.out.print(indent);
            if (last) {
                System.out.print("R----");
                indent += "     ";
            } else {
                System.out.print("L----");
                indent += "|    ";
            }
            System.out.println(node.key);
            printTree(node.left, indent, false);
            printTree(node.right, indent, true);
        }
    }
}