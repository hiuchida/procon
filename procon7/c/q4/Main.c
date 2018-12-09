#include <stdio.h>

#define INF (1<<29)

int main(int argc, char** argv) {
	int N, K;
	int a[5000];

	fscanf(stdin, "%d %d", &N, &K);
	for (int i = 0; i < N; ++i) {
		fscanf(stdin, "%d", &a[i]);
	}

	int left = -1;
	int right = INF;
	while (right - left > 1) {
		int x = (left + right) / 2;

		int numIntervals = 1;
		int sum = 0;
		int existBigger = 0;
		for (int i = 0; i < N; ++i) {
			if (a[i] > x) {
				existBigger = 1;
				break;
			}
			if (sum + a[i] > x) {
				++numIntervals;
				sum = 0;
			}
			sum += a[i];
		}
		if (!existBigger && numIntervals <= K) {
			right = x;
		} else {
			left = x;
		}
	}

	fprintf(stdout, "%d\n", right);

	return 0;
}
