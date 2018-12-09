#include <stdio.h>

int main(int argc, char** argv) {
	const char* S = "ABCHJK";
	const char* L = "ABCDEFGHJK";

	char x;
	int a;
	int b;

	char col;

	fscanf(stdin, "%c %d %d", &x, &a, &b);

	if (x == 'S') {
		col = S[b - 1];
	} else {
		col = L[b - 1];
	}

	fprintf(stdout, "%d%c\n", a, col);

	return 0;
}
