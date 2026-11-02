public class BinarySearchTree {


    private class TreeNode {
        public TreeObject data;
        public TreeNode left, right;

        public TreeNode(TreeObject obj) {
            this.data = obj;
            this.left = null;
            this.right = null;
        }
    }

    private TreeNode root;


    public BinarySearchTree() {
        root = null;
    }

    public void add(TreeObject obj) {
        if (obj == null || obj.getKey() == null) {
            return;
        }
        root = addNode(root, new TreeNode(obj));
    }


    private TreeNode addNode(TreeNode rootNode, TreeNode newNode) {
        if (rootNode == null) {
            return newNode;
        } else {
            TreeKey newID = newNode.data.getKey();
            TreeKey rootID = rootNode.data.getKey();


            if (newID.isLessThan(rootID)) {
                rootNode.left = addNode(rootNode.left, newNode);
            }

            else if (rootID.isLessThan(newID)) {
                rootNode.right = addNode(rootNode.right, newNode);
            }


            return rootNode;
        }
    }


    public TreeObject find(TreeKey key) {
        if (key == null) {
            return null;
        }
        TreeNode resultNode = findNode(root, key);
        if (resultNode != null) {
            return resultNode.data;
        }
        return null;
    }


    public Patient find(PatientID id) {
        return (Patient) find((TreeKey) id);
    }


    private TreeNode findNode(TreeNode rootNode, TreeKey key) {
        if (rootNode == null) {
            return null;
        }

        TreeKey rootID = rootNode.data.getKey();

        if (key.match(rootID)) {
            return rootNode; // Found!
        } else if (key.isLessThan(rootID)) {
            return findNode(rootNode.left, key); // Search left subtree
        } else {
            return findNode(rootNode.right, key); // Search right subtree
        }
    }


    public void print() {
        printTree(root);
    }


    private void printTree(TreeNode rootNode) {
        if (rootNode != null) {
            printTree(rootNode.left);
            System.out.println(rootNode.data.toString());
            printTree(rootNode.right);
        }
    }
}
