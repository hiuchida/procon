import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Main {
	public static void main(String[] args) {
		long S = System.currentTimeMillis();
		try {
			solve();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			long G = System.currentTimeMillis();
			System.err.println((G - S) + "ms");
		}
	}

	private static void solve() throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String[] ss = in.readLine().split(" ");
		int N = Integer.parseInt(ss[0]);
		int M = Integer.parseInt(ss[1]);
		int K = Integer.parseInt(in.readLine());

		Map<Point, Node> uni = new HashMap<>();
		Map<Point, Node> rita = new HashMap<>();

		for (int i = 0; i < K; ++i) {
			ss = in.readLine().split(" ");
			int x = Integer.parseInt(ss[0]);
			int y = Integer.parseInt(ss[1]);
			Map<Point, Node> turn = (i % 2 == 0 ? uni : rita);
			Point curPoint = new Point(x, y);
			Node curNode = new Node(curPoint);
			Point[] left = {
					new Point(x - 1, y), // êº
					new Point(x, y - 1), // ñk
					new Point(x - 1, y - 1), // ñkêº
					new Point(x + 1, y - 1) // ñkìå
			};
			Point[] right = {
					new Point(x + 1, y), // ìå
					new Point(x, y + 1), // ìÏ
					new Point(x + 1, y + 1), // ìÏìå
					new Point(x - 1, y + 1) // ìÏêº
			};
			for (int j = 0; j < 4; ++j) {
				Node leftNode = turn.get(left[j]);
				if (leftNode != null) {
					leftNode.unite(curNode, j);
					if (leftNode.getSize(j) >= N) {
						System.out.println((turn == uni ? "UNI " : "RITA ") + (i + 1));
						return;
					}
				}
				Node rightNode = turn.get(right[j]);
				if (rightNode != null) {
					rightNode.unite(curNode, j);
					if (rightNode.getSize(j) >= N) {
						System.out.println((turn == uni ? "UNI " : "RITA ") + (i + 1));
						return;
					}
				}
			}
			turn.put(curPoint, curNode);
		}
		System.out.println("DRAW");
	}
}

class Point {
	private int x;
	private int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int hashCode() {
		int hashCode = 17;
		hashCode = hashCode * 31 + x;
		hashCode = hashCode * 31 + y;
		return hashCode;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Point) {
			Point that = (Point) o;
			return this.x == that.x && this.y == that.y;
		}
		return false;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}

class Node {
	private Point point;
	private int[] size = { 1, 1, 1, 1 };
	private Node[] parents = {
		this, // row
		this, // col
		this, // cross_l
		this // cross_r
	};

	public Node(Point pt) {
		this.point = pt;
	}

	public boolean equals(Node node) {
		return this.point.equals(node.point);
	}

	public Node getRoot(int d) {
		if (equals(parents[d])) {
			return this;
		}
		parents[d] = parents[d].getRoot(d);
		return parents[d];
	}

	public void unite(Node node, int d) {
		Node n1 = this.getRoot(d);
		Node n2 = node.getRoot(d);
		if (n1.equals(n2)) {
			return;
		}
		n2.parents[d] = n1;
		n1.size[d] += n2.size[d];
		n2.size[d] = 0;
	}

	public int getSize(int d) {
		return this.getRoot(d).size[d];
	}
}
