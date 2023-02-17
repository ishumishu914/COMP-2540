public class test 
{
    public static boolean flag = false;

    public static class Node
    {        
        int key;
        Node left, right;
        Node root;
    };

    public static Node newNode(int data)
    {
        Node node = new Node();
        node.root = node;
        node.key = data;
        node.left = null;
        node.right = null;
        return node;
    }

    public static Node TreeInsert(Node x, int k) throws Exception
    {
        Node t = null;
        
        /*if (x == null) 
        {
            t = newNode(k);
        }
        else if (k < x.key)
            x.left = TreeInsert(x.left, k);
        else if (k > x.key)
            x.right = TreeInsert(x.right, k);
        else
            throw new Exception("Existing key!");*/

        while (x != null) 
        {
            t = x;
            if (k < x.key) 
            {
                x = x.left;    
            }    
            else
                x = x.right;
        }
        Node y = newNode(k);
        if (t == null) {
            t = y;
        }
        else if(k < t.key)
            t.left = y;
        else
            t.right = y;
        
        return t;
    }

    public static void TreeSearch(Node x, int k)
    {
        /*if (x == null || k == x.key) 
            return x;    
        else if (k < x.key)
            return TreeSearch(x.left, k);
        else
            return TreeSearch(x.right, k);*/

        if (x.root == null) 
            System.out.println("tree is empty");    
        else
        {
            if (x.key == k) 
            {
                flag = true;
                return;
            }
            if (flag == false && x.left != null) 
            {
                TreeSearch(x.left, k);    
            }
            if (flag == false && x.right != null) 
            {
                TreeSearch(x.right, k);    
            }
        }
        if (flag == true) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }

    public static void inorder(Node root)
    {
        if (root != null) 
        {
            inorder(root.left);
            System.out.print(root.key + " ");
            inorder(root.right);
        } 
    }

    public static void main(String[] args) throws Exception 
    {
        Node tree = null;
        /*tree = TreeInsert(tree, 16);
        //tree.root = TreeInsert(tree, 16);5
        TreeInsert(tree, 5);
        TreeInsert(tree, 18);
        TreeInsert(tree, 2);
        TreeInsert(tree, 15);
        TreeInsert(tree, 17);
        TreeInsert(tree, 19);
        TreeInsert(tree, 1);
        TreeInsert(tree, 3);
        TreeInsert(tree, 13);
        TreeInsert(tree, 12);
        TreeInsert(tree, 14);*/
        tree = newNode(1);//TreeInsert(tree, 1);
        TreeInsert(tree, 2);
        TreeInsert(tree, 3);
        TreeInsert(tree, 4);
        TreeInsert(tree, 5);
        TreeInsert(tree, 6);
        TreeInsert(tree, 7);
        TreeInsert(tree, 8);
        TreeInsert(tree, 9);
        TreeInsert(tree, 10);
        TreeInsert(tree, 11);
        TreeInsert(tree, 12);

        inorder(tree);
        System.out.println("\n" + "root: " + tree.root.key);

        for (int k = 1; k <= 20; k++) 
        {
            System.out.print("#" + k + ": ");
            TreeSearch(tree.root, k);          
        }
    }    
}