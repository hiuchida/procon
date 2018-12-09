#include <stdio.h>
#include <memory.h>

#define VALUE_MAX 100000L
#define TRY_MAX 100

int solve(int from, int to) {
	if (from == to) {
		return 0;
	}
	char s1[VALUE_MAX + 1];
	char s2[VALUE_MAX + 1];
	memset(s1, 0, sizeof(s1));
	s1[from] = 1;
	for (int i = 1; i <= TRY_MAX; ++i) {
		memset(s2, 0, sizeof(s2));
		for (int j = 0; j <= VALUE_MAX; ++j) {
			if (!s1[j]) {
				continue;
			}
			long x = j * 2L;
			long y = j / 2L;
			long z = j + 1L;
			if (x == to || y == to || z == to) {
				return i;
			}
			if (x <= VALUE_MAX) { s2[x] = 1; }
			if (y <= VALUE_MAX) { s2[y] = 1; }
			if (z <= VALUE_MAX) { s2[z] = 1; }
		}
		memcpy(s1, s2, sizeof(s1));
	}

	return -1;
}

int main(int argc, char** argv) {
	int a, b;

	fscanf(stdin, "%d %d", &a, &b);

	fprintf(stdout, "%d\n", solve(a, b));

	return 0;
}
