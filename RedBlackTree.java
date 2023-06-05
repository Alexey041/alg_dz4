public class RedBlackTree {

    private Node root;

    public boolean add(int value){
        if (root != null) {
            boolean result = addNode(root, value);
            root = rebalance(root);
            root.colour = Colour.BLACK;
            return result;
        }else{
            root = new Node();
            root.colour = Colour.BLACK;
            root.value = value;
            return true;
        }
    }

    private boolean addNode(Node node, int value){
        if (node.value == value) {
            return false;
        }else{
            if (node.value > value) {
                if (node.leftChild != null) {
                    boolean result = addNode(node.leftChild, value);
                    node.leftChild = rebalance(node.leftChild);
                    return result;
                }else{
                    node.leftChild = new Node();
                    node.leftChild.colour = Colour.RED;
                    node.leftChild.value = value;
                    return true;
                }
            }else{
                if (node.rightChild != null) {
                    boolean result = addNode(node.rightChild, value);
                    node.rightChild = rebalance(node.rightChild);
                    return result;
                }else{
                    node.rightChild = new Node();
                    node.rightChild.colour = Colour.RED;
                    node.rightChild.value = value;
                    return true;
                }
            }
        }
    }

    private Node rebalance(Node node){
        Node result = node;
        boolean needRebalance = false;
        do {
            needRebalance = false;
            if (result.rightChild != null && result.rightChild.colour == Colour.RED &&
                  (result.leftChild == null || result.leftChild.colour == Colour.BLACK)) {
                needRebalance = true;
                result = rightSwap(result);
            }
            if (result.leftChild != null && result.leftChild.colour == Colour.RED &&
                  (result.leftChild.leftChild != null && result.leftChild.leftChild.colour == Colour.RED)) {
                needRebalance = true;
                result = leftSwap(result);
            }
            if (result.leftChild != null && result.leftChild.colour == Colour.RED &&
                  (result.rightChild != null && result.rightChild.colour == Colour.RED)) {
                needRebalance = true;
                colorSwap(result);
            }
        } 
        while (needRebalance);
        return result;
    }

    private Node rightSwap(Node node){
        Node rightChild = node.rightChild;
        Node betweenChild = rightChild.leftChild;
        rightChild.leftChild = node;
        node.rightChild = betweenChild;
        rightChild.colour = node.colour;
        node.colour = Colour.RED;
        return rightChild;
    }

    private Node leftSwap(Node node){
        Node leftChild = node.leftChild;
        Node betweenChild = leftChild.rightChild;
        leftChild.rightChild = node;
        node.leftChild = betweenChild;
        leftChild.colour = node.colour;
        node.colour = Colour.RED;
        return leftChild;
    }

    private void colorSwap(Node node) {
        node.rightChild.colour = Colour.BLACK;
        node.leftChild.colour = Colour.BLACK;
        node.colour = Colour.RED;
    }
    

    private class Node {
        
        private int value;
        private Colour colour;
        private Node leftChild;
        private Node rightChild;
        
        @Override
        public String toString() {
            return "Node [value=" + value + ", colour=" + colour + ", leftChild=" + leftChild + ", rightChild="
                    + rightChild + "]";
        }
    }

    private enum Colour{
        RED, BLACK
    }
}
