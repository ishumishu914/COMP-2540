class lab8 
{
	public static class Node 
	{
		int key;
		Node left, right, parent;

		public Node(int data)
		{
			key = data;
			left = right = parent = null;
		}
	}

	public static Node root;
    public static boolean flag = false;

	public lab8() 
	{ 
	    root = null; 
	}

	public lab8(int data) 
	{ 
	    root = new Node(data);
	}

	public void TreeInsert(int key) 
	{ 
	    root = TreeInsertRec(root, key); 
	}

	public Node TreeInsertRec(Node x, int k)
	{
		if (x == null) 
		{
			x = new Node(k);
		}
		else
		{
			Node temp = null;
			if (k < x.key)
			{
				temp = TreeInsertRec(x.left, k);
				x.left = temp;
				temp.parent = x;
			}
			else if (k > x.key)
			{
				temp = TreeInsertRec(x.right, k);			
				x.right = temp;
				temp.parent = x;
			}
		}
		return x;
	}

	public void inorder() 
	{ 
		inorderRec(root); 
	}

	public void inorderRec(Node root)
	{
		if (root != null) {
			inorderRec(root.left);
			System.out.print(root.key + " ");
			inorderRec(root.right);
		}
	}

    public static void TreeSearch(Node x, int k)
    {
        if (x == null)
            flag = false;
        else
        {
            if(k == x.key)
            {
                flag = true;
                return;
            }
            else if (k < x.key)
                TreeSearch(x.left, k);
            else
                TreeSearch(x.right, k);              
        }
    }

	public static Node TreeSuccessor(Node x, Node k)
	{
		if (k.right != null) 
			return TreeMin(x.right);
		else
		{
			Node p = k.parent;
			while (p != null && k == p.right) 
			{
				k = p;
				p = p.parent;
			}
			return p;
		}			
	}

	public static Node TreeMin(Node x)
	{
		while (x.left != null) 
			x = x.left;	
		return x;
	}

	public static void main(String[] args)
	{
		lab8 tree = new lab8();
		tree.TreeInsert(16);
		tree.TreeInsert(5);
		tree.TreeInsert(18);
		tree.TreeInsert(2);
		tree.TreeInsert(15);
		tree.TreeInsert(17);
		tree.TreeInsert(19);
		tree.TreeInsert(1);
		tree.TreeInsert(2);
		tree.TreeInsert(13);
		tree.TreeInsert(12);
		tree.TreeInsert(14);

        tree.inorder();
        System.out.println("\n" + "root: " + tree.root.key);

        for (int k = 1; k <= 20; k++) 
        {
            /*System.out.print("k = " + k + ": ");
            TreeSearch(lab8.root, k);
            if (flag) 
                System.out.println(true);
            else 
                System.out.println(false);*/
				
			System.out.print("k = " + k + ": ");
			Node temp = null;
			temp = new Node(k);
			Node suc = TreeSuccessor(lab8.root, temp);
			
			if (suc != null) 
				System.out.println("Succesor of " + temp.key + " is " + suc.key);
			else
				System.out.println("Succesor does not exist");
        }
	}
}