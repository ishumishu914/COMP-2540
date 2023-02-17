import java.awt.Color;
import java.io.IOException;

public class lab6 
{
    Node root;

    public static class Node 
    {
        int data;
        Node left, right;

        public Node(int data)
        {
            this.data = data;
            this.left = this.right = null;
        }
    }

    public Node insert(int[] arr, Node root, int i)
    {
        if (i < arr.length) 
        {
            Node temp = new Node(arr[i]);
            root = temp;
            root.left = insert(arr, root.left, 2 * i + 1);  //left child
            root.right = insert(arr, root.right, 2 * i + 2);  // right child
        }
        return root;
    }

    public void Preorder(Node root)
    {
        if (root != null) 
        {
            System.out.print(root.data + " "); // data
            Preorder(root.left); // left subtree recursion
            Preorder(root.right); // right subtree recursion            
        } 
    }

    public void Inorder(Node root) 
    {
        if (root != null) 
        {
            Inorder(root.left); // left subtree recursion
            System.out.print(root.data + " "); // data
            Inorder(root.right); // right subtree recursion 
        }
    }

    public void Postorder(Node root)
    {
        if (root != null)
        {
            Postorder(root.left); // left subtree recursion
            Postorder(root.right); // right subtree recursion
            System.out.print(root.data + " "); // data            
        }      
    }

    public static void main(String[] args) throws IOException 
    {
        int[] array = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        lab6 tree = new lab6();
        tree.root = tree.insert(array, tree.root, 0);

        tree.Preorder(tree.root);
        System.out.println();
        tree.Inorder(tree.root);
        System.out.println();
        tree.Postorder(tree.root);
        System.out.println();

        Plot.Data curve = Plot.data();
        int[] x = {6, 3, 8, 1, 5, 7, 9, 0, 2, 4};
        int[] y = {4, 3, 3, 2, 2, 2, 2, 1, 1, 1};
     /* 0 - 6, 4
        1 - 3, 3
        2 - 8, 3
        3 - 1, 2
        4 - 5, 2
        5 - 7, 2
        6 - 9, 2
        7 - 0, 1
        8 - 2, 1
        9 - 4, 1 */
        for (int i = 0; i < y.length; i++) 
        {
            curve.xy(x[i], y[i]);    
        }

        Plot plot = Plot.plot(Plot.plotOpts().
                    title("Binary Search Tree").
                    legend(Plot.LegendFormat.BOTTOM)).
                    xAxis("x", Plot.axisOpts().range(0, 10)).
                    yAxis("y", Plot.axisOpts().range(0, 5)).
                    series("", curve, Plot.seriesOpts().
                                        marker(Plot.Marker.CIRCLE).
                                        markerColor(Color.CYAN).
                                        color(Color.BLACK));
        plot.save("BST", "png");   
    }
}