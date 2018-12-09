#include <iostream>
#include <chrono>
#include <vector>
#include <map>
#include <sys/resource.h>

using namespace std;

class Node {
private:
	int x;
	int y;
	int size[4] = { 1, 1, 1, 1 };
	Node* parents[4] = {
		this, // row
		this, // col
		this, // cross_l
		this  // cross_r
	};

	bool equals(Node* node) {
		return this->x == node->x && this->y == node->y;
	}
public:
	Node(int x, int y) {
		this->x = x;
		this->y = y;
	}

	int getSize(int d) {
		return this->size[d];
	}

	void setParent(int d, Node* node) {
		this->parents[d] = node;
	}

	void addSize(int d, int size) {
		this->size[d] += size;
	}

	pair<int, int> toPair() {
		return make_pair(x, y);
	}

	Node* root(int d) {
		if (this->equals(parents[d])){
			return this;
		}
		return parents[d] = parents[d]->root(d);
	}

	void unite(Node* node, int d) {
		Node* n1 = this->root(d);
		Node* n2 = node->root(d);
		if (n1->equals(n2)) {
			return;
		}
		node->setParent(d, n1);
		n2->setParent(d, n1);
		n1->addSize(d, n2->getSize(d));
		// cerr << "Join node: " << node->toString() << "(" << node->getSize(d) << "), root -> " << n1->toString() << "(" << n1->getSize(d) << ")" << endl;
	}
};

void solve();

int main(int argc, char** argv) {
	chrono::system_clock::time_point S, G;
	struct rusage r;

	S = chrono::system_clock::now();

	solve();

	G = chrono::system_clock::now();
	cerr << chrono::duration_cast<chrono::milliseconds>(G - S).count() << "ms" << endl;

	getrusage(RUSAGE_SELF, &r);
	cerr << "maxrss=" << r.ru_maxrss << "KB" << endl;

	return EXIT_SUCCESS;
}

void solve() {
	const int UNI = 0;
	const int RITA = 1;

	int N, M, K;

	cin >> N >> M >> K;

	long long MM = M + 2;

	map<pair<int, int>, Node*> uni;
	map<pair<int, int>, Node*> rita;

	int turn = UNI;

	for (int i = 0; i < K; ++i) {
		int x, y;
		cin >> x >> y;

		Node* node = new Node(x, y);

		pair<int, int> par[4] = {
			make_pair((x - 1), (y)),
			make_pair((x), (y - 1)),
			make_pair((x - 1), (y - 1)),
			make_pair((x + 1), (y - 1))
		};
		pair<int, int> chd[4] = {
			make_pair((x + 1), (y)),
			make_pair((x), (y + 1)),
			make_pair((x + 1), (y + 1)),
			make_pair((x - 1), (y + 1))
		};

		map<pair<int, int>, Node*>* m = (turn == UNI ? &uni : &rita);
		for (int j = 0; j < 4; ++j) {
			map<pair<int, int>, Node*>::iterator it = m->find(par[j]);
			if (it != m->end() && it->second != NULL) {
				Node* parent = it->second;
				parent->unite(node, j);
				if (parent->root(j)->getSize(j) >= N) {
					cout << (turn == UNI ? "UNI " : "RITA ") << (i + 1) << endl;
					return;
				}
			}
			it = m->find(chd[j]);
			if (it != m->end() && it->second != NULL) {
				Node* child = it->second;
				node->unite(child, j);
				if (node->root(j)->getSize(j) >= N) {
					cout << (turn == UNI ? "UNI " : "RITA ") << (i + 1) << endl;
					return;
				}
			}
		}

		m->insert(make_pair(node->toPair(), node));

		turn = (turn == UNI ? RITA : UNI);
	}
	cout << "DRAW" << endl;
}
