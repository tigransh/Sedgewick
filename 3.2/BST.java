//algorithm 3.3 (398+399+407+409+411+413)
//with 3.2.6(416)
import java.util.List;
import java.util.ArrayList;

public class BST<Key extends Comparable<Key>,Value>
{
	private Node root;
	
	private class Node
	{
		private Key key;
		private Value value;
		private Node left;
		private Node right;
		private int nodesCount;
	
		public Node(Key key,Value value,int nodesCount)
		{
			this.key=key;
			this.value=value;
			this.nodesCount=nodesCount;
		}
	}
	
	public void put(Key key,Value value)
	{
		if(value==null) delete(key);
		else root=put(root,key,value);
	}
	
	private Node put(Node node,Key key,Value value)
	{
		if(node==null) return new Node(key,value,1);
		int comp=key.compareTo(node.key);
		if(comp<0) node.left=put(node.left,key,value);
		else if(comp>0) node.right=put(node.right,key,value);
		else node.value=value;
		node.nodesCount=size(node.left)+size(node.right)+1;
		return node;
	}
	
	public Value get(Key key)
	{
		return get(root, key);
	}
	
	private Value get(Node node,Key key)
	{
		if(node==null) return null;
		int comp=key.compareTo(node.key);
		if(comp<0) return get(node.left,key);
		else if(comp>0) return get(node.right,key);
		else return node.value;
	}
	
	public void delete(Key key)
	{
		root=delete(root,key);
	}
	
	private Node delete(Node node,Key key)
	{
		if(node==null) return null;
		int comp=key.compareTo(node.key);
		if(comp<0) node.left=delete(node.left,key);
		else if(comp>0) node.right=delete(node.right,key);
		else
		{
			if(node.left==null) return node.right;
			if(node.right==null) return node.left;
			Node temp=node;
			node=min(temp.right);
			node.right=deleteMin(temp.right);
			node.left=temp.left;
		}
		
		node.nodesCount=size(node.left)+size(node.right)+1;
		return node;
	}
	
	public boolean contains(Key key)
	{
		return get(key)!=null;
	}
	
	public boolean isEmpty()
	{
		return size()==0;
	}
	
	public int size()
	{
		return size(root);
	}
	
	private int size(Node node)
	{
		if(node==null)return 0;
		return node.nodesCount;
	}
	
	public Key min()
	{
		if(isEmpty()) return null;
		return min(root).key;
	}
	
	private Node min(Node node)
	{
		if(node.left==null) return node;
		return min(node.left);
	}
	
	public Key max()
	{
		if(isEmpty()) return null;
		return max(root).key;
	}
	
	private Node max(Node node)
	{
		if(node.right==null) return node;
		return max(node.right);
	}
	
	public Key floor(Key key)
	{
		Node node=floor(root,key);
		if(node==null) return null;
		return node.key;
	}
	
	private Node floor(Node node,Key key)
	{
		if(node==null) return null;
		int comp=key.compareTo(node.key);
		if(comp==0) return node;
		if(comp<0) return floor(node.left,key);
		Node temp=floor(node.right,key);
		if(temp!=null) return temp;
		return node;
	}
	
	public Key ceiling(Key key)
	{
		Node node=ceiling(root,key);
		if(node!=null) return node.key;
		return null;
	}
	
	private Node ceiling(Node node,Key key)
	{
		if(node==null) return null;
		int comp=key.compareTo(node.key);
		if(comp==0) return node;
		if(comp<0)
		{
			Node temp=ceiling(node.left,key);
			if(temp!=null) return temp;
			return node;
		}
		return ceiling(node.right,key);
	}
	
	public int rank(Key key)
	{
		return rank(root,key);
	}
	
	private int rank(Node node,Key key)
	{
		if(node==null) return 0;
		int comp=key.compareTo(node.key);
		if(comp<0) return rank(node.left,key);
		if(comp>0) return 1+size(node.left)+rank(node.right,key);
		return size(node.left);
	}
	
	public Key select(int k)
	{
		if(k<0||k>=size()) return null;
		Node node=select(root,k);
		return node.key;
	}
	
	private Node select(Node node,int k)
	{
		if(node==null) return null;
		int temp=size(node.left);
		if(temp<k) return select(node.right,k-temp-1);
		if(temp>k) return select(node.left,k);
		return node;
	}
	
	public void deleteMin()
	{
		root=deleteMin(root);
	}
	
	private Node deleteMin(Node node)
	{
		if(node.left==null) return node.right;
		node.left=deleteMin(node.left);
		node.nodesCount=size(node.left)+size(node.right)+1;
		return node;
	}
	
	public void deleteMax()
	{
		root=deleteMax(root);
	}
	
	private Node deleteMax(Node node)
	{
		if(node.right==null) return node.left;
		node.right=deleteMax(node.right);
		node.nodesCount=size(node.left)+size(node.right)+1;
		return node;
	}
	
	public int size(Key lo,Key hi)
	{
		if(lo.compareTo(hi)>0) return 0;
		if(contains(hi)) return rank(hi)-rank(lo)+1;
		else return rank(hi)-rank(lo);
	}
	
	public Iterable<Key> keys(Key lo,Key hi)
	{
		List<Key> list=new ArrayList<Key>();
		keys(root,list,lo,hi);
		return list;
	}
	
	private void keys(Node node,List<Key> list,Key lo,Key hi)
	{
		if(node==null) return;
		int compLo=lo.compareTo(node.key);
		if(compLo<0) keys(node.left,list,lo,hi);
		int compHi=hi.compareTo(node.key);
		if(compLo<=0&&compHi>=0) list.add(node.key);
		if(compHi>0) keys(node.right,list,lo,hi);
		
	}
	
	public Iterable<Key> keys()
	{
		return keys(min(),max());
	}
	
	public int height()
	{
		return height(root);
	}
	
	private int height(Node node)
	{
		if(node==null) return -1;
		return 1+Math.max(height(node.left),height(node.right));
	}
	
	public static void main(String[] args)
	{
		BST<String,Integer> bst=new BST<String,Integer>();
		for(int i=0;!StdIn.isEmpty();i++)
		{
			String s=StdIn.readString();
			bst.put(s,i);
		}
		
		for(String s:bst.keys())
			StdOut.println(s+" "+bst.get(s));
	}
}