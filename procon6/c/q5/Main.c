#include <stdio.h>
#include <time.h>
#include <sys/time.h>
#include <stdlib.h>
#include <sys/resource.h>

typedef struct {
	int x;
	int y;
	int idx;
} Index;

typedef struct _Node {
	int x;
	int y;
	int pl;
	int size[4];
	struct _Node* parents[4];
} Node;

Index indexList[1001001];
Index orderList[1001001];
int indexCount = 0;

Node nodeList[1001001];
int nodeCount = 0;

void Index_init(Index* index, int x, int y, int idx) {
	index->x = x;
	index->y = y;
	index->idx = idx;
}

void Node_init(Node* node, int x, int y, int pl) {
	node->x = x;
	node->y = y;
	node->pl = pl;
	for (int i = 0; i < 4; ++i) {
		node->size[i] = 1;
		node->parents[i] = node;
	}
}
int Node_equals(Node* node1, Node* node2) {
	return node1->x == node2->x && node1->y == node2->y;
}
int Node_getSize(Node* node, int d) {
	return node->size[d];
}
void Node_setParent(Node* node1, int d, Node* node2) {
	node1->parents[d] = node2;
}
void Node_addSize(Node* node, int d, int size) {
	node->size[d] += size;
}
Node* Node_root(Node* node, int d) {
	if (Node_equals(node, node->parents[d])) {
		return node;
	}
	node->parents[d] = Node_root(node->parents[d], d);
	return node->parents[d];
}
void Node_unite(Node* node1, Node* node2, int d) {
	Node* n1 = Node_root(node1, d);
	Node* n2 = Node_root(node2, d);
	if (Node_equals(n1, n2)) {
		return;
	}
	Node_setParent(node2, d, n1);
	Node_setParent(n2, d, n1);
	Node_addSize(n1, d, Node_getSize(n2, d));
}

int compIndex(const void* p1, const void* p2) {
	Index* index1 = (Index*)p1;
	Index* index2 = (Index*)p2;
	if (index1->x > index2->x) {
		return 1;
	} else if (index1->x < index2->x) {
		return -1;
	}
	if (index1->y > index2->y) {
		return 1;
	} else if (index1->y < index2->y) {
		return -1;
	}
	return 0;
}
int searchIndex(int x, int y) {
	Index index;
	Index_init(&index, x, y, -1);

	int left = 0;
	int right = indexCount - 1;
	while (left <= right) {
		int c = (left + right) / 2;

		int res = compIndex(&indexList[c], &index);
		if (res == 0) {
			return c;
		} else if (res < 0) {
			left = c + 1;
		} else {
			right = c - 1;
		}
	}
	return -1;
}
int searchNode(int x, int y, int turn, int toIdx) {
	int idx = searchIndex(x, y);
	if (idx >= 0) {
		Index* tmp = &indexList[idx];
		if (tmp->idx <= toIdx && nodeList[tmp->idx].pl == turn) {
			return tmp->idx;
		}
	}
	return -1;
}

void solve();

int main(int argc, char** argv) {
	struct timeval S, G;
	struct rusage r;

	gettimeofday(&S, NULL);

	solve();

	gettimeofday(&G, NULL);
	time_t secDiff = difftime(G.tv_sec, S.tv_sec);
	suseconds_t usecDiff = G.tv_usec - S.tv_usec;
	fprintf(stderr, "%ldms\n", secDiff * 1000 + usecDiff / 1000);

	getrusage(RUSAGE_SELF, &r);
	fprintf(stderr, "maxrss=%ldKB\n", r.ru_maxrss);

	return 0;
}

void solve() {
	const int UNI = 0;
	const int RITA = 1;

	int N, M, K;

	fscanf(stdin, "%d %d", &N, &M);
	fscanf(stdin, "%d", &K);

	int turn = UNI;

	for (int i = 0; i < K; ++i) {
		int x, y;

		fscanf(stdin, "%d %d", &x, &y);

		Index* index = &indexList[indexCount];

		Index_init(&indexList[indexCount], x, y, indexCount);
		Index_init(&orderList[indexCount], x, y, indexCount);

		++indexCount;
	}
	qsort(indexList, indexCount, sizeof(Index), compIndex);

	for (int i = 0; i < K; ++i) {
		int x, y;

		Index* index = &orderList[i];
		x = index->x;
		y = index->y;

		Node* node = &nodeList[nodeCount++];

		Node_init(node, x, y, turn);

		int par[4][2] = {
			{ (x - 1), (y) },
			{ (x), (y - 1) },
			{ (x - 1), (y - 1) },
			{ (x + 1), (y - 1) }
		};
		int chd[4][2] = {
			{ (x + 1), (y) },
			{ (x), (y + 1) },
			{ (x + 1), (y + 1) },
			{ (x - 1), (y + 1) }
		};

		for (int j = 0; j < 4; ++j) {
			int parIdx = searchNode(par[j][0], par[j][1], turn, i);
			if (parIdx >= 0) {
				Node* parent = &nodeList[parIdx];
				Node_unite(parent, node, j);
				if (Node_getSize(Node_root(parent, j), j) >= N) {
					fprintf(stdout, "%s %d\n", (turn == UNI ? "UNI" : "RITA"), i + 1);
					return;
				}
			}
			int chdIdx = searchNode(chd[j][0], chd[j][1], turn, i);
			if (chdIdx >= 0) {
				Node* child = &nodeList[chdIdx];
				Node_unite(node, child, j);
				if (Node_getSize(Node_root(node, j), j) >= N) {
					fprintf(stdout, "%s %d\n", (turn == UNI ? "UNI" : "RITA"), i + 1);
					return;
				}
			}
		}

		turn = (turn == UNI ? RITA : UNI);
	}

	fprintf(stdout, "DRAW\n");
}
